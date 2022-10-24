package org.hiof.chatroom.database;

import java.sql.*;

public class DatabaseManager {
    public static void ensureDatabase(String path) {
        String url = "jdbc:sqlite:" + path;

        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(url);
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
