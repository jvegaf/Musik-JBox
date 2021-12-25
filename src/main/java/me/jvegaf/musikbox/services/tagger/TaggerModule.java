package me.jvegaf.musikbox.services.tagger;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import me.jvegaf.musikbox.services.web.client.ClientWebModule;

public class TaggerModule extends AbstractModule {
    @Override
    protected void configure() {
        install( new ClientWebModule() );
        bind(Tagger.class).to(BeatportTagger.class);
        bind(TaggerService.class).in(Singleton.class);
    }
}
