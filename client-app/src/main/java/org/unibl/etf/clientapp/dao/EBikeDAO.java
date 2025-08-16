package org.unibl.etf.clientapp.dao;

import org.unibl.etf.clientapp.db_util.DBUtil;
import org.unibl.etf.clientapp.dto.EBike;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EBikeDAO {

    public static List<EBike> getAllAvailableEBikes() {
        List<EBike> eBikes = new ArrayList<>();

        String sql = "SELECT " +
                "    v.id, v.model, v.purchase_price, v.vehicle_state, " +
                "    v.mapx, v.mapy, v.picture, m.name AS manufacturer_name, " +
                "    eb.max_range " +
                "FROM " +
                "    vehicle v " +
                "INNER JOIN " +
                "    ebike eb ON v.id = eb.id " +
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
                EBike eBike = new EBike();
                eBike.setId(rs.getString("id"));
                eBike.setManufacturer(rs.getString("manufacturer_name"));
                eBike.setModel(rs.getString("model"));
                eBike.setPicture(rs.getString("picture"));
                eBike.setMapX(rs.getDouble("mapx"));
                eBike.setMapY(rs.getDouble("mapy"));
                eBike.setMaxRange(rs.getInt("max_range"));

                eBikes.add(eBike);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, stmt, conn);
        }

        return eBikes;
    }
}
