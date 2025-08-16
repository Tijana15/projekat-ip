package org.unibl.etf.clientapp.dao;

import org.unibl.etf.clientapp.db_util.DBUtil;
import org.unibl.etf.clientapp.dto.Car;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarDAO {
    public static List<Car> getAllAvailableCars() {
        List<Car> cars = new ArrayList<>();
        String sql = "SELECT " +
                "    v.id, " +
                "    v.model, " +
                "    v.purchase_price, " +
                "    v.vehicle_state, " +
                "    v.mapx, " +
                "    v.mapy, " +
                "    v.picture, " +
                "    m.name AS manufacturer_name , " +
                "c.description " +
                "FROM " +
                "    vehicle v " +
                "INNER JOIN " +
                "    car c ON v.id = c.id " +
                "INNER JOIN " +
                "    manufacturer m ON v.manufacturer_id = m.id " +
                "WHERE " +
                "    v.vehicle_state = 'AVAILABLE'";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Car car = new Car();
                car.setId(rs.getString("id"));
                car.setManufacturer(rs.getString("manufacturer_name"));
                car.setModel(rs.getString("model"));
                car.setDescription(rs.getString("description"));
                car.setPicture(rs.getString("picture"));
                car.setMapX(rs.getDouble("mapx"));
                car.setMapY(rs.getDouble("mapy"));
                String vehicleState = rs.getString("vehicle_state");
                cars.add(car);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, stmt, conn);
        }

        return cars;
    }

    public static boolean setCarAvailabilityToRented(Long carId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        String sql = "UPDATE vehicle SET vehicle_state = ? WHERE id = ?";

        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, "RENTED");
            stmt.setLong(2, carId);
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

}

