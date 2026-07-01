<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
    <main class="dashboard-content">
        <div class="container-fluid px-3 px-lg-4 py-4">
            <div class="page-heading">
                <div class="page-heading-copy">
                    <span class="page-icon"><i class="bi bi-speedometer2" aria-hidden="true"></i></span>
                    <div>
                        <p class="eyebrow mb-1">Overview</p>
                        <h1 class="h3 mb-1">Dashboard</h1>
                        <p class="text-muted mb-0">Monitor performance, sales, users, and support from one clean workspace.</p>
                    </div>
                </div>
            </div>

            <section class="row g-3 mt-1" aria-label="Dashboard metrics">
                <div class="col-12 col-sm-6 col-xl-3">
                    <article class="metric-card metric-primary">
                        <div class="metric-top">
                            <span class="metric-label">Products</span>
                            <span class="metric-icon"><i class="bi bi-bag" aria-hidden="true"></i></span>

                        </div>
                        <div class="metric-value">${totalProducts}</div>

                    </article>
                </div>

                <div class="col-12 col-sm-6 col-xl-3">
                    <article class="metric-card metric-success">
                        <div class="metric-top">
                            <span class="metric-label">Orders</span>
                            <span class="metric-icon"><i class="bi bi-bag-check" aria-hidden="true"></i></span>
                        </div>
                        <div class="metric-value">${totalOrders}</div>

                    </article>
                </div>

                <div class="col-12 col-sm-6 col-xl-3">
                    <article class="metric-card metric-warning">
                        <div class="metric-top">
                            <span class="metric-label">Customers</span>
                            <span class="metric-icon"><i class="bi bi-people" aria-hidden="true"></i></span>
                        </div>
                        <div class="metric-value">${totalCustomers}</div>

                    </article>
                </div>

                <div class="col-12 col-sm-6 col-xl-3">
                    <article class="metric-card metric-danger">
                        <div class="metric-top">
                            <span class="metric-label">Supports</span>
                            <span class="metric-icon"><i class="bi bi-life-preserver" aria-hidden="true"></i></span>
                        </div>
                        <div class="metric-value">${totalSupports}</div>

                    </article>
                </div>
            </section>

            <section class="row g-3 mt-1">
                <div class="col-12 col-xl-8">
                    <div class="panel">
                        <div class="panel-header">
                            <h2 class="section-title">
                                <i class="bi bi-bag-check"></i>
                                Latest Orders
                            </h2>
                        </div>
                        <div class="table-responsive">
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Customer</th>
                                        <th>Total</th>
                                        <th>Status</th>
                                    </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${latestOrders}" var="o">
                                    <tr>
                                        <td>#${o.id}</td>
                                        <td>${o.customerName}</td>
                                        <td>
                                    <fmt:formatNumber
                                        value="${o.totalAmount}"
                                        maxFractionDigits="0"/>
                                    </td>
                                    <td>${o.status}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="panel mt-3">
                        <div class="panel-header">
                            <h2 class="section-title">
                                <i class="bi bi-box-seam"></i>
                                Latest Products
                            </h2>
                        </div>
                        <div class="table-responsive">
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th>Product</th>
                                        <th>Category</th>
                                        <th>Price</th>
                                        <th>Featured</th>
                                    </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${latestProducts}" var="p">
                                    <tr>
                                        <td>
                                            <div class="d-flex align-items-center gap-2">
                                                <img src="${p.imageUrl}"
                                                     style="width:50px;height:50px;object-fit:cover;border-radius:10px;">
                                                <span class="fw-semibold">
                                                    ${p.name}
                                                </span>
                                            </div>
                                        </td>
                                        <td>
                                            ${p.categoryName}
                                        </td>
                                        <td class="fw-bold text-success">
                                    <fmt:formatNumber
                                        value="${p.price}"
                                        maxFractionDigits="0"/>
                                    VNĐ
                                    </td>
                                    <td>
                                    <c:choose>
                                        <c:when test="${p.featured}">
                                            <span class="badge bg-warning text-dark">
                                                Featured
                                            </span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge bg-secondary">
                                                Normal
                                            </span>
                                        </c:otherwise>
                                    </c:choose>
                                    </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="panel mt-3">
                        <div class="panel-header">
                            <h2 class="section-title">
                                <i class="bi bi-headset"></i>
                                Latest Support
                            </h2>
                        </div>
                        <div class="table-responsive">
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th>Customer</th>
                                        <th>Subject</th>
                                        <th>Status</th>
                                        <th>Date</th>
                                    </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${latestSupports}" var="s">
                                    <tr>
                                        <td class="fw-semibold">
                                            ${s.customerName}
                                        </td>
                                        <td>
                                            ${s.subject}
                                        </td>
                                        <td>
                                    <c:choose>
                                        <c:when test="${s.status == 'Mới'}">
                                            <span class="badge bg-danger">
                                                New
                                            </span>
                                        </c:when>
                                        <c:when test="${s.status == 'Đang xử lý'}">
                                            <span class="badge bg-warning text-dark">
                                                Processing
                                            </span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge bg-success">
                                                Resolved
                                            </span>
                                        </c:otherwise>
                                    </c:choose>
                                    </td>
                                    <td>
                                        ${s.createdAt}
                                    </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>


                <div class="col-12 col-xl-4">
                    <div class="panel h-100">
                        <div class="panel-header">
                            <div>
                                <h2 class="h5 mb-1 section-title"><i class="bi bi-activity" aria-hidden="true"></i><span>Team Activity</span></h2>
                                <p class="text-muted mb-0">Recent operational updates.</p>
                            </div>
                        </div>

                        <div class="activity-list">
                            <div class="activity-item"><span class="activity-dot bg-primary"></span><div><p class="mb-1 fw-semibold">New campaign launched</p><p class="text-muted small mb-0">Marketing team published the May offer.</p></div></div>
                            <div class="activity-item"><span class="activity-dot bg-success"></span><div><p class="mb-1 fw-semibold">Payment batch cleared</p><p class="text-muted small mb-0">246 invoices were processed successfully.</p></div></div>
                            <div class="activity-item"><span class="activity-dot bg-warning"></span><div><p class="mb-1 fw-semibold">Support queue rising</p><p class="text-muted small mb-0">Average first response time is 18 minutes.</p></div></div>
                        </div>
                    </div>
                </div>
            </section>

            
                
            ${latestProducts}
        </div>
    </main>
</body>
</html>
