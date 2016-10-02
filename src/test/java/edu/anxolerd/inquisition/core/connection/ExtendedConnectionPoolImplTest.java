package edu.anxolerd.inquisition.core.connection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.assertTrue;

/**
 * This class contains unit tests for connection pool.
 */
public class ExtendedConnectionPoolImplTest {

    private static final String DRIVER = "org.postgresql.Driver";
    private static final String URL = "jdbc:postgresql://db/postgres";
    private static final String USER_NAME = "postgres";
    private static final String PASSWORD = "";
    private static final int MAX_CONNECTIONS = 5;
    private static final int INITIAL_CONNECTIONS = 2;
    private static final boolean WAIT_IF_BUSY = true;
    private Logger log = LoggerFactory.getLogger(getClass());
    private ExtendedConnectionPool pool = null;

    @Before
    public void setUp() throws SQLException {
        pool = new ExtendedConnectionPoolImpl(
            DRIVER, URL, USER_NAME, PASSWORD,
            INITIAL_CONNECTIONS, MAX_CONNECTIONS, WAIT_IF_BUSY
        );
        log.debug("Connection pool created" + pool);
    }

    @After
    public void tearDown() {
        pool = null;
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullDriver() throws SQLException {
        pool = new ExtendedConnectionPoolImpl(null, URL, USER_NAME, PASSWORD,
            INITIAL_CONNECTIONS, MAX_CONNECTIONS, WAIT_IF_BUSY);
    }

    @Test(expected = SQLException.class)
    public void testDriverNotFound() throws SQLException {
        pool = new ExtendedConnectionPoolImpl("some.funky.driver", URL,
            USER_NAME, PASSWORD, INITIAL_CONNECTIONS, MAX_CONNECTIONS,
            WAIT_IF_BUSY);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullUrl() throws SQLException {
        pool = new ExtendedConnectionPoolImpl(DRIVER, null, USER_NAME, PASSWORD,
            INITIAL_CONNECTIONS, MAX_CONNECTIONS, WAIT_IF_BUSY);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullUserName() throws SQLException {
        pool = new ExtendedConnectionPoolImpl(DRIVER, URL, null, PASSWORD,
            INITIAL_CONNECTIONS, MAX_CONNECTIONS, WAIT_IF_BUSY);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullPassword() throws SQLException {
        pool = new ExtendedConnectionPoolImpl(DRIVER, URL, USER_NAME, null,
            INITIAL_CONNECTIONS, MAX_CONNECTIONS, WAIT_IF_BUSY);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidMaxConnections() throws SQLException {
        pool = new ExtendedConnectionPoolImpl(DRIVER, URL, USER_NAME, PASSWORD,
            INITIAL_CONNECTIONS, -2, WAIT_IF_BUSY);
    }

    @Test
    public void testPoolInitialization() throws SQLException {
        assertTrue("unexpected size of pool",
            INITIAL_CONNECTIONS == pool.getNumberOfAvailableConnections());
    }

    @Test
    public void testGetConnection() throws SQLException {
        pool.getConnection();
        assertTrue(pool.getNumberOfAvailableConnections() == 1);
        assertTrue(pool.getNumberOfBusyConnections() == 1);
    }

    @Test
    public void testReleaseConnection() throws SQLException {
        Connection conn = pool.getConnection();
        assertTrue(pool.getNumberOfBusyConnections() == 1);
        pool.releaseConnection(conn);
        assertTrue(pool.getNumberOfBusyConnections() == 0);
    }

    @Test
    public void testCloseAllConnections() throws SQLException {
        pool.getConnection();
        assertTrue(pool.getNumberOfBusyConnections() == 1);
        assertTrue(pool.getNumberOfAvailableConnections() == 1);
        pool.closeAllConnections();
        assertTrue(pool.getNumberOfBusyConnections() == 0);
        assertTrue(pool.getNumberOfAvailableConnections() == 0);
    }
}