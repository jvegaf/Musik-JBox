package me.jvegaf.musikbox.context.shared.application;

import me.jvegaf.musikbox.shared.domain.TrackResponse;
import me.jvegaf.musikbox.shared.domain.bus.command.Command;

public class FixTagsCommand implements Command {

    private final TrackResponse track;


    public FixTagsCommand(TrackResponse track) {this.track = track;}

    public TrackResponse track() {return track;}
}
