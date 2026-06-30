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
import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/product") // Gom cụm tất cả đường dẫn bắt đầu bằng /product
public class ProductController {

    @Autowired
    private ProductService productService; // Chỉ gọi tầng Service

    @Autowired
    private CategoryService categoryService;

    private User getLoggedInUser(HttpSession session) {
        try {
            return (User) session.getAttribute("LOGIN_USER");
        } catch (Exception e) {
            return null;
        }
    }

    private String productLayout(User user) {
        if (user == null) {
            return "layout/main";
        }
        if ("ADMIN".equals(user.getRole())) {
            return "admin/layout/main";
        }
        if ("STAFF".equals(user.getRole())) {
            return "staff/layout/main";
        }
        return "layout/main";
    }

    private String productRedirect(User user) {
        if (user == null) {
            return "redirect:/login";
        }
        if ("ADMIN".equals(user.getRole())) {
            return "redirect:/product/admin/list";
        }
        if ("STAFF".equals(user.getRole())) {
            return "redirect:/product/staff/list";
        }
        return "redirect:/product/products";
    }

    private void prepareProductList(Model model, List<Product> products) {
        model.addAttribute("products", products);
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("body", "/product/list.jsp");
    }

    private String showProductError(Model model, User user, String message) {
        model.addAttribute("error", message);
        prepareProductList(model, Collections.emptyList());
        return productLayout(user);
    }

    @GetMapping("/products")
    public String showProductsBySeries(Model model) {
        try {
            // Lọc sản phẩm theo Series khách bấm vào
            List<Product> products = productService.getProductsByCategory(0);
            prepareProductList(model, products);
            return "layout/main";
        } catch (Exception e) {
            return showProductError(model, null, "Không thể tải danh sách sản phẩm. Vui lòng thử lại sau.");
        }
    }

    // R - Read: Hiển thị danh sách sản phẩm
    @GetMapping("admin/list")
    public String adminListProducts(Model model) {
        try {
            prepareProductList(model, productService.getAllProducts());
            return "admin/layout/main";
        } catch (Exception e) {
            return showProductError(model, null, "Không thể tải danh sách sản phẩm cho admin.");
        }
    }

    @GetMapping("staff/list")
    public String staffListProducts(Model model) {
        try {
            prepareProductList(model, productService.getAllProducts());
            return "staff/layout/main";
        } catch (Exception e) {
            return showProductError(model, null, "Không thể tải danh sách sản phẩm cho nhân viên.");
        }
    }

    // C - Create: Hiển thị form thêm mới
    @GetMapping("/add")
    public String showAddForm(Model model, HttpSession session) {
        try {
            User loginUser = getLoggedInUser(session);
            if (loginUser == null) {
                return "redirect:/login";
            }
            model.addAttribute("product", new Product());
            model.addAttribute("categories", categoryService.getAllCategories());
            model.addAttribute("body", "/product/form.jsp");
            return productLayout(loginUser);
        } catch (Exception e) {
            return showProductError(model, getLoggedInUser(session), "Không thể mở form thêm sản phẩm.");
        }
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model, HttpSession session) {
        try {
            User loginUser = getLoggedInUser(session);
            if (loginUser == null) {
                return "redirect:/login";
            }
            Product product = productService.getProductById(id);
            if (product == null) {
                return productRedirect(loginUser);
            }
            model.addAttribute("product", product);
            model.addAttribute("categories", categoryService.getAllCategories());
            model.addAttribute("body", "/product/form.jsp");
            return productLayout(loginUser);
        } catch (Exception e) {
            return showProductError(model, getLoggedInUser(session), "Không thể mở form cập nhật sản phẩm.");
        }
    }

    @PostMapping("/save")
    public String saveProduct(@ModelAttribute("product") @Valid Product product,
            BindingResult result, Model model, HttpSession session) {
        User loginUser = getLoggedInUser(session);
        try {
            if (loginUser == null) {
                return "redirect:/login";
            }
            if (result.hasErrors()) {
                model.addAttribute("categories", categoryService.getAllCategories());
                model.addAttribute("body", "/product/form.jsp");
                return productLayout(loginUser);
            }
            boolean success = product.getId() == 0
                    ? productService.addProduct(product)
                    : productService.updateProduct(product);
            if (!success) {
                model.addAttribute("error", "Không thể lưu sản phẩm. Vui lòng kiểm tra lại dữ liệu.");
                model.addAttribute("categories", categoryService.getAllCategories());
                model.addAttribute("body", "/product/form.jsp");
                return productLayout(loginUser);
            }
            return productRedirect(loginUser);
        } catch (Exception e) {
            model.addAttribute("error", "Đã xảy ra lỗi khi lưu sản phẩm.");
            model.addAttribute("categories", categoryService.getAllCategories());
            model.addAttribute("body", "/product/form.jsp");
            return productLayout(loginUser);
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable int id, Model model, HttpSession session) {
        User loginUser = getLoggedInUser(session);
        try {
            if (loginUser == null) {
                return "redirect:/login";
            }
            if (!productService.deleteProduct(id)) {
                model.addAttribute("error", "Không thể xóa sản phẩm.");
            }
            return productRedirect(loginUser);
        } catch (Exception e) {
            return showProductError(model, loginUser, "Đã xảy ra lỗi khi xóa sản phẩm.");
        }
    }

    @GetMapping("/search")
    public String searchProducts(
            @RequestParam("keyword") String keyword,
            Model model,
            HttpSession session) {
        User loginUser = getLoggedInUser(session);
        try {
            List<Product> products = productService.searchProducts(keyword);
            prepareProductList(model, products);
            model.addAttribute("keyword", keyword);
            return productLayout(loginUser);
        } catch (Exception e) {
            return showProductError(model, loginUser, "Không thể tìm kiếm sản phẩm.");
        }
    }

    @GetMapping("/category/{id}")
    public String category(@PathVariable int id, Model model) {
        try {
            Category category = categoryService.getById(id);
            if (category == null) {
                return "redirect:/product/products";
            }

            List<Product> products = productService.getProductsByCategory(id);
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
        } catch (Exception e) {
            return showProductError(model, null, "Không thể tải sản phẩm theo danh mục.");
        }
    }

    @GetMapping("/{id}")
    public String productDetail(@PathVariable("id") int id, Model model) {
        try {
            Product product = productService.getProductById(id);
            if (product == null) {
                return "redirect:/";
            }
            model.addAttribute("product", product);
            model.addAttribute("body", "product/detail.jsp");
            return "layout/main";
        } catch (Exception e) {
            model.addAttribute("error", "Không thể tải chi tiết sản phẩm.");
            return "redirect:/";
        }
    }

    @GetMapping("/featured/{id}")
    public String toggleFeatured(@PathVariable int id, HttpSession session, Model model) {
        User loginUser = getLoggedInUser(session);
        try {
            if (loginUser == null) {
                return "redirect:/login";
            }
            Product product = productService.getProductById(id);
            if (product == null) {
                model.addAttribute("error", "Không tìm thấy sản phẩm.");
                return productRedirect(loginUser);
            }
            if (!productService.updateFeatured(id, !product.isFeatured())) {
                model.addAttribute("error", "Không thể cập nhật trạng thái nổi bật.");
            }
            return productRedirect(loginUser);
        } catch (Exception e) {
            return showProductError(model, loginUser, "Đã xảy ra lỗi khi cập nhật sản phẩm nổi bật.");
        }
    }
}
