package edu.anxolerd.inquisition.core.repo;


import edu.anxolerd.inquisition.core.connection.ConnectionPoolWrapper;
import edu.anxolerd.inquisition.core.entities.Case;
import edu.anxolerd.inquisition.core.entities.CaseStatus;
import edu.anxolerd.inquisition.core.entities.CaseVerdict;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.*;
import java.util.Date;


public class CaseRepo {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private final ConnectionPoolWrapper pool;

    private static final String SQL_CREATE = (
        "INSERT INTO \"case\" (" +
        "  suspect_person_id, date_opened, status, accuses, date_closed, verdict, sentence\n" +
        ") VALUES (?, ?, ?, ?, ?, ?, ?);"
    );
    private static final String SQL_GET_BY_ID = (
        "SELECT\n" +
        "  id, suspect_person_id, date_opened, status, accuses, date_closed, verdict, sentence\n" +
        "FROM \"case\" \n" +
        "WHERE id = ?\n" +
        "LIMIT 1;\n"
    );
    private static final String SQL_UPDATE = (
        "UPDATE \"case\"\n" +
        "SET\n" +
            "  suspect_person_id=?,\n" +
            "  date_opened=?,\n" +
            "  status=?,\n" +
            "  accuses=?,\n" +
            "  date_closed=?,\n" +
            "  verdict=?,\n" +
            "  sentence=?\n" +
        "WHERE id=?;"
    );
    private static final String SQL_DELETE = "DELETE FROM \"case\" WHERE id=?;";
    private static final String SQL_GET_ALL = (
        "SELECT\n" +
        "  id, suspect_person_id, date_opened, status, accuses, date_closed, verdict, sentence\n" +
        "FROM \"case\"\n" +
        "ORDER BY date_opened ASC;"
    );
    private static final String SQL_GET_BY_STATUS = (
        "SELECT\n" +
        "  id, suspect_person_id, date_opened, status, accuses, date_closed, verdict, sentence\n" +
        "FROM \"case\"\n" +
        "WHERE status=?\n" +
        "ORDER BY date_opened ASC;"
    );
    public CaseRepo(ConnectionPoolWrapper pool) {
        this.pool = pool;
    }

    public Case create(Case aCase) {
        Connection conn = pool.getConnection();

        Case returnVal = null;
        try (PreparedStatement stmt = conn.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setObject(1, aCase.getSuspectPersonId());
            stmt.setTimestamp(2, new Timestamp(aCase.getDateOpened().getTime()));
            stmt.setInt(3, aCase.getStatus().getPersistentValue());
            stmt.setString(4, aCase.getAccuses());
            stmt.setTimestamp(5,
                aCase.getDateClosed() != null
                ? new Timestamp(aCase.getDateClosed().getTime())
                : null
            );
            if (aCase.getVerdict() != null) {
                stmt.setInt(6, aCase.getVerdict().getPersistentValue());
            } else {
                stmt.setObject(6, null);
            }
            stmt.setString(7, aCase.getSentence());

            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                aCase.setId((UUID) rs.getObject(1));
                returnVal = aCase;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            pool.releaseConection(conn);
        }

        return returnVal;
    }

    public Case getById(UUID id) {
        Connection conn = pool.getConnection();

        Case returnVal = null;
        try (PreparedStatement stmt = conn.prepareStatement(SQL_GET_BY_ID)) {
            stmt.setObject(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                returnVal = fromResultSet(rs);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            pool.releaseConection(conn);
        }

        return returnVal;
    }

    public boolean update(Case aCase) {
        Connection conn = pool.getConnection();
        boolean returnVal = false;

        try (PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE)) {
            stmt.setObject(1, aCase.getSuspectPersonId());
            stmt.setTimestamp(2, new Timestamp(aCase.getDateOpened().getTime()));
            stmt.setInt(3, aCase.getStatus().getPersistentValue());
            stmt.setString(4, aCase.getAccuses());
            stmt.setTimestamp(5,
                aCase.getDateClosed() != null
                ? new Timestamp(aCase.getDateClosed().getTime())
                : null
            );
            if (aCase.getVerdict() != null) {
                stmt.setInt(6, aCase.getVerdict().getPersistentValue());
            } else {
                stmt.setObject(6, null);
            }
            stmt.setString(7, aCase.getSentence());
            stmt.setObject(8, aCase.getId());

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

    public List<Case> getAll() {
        Connection conn = pool.getConnection();
        List<Case> cases = new ArrayList<>();

        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(SQL_GET_ALL);
            while (rs.next()) {
                cases.add(fromResultSet(rs));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            pool.releaseConection(conn);
        }
        return cases;
    }

    public List<Case> getByStatus(CaseStatus status) {
        int db = status.getPersistentValue();
        Connection conn = pool.getConnection();
        List<Case> cases = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(SQL_GET_BY_STATUS)) {
            stmt.setInt(1, db);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                cases.add(fromResultSet(rs));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            pool.releaseConection(conn);
        }
        return cases;
    }

    private static Case fromResultSet(ResultSet rs) throws SQLException {
        return new Case()
            .setId((UUID) rs.getObject("id"))
            .setSuspectPersonId((UUID) rs.getObject("suspect_person_id"))
            .setAccuses(rs.getString("accuses"))
            .setDateOpened(new Date(rs.getTimestamp("date_opened").getTime()))
            .setStatus(CaseStatus.getByPersistentValue(rs.getInt("status")))
            .setDateClosed(
                rs.getTimestamp("date_closed") != null
                ? new Date(rs.getTimestamp("date_closed").getTime())
                : null
            )
            .setVerdict(
                rs.getObject("verdict") != null
                ? CaseVerdict.getByPersistentValue(rs.getInt("verdict"))
                : null
            )
            .setSentence(rs.getString("sentence"));
    }

}
