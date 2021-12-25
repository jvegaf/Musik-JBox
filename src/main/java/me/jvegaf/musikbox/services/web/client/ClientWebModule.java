package me.jvegaf.musikbox.services.web.client;

import com.google.inject.AbstractModule;

public class ClientWebModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ClientWeb.class);
    }
}
