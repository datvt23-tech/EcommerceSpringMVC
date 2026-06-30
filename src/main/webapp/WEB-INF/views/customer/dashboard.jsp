<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="container py-5">
    <div class="mb-4">
        <h2 class="fw-bold">Trang cá nhân khách hàng</h2>
        <p class="text-muted mb-0">Theo dõi mua sắm, đơn hàng và bảo hành tại TechShop.</p>
    </div>

    <div class="row g-4">
        <div class="col-md-4">
            <div class="card h-100 shadow-sm border-0">
                <div class="card-body">
                    <h5 class="fw-bold">Theo dõi đơn hàng</h5>
                    <p class="text-muted">Xem trạng thái Đang xử lý, Đang giao và Đã giao.</p>
                    <a href="${pageContext.request.contextPath}/orders" class="btn btn-dark btn-sm">Xem đơn hàng</a>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card h-100 shadow-sm border-0">
                <div class="card-body">
                    <h5 class="fw-bold">Lịch sử mua hàng</h5>
                    <p class="text-muted">Lưu lại các sản phẩm công nghệ đã mua để tiện tra cứu.</p>
                    <button class="btn btn-outline-secondary btn-sm" disabled>Đang phát triển</button>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card h-100 shadow-sm border-0">
                <div class="card-body">
                    <h5 class="fw-bold">Hỗ trợ</h5>
                    <p class="text-muted">Hỗ trợ thông tin bảo hành điện tử cho thiết bị.</p>
                    <a href="${pageContext.request.contextPath}/support" class="btn btn-dark btn-sm">Gửi yêu cầu</a>
                </div>
            </div>
        </div>
    </div>
</div>
