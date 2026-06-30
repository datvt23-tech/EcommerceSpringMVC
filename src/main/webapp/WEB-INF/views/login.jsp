<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="container d-flex justify-content-center align-items-center py-5" style="min-height: 75vh;">
    <div class="card shadow p-4 border-0" style="width: 400px;">
        <h3 class="text-center mb-4">ĐĂNG NHẬP</h3>

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

        <form action="${pageContext.request.contextPath}/login" method="POST">
            <div class="mb-3">
                <label class="form-label">Tài khoản</label>
                <input type="text" name="username" class="form-control" required placeholder="Nhập tên đăng nhập">
            </div>

            <div class="mb-3">
                <label class="form-label">Mật khẩu</label>
                <input type="password" name="password" class="form-control" required placeholder="Nhập mật khẩu">
                <a href="${pageContext.request.contextPath}/forgot-password" class="text-decoration-none small">Quên mật khẩu?</a>
            </div>
            <button type="submit" class="btn btn-primary w-100 mb-2">Đăng nhập</button>
            <a href="${pageContext.request.contextPath}/register" class="btn btn-outline-primary w-100 text-center">Đăng ký</a>
        </form>
    </div>
</div>
