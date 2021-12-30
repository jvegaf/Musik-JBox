package me.jvegaf.musikbox.context.tracks;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public final class TrackModule extends AbstractModule {
    @Override
    protected void configure() {
        super.configure();
        bind(OldTracksRepository.class).in(Singleton.class);
    }
}
