package me.jvegaf.musikbox.services.tagger;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class SpotifyTaggerTest {

    private final SpotifyTagger finder = new SpotifyTagger();

    @Test
    void findWithArtistAndTitle() {
        String[] args = {"Edu Imbernon", "Indenait"};

        List<SpotifyTag> result = finder.search(args);
        assertNotNull(result);
    }

    @Test
    void findOnlyWithTitle() {
        String[] args = {"DJ Wady Sonarzims"};

        System.out.println("title: " + args[0]);
        List<SpotifyTag> result = finder.search(args);
        assertNotNull(result);
    }

    @Test
    void findWithWorstTitle() {
        String[] args = {"Harry_Romero_From_The_Root_Extended_Mix_"};

        System.out.println("title: " + args[0]);
        List<SpotifyTag> result = finder.search(args);
        assertNotNull(result);
    }
}
