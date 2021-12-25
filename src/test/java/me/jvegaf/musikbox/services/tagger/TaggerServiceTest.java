package me.jvegaf.musikbox.services.tagger;

import me.jvegaf.musikbox.services.web.client.ClientWeb;
import me.jvegaf.musikbox.tracks.Track;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaggerServiceTest {

    @Test
    void fetchTags() {
        TaggerService taggerService = new TaggerService(new BeatportTagger(new ClientWeb()));
        Track t = new Track();
        t.setArtist("Enrico Caruso");
        t.setName("Jenny");
        t.setDuration("06:39");

        assertDoesNotThrow(() -> taggerService.fetchTags(t));
    }
}
