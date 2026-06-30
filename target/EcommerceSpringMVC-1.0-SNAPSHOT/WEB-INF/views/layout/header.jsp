<%-- 
    Document   : header
    Created on : 26 thg 5, 2026, 08:45:52
    Author     : votandat
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<head>
    <meta charset="UTF-8">
    <title>TechShop</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
          rel="stylesheet">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css"
          rel="stylesheet">
</head>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark shadow-sm">
    <div class="container">
        <a class="navbar-brand fw-bold" href="${pageContext.request.contextPath}/">
            TechShop
        </a>
        <ul class="navbar-nav mx-auto menu-custom">

            <li class="nav-item fw-bold">
                <a class="nav-link" href="${pageContext.request.contextPath}/product/category/1">
                    iPhone
                </a>
            </li>

            <li class="nav-item fw-bold">
                <a class="nav-link" href="${pageContext.request.contextPath}/product/category/2">
                    Mac
                </a>
            </li>

            <li class="nav-item fw-bold">
                <a class="nav-link" href="${pageContext.request.contextPath}/product/category/3">
                    iPad
                </a>
            </li>

            <li class="nav-item fw-bold">
                <a class="nav-link" href="${pageContext.request.contextPath}/product/category/4">
                    Phụ kiện
                </a>
            </li>

        </ul>
        <div class="navbar-nav ms-auto align-items-center">
            
            <a href="${pageContext.request.contextPath}/cart"
               class="nav-link position-relative me-3"
               title="Shopping Cart">

                <i class="bi bi-cart3 fs-4 text-white"></i>

                <!-- Badge số lượng -->
                <span class="position-absolute top-0 start-100 translate-middle
                      badge rounded-pill bg-danger">
                    ${cartSize}
                </span>
            </a>
            <c:if test="${not empty sessionScope.LOGIN_USER}">
                <a class="nav-link fw-bold text-primary" href="${pageContext.request.contextPath}/customer/dashboard">
                    👤 Trang cá nhân
                </a>                                                     
            </c:if>

            <c:choose>
                <c:when test="${not empty sessionScope.LOGIN_USER}">
                    <span class="navbar-text text-white ms-3">
                        ${sessionScope.LOGIN_USER.username}
                    </span>
                    <a href="${pageContext.request.contextPath}/logout" class="btn btn-danger btn-sm ms-2">Đăng xuất</a>
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/login" class="btn btn-primary btn-sm ms-2">Đăng nhập</a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</nav>
