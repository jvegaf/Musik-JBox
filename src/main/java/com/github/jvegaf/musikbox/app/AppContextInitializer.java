package com.github.jvegaf.musikbox.app;

import com.github.jvegaf.musikbox.shared.infrastructure.util.ConfigHelper;
import com.github.jvegaf.musikbox.shared.infrastructure.util.SQLiteHelper;
import javafx.application.Platform;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.io.File;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class AppContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        ConfigHelper.workDirectoryChecker();

        ConfigurableEnvironment environment = configurableApplicationContext.getEnvironment();

        String workingDir = ConfigHelper.workDirectoryChecker();
        // Create map for properites and add first (important)
        Map<String, Object> myProperties = new HashMap<>();
        String databaseUrl = "jdbc:sqlite:";
        databaseUrl += workingDir + File.separator + "musikbox.db";
        myProperties.put("workdir", workingDir);
        myProperties.put("database.url", databaseUrl);
        myProperties.put("database.file", workingDir + File.separator + "musikbox.db");
        environment.getPropertySources()
                .addFirst(new MapPropertySource("my-props", myProperties));
        String url = databaseUrl;
        Platform.runLater(() -> {
            try {
                SQLiteHelper.checkDatabase(url);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

}