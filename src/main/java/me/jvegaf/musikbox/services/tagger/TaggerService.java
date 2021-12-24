package me.jvegaf.musikbox.services.tagger;

import com.google.inject.Inject;
import me.jvegaf.musikbox.services.web.client.Sanitizer;
import me.jvegaf.musikbox.tracks.Track;

import java.util.ArrayList;
import java.util.List;

public final class TaggerService {
    private final BeatportTagger beatportTagger;
    private final SpotifyTagger spotifyTagger;

    @Inject
    public TaggerService(BeatportTagger beatportTagger, SpotifyTagger spotifyTagger) {
        this.beatportTagger = beatportTagger;
        this.spotifyTagger = spotifyTagger;
    }

    public void fetchTags(Track track) {
        var args = retrieveArgs(track);
        var beatSR = beatportTagger.search(args);
//        Track resultTrack = trackMatcher(track, beatSR);
    }


    private List<String> retrieveArgs(Track track) {
        ArrayList<String> argsl = new ArrayList<>();
        if (track.getArtist() != null && track.getArtist().length() > 0) {
            argsl.add(track.getArtist());
        }
        argsl.add(track.getTitle());
        return Sanitizer.sanitize(argsl);

    }

    private void trackMatcher(Track track, List<SearchResult> beatSR) {
        List<String> args = retrieveArgs(track);
//        beatSR.stream().forEach(searchResult -> {
//            if (args.size() == 2) {
//            if (searchResult.Artists().get(0).equals(args.get(0)) && searchResult.Title().equals(args.get(1))) {
//                beatportTagger.fetchTrack(searchResult.Id());
//            }
//                     }
//        });
    }

}
