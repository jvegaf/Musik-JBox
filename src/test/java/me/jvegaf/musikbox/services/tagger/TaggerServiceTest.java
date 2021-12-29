package me.jvegaf.musikbox.services.tagger;

import me.jvegaf.musikbox.tracks.Track;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaggerServiceTest {

    @Test
    void TryToFetchTags() {
        TaggerService taggerService = new TaggerService(new BeatportTagger());
        Track t = new Track();
        t.setArtist("joeski");
        t.setName("un congo");
        t.setDuration("07:11");

        Track result = taggerService.fetchTags(t).orElseThrow();
        assertEquals("Un Congo (Extended Mix)", result.getName());
        assertEquals("Joeski", result.getArtist());
        assertEquals("Un Congo EP", result.getAlbum());
        assertEquals("Tech House", result.getGenre());
    }
}
