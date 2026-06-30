/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.service;

import com.example.dao.SupportTicketDAO;
import com.example.model.SupportTicket;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SupportService {

    @Autowired
    private SupportTicketDAO supportTicketDAO;

    // ==========================
    // CREATE
    // ==========================
    public boolean createTicket(int userId,
            String customerName,
            String subject,
            String message) {

        if (subject == null || subject.trim().isEmpty()) {
            return false;
        }

        if (message == null || message.trim().isEmpty()) {
            return false;
        }

        return supportTicketDAO.createTicket(
                userId,
                customerName,
                subject,
                message
        );
    }

    // ==========================
    // CUSTOMER
    // ==========================
    public List<SupportTicket> getTicketsByUserId(int userId) {
        return supportTicketDAO.getTicketsByUserId(userId);
    }

    // ==========================
    // STAFF / ADMIN
    // ==========================
    public List<SupportTicket> getAllTickets() {
        return supportTicketDAO.getAllTickets();
    }

    // ==========================
    // UPDATE
    // ==========================
    public boolean updateTicket(int id,
            String status,
            String reply) {

        if (id <= 0) {
            return false;
        }

        if (!isValidStatus(status)) {
            return false;
        }

        return supportTicketDAO.updateTicket(
                id,
                status,
                reply
        );
    }

    // ==========================
    // BUSINESS RULE
    // ==========================
    private boolean isValidStatus(String status) {

        return "Mới".equals(status)
                || "Đang xử lý".equals(status)
                || "Đã phản hồi".equals(status)
                || "Đã đóng".equals(status);
    }
}
