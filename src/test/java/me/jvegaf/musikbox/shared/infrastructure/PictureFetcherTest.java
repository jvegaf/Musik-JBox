package me.jvegaf.musikbox.shared.infrastructure;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class PictureFetcherTest {

    @Test
    void try_fetch_picture_from_url() throws IOException {
        PictureFetcher fetcher = new PictureFetcher();
        String uri = "https://geo-media.beatport.com/image_size/1400x1400/816179c4-22ef-41b8-9419-98177cdf1757.jpg";
        byte[] result = fetcher.fetchFromURI(uri);
        assertEquals(227348, result.length);
    }
}