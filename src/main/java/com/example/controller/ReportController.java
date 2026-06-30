package com.example.controller;

import com.example.model.User;
import com.example.service.ReportService;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller hiển thị trang báo cáo doanh thu dành cho Admin.
 *
 * @author trinhthuytrang
 */
@Controller
public class ReportController {

    @Autowired
    private ReportService reportService;

    private User getLoggedInUser(HttpSession session) {
        try {
            return (User) session.getAttribute("LOGIN_USER");
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping("/admin/report")
    public String report(HttpSession session, Model model) {
        try {
            User user = getLoggedInUser(session);
            if (user == null || !"ADMIN".equalsIgnoreCase(user.getRole())) {
                return "redirect:/login";
            }

            model.addAttribute("stats", reportService.getSummaryStats());
            model.addAttribute("customerStats", reportService.getCustomerStats());
            model.addAttribute("topProducts", reportService.getTopProducts(10));
            model.addAttribute("statusBreakdown", reportService.getOrderStatusBreakdown());
            model.addAttribute("recentOrders", reportService.getRecentOrders(10));

            List<Map<String, Object>> monthly = reportService.getMonthlyRevenue();
            model.addAttribute("monthlyLabels", reportService.buildChartLabels(monthly, "month"));
            model.addAttribute("monthlyData", reportService.buildChartData(monthly, "revenue"));
            model.addAttribute("monthlyOrders", reportService.buildChartData(monthly, "orderCount"));

            List<Map<String, Object>> statusList = reportService.getOrderStatusBreakdown();
            model.addAttribute("statusLabels", reportService.buildChartLabels(statusList, "status"));
            model.addAttribute("statusData", reportService.buildChartData(statusList, "cnt"));
        } catch (Exception e) {
            model.addAttribute("error", "Không thể tải báo cáo doanh thu.");
            model.addAttribute("stats", Collections.emptyMap());
            model.addAttribute("customerStats", Collections.emptyMap());
            model.addAttribute("topProducts", Collections.emptyList());
            model.addAttribute("statusBreakdown", Collections.emptyList());
            model.addAttribute("recentOrders", Collections.emptyList());
            model.addAttribute("monthlyLabels", "");
            model.addAttribute("monthlyData", "");
            model.addAttribute("monthlyOrders", "");
            model.addAttribute("statusLabels", "");
            model.addAttribute("statusData", "");
        }
        return "admin/report";
    }
}
