package me.jvegaf.musikbox.tracks;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public final class TrackModule extends AbstractModule {
    @Override
    protected void configure() {
        super.configure();
        bind(TracksRepository.class).in(Singleton.class);
    }
}
