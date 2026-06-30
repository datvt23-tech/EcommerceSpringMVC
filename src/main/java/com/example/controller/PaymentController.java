package com.example.controller;

import com.example.model.Order;
import com.example.model.User;
import com.example.service.OrderService;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller xử lý luồng thanh toán đơn hàng.
 * Hỗ trợ hai phương thức: COD (thanh toán khi nhận hàng) và CARD (thẻ tín dụng/ghi nợ).
 *
 * @author trinhthuytrang
 */
@Controller
public class PaymentController {

    @Autowired
    private OrderService orderService;

    /** Lấy thông tin người dùng đang đăng nhập từ session. */
    private User getLoggedInUser(HttpSession session) {
        try {
            return (User) session.getAttribute("LOGIN_USER");
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping("/payment/{orderId}")
    public String showPaymentPage(@PathVariable int orderId, HttpSession session, Model model) {
        try {
            User user = getLoggedInUser(session);
            if (user == null) {
                return "redirect:/login";
            }

            Order order = orderService.getOrderById(orderId);
            if (order == null || order.getUserId() != user.getId()) {
                return "redirect:/orders";
            }
            if ("PAID".equals(order.getPaymentStatus()) || "FAILED".equals(order.getPaymentStatus())) {
                return "redirect:/orders";
            }

            model.addAttribute("order", order);
            return "payment";
        } catch (Exception e) {
            model.addAttribute("error", "Không thể mở trang thanh toán.");
            return "redirect:/orders";
        }
    }

    @PostMapping("/payment/process")
    public String processPayment(@RequestParam("orderId") int orderId,
            @RequestParam(value = "cvv", defaultValue = "") String cvv,
            @RequestParam(value = "action", defaultValue = "confirm") String action,
            HttpSession session,
            Model model) {
        try {
            User user = getLoggedInUser(session);
            if (user == null) {
                return "redirect:/login";
            }

            Order order = orderService.getOrderById(orderId);
            if (order == null || order.getUserId() != user.getId()) {
                return "redirect:/orders";
            }
            if (!"PENDING".equals(order.getPaymentStatus())) {
                return "redirect:/orders";
            }

            if ("cancel".equals(action)) {
                orderService.processPayment(orderId, false);
                return "redirect:/orders";
            }

            boolean success = !"CARD".equals(order.getPaymentMethod()) || !"000".equals(cvv);
            if (!orderService.processPayment(orderId, success)) {
                model.addAttribute("order", order);
                model.addAttribute("error", "Không thể cập nhật trạng thái thanh toán.");
                return "payment";
            }

            if (success) {
                return "redirect:/payment/success?orderId=" + orderId;
            }
            model.addAttribute("order", order);
            model.addAttribute("error", "Thẻ bị từ chối. Vui lòng kiểm tra lại thông tin.");
            return "payment";
        } catch (Exception e) {
            model.addAttribute("error", "Đã xảy ra lỗi khi xử lý thanh toán.");
            return "payment";
        }
    }

    @GetMapping("/payment/success")
    public String paymentSuccess(@RequestParam("orderId") int orderId, HttpSession session, Model model) {
        try {
            User user = getLoggedInUser(session);
            if (user == null) {
                return "redirect:/login";
            }

            Order order = orderService.getOrderById(orderId);
            if (order == null || order.getUserId() != user.getId()) {
                return "redirect:/orders";
            }

            model.addAttribute("order", order);
            return "payment-success";
        } catch (Exception e) {
            model.addAttribute("error", "Không thể hiển thị kết quả thanh toán.");
            return "redirect:/orders";
        }
    }
}
