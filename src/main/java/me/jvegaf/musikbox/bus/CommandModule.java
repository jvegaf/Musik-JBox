package me.jvegaf.musikbox.bus;

import com.google.inject.AbstractModule;

public final class CommandModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(CommandBus.class).to(CommandHandler.class);
    }
}
