package me.jvegaf.musikbox.context.tracks.application;

import me.jvegaf.musikbox.context.tracks.domain.Track;
import me.jvegaf.musikbox.shared.domain.bus.query.Response;

import java.util.Optional;

public final class TrackResponse implements Response {

    private final String           id;
    private final String           title;
    private final String           location;
    private final String           duration;
    private String artist;
    private String album;
    private String genre;
    private String year;
    private Integer bpm;
    private String  key;
    private String  comments;


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

    public String id() {
        return id;
    }

    public String title() { return title; }

    public String location() {
        return location;
    }

    public String duration() {
        return duration;
    }

    public Optional<String> artist() {
        return Optional.ofNullable(artist);
    }

    public Optional<String> album() {
        return Optional.ofNullable(album);
    }

    public Optional<String> genre() {
        return Optional.ofNullable(genre);
    }

    public Optional<String> year() {
        return Optional.ofNullable(year);
    }

    public Optional<Integer> bpm() {
        return Optional.ofNullable(bpm);
    }

    public Optional<String> key() {
        return Optional.ofNullable(key);
    }

    public Optional<String> comments() {
        return Optional.ofNullable(comments);
    }

}
