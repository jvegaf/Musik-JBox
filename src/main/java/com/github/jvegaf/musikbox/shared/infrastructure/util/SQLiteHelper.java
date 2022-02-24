package com.github.jvegaf.musikbox.shared.infrastructure.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteHelper {
    private static Connection connection;

    public synchronized static Connection getConnection() throws SQLException {

        if (connection == null) {
            try {
                String driverClass = "org.sqlite.JDBC";
                Class.forName(driverClass);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            String url = "jdbc:sqlite:src/main/resources/db/musikbox.db";
            return connection = DriverManager.getConnection(url);
        } else {
            return connection;
        }
    }
    public static void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            throw new NullPointerException("Can not close db");
        }
    }

    private static final String DB_TRACK = "create table if not exists tracks (" +
                                            "  id text(36) not null," +
                                            "  title text(255) not null," +
                                            "  location text(255) not null," +
                                            "  duration integer not null," +
                                            "  artist text(255)," +
                                            "  album text(255)," +
                                            "  genre text(20)," +
                                            "  year text(4)," +
                                            "  bpm integer," +
                                            "  key text(10)," +
                                            "  comments text(255)," +
                                            "  primary key (id)" +
                                            ");";

    private static final String DB_PLAYLIST = "create table if not exists playlists (" +
                                            "  id text(36) not null," +
                                            "  name text(255) not null," +
                                            "  primary key (id)" +
                                            ");";

    private static final String DB_TRACK_PL = "create table if not exists track_playlist (" +
                                          "  id text(36) not null," +
                                          "  track_id text(36) not null," +
                                          "  playlist_id text(36) not null," +
                                          "  position integer not null," +
                                          "  primary key (id)" +
                                          ");";

    public static void createDatabases() throws SQLException, IOException {
        Connection connection = getConnection();
        Statement  statement  = connection.createStatement();
        statement.execute(DB_TRACK);
        statement.execute(DB_PLAYLIST);
        statement.execute(DB_TRACK_PL);
        close();
    }
}
