package me.jvegaf.musikbox.context.tracks.application.update;

import me.jvegaf.musikbox.shared.domain.bus.command.Command;

public class UpdateTrackCommand implements Command {

    private final String  trackId;
    private final String  title;
    private final String  artist;
    private final String  album;
    private final String  genre;
    private final String  year;
    private final Integer bpm;
    private final String  key;
    private final String  comments;

    public UpdateTrackCommand(
            String trackId,
            String title,
            String artist,
            String album,
            String genre,
            String year,
            Integer bpm,
            String key,
            String comments
    ) {
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

    public String trackId() {return trackId;}

    public String title() {return title;}

    public String artist() {return artist;}

    public String album() {return album;}

    public Integer bpm() {return bpm;}

    public String key() {return key;}

    public String genre() {return genre;}

    public String year() {return year;}

    public String comments() {return comments;}
}
