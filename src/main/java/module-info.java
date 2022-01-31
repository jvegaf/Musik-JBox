module com.github.jvegaf.musikbox {
    requires javafx.controls;
    requires javafx.fxml;

    requires spring.boot.autoconfigure;
    requires spring.context;
    requires net.rgielen.fxweaver.core;
    requires spring.boot;
    requires org.apache.logging.log4j;
    requires org.apache.commons.io;
    requires fr.brouillard.oss.cssfx;
    requires lombok;
    requires com.google.gson;
    requires java.net.http;
    requires jaudiotagger;
    requires javafx.media;
    requires spring.beans;
    requires org.hibernate.orm.core;
    requires spring.core;
    requires spring.jdbc;
    requires spring.orm;
    requires spring.tx;
    requires java.sql;
    requires java.naming;
    requires java.persistence;
    requires dotenv.java;
    requires org.reflections;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires com.google.common;

    opens com.github.jvegaf.musikbox.app to javafx.fxml;
    exports com.github.jvegaf.musikbox.app;
}
