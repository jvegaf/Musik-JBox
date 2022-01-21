package me.jvegaf.musikbox.app;

import javafx.application.Application;
import me.jvegaf.musikbox.shared.domain.Service;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
@ComponentScan(includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Service.class),
               value = {"me.jvegaf.musikbox.context", "me.jvegaf.musikbox.app", "me.jvegaf.musikbox.shared"})
public class Main {

    public static void main(String[] args) {
        Application.launch(MusikBoxApp.class, args);
    }

}
