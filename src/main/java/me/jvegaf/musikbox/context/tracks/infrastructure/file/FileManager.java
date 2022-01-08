package me.jvegaf.musikbox.context.tracks.infrastructure.file;


import lombok.extern.log4j.Log4j2;
import me.jvegaf.musikbox.context.tracks.application.create.CreateTrackCommand;
import me.jvegaf.musikbox.shared.domain.Service;
import me.jvegaf.musikbox.shared.domain.bus.command.CommandBus;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;
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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Log4j2
@Service
public final class FileManager {

    private final CommandBus bus;


    public FileManager(CommandBus bus) {
        this.bus = bus;
    }

    public void dispatchFiles(File path) {
        shutupLog();
        List<File> files = searchMusicFiles(path.getAbsoluteFile());

        for (File file : files) {
            try {
                MP3File f = (MP3File) AudioFileIO.read(file);
                AbstractID3v2Tag tag = f.getID3v2Tag();
                String title = tag.getFirst(FieldKey.TITLE);
                if (title == null || title.isEmpty()) {
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
                        tag.getFirst(FieldKey.COMMENT), tag.getFirst(FieldKey.BPM), tag.getFirst(FieldKey.KEY)));

            } catch (CannotReadException | TagException | IOException | ReadOnlyFileException | InvalidAudioFrameException e) {
                log.error("Error reading file: " + file.getName());
            }
        }
    }

    private List<File> searchMusicFiles(File path) {

        return FileUtils.listFiles(path, new RegexFileFilter("^(.*?)"), DirectoryFileFilter.DIRECTORY)
                .stream()
                .filter(file -> file.getName().endsWith(".mp3"))
                .toList();
    }

    private void shutupLog() {
        Logger[] pin;
        pin = new Logger[]{ Logger.getLogger("org.jaudiotagger") };

        for (Logger l : pin)
            l.setLevel(Level.OFF);
    }
}
