package com.example.controller;

import com.example.model.User;
import com.example.service.ReportService;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller hiển thị trang báo cáo doanh thu dành cho Admin.
 * Tổng hợp các số liệu thống kê: doanh thu, đơn hàng, khách hàng,
 * top sản phẩm bán chạy và dữ liệu vẽ biểu đồ Chart.js.
 *
 * @author trinhthuytrang
 */
@Controller
public class ReportController {

    @Autowired
    private ReportService reportService;

    /**
     * Trang báo cáo tổng hợp — chỉ Admin mới được truy cập.
     * Đẩy toàn bộ dữ liệu thống kê và chuỗi JSON cho Chart.js vào model.
     */
    @GetMapping("/admin/report")
    public String report(HttpSession session, Model model) {
        User user = (User) session.getAttribute("LOGIN_USER");
        if (user == null || !"ADMIN".equalsIgnoreCase(user.getRole())) {
            return "redirect:/login";
        }

        // Thống kê tổng quan: tổng doanh thu, doanh thu tháng/hôm nay, số đơn hàng
        model.addAttribute("stats", reportService.getSummaryStats());
        // Thống kê khách hàng: tổng, mới trong tháng, đang hoạt động
        model.addAttribute("customerStats", reportService.getCustomerStats());

        // Bảng top 10 sản phẩm bán chạy nhất
        model.addAttribute("topProducts", reportService.getTopProducts(10));
        // Phân bổ đơn hàng theo từng trạng thái (dùng cho bảng lẫn biểu đồ Doughnut)
        model.addAttribute("statusBreakdown", reportService.getOrderStatusBreakdown());
        // 10 đơn hàng gần nhất để hiển thị trong bảng tổng hợp
        model.addAttribute("recentOrders", reportService.getRecentOrders(10));

        // Dữ liệu doanh thu 6 tháng gần nhất — chuyển thành chuỗi cho biểu đồ Line
        List<Map<String, Object>> monthly = reportService.getMonthlyRevenue();
        model.addAttribute("monthlyLabels", reportService.buildChartLabels(monthly, "month"));
        model.addAttribute("monthlyData", reportService.buildChartData(monthly, "revenue"));
        model.addAttribute("monthlyOrders", reportService.buildChartData(monthly, "orderCount"));

        // Dữ liệu phân bổ trạng thái đơn hàng — chuyển thành chuỗi cho biểu đồ Doughnut
        List<Map<String, Object>> statusList = reportService.getOrderStatusBreakdown();
        model.addAttribute("statusLabels", reportService.buildChartLabels(statusList, "status"));
        model.addAttribute("statusData", reportService.buildChartData(statusList, "cnt"));

        return "admin/report";
    }
}
