<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quản Lý Thu Đổi Trả Sản Phẩm</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="bg-light">
<div class="container mt-5">
    <div class="card shadow">
        <div class="card-header bg-danger text-white">
            <h3 class="card-title mb-0">🛠️ Quy Trình Tiếp Nhận Thu Hồi & Đổi Trả (Kèm Ảnh)</h3>
        </div>
        <div class="card-body">
            
            <form action="${pageContext.request.contextPath}/staff/return/search" method="GET" class="row g-3 mb-4">
                <div class="col-md-8">
                    <input type="text" name="orderId" class="form-control" placeholder="Nhập Mã Đơn Hàng (Order ID) cần xử lý..." required value="${orderId}">
                </div>
                <div class="col-md-4">
                    <button type="submit" class="btn btn-primary w-100">🔍 Tìm Đơn Hàng</button>
                </div>
            </form>

            <c:if test="${not empty error}">
                <div class="alert alert-danger">${error}</div>
            </c:if>
            <c:if test="${not empty success}">
                <div class="alert alert-success">${success}</div>
            </c:if>

            <c:if test="${not empty orderDetails}">
                <form action="${pageContext.request.contextPath}/staff/return/submit" method="POST" enctype="multipart/form-data">
                    <input type="hidden" name="orderId" value="${orderId}">
                    
                    <h5 class="mb-3 text-secondary">Danh sách sản phẩm trong đơn hàng: #${orderId}</h5>
                    <table class="table table-striped table-bordered align-middle">
                        <thead class="table-dark">
                            <tr>
                                <th>Tên Sản phẩm</th>
                                <th class="text-center">SL Đã Mua</th>
                                <th width="15%">Số Lượng Lỗi</th>
                                <th width="20%">Hình Thức Xử Lý</th>
                                <th width="35%">Lý Do & Hình Ảnh Minh Chứng</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="item" items="${orderDetails}">
                                <tr>
                                    <td>
                                        <strong>${item.product_name}</strong>
                                        <input type="hidden" name="productId" value="${item.product_id}">
                                    </td>
                                    <td class="text-center">${item.quantity}</td>
                                    <td>
                                        <input type="number" name="returnQuantity_${item.product_id}" class="form-control" min="0" max="${item.quantity}" value="0">
                                    </td>
                                    <td>
                                        <select name="returnType_${item.product_id}" class="form-select">
                                            <option value="RETURN">Trả hàng (Hoàn kho & tiền)</option>
                                            <option value="EXCHANGE">Đổi mới (Hàng lỗi đổi cái khác)</option>
                                        </select>
                                    </td>
                                    <td>
                                        <input type="text" name="reason_${item.product_id}" class="form-control mb-2" placeholder="Ví dụ: Nứt màn hình, vỡ vỏ...">
                                        
                                        <label class="form-label small text-muted mb-1 fw-bold">Tải ảnh minh chứng lỗi:</label>
                                        <input type="file" name="image_${item.product_id}" class="form-control form-control-sm" accept="image/*">
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>

                    <div class="text-end mt-3">
                        <button type="submit" class="btn btn-success px-4 py-2 fw-bold">✅ Xác Nhận Thu Hồi & Cập Nhật Kho</button>
                    </div>
                </form>
            </c:if>
        </div>
    </div>
</div>
</body>
</html>