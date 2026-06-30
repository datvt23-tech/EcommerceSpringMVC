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
        return (User) session.getAttribute("LOGIN_USER");
    }

    /**
     * Hiển thị trang thanh toán cho đơn hàng.
     * Chỉ cho phép xem nếu đơn hàng đang ở trạng thái chưa thanh toán (PENDING).
     */
    @GetMapping("/payment/{orderId}")
    public String showPaymentPage(@PathVariable int orderId, HttpSession session, Model model) {
        User user = getLoggedInUser(session);
        if (user == null) return "redirect:/login";

        Order order = orderService.getOrderById(orderId);
        // Chỉ chủ sở hữu đơn hàng mới được truy cập
        if (order == null || order.getUserId() != user.getId()) {
            return "redirect:/orders";
        }
        // Đơn đã thanh toán hoặc thất bại thì không cho vào lại trang thanh toán
        if ("PAID".equals(order.getPaymentStatus()) || "FAILED".equals(order.getPaymentStatus())) {
            return "redirect:/orders";
        }

        model.addAttribute("order", order);
        return "payment";
    }

    /**
     * Xử lý xác nhận hoặc hủy thanh toán.
     * <ul>
     *   <li>COD: luôn thành công ngay khi xác nhận.</li>
     *   <li>CARD: CVV "000" mô phỏng thẻ bị từ chối (dùng để demo/test).</li>
     * </ul>
     */
    @PostMapping("/payment/process")
    public String processPayment(@RequestParam("orderId") int orderId,
            @RequestParam(value = "cvv", defaultValue = "") String cvv,
            @RequestParam(value = "action", defaultValue = "confirm") String action,
            HttpSession session,
            Model model) {
        User user = getLoggedInUser(session);
        if (user == null) return "redirect:/login";

        Order order = orderService.getOrderById(orderId);
        if (order == null || order.getUserId() != user.getId()) {
            return "redirect:/orders";
        }
        // Chỉ cho phép thanh toán khi trạng thái vẫn là PENDING
        if (!"PENDING".equals(order.getPaymentStatus())) {
            return "redirect:/orders";
        }

        // Người dùng chọn hủy thanh toán
        if ("cancel".equals(action)) {
            orderService.processPayment(orderId, false);
            return "redirect:/orders";
        }

        // COD: luôn thành công
        // CARD: CVV "000" = thất bại để demo
        boolean success = !"CARD".equals(order.getPaymentMethod()) || !"000".equals(cvv);
        orderService.processPayment(orderId, success);

        if (success) {
            return "redirect:/payment/success?orderId=" + orderId;
        } else {
            model.addAttribute("order", order);
            model.addAttribute("error", "Thẻ bị từ chối. Vui lòng kiểm tra lại thông tin.");
            return "payment";
        }
    }

    /**
     * Hiển thị trang xác nhận thanh toán thành công.
     * Dùng để thông báo kết quả và tóm tắt thông tin đơn hàng cho khách.
     */
    @GetMapping("/payment/success")
    public String paymentSuccess(@RequestParam("orderId") int orderId, HttpSession session, Model model) {
        User user = getLoggedInUser(session);
        if (user == null) return "redirect:/login";

        Order order = orderService.getOrderById(orderId);
        if (order == null || order.getUserId() != user.getId()) {
            return "redirect:/orders";
        }

        model.addAttribute("order", order);
        return "payment-success";
    }
}
