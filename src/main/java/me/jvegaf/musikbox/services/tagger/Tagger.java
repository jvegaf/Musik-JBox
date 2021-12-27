package me.jvegaf.musikbox.services.tagger;

import java.util.List;

public interface Tagger {
    List<SearchResult> search(List<String> reqArgs);

//    Optional<Track> fetchTrack(String id);
}
