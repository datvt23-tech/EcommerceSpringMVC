/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.controller;

import com.example.model.Category;
import com.example.model.Product;
import com.example.model.User;
import com.example.service.CategoryService;
import com.example.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.validation.BindingResult;

@Controller
@RequestMapping("/product") // Gom cụm tất cả đường dẫn bắt đầu bằng /product
public class ProductController {

    @Autowired
    private ProductService productService; // Chỉ gọi tầng Service

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/products")
    public String showProductsBySeries(Model model) {
        // Lọc sản phẩm theo Series khách bấm vào
        List<Product> products = productService.getProductsByCategory(0);

        model.addAttribute("products", products);
        model.addAttribute("categories", categoryService.getAllCategories());

        model.addAttribute("body", "/product/list.jsp"); // Nạp trang list sản phẩm
        return "layout/main";
    }

    // R - Read: Hiển thị danh sách sản phẩm
    @GetMapping("admin/list")
    public String adminListProducts(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("body", "/product/list.jsp");
        return "admin/layout/main";
    }

    @GetMapping("staff/list")
    public String staffListProducts(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("body", "/product/list.jsp");
        return "staff/layout/main";
    }

    // C - Create: Hiển thị form thêm mới
    @GetMapping("/add")
    public String showAddForm(Model model, HttpSession session) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("body", "/product/form.jsp");
        User loginUser
                = (User) session.getAttribute("LOGIN_USER");

        if ("ADMIN".equals(loginUser.getRole())) {
            return "admin/layout/main";
        }

        return "staff/layout/main";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model, HttpSession session) {
        Product product = productService.getProductById(id);
        if (product == null) {
            return "redirect:/product/list";
        }
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("body", "/product/form.jsp");
        User loginUser
                = (User) session.getAttribute("LOGIN_USER");

        if ("ADMIN".equals(loginUser.getRole())) {
            return "admin/layout/main";
        }

        return "staff/layout/main";
    }

    @PostMapping("/save")
    public String saveProduct(@ModelAttribute("product") @Valid Product product,
            BindingResult result, Model model, HttpSession session) {
        User loginUser = (User) session.getAttribute("LOGIN_USER");
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getAllCategories());
            model.addAttribute("body", "/product/form.jsp");
            return "layout/main";
        }
        if (product.getId() == 0) {
            productService.addProduct(product);
        } else {
            productService.updateProduct(product);
        }
        model.addAttribute("body", "/product/list.jsp");

        if ("ADMIN".equals(loginUser.getRole())) {
            return "redirect:/product/admin/list";
        }

        return "redirect:/product/staff/list";

    }

    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable int id, Model model, HttpSession session) {
        User loginUser = (User) session.getAttribute("LOGIN_USER");
        productService.deleteProduct(id);
        model.addAttribute("body", "/product/list.jsp");
        if ("ADMIN".equals(loginUser.getRole())) {
            return "redirect:/product/admin/list";
        }

        return "redirect:/product/staff/list";
    }

    @GetMapping("/search")
    public String searchProducts(
            @RequestParam("keyword") String keyword,
            Model model,
            HttpSession session) {

        User loginUser = (User) session.getAttribute("LOGIN_USER");

        List<Product> products = productService.searchProducts(keyword);

        model.addAttribute("products", products);
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("keyword", keyword);
        model.addAttribute("body", "/product/list.jsp");

        if ("ADMIN".equals(loginUser.getRole())) {
            return "redirect:/product/admin/list";
        }

        if ("STAFF".equals(loginUser.getRole())) {
            return "redirect:/product/staff/list";
        }
        return "layout/main";
    }

    @GetMapping("/category/{id}")
    public String category(@PathVariable int id, Model model) {

        Category category = categoryService.getById(id);

        List<Product> products
                = productService.getProductsByCategory(id);

        model.addAttribute("products", products);
        model.addAttribute("category", category);

        switch (category.getName()) {

            case "iPhone":
                model.addAttribute("body", "category/iphone.jsp");
                return "layout/main";

            case "Mac":
                model.addAttribute("body", "category/mac.jsp");
                return "layout/main";

            case "iPad":
                model.addAttribute("body", "category/ipad.jsp");
                return "layout/main";

            case "Phụ kiện":
                model.addAttribute("body", "category/accessory.jsp");
                return "layout/main";

            default:
                return "category/default";
        }
    }

    @GetMapping("/{id}")
    public String productDetail(@PathVariable("id") int id,
            Model model) {

        Product product = productService.getProductById(id);

        if (product == null) {
            return "redirect:/";
        }

        model.addAttribute("product", product);

        model.addAttribute("body", "product/detail.jsp");
        return "layout/main";
    }

    @GetMapping("/featured/{id}")
    public String toggleFeatured(@PathVariable int id,
            HttpSession session,Model model) {

        Product product = productService.getProductById(id);

        productService.updateFeatured(
                id,
                !product.isFeatured()
        );

        User loginUser
                = (User) session.getAttribute("LOGIN_USER");

        model.addAttribute("body", "/product/list.jsp");
        if ("ADMIN".equals(loginUser.getRole())) {
            return "redirect:/product/admin/list";
        }

        return "redirect:/product/staff/list";
    }
}
