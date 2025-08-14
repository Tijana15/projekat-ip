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
    private String SQL_SELECT_AVAILABLE_BIKES = "SELECT v.id, v.model, v.picture, v.purchase_price, v.vehicle_state, v.manufacturer_id, e.max_range\n" +
            "FROM vehicle v\n" +
            "JOIN ebike e ON v.id = e.id\n" +
            "WHERE v.vehicle_state = 'AVAILABLE';\n";

    public List<EBike> selectAvailableBikes() {
        List<EBike> ebikes = new ArrayList<>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            ps = DBUtil.prepareStatement(conn, SQL_SELECT_AVAILABLE_BIKES, false);
            rs = ps.executeQuery();

            while (rs.next()) {
                EBike ebike = new EBike();
                ebike.setId(rs.getString("id"));
                ebike.setModel(rs.getString("model"));
                ebike.setPicture(rs.getString("picture"));
                ebike.setMaxRange(rs.getInt("max_range"));

                ebikes.add(ebike);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, ps, conn);
        }
        return ebikes;
    }

    public static EBike getEbikeById(String id) {
        EBike ebike = null;

        String SQL_SELECT_BY_ID =
                "SELECT v.id, v.model, v.picture, v.purchase_price, v.vehicle_state, v.manufacturer_id, e.max_range " +
                        "FROM vehicle v JOIN ebike e ON v.id = e.id " +
                        "WHERE v.id = ?";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            ps = DBUtil.prepareStatement(conn, SQL_SELECT_BY_ID, false, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                ebike = new EBike();
                ebike.setId(rs.getString("id"));
                ebike.setModel(rs.getString("model"));
                ebike.setPicture(rs.getString("picture"));
                ebike.setMaxRange(rs.getInt("max_range"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, ps, conn);
        }

        return ebike;
    }

}
