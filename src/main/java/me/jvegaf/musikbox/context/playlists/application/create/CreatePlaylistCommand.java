package me.jvegaf.musikbox.context.playlists.application.create;

import me.jvegaf.musikbox.shared.domain.bus.command.Command;

public class CreatePlaylistCommand implements Command {

    private final String name;

    public CreatePlaylistCommand(String name) {
        this.name = name;
    }

    public String name() { return name; }
}
