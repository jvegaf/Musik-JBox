package me.jvegaf.musikbox.context.tags.domain;

import java.util.List;

public final class Tag {

    private final String       title;
    private final String       remixName;
    private final List<String> artists;
    private final String       album;
    private final String       genre;
    private final String       year;
    private final Integer      bpm;
    private final Integer      duration;
    private final String       key;
    private final String       artworkURL;

    public Tag(String title,
               String remixName,
               List<String> artists,
               String album,
               String genre,
               String year,
               Integer bpm,
               Integer duration,
               String key,
               String artworkURL) {
        this.title      = title;
        this.remixName  = remixName;
        this.artists    = artists;
        this.album      = album;
        this.genre      = genre;
        this.year       = year;
        this.bpm        = bpm;
        this.duration   = duration;
        this.key        = key;
        this.artworkURL = artworkURL;
    }

    public static Tag createTag(String title,
                                String remixName,
                                List<String> artists,
                                String album,
                                String genre,
                                String year,
                                Integer bpm,
                                Integer duration,
                                String key,
                                String artworkURL) {
        return new Tag(title, remixName, artists, album, genre, year, bpm, duration, key, artworkURL);
    }

    public String title() { return title; }

    public String remixName() { return remixName; }

    public List<String> artists() { return artists; }

    public String album() { return album; }

    public String genre() { return genre; }

    public String year() { return year; }

    public Integer bpm() { return bpm; }

    public Integer duration() { return duration; }

    public String key() { return key; }

    public String artworkURL() { return artworkURL; }
}
