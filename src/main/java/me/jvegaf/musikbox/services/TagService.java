package me.jvegaf.musikbox.services;

import me.jvegaf.musikbox.tracks.Track;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.datatype.Artwork;
import org.jaudiotagger.tag.id3.AbstractID3v2Tag;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class TagService {

  public static Track createTrackFromFile(File file) {

    MP3File f = null;

    try {
       f = (MP3File) AudioFileIO.read(file);
    } catch (CannotReadException | IOException | TagException | ReadOnlyFileException
            | InvalidAudioFrameException e) {
      e.notify();
    }
    assert f != null;
    return generateTrack(file, f);
  }

  private static Track generateTrack(File file, MP3File f) {
    if (!f.hasID3v2Tag()) return new Track(file.getAbsolutePath());
    AbstractID3v2Tag tag = f.getID3v2Tag();

    return Track.createTrack(
            tag.getFirst(FieldKey.ARTIST),
            tag.getFirst(FieldKey.TITLE),
            tag.getFirst(FieldKey.ALBUM),
            tag.getFirst(FieldKey.GENRE),
            tag.getFirst(FieldKey.YEAR),
            tag.getFirst(FieldKey.BPM),
            f.getMP3AudioHeader().getTrackLengthAsString(),
            file.getAbsolutePath(),
            file.getName(),
            tag.getFirst(FieldKey.KEY),
            tag.getFirst(FieldKey.COMMENT),
            getCoverData(tag)
            );
  }

  private static byte[] getCoverData(AbstractID3v2Tag tag) {
    List<Artwork> artworkList = tag.getArtworkList();
    if (artworkList.isEmpty()) return new byte[0];
    Artwork artwork = tag.getFirstArtwork();
    return artwork.getBinaryData();
  }

  public static void saveTags(Track track) {
    try {
      AudioFile f = AudioFileIO.read(new File(track.getPath()));
      Tag tag = f.getTag();
      if (track.getName() != null) tag.setField(FieldKey.TITLE, track.getName());
      if (track.getArtist() != null) tag.setField(FieldKey.ARTIST, track.getArtist());
      if (track.getAlbum() != null) tag.setField(FieldKey.ALBUM, track.getAlbum());
      if (track.getGenre() != null) tag.setField(FieldKey.GENRE, track.getGenre());
      if (track.getYear() != null) tag.setField(FieldKey.YEAR, track.getYear());
      if (track.getBpm() != null) tag.setField(FieldKey.BPM, track.getBpm().toString());
      if (track.getKey() != null) tag.setField(FieldKey.KEY, track.getKey());
      if (track.getComments() != null) tag.setField(FieldKey.COMMENT, track.getComments());
      if (track.getArtworkData().length > 0) tag.setField(generateArtwork(track.getArtworkData()));

      f.commit();

    } catch (CannotReadException | IOException | InvalidAudioFrameException | ReadOnlyFileException | TagException | CannotWriteException e) {
      e.printStackTrace();
    }
  }

  private static Artwork generateArtwork(byte[] artworkData) {
    Artwork artwork = new Artwork();
    artwork.setBinaryData(artworkData);
    return artwork;
  }
}
