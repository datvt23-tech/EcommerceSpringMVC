package com.example.controller;

import com.example.model.User;
import com.example.service.OrderService;
import com.example.service.ProductService;
import com.example.service.SupportService;
import com.example.service.UserService;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private SupportService supportService;

    private User getLoggedInUser(HttpSession session) {
        try {
            return (User) session.getAttribute("LOGIN_USER");
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard(HttpSession session, Model model) {
        try {
            User user = getLoggedInUser(session);
            if (user == null || !"ADMIN".equalsIgnoreCase(user.getRole())) {
                return "redirect:/login";
            }
            model.addAttribute("totalProducts",
                    productService.getAllProducts().size());

            model.addAttribute("totalOrders",
                    orderService.getAllOrders().size());

            model.addAttribute("totalCustomers",
                    userService.getAllUsers().size());

            model.addAttribute("totalSupports",
                    supportService.getAllTickets().size());

            model.addAttribute("latestOrders",
                    orderService.getLatestOrders());
            
            model.addAttribute("latestProducts",
                    productService.getLatestProducts());

            model.addAttribute("latestSupports",
                    supportService.getLatestTickets());

            model.addAttribute("body", "/admin/dashboard.jsp");
            return "admin/layout/main";
        } catch (Exception e) {
            return "redirect:/login";
        }
    }

    @GetMapping("/staff/dashboard")
    public String staffDashboard(HttpSession session, Model model) {
        try {
            User user = getLoggedInUser(session);
            if (user == null || !"STAFF".equalsIgnoreCase(user.getRole())) {
                return "redirect:/login";
            }
            model.addAttribute("body", "/staff/dashboard.jsp");
            return "staff/layout/main";
        } catch (Exception e) {
            return "redirect:/login";
        }
    }

    @GetMapping("/customer/dashboard")
    public String customerDashboard(HttpSession session, Model model) {
        try {
            User user = getLoggedInUser(session);
            if (user == null) {
                return "redirect:/login";
            }
            model.addAttribute("body", "/customer/dashboard.jsp");
            return "layout/main";
        } catch (Exception e) {
            return "redirect:/login";
        }
    }
}
