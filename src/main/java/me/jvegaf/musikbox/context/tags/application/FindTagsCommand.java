package me.jvegaf.musikbox.context.tags.application;

import me.jvegaf.musikbox.shared.domain.bus.command.Command;

public class FindTagsCommand implements Command {

    private final String title;
    private final String artist;


    public FindTagsCommand(String title, String artist) {
        this.title  = title;
        this.artist = artist;
    }

    public String title() { return title; }

    public String artist() { return artist; }
}
