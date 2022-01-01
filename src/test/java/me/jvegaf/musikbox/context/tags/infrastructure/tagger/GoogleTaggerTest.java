package me.jvegaf.musikbox.context.tags.infrastructure.tagger;

import org.junit.jupiter.api.Test;

final class GoogleTaggerTest {

    @Test
    void search() {
        GoogleTagger tagger = new GoogleTagger();
        String title = "Fly Life Xtra (extended mix)";
        String artist = "Basement Jaxx";

        tagger.search(title, artist);
    }
}