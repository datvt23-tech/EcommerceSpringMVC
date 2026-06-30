package com.example.dao;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * DAO thuc hien cac truy van thong ke bao cao doanh thu.
 * Moi phuong thuc tuong ung voi mot widget/bieu do tren trang Admin Report.
 *
 * @author trinhthuytrang
 */
@Repository
public class ReportDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Lay bo so lieu tong hop tu bang orders:
     * tong doanh thu tat ca thoi gian, doanh thu thang nay, doanh thu hom nay,
     * tong so don, so don da thanh toan, so don da huy.
     * Chi tinh doanh thu tu cac don co payment_status = 'PAID'.
     */
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
        return jdbcTemplate.queryForMap(sql);
    }

    /**
     * Lay thong ke khach hang:
     * tong so CUSTOMER, so khach moi dang ky trong thang nay,
     * so khach da tung dat don thanh cong (active).
     */
    public Map<String, Object> getCustomerStats() {
        String sql = "SELECT "
                + "(SELECT COUNT(*) FROM users WHERE role = 'CUSTOMER') AS totalCustomers, "
                + "(SELECT COUNT(*) FROM users WHERE role = 'CUSTOMER' "
                + "  AND YEAR(created_at) = YEAR(NOW()) AND MONTH(created_at) = MONTH(NOW())) AS newThisMonth, "
                + "(SELECT COUNT(DISTINCT user_id) FROM orders WHERE payment_status = 'PAID') AS activeCustomers";
        return jdbcTemplate.queryForMap(sql);
    }

    /**
     * Lay doanh thu 6 thang gan nhat, nhom theo thang (dinh dang YYYY-MM).
     * Chi tinh don co payment_status = 'PAID'.
     * Ket qua sap xep tang dan theo thang de ve bieu do duong.
     */
    public List<Map<String, Object>> getMonthlyRevenue() {
        String sql = "SELECT DATE_FORMAT(created_at, '%Y-%m') AS month, "
                + "COALESCE(SUM(total_amount), 0) AS revenue, "
                + "COUNT(*) AS orderCount "
                + "FROM orders "
                + "WHERE payment_status = 'PAID' "
                + "  AND created_at >= DATE_SUB(NOW(), INTERVAL 6 MONTH) "
                + "GROUP BY DATE_FORMAT(created_at, '%Y-%m') "
                + "ORDER BY month ASC";
        return jdbcTemplate.queryForList(sql);
    }

    /**
     * Lay top san pham ban chay nhat dua tren tong so luong da ban.
     * Chi tinh don co payment_status = 'PAID'.
     *
     * @param limit so luong san pham toi da can tra ve
     */
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
        return jdbcTemplate.queryForList(sql, limit);
    }

    /**
     * Lay so luong don hang theo tung trang thai (Vi du: Cho xu ly, Dang giao,...).
     * Sap xep giam dan theo so luong de hien thi trang thai pho bien nhat len dau.
     */
    public List<Map<String, Object>> getOrderStatusBreakdown() {
        String sql = "SELECT status, COUNT(*) AS cnt FROM orders GROUP BY status ORDER BY cnt DESC";
        return jdbcTemplate.queryForList(sql);
    }

    /**
     * Lay danh sach don hang gan nhat kem thong tin khach hang.
     *
     * @param limit so don hang toi da can tra ve
     */
    public List<Map<String, Object>> getRecentOrders(int limit) {
        String sql = "SELECT o.id, o.customer_name AS customerName, "
                + "o.total_amount AS totalAmount, o.status, "
                + "o.payment_method AS paymentMethod, "
                + "o.payment_status AS paymentStatus, "
                + "o.created_at AS createdAt, u.username "
                + "FROM orders o "
                + "JOIN users u ON o.user_id = u.id "
                + "ORDER BY o.created_at DESC LIMIT ?";
        return jdbcTemplate.queryForList(sql, limit);
    }
}
