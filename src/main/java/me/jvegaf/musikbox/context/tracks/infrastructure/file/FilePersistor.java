package me.jvegaf.musikbox.context.tracks.infrastructure.file;


import lombok.extern.log4j.Log4j2;
import me.jvegaf.musikbox.context.tracks.domain.Track;
import me.jvegaf.musikbox.shared.domain.Service;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.datatype.Artwork;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Log4j2
@Service
public final class FilePersistor {

    public FilePersistor() { }

    public void persist(Track t) {
        shutupLog();
        try {
            AudioFile f   = AudioFileIO.read(new File(t.location().value()));
            Tag       tag = f.getTag();
            tag.setField(FieldKey.TITLE, t.title().value());
            if (t.artist().isPresent()) tag.setField(FieldKey.ARTIST, t.artist().get().value());
            if (t.album().isPresent()) tag.setField(FieldKey.ALBUM, t.album().get().value());
            if (t.genre().isPresent()) tag.setField(FieldKey.GENRE, t.genre().get().value());
            if (t.year().isPresent()) tag.setField(FieldKey.YEAR, t.year().get().value());
            if (t.bpm().isPresent()) tag.setField(FieldKey.BPM, t.bpm().get().value().toString());
            if (t.key().isPresent()) tag.setField(FieldKey.KEY, t.key().get().value());
            if (t.comments().isPresent()) tag.setField(FieldKey.COMMENT, t.comments().get().value());

            f.commit();

        } catch (CannotReadException | IOException | InvalidAudioFrameException | ReadOnlyFileException | TagException | CannotWriteException e) {
            e.printStackTrace();
        }
    }

    private void shutupLog() {
        Logger[] pin;
        pin = new Logger[]{Logger.getLogger("org.jaudiotagger")};

        for (Logger l : pin)
            l.setLevel(Level.OFF);
    }
}
