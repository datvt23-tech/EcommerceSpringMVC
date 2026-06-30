package com.example.controller;

import com.example.dao.SupportTicketDAO;
import com.example.model.User;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SupportController {

    private static final String[] TICKET_STATUSES = {"Mới", "Đang xử lý", "Đã phản hồi", "Đã đóng"};

    @Autowired
    private SupportTicketDAO supportTicketDAO;

    private User getLoggedInUser(HttpSession session) {
        return (User) session.getAttribute("LOGIN_USER");
    }

    private boolean hasRole(HttpSession session, String role) {
        User user = getLoggedInUser(session);
        return user != null && role.equals(user.getRole());
    }

    private boolean canOperate(HttpSession session) {
        return hasRole(session, "STAFF") || hasRole(session, "ADMIN");
    }

    private boolean isAllowedStatus(String status) {
        for (String allowedStatus : TICKET_STATUSES) {
            if (allowedStatus.equals(status)) {
                return true;
            }
        }
        return false;
    }

    @GetMapping("/support")
    public String support(HttpSession session, Model model) {
        User user = getLoggedInUser(session);
        if (user == null) {
            return "redirect:/login";
        }
        if (canOperate(session)) {
            return "redirect:/staff/support";
        }

        model.addAttribute("tickets", supportTicketDAO.getTicketsByUserId(user.getId()));
        model.addAttribute("body", "/customer/support.jsp");
        return "layout/main";
    }

    @PostMapping("/support/create")
    public String createTicket(@RequestParam("subject") String subject,
            @RequestParam("message") String message,
            HttpSession session,
            Model model) {
        User user = getLoggedInUser(session);
        if (user == null) {
            return "redirect:/login";
        }
        if (canOperate(session)) {
            return "redirect:/staff/support";
        }

        String customerName = user.getFullName() != null && !user.getFullName().isEmpty()
                ? user.getFullName()
                : user.getUsername();
        if (supportTicketDAO.createTicket(user.getId(), customerName, subject, message)) {
            model.addAttribute("success", "Đã gửi yêu cầu hỗ trợ.");
        } else {
            model.addAttribute("error", "Không thể gửi yêu cầu hỗ trợ.");
        }
        return support(session, model);
    }

    @GetMapping("/staff/support")
    public String staffSupport(HttpSession session, Model model) {
        if (!canOperate(session)) {
            return getLoggedInUser(session) == null ? "redirect:/login" : "redirect:/";
        }

        model.addAttribute("tickets", supportTicketDAO.getAllTickets());
        model.addAttribute("statuses", TICKET_STATUSES);
        model.addAttribute("body", "/staff/support.jsp");
        return "staff/layout/main";
    }

    @PostMapping("/staff/support/update")
    public String updateTicket(@RequestParam("id") int id,
            @RequestParam("status") String status,
            @RequestParam("reply") String reply,
            HttpSession session,
            Model model) {
        if (!canOperate(session)) {
            return getLoggedInUser(session) == null ? "redirect:/login" : "redirect:/";
        }
        if (!isAllowedStatus(status)) {
            model.addAttribute("error", "Trạng thái hỗ trợ không hợp lệ.");
            return staffSupport(session, model);
        }

        if (supportTicketDAO.updateTicket(id, status, reply)) {
            model.addAttribute("success", "Cập nhật yêu cầu hỗ trợ thành công.");
        } else {
            model.addAttribute("error", "Không thể cập nhật yêu cầu hỗ trợ.");
        }
        return staffSupport(session, model);
    }
    @GetMapping("/admin/support")
    public String adminSupport(HttpSession session, Model model) {
        if (!canOperate(session)) {
            return getLoggedInUser(session) == null ? "redirect:/login" : "redirect:/";
        }

        model.addAttribute("tickets", supportTicketDAO.getAllTickets());
        model.addAttribute("statuses", TICKET_STATUSES);
        model.addAttribute("body", "/admin/support.jsp");
        return "admin/layout/main";
    }
}
