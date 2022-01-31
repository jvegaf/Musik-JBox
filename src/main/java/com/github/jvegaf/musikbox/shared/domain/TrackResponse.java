package com.github.jvegaf.musikbox.shared.domain;

import com.github.jvegaf.musikbox.shared.domain.bus.query.Response;

import java.util.Optional;

public interface TrackResponse extends Response {

    String id();

    String title();

    String location();

    String duration();

    Integer durationInt();

    Optional<String> artist();

    Optional<String> album();

    Optional<String> genre();

    Optional<String> year();

    Optional<Integer> bpm();

    Optional<String> key();

    Optional<String> comments();

    void setArtist(String artist);

    void setAlbum(String album);

    void setGenre(String genre);

    void setYear(String year);

    void setBpm(Integer bpm);

    void setKey(String key);

    void setComments(String comments);
}
