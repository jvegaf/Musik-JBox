package me.jvegaf.musikbox.context.tracks.application;

import me.jvegaf.musikbox.context.tracks.domain.Track;
import me.jvegaf.musikbox.shared.domain.bus.query.Response;

import java.util.Optional;

public final class TrackResponse implements Response {

    private final String  id;
    private final String  title;
    private final String  location;
    private final String  duration;
    private       String  artist;
    private       String  album;
    private       String  genre;
    private       String  year;
    private       Integer bpm;
    private       String  key;
    private       String  comments;


    public TrackResponse(String id, String title, String location, String duration) {
        this.id       = id;
        this.title    = title;
        this.location = location;
        this.duration = duration;
    }

    public static TrackResponse fromAggregate(Track track) {
        var
                r =
                new TrackResponse(track.id().value(),
                                  track.title().value(),
                                  track.location().value(),
                                  track.duration().stringValue());

        track.artist().ifPresent(v -> r.setArtist(v.value()));
        track.album().ifPresent(v -> r.setAlbum(v.value()));
        track.genre().ifPresent(v -> r.setGenre(v.value()));
        track.year().ifPresent(v -> r.setYear(v.value()));
        track.bpm().ifPresent(v -> r.setBpm(v.value()));
        track.key().ifPresent(v -> r.setKey(v.value()));
        track.comments().ifPresent(v -> r.setComments(v.value()));

        return r;
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

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setBpm(Integer bpm) {
        this.bpm = bpm;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
