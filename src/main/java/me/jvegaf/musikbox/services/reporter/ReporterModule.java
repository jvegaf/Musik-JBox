package me.jvegaf.musikbox.services.reporter;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class ReporterModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Reporter.class).in(Singleton.class);
    }
}
