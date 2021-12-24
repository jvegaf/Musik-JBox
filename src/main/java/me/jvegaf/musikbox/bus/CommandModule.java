package me.jvegaf.musikbox.bus;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public final class CommandModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(CommandBus.class).to(CommandHandler.class);
    }
}
