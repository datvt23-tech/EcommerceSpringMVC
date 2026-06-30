package com.example.dao;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * DAO thuc hien cac truy van thong ke bao cao doanh thu.
 *
 * @author trinhthuytrang
 */
@Repository
public class ReportDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Map<String, Object> getSummaryStats() {
        String sql = "SELECT "
                + "COALESCE(SUM(CASE WHEN payment_status = 'PAID' THEN total_amount ELSE 0 END), 0) AS totalRevenue, "
                + "COALESCE(SUM(CASE WHEN payment_status = 'PAID' "
                + "  AND YEAR(created_at) = YEAR(NOW()) AND MONTH(created_at) = MONTH(NOW()) "
                + "  THEN total_amount ELSE 0 END), 0) AS monthRevenue, "
                + "COALESCE(SUM(CASE WHEN payment_status = 'PAID' AND DATE(created_at) = CURDATE() "
                + "  THEN total_amount ELSE 0 END), 0) AS todayRevenue, "
                + "COUNT(*) AS totalOrders, "
                + "SUM(CASE WHEN payment_status = 'PAID' THEN 1 ELSE 0 END) AS paidOrders, "
                + "SUM(CASE WHEN status = 'Da huy' THEN 1 ELSE 0 END) AS cancelledOrders "
                + "FROM orders";
        try {
            return jdbcTemplate.queryForMap(sql);
        } catch (DataAccessException e) {
            return new HashMap<>();
        }
    }

    public Map<String, Object> getCustomerStats() {
        String sql = "SELECT "
                + "(SELECT COUNT(*) FROM users WHERE role = 'CUSTOMER') AS totalCustomers, "
                + "(SELECT COUNT(*) FROM users WHERE role = 'CUSTOMER' "
                + "  AND YEAR(created_at) = YEAR(NOW()) AND MONTH(created_at) = MONTH(NOW())) AS newThisMonth, "
                + "(SELECT COUNT(DISTINCT user_id) FROM orders WHERE payment_status = 'PAID') AS activeCustomers";
        try {
            return jdbcTemplate.queryForMap(sql);
        } catch (DataAccessException e) {
            return new HashMap<>();
        }
    }

    public List<Map<String, Object>> getMonthlyRevenue() {
        String sql = "SELECT DATE_FORMAT(created_at, '%Y-%m') AS month, "
                + "COALESCE(SUM(total_amount), 0) AS revenue, "
                + "COUNT(*) AS orderCount "
                + "FROM orders "
                + "WHERE payment_status = 'PAID' "
                + "  AND created_at >= DATE_SUB(NOW(), INTERVAL 6 MONTH) "
                + "GROUP BY DATE_FORMAT(created_at, '%Y-%m') "
                + "ORDER BY month ASC";
        try {
            return jdbcTemplate.queryForList(sql);
        } catch (DataAccessException e) {
            return Collections.emptyList();
        }
    }

    public List<Map<String, Object>> getTopProducts(int limit) {
        String sql = "SELECT oi.product_name AS productName, "
                + "SUM(oi.quantity) AS totalQty, "
                + "SUM(oi.price * oi.quantity) AS totalRevenue "
                + "FROM order_items oi "
                + "JOIN orders o ON oi.order_id = o.id "
                + "WHERE o.payment_status = 'PAID' "
                + "GROUP BY oi.product_name "
                + "ORDER BY totalQty DESC "
                + "LIMIT ?";
        try {
            return jdbcTemplate.queryForList(sql, limit);
        } catch (DataAccessException e) {
            return Collections.emptyList();
        }
    }

    public List<Map<String, Object>> getOrderStatusBreakdown() {
        String sql = "SELECT status, COUNT(*) AS cnt FROM orders GROUP BY status ORDER BY cnt DESC";
        try {
            return jdbcTemplate.queryForList(sql);
        } catch (DataAccessException e) {
            return Collections.emptyList();
        }
    }

    public List<Map<String, Object>> getRecentOrders(int limit) {
        String sql = "SELECT o.id, o.customer_name AS customerName, "
                + "o.total_amount AS totalAmount, o.status, "
                + "o.payment_method AS paymentMethod, "
                + "o.payment_status AS paymentStatus, "
                + "o.created_at AS createdAt, u.username "
                + "FROM orders o "
                + "JOIN users u ON o.user_id = u.id "
                + "ORDER BY o.created_at DESC LIMIT ?";
        try {
            return jdbcTemplate.queryForList(sql, limit);
        } catch (DataAccessException e) {
            return Collections.emptyList();
        }
    }
}
