package me.jvegaf.musikbox.bus;

import com.google.inject.Inject;
import me.jvegaf.musikbox.services.player.MusicPlayer;
import me.jvegaf.musikbox.tracks.Track;
import me.jvegaf.musikbox.tracks.TracksRepository;
import me.jvegaf.musikbox.ui.views.MainController;

import java.util.ArrayList;

public final class CommandHandler implements CommandBus {

    private final TracksRepository tracksRepository;
    private final MusicPlayer musicPlayer;
    private final MainController mainViewController;

    @Inject
    public CommandHandler(TracksRepository tracksRepository, MusicPlayer musicPlayer, MainController mainViewController) {
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
}
