package org.example.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.EmptyStackException;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class ConnectionProvider {
    private static ConnectionProvider _instance;

    private Stack<Connection> connections;
    private Set<Connection> usedConnections;
    private static final int MAX_CONNECTIONS = 5;

    private ConnectionProvider() throws DbException {
        connections = new Stack<>();
        usedConnections = new HashSet<>();
        try {
            Class.forName("org.postgresql.Driver");
            for(int i=0; i < MAX_CONNECTIONS; i++){
                Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/culinaryExchange", "postgres", "123");
                connections.push(connection);
            }
        } catch (ClassNotFoundException e) {
            throw new DbException("PostgreSQL JDBC Driver not found.", e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static synchronized ConnectionProvider getInstance() throws SQLException, DbException {
        if (_instance == null) {
            synchronized (ConnectionProvider.class) {
                if (_instance == null) {
                    _instance = new ConnectionProvider();
                }
            }
        }
        return _instance;
    }
    public synchronized Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            connection = connections.pop();
            usedConnections.add(connection);
        } catch (EmptyStackException e) {
            connection =
                    DriverManager.getConnection(
                            "jdbc:postgresql://localhost:5432/culinaryExchange", "postgres", "123");
        }
        return connection;
    }
    public synchronized void releaseConnection(Connection connection) {
        usedConnections.remove(connection);
        connections.push(connection);
    }

    public void destroy() {
        for (Connection connection : usedConnections ) {
            try {
                connection.rollback();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        for (Connection connection : connections) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}


