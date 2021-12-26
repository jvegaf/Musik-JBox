package me.jvegaf.musikbox.services.tagger;

import me.jvegaf.musikbox.tracks.Track;

import java.util.List;
import java.util.Optional;

public interface Tagger {
    List<SearchResult> search(List<String> reqArgs);

    Optional<Track> fetchTrack(String id);
}
