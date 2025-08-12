package org.unibl.etf.promotionsapp.dao;

import org.unibl.etf.promotionsapp.db_util.DBUtil;
import org.unibl.etf.promotionsapp.dto.PostDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostDAO {
    public List<PostDTO> findAll() {
        List<PostDTO> posts = new ArrayList<>();
        String sql = "SELECT * FROM post ORDER BY created_at DESC";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            stmt = DBUtil.prepareStatement(conn, sql, false);
            rs = stmt.executeQuery();

            while (rs.next()) {
                PostDTO post = new PostDTO();
                post.setId(rs.getLong("id"));
                post.setTitle(rs.getString("title"));
                post.setContent(rs.getString("content"));
                post.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                posts.add(post);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, stmt, conn);
        }
        return posts;
    }

    public boolean save(PostDTO post) {
        String sql = "INSERT INTO posts (title, content, created_at) VALUES (?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DBUtil.getConnection();
            stmt = DBUtil.prepareStatement(conn, sql, false,
                    post.getTitle(),
                    post.getContent(),
                    Timestamp.valueOf(post.getCreatedAt()));
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(stmt, conn);
        }
        return false;
    }

    public List<PostDTO> searchByContent(String searchTerm) {
        List<PostDTO> posts = new ArrayList<>();
        String sql = "SELECT * FROM post WHERE title LIKE ? OR content LIKE ? ORDER BY created_at DESC";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            String searchPattern = "%" + searchTerm + "%";
            stmt = DBUtil.prepareStatement(conn, sql, false, searchPattern, searchPattern);
            rs = stmt.executeQuery();

            while (rs.next()) {
                PostDTO post = new PostDTO();
                post.setId(rs.getLong("id"));
                post.setTitle(rs.getString("title"));
                post.setContent(rs.getString("content"));
                post.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                posts.add(post);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, stmt, conn);
        }
        return posts;
    }

    public boolean delete(Long id) {
        String sql = "DELETE FROM post WHERE id = ?";
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
