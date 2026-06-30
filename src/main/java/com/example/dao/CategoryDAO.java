/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dao;

import com.example.model.Category;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author votandat
 */
@Repository
public class CategoryDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Category> getAllCategories() {

        String sql = "SELECT * FROM category ORDER BY name";

        try {
            return jdbcTemplate.query(
                    sql,
                    new BeanPropertyRowMapper<>(Category.class)
            );
        } catch (DataAccessException e) {
            return Collections.emptyList();
        }
    }

    public Category getById(int id) {

        String sql = "SELECT * FROM category WHERE id=?";

        try {
            return jdbcTemplate.queryForObject(
                    sql,
                    new BeanPropertyRowMapper<>(Category.class),
                    id
            );
        } catch (DataAccessException e) {
            return null;
        }
    }
}
