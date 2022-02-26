package com.github.jvegaf.musikbox.context.shared.application;

import com.github.jvegaf.musikbox.context.shared.domain.Art;
import com.github.jvegaf.musikbox.shared.domain.bus.command.Command;

public class PersistArtworkCommand implements Command {
    private final Art    art;
    private final String trackLocation;

    public PersistArtworkCommand(Art art, String trackLocation) {
        this.art           = art;
        this.trackLocation = trackLocation;
    }

    public Art artwork() {return art;}

    public String trackLocation() {return trackLocation;}
}
