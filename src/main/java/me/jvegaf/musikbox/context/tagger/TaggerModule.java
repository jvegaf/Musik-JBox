package me.jvegaf.musikbox.context.tagger;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class TaggerModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(BeatportTagger.class).in(Singleton.class);
        bind(TaggerService.class).in(Singleton.class);
    }
}
