package com.github.jvegaf.musikbox.context.shared.infrastructure;

import com.github.jvegaf.musikbox.context.shared.domain.Artwork;
import com.github.jvegaf.musikbox.shared.domain.Service;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.id3.AbstractID3v2Tag;

import java.io.File;
import java.io.IOException;

@Service
public class ArtworkRetriever {

    public Artwork retrieve(String location) throws
                                             CannotReadException,
                                             TagException,
                                             InvalidAudioFrameException,
                                             ReadOnlyFileException,
                                             IOException {
        File             file = new File(location);
        MP3File          f    = (MP3File) AudioFileIO.read(file);
        AbstractID3v2Tag tag  = f.getID3v2Tag();

        if (tag.getFirstArtwork()==null) return new Artwork();

        return new Artwork(tag.getFirstArtwork()
                              .getBinaryData(),
                           tag.getFirstArtwork()
                              .getDescription(),
                           tag.getFirstArtwork()
                              .getImageUrl(),
                           tag.getFirstArtwork()
                              .isLinked(),
                           tag.getFirstArtwork()
                              .getMimeType(),
                           tag.getFirstArtwork()
                              .getPictureType());
    }
}
