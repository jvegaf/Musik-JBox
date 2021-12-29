package me.jvegaf.musikbox.services.tagger;

import com.google.inject.Inject;
import me.jvegaf.musikbox.services.picture.PictureFetcher;
import me.jvegaf.musikbox.services.shared.Sanitizer;
import me.jvegaf.musikbox.tracks.Track;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;


public final class TaggerService {
    private final BeatportTagger beatportTagger;

    private final Logger LOG = Logger.getLogger(TaggerService.class);

    @Inject
    public TaggerService(BeatportTagger beatportTagger) {
        this.beatportTagger = beatportTagger;
    }

    public Optional<Track> fetchTags(Track track) {
        var args = retrieveArgs(track);
        SearchRequest request = SearchRequest.createCompleteRequest(track.getName(), track.getArtist());
        var beatSR = beatportTagger.search(request);
        if (beatSR.size() < 1) {
            LOG.info("No results found");
            return Optional.empty();
        }

        return Optional.of(matchResultsWithTrack(track, beatSR));
    }

    private Track matchResultsWithTrack(Track track, List<SearchResult> results) {


        results.sort(Comparator.comparing(t -> t.Duration().compareTo(track.getDuration())));

        var result = results.get(0);
        track.setName(composeName(result.Title(), result.RemixName()));
        track.setArtist(result.Artists().stream().reduce((a, b) -> a + ", " + b ).orElse(result.Artists().get(0)));
        track.setAlbum(result.Album());
        track.setYear(result.Year());
        result.Bpm().ifPresent(track::setBpm);
        track.setGenre(result.Genre());
        track.setDuration(result.Duration());
        track.setKey(result.Key());
        Optional<String> pictureUrl = Optional.ofNullable(result.ArtworkURL());
        pictureUrl.ifPresent(value -> track.setArtworkData(PictureFetcher.getFromURL(result.ArtworkURL())));

        return track;
    }

    private String composeName(String title, String remixName) {
        if (remixName != null && remixName.length() > 0) {
            return title + " (" + remixName + ")";
        }
        return title;
    }


    private List<String> retrieveArgs(Track track) {
        ArrayList<String> args = new ArrayList<>();
        if (track.getArtist() != null && track.getArtist().length() > 0) {
            args.add(track.getArtist());
        }
        args.add(track.getName());
        return Sanitizer.sanitize(args);

    }

}
