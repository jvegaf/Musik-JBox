package me.jvegaf.musikbox.context.tracks.domain;

import me.jvegaf.musikbox.shared.domain.AggregateRoot;
import me.jvegaf.musikbox.shared.domain.track.TrackCreatedDomainEvent;
import me.jvegaf.musikbox.shared.domain.track.TrackUpdatedDomainEvent;

import java.util.Optional;

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

    public Track(TrackId id, TrackTitle title, TrackLocation location, TrackDuration duration) {
        this.id       = id;
        this.title    = title;
        this.location = location;
        this.duration = duration;
    }

    public Track(TrackId id,
                 TrackTitle title,
                 TrackLocation location,
                 TrackDuration duration,
                 TrackArtist artist,
                 TrackAlbum album,
                 TrackGenre genre,
                 TrackYear year,
                 TrackBpm bpm,
                 TrackInitKey key,
                 TrackComments comments) {
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

    private Track() {
        id       = null;
        title    = null;
        location = null;
        duration = null;
    }

    public static Track create(
            TrackId id,
            TrackTitle title,
            TrackLocation location,
            TrackDuration duration,
            TrackArtist artist,
            TrackAlbum album,
            TrackGenre genre,
            TrackYear year,
            TrackBpm bpm,
            TrackInitKey key,
            TrackComments comments
            ) {
        Track track = new Track(
                id,
                title,
                location,
                duration,
                artist,
                album,
                genre,
                year,
                bpm,
                key,
                comments
                );

        track.record(new TrackCreatedDomainEvent(id.value(), title.value(), location.value()));
        return track;
    }

    public TrackId id() { return id; }

    public TrackTitle title() { return title; }

    public TrackLocation location() { return location; }

    public TrackDuration duration() { return duration; }

    public Optional<TrackArtist> artist() { return Optional.ofNullable(artist); }

    public Optional<TrackAlbum> album() { return Optional.ofNullable(album); }

    public Optional<TrackGenre> genre() { return Optional.ofNullable(genre); }

    public Optional<TrackYear> year() { return Optional.ofNullable(year); }

    public Optional<TrackBpm> bpm() { return Optional.ofNullable(bpm); }

    public Optional<TrackInitKey> key() { return Optional.ofNullable(key); }

    public Optional<TrackComments> comments() { return Optional.ofNullable(comments); }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass()) return false;
        Track t = (Track) obj;
        return id.equals(t.id()) && title.equals(t.title()) && location.equals(t.location()) && duration.equals(t.duration());
    }

    public Track improveMetadata(TrackTitle title,
                                 TrackArtist artist,
                                 TrackAlbum album,
                                 TrackGenre genre,
                                 TrackYear year,
                                 TrackBpm bpm,
                                 TrackInitKey key,
                                 TrackComments comments
    ) {
        Track
                t =
                new Track(this.id, title, this.location, this.duration, artist, album, genre, year, bpm, key, comments);
        t.record(new TrackUpdatedDomainEvent(id.value(), title.value(), location.value()));
        return t;
    }
}
