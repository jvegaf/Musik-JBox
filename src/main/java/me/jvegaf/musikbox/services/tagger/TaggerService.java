package me.jvegaf.musikbox.services.tagger;

import me.jvegaf.musikbox.services.web.client.Sanitizer;
import me.jvegaf.musikbox.tracks.Track;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public final class TaggerService {
    private final BeatportTagger beatportTagger;

    public TaggerService() {
        this.beatportTagger = new BeatportTagger();
    }

    public Track fetchTags(Track track) {
        var args = retrieveArgs(track);
        var beatSR = beatportTagger.search(args);
        System.out.println("total search results: " + beatSR.size());
        String no_results_found = "No results found";
        if (beatSR.size() < 1) {
            System.out.println(no_results_found);
            return track;
        }
        var t = matchResultsWithTrack(track, beatSR);
        return track.importMetadataOf(t);
    }

    private Track matchResultsWithTrack(Track track, List<SearchResult> beatSR) {
        List<Track> resultTracks = new ArrayList<>();
        for (SearchResult sr : beatSR) {
            var o = beatportTagger.fetchTrack(sr.Id());
            o.ifPresent(resultTracks::add);
        }
        resultTracks.sort(Comparator.comparing(t -> t.DurationDifference(track)));
        return resultTracks.get(0);
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
