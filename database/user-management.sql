ALTER TABLE users
ADD COLUMN active TINYINT(1) NOT NULL DEFAULT 1;

UPDATE users
SET active = 1
WHERE active IS NULL;

-- Role hợp lệ trong hệ thống:
-- CUSTOMER: khách hàng đã đăng ký
-- STAFF: nhân viên vận hành
-- ADMIN: quản trị viên tối cao
