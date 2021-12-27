package me.jvegaf.musikbox.services.parser;

import me.jvegaf.musikbox.services.tagger.SearchResult;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

final class ParserTest {

    @Test
    void tryAGenerateAListOfSearchResults() {
        File file = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("response.json")).getFile());
        Path filePath = Path.of(file.getAbsolutePath());
        String responseStr = null;
        try {
            responseStr = Files.readString(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<SearchResult> result = Parser.getResultsFromResponse(responseStr);
        assertNotNull(result);
        assertTrue(result.size() > 0);
    }

    @Test
    void tryGenerateSearchResult() {
        File file = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("track2artist.json")).getFile());
        Path filePath = Path.of(file.getAbsolutePath());
        String responseStr = null;
        try {
            responseStr = Files.readString(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<SearchResult> results = Parser.getResultsFromResponse(responseStr);
        SearchResult result = results.get(0);
        assertEquals("Ben A", result.Artists().get(0));
        assertEquals("Alejandro Penaloza", result.Artists().get(1));
        assertEquals("Yo Tengo Un Congo", result.Title());
        assertEquals("Extended Mix", result.RemixName());
        assertEquals("B1", result.Key());
        assertEquals("2021", result.Year());
        assertEquals("06:10", result.Duration());
    }
}
