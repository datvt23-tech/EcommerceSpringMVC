/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.controller;

/**
 *
 * @author votandat
 */
import com.example.model.CartItem;
import com.example.model.User;
import com.example.service.OrderService;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OrderController {

    private static final String[] ORDER_STATUSES = {"Đang xử lý", "Đã xác nhận", "Đóng gói", "Đang giao", "Đã giao", "Đã hủy"};

    @Autowired
    private OrderService orderService;

    private User getLoggedInUser(HttpSession session) {
        return (User) session.getAttribute("LOGIN_USER");
    }

    private boolean hasRole(HttpSession session, String role) {
        User user = getLoggedInUser(session);
        return user != null && role.equals(user.getRole());
    }

    private boolean canOperate(HttpSession session) {
        return hasRole(session, "STAFF") || hasRole(session, "ADMIN");
    }

    private boolean isAllowedStatus(String status) {
        for (String allowedStatus : ORDER_STATUSES) {
            if (allowedStatus.equals(status)) {
                return true;
            }
        }
        return false;
    }

    @PostMapping("/checkout")
    public String checkout(@RequestParam("customerName") String customerName,
            @RequestParam("phone") String phone,
            @RequestParam("address") String address,
            HttpSession session,
            Model model) {
        User user = getLoggedInUser(session);
        if (user == null) {
            session.setAttribute("REDIRECT_AFTER_LOGIN", "/cart");
            return "redirect:/login";
        }
        if (canOperate(session)) {
            return "redirect:/dashboard";
        }

        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            model.addAttribute("error", "Giỏ hàng đang trống.");
            return "redirect:/cart";
        }

        double total = 0;
        for (CartItem item : cart) {
            total += item.getProduct().getPrice() * item.getQuantity();
        }

        orderService.createOrder(user.getId(), customerName, phone, address, cart);
        session.removeAttribute("cart");
        return "redirect:/orders";
    }

    @GetMapping("/orders")
    public String myOrders(HttpSession session, Model model) {
        User user = getLoggedInUser(session);
        if (user == null) {
            return "redirect:/login";
        }
        if (canOperate(session)) {
            return "redirect:/dashboard";
        }

        model.addAttribute("orders", orderService.getOrdersByUserId(user.getId()));
        model.addAttribute("body", "/customer/orders.jsp");
        return "layout/main";
    }

    @GetMapping("/staff/orders")
    public String staffOrders(HttpSession session, Model model) {
        if (!canOperate(session)) {
            return getLoggedInUser(session) == null ? "redirect:/login" : "redirect:/";
        }

        model.addAttribute("orders", orderService.getAllOrders());
        model.addAttribute("statuses", ORDER_STATUSES);
        model.addAttribute("body", "/staff/orders.jsp");
        return "staff/layout/main";
    }

    @PostMapping("/staff/orders/update-status")
    public String updateStatus(@RequestParam("id") int id,
            @RequestParam("status") String status,
            HttpSession session,
            Model model) {
        if (!canOperate(session)) {
            return getLoggedInUser(session) == null ? "redirect:/login" : "redirect:/";
        }
        if (!isAllowedStatus(status)) {
            model.addAttribute("error", "Trạng thái đơn hàng không hợp lệ.");
            return staffOrders(session, model);
        }

        if (orderService.updateOrderStatus(id, status)) {
            model.addAttribute("success", "Cập nhật trạng thái đơn hàng thành công.");
        } else {
            model.addAttribute("error", "Không thể cập nhật trạng thái đơn hàng.");
        }
        return staffOrders(session, model);
    }
    @GetMapping("/admin/orders")
    public String adminOrders(HttpSession session, Model model) {
        if (!canOperate(session)) {
            return getLoggedInUser(session) == null ? "redirect:/login" : "redirect:/";
        }

        model.addAttribute("orders", orderService.getAllOrders());
        model.addAttribute("statuses", ORDER_STATUSES);
        model.addAttribute("body", "/admin/orders.jsp");
        return "admin/layout/main";
    }
}
