package com.github.jvegaf.musikbox.context.tracks.domain;

import com.github.jvegaf.musikbox.shared.domain.AggregateRoot;
import com.github.jvegaf.musikbox.shared.domain.track.TrackCreatedDomainEvent;
import com.github.jvegaf.musikbox.shared.domain.track.TrackUpdatedDomainEvent;

import java.util.Objects;
import java.util.Optional;

public final class Track extends AggregateRoot {

    private final TrackId       id;
    private final TrackLocation location;
    private final TrackDuration duration;
    private       TrackTitle    title;
    private       TrackArtist   artist;
    private       TrackAlbum    album;
    private       TrackGenre    genre;
    private       TrackYear     year;
    private       TrackBpm      bpm;
    private       TrackInitKey  key;
    private       TrackComments comments;

    public Track(TrackId id, TrackLocation location, TrackDuration duration) {
        this.id       = id;
        this.location = location;
        this.duration = duration;
    }

    public Track(
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
        Track track = new Track(id, title, location, duration, artist, album, genre, year, bpm, key, comments);

        track.record(new TrackCreatedDomainEvent(id.value(), title.value(), location.value()));
        return track;
    }

    public TrackId id() {return id;}

    public TrackTitle title() {return title;}

    public TrackLocation location() {return location;}

    public TrackDuration duration() {return duration;}

    public Optional<TrackArtist> artist() {return Optional.ofNullable(artist);}

    public Optional<TrackAlbum> album() {return Optional.ofNullable(album);}

    public Optional<TrackGenre> genre() {return Optional.ofNullable(genre);}

    public Optional<TrackYear> year() {return Optional.ofNullable(year);}

    public Optional<TrackBpm> bpm() {return Optional.ofNullable(bpm);}

    public Optional<TrackInitKey> key() {return Optional.ofNullable(key);}

    public Optional<TrackComments> comments() {return Optional.ofNullable(comments);}

    @Override
    public int hashCode() {
        return Objects.hash(id, location, duration, title, artist, album, genre, year, bpm, key, comments);
    }

    @Override
    public boolean equals(Object o) {
        if (this==o) return true;
        if (o==null || getClass()!=o.getClass()) return false;
        Track track = (Track) o;
        assert id!=null;
        if (!id.equals(track.id)) return false;
        assert location!=null;
        if (!location.equals(track.location)) return false;
        assert duration!=null;
        return duration.equals(track.duration) &&
               Objects.equals(title, track.title) &&
               Objects.equals(artist,
                              track.artist) &&
               Objects.equals(album, track.album) &&
               Objects.equals(genre, track.genre) &&
               Objects.equals(year, track.year) &&
               Objects.equals(bpm, track.bpm) &&
               Objects.equals(key, track.key) &&
               Objects.equals(comments, track.comments);
    }

    @SuppressWarnings("ConstantConditions")
    public Track updateFromPrimitives(
            String title,
            String artist,
            String album,
            String genre,
            String year,
            Integer bpm,
            String key,
            String comments
    ) {

        if (title!=null) this.title = new TrackTitle(title);
        if (artist!=null) this.artist = new TrackArtist(artist);
        if (album!=null) this.album = new TrackAlbum(album);
        if (genre!=null) this.genre = new TrackGenre(genre);
        if (year!=null) this.year = new TrackYear(year);
        if (bpm!=null) this.bpm = new TrackBpm(bpm);
        if (key!=null) this.key = new TrackInitKey(key);
        if (comments!=null) this.comments = new TrackComments(comments);

        this.record(new TrackUpdatedDomainEvent(this.id.value(), this.title.value(), this.location.value()));
        return this;
    }
}
