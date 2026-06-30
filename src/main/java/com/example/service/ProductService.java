/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.service;

import com.example.dao.ProductDAO;
import com.example.model.Product;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductDAO productDAO; // Service gọi DAO

    public List<Product> getAllProducts() {
        try {
            return productDAO.getAll();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public Product getProductById(int id) {
        if (id <= 0) {
            return null;
        }
        try {
            return productDAO.getById(id);
        } catch (Exception e) {
            return null;
        }
    }

    public boolean addProduct(Product product) {
        if (product == null || product.getPrice() < 0) {
            return false;
        }
        try {
            return productDAO.insert(product);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean updateProduct(Product product) {
        if (product == null || product.getId() <= 0 || product.getPrice() < 0) {
            return false;
        }
        try {
            Product existing = productDAO.getById(product.getId());
            return existing != null && productDAO.update(product);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean deleteProduct(int id) {
        try {
            return productDAO.delete(id);
        } catch (Exception e) {
            return false;
        }
    }

    public List<Product> searchProducts(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllProducts();
        }
        try {
            return productDAO.searchByName(keyword);
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public List<Product> getProductsByCategory(int categoryId) {
        try {
            return productDAO.getProductsByCategory(categoryId);
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public List<Product> getFeaturedProducts() {
        try {
            return productDAO.getFeaturedProducts();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public boolean updateFeatured(int id, boolean featured) {
        try {
            return productDAO.updateFeatured(id, featured);
        } catch (Exception e) {
            return false;
        }
    }
}
