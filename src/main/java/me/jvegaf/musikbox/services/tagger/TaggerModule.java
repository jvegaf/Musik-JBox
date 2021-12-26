package me.jvegaf.musikbox.services.tagger;

import com.google.inject.AbstractModule;

public class TaggerModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(BeatportTagger.class);
        bind(TaggerService.class);
    }
}
