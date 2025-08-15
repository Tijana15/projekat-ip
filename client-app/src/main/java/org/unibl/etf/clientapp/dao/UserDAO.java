package org.unibl.etf.clientapp.dao;

import org.unibl.etf.clientapp.db_util.DBUtil;
import org.unibl.etf.clientapp.dto.User;

import java.sql.*;

public class UserDAO {

    public static User findByUsernameAndPassword(String username, String password) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        User user = null;
        String SQL_SELECT_BY_USERNAME_AND_PASSWORD = "SELECT \n" +
                "    u.firstname,\n" +
                "    u.lastname,\n" +
                "    u.password,\n" +
                "    u.role,\n" +
                "    u.username,\n" +
                "    c.*\n" +
                "FROM user u\n" +
                "JOIN client c ON u.id = c.id\n" +
                "WHERE u.username = ? AND u.password = ?;";
        try {
            conn = DBUtil.getConnection();
            stmt = DBUtil.prepareStatement(conn, SQL_SELECT_BY_USERNAME_AND_PASSWORD, false, username);
            stmt.setString(1, username);
            stmt.setString(2, password);
            rs = stmt.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setId(rs.getLong("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                user.setFirstname(rs.getString("firstname"));
                user.setLastname(rs.getString("lastname"));
                user.setBlocked(rs.getBoolean("blocked"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, stmt, conn);
        }
        return null;

    }

    public static boolean existsByUsername(String username) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String sql = "SELECT COUNT(*) FROM user WHERE username = ?";

        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, stmt, conn);
        }
        return false;
    }

    public static boolean addNewClient(User user) {
        Connection conn = null;
        PreparedStatement stmtUser = null;
        PreparedStatement stmtClient = null;
        ResultSet generatedKeys = null;

        String sqlUser = "INSERT INTO user (firstname, lastname, password, role, username) VALUES (?, ?, ?, ?, ?)";
        String sqlClient = "INSERT INTO client (id, email, id_document, phone, blocked) VALUES (?, ?, ?, ?, ?)";

        try {
            conn = DBUtil.getConnection();

            conn.setAutoCommit(false);

            stmtUser = conn.prepareStatement(sqlUser, Statement.RETURN_GENERATED_KEYS);
            stmtUser.setString(1, user.getFirstname());
            stmtUser.setString(2, user.getLastname());
            stmtUser.setString(3, user.getPassword());
            stmtUser.setString(4, "CLIENT");
            stmtUser.setString(5, user.getUsername());

            int affectedRows = stmtUser.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Kreiranje korisnika nije uspjelo, nijedan red nije dodat u tabelu 'user'.");
            }

            generatedKeys = stmtUser.getGeneratedKeys();
            if (generatedKeys.next()) {
                long userId = generatedKeys.getLong(1);

                stmtClient = conn.prepareStatement(sqlClient);
                stmtClient.setLong(1, userId);
                stmtClient.setString(2, user.getEmail());
                stmtClient.setString(3, user.getId_document());
                stmtClient.setString(4, user.getPhone());
                stmtClient.setBoolean(5, false);

                stmtClient.executeUpdate();

                conn.commit();
                return true;

            } else {
                conn.rollback();
                return false;
            }

        } catch (SQLException e) {
            System.err.println("Greška prilikom registracije korisnika. Poništavanje transakcije.");
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            return false;
        } finally {

            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            DBUtil.close(generatedKeys, stmtUser, null);
            DBUtil.close(null, stmtClient, conn);
        }
    }

}
