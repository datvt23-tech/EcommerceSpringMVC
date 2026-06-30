package com.example.controller;

import com.example.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.http.HttpSession;

@Controller
public class DashboardController {

    
    @GetMapping("/admin/dashboard")
    public String adminDashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("LOGIN_USER");
        
        if (user == null || !"ADMIN".equalsIgnoreCase(user.getRole())) {
            return "redirect:/login";
        }
        
       
        model.addAttribute("body", "/admin/dashboard.jsp");
        return "admin/layout/main";
    }

    // 2. CHỢ CHO STAFF (Nhân viên)
    @GetMapping("/staff/dashboard")
    public String staffDashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("LOGIN_USER");
        
        // Kiểm tra bảo mật: Nếu chưa đăng nhập HOẶC không phải staff -> Đuổi về trang login
        if (user == null || !"STAFF".equalsIgnoreCase(user.getRole())) {
            return "redirect:/login";
        }
        
        // Nạp file giao diện tương ứng theo cấu trúc của bạn: staff/staff-dashboard.jsp
        model.addAttribute("body", "/staff/dashboard.jsp");
        return "staff/layout/main";
    }

    // 3. CHỢ CHO CUSTOMER (Khách hàng)
    @GetMapping("/customer/dashboard")
    public String customerDashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("LOGIN_USER");
        
        if (user == null) {
            return "redirect:/login";
        }
        
        model.addAttribute("body", "/customer/dashboard.jsp");
        return "layout/main";
    }
}