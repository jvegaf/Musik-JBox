package com.github.jvegaf.musikbox.context.playlists.application.create;

import com.github.jvegaf.musikbox.shared.domain.bus.command.Command;

public class CreatePlaylistCommand implements Command {

    private final String name;

    public CreatePlaylistCommand(String name) {
        this.name = name;
    }

    public String name() {return name;}
}
