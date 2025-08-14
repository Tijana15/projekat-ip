package org.unibl.etf.clientapp.dao;

import org.unibl.etf.clientapp.db_util.DBUtil;
import org.unibl.etf.clientapp.dto.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    public User findByUsernameAndPassword(String username, String password) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        User user = null;
        try {
            conn = DBUtil.getConnection();
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
            stmt = DBUtil.prepareStatement(conn, SQL_SELECT_BY_USERNAME_AND_PASSWORD, false, username);
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
}
