package me.jvegaf.musikbox.services.tagger;

import com.google.inject.Inject;
import me.jvegaf.musikbox.services.web.client.Sanitizer;
import me.jvegaf.musikbox.tracks.Track;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("FieldCanBeLocal")
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
        argsl.add(track.getName());
        return Sanitizer.sanitize(argsl);

    }

    private void trackMatcher(Track track, List<SearchResult> beatSR) {
        List<String> args = retrieveArgs(track);
    }

}
