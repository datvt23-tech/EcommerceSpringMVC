/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.service;

import com.example.dao.OrderDAO;
import com.example.model.CartItem;
import com.example.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderDAO orderDAO;

    // Checkout
    public int createOrder(int userId, String customerName, String phone,
            String address, List<CartItem> cart) {

        try {
            if (userId <= 0 || cart == null || cart.isEmpty()) {
                return 0;
            }

            double total = calculateTotal(cart);

            return orderDAO.createOrder(
                    userId,
                    customerName,
                    phone,
                    address,
                    total,
                    cart
            );
        } catch (Exception e) {
            return 0;
        }
    }

    // Tính tổng tiền
    public double calculateTotal(List<CartItem> cart) {
        double total = 0;
        if (cart == null) {
            return total;
        }
        for (CartItem item : cart) {
            if (item != null && item.getProduct() != null) {
                total += item.getProduct().getPrice() * item.getQuantity();
            }
        }
        return total;
    }

    // Lấy đơn user
    public List<Order> getOrdersByUserId(int userId) {
        return orderDAO.getOrdersByUserId(userId);
    }

    // Lấy tất cả đơn (staff/admin)
    public List<Order> getAllOrders() {
        return orderDAO.getAllOrders();
    }

    // Update status
    public boolean updateOrderStatus(int id, String status) {
        return orderDAO.updateOrderStatus(id, status);
    }

    public Order getOrderById(int orderId) {
        if (orderId <= 0) {
            return null;
        }
        try {
            return orderDAO.getOrderById(orderId);
        } catch (Exception e) {
            return null;
        }
    }
    public boolean processPayment(int orderId, boolean success) {
        try {
            if (success) {
                return orderDAO.updatePaymentStatus(orderId, "PAID", "Đã xác nhận");
            } else {
                return orderDAO.updatePaymentStatus(orderId, "FAILED", "Đã hủy");
            }
        } catch (Exception e) {
            return false;
        }
    }
}
