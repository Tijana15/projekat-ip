package org.unibl.etf.clientapp.dao;

import org.unibl.etf.clientapp.db_util.DBUtil;
import org.unibl.etf.clientapp.dto.EScooter; // Obavezno importuj EScooter DTO

import java.util.List;
import java.util.ArrayList;
import java.sql.*;

public class EScooterDAO {

    public static List<EScooter> getAllAvailableEScooters() {
        List<EScooter> eScooters = new ArrayList<>();
        String sql = "SELECT " +
                "    v.id, v.model, v.purchase_price, v.vehicle_state, " +
                "    v.mapx, v.mapy, v.picture, m.name AS manufacturer_name , " +
                "    es.max_speed " +
                "FROM " +
                "    vehicle v " +
                "INNER JOIN " +
                "    escooter es ON v.id = es.id " +
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
                EScooter eScooter = new EScooter();
                eScooter.setId(rs.getString("id"));
                eScooter.setManufacturer(rs.getString("manufacturer_name"));
                eScooter.setModel(rs.getString("model"));
                eScooter.setMaxSpeed(rs.getInt("max_speed"));
                eScooter.setPicture(rs.getString("picture"));
                eScooter.setMapX(rs.getDouble("mapx"));
                eScooter.setMapY(rs.getDouble("mapy"));
                eScooters.add(eScooter);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, stmt, conn);
        }

        return eScooters;
    }

}
