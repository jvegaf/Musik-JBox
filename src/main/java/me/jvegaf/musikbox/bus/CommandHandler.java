package me.jvegaf.musikbox.bus;

import com.google.inject.Inject;
import me.jvegaf.musikbox.services.player.MusicPlayer;
import me.jvegaf.musikbox.services.tagger.TaggerService;
import me.jvegaf.musikbox.tracks.Track;
import me.jvegaf.musikbox.tracks.TracksRepository;
import me.jvegaf.musikbox.ui.views.MainController;

import java.util.ArrayList;
import java.util.List;

public final class CommandHandler implements CommandBus {

    private final TracksRepository tracksRepository;
    private final MusicPlayer musicPlayer;
    private final MainController mainViewController;
    private final TaggerService taggerService;

    @Inject
    public CommandHandler(TracksRepository tracksRepository,
                          MusicPlayer musicPlayer,
                          MainController mainViewController,
                          TaggerService taggerService
    ) {
        this.taggerService = taggerService;
        this.tracksRepository = tracksRepository;
        this.musicPlayer = musicPlayer;
        this.mainViewController = mainViewController;
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

    private void fixTags(Track track) {
        System.out.println("Fixing tags for " + track.toString());


        Runnable task = () -> {
            System.out.println("Search Starts");
            Track t = this.taggerService.fetchTags(track);
            System.out.println("Search Ends");
            this.tracksRepository.updateTrack(t);
            System.out.println("track updated");
        };

        Thread t = new Thread(task);

        t.start();
    }

    @Override
    public void fixTags(List<Track> tracks) {
        System.out.println(tracks.size() + " tracks to fix\n\n");

        for (Track track : tracks) {
            fixTags(track);
        }
    }

}
