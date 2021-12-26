package me.jvegaf.musikbox.tasks;

import me.jvegaf.musikbox.services.tagger.TaggerService;
import me.jvegaf.musikbox.tracks.Track;
import me.jvegaf.musikbox.tracks.TracksRepository;

public final class FetchTask implements Runnable {

    private TaggerService taggerService;
    private TracksRepository tracksRepository;
    private Track track;

    public FetchTask(TracksRepository tracksRepository, Track track) {
        this.taggerService = new TaggerService();
        this.tracksRepository = tracksRepository;
        this.track = track;
    }

    @Override
    public void run() {
        var t = taggerService.fetchTags(track);
        tracksRepository.updateTrack(t);
    }
}
