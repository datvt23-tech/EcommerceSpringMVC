/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dao;

import com.example.model.User;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

/**
 *
 * @author votandat
 */
@Repository
public class UserDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public User checkLogin(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ? AND active = 1";
        try {
            // Dùng BeanPropertyRowMapper để tự động map cột trong DB vào class User
            return jdbcTemplate.queryForObject(sql,
                    new BeanPropertyRowMapper<>(User.class),
                    username,
                    password);
        } catch (EmptyResultDataAccessException e) {
            // Bắt lỗi nếu không tìm thấy dòng nào (Sai user hoặc pass)
            return null;
        }
    }

    // Hàm Thêm tài khoản mới (C - Create)
    public boolean registerUser(User user) {
        // Cập nhật câu SQL ăn khớp với cấu trúc bảng của bạn
        String sql = "INSERT INTO users (full_name, email, username, password, role, active) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            int rows = jdbcTemplate.update(sql,
                    user.getFullName(),
                    user.getEmail(),
                    user.getUsername(),
                    user.getPassword(),
                    user.getRole(),
                    user.isActive());
            return rows > 0; // Trả về true nếu Insert thành công
        } catch (DuplicateKeyException | EmptyResultDataAccessException e) {
            // Bắt lỗi nếu vi phạm ràng buộc UNIQUE của cột username trong MySQL
            return false;
        }
    }

    // Hàm Đổi mật khẩu (U - Update)
    public boolean updatePassword(String username, String newPassword) {
        String sql = "UPDATE users SET password = ? WHERE username = ?";
        try {
            int rowsAffected = jdbcTemplate.update(sql, newPassword, username);
            return rowsAffected > 0; // Trả về true nếu Update thành công (tìm thấy user)
        } catch (DuplicateKeyException | EmptyResultDataAccessException e) {
            return false;
        }
    }

    public List<User> getAllUsers() {
        String sql = "SELECT * FROM users ORDER BY role, username";
        try {
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
        } catch (DataAccessException e) {
            return Collections.emptyList();
        }
    }

    public boolean updateUserRole(int id, String role) {
        String sql = "UPDATE users SET role = ? WHERE id = ?";
        try {
            return jdbcTemplate.update(sql, role, id) > 0;
        } catch (DuplicateKeyException | EmptyResultDataAccessException e) {
            return false;
        }
    }

    public boolean updateUserActive(int id, boolean active) {
        String sql = "UPDATE users SET active = ? WHERE id = ?";
        try {
            return jdbcTemplate.update(sql, active, id) > 0;
        } catch (DuplicateKeyException | EmptyResultDataAccessException e) {
            return false;
        }
    }

    public User findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";

        try {
            return jdbcTemplate.queryForObject(sql,
                    new BeanPropertyRowMapper<>(User.class),
                    username);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    //  check trùng username
    public boolean existsByUsername(String username) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
        try {
            Integer count = jdbcTemplate.queryForObject(sql, Integer.class, username);
            return count != null && count > 0;
        } catch (DataAccessException e) {
            return false;
        }
    }

    public boolean createUser(User user) {
        return registerUser(user);
    }
}
