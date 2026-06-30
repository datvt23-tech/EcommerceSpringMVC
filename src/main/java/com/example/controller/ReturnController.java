package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.annotation.MultipartConfig; // 📸 MỚI THÊM THƯ VIỆN CẤU HÌNH MULTIPART
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/staff/return")
@MultipartConfig // 📸 CHÈN THÊM DÒNG NÀY ĐỂ KÍCH HOẠT TÍNH NĂNG NHẬN FILE CHO CONTROLLER
public class ReturnController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping(method = RequestMethod.GET)
    public String viewReturnPage() {
        return "admin/return-product";
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String searchOrder(@RequestParam("orderId") String orderId, Model model) {
        try {
            String sql = "SELECT od.product_id, p.product_name, od.quantity, od.price " +
                         "FROM order_details od " +
                         "JOIN products p ON od.product_id = p.id " +
                         "WHERE od.order_id = ?";
            List<Map<String, Object>> orderDetails = jdbcTemplate.queryForList(sql, orderId);
            if (orderDetails.isEmpty()) {
                model.addAttribute("error", "Không tìm thấy đơn hàng #" + orderId);
            } else {
                model.addAttribute("orderDetails", orderDetails);
                model.addAttribute("orderId", orderId);
            }
        } catch (Exception e) {
            model.addAttribute("error", "Lỗi: " + e.getMessage());
        }
        return "admin/return-product";
    }

    // Xử lý Thu hồi - Đổi trả có KÈM HÌNH ẢNH (Giữ nguyên vẹn 100% code của bạn)
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public String submitReturn(HttpServletRequest request, Model model) {
        try {
            String orderId = request.getParameter("orderId");
            String[] productIds = request.getParameterValues("productId");

            if (!(request instanceof MultipartHttpServletRequest)) {
                model.addAttribute("error", "Request không hợp lệ hoặc thiếu dữ liệu upload.");
                return "admin/return-product";
            }

            // Ép kiểu request về Multipart để xử lý file ảnh
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

            // Đường dẫn thư mục lưu ảnh trong dự án của bạn (Tomcat Server)
            String uploadDir = request.getServletContext().getRealPath("/") + "assets/images/returns/";

            if (productIds != null) {
                for (int i = 0; i < productIds.length; i++) {
                    String productId = productIds[i];
                    String returnQtyStr = request.getParameter("returnQuantity_" + productId);
                    String returnType = request.getParameter("returnType_" + productId);
                    String reason = request.getParameter("reason_" + productId);

                    // Hứng file ảnh theo tên của từng sản phẩm
                    MultipartFile imageFile = multipartRequest.getFile("image_" + productId);
                    String imageName = null;

                    int returnQty = (returnQtyStr != null && !returnQtyStr.isEmpty()) ? Integer.parseInt(returnQtyStr) : 0;

                    if (returnQty > 0) {
                        // Xử lý lưu file ảnh nếu nhân viên có chọn ảnh
                        if (imageFile != null && !imageFile.isEmpty()) {
                            // Tạo thư mục lưu nếu chưa tồn tại
                            File dir = new File(uploadDir);
                            if (!dir.exists()) dir.mkdirs();

                            // Đổi tên file thành chuỗi ngẫu nhiên tránh trùng lặp file trùng tên
                            imageName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
                            File serverFile = new File(dir.getAbsolutePath() + File.separator + imageName);

                            // Lưu file xuống ổ cứng server
                            imageFile.transferTo(serverFile);
                        }

                        // Thao tác 1: Lưu vào DB (Có kèm tên file ảnh ở cột cuối)
                        String insertLogSql = "INSERT INTO return_log (order_id, product_id, return_quantity, return_type, reason, image_url) VALUES (?, ?, ?, ?, ?, ?)";
                        jdbcTemplate.update(insertLogSql, orderId, productId, returnQty, returnType, reason, imageName);

                        // Thao tác 2: Cộng lại kho nếu khách chọn trả hàng
                        if ("RETURN".equals(returnType)) {
                            String updateStockSql = "UPDATE products SET quantity = quantity + ? WHERE id = ?";
                            jdbcTemplate.update(updateStockSql, returnQty, productId);
                        }
                    }
                }
                model.addAttribute("success", "Đã tiếp nhận yêu cầu thu hồi đổi trả kèm hình ảnh minh chứng thành công!");
            }
        } catch (NumberFormatException e) {
            model.addAttribute("error", "Số lượng đổi trả không hợp lệ.");
        } catch (Exception e) {
            model.addAttribute("error", "Xử lý thất bại: " + e.getLocalizedMessage());
        }

        return "admin/return-product";
    }
}
