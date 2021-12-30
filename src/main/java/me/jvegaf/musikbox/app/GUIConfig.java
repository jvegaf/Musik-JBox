package me.jvegaf.musikbox.app;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import me.jvegaf.musikbox.app.components.Header;
import me.jvegaf.musikbox.app.components.SideBar;
import me.jvegaf.musikbox.app.components.Tracklist;
import me.jvegaf.musikbox.app.controller.DetailViewController;
import me.jvegaf.musikbox.app.controller.MainController;
import me.jvegaf.musikbox.app.controller.MainViewController;
import me.jvegaf.musikbox.app.player.MusicPlayer;
import me.jvegaf.musikbox.bus.CommandModule;
import me.jvegaf.musikbox.context.playlists.PlaylistModule;
import me.jvegaf.musikbox.context.tracks.TrackModule;
import me.jvegaf.musikbox.services.reporter.ReporterModule;

public final class GUIConfig extends AbstractModule {
    @Override
    protected void configure() {

        install ( new PlaylistModule() );
        install( new TrackModule() );
        install( new CommandModule());
        install( new ReporterModule() );
        bind(MusicPlayer.class).in(Singleton.class);
        bind(Header.class);
        bind(SideBar.class);
        bind(Tracklist.class);
        bind(DetailViewController.class);
        bind(MainController.class).to(MainViewController.class);
    }
}
