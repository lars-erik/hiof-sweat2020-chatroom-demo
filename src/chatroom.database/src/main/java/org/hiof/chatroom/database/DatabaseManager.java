package org.hiof.chatroom.database;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    public static void ensureDatabase(String path, Boolean delete) {
        String url = "jdbc:sqlite:" + path;

        if (delete) {
            try {
                File dbFile = new File(path);
                dbFile.delete();
            }
            catch(Exception ex) {
                // Ignore file not exists
            }
        }

        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(url);
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
//                System.out.println("The driver name is " + meta.getDriverName());
//                System.out.println("A new database has been created.");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
