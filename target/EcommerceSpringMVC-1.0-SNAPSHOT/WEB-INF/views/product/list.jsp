<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html lang="en" data-theme="light" data-bs-theme="light">
    <style>
        .featured-btn{
            min-width:130px;
            font-weight:600;
        }

        .featured-on{
            background:#ffc107;
            color:#000;
            border:none;
        }

        .featured-off{
            background:#f8f9fa;
            color:#6c757d;
            border:1px solid #dee2e6;
        }
    </style>
    <main class="dashboard-content">
        <div class="container-fluid px-3 px-lg-4 py-4">
            <div class="page-heading">
                <div class="page-heading-copy">
                    <span class="page-icon"><i class="bi bi-table" aria-hidden="true"></i></span>
                    <div>
                        <p class="eyebrow mb-1">Data</p>
                        <h1 class="h3 mb-1">Products</h1>
                        <p class="text-muted mb-0">Search, manage and update products in the system.</p>
                    </div>
                </div>
            </div>
            <section class="panel">
                <div class="panel-header">
                    <div>
                        <h2 class="h5 mb-1 section-title">
                            <i class="bi bi-box-seam"></i>
                            <span>Product Management</span>
                        </h2>

                    </div>
                    <div class="d-flex gap-2">
                        <form action="${pageContext.request.contextPath}/product/search"
                              method="get">
                            <input class="form-control form-control-sm"
                                   type="search"
                                   name="keyword"
                                   placeholder="Search product...">
                        </form>
                        <a href="${pageContext.request.contextPath}/product/add"
                           class="btn btn-success btn-sm">
                            <i class="bi bi-plus-circle"></i>
                            Add Product
                        </a>
                    </div>
                </div>
                <div class="table-responsive">
                    <table class="table align-middle mb-0">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Product</th>
                                <th>Category</th>
                                <th>Price</th>
                                <th>Featured</th>
                                <th class="text-end">Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${not empty products}">
                                    <c:forEach var="p" items="${products}">
                                        <tr>
                                            <!-- ID -->
                                            <td class="fw-semibold">
                                                #${p.id}
                                            </td>
                                            <!-- Product -->
                                            <td>
                                                <div class="table-media d-flex align-items-center gap-2">
                                                    <img class="product-thumb"
                                                         src="${p.imageUrl}"
                                                         alt="${p.name}"
                                                         style="width:50px;height:50px;object-fit:cover;">
                                                    <span class="fw-semibold">
                                                        ${p.name}
                                                    </span>
                                                </div>
                                            </td>
                                            <!-- Series -->
                                            <td>
                                                <c:choose>
                                                    <c:when test="${p.categoryName == 'iPhone'}">
                                                        <span class="badge text-bg-primary">
                                                            iPhone
                                                        </span>
                                                    </c:when>
                                                    <c:when test="${p.categoryName == 'iPad'}">
                                                        <span class="badge text-bg-success">
                                                            iPad
                                                        </span>
                                                    </c:when>
                                                    <c:when test="${p.categoryName == 'MacBook'}">
                                                        <span class="badge text-bg-dark">
                                                            MacBook
                                                        </span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="badge text-bg-secondary">
                                                            ${p.categoryName}
                                                        </span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <!-- Price -->
                                            <td class="fw-bold text-success">
                                                <fmt:formatNumber
                                                    value="${p.price}"
                                                    maxFractionDigits="0"/>
                                                VNĐ
                                            </td>
                                            <td>
                                                <c:choose>

                                                    <c:when test="${p.featured}">
                                                        <a href="${pageContext.request.contextPath}/product/featured/${p.id}"
                                                           class="btn featured-btn featured-on">

                                                            Featured

                                                        </a>
                                                    </c:when>

                                                    <c:otherwise>
                                                        <a href="${pageContext.request.contextPath}/product/featured/${p.id}"
                                                           class="btn featured-btn featured-off">

                                                            Not Featured

                                                        </a>
                                                    </c:otherwise>

                                                </c:choose>
                                                
                                            </td>
                                            <!-- Action -->
                                            <td class="text-end">
                                                <a href="${pageContext.request.contextPath}/product/edit/${p.id}"
                                                   class="btn btn-light btn-sm">
                                                    Edit
                                                </a>
                                                <form action="${pageContext.request.contextPath}/product/delete/${p.id}"
                                                      method="post"
                                                      class="d-inline">
                                                    <button type="submit"
                                                            class="btn btn-danger btn-sm"
                                                            onclick="return confirm('Bạn có chắc chắn muốn xóa sản phẩm này?')">
                                                        Delete
                                                    </button>
                                                </form>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <tr>
                                        <td colspan="5"
                                            class="text-center py-5 text-muted">
                                            No products found.
                                        </td>
                                    </tr>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>
                </div>
            </section>
        </div>
    </main>

</html>