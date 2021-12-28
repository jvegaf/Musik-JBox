package me.jvegaf.musikbox.services.tagger;

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

    public TaggerService() {
        this.beatportTagger = new BeatportTagger();
    }

    public Track fetchTags(Track track) {
        var args = retrieveArgs(track);
        var beatSR = beatportTagger.search(args);
//        LOG.debug("total search results: " + beatSR.size());
        String no_results_found = "No results found";
        if (beatSR.size() < 1) {
            LOG.info(no_results_found);
            return track;
        }

        return matchResultsWithTrack(track, beatSR);
    }

    private Track matchResultsWithTrack(Track track, List<SearchResult> results) {


        results.sort(Comparator.comparing(t -> t.Duration().compareTo(track.getDuration())));

        var result = results.get(0);
        track.setName(composeName(result.Title(), result.RemixName()));
        track.setArtist(result.Artists().stream().reduce((a, b) -> a + ", " + b ).orElse(result.Artists().get(0)));
        track.setAlbum(result.Album());
        track.setYear(result.Year());
        track.setBpm(result.Bpm());
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
