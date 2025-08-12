package org.unibl.etf.promotionsapp.dao;

import org.unibl.etf.promotionsapp.db_util.DBUtil;
import org.unibl.etf.promotionsapp.dto.UserDTO;

import java.sql.*;

public class UserDAO {
    public UserDTO findByUsername(String username) {
        String sql = "SELECT * FROM user WHERE username = ? AND role = 'MANAGEMENT'";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            stmt = DBUtil.prepareStatement(conn, sql, false, username);
            rs = stmt.executeQuery();

            if (rs.next()) {
                UserDTO user = new UserDTO();
                user.setId(rs.getLong("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, stmt, conn);
        }
        return null;
    }

    public boolean authenticate(String username, String password) {
        UserDTO user = findByUsername(username);
        return user != null && user.getPassword().equals(password);
    }
}
