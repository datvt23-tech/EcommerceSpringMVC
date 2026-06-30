<%-- 
    Document   : category
    Created on : 12 thg 6, 2026, 19:05:28
    Author     : votandat
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<h2>${categoryName}</h2>

<div class="row">

    <c:forEach items="${products}" var="p">

        <div class="col-md-3 mb-4">

            <div class="card h-100">

                <img src="${p.imageUrl}"
                     class="card-img-top">

                <div class="card-body">

                    <h5>${p.name}</h5>

                    <p class="text-danger">

                        <fmt:formatNumber
                            value="${p.price}"
                            maxFractionDigits="0"/>

                        VNĐ

                    </p>

                    <a href="${pageContext.request.contextPath}/product/detail/${p.id}"
                       class="btn btn-primary">

                        Xem chi tiết

                    </a>

                </div>

            </div>

        </div>

    </c:forEach>

</div>
