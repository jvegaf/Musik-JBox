package me.jvegaf.musikbox.app.command.player;

import me.jvegaf.musikbox.shared.domain.bus.command.Command;

public class PlaybackCommand implements Command {

    private final String trackId;

    public PlaybackCommand(String trackId) {
        this.trackId    = trackId;

    }

    public String trackId() { return trackId; }
}
