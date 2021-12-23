package me.jvegaf.musikbox;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import me.jvegaf.musikbox.ui.GUI;
import me.jvegaf.musikbox.ui.GUIConfig;

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
