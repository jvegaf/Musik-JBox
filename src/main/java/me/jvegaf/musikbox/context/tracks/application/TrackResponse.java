package me.jvegaf.musikbox.context.tracks.application;

import me.jvegaf.musikbox.context.tracks.domain.Track;
import me.jvegaf.musikbox.shared.domain.bus.query.Response;

public final class TrackResponse implements Response {

    private final String  id;
    private final String  title;
    private final String  location;
    private final String  duration;
    private final String  artist;
    private final String  album;
    private final String  genre;
    private final String  year;
    private final Integer bpm;
    private final String  key;
    private final String  comments;


    public TrackResponse(String id,
                         String title,
                         String location,
                         String duration,
                         String artist,
                         String album,
                         String genre,
                         String year,
                         Integer bpm,
                         String key,
                         String comments) {
        this.id       = id;
        this.title    = title;
        this.location = location;
        this.duration = duration;
        this.artist   = artist;
        this.album    = album;
        this.genre    = genre;
        this.year     = year;
        this.bpm      = bpm;
        this.key      = key;
        this.comments = comments;
    }

    public static TrackResponse fromAggregate(Track track) {
        return new TrackResponse(track.id().value(),
                                 track.title().value(),
                                 track.location().value(),
                                 track.duration().stringValue(),
                                 track.artist().value(),
                                 track.album().value(),
                                 track.genre().value(),
                                 track.year().value(),
                                 track.bpm().value(),
                                 track.key().value(),
                                 track.comments().value());
    }

    public String id() { return id; }

    public String title() { return title; }

    public String location() { return location; }

    public String duration() { return duration; }

    public String artist() { return artist; }

    public String album() { return album; }

    public String genre() { return genre; }

    public String year() { return year; }

    public Integer bpm() { return bpm; }

    public String key() { return key; }

    public String comments() { return comments; }
}
