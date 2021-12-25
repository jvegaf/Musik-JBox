package me.jvegaf.musikbox.services.parser;

import me.jvegaf.musikbox.tracks.Track;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class ParserTest {

    @Test
    void tryAGenerateTrackWithOneArtistFromJSONString() {
        File file = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("response.json")).getFile());
        Path filePath = Path.of(file.getAbsolutePath());
        String responseStr = null;
        try {
            responseStr = Files.readString(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Track t = Parser.parseTrack(responseStr);
        assertEquals("Joeski", t.getArtist());
        assertEquals("Un Congo (Extended Mix)", t.getName());
        assertEquals("A10", t.getKey());
        assertEquals("2020", t.getYear());
        assertEquals("7:11", t.getDuration());
    }

    @Test
    void tryAGenerateTrackWithTwoArtistsFromJSONString() {
        File file = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("response2artist.json")).getFile());
        Path filePath = Path.of(file.getAbsolutePath());
        String responseStr = null;
        try {
            responseStr = Files.readString(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Track t = Parser.parseTrack(responseStr);
        assertEquals("Ben A, Alejandro Penaloza", t.getArtist());
        assertEquals("Yo Tengo Un Congo (Extended Mix)", t.getName());
        assertEquals("B1", t.getKey());
        assertEquals("2021", t.getYear());
        assertEquals("6:10", t.getDuration());
    }
}
