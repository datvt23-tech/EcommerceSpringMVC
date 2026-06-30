<%-- 
    Document   : iphone
    Created on : 13 thg 6, 2026, 20:11:34
    Author     : votandat
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<style>
    .product-image{
        height:250px;
        display:flex;
        justify-content:center;
        align-items:center;
        padding:20px;
    }

    .category-section{
        margin-top:80px;
    }

    .category-title{
        color:black;
        text-align:center;
        font-size:48px;
        font-weight:700;
        margin-bottom:40px;
    }

    .product-card {
        background: #fff;
        border-radius: 20px;
        padding: 20px;
        height: 100%;
        transition: all 0.3s ease;
        box-shadow: 0 4px 12px rgba(0,0,0,.08);
    }

    .product-card:hover {
        transform: translateY(-5px);
        box-shadow: 0 8px 25px rgba(0,0,0,.15);
    }

    .product-card img {
        width: 100%;
        height: 220px;
        object-fit: contain;
    }

    .product-name {
        font-size: 18px;
        font-weight: 600;
        color: #000;
        text-align: center;
        margin-top: 15px;
    }

    .product-price {
        font-size: 24px;
        font-weight: 700;
        color: #d70018;
        text-align: center;
    }
    .hero-slide{
        background:white;
        border-radius:24px;
        overflow:hidden;
    }
    .hero-slide img {
        width: 100%;
        height: 300px;
        object-fit: cover;
        display: block;
    }
    .carousel-indicators [data-bs-target] {
        width: 10px;
        height: 10px;
        border-radius: 50%;
    }
</style>
<div class="container mt-4 pb-4">
    
</div>
<h1 class="category-title">
     Mac
</h1>
<div class="container">
    <section id="homeBanner" class="carousel slide" data-bs-ride="carousel" data-bs-interval="4500">
        <div class="carousel-indicators">
            <button type="button" data-bs-target="#homeBanner" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Banner 1"></button>
            <
        </div>

        <div class="carousel-inner">
            <div class="carousel-item active">
                <div class="hero-slide">
                    <img src="https://cdnv2.tgdd.vn/mwg-static/topzone/Banner/d6/2a/d62a84a78cefbcc3efbf97e3bc348441.png" alt="Ưu đãi iPhone mới tại TechShop">

                </div>
            </div>

            \
        </div>

        <button class="carousel-control-prev" type="button" data-bs-target="#homeBanner" data-bs-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Trước</span>
        </button>
        <button class="carousel-control-next" type="button" data-bs-target="#homeBanner" data-bs-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Sau</span>
        </button>
    </section>  
</div>

<section class="category-section">
    <div class="container">
        <div class="row row-cols-2 row-cols-md-4 g-4">
            <c:forEach items="${products}" var="p">
                <div class="col">
                    <a href="${pageContext.request.contextPath}/product/${p.id}"
                       class="text-decoration-none text-dark">
                        <div class="card product-card h-100">
                            <div class="product-image">
                                <img src="${p.imageUrl}">
                            </div>
                            <div class="card-body text-center">
                                <h6>${p.name}</h6>
                                <p class="product-price">
                                    <fmt:formatNumber value="${p.price}"
                                                      maxFractionDigits="0"/>
                                    ₫
                                </p>
                            </div>
                        </div>  
                    </a>
                </div>
            </c:forEach>
        </div>
    </div>
</section>