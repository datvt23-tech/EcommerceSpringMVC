package com.example.dao;

import com.example.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

/**
 *
 * @author votandat
 */
@Repository
public class ProductDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Tạo một RowMapper dùng chung để tránh lặp lại code ở hàm getAll và getById
    private final RowMapper<Product> productRowMapper = new RowMapper<Product>() {
        @Override
        public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
            Product product = new Product();
            product.setId(rs.getInt("id"));
            product.setName(rs.getString("name"));
            product.setPrice(rs.getDouble("price"));
            product.setDescription(rs.getString("description"));
            product.setImageUrl(rs.getString("image_url"));
            product.setCategoryId(rs.getInt("category_id"));
            product.setCategoryName(rs.getString("category_name"));
            product.setFeatured(rs.getBoolean("featured"));
            return product;
        }
    };

    // 1. Lấy toàn bộ sản phẩm
    public List<Product> getAll() {
        String sql = "SELECT p.*, c.name AS category_name\n"
                + "FROM product p\n"
                + "LEFT JOIN category c\n"
                + "ON p.category_id = c.id";
        try {
            return jdbcTemplate.query(sql, productRowMapper);
        } catch (DataAccessException e) {
            return Collections.emptyList();
        }
    }

    // 2. Tìm kiếm một sản phẩm dựa vào ID (Có try-catch an toàn)
    public Product getById(int id) {
        String sql = "SELECT p.*, c.name AS category_name "
                + "FROM product p "
                + "LEFT JOIN category c ON p.category_id = c.id "
                + "WHERE p.id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, productRowMapper, id);
        } catch (DataAccessException e) {
            // Trả về null nếu không tìm thấy ID sản phẩm trong DB thay vì làm sập ứng dụng

            return null;
        }
    }

    // 3. Thêm sản phẩm mới (Trả về true nếu thêm thành công)
    public boolean insert(Product product) {
        String sql = "INSERT INTO product (name, price, description, image_url, category_id) VALUES (?, ?, ?, ?, ?)";
        try {
            int rowsAffected = jdbcTemplate.update(sql,
                    product.getName(),
                    product.getPrice(),
                    product.getDescription(),
                    product.getImageUrl(),
                    product.getCategoryId()
            );
            return rowsAffected > 0;
        } catch (DataAccessException e) {
            return false;
        }
    }

    // 4. Xóa sản phẩm (Trả về true nếu xóa thành công)
    public boolean delete(int id) {
        String sql = "DELETE FROM product WHERE id = ?";
        try {
            int rowsAffected = jdbcTemplate.update(sql, id);
            return rowsAffected > 0;
        } catch (DataAccessException e) {
            return false;
        }
    }

    // 5. Cập nhật thông tin sản phẩm (Đã bổ sung sửa cả ảnh image_url)
    public boolean update(Product product) {
        String sql = "UPDATE product SET name = ?, price = ?, description = ?, image_url = ?, category_id = ? WHERE id = ?";
        try {
            int rowsAffected = jdbcTemplate.update(sql,
                    product.getName(),
                    product.getPrice(),
                    product.getDescription(),
                    product.getImageUrl(), // Bổ sung cập nhật ảnh để không bị mất link ảnh cũ
                    product.getCategoryId(),
                    product.getId()
            );
            return rowsAffected > 0;
        } catch (DataAccessException e) {
            return false;
        }
    }

    public List<Product> searchByName(String keyword) {
        String sql = "SELECT p.*, c.name AS category_name "
                + "FROM product p "
                + "LEFT JOIN category c ON p.category_id = c.id "
                + "WHERE p.name LIKE ?";
        try {
            return jdbcTemplate.query(sql, productRowMapper, "%" + keyword + "%");
        } catch (DataAccessException e) {
            return Collections.emptyList();
        }
    }

    public List<Product> getProductsByCategory(int categoryId) {
        if (categoryId <= 0) {
            return getAll();
        }

        String sql
                = "SELECT p.*, c.name category_name "
                + "FROM product p "
                + "LEFT JOIN category c ON p.category_id=c.id "
                + "WHERE p.category_id=?";

        try {
            return jdbcTemplate.query(
                    sql,
                    productRowMapper,
                    categoryId);
        } catch (DataAccessException e) {
            return Collections.emptyList();
        }
    }

    public List<Product> getFeaturedProducts() {
        String sql = "SELECT * FROM product WHERE featured = true";
        try {
            return jdbcTemplate.query(
                    sql,
                    new BeanPropertyRowMapper<>(Product.class)
            );
        } catch (DataAccessException e) {
            return Collections.emptyList();
        }
    }

    public boolean updateFeatured(int id, boolean featured) {

        String sql
                = "UPDATE product SET featured = ? WHERE id = ?";

        try {
            return jdbcTemplate.update(sql, featured, id) > 0;
        } catch (DataAccessException e) {
            return false;
        }
    }
}
