package me.jvegaf.musikbox.context.tracks;

import me.jvegaf.musikbox.context.tracks.domain.TrackDuration;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TrackDurationTest {

    @Test void tryInitializeWithStringValue() {
        TrackDuration duration = new TrackDuration("18:10");

        int expected = 1090;

        assertEquals(expected, duration.value());
    }

    @Test void tryInitializeWithIntValue() {
        TrackDuration duration = new TrackDuration(890);

        String expected = "14:50";

        assertEquals(expected, duration.stringValue());
    }
}