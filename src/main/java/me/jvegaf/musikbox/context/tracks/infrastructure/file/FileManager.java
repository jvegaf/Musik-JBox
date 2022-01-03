package me.jvegaf.musikbox.context.tracks.infrastructure.file;


import lombok.extern.log4j.Log4j2;
import me.jvegaf.musikbox.context.tracks.application.create.CreateTrackCommand;
import me.jvegaf.musikbox.shared.domain.Service;
import me.jvegaf.musikbox.shared.domain.bus.command.CommandBus;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.id3.AbstractID3v2Tag;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Log4j2
@Service
public final class FileManager {

    private final CommandBus bus;



    public FileManager(CommandBus bus) {
        this.bus = bus;

        Logger.getLogger("org.jaudiotagger").setLevel(Level.OFF);
        Logger.getLogger("org.jaudiotagger.tag").setLevel(Level.OFF);
        Logger.getLogger("org.jaudiotagger.tag.id3").setLevel(Level.OFF);
        Logger.getLogger("org.jaudiotagger.tag.datatype").setLevel(Level.OFF);
        Logger.getLogger("org.jaudiotagger.audio.mp3.MP3File").setLevel(Level.OFF);
        Logger.getLogger("org.jaudiotagger.tag.id3.ID3v23Tag").setLevel(Level.OFF);

    }

    public void dispatchFiles(File[] files) {
        for (File file : files) {

            try {



                MP3File f = (MP3File) AudioFileIO.read(file);
                AbstractID3v2Tag tag = f.getID3v2Tag();
                String title = tag.getFirst(FieldKey.TITLE);
                if (title == null || title.isEmpty()){
                    title = file.getName().replaceAll(".mp3", "");
                }

                bus.dispatch(new CreateTrackCommand(
                        title,
                        file.getAbsolutePath(),
                        f.getAudioHeader().getTrackLength(),
                        tag.getFirst(FieldKey.ARTIST),
                        tag.getFirst(FieldKey.ALBUM),
                        tag.getFirst(FieldKey.GENRE),
                        tag.getFirst(FieldKey.YEAR),
                        tag.getFirst(FieldKey.COMMENT),
                        tag.getFirst(FieldKey.BPM),
                        tag.getFirst(FieldKey.KEY)
                ));

            } catch (CannotReadException | TagException | IOException | ReadOnlyFileException | InvalidAudioFrameException e) {
                log.error("Error reading file: " + file.getName());
            }
        }
    }

}
