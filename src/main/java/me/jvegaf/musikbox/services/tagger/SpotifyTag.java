package me.jvegaf.musikbox.services.tagger;

import se.michaelthelin.spotify.model_objects.specification.Image;

import java.time.Year;
import java.util.Arrays;

public class SpotifyTag {
    private String artist;
    private String title;
    private String album;
    private String genre;
    private Year year;
    private Integer bpm;
    private String key;
    private Image[] images;

    public SpotifyTag() {
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    public Integer getBpm() {
        return bpm;
    }

    public void setBpm(Integer bpm) {
        this.bpm = bpm;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Image[] getImages() {
        return images;
    }

    public void setImages(Image[] images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "SpotifyTag{" +
                "artist='" + artist + '\'' +
                ", title='" + title + '\'' +
                ", album='" + album + '\'' +
                ", genre='" + genre + '\'' +
                ", year=" + year +
                ", bpm=" + bpm +
                ", key='" + key + '\'' +
                ", images=" + Arrays.toString(images) +
                '}';
    }
}
