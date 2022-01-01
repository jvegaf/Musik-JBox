package me.jvegaf.musikbox.context.tracks.application.upgrade;

import me.jvegaf.musikbox.shared.domain.bus.command.Command;

public class UpgradeTrackCommand implements Command {

    private final String trackId;
    private final String title;
    private final String artist;
    private final String album;
    private final String genre;
    private final String year;
    private final String bpm;
    private final String key;
    private final String comments;

    public UpgradeTrackCommand(String trackId,
                               String title,
                               String artist,
                               String album,
                               String genre,
                               String year,
                               String bpm,
                               String key,
                               String comments) {
        this.trackId  = trackId;
        this.title    = title;
        this.artist   = artist;
        this.album    = album;
        this.genre    = genre;
        this.year     = year;
        this.bpm      = bpm;
        this.key      = key;
        this.comments = comments;
    }

    public String trackId() { return trackId; }

    public String title() { return title; }

    public String artist() { return artist; }

    public String album() { return album; }

    public String bpm() { return bpm; }

    public String key() { return key; }

    public String genre() { return genre; }

    public String year() { return year; }

    public String comments() { return comments; }
}
