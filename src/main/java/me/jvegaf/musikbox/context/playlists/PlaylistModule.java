package me.jvegaf.musikbox.context.playlists;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class PlaylistModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(PlaylistRepository.class).in(Singleton.class);
    }
}
