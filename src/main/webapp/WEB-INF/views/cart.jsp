<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Giỏ Hàng Của Bạn</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body class="bg-light">

        <div class="container my-5">
            <h2 class="fw-bold text-dark mb-4 text-center">🛒 GIỎ HÀNG CỦA BẠN</h2>

            <div class="card shadow-sm border-0 rounded-3">
                <div class="card-body p-4">
                    <table class="table table-hover align-middle">
                        <thead class="table-dark">
                            <tr>
                                <th>Sản Phẩm</th>
                                <th>Giá Bán</th>
                                <th style="width: 15%" class="text-center">Số Lượng</th>
                                <th>Tổng Phụ</th>
                                <th class="text-center">Thao tác</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:set var="totalPrice" value="0" />
                            
                            <c:choose>
                                <c:when test="${not empty cart}">                                
                                    <c:forEach items="${cart}" var="item">
                                        <c:set var="totalPrice" value="${totalPrice + (item.product.price * item.quantity)}" />
                                        <tr>
                                            <td class="fw-bold">${item.product.name}</td>
                                            <td><fmt:formatNumber value="${item.product.price}" maxFractionDigits="0"/> VNĐ</td>
                                            
                                            <td>
                                                <div class="input-group input-group-sm mx-auto" style="max-width: 110px;">
                                                    <a href="${pageContext.request.contextPath}/cart/update?productId=${item.product.id}&action=dec" 
                                                       class="btn btn-outline-secondary fw-bold ${item.quantity <= 1 ? 'disabled' : ''}">-</a>
                                                    
                                                    <input type="text" class="form-control text-center bg-white" value="${item.quantity}" readonly>
                                                    
                                                    <a href="${pageContext.request.contextPath}/cart/update?productId=${item.product.id}&action=inc" 
                                                       class="btn btn-outline-secondary fw-bold">+</a>
                                                </div>
                                            </td>
                                            
                                            <td class="text-danger fw-bold">
                                                <fmt:formatNumber value="${item.product.price * item.quantity}" maxFractionDigits="0"/> VNĐ
                                            </td>
                                            
                                            <td class="text-center">
                                                <a href="${pageContext.request.contextPath}/cart/remove?productId=${item.product.id}" 
                                                   class="btn btn-sm btn-danger px-3 rounded-pill"
                                                   onclick="return confirm('Bạn có chắc chắn muốn xóa sản phẩm này khỏi giỏ hàng?')">
                                                    Xóa
                                                </a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <tr>
                                        <td colspan="5" class="text-center py-5 text-muted fs-5">Giỏ hàng của bạn đang trống trơn!</td>
                                    </tr>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>

                    <div class="d-flex justify-content-between align-items-center mt-4">
                        <a href="${pageContext.request.contextPath}/" class="btn btn-outline-secondary">← Tiếp tục mua sắm</a>
                        <div class="text-end">
                            <h4 class="fw-bold">Tổng tiền thanh toán: 
                                <span class="text-danger"><fmt:formatNumber value="${totalPrice}" maxFractionDigits="0"/> VNĐ</span>
                            </h4>
                        </div>
                    </div>

                    <c:if test="${not empty cart}">
                        <hr class="my-4">
                        <h5 class="fw-bold mb-3">Thông tin nhận hàng</h5>
                        <form action="${pageContext.request.contextPath}/checkout" method="POST" class="row g-3">
                            <div class="col-md-4">
                                <label class="form-label">Họ tên người nhận</label>
                                <input type="text" name="customerName" class="form-control" required
                                       value="${sessionScope.LOGIN_USER.fullName}">
                            </div>
                            <div class="col-md-3">
                                <label class="form-label">Số điện thoại</label>
                                <input type="text" name="phone" class="form-control" required placeholder="Nhập số điện thoại">
                            </div>
                            <div class="col-md-5">
                                <label class="form-label">Địa chỉ giao hàng</label>
                                <input type="text" name="address" class="form-control" required placeholder="Nhập địa chỉ nhận hàng">
                            </div>
                            <div class="col-12 text-end">
                                <button type="submit" class="btn btn-primary btn-lg px-5 rounded-pill shadow">Đặt hàng</button>
                            </div>
                        </form>
                    </c:if>

                </div>
            </div>
        </div>

    </body>
</html>