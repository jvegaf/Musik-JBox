package me.jvegaf.musikbox.context.tracks.application.create;

import me.jvegaf.musikbox.shared.domain.bus.command.Command;

public class CreateTrackCommand implements Command {

    private final String title;
    private final String location;
    private final int    duration;
    private final String artist;
    private final String album;
    private final String genre;
    private final String year;
    private final String comments;
    private final String bpm;

    public CreateTrackCommand(String title,
                              String location,
                              int duration,
                              String artist,
                              String album,
                              String genre,
                              String year,
                              String comments,
                              String bpm) {
        this.title    = title;
        this.location = location;
        this.duration = duration;
        this.artist   = artist;
        this.album    = album;
        this.genre    = genre;
        this.year     = year;
        this.comments  = comments;
        this.bpm      = bpm;
    }

    public String title() { return title; }

    public String location() { return location; }

    public int duration() { return duration; }

    public String artist() { return artist; }

    public String album() { return album; }

    public String genre() { return genre; }

    public String year() { return year; }

    public String comments() { return comments; }

    public String bpm() { return bpm; }
}
