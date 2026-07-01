/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dao;

/**
 *
 * @author votandat
 */
import com.example.model.CartItem;
import com.example.model.Order;
import com.example.model.OrderItem;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int createOrder(int userId, String customerName, String phone, String address,
            double totalAmount, List<CartItem> cart) {
        String orderSql = "INSERT INTO orders (user_id, customer_name, phone, address, total_amount, status) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(orderSql, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, userId);
                ps.setString(2, customerName);
                ps.setString(3, phone);
                ps.setString(4, address);
                ps.setDouble(5, totalAmount);
                ps.setString(6, "Đang xử lý");
                return ps;
            }, keyHolder);

            if (keyHolder.getKey() == null) {
                return 0;
            }

            int orderId = keyHolder.getKey().intValue();
            String itemSql = "INSERT INTO order_items (order_id, product_id, product_name, price, quantity) "
                    + "VALUES (?, ?, ?, ?, ?)";
            for (CartItem item : cart) {
                jdbcTemplate.update(itemSql,
                        orderId,
                        item.getProduct().getId(),
                        item.getProduct().getName(),
                        item.getProduct().getPrice(),
                        item.getQuantity());
            }
            return orderId;
        } catch (DataAccessException e) {
            return 0;
        }
    }

    public List<Order> getOrdersByUserId(int userId) {
        String sql = "SELECT * FROM orders WHERE user_id = ? ORDER BY created_at DESC";
        try {
            List<Order> orders = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Order.class), userId);
            attachItems(orders);
            return orders;
        } catch (DataAccessException e) {
            return Collections.emptyList();
        }
    }

    public List<Order> getAllOrders() {
        String sql = "SELECT * FROM orders ORDER BY created_at DESC";
        try {
            List<Order> orders = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Order.class));
            attachItems(orders);
            return orders;
        } catch (DataAccessException e) {
            return Collections.emptyList();
        }
    }

    public Order getOrderById(int orderId) {
        String sql = "SELECT * FROM orders WHERE id = ?";
        try {
            Order order = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Order.class), orderId);
            if (order != null) {
                attachItems(Collections.singletonList(order));
            }
            return order;
        } catch (DataAccessException e) {
            return null;
        }
    }

    public boolean updatePaymentStatus(int orderId, String paymentStatus, String orderStatus) {
        String sql = "UPDATE orders SET payment_status = ?, status = ? WHERE id = ?";
        try {
            return jdbcTemplate.update(sql, paymentStatus, orderStatus, orderId) > 0;
        } catch (DataAccessException e) {
            return false;
        }
    }

    public boolean updateOrderStatus(int id, String status) {
        String sql = "UPDATE orders SET status = ? WHERE id = ?";
        try {
            return jdbcTemplate.update(sql, status, id) > 0;
        } catch (DataAccessException e) {
            return false;
        }
    }

    private void attachItems(List<Order> orders) {
        for (Order order : orders) {
            String sql = "SELECT * FROM order_items WHERE order_id = ?";
            try {
                List<OrderItem> items = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(OrderItem.class), order.getId());
                order.setItems(items);
            } catch (DataAccessException e) {
                order.setItems(Collections.emptyList());
            }
        }
    }

    public List<Order> getLatestOrders() {

        String sql
                = "SELECT * FROM orders "
                + "ORDER BY id DESC "
                + "LIMIT 5";

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Order.class));
    }

    public boolean cancelOrder(int id) {

        String sql = 
        "UPDATE orders"+
        "SET status = 'CANCELLED'"+
        "WHERE id = ?"+
        "AND status = 'PENDING'"+
        "AND payment_status = 'PENDING'"
        ;

        return jdbcTemplate.update(sql, id) > 0;
    }
}
