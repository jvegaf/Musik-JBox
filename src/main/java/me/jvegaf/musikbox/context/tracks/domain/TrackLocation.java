package me.jvegaf.musikbox.context.tracks.domain;

public class TrackLocation {

    private String value;

    public TrackLocation(String location) {
        // TODO: Implement VALIDATION
        this.value = location;
    }

    public String value() {
        return value;
    }
}