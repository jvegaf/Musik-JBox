package me.jvegaf.musikbox.services.tagger;

import com.google.inject.Inject;
import me.jvegaf.musikbox.services.web.client.Sanitizer;
import me.jvegaf.musikbox.tracks.Track;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Collections;


public final class TaggerService {
    private final BeatportTagger beatportTagger;

    @Inject
    public TaggerService(BeatportTagger beatportTagger) {
        this.beatportTagger = beatportTagger;
    }

    public Track fetchTags(Track track) throws RuntimeException {
        var args = retrieveArgs(track);
        var beatSR = beatportTagger.search(args);
        System.out.println("total search results: " + beatSR.size());
        String no_results_found = "No results found";
        if (beatSR.size() < 1) {
            System.out.println(no_results_found);
            throw new RuntimeException(no_results_found);
        }
        var resultTrack = matchResultsWithTrack(track, beatSR);
        if (resultTrack == null) {
            System.out.println(no_results_found);
            throw new RuntimeException(no_results_found);
        }
        return track.importMetadataOf(resultTrack);
    }

    private Track matchResultsWithTrack(Track track, List<SearchResult> beatSR) {
        List<Track> resultTracks = new ArrayList<>();
        for (SearchResult sr : beatSR) {
            resultTracks.add(beatportTagger.fetchTrack(sr.Id()));
        }
        resultTracks.sort(Comparator.comparing(t -> t.DurationDifference(track)));
        return resultTracks.stream().findFirst().orElse(null);
    }

    private boolean matchTrack(SearchResult sr, Track track) {
        return sr.ResultKeys().contains(track.getArtist()) &&
            sr.ResultKeys().contains(track.getName());
    }


    private List<String> retrieveArgs(Track track) {
        ArrayList<String> argsl = new ArrayList<>();
        if (track.getArtist() != null && track.getArtist().length() > 0) {
            argsl.add(track.getArtist());
        }
        argsl.add(track.getName());
        return Sanitizer.sanitize(argsl);

    }

}
