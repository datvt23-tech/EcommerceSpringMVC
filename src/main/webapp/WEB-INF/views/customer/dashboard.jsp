<%@page contentType="text/html" pageEncoding="UTF-8"%>

<style>
.dashboard-wrapper{
    background:#f5f7fb;
    min-height:100vh;
    padding:40px 0;
}

.profile-card{
    background:#fff;
    border-radius:20px;
    padding:30px;
    box-shadow:0 10px 30px rgba(0,0,0,.08);
    margin-bottom:30px;
}

.avatar-circle{
    width:90px;
    height:90px;
    border-radius:50%;
    background:#0d6efd;
    color:#fff;
    font-size:36px;
    display:flex;
    align-items:center;
    justify-content:center;
}

.stat-card{
    background:#fff;
    border-radius:18px;
    padding:25px;
    box-shadow:0 8px 25px rgba(0,0,0,.08);
    transition:.25s;
    height:100%;
}

.stat-card:hover{
    transform:translateY(-5px);
}

.stat-icon{
    width:55px;
    height:55px;
    border-radius:15px;
    display:flex;
    align-items:center;
    justify-content:center;
    font-size:24px;
    margin-bottom:15px;
}

.menu-card{
    background:#fff;
    border-radius:20px;
    padding:25px;
    box-shadow:0 8px 25px rgba(0,0,0,.08);
    height:100%;
}

.menu-item{
    display:flex;
    justify-content:space-between;
    align-items:center;
    padding:18px;
    border-radius:15px;
    text-decoration:none;
    color:#222;
    transition:.2s;
    margin-bottom:10px;
}

.menu-item:hover{
    background:#f3f7ff;
    color:#0d6efd;
}

.info-item{
    display:flex;
    justify-content:space-between;
    border-bottom:1px solid #eee;
    padding:12px 0;
}
</style>

<div class="dashboard-wrapper">

<div class="container">

    <!-- Header -->
    <div class="profile-card d-flex align-items-center justify-content-between">

        <div class="d-flex align-items-center">

            <div class="avatar-circle">
                <i class="bi bi-person-fill"></i>
            </div>

            <div class="ms-4">
                <h3 class="fw-bold mb-1">
                    Xin chào,
                    ${sessionScope.LOGIN_USER.username}
                </h3>

                <p class="text-muted mb-0">
                    Chào mừng bạn quay trở lại TechShop.
                </p>
            </div>

        </div>

        <a href="${pageContext.request.contextPath}/logout"
           class="btn btn-outline-danger">
            Đăng xuất
        </a>

    </div>

    <!-- Statistics -->
    <div class="row g-4 mb-4">

        <div class="col-md-3">
            <div class="stat-card">

                <div class="stat-icon bg-primary text-white">
                    <i class="bi bi-bag-check"></i>
                </div>

                <h5>Đơn hàng</h5>
                <h2>${totalOrders}</h2>

            </div>
        </div>

        <div class="col-md-3">
            <div class="stat-card">

                <div class="stat-icon bg-success text-white">
                    <i class="bi bi-cart3"></i>
                </div>

                <h5>Giỏ hàng</h5>
                <h2>${cartCount}</h2>

            </div>
        </div>

        <div class="col-md-3">
            <div class="stat-card">

                <div class="stat-icon bg-warning text-dark">
                    <i class="bi bi-headset"></i>
                </div>

                <h5>Yêu cầu hỗ trợ</h5>
                <h2>${supportCount}</h2>

            </div>
        </div>

        <div class="col-md-3">
            <div class="stat-card">

                <div class="stat-icon bg-dark text-white">
                    <i class="bi bi-shield-check"></i>
                </div>

                <h5>Tài khoản</h5>
                <h2>VIP</h2>

            </div>
        </div>

    </div>

    <!-- Content -->
    <div class="row g-4">

        <!-- Menu -->
        <div class="col-lg-7">

            <div class="menu-card">

                <h4 class="fw-bold mb-4">
                    Chức năng
                </h4>

                <a class="menu-item"
                   href="${pageContext.request.contextPath}/orders">

                    <span>
                        <i class="bi bi-bag-check me-2"></i>
                        Đơn hàng của tôi
                    </span>

                    <i class="bi bi-chevron-right"></i>

                </a>

                <a class="menu-item"
                   href="${pageContext.request.contextPath}/cart">

                    <span>
                        <i class="bi bi-cart3 me-2"></i>
                        Giỏ hàng
                    </span>

                    <i class="bi bi-chevron-right"></i>

                </a>

                <a class="menu-item"
                   href="${pageContext.request.contextPath}/support">

                    <span>
                        <i class="bi bi-headset me-2"></i>
                        Trung tâm hỗ trợ
                    </span>

                    <i class="bi bi-chevron-right"></i>

                </a>

                <a class="menu-item"
                   href="#">

                    <span>
                        <i class="bi bi-gear me-2"></i>
                        Cài đặt tài khoản
                    </span>

                    <i class="bi bi-chevron-right"></i>

                </a>

            </div>

        </div>

        <!-- Info -->
        <div class="col-lg-5">

            <div class="menu-card">

                <h4 class="fw-bold mb-4">
                    Thông tin tài khoản
                </h4>

                <div class="info-item">
                    <strong>Tên đăng nhập</strong>
                    <span>${sessionScope.LOGIN_USER.username}</span>
                </div>

                <div class="info-item">
                    <strong>Vai trò</strong>
                    <span>${sessionScope.LOGIN_USER.role}</span>
                </div>

                <div class="info-item">
                    <strong>Trạng thái</strong>
                    <span class="text-success">
                        Hoạt động
                    </span>
                </div>

                <div class="info-item">
                    <strong>Thành viên</strong>
                    <span>TechShop</span>
                </div>

            </div>

        </div>

    </div>

</div>

</div>