package com.example.controller;

import com.example.model.User;
import com.example.service.UserService;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class AdminUserController {

    private static final String[] ALLOWED_ROLES = {"CUSTOMER", "STAFF", "ADMIN"};

    @Autowired
    private UserService userService;

    private User getLoggedInUser(HttpSession session) {
        try {
            return (User) session.getAttribute("LOGIN_USER");
        } catch (Exception ex) {
            return null;
        }
    }

    private boolean isAdmin(HttpSession session) {
        try {
            User user = getLoggedInUser(session);
            if (user == null) {
                return false;
            }
            return "ADMIN".equals(user.getRole());
        } catch (Exception ex) {
            return false;
        }

    }

    private boolean isAllowedRole(String role) {
        for (String allowedRole : ALLOWED_ROLES) {
            if (allowedRole.equals(role)) {
                return true;
            }
        }
        return false;
    }

    private String requireAdmin(HttpSession session) {
        return getLoggedInUser(session) == null ? "redirect:/login" : "redirect:/";
    }

    @GetMapping("/users")
    public String showUsers(HttpSession session, Model model) {

        if (!isAdmin(session)) {
            return requireAdmin(session);
        }

        model.addAttribute("users", userService.getAllUsers());

        model.addAttribute("body", "/admin/users.jsp");
        return "admin/layout/main";
    }

    @PostMapping("/users/create")

    public String createUser(@RequestParam("fullName") String fullName,
            @RequestParam("email") String email,
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("role") String role,
            HttpSession session,
            Model model) {

        if (!isAdmin(session)) {

            return requireAdmin(session);

        }

        User user = new User();

        user.setFullName(fullName);

        user.setEmail(email);

        user.setUsername(username);

        user.setPassword(password);

        user.setRole(role);

        user.setActive(true);

        String result = userService.register(user, password);

        if ("SUCCESS".equals(result)) {

            model.addAttribute("success", "Tạo tài khoản thành công.");

        } else {

            model.addAttribute("error", "Tạo thất bại: " + result);

        }

        return showUsers(session, model);

    }

    @PostMapping("/users/update-role")
    public String updateRole(@RequestParam("id") int id,
            @RequestParam("role") String role,
            HttpSession session,
            Model model) {
        if (!isAdmin(session)) {
            return requireAdmin(session);
        }

        if (!isAllowedRole(role)) {
            model.addAttribute("error", "Role không hợp lệ.");
            return "redirect:/admin/users";
        }

        if (userService.updateUserRole(id, role)) {
            model.addAttribute("success", "Cập nhật quyền tài khoản thành công.");
        } else {
            model.addAttribute("error", "Không thể cập nhật quyền tài khoản.");
        }
        return "redirect:/admin/users";
    }

    @PostMapping("/users/toggle-active")
    public String toggleActive(@RequestParam("id") int id,
            @RequestParam("active") boolean active,
            HttpSession session,
            Model model) {
        if (!isAdmin(session)) {
            return requireAdmin(session);
        }

        User currentUser = getLoggedInUser(session);
        if (currentUser != null && currentUser.getId() == id) {
            model.addAttribute("error", "Bạn không thể khóa chính tài khoản đang đăng nhập.");
            return "redirect:/admin/users";
        }

        if (userService.updateUserActive(id, active)) {
            model.addAttribute("success", active ? "Đã mở khóa tài khoản." : "Đã khóa tài khoản.");
        } else {
            model.addAttribute("error", "Không thể cập nhật trạng thái tài khoản.");
        }
        return "redirect:/admin/users";
    }

    @GetMapping("/users/add")
    public String showAddUserForm(HttpSession session, Model model) {

        if (!isAdmin(session)) {
            return requireAdmin(session);
        }

        model.addAttribute("user", new User());

        model.addAttribute("body", "/admin/users-form.jsp");
        return "admin/layout/main"; 
    }

    @PostMapping("/users/save")
    public String saveUser(
            @ModelAttribute User user,
            HttpSession session,
            Model model) {

        if (!isAdmin(session)) {
            return requireAdmin(session);
        }

        if (userService.createUser(user)) {
            return "redirect:/admin/users";
        }

        model.addAttribute("error", "Tạo user thất bại");
        model.addAttribute("body", "/admin/users-form.jsp");
        return "admin/layout/main"; 
    }

}
