package me.jvegaf.musikbox.context.tracks.application.create;

import me.jvegaf.musikbox.shared.domain.bus.command.Command;

public class CreateTrackCommand implements Command {

    private final String title;
    private final String location;
    private final int    duration;

    public CreateTrackCommand(String title, String location, int duration) {
        this.title    = title;
        this.location = location;
        this.duration = duration;
    }

    public String title() { return title; }

    public String location() { return location; }

    public int duration() { return duration; }
}
