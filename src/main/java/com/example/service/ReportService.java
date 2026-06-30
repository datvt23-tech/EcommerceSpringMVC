package com.example.service;

import com.example.dao.ReportDAO;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service cung cap cac nghiep vu thong ke bao cao doanh thu cho Admin.
 * Uy quyen truy van xuong ReportDAO va cung cap tien ich chuyen doi
 * du lieu sang chuoi JSON de ve bieu do Chart.js.
 *
 * @author trinhthuytrang
 */
@Service
public class ReportService {

    @Autowired
    private ReportDAO reportDAO;

    /** Tra ve bo so lieu tong hop: tong doanh thu, doanh thu thang/hom nay, so don hang. */
    public Map<String, Object> getSummaryStats() {
        try {
            return reportDAO.getSummaryStats();
        } catch (Exception e) {
            return new HashMap<>();
        }
    }

    /** Tra ve thong ke khach hang: tong so, moi trong thang, dang hoat dong. */
    public Map<String, Object> getCustomerStats() {
        try {
            return reportDAO.getCustomerStats();
        } catch (Exception e) {
            return new HashMap<>();
        }
    }

    /** Tra ve danh sach doanh thu theo thang trong 6 thang gan nhat. */
    public List<Map<String, Object>> getMonthlyRevenue() {
        try {
            return reportDAO.getMonthlyRevenue();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    /**
     * Tra ve top san pham ban chay.
     *
     * @param limit so luong san pham toi da can lay
     */
    public List<Map<String, Object>> getTopProducts(int limit) {
        try {
            return reportDAO.getTopProducts(limit);
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    /** Tra ve so luong don hang theo tung trang thai (dung cho bieu do Doughnut). */
    public List<Map<String, Object>> getOrderStatusBreakdown() {
        try {
            return reportDAO.getOrderStatusBreakdown();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    /**
     * Tra ve danh sach don hang gan nhat.
     *
     * @param limit so don hang toi da can lay
     */
    public List<Map<String, Object>> getRecentOrders(int limit) {
        try {
            return reportDAO.getRecentOrders(limit);
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    /**
     * Chuyen danh sach row thanh chuoi nhan bieu do Chart.js (dinh dang: 'val1','val2',...).
     *
     * @param rows danh sach du lieu tra ve tu DAO
     * @param key  ten cot can lay lam nhan
     */
    public String buildChartLabels(List<Map<String, Object>> rows, String key) {
        StringBuilder sb = new StringBuilder();
        if (rows == null) {
            return "";
        }
        for (int i = 0; i < rows.size(); i++) {
            if (i > 0) sb.append(",");
            sb.append("'").append(rows.get(i).get(key)).append("'");
        }
        return sb.toString();
    }

    /**
     * Chuyen danh sach row thanh chuoi gia tri so bieu do Chart.js (dinh dang: val1,val2,...).
     * Gia tri null duoc thay bang 0 de tranh loi render bieu do.
     *
     * @param rows danh sach du lieu tra ve tu DAO
     * @param key  ten cot can lay lam gia tri
     */
    public String buildChartData(List<Map<String, Object>> rows, String key) {
        StringBuilder sb = new StringBuilder();
        if (rows == null) {
            return "";
        }
        for (int i = 0; i < rows.size(); i++) {
            if (i > 0) sb.append(",");
            Object val = rows.get(i).get(key);
            sb.append(val != null ? val.toString() : "0");
        }
        return sb.toString();
    }
}
