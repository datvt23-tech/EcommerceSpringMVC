/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.service;

import com.example.dao.CategoryDAO;
import com.example.model.Category;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author votandat
 */
@Service
public class CategoryService {

    @Autowired
    private CategoryDAO categoryDAO;

    public List<Category> getAllCategories() {
        try {
            return categoryDAO.getAllCategories();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public Category getById(int id) {
        try {
            return categoryDAO.getById(id);
        } catch (Exception e) {
            return null;
        }
    }
}
