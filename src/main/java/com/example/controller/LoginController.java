/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.controller;

import com.example.model.User;
import com.example.service.UserService;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private UserService userService; // BẢN CHẤT: Controller giờ chỉ gọi Service, KHÔNG gọi trực tiếp DAO nữa

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("body", "login.jsp");
        return "layout/main";
    }

    @PostMapping("/login")
    public String handleLogin(@RequestParam String username,
            @RequestParam String password,
            HttpSession session,
            Model model) {
        try {
            User user = userService.checkLogin(username, password);
            if (user != null) {
                session.setAttribute("LOGIN_USER", user);
                String role = user.getRole() != null ? user.getRole().toUpperCase() : "CUSTOMER";
                switch (role) {
                    case "ADMIN":
                        return "redirect:/admin/dashboard";
                    case "STAFF":
                        return "redirect:/staff/dashboard";
                    default:
                        return "redirect:/customer/dashboard";
                }
            }
            model.addAttribute("error", "Tên đăng nhập hoặc mật khẩu không chính xác!");
        } catch (Exception e) {
            model.addAttribute("error", "Đã xảy ra lỗi khi đăng nhập. Vui lòng thử lại sau.");
        }
        model.addAttribute("body", "/login.jsp");
        return "layout/main";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("body", "/register.jsp");
        return "layout/main";
    }

    @PostMapping("/register")
    public String processRegister(@RequestParam("fullName") String fullName,
            @RequestParam("email") String email,
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("confirmPassword") String confirmPassword,
            Model model) {
        try {
            User newUser = new User();
            newUser.setFullName(fullName);
            newUser.setEmail(email);
            newUser.setUsername(username);
            newUser.setPassword(password);

            String result = userService.register(newUser, confirmPassword);
            if ("SUCCESS".equals(result)) {
                model.addAttribute("success", "Đăng ký thành công! Vui lòng đăng nhập.");
                model.addAttribute("body", "/login.jsp");
                return "layout/main";
            } else if ("PASSWORD_NOT_MATCH".equals(result)) {
                model.addAttribute("error", "Mật khẩu xác nhận không khớp!");
            } else {
                model.addAttribute("error", "Đăng ký thất bại! Tên đăng nhập đã tồn tại.");
            }
        } catch (Exception e) {
            model.addAttribute("error", "Đã xảy ra lỗi khi đăng ký. Vui lòng thử lại sau.");
        }
        model.addAttribute("body", "/register.jsp");
        return "layout/main";
    }

    @GetMapping("/forgot-password")
    public String showForgotPasswordForm(Model model) {
        model.addAttribute("body", "/forgot-password.jsp");
        return "layout/main";
    }

    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam("username") String username,
            @RequestParam("newPassword") String newPassword,
            @RequestParam("confirmNewPassword") String confirmNewPassword,
            Model model) {
        try {
            String result = userService.resetPassword(username, newPassword, confirmNewPassword);
            if ("SUCCESS".equals(result)) {
                model.addAttribute("success", "Đổi mật khẩu thành công! Vui lòng đăng nhập.");
                model.addAttribute("body", "/login.jsp");
                return "layout/main";
            } else if ("PASSWORD_NOT_MATCH".equals(result)) {
                model.addAttribute("error", "Mật khẩu mới không khớp nhau!");
            } else {
                model.addAttribute("error", "Không tìm thấy tên tài khoản này trong hệ thống!");
            }
        } catch (Exception e) {
            model.addAttribute("error", "Đã xảy ra lỗi khi đổi mật khẩu. Vui lòng thử lại sau.");
        }
        model.addAttribute("body", "/forgot-password.jsp");
        return "layout/main";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        try {
            session.removeAttribute("LOGIN_USER");
        } catch (Exception e) {
            session.invalidate();
        }
        return "redirect:/login";
    }
}
