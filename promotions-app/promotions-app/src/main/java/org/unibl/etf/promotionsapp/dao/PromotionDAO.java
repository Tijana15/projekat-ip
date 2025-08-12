package org.unibl.etf.promotionsapp.dao;

import org.unibl.etf.promotionsapp.db_util.DBUtil;
import org.unibl.etf.promotionsapp.dto.PromotionDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PromotionDAO {
    public List<PromotionDTO> findAll() {
        List<PromotionDTO> promotions = new ArrayList<>();
        String sql = "SELECT * FROM promotion ORDER BY creation_date DESC";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            stmt = DBUtil.prepareStatement(conn, sql, false);
            rs = stmt.executeQuery();

            while (rs.next()) {
                PromotionDTO promotion = new PromotionDTO();
                promotion.setId(rs.getLong("id"));
                promotion.setTitle(rs.getString("title"));
                promotion.setDescription(rs.getString("description"));
                promotion.setStartDate(rs.getTimestamp("creation_date").toLocalDateTime());
                promotion.setEndDate(rs.getTimestamp("end_date").toLocalDateTime());
                promotions.add(promotion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, stmt, conn);
        }
        return promotions;
    }

    public boolean save(PromotionDTO promotion) {
        String sql = "INSERT INTO promotion (title, description, creation_date, end_date) VALUES (?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DBUtil.getConnection();
            stmt = DBUtil.prepareStatement(conn, sql, false,
                    promotion.getTitle(),
                    promotion.getDescription(),
                    Timestamp.valueOf(promotion.getStartDate()),
                    Timestamp.valueOf(promotion.getEndDate()));
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(stmt, conn);
        }
        return false;
    }

    public List<PromotionDTO> searchByContent(String searchTerm) {
        List<PromotionDTO> promotions = new ArrayList<>();
        String sql = "SELECT * FROM promotion WHERE title LIKE ? OR description LIKE ? ORDER BY creation_date DESC";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            String searchPattern = "%" + searchTerm + "%";
            stmt = DBUtil.prepareStatement(conn, sql, false, searchPattern, searchPattern);
            rs = stmt.executeQuery();

            while (rs.next()) {
                PromotionDTO promotion = new PromotionDTO();
                promotion.setId(rs.getLong("id"));
                promotion.setTitle(rs.getString("title"));
                promotion.setDescription(rs.getString("description"));
                promotion.setStartDate(rs.getTimestamp("creation_date").toLocalDateTime());
                promotion.setEndDate(rs.getTimestamp("end_date").toLocalDateTime());
                promotions.add(promotion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, stmt, conn);
        }
        return promotions;
    }

    public boolean delete(Long id) {
        String sql = "DELETE FROM promotion WHERE id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DBUtil.getConnection();
            stmt = DBUtil.prepareStatement(conn, sql, false, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(stmt, conn);
        }
        return false;
    }
}
