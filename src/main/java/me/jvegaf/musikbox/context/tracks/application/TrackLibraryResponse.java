package me.jvegaf.musikbox.context.tracks.application;

import me.jvegaf.musikbox.context.tracks.domain.Track;
import me.jvegaf.musikbox.shared.domain.TrackResponse;

import java.util.Optional;

public class TrackLibraryResponse implements TrackResponse {

    protected final String  id;
    protected final String  title;
    protected final String  location;
    protected final String  duration;
    protected       String  artist;
    protected       String  album;
    protected       String  genre;
    protected       String  year;
    protected       Integer bpm;
    protected       String  key;
    protected       String  comments;


    public TrackLibraryResponse(String id, String title, String location, String duration) {
        this.id       = id;
        this.title    = title;
        this.location = location;
        this.duration = duration;
    }

    public static TrackResponse fromAggregate(Track track) {
        var r = new TrackLibraryResponse(track.id().value(),
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

    @Override
    public String id() {return id;}

    @Override
    public String title() {return title;}

    @Override
    public String location() {
        return location;
    }

    @Override
    public String duration() {
        return duration;
    }

    @Override
    public Integer durationInt() {
        return fromStringValue(duration);
    }

    @Override
    public Optional<String> artist() {
        return Optional.ofNullable(artist);
    }

    @Override
    public Optional<String> album() {
        return Optional.ofNullable(album);
    }

    @Override
    public Optional<String> genre() {
        return Optional.ofNullable(genre);
    }

    @Override
    public Optional<String> year() {
        return Optional.ofNullable(year);
    }

    @Override
    public Optional<Integer> bpm() {
        return Optional.ofNullable(bpm);
    }

    @Override
    public Optional<String> key() {
        return Optional.ofNullable(key);
    }

    @Override
    public Optional<String> comments() {
        return Optional.ofNullable(comments);
    }

    @Override
    public void setArtist(String artist) {
        this.artist = artist;
    }

    @Override
    public void setAlbum(String album) {
        this.album = album;
    }

    @Override
    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public void setBpm(Integer bpm) {
        this.bpm = bpm;
    }

    @Override
    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public void setComments(String comments) {
        this.comments = comments;
    }

    protected int fromStringValue(String strValue) {
        String[] timeParts = strValue.split(":");
        int      minutes   = Integer.parseInt(timeParts[0]);
        int      seconds   = Integer.parseInt(timeParts[1]);
        return minutes * 60 + seconds;
    }
}
