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
import com.example.model.Order;
import com.example.model.User;
import com.example.service.OrderService;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OrderController {

    private static final String[] ORDER_STATUSES = {"Đang xử lý", "Đã xác nhận", "Đóng gói", "Đang giao", "Đã giao", "Đã hủy"};

    @Autowired
    private OrderService orderService;

    private User getLoggedInUser(HttpSession session) {
        try {
            return (User) session.getAttribute("LOGIN_USER");
        } catch (Exception e) {
            return null;
        }
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
        try {
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
                session.setAttribute("cartError", "Giỏ hàng đang trống.");
                return "redirect:/cart";
            }

            int orderId = orderService.createOrder(user.getId(), customerName, phone, address, cart);
            if (orderId <= 0) {
                session.setAttribute("cartError", "Không thể tạo đơn hàng. Vui lòng thử lại.");
                return "redirect:/cart";
            }
            session.removeAttribute("cart");
            return "redirect:/orders";
        } catch (Exception e) {
            session.setAttribute("cartError", "Đã xảy ra lỗi khi thanh toán giỏ hàng.");
            return "redirect:/cart";
        }
    }

    @GetMapping("/orders")
    public String myOrders(HttpSession session, Model model) {
        try {
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
        } catch (Exception e) {
            model.addAttribute("error", "Không thể tải danh sách đơn hàng.");
            model.addAttribute("body", "/customer/orders.jsp");
            return "layout/main";
        }
    }

    @GetMapping("/staff/orders")
    public String staffOrders(HttpSession session, Model model) {
        try {
            if (!canOperate(session)) {
                return getLoggedInUser(session) == null ? "redirect:/login" : "redirect:/";
            }
            model.addAttribute("orders", orderService.getAllOrders());
            model.addAttribute("statuses", ORDER_STATUSES);
            model.addAttribute("body", "/staff/orders.jsp");
            return "staff/layout/main";
        } catch (Exception e) {
            model.addAttribute("error", "Không thể tải danh sách đơn hàng.");
            model.addAttribute("statuses", ORDER_STATUSES);
            model.addAttribute("body", "/staff/orders.jsp");
            return "staff/layout/main";
        }
    }

    @PostMapping("/staff/orders/update-status")
    public String updateStatus(@RequestParam("id") int id,
            @RequestParam("status") String status,
            HttpSession session,
            Model model) {
        try {
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
        } catch (Exception e) {
            model.addAttribute("error", "Đã xảy ra lỗi khi cập nhật đơn hàng.");
            return staffOrders(session, model);
        }
    }

    @GetMapping("/admin/orders")
    public String adminOrders(HttpSession session, Model model) {
        try {
            if (!canOperate(session)) {
                return getLoggedInUser(session) == null ? "redirect:/login" : "redirect:/";
            }
            model.addAttribute("orders", orderService.getAllOrders());
            model.addAttribute("statuses", ORDER_STATUSES);
            model.addAttribute("body", "/admin/orders.jsp");
            return "admin/layout/main";
        } catch (Exception e) {
            model.addAttribute("error", "Không thể tải danh sách đơn hàng.");
            model.addAttribute("statuses", ORDER_STATUSES);
            model.addAttribute("body", "/admin/orders.jsp");
            return "admin/layout/main";
        }
    }

    @PostMapping("/orders/cancel/{id}")
    public String cancelOrder(@PathVariable int id,
            HttpSession session,
            Model model) {

        try {

            User user = (User) session.getAttribute("LOGIN_USER");

            if (user == null) {
                return "redirect:/login";
            }

            Order order = orderService.getOrderById(id);

            if (order == null) {
                return "redirect:/orders";
            }

            // Không cho hủy đơn của người khác
            if (order.getUserId() != user.getId()) {
                return "redirect:/orders";
            }

            // Chỉ cho hủy khi chưa thanh toán và đang chờ xử lý
            if (!"PENDING".equals(order.getStatus())
                    || !"PENDING".equals(order.getPaymentStatus())) {

                return "redirect:/orders";
            }

            boolean success = orderService.cancelOrder(id);

            if (!success) {
                model.addAttribute("error",
                        "Không thể hủy đơn hàng.");
            }

            return "redirect:/orders";

        } catch (Exception e) {

            e.printStackTrace(); // Debug

            model.addAttribute("error",
                    "Đã xảy ra lỗi khi hủy đơn hàng.");

            return "redirect:/orders";
        }
    }

}
