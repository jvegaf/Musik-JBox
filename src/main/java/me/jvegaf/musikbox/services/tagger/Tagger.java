package me.jvegaf.musikbox.services.tagger;

import me.jvegaf.musikbox.tracks.Track;

import java.util.List;

public interface Tagger {
    List<SearchResult> search(List<String> reqArgs);

    Track fetchTrack(String id);
}
