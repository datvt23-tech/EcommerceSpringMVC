<%-- 
    Document   : main
    Created on : 13 thg 6, 2026, 19:24:41
    Author     : votandat
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <jsp:include page="/WEB-INF/views/staff/layout/header.jsp" />
    <jsp:include page="/WEB-INF/views/${body}" />
    <jsp:include page="/WEB-INF/views/staff/layout/footer.jsp" />
    <script src="${pageContext.request.contextPath}/assets/js/bootstrap.bundle.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/main.js"></script>
</html>
