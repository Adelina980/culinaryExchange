package org.example.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionProvider {
    private static ConnectionProvider _instance;
    private static final String URL = "jdbc:postgresql://localhost:5432/culinaryExchange";
    private static final String USER = "root";
    private static final String PASSWORD = "123";

    private ConnectionProvider() throws DbException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new DbException("PostgreSQL JDBC Driver not found.", e);
        }
    }

    public static synchronized ConnectionProvider getInstance() throws DbException {
        if (_instance == null) {
            _instance = new ConnectionProvider();
        }
        return _instance;
    }

    public Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        if (connection != null) {
            System.out.println("Connection established");
        } else {
            System.out.println("Failed to establish connection");
        }
        return connection;
    }
}

