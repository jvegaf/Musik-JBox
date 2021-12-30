package me.jvegaf.musikbox.services.tagger;

import me.jvegaf.musikbox.context.tagger.BeatportTagger;
import me.jvegaf.musikbox.context.tagger.TaggerService;
import me.jvegaf.musikbox.context.tracks.domain.Track;
import org.junit.jupiter.api.Test;

class TaggerServiceTest {

    @Test
    void TryToFetchTags() {
        TaggerService taggerService = new TaggerService(new BeatportTagger());
        Track t = new Track(id, location, duration, title);
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
