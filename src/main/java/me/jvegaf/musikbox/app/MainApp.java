package me.jvegaf.musikbox.app;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class MainApp {

    public static void main(String[] args)
    {

        Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                install(new GUIConfig());
            }
        });

        GUI gui = injector.getInstance(GUI.class);

        try {
            gui.run(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
