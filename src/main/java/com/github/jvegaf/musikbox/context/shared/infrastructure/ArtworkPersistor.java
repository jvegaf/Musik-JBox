package com.github.jvegaf.musikbox.context.shared.infrastructure;

import com.github.jvegaf.musikbox.context.shared.domain.Artwork;
import com.github.jvegaf.musikbox.shared.domain.Service;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.id3.AbstractID3v2Tag;

import java.io.File;
import java.io.IOException;

@Service
public class ArtworkPersistor {

    public void persist(String location, Artwork artwork) throws
                                                          CannotReadException,
                                                          TagException,
                                                          InvalidAudioFrameException,
                                                          ReadOnlyFileException,
                                                          IOException,
                                                          CannotWriteException {
        File             file = new File(location);
        MP3File          f    = (MP3File) AudioFileIO.read(file);
        AbstractID3v2Tag tag  = f.getID3v2Tag();

        org.jaudiotagger.tag.datatype.Artwork art = new org.jaudiotagger.tag.datatype.Artwork();
        art.setBinaryData(artwork.data());
        art.setDescription(artwork.description());
        art.setLinked(artwork.isLinked());
        art.setMimeType(artwork.mimeType());
        art.setPictureType(artwork.pictureType());
        art.setImageUrl(artwork.imageUrl());

        tag.setField(art);
        f.commit();

    }
}
