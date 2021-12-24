package me.jvegaf.musikbox.bus;

import me.jvegaf.musikbox.tracks.Track;

import java.util.ArrayList;

public interface CommandBus {
    void playTrack(Track track);

    void showTrackDetail(Track track);

    void addBatch(ArrayList<Track> tracks);
}
