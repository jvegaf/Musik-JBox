package me.jvegaf.musikbox.ui;

import com.google.inject.AbstractModule;
import me.jvegaf.musikbox.tracks.TrackModule;
import me.jvegaf.musikbox.ui.views.MainViewController;

public final class GUIConfig extends AbstractModule {
    @Override
    protected void configure() {

        install( new TrackModule() );
        bind(MainViewController.class);
    }
}
