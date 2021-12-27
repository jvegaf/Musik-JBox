package me.jvegaf.musikbox.services.tagger;

import me.jvegaf.musikbox.tracks.Track;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

public class SearchResult {
    private String title;
    private String remixName;
    private List<String> artists;
    private String album;
    private String genre;
    private String year;
    private Integer bpm;
    private String duration;
    private String key;
    private String artworkURL;

    public SearchResult() {
    }


    public SearchResult(String title,
                        String remixName,
                        List<String> artists,
                        String album,
                        String genre,
                        String year,
                        Integer bpm,
                        String duration,
                        String key,
                        String artworkURL) {
        this.title = title;
        this.remixName = remixName;
        this.artists = artists;
        this.album = album;
        this.genre = genre;
        this.year = year;
        this.bpm = bpm;
        this.duration = duration;
        this.key = key;
        this.artworkURL = artworkURL;
    }

    public String Title() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String RemixName() {
        return remixName;
    }

    public void setRemixName(String remixName) {
        this.remixName = remixName;
    }

    public List<String> Artists() {
        return artists;
    }

    public void setArtists(List<String> artists) {
        this.artists = artists;
    }

    public String Album() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String Genre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String Year() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Integer Bpm() {
        return bpm;
    }

    public void setBpm(Integer bpm) {
        this.bpm = bpm;
    }

    public String Duration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = sanitizeTime(duration);
    }

    public String Key() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String ArtworkURL() {
        return artworkURL;
    }

    public void setArtworkURL(String artworkURL) {
        this.artworkURL = artworkURL;
    }

    private static String sanitizeTime(String timeStr) {
        if (timeStr.length() < 5) return "0" + timeStr;
        return timeStr;
    }

    public String DurationDifference(Track otherTrack) {
        var durationSelf = parseHelper(this.Duration());
        var durationOtherTrack = parseHelper(otherTrack.getDuration());
        return Long.toUnsignedString(Duration.between(durationSelf, durationOtherTrack).toSeconds());
    }

    private static LocalTime parseHelper(String str) {
        return LocalTime.parse("00:" + str);
    }
}
