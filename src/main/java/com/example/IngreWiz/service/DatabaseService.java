package com.example.IngreWiz.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseService {
    private static final String URL = "jdbc:sqlite:IngreWiz.db"; // Database file will be created in the project root

    public static void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {
            String chefTableSql = "CREATE TABLE IF NOT EXISTS chefs (" +
                                  "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                                  "NAME TEXT NOT NULL," +
                                  "EMAIL TEXT NOT NULL," +
                                  "CATEGORY TEXT NOT NULL," +
                                  "PHONENUMBER TEXT," +
                                  "PROFILEPICTUREURL TEXT)";
            stmt.execute(chefTableSql);

            String recipeTableSql = "CREATE TABLE IF NOT EXISTS recipes (" +
                                    "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                                    "NAME TEXT NOT NULL," +
                                    "DESCRIPTION TEXT NOT NULL," +
                                    "INGREDIENTS TEXT NOT NULL," +
                                    "INSTRUCTIONS TEXT NOT NULL," +
                                    "CHEF_ID INTEGER," +
                                    "FOREIGN KEY (CHEF_ID) REFERENCES chefs(ID) ON DELETE CASCADE)";
            stmt.execute(recipeTableSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}