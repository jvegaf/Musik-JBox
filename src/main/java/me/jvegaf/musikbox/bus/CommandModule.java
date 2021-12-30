package me.jvegaf.musikbox.bus;

import com.google.inject.AbstractModule;
import me.jvegaf.musikbox.context.tagger.TaggerModule;
import me.jvegaf.musikbox.services.reporter.ReporterModule;

public final class CommandModule extends AbstractModule {
    @Override
    protected void configure() {
        install( new TaggerModule() );
        install( new ReporterModule() );
        bind(CommandBus.class).to(CommandHandler.class);
    }
}
