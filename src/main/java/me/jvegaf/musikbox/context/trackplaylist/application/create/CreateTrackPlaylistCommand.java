package me.jvegaf.musikbox.context.trackplaylist.application.create;

import me.jvegaf.musikbox.shared.domain.bus.command.Command;

public class CreateTrackPlaylistCommand implements Command {

    private final String playlistId;
    private final String trackId;

    public CreateTrackPlaylistCommand(String playlistId, String trackId) {
        this.playlistId = playlistId;
        this.trackId    = trackId;
    }

    public String playlistId() {return playlistId;}

    public String trackId() {return trackId;}
}
