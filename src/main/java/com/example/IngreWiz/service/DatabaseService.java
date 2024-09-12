package com.example.IngreWiz.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseService {
    private static final String URL = "jdbc:sqlite:src/main/resources/database.db"; // Database file will be created in the project root

    public static void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {
            String chefTableSql = "CREATE TABLE IF NOT EXISTS chef (" +
                                  "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                                  "NAME TEXT NOT NULL," +
                                  "EMAIL TEXT NOT NULL," +
                                  "CATEGORY TEXT NOT NULL," +
                                  "PHONE_NUMBER TEXT," +
                                  "PROFILE_PICTURE_URL TEXT)";
            stmt.execute(chefTableSql);

            String recipeTableSql = "CREATE TABLE IF NOT EXISTS recipe (" +
                                    "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                                    "NAME TEXT NOT NULL," +
                                    "CATEGORY TEXT NOT NULL," +
                                    "SERVINGS INTEGER NOT NULL," +
                                    "DESCRIPTION TEXT NOT NULL," +
                                    "INGREDIENTS TEXT NOT NULL," +
                                    "STEPS TEXT NOT NULL," +
                                    "CHEF_ID INTEGER NOT NULL," +
                                    "FOREIGN KEY (CHEF_ID) REFERENCES chef(ID) ON DELETE CASCADE)";
            stmt.execute(recipeTableSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
