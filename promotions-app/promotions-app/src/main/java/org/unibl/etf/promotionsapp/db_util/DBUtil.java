package org.unibl.etf.promotionsapp.db_util;

import java.sql.*;

public class DBUtil {

    private static ConnectionPool connectionPool = ConnectionPool.getInstance();

    public static PreparedStatement prepareStatement(Connection c, String sql,
                                                     boolean retGenKeys, Object... values) throws SQLException {

        PreparedStatement ps = c.prepareStatement(sql,
                retGenKeys ? Statement.RETURN_GENERATED_KEYS
                        : Statement.NO_GENERATED_KEYS);

        for (int i = 0; i < values.length; i++)
            ps.setObject(i + 1, values[i]);

        return ps;
    }

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Connection c = connectionPool.checkOut();
        return c;
    }

    public static void close(Connection c) {
        if (c != null)
            connectionPool.checkIn(c);
    }

    public static void close(Statement s) {
        if (s != null)
            try {
                s.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    public static void close(ResultSet rs) {
        if (rs != null)
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    public static void close(ResultSet rs, Statement s, Connection c) {
        close(rs);
        close(s);
        close(c);
    }

    public static void close(Statement s, Connection c) {
        close(s);
        close(c);
    }
}