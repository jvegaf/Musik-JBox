package me.jvegaf.musikbox.services.tagger;

import me.jvegaf.musikbox.tracks.Track;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TaggerServiceTest {

    @Test
    void TryToFetchTags() {
        TaggerService taggerService = new TaggerService();
        Track t = new Track();
        t.setArtist("Enrico Caruso");
        t.setName("Jenny");
        t.setDuration("06:39");

        Track result = taggerService.fetchTags(t);
        assertNotNull(result);
        System.out.println(result);
    }
}
