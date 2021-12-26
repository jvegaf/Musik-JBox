package me.jvegaf.musikbox.services.tagger;

import me.jvegaf.musikbox.services.web.client.QueryBuilder;
import me.jvegaf.musikbox.tracks.Track;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BeatportTaggerTest {

    final BeatportTagger tagger = new BeatportTagger();

    @Test
    void getTagsFromBeatport() {
        List<String> args = new ArrayList<>();
        args.add("joeski");
        args.add("un congo");

        QueryBuilder.build(args, "+");
        List<SearchResult> results = tagger.search(args);

        System.out.println("total results: " + results.size());
        assertTrue(results.size() > 19);
    }

    @Test
    void tryFetchTrackEmbed() {
//        String trackId = "13732823";
        String trackId = "15587588";

        Optional<Track> track = tagger.fetchTrack(trackId);
        assertDoesNotThrow(() -> tagger.fetchTrack(trackId));
        assertTrue(track.isPresent());
    }
}
