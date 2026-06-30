<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt"
           uri="http://java.sun.com/jsp/jstl/fmt"%>
<style>
body{
    background:#f5f5f7;
}

.detail-page{
    margin-top:40px;
    margin-bottom:50px;
}

.image-panel{
    background:#fff;
    border-radius:24px;
    padding:30px;
    box-shadow:0 5px 20px rgba(0,0,0,.08);
}

.main-image{
    width:100%;
    height:600px;
    object-fit:contain;
}

.thumb-list{
    display:flex;
    gap:15px;
    margin-top:20px;
    justify-content:center;
}

.thumb-list img{
    width:80px;
    height:80px;
    object-fit:contain;
    border:1px solid #ddd;
    border-radius:12px;
    padding:8px;
    cursor:pointer;
}

.thumb-list img:hover{
    border-color:#0071e3;
}

.info-panel{
    background:#fff;
    border-radius:24px;
    padding:35px;
    box-shadow:0 5px 20px rgba(0,0,0,.08);
}

.product-title{
    font-size:42px;
    font-weight:700;
    margin-bottom:25px;
}

.sale-price{
    color:#d70018;
    font-size:40px;
    font-weight:700;
}

.old-price{
    color:#888;
    text-decoration:line-through;
    font-size:22px;
    margin-left:15px;
}

.discount-badge{
    background:#fff3cd;
    color:#856404;
    padding:10px 15px;
    border-radius:12px;
    display:inline-block;
    margin-top:15px;
    margin-bottom:25px;
}

.promo-box{
    background:#f8f9fa;
    border-radius:16px;
    padding:20px;
}

.promo-box ul{
    margin-top:15px;
}

.promo-box li{
    margin-bottom:10px;
}

.buy-btn{
    display:block;
    text-align:center;
    background:#0071e3;
    color:white;
    text-decoration:none;
    font-size:22px;
    font-weight:700;
    padding:16px;
    border-radius:16px;
    margin-top:25px;
}

.buy-btn:hover{
    background:#0056b3;
    color:white;
}

.desc-box{
    margin-top:25px;
    background:#f8f9fa;
    border-radius:16px;
    padding:20px;
}
</style>


<div class="container detail-page">

    <div class="row g-5">

        <!-- LEFT -->
        <div class="col-lg-7">

            <div class="image-panel">

                <img src="${product.imageUrl}" class="main-image">

                <div class="thumb-list">
                    <img src="${product.imageUrl}">
                    <img src="${product.imageUrl}">
                    <img src="${product.imageUrl}">
                    <img src="${product.imageUrl}">
                </div>

            </div>

        </div>

        <!-- RIGHT -->
        <div class="col-lg-5">

            <div class="info-panel">

                <h1 class="product-title">
                    ${product.name}
                </h1>

                <div class="price-area">

                    <span class="sale-price">
                        <fmt:formatNumber value="${product.price}"
                                          maxFractionDigits="0"/>
                        ₫
                    </span>

                    <span class="old-price">
                        <fmt:formatNumber value="${product.price*1.15}"
                                          maxFractionDigits="0"/>
                        ₫
                    </span>

                </div>

                <div class="discount-badge">
                    🔥 Giảm giá đặc biệt hôm nay
                </div>

                <div class="promo-box">

                    <h5>🎁 Khuyến mãi</h5>

                    <ul>
                        <li>Tặng voucher 500.000₫</li>
                        <li>Giảm 10% phụ kiện</li>
                        <li>Trả góp 0%</li>
                        <li>Miễn phí giao hàng toàn quốc</li>
                    </ul>

                </div>

                <a href="${pageContext.request.contextPath}/cart/add?productId=${product.id}"
                   class="buy-btn">
                    MUA NGAY
                </a>

                <div class="desc-box">

                    <h5>Mô tả sản phẩm</h5>

                    <p>${product.description}</p>

                </div>

            </div>

        </div>

    </div>

</div>