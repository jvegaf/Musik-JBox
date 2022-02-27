package com.github.jvegaf.musikbox.app;

import com.github.jvegaf.musikbox.shared.domain.Service;
import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
@ComponentScan(includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Service.class),
               value = {"com.github.jvegaf.musikbox.context",
                        "com.github.jvegaf.musikbox.app",
                        "com.github.jvegaf.musikbox.shared"})
public class Main {

    public static void main(String[] args) {
        System.out.println("prelaunch");
        Application.launch(MusikBoxApp.class, args);
    }

}
