package com.github.jvegaf.musikbox.shared.infrastructure.persistence.hibernate;

import com.github.jvegaf.musikbox.shared.infrastructure.config.Parameter;
import com.github.jvegaf.musikbox.shared.infrastructure.config.ParameterNotExist;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@EnableTransactionManagement
public class MusikBOXHibernateConfiguration {

    private final HibernateConfigurationFactory factory;
    private final Parameter                     config;

    public MusikBOXHibernateConfiguration(HibernateConfigurationFactory factory, Parameter config) {
        this.factory = factory;
        this.config  = config;
    }

    @Bean("musikbox-transaction_manager")
    public PlatformTransactionManager hibernateTransactionManager() throws IOException, ParameterNotExist {
        return factory.hibernateTransactionManager(sessionFactory());
    }

    @Bean("musikbox-session_factory")
    public LocalSessionFactoryBean sessionFactory() throws IOException, ParameterNotExist {
        String CONTEXT_NAME = "musikbox";
        return factory.sessionFactory(CONTEXT_NAME, dataSource());
    }

    @Bean("musikbox-data_source")
    public DataSource dataSource() throws IOException, ParameterNotExist {
        return factory.dataSource(config.get("DATABASE_NAME"),
                                  config.get("DATABASE_USER"),
                                  config.get("DATABASE_PASSWORD"));
    }
}
