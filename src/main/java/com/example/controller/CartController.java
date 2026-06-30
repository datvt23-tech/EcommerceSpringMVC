/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.controller;

import com.example.model.CartItem;
import com.example.model.Product;
import com.example.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import com.example.model.User;

/**
 *
 * @author votandat
 */
@Controller
public class CartController {

    @Autowired
    private ProductService productService;

    private boolean canManageProducts(HttpSession session) {
        User user = (User) session.getAttribute("LOGIN_USER");
        return user != null && ("STAFF".equals(user.getRole()) || "ADMIN".equals(user.getRole()));
    }

    // 1. LUỒNG XỬ LÝ: THÊM SẢN PHẨM VÀO GIỎ HÀNG
    @GetMapping("/cart/add")
    public String addToCart(@RequestParam("productId") int productId, HttpSession session) {

        // 1. BƯỚC CHẶN ĐĂNG NHẬP: Kiểm tra xem LOGIN_USER đã tồn tại trong Session chưa
        if (session.getAttribute("LOGIN_USER") == null) {
            // Nếu chưa đăng nhập -> Chuyển hướng (Redirect) ngay lập tức sang trang login
            // (Bạn nhớ đổi đường dẫn "/login" này cho đúng với URL trang đăng nhập của dự án nhé)
            return "redirect:/login";
        }
        if (canManageProducts(session)) {
            return "redirect:/dashboard";
        }

        // 2. LOGIC CŨ (Chỉ chạy khi đã vượt qua bước kiểm tra đăng nhập ở trên)
        Product product = productService.getProductById(productId);

        if (product != null) {
            List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

            if (cart == null) {
                cart = new ArrayList<>();
            }

            boolean isExist = false;
            for (CartItem item : cart) {
                if (item.getProduct().getId() == productId) {
                    item.setQuantity(item.getQuantity() + 1);
                    isExist = true;
                    break;
                }
            }

            if (!isExist) {
                cart.add(new CartItem(product, 1));
            }

            session.setAttribute("cart", cart);
        }

        return "redirect:/cart";
    }

    // 2. LUỒNG XỬ LÝ: XEM TRANG GIỎ HÀNG
    @GetMapping("/cart")
    public String showCart(Model model, HttpSession session) {
        if (session.getAttribute("LOGIN_USER") == null) {
            return "redirect:/login";
        }
        if (canManageProducts(session)) {
            return "redirect:/dashboard";
        }
        model.addAttribute("body", "cart.jsp"); // Nạp file cart.jsp vào master layout
        return "layout/main";
    }
    // 1. LUỒNG XỬ LÝ: TĂNG / GIẢM SỐ LƯỢNG

    @GetMapping("/cart/update")
    public String updateCart(@RequestParam("productId") int productId, @RequestParam("action") String action, HttpSession session) {
        if (canManageProducts(session)) {
            return "redirect:/dashboard";
        }
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        if (cart != null) {
            for (CartItem item : cart) {
                if (item.getProduct().getId() == productId) {
                    if ("inc".equals(action)) {
                        item.setQuantity(item.getQuantity() + 1); // Tăng lên 1
                    } else if ("dec".equals(action)) {
                        if (item.getQuantity() > 1) {
                            item.setQuantity(item.getQuantity() - 1); // Giảm đi 1 (giới hạn tối thiểu là 1)
                        }
                    }
                    break;
                }
            }
            session.setAttribute("cart", cart); // Lưu lại vào session
        }
        return "redirect:/cart"; // Quay lại trang giỏ hàng để cập nhật giao diện
    }

// 2. LUỒNG XỬ LÝ: XÓA SẢN PHẨM KHỎI GIỎ
    @GetMapping("/cart/remove")
    public String removeFromCart(@RequestParam("productId") int productId, HttpSession session) {
        if (canManageProducts(session)) {
            return "redirect:/dashboard";
        }
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        if (cart != null) {
            // Tìm và xóa sản phẩm có ID trùng khớp
            cart.removeIf(item -> item.getProduct().getId() == productId);
            session.setAttribute("cart", cart); // Cập nhật lại session
        }
        return "redirect:/cart";
    }
}
