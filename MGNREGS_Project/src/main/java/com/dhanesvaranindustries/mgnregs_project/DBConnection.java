package com.dhanesvaranindustries.mgnregs_project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/mgnregs_project";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static Connection getConnection() {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Database connected successfully!");
            return conn;
        } catch (SQLException e) {
            System.out.println("❌ Database connection failed: " + e.getMessage());
            return null;
        }
    }

    public static void main(String[] args) {
        getConnection();  // Running a connection test
    }
}