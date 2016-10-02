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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;


public class InterestRepoTest {
    private static final String DRIVER = "org.postgresql.Driver";
    private static final String URL = "jdbc:postgresql://db/postgres";
    private static final String USER_NAME = "postgres";
    private static final String PASSWORD = "";
    private static final int MAX_CONNECTIONS = 5;
    private static final int INITIAL_CONNECTIONS = 2;
    private static final boolean WAIT_IF_BUSY = true;
    private Logger logger = LoggerFactory.getLogger(getClass());

    private ExtendedConnectionPool pool;
    private InterestRepo interestRepo;

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

        interestRepo = new InterestRepo(new ConnectionPoolWrapper(pool));
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
        interestRepo = null;
    }

    @Test
    public void testCreate() throws Exception {
        final String TITLE = "Random title";
        final String DESCRIPTION = "Random description";
        final int SIN_RATE = 12;

        Interest interest = new Interest()
            .setTitle(TITLE)
            .setDescription(DESCRIPTION)
            .setSinRate(SIN_RATE);

        assertNull(interest.getId());
        interest = interestRepo.create(interest);
        assertNotNull(interest);
        assertNotNull(interest.getId());
    }

    @Test
    public void testGetById() throws Exception {
        final String TITLE = "Random title";
        final String DESCRIPTION = "Random description";
        final int SIN_RATE = 12;

        Interest interest = new Interest()
            .setTitle(TITLE)
            .setDescription(DESCRIPTION)
            .setSinRate(SIN_RATE);
        interestRepo.create(interest);
        Interest recieved = interestRepo.getById(interest.getId());
        assertEquals(interest, recieved);

        UUID nonExistentId = UUID.randomUUID();
        recieved = interestRepo.getById(nonExistentId);
        assertNull(recieved);
    }

    @Test
    public void testUpdate() throws Exception {
        final String TITLE = "Random title";
        final String NEW_TITLE = "Not so random title";
        final String DESCRIPTION = "Random description";
        final int SIN_RATE = 12;
        final int NEW_SIN_RATE = 13;

        Interest interest = new Interest()
            .setTitle(TITLE)
            .setDescription(DESCRIPTION)
            .setSinRate(SIN_RATE);
        interestRepo.create(interest);

        interest.setSinRate(NEW_SIN_RATE).setTitle(NEW_TITLE);
        boolean success = interestRepo.update(interest);
        assertTrue(success);

        Interest recieved = interestRepo.getById(interest.getId());
        assertEquals(NEW_TITLE, recieved.getTitle());
        assertEquals(NEW_SIN_RATE, recieved.getSinRate());

        interest.setId(UUID.randomUUID());
        success = interestRepo.update(interest);
        assertFalse(success);
    }

    @Test
    public void testDelete() throws Exception {
        UUID nonExistent = UUID.randomUUID();
        boolean success = interestRepo.delete(nonExistent);
        assertFalse(success);

        final String TITLE = "Random title";
        final String DESCRIPTION = "Random description";
        final int SIN_RATE = 12;

        Interest interest = new Interest()
            .setTitle(TITLE)
            .setDescription(DESCRIPTION)
            .setSinRate(SIN_RATE);
        interest = interestRepo.create(interest);
        success = interestRepo.delete(interest.getId());
        Interest recieved = interestRepo.getById(interest.getId());

        assertTrue(success);
        assertNull(recieved);
    }

    @Test
    public void testGetInterestsForPerson() throws Exception {
        final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        PersonRepo personRepo = new PersonRepo(new ConnectionPoolWrapper(pool));
        Person person = new Person()
            .setFirstName("Vladimir")
            .setMiddleName("Ilyich")
            .setLastName("Lenin")
            .setBirthDate(df.parse("1870-04-22"))
            .setDeathDate(df.parse("1921-01-21"));
        person = personRepo.create(person);
        List<Interest> interests = new ArrayList<>();
        interests.add(new Interest().setTitle("Communism").setSinRate(10));
        interests.add(new Interest().setTitle("Subbotnik").setSinRate(0));
        interests.add(new Interest().setTitle("Revolution").setSinRate(200));

        for (Interest interest : interests) {
            interestRepo.create(interest);
            interestRepo.addInterestForPerson(interest.getId(), person.getId());
        }

        List<Interest> leninInterests = interestRepo.getInterestsForPerson(person.getId());
        assertEquals(interests.size(), leninInterests.size());
        assertTrue(interests.containsAll(leninInterests));
        assertTrue(leninInterests.containsAll(interests));
    }

    @Test
    public void testAddInterestForPerson() throws Exception {
        final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        PersonRepo personRepo = new PersonRepo(new ConnectionPoolWrapper(pool));
        Person person = new Person()
            .setFirstName("Vladimir")
            .setMiddleName("Ilyich")
            .setLastName("Lenin")
            .setBirthDate(df.parse("1870-04-22"))
            .setDeathDate(df.parse("1921-01-21"));
        person = personRepo.create(person);
        Interest interest = interestRepo.create(
            new Interest()
                .setTitle("Subbotnik")
                .setSinRate(0)
        );

        interestRepo.addInterestForPerson(interest.getId(), person.getId());

        List<Interest> leninInterests = interestRepo.getInterestsForPerson(person.getId());
        assertEquals(1, leninInterests.size());
        assertTrue(leninInterests.contains(interest));
    }

    @Test
    public void testRemoveInterestForPerson() throws Exception {
        final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        PersonRepo personRepo = new PersonRepo(new ConnectionPoolWrapper(pool));
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
                .setSinRate(200)
        );
        Interest subbotnik = interestRepo.create(
            new Interest()
                .setTitle("Subbotnik")
                .setSinRate(0)
        );

        interestRepo.addInterestForPerson(revolution.getId(), person.getId());
        interestRepo.addInterestForPerson(subbotnik.getId(), person.getId());
        interestRepo.removeInterestForPerson(revolution.getId(), person.getId());

        List<Interest> leninInterests = interestRepo.getInterestsForPerson(person.getId());
        assertEquals(1, leninInterests.size());
        assertFalse(leninInterests.contains(revolution));
    }

    @Test
    public void testGetAll() throws Exception {
        List<Interest> interests = new ArrayList<>();
        interests.add(interestRepo.create(new Interest().setTitle("Communism").setSinRate(10)));
        interests.add(interestRepo.create(new Interest().setTitle("Subbotnik").setSinRate(0)));
        interests.add(interestRepo.create(new Interest().setTitle("Revolution").setSinRate(200)));

        List<Interest> allInterests = interestRepo.getAll();
        assertEquals(interests.size(), allInterests.size());
        assertTrue(allInterests.containsAll(interests));
    }

}