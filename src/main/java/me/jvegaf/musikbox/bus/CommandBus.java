package me.jvegaf.musikbox.bus;

import me.jvegaf.musikbox.tracks.Track;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface CommandBus {
    void playTrack(Track track);

    void showTrackDetail(Track track);

    void addBatch(ArrayList<Track> tracks);

    void fixTags(Track track);

    void fixTags(List<Track> tracks);
}
