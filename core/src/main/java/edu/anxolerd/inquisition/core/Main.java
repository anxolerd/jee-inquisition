package edu.anxolerd.inquisition.core;


import edu.anxolerd.inquisition.core.connection.ConnectionPool;
import edu.anxolerd.inquisition.core.connection.ExtendedConnectionPool;
import edu.anxolerd.inquisition.core.connection.ExtendedConnectionPoolImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    private static final String DRIVER = "org.postgresql.Driver";
    private static final int MAX_CONNECTIONS = 5;
    private static final int INITIAL_CONNECTIONS = 2;
    private static final boolean WAIT_IF_BUSY = true;

    public static void main(String[] args) throws Exception {
        ExtendedConnectionPool pool = new ExtendedConnectionPoolImpl(
            DRIVER,
            System.getProperty("db.url"),
            System.getProperty("db.user"),
            System.getProperty("db.pass"),
            INITIAL_CONNECTIONS,
            MAX_CONNECTIONS,
            WAIT_IF_BUSY
        );

        Connection conn = pool.getConnection();
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(DbHelper.INIT_SQL);
        } catch (SQLException e) { /* probably db is already created */ }
        finally { pool.releaseConnection(conn); }
    }
}
