package edu.anxolerd.inquisition.core.repo;

import edu.anxolerd.inquisition.core.DbHelper;
import edu.anxolerd.inquisition.core.connection.ConnectionPoolWrapper;
import edu.anxolerd.inquisition.core.connection.ExtendedConnectionPool;
import edu.anxolerd.inquisition.core.connection.ExtendedConnectionPoolImpl;
import edu.anxolerd.inquisition.core.entities.Interest;
import edu.anxolerd.inquisition.core.entities.Person;
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
import java.util.Date;
import java.util.UUID;

import static org.junit.Assert.*;


public class PersonRepoTest {
    private static final String DRIVER = "org.postgresql.Driver";
    private static final String URL = "jdbc:postgresql://db/postgres";
    private static final String USER_NAME = "postgres";
    private static final String PASSWORD = "";
    private static final int MAX_CONNECTIONS = 5;
    private static final int INITIAL_CONNECTIONS = 2;
    private static final boolean WAIT_IF_BUSY = true;
    private Logger logger = LoggerFactory.getLogger(getClass());

    private ExtendedConnectionPool pool;
    private PersonRepo personRepo;

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

        personRepo = new PersonRepo(new ConnectionPoolWrapper(pool));
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
        personRepo = null;
    }

    @Test
    public void testCreate() throws Exception {
        final String FIRST_NAME = "John";
        final String LAST_NAME = "Doe";
        final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        final Date BIRTH_DATE = df.parse("1517-11-08");

        Person person = new Person()
            .setFirstName(FIRST_NAME)
            .setLastName(LAST_NAME)
            .setBirthDate(BIRTH_DATE);

        assertNull(person.getId());
        person = personRepo.create(person);
        assertNotNull(person);
        assertNotNull(person.getId());
    }

    @Test
    public void testGetById() throws Exception {
        final String FIRST_NAME = "John";
        final String LAST_NAME = "Doe";
        final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        final Date BIRTH_DATE = df.parse("1517-11-08");

        Person person = new Person()
            .setFirstName(FIRST_NAME)
            .setLastName(LAST_NAME)
            .setBirthDate(BIRTH_DATE);
        personRepo.create(person);
        Person recieved = personRepo.getById(person.getId());
        assertEquals(person, recieved);

        UUID nonExistentId = UUID.randomUUID();
        recieved = personRepo.getById(nonExistentId);
        assertNull(recieved);
    }

    @Test
    public void testUpdate() throws Exception {
        final String FIRST_NAME = "John";
        final String MIDDLE_NAME = "Antony";
        final String LAST_NAME = "Doe";
        final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        final Date BIRTH_DATE = df.parse("1517-11-08");
        final Date DEATH_DATE = df.parse("1565-10-18");

        Person person = new Person()
            .setFirstName(FIRST_NAME)
            .setLastName(LAST_NAME)
            .setBirthDate(BIRTH_DATE);
        personRepo.create(person);

        person.setDeathDate(DEATH_DATE).setMiddleName(MIDDLE_NAME);
        boolean success = personRepo.update(person);
        assertTrue(success);

        Person recieved = personRepo.getById(person.getId());
        assertEquals(DEATH_DATE, recieved.getDeathDate());
        assertEquals(MIDDLE_NAME, recieved.getMiddleName());

        person.setId(UUID.randomUUID());
        success = personRepo.update(person);
        assertFalse(success);
    }

    @Test
    public void testDelete() throws Exception {
        UUID nonExistent = UUID.randomUUID();
        boolean success = personRepo.delete(nonExistent);
        assertFalse(success);

        final String FIRST_NAME = "John";
        final String LAST_NAME = "Doe";
        final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        final Date BIRTH_DATE = df.parse("1517-11-08");

        Person person = new Person()
            .setFirstName(FIRST_NAME)
            .setLastName(LAST_NAME)
            .setBirthDate(BIRTH_DATE);
        person = personRepo.create(person);
        success = personRepo.delete(person.getId());
        Person recieved = personRepo.getById(person.getId());

        assertTrue(success);
        assertNull(recieved);
    }

    @Test
    public void testGetSinRate() throws Exception {
        InterestRepo interestRepo = new InterestRepo(new ConnectionPoolWrapper(pool));
        final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Person person = new Person()
            .setFirstName("Vladimir")
            .setMiddleName("Ilyich")
            .setLastName("Lenin")
            .setBirthDate(df.parse("1870-04-22"))
            .setDeathDate(df.parse("1921-01-21"));
        person = personRepo.create(person);
        Interest revolution = interestRepo.create(
            new Interest()
                .setTitle("Revolution")
                .setSinRate(202)
        );
        Interest subbotnik = interestRepo.create(
            new Interest()
                .setTitle("Subbotnik")
                .setSinRate(3)
        );
        interestRepo.create(new Interest().setTitle("Communism").setSinRate(97));

        interestRepo.addInterestForPerson(revolution.getId(), person.getId());
        interestRepo.addInterestForPerson(subbotnik.getId(), person.getId());

        int sinRate = personRepo.getSinRate(person.getId());
        assertEquals(205, sinRate);
    }
}