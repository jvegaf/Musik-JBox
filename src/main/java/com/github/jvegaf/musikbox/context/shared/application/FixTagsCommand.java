package com.github.jvegaf.musikbox.context.shared.application;

import com.github.jvegaf.musikbox.shared.domain.TrackResponse;
import com.github.jvegaf.musikbox.shared.domain.bus.command.Command;

public class FixTagsCommand implements Command {

    private final TrackResponse track;


    public FixTagsCommand(TrackResponse track) {this.track = track;}

    public TrackResponse track() {return track;}
}
