package me.jvegaf.musikbox.services.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TimeParserTest {

    @Test
    void tryParseTime() {
        String time = "8:10";

        int expected = 490;
        int result = TimeParser.parseTime(time);

        assertEquals(expected, result);
    }
    @Test
    void tryParseOtherTime() {
        String time = "18:10";

        int expected = 1090;
        int result = TimeParser.parseTime(time);

        assertEquals(expected, result);
    }

    @Test
    void tryFormatTime() {
        int time = 490;

        String expected = "08:10";
        String result = TimeParser.formatTime(time);

        assertEquals(expected, result);
    }

    @Test
    void tryFormatOtherTime() {
        int time = 890;

        String expected = "14:50";
        String result = TimeParser.formatTime(time);

        assertEquals(expected, result);
    }

    @Test
    void tryFormatDefaultTime() {
        int time = 0;

        String expected = "00:00";
        String result = TimeParser.formatTime(time);

        assertEquals(expected, result);
    }
}