package me.jvegaf.musikbox.app.command.detail;

import me.jvegaf.musikbox.shared.domain.bus.command.Command;

public class ShowDetailCommand implements Command {

    private final String trackId;

    public ShowDetailCommand(String trackId) {
        this.trackId    = trackId;

    }

    public String trackId() { return trackId; }
}
