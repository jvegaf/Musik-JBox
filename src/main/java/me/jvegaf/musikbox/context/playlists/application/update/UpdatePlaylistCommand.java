package me.jvegaf.musikbox.context.playlists.application.update;

import me.jvegaf.musikbox.shared.domain.bus.command.Command;

public final class UpdatePlaylistCommand implements Command {

    private final String id;
    private final String name;

    public UpdatePlaylistCommand(String id, String name) {
        this.id   = id;
        this.name = name;
    }

    public String id() {return id;}

    public String name() {return name;}
}
