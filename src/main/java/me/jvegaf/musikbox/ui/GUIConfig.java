package me.jvegaf.musikbox.ui;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import me.jvegaf.musikbox.services.player.MusicPlayer;
import me.jvegaf.musikbox.tracks.TrackModule;
import me.jvegaf.musikbox.ui.components.Header;
import me.jvegaf.musikbox.ui.components.SideBar;
import me.jvegaf.musikbox.ui.components.Tracklist;
import me.jvegaf.musikbox.ui.views.DetailViewController;
import me.jvegaf.musikbox.ui.views.MainViewController;

public final class GUIConfig extends AbstractModule {
    @Override
    protected void configure() {

        install( new TrackModule() );
        bind(MusicPlayer.class).in(Singleton.class);
        bind(Header.class);
        bind(SideBar.class);
        bind(Tracklist.class);
        bind(DetailViewController.class);
        bind(MainViewController.class);
    }
}
