package me.jvegaf.musikbox.bus;

import me.jvegaf.musikbox.context.tracks.domain.Track;

import java.util.ArrayList;
import java.util.List;

public interface CommandBus {
    void playTrack(Track track);

    void showTrackDetail(Track track);

    void addBatch(ArrayList<Track> tracks);

    void fixTags(List<Track> tracks);
}
