package edu.anxolerd.inquisition.core.repo;

import edu.anxolerd.inquisition.core.DbHelper;
import edu.anxolerd.inquisition.core.connection.ConnectionPoolWrapper;
import edu.anxolerd.inquisition.core.connection.ExtendedConnectionPool;
import edu.anxolerd.inquisition.core.connection.ExtendedConnectionPoolImpl;
import edu.anxolerd.inquisition.core.entities.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;


public class CaseRepoTest {
    private static final String DRIVER = "org.postgresql.Driver";
    private static final String URL = "jdbc:postgresql://db/postgres";
    private static final String USER_NAME = "postgres";
    private static final String PASSWORD = "";
    private static final int MAX_CONNECTIONS = 5;
    private static final int INITIAL_CONNECTIONS = 2;
    private static final boolean WAIT_IF_BUSY = true;
    private Logger logger = LoggerFactory.getLogger(getClass());

    private ExtendedConnectionPool pool;
    private CaseRepo caseRepo;
    private PersonRepo personRepo;
    private Person person;

    @Before
    public void setUp() throws Exception {
        pool = new ExtendedConnectionPoolImpl(
            DRIVER, URL, USER_NAME, PASSWORD,
            INITIAL_CONNECTIONS, MAX_CONNECTIONS, WAIT_IF_BUSY
        );

        Connection conn = pool.getConnection();
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(DbHelper.INIT_SQL);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            pool.releaseConnection(conn);
        }

        caseRepo = new CaseRepo(new ConnectionPoolWrapper(pool));
        personRepo = new PersonRepo(new ConnectionPoolWrapper(pool));

        final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        this.person = new Person()
            .setFirstName("Vladimir")
            .setMiddleName("Ilyich")
            .setLastName("Lenin")
            .setBirthDate(df.parse("1870-04-22"))
            .setDeathDate(df.parse("1921-01-21"));
        this.person = personRepo.create(person);
    }

    @After
    public void tearDown() throws Exception {
        Connection conn = pool.getConnection();
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(DbHelper.DROP_SQL);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            pool.releaseConnection(conn);
        }
        pool.closeAllConnections();
        pool = null;
        caseRepo = null;
        personRepo = null;
        this.person = null;
    }

    @Test
    public void testCreate() throws Exception {
        final String ACCUSES = "did on diverse dates commit heresy against the Holy Church";
        Case aCase = new Case()
            .setDateOpened(new Date())
            .setAccuses(ACCUSES)
            .setSuspectPersonId(person.getId())
            .setStatus(CaseStatus.OPEN);
        aCase = caseRepo.create(aCase);
        assertNotNull(aCase);
        assertNotNull(aCase.getId());
    }

    @Test
    public void testGetById() throws Exception {
        final String ACCUSES = "did on diverse dates commit heresy against the Holy Church";
        Case aCase = new Case()
            .setDateOpened(new Date())
            .setAccuses(ACCUSES)
            .setSuspectPersonId(person.getId())
            .setStatus(CaseStatus.OPEN);
        aCase = caseRepo.create(aCase);
        Case recieved = caseRepo.getById(aCase.getId());
        assertEquals(aCase, recieved);

        UUID nonExistentId = UUID.randomUUID();
        recieved = caseRepo.getById(nonExistentId);
        assertNull(recieved);
    }

    @Test
    public void testUpdate() throws Exception {
        final String ACCUSES = "did on diverse dates commit heresy against the Holy Church";
        Case aCase = new Case()
            .setDateOpened(new Date())
            .setAccuses(ACCUSES)
            .setSuspectPersonId(person.getId())
            .setStatus(CaseStatus.OPEN);
        aCase = caseRepo.create(aCase);

        Date DATE_CLOSED = new Date();
        CaseVerdict VERDICT = CaseVerdict.GUILTY;
        String SENTENCE = "Fear, surprise, and a most ruthless-- Ooooh! Now, Cardinal -- the rack!";
        aCase
            .setDateClosed(DATE_CLOSED)
            .setVerdict(VERDICT)
            .setSentence(SENTENCE)
            .setStatus(CaseStatus.CLOSED);
        boolean success = caseRepo.update(aCase);
        assertTrue(success);

        Case recieved = caseRepo.getById(aCase.getId());
        assertEquals(DATE_CLOSED, recieved.getDateClosed());
        assertEquals(SENTENCE, recieved.getSentence());
        assertEquals(VERDICT, recieved.getVerdict());

        aCase.setId(UUID.randomUUID());
        success = caseRepo.update(aCase);
        assertFalse(success);
    }

    @Test
    public void testDelete() throws Exception {
        UUID nonExistent = UUID.randomUUID();
        boolean success = caseRepo.delete(nonExistent);
        assertFalse(success);

        final String ACCUSES = "did on diverse dates commit heresy against the Holy Church";
        Case aCase = new Case()
            .setDateOpened(new Date())
            .setAccuses(ACCUSES)
            .setSuspectPersonId(person.getId())
            .setStatus(CaseStatus.OPEN);
        aCase = caseRepo.create(aCase);
        success = caseRepo.delete(aCase.getId());
        Case recieved = caseRepo.getById(aCase.getId());

        assertTrue(success);
        assertNull(recieved);
    }

    @Test
    public void testGetByStatus() throws Exception {
        final String ACCUSES = "did on diverse dates commit heresy against the Holy Church";
        final String SENTENCE = "Fear, surprise, and a most ruthless-- Ooooh! Now, Cardinal -- the rack!";
        Case case1 = new Case()
            .setDateOpened(new Date())
            .setAccuses(ACCUSES)
            .setSuspectPersonId(person.getId())
            .setStatus(CaseStatus.OPEN);
        case1 = caseRepo.create(case1);

        Case case2 = new Case()
            .setDateOpened(new Date())
            .setAccuses(ACCUSES)
            .setSuspectPersonId(person.getId())
            .setStatus(CaseStatus.CLOSED)
            .setDateClosed(new Date())
            .setVerdict(CaseVerdict.GUILTY)
            .setSentence(SENTENCE);
        case2 = caseRepo.create(case2);

        List<Case> openCases = caseRepo.getByStatus(CaseStatus.OPEN);
        assertEquals(1, openCases.size());
        assertTrue(openCases.contains(case1));

        List<Case> closedCases = caseRepo.getByStatus(CaseStatus.CLOSED);
        assertEquals(1, openCases.size());
        assertTrue(closedCases.contains(case2));
    }

    @Test
    public void testGetAll() throws Exception {
        final String ACCUSES = "did on diverse dates commit heresy against the Holy Church";
        final String SENTENCE = "Fear, surprise, and a most ruthless-- Ooooh! Now, Cardinal -- the rack!";
        Case case1 = new Case()
            .setDateOpened(new Date())
            .setAccuses(ACCUSES)
            .setSuspectPersonId(person.getId())
            .setStatus(CaseStatus.OPEN);
        case1 = caseRepo.create(case1);

        Case case2 = new Case()
            .setDateOpened(new Date())
            .setAccuses(ACCUSES)
            .setSuspectPersonId(person.getId())
            .setStatus(CaseStatus.CLOSED)
            .setDateClosed(new Date())
            .setVerdict(CaseVerdict.GUILTY)
            .setSentence(SENTENCE);
        case2 = caseRepo.create(case2);

        List<Case> cases = new ArrayList<>();
        cases.add(case1);
        cases.add(case2);

        List<Case> allCases = caseRepo.getAll();
        assertEquals(cases.size(), allCases.size());
        assertTrue(allCases.containsAll(cases));
    }

}