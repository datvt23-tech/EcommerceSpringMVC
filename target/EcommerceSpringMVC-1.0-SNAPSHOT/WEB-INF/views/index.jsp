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
    .hero-slide {
        position: relative;
        min-height: 420px;
        overflow: hidden;
        background: #0f172a;
    }
    .hero-slide img {
        width: 100%;
        height: 420px;
        object-fit: cover;
        display: block;
    }
    .carousel-indicators [data-bs-target] {
        width: 10px;
        height: 10px;
        border-radius: 50%;
    }
</style>

<section id="homeBanner" class="carousel slide" data-bs-ride="carousel" data-bs-interval="4500">
    <div class="carousel-indicators">
        <button type="button" data-bs-target="#homeBanner" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Banner 1"></button>
        <button type="button" data-bs-target="#homeBanner" data-bs-slide-to="1" aria-label="Banner 2"></button>
        <button type="button" data-bs-target="#homeBanner" data-bs-slide-to="2" aria-label="Banner 3"></button>
        <button type="button" data-bs-target="#homeBanner" data-bs-slide-to="3" aria-label="Banner 4"></button>
        <button type="button" data-bs-target="#homeBanner" data-bs-slide-to="4" aria-label="Banner 5"></button>
        <button type="button" data-bs-target="#homeBanner" data-bs-slide-to="5" aria-label="Banner 6"></button>
        <button type="button" data-bs-target="#homeBanner" data-bs-slide-to="6" aria-label="Banner 7"></button>
        <button type="button" data-bs-target="#homeBanner" data-bs-slide-to="7" aria-label="Banner 8"></button>
        <button type="button" data-bs-target="#homeBanner" data-bs-slide-to="8" aria-label="Banner 9"></button>
        <button type="button" data-bs-target="#homeBanner" data-bs-slide-to="9" aria-label="Banner 10"></button>
        <button type="button" data-bs-target="#homeBanner" data-bs-slide-to="10" aria-label="Banner 11"></button>
    </div>

    <div class="carousel-inner">
        <div class="carousel-item active">
            <div class="hero-slide">
                <img src="https://cdnv2.tgdd.vn/mwg-static/topzone/Banner/d7/b3/d7b32b2100bb2af0eaa3089a57bf04e6.png" alt="Ưu đãi iPhone mới tại TechShop">

            </div>
        </div>

        <div class="carousel-item">
            <div class="hero-slide">
                <img src="https://cdnv2.tgdd.vn/mwg-static/topzone/Banner/81/89/81895040878290b45d469712315a87d2.png" alt="Thu cũ đổi mới tại TechShop">

            </div>
        </div>

        <div class="carousel-item">
            <div class="hero-slide">
                <img src="https://cdnv2.tgdd.vn/mwg-static/topzone/Banner/94/5c/945c93ba978bb454c5a4c8e84c8306e1.png" alt="Phụ kiện công nghệ tại TechShop">

            </div>
        </div>
        <div class="carousel-item">
            <div class="hero-slide">
                <img src="https://cdnv2.tgdd.vn/mwg-static/topzone/Banner/95/1c/951ccf45000f5366a6923b7dd2c4e94f.png" alt="Thu cũ đổi mới tại TechShop">

            </div>
        </div>

        <div class="carousel-item">
            <div class="hero-slide">
                <img src="https://cdnv2.tgdd.vn/mwg-static/topzone/Banner/78/a8/78a80c31de651037de1e04ffa7e88987.png" alt="Phụ kiện công nghệ tại TechShop">

            </div>
        </div>
        <div class="carousel-item">
            <div class="hero-slide">
                <img src="https://cdnv2.tgdd.vn/mwg-static/topzone/Banner/84/d2/84d2bb1d1fd2871b0fd6cfe07d21cb7e.png" alt="Thu cũ đổi mới tại TechShop">

            </div>
        </div>

        <div class="carousel-item">
            <div class="hero-slide">
                <img src="https://cdnv2.tgdd.vn/mwg-static/topzone/Banner/4e/a9/4ea92beb5e9c05f7ea398c7dbdc922c2.png" alt="Phụ kiện công nghệ tại TechShop">

            </div>
        </div>
        <div class="carousel-item">
            <div class="hero-slide">
                <img src="https://cdnv2.tgdd.vn/mwg-static/topzone/Banner/4e/a9/4ea92beb5e9c05f7ea398c7dbdc922c2.png" alt="Thu cũ đổi mới tại TechShop">

            </div>
        </div>

        <div class="carousel-item">
            <div class="hero-slide">
                <img src="https://cdnv2.tgdd.vn/mwg-static/topzone/Banner/93/65/9365b57b2b1cf0e79b817703b04ac17d.png" alt="Phụ kiện công nghệ tại TechShop">

            </div>
        </div>
        <div class="carousel-item">
            <div class="hero-slide">
                <img src="https://cdnv2.tgdd.vn/mwg-static/topzone/Banner/b1/dc/b1dcc47ed583138a1bf86c08871fcb43.png" alt="Thu cũ đổi mới tại TechShop">

            </div>
        </div>

        <div class="carousel-item">
            <div class="hero-slide">
                <img src="https://cdnv2.tgdd.vn/mwg-static/topzone/Banner/73/19/73198cb99d7c83f75337f994bfbc5585.png" alt="Phụ kiện công nghệ tại TechShop">

            </div>
        </div>

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

<section id="about" class="about-section py-5 border-bottom">
    <div class="container">
        <div class="row align-items-center g-4">
            <div class="col-lg-6">
                <h2 class="fw-bold mb-3">TechShop dành cho người mê công nghệ</h2>
                <p class="text-muted mb-0">
                    Chúng tôi tập trung vào sản phẩm công nghệ chính hãng, thông tin cấu hình rõ ràng,
                    giá bán minh bạch và trải nghiệm mua hàng gọn nhẹ từ lúc chọn sản phẩm đến khi nhận hàng.
                </p>
            </div>
            <div class="col-lg-6">
                <div class="row g-3">
                    <div class="col-sm-4">
                        <div class="about-stat">
                            <h5 class="fw-bold mb-1">Chính hãng</h5>
                            <small class="text-muted">Nguồn gốc rõ ràng</small>
                        </div>
                    </div>
                    <div class="col-sm-4">
                        <div class="about-stat">
                            <h5 class="fw-bold mb-1">Bảo hành</h5>
                            <small class="text-muted">Hỗ trợ đổi trả</small>
                        </div>
                    </div>
                    <div class="col-sm-4">
                        <div class="about-stat">
                            <h5 class="fw-bold mb-1">Tư vấn</h5>
                            <small class="text-muted">Chọn đúng nhu cầu</small>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<div class="container mt-5 pb-5">

    <h2 class="category-title">
        Danh mục sản phẩm
    </h2>

    <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 row-cols-lg-4 g-4">
        <c:forEach var="category" items="${categories}">
            <div class="col">
                <a href="${pageContext.request.contextPath}/product/category/${category.id}"
                   class="card h-100 border-0 shadow-sm text-decoration-none text-dark rounded-3">
                    <div class="card-body text-center py-4">
                        <h4 class="fw-bold mb-2">${category.name}</h4>
                    </div>
                </a>
            </div>
        </c:forEach>
    </div>         
</div>

<section class="featured-section">

    <h2 class="category-title">
        Sản phẩm nổi bật
    </h2>

    <div class="container">
        <div class="row row-cols-2 row-cols-md-4 g-4">
            <c:forEach items="${featuredProducts}" var="p">
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

<section class="category-section">
    <h2 class="category-title">
         iPhone
    </h2>
    <div class="container">
        <div class="row row-cols-2 row-cols-md-4 g-4">
            <c:forEach items="${iphoneProducts}" var="p">
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

<section class="category-section">
    <h2 class="category-title">
         Mac
    </h2>
    <div class="container">
        <div class="row row-cols-2 row-cols-md-4 g-4">
            <c:forEach items="${macProducts}" var="p">
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

<section class="category-section">
    <h2 class="category-title">
         iPad
    </h2>
    <div class="container">
        <div class="row row-cols-2 row-cols-md-4 g-4">
            <c:forEach items="${ipadProducts}" var="p">
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
<section class="category-section">
    <h2 class="category-title">
        Phụ kiện
    </h2>
    <div class="container">
        <div class="row row-cols-2 row-cols-md-4 g-4">
            <c:forEach items="${accessoryProducts}" var="p">
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


