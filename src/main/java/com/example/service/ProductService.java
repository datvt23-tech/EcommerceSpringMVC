/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.service;

import com.example.dao.ProductDAO;
import com.example.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductDAO productDAO; // Service gọi DAO

    // 1. Lấy toàn bộ sản phẩm
    public List<Product> getAllProducts() {
        return productDAO.getAll();
    }

    // 2. Lấy chi tiết 1 sản phẩm theo ID
    public Product getProductById(int id) {
        if (id <= 0) {
            return null;
        }
        return productDAO.getById(id);
    }

    // 3. Logic thêm sản phẩm mới (Có kiểm tra nghiệp vụ)
    public boolean addProduct(Product product) {
        if (product == null) {
            return false;
        }

        if (product.getPrice() < 0) {
            return false;
        }

        return productDAO.insert(product);
    }

    // 4. Logic cập nhật sản phẩm
    public boolean updateProduct(Product product) {
        if (product == null || product.getId() <= 0) {
            return false;
        }

        if (product.getPrice() < 0) {
            return false;
        }

        Product existing = productDAO.getById(product.getId());
        if (existing == null) {
            return false;
        }

        return productDAO.update(product);
    }

    // 5. Logic xóa sản phẩm
    public boolean deleteProduct(int id) {
        return productDAO.delete(id);
    }

    public List<Product> searchProducts(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return productDAO.getAll();
        }
        return productDAO.searchByName(keyword);
    }

    public List<Product> getProductsByCategory(int categoryId) {
        return productDAO.getProductsByCategory(categoryId);
    }

    public List<Product> getFeaturedProducts() {
        return productDAO.getFeaturedProducts();
    }

    public boolean updateFeatured(int id, boolean featured) {
        return productDAO.updateFeatured(id, featured);
    }
}
