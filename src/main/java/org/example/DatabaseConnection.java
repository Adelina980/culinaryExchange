package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static Connection getConnection() {
    try {
        String url = "jdbc:postgresql://localhost:5432/culinaryExchange";
        String user = "root";
        String password = "123";
        return DriverManager.getConnection(url, user, password);
    } catch (SQLException e) {
        e.printStackTrace();
        return null;
    }
}
}
