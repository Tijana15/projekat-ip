package org.unibl.etf.clientapp.dao;

import org.unibl.etf.clientapp.db_util.DBUtil;
import org.unibl.etf.clientapp.dto.InvoiceData;
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

    public static InvoiceData getInvoiceData(long rentalId) {
        InvoiceData data = null;
        String sql = "SELECT " +
                "    r.id AS rental_id, r.date_time, r.duration_seconds, r.price, " +
                "    r.startx, r.starty, r.endx, r.endy, r.vehicle_id, " +
                "    u.firstname, u.lastname, " +
                "    c.id_document, c.drivers_licence, " +
                "    v.model, " +
                "    m.name AS manufacturer_name " +
                "FROM " +
                "    rental r " +
                "INNER JOIN client c ON r.client_id = c.id " +
                "INNER JOIN user u ON c.id = u.id " +
                "INNER JOIN vehicle v ON r.vehicle_id = v.id " +
                "INNER JOIN manufacturer m ON v.manufacturer_id = m.id " +
                "WHERE r.id = ?";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, rentalId);
            rs = stmt.executeQuery();

            if (rs.next()) {
                data = new InvoiceData();

                data.setRentalId(rs.getLong("rental_id"));
                data.setStartDateTime(rs.getTimestamp("date_time").toLocalDateTime());
                data.setDurationSeconds(rs.getInt("duration_seconds"));
                data.setPrice(rs.getDouble("price"));
                data.setStartX(rs.getDouble("startx"));
                data.setStartY(rs.getDouble("starty"));
                data.setEndX(rs.getDouble("endx"));
                data.setEndY(rs.getDouble("endy"));

                data.setClientFirstname(rs.getString("firstname"));
                data.setClientLastname(rs.getString("lastname"));
                data.setClientIdDocument(rs.getString("id_document"));
                data.setClientDriversLicence(rs.getString("drivers_licence"));

                String vehicleModel = rs.getString("manufacturer_name") + " " + rs.getString("model");
                data.setVehicleModel(vehicleModel);

                String vehicleId = rs.getString("vehicle_id");
                if (vehicleId.startsWith("CAR")) {
                    data.setVehicleType("car");
                } else if (vehicleId.startsWith("EBIKE")) {
                    data.setVehicleType("ebike");
                } else if (vehicleId.startsWith("ESCOOTER")) {
                    data.setVehicleType("escooter");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, stmt, conn);
        }

        return data;
    }
}
