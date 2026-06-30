/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.controller;

import com.example.model.CartItem;
import com.example.model.Product;
import com.example.model.User;
import com.example.service.ProductService;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author votandat
 */
@Controller
public class CartController {

    @Autowired
    private ProductService productService;

    private User getLoggedInUser(HttpSession session) {
        try {
            return (User) session.getAttribute("LOGIN_USER");
        } catch (Exception e) {
            return null;
        }
    }

    private boolean canManageProducts(HttpSession session) {
        User user = getLoggedInUser(session);
        return user != null && ("STAFF".equals(user.getRole()) || "ADMIN".equals(user.getRole()));
    }

    // 1. LUỒNG XỬ LÝ: THÊM SẢN PHẨM VÀO GIỎ HÀNG
    @GetMapping("/cart/add")
    public String addToCart(@RequestParam("productId") int productId, HttpSession session) {
        try {
            if (getLoggedInUser(session) == null) {
                return "redirect:/login";
            }
            if (canManageProducts(session)) {
                return "redirect:/dashboard";
            }

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
        } catch (Exception e) {
            session.setAttribute("cartError", "Không thể thêm sản phẩm vào giỏ hàng.");
        }
        return "redirect:/cart";
    }

    // 2. LUỒNG XỬ LÝ: XEM TRANG GIỎ HÀNG
    @GetMapping("/cart")
    public String showCart(Model model, HttpSession session) {
        try {
            if (getLoggedInUser(session) == null) {
                return "redirect:/login";
            }
            if (canManageProducts(session)) {
                return "redirect:/dashboard";
            }
            Object error = session.getAttribute("cartError");
            if (error != null) {
                model.addAttribute("error", error);
                session.removeAttribute("cartError");
            }
            model.addAttribute("body", "cart.jsp");
            return "layout/main";
        } catch (Exception e) {
            model.addAttribute("error", "Không thể tải giỏ hàng.");
            model.addAttribute("body", "cart.jsp");
            return "layout/main";
        }
    }

    // 1. LUỒNG XỬ LÝ: TĂNG / GIẢM SỐ LƯỢNG
    @GetMapping("/cart/update")
    public String updateCart(@RequestParam("productId") int productId,
            @RequestParam("action") String action,
            HttpSession session) {
        try {
            if (canManageProducts(session)) {
                return "redirect:/dashboard";
            }
            List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
            if (cart != null) {
                for (CartItem item : cart) {
                    if (item.getProduct().getId() == productId) {
                        if ("inc".equals(action)) {
                            item.setQuantity(item.getQuantity() + 1);
                        } else if ("dec".equals(action) && item.getQuantity() > 1) {
                            item.setQuantity(item.getQuantity() - 1);
                        }
                        break;
                    }
                }
                session.setAttribute("cart", cart);
            }
        } catch (Exception e) {
            session.setAttribute("cartError", "Không thể cập nhật số lượng sản phẩm.");
        }
        return "redirect:/cart";
    }

    // 2. LUỒNG XỬ LÝ: XÓA SẢN PHẨM KHỎI GIỎ
    @GetMapping("/cart/remove")
    public String removeFromCart(@RequestParam("productId") int productId, HttpSession session) {
        try {
            if (canManageProducts(session)) {
                return "redirect:/dashboard";
            }
            List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
            if (cart != null) {
                cart.removeIf(item -> item.getProduct().getId() == productId);
                session.setAttribute("cart", cart);
            }
        } catch (Exception e) {
            session.setAttribute("cartError", "Không thể xóa sản phẩm khỏi giỏ hàng.");
        }
        return "redirect:/cart";
    }
}
