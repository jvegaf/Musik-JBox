package me.jvegaf.musikbox.bus;

import com.google.inject.Inject;
import me.jvegaf.musikbox.app.controller.MainController;
import me.jvegaf.musikbox.app.player.MusicPlayer;
import me.jvegaf.musikbox.context.tagger.TaggerService;
import me.jvegaf.musikbox.context.tracks.OldTracksRepository;
import me.jvegaf.musikbox.context.tracks.domain.Track;
import me.jvegaf.musikbox.services.reporter.Reporter;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public final class CommandHandler implements CommandBus {

    private final OldTracksRepository oldTracksRepository;
    private final MusicPlayer         musicPlayer;
    private final MainController      mainViewController;
    private final TaggerService       taggerService;
    @SuppressWarnings("FieldCanBeLocal")
    private final Reporter            reporter;

    private final Logger LOG = Logger.getLogger(CommandHandler.class);

    @Inject
    public CommandHandler(OldTracksRepository oldTracksRepository,
                          MusicPlayer musicPlayer,
                          MainController mainViewController,
                          TaggerService taggerService,
                          Reporter reporter) {
        this.taggerService       = taggerService;
        this.oldTracksRepository = oldTracksRepository;
        this.musicPlayer         = musicPlayer;
        this.mainViewController  = mainViewController;
        this.reporter            = reporter;
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
        this.oldTracksRepository.addBatch(tracks);
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
                    oldTracksRepository.updateTrack(t.get());
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
