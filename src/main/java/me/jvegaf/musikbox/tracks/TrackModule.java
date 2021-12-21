package me.jvegaf.musikbox.tracks;

import com.google.inject.AbstractModule;

public final class TrackModule extends AbstractModule {
    @Override
    protected void configure() {
        super.configure();
        bind(TracksRepository.class).to(TrackListRepository.class);
    }
}
