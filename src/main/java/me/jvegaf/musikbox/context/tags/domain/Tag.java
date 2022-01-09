package me.jvegaf.musikbox.context.tags.domain;

import me.jvegaf.musikbox.shared.domain.bus.query.Response;

import java.util.List;

public final class Tag implements Response {

    private String       title;
    private String       remixName;
    private List<String> artists;
    private String       album;
    private String       genre;
    private String       year;
    private Integer      bpm;
    private Integer      duration;
    private String       key;
    private String       artworkURL;

    public Tag() {}

    public String title() {
        if (remixName==null) return title;
        return title + " (" + remixName + ")";
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRemixName(String remixName) {
        this.remixName = remixName;
    }

    public String artist() {
        return artists.stream().reduce((a, b) -> a + ", " + b).orElse("");
    }

    public void setArtists(List<String> artists) {
        this.artists = artists;
    }

    public String album() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String genre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String year() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Integer bpm() {
        return bpm;
    }

    public void setBpm(Integer bpm) {
        this.bpm = bpm;
    }

    public Integer duration() {
        return duration;
    }

    public void setDuration(String durationStr) {
        this.duration = fromStringValue(durationStr);
    }

    private int fromStringValue(String strValue) {
        String[] timeParts = strValue.split(":");
        int      minutes   = Integer.parseInt(timeParts[0]);
        int      seconds   = Integer.parseInt(timeParts[1]);
        return minutes * 60 + seconds;
    }

    public String key() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @SuppressWarnings("unused")
    public String artworkURL() {
        return artworkURL;
    }

    public void setArtworkURL(String artworkURL) {
        this.artworkURL = artworkURL;
    }
}
