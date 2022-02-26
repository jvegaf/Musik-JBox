package com.github.jvegaf.musikbox.context.shared.infrastructure;

import com.github.jvegaf.musikbox.context.shared.domain.Art;
import com.github.jvegaf.musikbox.shared.domain.Service;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.id3.AbstractID3v2Tag;
import org.jaudiotagger.tag.images.Artwork;
import org.jaudiotagger.tag.images.StandardArtwork;

import java.io.File;
import java.io.IOException;

@Service
public class ArtworkPersistor {

    public void persist(String location, Art art) throws
                                                          CannotReadException,
                                                          TagException,
                                                          InvalidAudioFrameException,
                                                          ReadOnlyFileException,
                                                          IOException,
                                                          CannotWriteException {
        File             file = new File(location);
        MP3File          f    = (MP3File) AudioFileIO.read(file);
        AbstractID3v2Tag tag  = f.getID3v2Tag();

        Artwork artwork = new StandardArtwork();
        artwork.setBinaryData(art.data());
        artwork.setDescription(art.description());
        artwork.setLinked(art.isLinked());
        artwork.setMimeType(art.mimeType());
        artwork.setPictureType(art.pictureType());
        artwork.setImageUrl(art.imageUrl());

        tag.setField(artwork);
        f.commit();

    }
}
