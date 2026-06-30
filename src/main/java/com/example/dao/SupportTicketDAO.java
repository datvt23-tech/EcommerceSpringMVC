package com.example.dao;

import com.example.model.SupportTicket;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SupportTicketDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean createTicket(int userId, String customerName, String subject, String message) {
        String sql = "INSERT INTO support_tickets (user_id, customer_name, subject, message, status) "
                + "VALUES (?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, userId, customerName, subject, message, "Mới") > 0;
    }

    public List<SupportTicket> getTicketsByUserId(int userId) {
        String sql = "SELECT * FROM support_tickets WHERE user_id = ? ORDER BY created_at DESC";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(SupportTicket.class), userId);
    }

    public List<SupportTicket> getAllTickets() {
        String sql = "SELECT * FROM support_tickets ORDER BY created_at DESC";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(SupportTicket.class));
    }

    public boolean updateTicket(int id, String status, String reply) {
        String sql = "UPDATE support_tickets SET status = ?, reply = ? WHERE id = ?";
        return jdbcTemplate.update(sql, status, reply, id) > 0;
    }
}
