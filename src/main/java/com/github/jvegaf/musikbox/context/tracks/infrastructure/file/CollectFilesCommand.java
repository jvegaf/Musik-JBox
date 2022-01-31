package com.github.jvegaf.musikbox.context.tracks.infrastructure.file;

import com.github.jvegaf.musikbox.shared.domain.bus.command.Command;

import java.io.File;

public final class CollectFilesCommand implements Command {
    private final File path;

    public CollectFilesCommand(File path) {this.path = path;}

    public File path() {return path;}
}
