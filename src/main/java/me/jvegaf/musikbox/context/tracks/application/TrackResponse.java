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

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getLocation() {
        return location;
    }

    public String getDuration() {
        return duration;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public String getGenre() {
        return genre;
    }

    public String getYear() {
        return year;
    }

    public Integer getBpm() {
        return bpm;
    }

    public String getKey() {
        return key;
    }

    public String getComments() {
        return comments;
    }
}
