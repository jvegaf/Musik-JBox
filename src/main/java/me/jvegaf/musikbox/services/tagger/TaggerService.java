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

    public Track fetchTags(Track track) {
        String[] args = retrieveArgs(track);
        var beatSR = beatportTagger.search(args);
        Track resultTrack = trackMatcher(track, beatSR);
    }

    private Track trackMatcher(Track track, List<SearchResult> beatSR) {
        var args = retrieveArgs(track);

    }

    private String[] retrieveArgs(Track track) {
        ArrayList<String> argsl = new ArrayList<>();
        if (track.getArtist() != null && track.getArtist().length() > 0) {
            argsl.add(track.getArtist());
        }
        argsl.add(track.getTitle());
        var sanitized = Sanitizer.sanitize((String[]) argsl.toArray());
        return (String[]) sanitized.toArray();
    }


}
