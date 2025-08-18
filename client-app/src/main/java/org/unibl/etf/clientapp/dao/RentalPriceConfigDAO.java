package org.unibl.etf.clientapp.dao;

import org.unibl.etf.clientapp.db_util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class RentalPriceConfigDAO {

    public static double getPriceForCar() {
        String sql = "SELECT price FROM rental_price_config WHERE vehicle_type = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, "CAR");
            rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getDouble("price");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, stmt, conn);
        }
        return 0.0;
    }

    public static double getPriceForEBike() {
        String sql = "SELECT price FROM rental_price_config WHERE vehicle_type = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, "EBIKE");
            rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getDouble("price");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, stmt, conn);
        }
        return 0.0;
    }

    public static double getPriceForEScooter() {
        String sql = "SELECT price FROM rental_price_config WHERE vehicle_type = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, "ESCOOTER");
            rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getDouble("price");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, stmt, conn);
        }
        return 0.0;
    }

    public static double getPriceByVehicleType(String vehicleType) {
        if (vehicleType == null) {
            return 0.0;
        }

        switch (vehicleType.toLowerCase()) {
            case "car":
                return getPriceForCar();
            case "ebike":
                return getPriceForEBike();
            case "escooter":
                return getPriceForEScooter();
            default:
                return 0.0;
        }
    }

}

