package me.jvegaf.musikbox.bus;

import com.google.inject.Inject;
import me.jvegaf.musikbox.services.player.MusicPlayer;
import me.jvegaf.musikbox.services.tagger.TaggerService;
import me.jvegaf.musikbox.tasks.FetchTask;
import me.jvegaf.musikbox.tracks.Track;
import me.jvegaf.musikbox.tracks.TracksRepository;
import me.jvegaf.musikbox.ui.views.MainController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@SuppressWarnings("StatementWithEmptyBody")
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

    private static Callable<Track> getTrackAsync(TaggerService service, Track track) {
        return Executors.callable(() -> service.fetchTags(track), track);
    }

    private static void Log(Object mensaje) {
        System.out.printf("%s%n", mensaje.toString());
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
    public void fixTags(Track track) {
        System.out.println("Fixing tags for " + track.toString());

        Executors.newSingleThreadExecutor().execute(() -> {
            System.out.println("Search Starts");
            var t = this.taggerService.fetchTags(track);
            System.out.println("Search Ends");
            this.tracksRepository.updateTrack(t);
            System.out.println("track updated");
        });

    }

    @Override
    public void fixTags(List<Track> tracks) {
        System.out.println(tracks.size() + " tracks to fix\n\n");

        List<FetchTask> tasks = new ArrayList<>();
        for (int i = 0; i < tracks.size(); i++) {
            tasks.add(new FetchTask(tracksRepository, tracks.get(i), i));
        }

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
        for (FetchTask task : tasks) {
            executor.execute(task);
        }

        executor.shutdown();

        while (!executor.isTerminated()) {
        }
        System.out.println("Finished all threads");


        System.out.println("All tracks updated");
    }

}
