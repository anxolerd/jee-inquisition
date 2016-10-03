package edu.anxolerd.inquisition.core.connection;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPoolWrapper {
    private final ConnectionPool pool;

    public ConnectionPoolWrapper(ConnectionPool pool) {
        this.pool = pool;
    }

    public Connection getConnection() {
        try {
            Connection conn = pool.getConnection();
            return conn;
        } catch (SQLException sqle) {
            throw new RuntimeException("Failed to obtain a connection", sqle);
        }
    }

    public void releaseConection(Connection connection) {
        try {
            this.pool.releaseConnection(connection);
        } catch (SQLException sqle) {
            throw new RuntimeException("Failed to release a connection", sqle);
        }
    }
}
