/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author votandat
 */
package com.example.controller;

import com.example.model.Category;
import com.example.service.CategoryService;
import com.example.service.ProductService;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/")
    public String home(Model model) {
        try {
            List<Category> categories = categoryService.getAllCategories();
            model.addAttribute("categories", categories);
            model.addAttribute("iphoneProducts", productService.getProductsByCategory(1));
            model.addAttribute("macProducts", productService.getProductsByCategory(2));
            model.addAttribute("ipadProducts", productService.getProductsByCategory(3));
            model.addAttribute("accessoryProducts", productService.getProductsByCategory(4));
            model.addAttribute("featuredProducts", productService.getFeaturedProducts());
        } catch (Exception e) {
            model.addAttribute("error", "Không thể tải dữ liệu trang chủ.");
            model.addAttribute("categories", Collections.emptyList());
            model.addAttribute("iphoneProducts", Collections.emptyList());
            model.addAttribute("macProducts", Collections.emptyList());
            model.addAttribute("ipadProducts", Collections.emptyList());
            model.addAttribute("accessoryProducts", Collections.emptyList());
            model.addAttribute("featuredProducts", Collections.emptyList());
        }
        model.addAttribute("body", "index.jsp");
        return "layout/main";
    }
}
