package me.jvegaf.musikbox.services;

import me.jvegaf.musikbox.models.Track;
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
import java.time.Year;
import java.util.List;

public class TagService {

  private File file;
  private AbstractID3v2Tag tag;
  private MP3File f;

  public TagService() {
  }

  public Track createTrackFromFile(File file) {
    this.file = file;
    try {
      f = (MP3File) AudioFileIO.read(file);
      System.out.printf("duration: %s%n", f.getMP3AudioHeader().getTrackLengthAsString());
      tag = f.getID3v2Tag();
    } catch (CannotReadException | IOException | TagException | ReadOnlyFileException
            | InvalidAudioFrameException e) {
      e.printStackTrace();
    }
    return generateTrack();
  }

  private Track generateTrack() {
    if (!this.f.hasID3v2Tag()) return new Track(this.file.getAbsolutePath());

    return Track.createTrack(
            this.getArtist(),
            this.getTitle(),
            this.getAlbum(),
            this.getGenre(),
            this.getYear(),
            this.getBPM(),
            this.file.getAbsolutePath(),
            this.file.getName(),
            this.getKey(),
            this.getComments(),
            this.getCoverData()
            );
  }

  private String getTitle() {
    return this.tag.getFirst(FieldKey.TITLE);
  }

  private String getArtist() {
    return this.tag.getFirst(FieldKey.ARTIST);
  }

  private String getAlbum() {
    return this.tag.getFirst(FieldKey.ALBUM);
  }

  private String getGenre() {
    return this.tag.getFirst(FieldKey.GENRE);
  }

  private Year getYear() {
    String _year = this.tag.getFirst(FieldKey.YEAR);
    if (_year != null && _year.length() == 4) {
      return Year.parse(_year);
    }
    return null;
  }

  private Integer getBPM() {
    String _bpm = this.tag.getFirst(FieldKey.BPM);
    if (_bpm != null && !_bpm.equals("")) {
      return Integer.parseInt(_bpm);
    }
    return null;
  }

  private byte[] getCoverData() {
    List<Artwork> artworkList = this.tag.getArtworkList();
    if (artworkList.isEmpty()) return new byte[0];
    Artwork artwork = this.tag.getFirstArtwork();
    return artwork.getBinaryData();
  }

  private String getComments() {
    return this.tag.getFirst(FieldKey.COMMENT);
  }

  private String getKey() {
    return this.tag.getFirst(FieldKey.KEY);
  }

  public void saveTags(Track track) {
    try {
      AudioFile f = AudioFileIO.read(new File(track.getPath()));
      Tag tag = f.getTag();
      if (track.getTitle() != null) tag.setField(FieldKey.TITLE, track.getTitle());
      if (track.getArtist() != null) tag.setField(FieldKey.ARTIST, track.getArtist());
      if (track.getAlbum() != null) tag.setField(FieldKey.ALBUM, track.getAlbum());
      if (track.getGenre() != null) tag.setField(FieldKey.GENRE, track.getGenre());
      if (track.getYear() != null) tag.setField(FieldKey.YEAR, track.getYear().toString());
      if (track.getBpm() != null) tag.setField(FieldKey.BPM, track.getBpm().toString());
      if (track.getKey() != null) tag.setField(FieldKey.KEY, track.getKey());
      if (track.getComments() != null) tag.setField(FieldKey.COMMENT, track.getComments());
      if (track.getArtworkData().length > 0) tag.setField(generateArtwork(track.getArtworkData()));

      f.commit();

    } catch (CannotReadException | IOException | InvalidAudioFrameException | ReadOnlyFileException | TagException | CannotWriteException e) {
      e.printStackTrace();
    }
  }

  private Artwork generateArtwork(byte[] artworkData) {
    Artwork artwork = new Artwork();
    artwork.setBinaryData(artworkData);
    return artwork;
  }
}
