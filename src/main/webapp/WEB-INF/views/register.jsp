<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="container d-flex justify-content-center align-items-center py-5" style="min-height: 75vh;">
    <div class="card shadow p-4 border-0" style="width: 450px;">
        <h3 class="text-center mb-4">TẠO TÀI KHOẢN KHÁCH HÀNG</h3>

        <c:if test="${not empty error}">
            <div class="alert alert-danger text-center">
                ${error}
            </div>
        </c:if>

        <c:if test="${not empty success}">
            <div class="alert alert-success text-center">
                ${success}
            </div>
        </c:if>

        <form action="${pageContext.request.contextPath}/register" method="POST">
            <div class="mb-3">
                <label class="form-label">Họ và tên</label>
                <input type="text" name="fullName" class="form-control" required placeholder="Nhập họ và tên của bạn">
            </div>

            <div class="mb-3">
                <label class="form-label">Địa chỉ Email</label>
                <input type="email" name="email" class="form-control" required placeholder="vi_du@gmail.com">
            </div>

            <div class="mb-3">
                <label class="form-label">Tên đăng nhập</label>
                <input type="text" name="username" class="form-control" required placeholder="Nhập tên đăng nhập">
            </div>

            <div class="mb-3">
                <label class="form-label">Mật khẩu</label>
                <input type="password" name="password" class="form-control" required placeholder="Nhập mật khẩu">
            </div>

            <div class="mb-4">
                <label class="form-label">Xác nhận mật khẩu</label>
                <input type="password" name="confirmPassword" class="form-control" required placeholder="Nhập lại mật khẩu">
            </div>

            <button type="submit" class="btn btn-success w-100 mb-3">Đăng ký ngay</button>

            <div class="text-center text-muted small">
                Đã có tài khoản? <a href="${pageContext.request.contextPath}/login" class="text-decoration-none fw-bold">Đăng nhập tại đây</a>
            </div>
        </form>
    </div>
</div>
