package edu.anxolerd.inquisition.core.repo;


import edu.anxolerd.inquisition.core.connection.ConnectionPoolWrapper;
import edu.anxolerd.inquisition.core.entities.Interest;
import edu.anxolerd.inquisition.core.entities.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InterestRepo {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private final ConnectionPoolWrapper pool;

    private static final String SQL_CREATE = "INSERT INTO interest (title, description, sin_rate) VALUES (?, ?, ?);";
    private static final String SQL_GET_BY_ID = (
        "SELECT id, title, description, sin_rate\n" +
        "FROM interest\n" +
        "WHERE id = ?\n" +
        "LIMIT 1;\n"
    );
    private static final String SQL_UPDATE = "UPDATE interest SET title=?, description=?, sin_rate=? WHERE id=?;";
    private static final String SQL_DELETE = "DELETE FROM interest WHERE id=?;";

    private static final String SQL_GET_INTERESTS_FOR_PERSON = (
        "SELECT\n" +
        "  interest.id,\n" +
        "  interest.title,\n" +
        "  interest.description,\n" +
        "  interest.sin_rate\n" +
        "FROM m2m_person_interest\n" +
        "  JOIN interest ON interest.id=m2m_person_interest.interest_id\n" +
        "WHERE m2m_person_interest.person_id=?;"
    );
    private static final String SQL_ADD_INTEREST_FOR_PERSON = (
        "INSERT INTO m2m_person_interest (person_id, interest_id)\n" +
        "VALUES (?, ?);"
    );
    private static final String SQL_REMOVE_INTEREST_FOR_PERSON = (
        "DELETE FROM m2m_person_interest\n" +
        "WHERE person_id = ? AND interest_id = ?;"
    );
    private static final String SQL_GET_ALL = "SELECT id, title, description, sin_rate FROM interest;";

    public InterestRepo(ConnectionPoolWrapper pool) {
        this.pool = pool;
    }

    public Interest create(Interest interest) {
        Connection conn = pool.getConnection();

        Interest returnVal = null;
        try (PreparedStatement stmt = conn.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, interest.getTitle());
            stmt.setString(2, interest.getDescription());
            stmt.setInt(3, interest.getSinRate());

            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                interest.setId((UUID) rs.getObject(1));
                returnVal = interest;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            pool.releaseConection(conn);
        }

        return returnVal;
    }

    public Interest getById(UUID id) {
        Connection conn = pool.getConnection();

        Interest returnVal = null;
        try (PreparedStatement stmt = conn.prepareStatement(SQL_GET_BY_ID)) {
            stmt.setObject(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                returnVal = new Interest()
                    .setId((UUID) rs.getObject("id"))
                    .setTitle(rs.getString("title"))
                    .setDescription(rs.getString("description"))
                    .setSinRate(rs.getInt("sin_rate"));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            pool.releaseConection(conn);
        }

        return returnVal;
    }

    public boolean update(Interest interest) {
        Connection conn = pool.getConnection();
        boolean returnVal = false;

        try (PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE)) {
            stmt.setString(1, interest.getTitle());
            stmt.setString(2, interest.getDescription());
            stmt.setInt(3, interest.getSinRate());
            stmt.setObject(4, interest.getId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) { returnVal = true; }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
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
        } finally {
            pool.releaseConection(conn);
        }

        return success;
    }

    public List<Interest> getInterestsForPerson(UUID personId) {
        Connection conn = pool.getConnection();
        List<Interest> interests = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(SQL_GET_INTERESTS_FOR_PERSON)) {
            stmt.setObject(1, personId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                interests.add(
                    new Interest()
                    .setId((UUID) rs.getObject("id"))
                    .setTitle(rs.getString("title"))
                    .setDescription(rs.getString("description"))
                    .setSinRate(rs.getInt("sin_rate"))
                );
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            pool.releaseConection(conn);
        }
        return interests;
    }

    public void addInterestForPerson(UUID interestId, UUID personId) {
        Connection conn = pool.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(SQL_ADD_INTEREST_FOR_PERSON)) {
            stmt.setObject(1, personId);
            stmt.setObject(2, interestId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            pool.releaseConection(conn);
        }
    }

    public void removeInterestForPerson(UUID interestId, UUID personId) {
        Connection conn = pool.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(SQL_REMOVE_INTEREST_FOR_PERSON)) {
            stmt.setObject(1, personId);
            stmt.setObject(2, interestId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            pool.releaseConection(conn);
        }
    }

    public List<Interest> getAll() {
        Connection conn = pool.getConnection();
        List<Interest> interests = new ArrayList<>();

        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(SQL_GET_ALL);
            while (rs.next()) {
                interests.add(
                    new Interest()
                        .setId((UUID) rs.getObject("id"))
                        .setTitle(rs.getString("title"))
                        .setDescription(rs.getString("description"))
                        .setSinRate(rs.getInt("sin_rate"))
                );
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            pool.releaseConection(conn);
        }
        return interests;
    }
}
