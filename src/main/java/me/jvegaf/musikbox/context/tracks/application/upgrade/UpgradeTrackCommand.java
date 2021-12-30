package me.jvegaf.musikbox.context.tracks.application.upgrade;

import me.jvegaf.musikbox.context.tracks.domain.Track;
import me.jvegaf.musikbox.shared.domain.bus.command.Command;

public class UpgradeTrackCommand implements Command {

    private final Track track;


    public UpgradeTrackCommand(Track track) { this.track = track; }

    public Track track() { return track; }
}
