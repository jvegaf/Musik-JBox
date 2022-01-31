package com.github.jvegaf.musikbox.context.shared.application;

import com.github.jvegaf.musikbox.context.shared.domain.Artwork;
import com.github.jvegaf.musikbox.shared.domain.bus.command.Command;

public class PersistArtworkCommand implements Command {
    private final Artwork artwork;
    private final String  trackLocation;

    public PersistArtworkCommand(Artwork artwork, String trackLocation) {
        this.artwork       = artwork;
        this.trackLocation = trackLocation;
    }

    public Artwork artwork() {return artwork;}

    public String trackLocation() {return trackLocation;}
}
