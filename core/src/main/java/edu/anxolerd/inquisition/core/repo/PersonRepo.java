package edu.anxolerd.inquisition.core.repo;


import edu.anxolerd.inquisition.core.connection.ConnectionPoolWrapper;
import edu.anxolerd.inquisition.core.entities.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.UUID;

public class PersonRepo {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private final ConnectionPoolWrapper pool;

    private static final String SQL_CREATE = (
        "INSERT INTO person (first_name, middle_name, last_name, birth_date, death_date)\n" +
        "VALUES (?, ?, ?, ?, ?);"
    );
    private static final String SQL_GET_BY_ID = (
        "SELECT id, first_name, middle_name, last_name, birth_date, death_date\n" +
        "FROM person\n" +
        "WHERE id = ?\n" +
        "LIMIT 1;\n"
    );
    private static final String SQL_UPDATE = (
        "UPDATE person\n" +
        "SET first_name=?, middle_name=?, last_name=?, birth_date=?, death_date=?\n" +
        "WHERE id=?;"
    );
    private static final String SQL_DELETE = "DELETE FROM person WHERE id=?;";


    public PersonRepo(ConnectionPoolWrapper pool) {
        this.pool = pool;
    }

    public Person create(Person person) {
        Connection conn = pool.getConnection();

        Person returnVal = null;
        try (PreparedStatement stmt = conn.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, person.getFirstName());
            stmt.setString(2, person.getMiddleName());
            stmt.setString(3, person.getLastName());
            stmt.setDate(4, new Date(person.getBirthDate().getTime()));
            stmt.setDate(5, person.getDeathDate() != null
                                ? new Date(person.getDeathDate().getTime())
                                : null
            );

            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                person.setId((UUID) rs.getObject(1));
                returnVal = person;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            pool.releaseConection(conn);
        }

        return returnVal;
    }

    public Person getById(UUID id) {
        Connection conn = pool.getConnection();

        Person returnVal = null;
        try (PreparedStatement stmt = conn.prepareStatement(SQL_GET_BY_ID)) {
            stmt.setObject(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                returnVal = new Person()
                    .setId((UUID) rs.getObject("id"))
                    .setFirstName(rs.getString("first_name"))
                    .setMiddleName(rs.getString("middle_name"))
                    .setLastName(rs.getString("last_name"))
                    .setBirthDate(new java.util.Date(rs.getDate("birth_date").getTime()))
                    .setDeathDate(
                        rs.getDate("death_date") != null
                            ? new java.util.Date(rs.getDate("death_date").getTime())
                            : null
                    );
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            pool.releaseConection(conn);
        }

        return returnVal;
    }

    public boolean update(Person person) {
        Connection conn = pool.getConnection();
        boolean returnVal = false;

        try (PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE)) {
            stmt.setString(1, person.getFirstName());
            stmt.setString(2, person.getMiddleName());
            stmt.setString(3, person.getLastName());
            stmt.setDate(4, new Date(person.getBirthDate().getTime()));
            stmt.setDate(5, person.getDeathDate() != null
                ? new Date(person.getDeathDate().getTime())
                : null
            );
            stmt.setObject(6, person.getId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) { returnVal = true; }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            pool.releaseConection(conn);
        }

        return returnVal;
    }

    public boolean delete(UUID id) {
        Connection conn = pool.getConnection();
        boolean success = false;

        try (PreparedStatement stmt = conn.prepareStatement(SQL_DELETE)) {
            stmt.setObject(1, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) { success = true; }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            pool.releaseConection(conn);
        }

        return success;
    }

    public int getSinRate(UUID person_id) {
        Connection conn = pool.getConnection();
        int sinRate;

        try (CallableStatement calcSinRateProc = conn.prepareCall("{ ? = call calc_sin_rate( ? ) }")) {
            calcSinRateProc.registerOutParameter(1, Types.INTEGER);
            calcSinRateProc.setObject(2, person_id);
            calcSinRateProc.execute();
            sinRate = calcSinRateProc.getInt(1);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            pool.releaseConection(conn);
        }
        return sinRate;
    }
}
