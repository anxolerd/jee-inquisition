package edu.anxolerd.inquisition.jsp;

import edu.anxolerd.inquisition.core.DbHelper;
import edu.anxolerd.inquisition.core.connection.ConnectionPool;
import edu.anxolerd.inquisition.core.connection.ConnectionPoolWrapper;
import edu.anxolerd.inquisition.core.connection.ExtendedConnectionPoolImpl;
import edu.anxolerd.inquisition.core.repo.CaseRepo;
import edu.anxolerd.inquisition.core.repo.InterestRepo;
import edu.anxolerd.inquisition.core.repo.PersonRepo;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;


public class RepoHolder {
    private static CaseRepo caseRepo = null;
    private static InterestRepo interestRepo = null;
    private static PersonRepo personRepo = null;

    private static ConnectionPool pool = null;

    private static final String DRIVER = "org.postgresql.Driver";
    private static final int MAX_CONNECTIONS = 5;
    private static final int INITIAL_CONNECTIONS = 2;
    private static final boolean WAIT_IF_BUSY = true;

    private static ConnectionPool getPool() {
        try {
            if (pool == null) {
                pool = new ExtendedConnectionPoolImpl(
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
                } catch (SQLException e) {
                    /* do nothing here */
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }

        return pool;
    }

    public static PersonRepo getPersonRepo() {
        if (personRepo == null) {
            personRepo = new PersonRepo(new ConnectionPoolWrapper(getPool()));
        }
        return personRepo;
    }

    public static InterestRepo getInterestRepo() {
        if (interestRepo == null) {
            interestRepo = new InterestRepo(new ConnectionPoolWrapper(getPool()));
        }
        return interestRepo;
    }

    public static CaseRepo getCaseRepo() {
        if (caseRepo == null) {
            caseRepo = new CaseRepo(new ConnectionPoolWrapper(getPool()));
        }
        return caseRepo;
    }
}
