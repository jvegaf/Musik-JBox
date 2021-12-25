package me.jvegaf.musikbox.bus;

import com.google.inject.AbstractModule;
import me.jvegaf.musikbox.services.tagger.TaggerModule;
import me.jvegaf.musikbox.services.web.client.ClientWebModule;

public final class CommandModule extends AbstractModule {
    @Override
    protected void configure() {
        install( new TaggerModule() );
        bind(CommandBus.class).to(CommandHandler.class);
    }
}
