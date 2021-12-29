package me.jvegaf.musikbox.services.tagger;

import java.util.List;

public interface Tagger {
    List<SearchResult> search(SearchRequest request);

//    Optional<Track> fetchTrack(String id);
}
