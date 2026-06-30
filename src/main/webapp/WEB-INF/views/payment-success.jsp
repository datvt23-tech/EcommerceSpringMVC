<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thanh toán thành công — TechShop</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background: #f0f2f5; }
        .success-card { max-width: 560px; margin: 4rem auto; text-align: center; }
        .checkmark { width: 80px; height: 80px; border-radius: 50%; background: #22c55e; display: flex; align-items: center; justify-content: center; font-size: 2.5rem; margin: 0 auto 1.5rem; }
    </style>
</head>
<body>
<div class="success-card">
    <div class="card border-0 shadow-sm p-4">
        <div class="checkmark">✓</div>
        <h3 class="fw-bold text-success mb-1">Thanh toán thành công!</h3>
        <p class="text-muted mb-4">
            <c:choose>
                <c:when test="${order.paymentMethod eq 'CARD'}">Thẻ của bạn đã được ghi nợ thành công.</c:when>
                <c:otherwise>Đơn hàng COD của bạn đã được xác nhận.</c:otherwise>
            </c:choose>
        </p>

        <div class="card bg-light border-0 text-start mb-4 p-3">
            <div class="row g-2 small">
                <div class="col-6 text-muted">Mã đơn hàng</div>
                <div class="col-6 fw-bold">#${order.id}</div>
                <div class="col-6 text-muted">Người nhận</div>
                <div class="col-6">${order.customerName}</div>
                <div class="col-6 text-muted">Địa chỉ</div>
                <div class="col-6">${order.address}</div>
                <div class="col-6 text-muted">Phương thức</div>
                <div class="col-6">
                    <c:choose>
                        <c:when test="${order.paymentMethod eq 'CARD'}">💳 Thẻ ngân hàng</c:when>
                        <c:otherwise>🚚 COD</c:otherwise>
                    </c:choose>
                </div>
                <div class="col-6 text-muted">Trạng thái</div>
                <div class="col-6"><span class="badge bg-success">${order.status}</span></div>
                <div class="col-6 text-muted fw-bold">Tổng tiền</div>
                <div class="col-6 fw-bold text-danger">
                    <fmt:formatNumber value="${order.totalAmount}" maxFractionDigits="0"/> VNĐ
                </div>
            </div>
        </div>

        <div class="d-grid gap-2">
            <a href="${pageContext.request.contextPath}/orders" class="btn btn-primary">Xem đơn hàng của tôi</a>
            <a href="${pageContext.request.contextPath}/" class="btn btn-outline-secondary">Tiếp tục mua sắm</a>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
