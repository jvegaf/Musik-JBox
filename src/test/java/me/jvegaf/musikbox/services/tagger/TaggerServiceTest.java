package me.jvegaf.musikbox.services.tagger;

import me.jvegaf.musikbox.tracks.Track;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class TaggerServiceTest {

    @Test
    void fetchTags() {
        TaggerService taggerService = new TaggerService();
        Track t = new Track();
        t.setArtist("Enrico Caruso");
        t.setName("Jenny");
        t.setDuration("06:39");

        assertDoesNotThrow(() -> taggerService.fetchTags(t));
    }
}
