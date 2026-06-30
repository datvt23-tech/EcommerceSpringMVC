<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="container d-flex justify-content-center align-items-center py-5" style="min-height: 75vh;">
    <div class="card shadow p-4 border-0" style="width: 450px;">
        <h3 class="text-center mb-2">QUÊN MẬT KHẨU?</h3>
        <p class="text-center text-muted mb-4 small">Vui lòng nhập tên tài khoản của bạn để đặt lại mật khẩu mới.</p>

        <c:if test="${not empty error}">
            <div class="alert alert-danger text-center">
                ${error}
            </div>
        </c:if>

        <form action="${pageContext.request.contextPath}/forgot-password" method="POST">
            <div class="mb-3">
                <label class="form-label">Tài khoản cần khôi phục</label>
                <input type="text" name="username" class="form-control" required placeholder="Nhập tên đăng nhập của bạn">
            </div>

            <div class="mb-3">
                <label class="form-label">Mật khẩu mới</label>
                <input type="password" name="newPassword" class="form-control" required placeholder="Nhập mật khẩu mới">
            </div>

            <div class="mb-4">
                <label class="form-label">Xác nhận mật khẩu mới</label>
                <input type="password" name="confirmNewPassword" class="form-control" required placeholder="Nhập lại mật khẩu mới">
            </div>

            <button type="submit" class="btn btn-warning w-100 mb-3 fw-bold text-dark">Đặt lại mật khẩu</button>

            <div class="text-center text-muted small">
                Nhớ ra mật khẩu rồi? <a href="${pageContext.request.contextPath}/login" class="text-decoration-none fw-bold">Quay lại đăng nhập</a>
            </div>
        </form>
    </div>
</div>
