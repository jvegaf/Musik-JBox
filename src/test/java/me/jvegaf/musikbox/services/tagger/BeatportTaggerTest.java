package me.jvegaf.musikbox.services.tagger;

import me.jvegaf.musikbox.services.web.client.Client;
import me.jvegaf.musikbox.services.web.client.QueryBuilder;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BeatportTaggerTest {

    Client client = new Client();
    BeatportTagger tagger = new BeatportTagger(client);

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

//    @Test
//    void getTagsFromBeatportWithWorstRequestArgument() {
//        String[] args = {"deadmau5_1981_Mike_Vale_vs_Jerome_Robins_Remix_"};
//
//        List<SearchResult> results = tagger.search(args);
//
//        System.out.println("total results: " + results.size());
//        assertTrue(results.size() > 19);
//    }

    @Test
    void tryFetchTrackEmbed() {
//        String trackId = "13732823";
        String trackId = "15587588";

        assertDoesNotThrow(()-> tagger.fetchTrack(trackId));
    }
}
