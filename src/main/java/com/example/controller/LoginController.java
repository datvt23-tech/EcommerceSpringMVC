/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.controller;

import com.example.model.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private UserService userService; // BẢN CHẤT: Controller giờ chỉ gọi Service, KHÔNG gọi trực tiếp DAO nữa

    // ==========================================
    // 1. LUỒNG ĐĂNG NHẬP (LOGIN)
    // ==========================================
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

        // Gọi Service kiểm tra đăng nhập
        User user = userService.checkLogin(username, password);

        if (user != null) {
            // 1. Lưu thông tin người dùng vào Session để giữ trạng thái đăng nhập
            session.setAttribute("LOGIN_USER", user);

            // 2. Lấy Role ra và chuyển chữ thành viết hoa để so sánh chính xác
            String role = user.getRole().toUpperCase();

            if (null == role) {
                return "redirect:/customer/dashboard"; // Hoặc trang chủ tùy ý bạn
            } else // 3. Điều hướng "Đúng người đúng việc"
            switch (role) {
                case "ADMIN":
                    return "redirect:/admin/dashboard";
                case "STAFF":
                    return "redirect:/staff/dashboard";
                default:
                    return "redirect:/customer/dashboard"; // Hoặc trang chủ tùy ý bạn
            }

        } else {
            // Đăng nhập thất bại, trả về lỗi
            model.addAttribute("error", "Tên đăng nhập hoặc mật khẩu không chính xác!");
            model.addAttribute("body", "/login.jsp");
            return "layout/main"; // Tên file login.jsp bên ngoài views
        }
    }

    // ==========================================
    // 2. LUỒNG ĐĂNG KÝ (REGISTER)
    // ==========================================
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

        // Đóng gói dữ liệu từ Form vào đối tượng Model
        User newUser = new User();
        newUser.setFullName(fullName);
        newUser.setEmail(email);
        newUser.setUsername(username);
        newUser.setPassword(password);

        // Đẩy xuống Service xử lý và nhận về kết quả dạng String trạng thái
        String result = userService.register(newUser, confirmPassword);

        if ("SUCCESS".equals(result)) {
            model.addAttribute("success", "Đăng ký thành công! Vui lòng đăng nhập.");
            model.addAttribute("body", "/login.jsp"); // Đẩy sang trang đăng nhập luôn
            return "layout/main";
        } else if ("PASSWORD_NOT_MATCH".equals(result)) {
            model.addAttribute("error", "Mật khẩu xác nhận không khớp!");
            model.addAttribute("body", "/register.jsp");
            return "layout/main";
        } else {
            model.addAttribute("error", "Đăng ký thất bại! Tên đăng nhập đã tồn tại.");
            model.addAttribute("body", "/register.jsp");
            return "layout/main";
        }
    }

    // ==========================================
    // 3. LUỒNG QUÊN MẬT KHẨU (FORGOT PASSWORD)
    // ==========================================
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

        String result = userService.resetPassword(username, newPassword, confirmNewPassword);

        if ("SUCCESS".equals(result)) {
            model.addAttribute("success", "Đổi mật khẩu thành công! Vui lòng đăng nhập.");
            model.addAttribute("body", "/login.jsp");
            return "layout/main";
        } else if ("PASSWORD_NOT_MATCH".equals(result)) {
            model.addAttribute("error", "Mật khẩu mới không khớp nhau!");
            model.addAttribute("body", "/forgot-password.jsp");
            return "layout/main";
        } else {
            model.addAttribute("error", "Không tìm thấy tên tài khoản này trong hệ thống!");
            model.addAttribute("body", "/forgot-password.jsp");
            return "layout/main";
        }
    }

    // ==========================================
    // 4. ĐĂNG XUẤT (LOGOUT)
    // ==========================================
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("LOGIN_USER");
        return "redirect:/login";
    }

}
