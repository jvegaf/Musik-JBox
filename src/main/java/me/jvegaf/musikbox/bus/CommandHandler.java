package me.jvegaf.musikbox.bus;

import com.google.inject.Inject;
import me.jvegaf.musikbox.services.player.MusicPlayer;
import me.jvegaf.musikbox.services.reporter.Reporter;
import me.jvegaf.musikbox.services.tagger.TaggerService;
import me.jvegaf.musikbox.tracks.Track;
import me.jvegaf.musikbox.tracks.TracksRepository;
import me.jvegaf.musikbox.ui.views.MainController;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public final class CommandHandler implements CommandBus {

    private final TracksRepository tracksRepository;
    private final MusicPlayer musicPlayer;
    private final MainController mainViewController;
    private final TaggerService taggerService;
    @SuppressWarnings("FieldCanBeLocal")
    private final Reporter reporter;

    private final Logger LOG = Logger.getLogger(CommandHandler.class);

    @Inject
    public CommandHandler(TracksRepository tracksRepository,
                          MusicPlayer musicPlayer,
                          MainController mainViewController,
                          TaggerService taggerService,
                          Reporter reporter) {
        this.taggerService = taggerService;
        this.tracksRepository = tracksRepository;
        this.musicPlayer = musicPlayer;
        this.mainViewController = mainViewController;
        this.reporter = reporter;
    }

    @Override
    public void playTrack(Track track) {
        this.musicPlayer.playTrack(track);
    }

    @Override
    public void showTrackDetail(Track track) {
        this.mainViewController.detailActionListener(track);
    }

    @Override
    public void addBatch(ArrayList<Track> tracks) {
        this.tracksRepository.addBatch(tracks);
    }

    @Override
    public void fixTags(List<Track> tracks) {
//        reporter.setTotalItems(tracks.size());
        LOG.info("Fixing tags for " + tracks.size() + " tracks");
        AtomicInteger counter = new AtomicInteger(0);

        for (Track track : tracks) {

            Runnable task = () -> {
                Optional<Track> t = taggerService.fetchTags(track);
                if (t.isPresent()) {
                    tracksRepository.updateTrack(t.get());
                    LOG.info("Fixed tags for " + counter.incrementAndGet() + " tracks");
                    if (counter.get() == tracks.size()) {
                        LOG.info("Finished fixing tags");
                    }
                } else {
                    LOG.info("Faulty track: " + track.getName());
                }
            };

            Thread t = new Thread(task);

            t.start();
        }


    }

}
