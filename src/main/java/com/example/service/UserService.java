/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.service;

import java.util.List;
import com.example.dao.UserDAO;
import com.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service // Đánh dấu cho Spring biết đây là tầng Service
public class UserService {

    enum UserStatus {
        SUCCESS,
        PASSWORD_NOT_MATCH,
        USERNAME_EXISTS,
        USER_NOT_FOUND
    }
    @Autowired
    private UserDAO userDAO; // Service gọi DAO để tương tác với DB

    // 1. Logic Đăng nhập
    public User checkLogin(String username, String password) {
        if (username == null || password == null
                || username.trim().isEmpty() || password.trim().isEmpty()) {
            return null;
        }
        return userDAO.checkLogin(username, password);
    }

    // 2. Logic Đăng ký (Trả về các chuỗi trạng thái để Controller xử lý giao diện)
    public String register(User user, String confirmPassword) {

        if (user == null) {
            return "ERROR";
        }

        try {
            if (user.getPassword() == null || !user.getPassword().equals(confirmPassword)) {
                return "PASSWORD_NOT_MATCH";
            }

            if (userDAO.existsByUsername(user.getUsername())) {
                return "USERNAME_EXISTS";
            }

            if (user.getRole() == null || user.getRole().trim().isEmpty()) {
                user.setRole("CUSTOMER");
            }
            user.setActive(true);

            boolean ok = userDAO.registerUser(user);

            return ok ? "SUCCESS" : "ERROR";
        } catch (Exception e) {
            return "ERROR";
        }
    }

    // 3. Logic Khôi phục mật khẩu
    public String resetPassword(String username, String newPassword, String confirmNewPassword) {

        if (username == null || username.trim().isEmpty()) {
            return "USER_NOT_FOUND";
        }

        try {
            if (newPassword == null || !newPassword.equals(confirmNewPassword)) {
                return "PASSWORD_NOT_MATCH";
            }

            User user = userDAO.findByUsername(username);
            if (user == null) {
                return "USER_NOT_FOUND";
            }

            boolean ok = userDAO.updatePassword(username, newPassword);

            return ok ? "SUCCESS" : "ERROR";
        } catch (Exception e) {
            return "ERROR";
        }

    }

    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    public boolean updateUserRole(int id, String role) {
        return userDAO.updateUserRole(id, role);
    }

    public boolean updateUserActive(int id, boolean active) {
        return userDAO.updateUserActive(id, active);
    }

    public boolean createUser(User user) {

        if (user.getRole() == null
                || user.getRole().trim().isEmpty()) {

            user.setRole("CUSTOMER");
        }

        user.setActive(true);

        try {
            return userDAO.createUser(user);
        } catch (Exception e) {
            return false;
        }
    }

}
