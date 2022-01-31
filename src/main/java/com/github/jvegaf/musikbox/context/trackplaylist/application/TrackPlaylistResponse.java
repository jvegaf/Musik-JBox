package com.github.jvegaf.musikbox.context.trackplaylist.application;

import com.github.jvegaf.musikbox.context.tracks.application.TrackLibraryResponse;
import com.github.jvegaf.musikbox.context.tracks.domain.Track;
import com.github.jvegaf.musikbox.shared.domain.TrackResponse;

public final class TrackPlaylistResponse extends TrackLibraryResponse {

    private final Integer position;

    public TrackPlaylistResponse(String id, String title, String location, String duration, Integer position) {
        super(id, title, location, duration);
        this.position = position;
    }

    public static TrackResponse fromAggregate(Track track, Integer position) {
        var r = new TrackPlaylistResponse(track.id()
                                               .value(),
                                          track.title()
                                               .value(),
                                          track.location()
                                               .value(),
                                          track.duration()
                                               .stringValue(),
                                          position);

        track.artist()
             .ifPresent(v -> r.setArtist(v.value()));
        track.album()
             .ifPresent(v -> r.setAlbum(v.value()));
        track.genre()
             .ifPresent(v -> r.setGenre(v.value()));
        track.year()
             .ifPresent(v -> r.setYear(v.value()));
        track.bpm()
             .ifPresent(v -> r.setBpm(v.value()));
        track.key()
             .ifPresent(v -> r.setKey(v.value()));
        track.comments()
             .ifPresent(v -> r.setComments(v.value()));

        return r;
    }

    public Integer position() {return position;}

    public String positionStr() {return String.valueOf(position + 1);}
}
