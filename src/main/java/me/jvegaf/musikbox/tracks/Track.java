package me.jvegaf.musikbox.tracks;

import java.util.UUID;

public final class Track {
    private final String id;
    private String artist;
    private String name;
    private String album;
    private String genre;
    private String year;
    private Integer bpm;
    private String duration;
    private String path;
    private String fileName;
    private String key;
    private String comments;
    private byte[] artworkData;


    public Track()  {
        this.id = UUID.randomUUID().toString();
    }

    public Track(String path) {
        this.id = UUID.randomUUID().toString();
        this.path = path;
    }

    public Track(String id, String artist, String title, String album, String genre, String yearStr, String bpmStr, String duration, String path, String filename, String key, String comments, byte[] artworkData) {
        this.id = id;
        this.artist = artist;
        this.name = title;
        this.album = album;
        this.genre = genre;
        this.year = yearStr;
        this.setBpm(bpmStr);
        this.duration = duration;
        this.path = path;
        this.fileName = filename;
        this.key = key;
        this.comments = comments;
        this.artworkData = artworkData;
    }

    public static Track createTrack(String artist, String name, String album, String genre, String yearStr, String bpmStr, String duration, String path, String filename, String key, String comments, byte[] artworkData) {

        return new Track(
                UUID.randomUUID().toString(),
                artist,
                name,
                album,
                genre,
                yearStr,
                bpmStr,
                duration,
                path,
                filename,
                key,
                comments,
                artworkData
        );
    }

    public String getId() {
        return id;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String sYear) {
        this.year = sYear;
    }

    public Integer getBpm() {
        return bpm;
    }

    public void setBpm(String sBpm) {
        if (sBpm == null) return;
        if (sBpm.length() < 1) return;
        this.bpm = Integer.valueOf(sBpm);
    }

    public void setBpm(int bpm) {
        this.bpm = bpm;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String Filename() {
        return this.fileName;
    }

    public void setFilename(String filename) {
        this.fileName = filename;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getComments() {
        return this.comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public byte[] getArtworkData() {
        return artworkData;
    }

    public void setArtworkData(byte[] artworkData) {
        this.artworkData = artworkData;
    }

    public String getDuration() {
        return this.duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
