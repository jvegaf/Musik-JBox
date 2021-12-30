package me.jvegaf.musikbox.context.tracks.domain;

import me.jvegaf.musikbox.shared.domain.AggregateRoot;
import me.jvegaf.musikbox.shared.domain.track.TrackCreatedDomainEvent;

public final class Track extends AggregateRoot {

    private final TrackId       id;
    private final TrackTitle    title;
    private final TrackLocation location;
    private final TrackDuration duration;
    private       TrackArtist   artist;
    private       TrackAlbum    album;
    private       TrackGenre    genre;
    private       TrackYear     year;
    private       TrackBpm      bpm;
    private       TrackInitKey  key;
    private       TrackComments comments;

    public Track(TrackTitle title, TrackLocation location, TrackDuration duration) {
        this.id       = TrackId.create();
        this.title    = title;
        this.location = location;
        this.duration = duration;
    }

    public Track(TrackId id, TrackTitle title, TrackLocation location, TrackDuration duration) {
        this.id       = id;
        this.title    = title;
        this.location = location;
        this.duration = duration;
    }

    private Track() {
        id       = null;
        title    = null;
        location = null;
        duration = null;
    }

    public static Track create(TrackId id, TrackTitle title, TrackLocation location, TrackDuration duration) {
        Track track = new Track(id, title, location, duration);

        track.record(new TrackCreatedDomainEvent(id.value(), title.value(), location.value()));
        return track;
    }

    public TrackId id() { return id; }

    public TrackTitle title() { return title; }

    public TrackLocation location() { return location; }

    public TrackDuration duration() { return duration; }

    public TrackArtist artist() { return artist; }

    public TrackAlbum album() { return album; }

    public TrackGenre genre() { return genre; }

    public TrackYear year() { return year; }

    public TrackBpm bpm() { return bpm; }

    public TrackInitKey key() { return key; }

    public TrackComments comments() { return comments; }

    @Override public int hashCode() {
        return super.hashCode();
    }

    @Override public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass()) return false;
        Track t = (Track) obj;
        return id.equals(t.id) && title.equals(t.title) && location.equals(t.location) && duration.equals(t.duration);
    }
}
