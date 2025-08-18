package org.unibl.etf.clientapp.dao;

import org.unibl.etf.clientapp.db_util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class VehicleDAO {
    public static boolean setVehicleAvailabilityToRented(String carId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        String sql = "UPDATE vehicle SET vehicle_state = ? WHERE id = ?";

        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, "RENTED");
            stmt.setString(2, carId);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            System.err.println("Error occurred while setting car state to rented" + carId);
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.close(null, stmt, conn);
        }
    }

    public static boolean setVehicleAvailabilityToAvailable(String carId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        String sql = "UPDATE vehicle SET vehicle_state = ? WHERE id = ?";

        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, "AVAILABLE");
            stmt.setString(2, carId);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            System.err.println("Error occurred while setting car state to available" + carId);
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.close(null, stmt, conn);
        }
    }

    public static boolean updateVehicleCoordinates(String vehicleId, double mapX, double mapY) {
        String sql = "UPDATE vehicle SET mapx = ?, mapy = ? WHERE id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setDouble(1, mapX);
            stmt.setDouble(2, mapY);
            stmt.setString(3, vehicleId);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error occurred while updating vehicle coordinates on map" + vehicleId);
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.close(null, stmt, conn);
        }
    }
}
