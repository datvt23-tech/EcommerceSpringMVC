DROP DATABASE IF EXISTS ecommerce_db;
CREATE DATABASE ecommerce_db
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE ecommerce_db;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(150) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    username VARCHAR(80) NOT NULL UNIQUE,
    password VARCHAR(120) NOT NULL,
    role ENUM('CUSTOMER', 'STAFF', 'ADMIN') NOT NULL DEFAULT 'CUSTOMER',
    active TINYINT(1) NOT NULL DEFAULT 1,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_users_role (role),
    INDEX idx_users_active (active)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE category (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE product (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price DOUBLE NOT NULL DEFAULT 0,
    description TEXT NULL,
    image_url VARCHAR(500) NULL,
    category_id INT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_product_name (name),
    INDEX idx_product_category_id (category_id),
    CONSTRAINT fk_product_category
        FOREIGN KEY (category_id) REFERENCES category(id)
        ON DELETE SET NULL
        ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    customer_name VARCHAR(150) NOT NULL,
    phone VARCHAR(30) NOT NULL,
    address VARCHAR(255) NOT NULL,
    total_amount DOUBLE NOT NULL DEFAULT 0,
    status ENUM('Đang xử lý', 'Đã xác nhận', 'Đóng gói', 'Đang giao', 'Đã giao', 'Đã hủy')
        NOT NULL DEFAULT 'Đang xử lý',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_orders_user_id (user_id),
    INDEX idx_orders_status (status),
    CONSTRAINT fk_orders_user
        FOREIGN KEY (user_id) REFERENCES users(id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE order_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    product_id INT NOT NULL,
    product_name VARCHAR(255) NOT NULL,
    price DOUBLE NOT NULL DEFAULT 0,
    quantity INT NOT NULL DEFAULT 1,
    INDEX idx_order_items_order_id (order_id),
    INDEX idx_order_items_product_id (product_id),
    CONSTRAINT fk_order_items_order
        FOREIGN KEY (order_id) REFERENCES orders(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    CONSTRAINT fk_order_items_product
        FOREIGN KEY (product_id) REFERENCES product(id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE support_tickets (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    customer_name VARCHAR(150) NOT NULL,
    subject VARCHAR(255) NOT NULL,
    message TEXT NOT NULL,
    reply TEXT NULL,
    status ENUM('Mới', 'Đang xử lý', 'Đã phản hồi', 'Đã đóng') NOT NULL DEFAULT 'Mới',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_support_tickets_user_id (user_id),
    INDEX idx_support_tickets_status (status),
    CONSTRAINT fk_support_tickets_user
        FOREIGN KEY (user_id) REFERENCES users(id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO users (full_name, email, username, password, role, active) VALUES
('Quản trị viên', 'admin@techshop.vn', 'admin', '123456', 'ADMIN', 1),
('Nhân viên vận hành', 'staff@techshop.vn', 'staff', '123456', 'STAFF', 1),
('Khách hàng mẫu', 'customer@techshop.vn', 'customer', '123456', 'CUSTOMER', 1);

INSERT INTO category (name) VALUES
('iPhone'),
('Mac'),
('iPad'),
('Phụ kiện');

INSERT INTO product (name, price, description, image_url, category_id) VALUES
('iPhone 17 Series', 34990000, 'Bảng giá hiện tại đang có iPhone 17 Pro Max newseal bản VNA theo từng màu thực tế.', 'https://tam300.com/assets/images/iphone-17-pro-max.webp', 1),
('iPhone 16 Series', 24990000, 'Bao gồm máy LLA đã kích hoạt, new trần và newseal VNA theo từng model đang có trong file giá.', 'https://tam300.com/assets/images/iphone-16-pro-max.webp', 1),
('iPhone 15 Series', 18990000, 'Đầy đủ 15, 15 Plus, 15 Pro và 15 Pro Max theo bảng giá LLA cập nhật.', 'https://tam300.com/assets/images/iphone-15-pro-max.webp', 1),
('iPhone 14 Series', 13990000, 'Có iPhone 14, 14 Plus, 14 Pro và 14 Pro Max theo bảng giá LLA cập nhật.', 'https://tam300.com/assets/images/iphone-14-pro-max.webp', 1);

INSERT INTO orders (user_id, customer_name, phone, address, total_amount, status) VALUES
(3, 'Khách hàng mẫu', '0909000000', '123 Nguyễn Huệ, Quận 1, TP.HCM', 34990000, 'Đang xử lý');

INSERT INTO order_items (order_id, product_id, product_name, price, quantity) VALUES
(1, 1, 'iPhone 17 Series', 34990000, 1);

INSERT INTO support_tickets (user_id, customer_name, subject, message, reply, status) VALUES
(3, 'Khách hàng mẫu', 'Hỏi về bảo hành', 'Sản phẩm iPhone được bảo hành như thế nào?', 'TechShop hỗ trợ bảo hành theo chính sách chính hãng và đổi trả theo điều kiện cửa hàng.', 'Đã phản hồi');
