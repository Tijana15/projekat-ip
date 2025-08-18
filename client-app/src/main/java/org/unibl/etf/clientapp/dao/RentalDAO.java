package org.unibl.etf.clientapp.dao;

import org.unibl.etf.clientapp.db_util.DBUtil;
import org.unibl.etf.clientapp.dto.Rental;

import java.sql.*;

public class RentalDAO {
    public static long startRental(Rental rental) {
        String sql = "INSERT INTO rental (client_id, vehicle_id, date_time, startx, starty, duration_seconds, active) VALUES (?, ?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet generatedKeys = null;
        long rentalId = -1;

        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setLong(1, rental.getClientId());
            stmt.setString(2, rental.getVehicleId());
            stmt.setTimestamp(3, Timestamp.valueOf(rental.getDateTime()));
            stmt.setDouble(4, rental.getStartX());
            stmt.setDouble(5, rental.getStartY());
            stmt.setInt(6, 0);
            stmt.setBoolean(7, rental.isActive());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    rentalId = generatedKeys.getLong(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(generatedKeys, stmt, conn);
        }
        return rentalId;
    }

    public static void endRental(long rentalId, int durationInSeconds, double finalPrice, double endMapX, double endMapY) {
        String sql = "UPDATE rental SET duration_seconds = ?, price = ?, endx = ?, endy = ?, active=? WHERE id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, durationInSeconds);
            stmt.setDouble(2, finalPrice);
            stmt.setDouble(3, endMapX);
            stmt.setDouble(4, endMapY);
            stmt.setBoolean(5, false);
            stmt.setLong(6, rentalId);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(null, stmt, conn);
        }
    }
}
