<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="container py-5">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <div>
            <h2 class="fw-bold mb-1">Đơn hàng của tôi</h2>
            <p class="text-muted mb-0">Theo dõi trạng thái xử lý và giao hàng.</p>
        </div>
        <a href="${pageContext.request.contextPath}/" class="btn btn-outline-secondary">Tiếp tục mua sắm</a>
    </div>

    <c:choose>
        <c:when test="${not empty orders}">
            <div class="row g-4">
                <c:forEach items="${orders}" var="order">
                    <div class="col-12">
                        <div class="card border-0 shadow-sm">
                            <div class="card-header bg-white d-flex flex-wrap justify-content-between gap-2">
                                <div>
                                    <span class="fw-bold">Đơn #${order.id}</span>
                                    <span class="text-muted ms-2">${order.createdAt}</span>
                                </div>
                                <span class="badge bg-primary">${order.status}</span>
                            </div>
                            <div class="card-body">
                                <div class="row g-3 mb-3">
                                    <div class="col-md-4"><b>Người nhận:</b> ${order.customerName}</div>
                                    <div class="col-md-3"><b>SĐT:</b> ${order.phone}</div>
                                    <div class="col-md-5"><b>Địa chỉ:</b> ${order.address}</div>
                                </div>
                                <table class="table table-sm align-middle">
                                    <thead>
                                        <tr>
                                            <th>Sản phẩm</th>
                                            <th>Giá</th>
                                            <th>Số lượng</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${order.items}" var="item">
                                            <tr>
                                                <td>${item.productName}</td>
                                                <td><fmt:formatNumber value="${item.price}" maxFractionDigits="0"/> VNĐ</td>
                                                <td>${item.quantity}</td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                                <div class="text-end fw-bold text-danger">
                                    Tổng tiền: <fmt:formatNumber value="${order.totalAmount}" maxFractionDigits="0"/> VNĐ
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:when>
        <c:otherwise>
            <div class="alert alert-info">Bạn chưa có đơn hàng nào.</div>
        </c:otherwise>
    </c:choose>
</div>
