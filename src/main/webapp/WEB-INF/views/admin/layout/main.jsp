<%-- 
    Document   : main
    Created on : 13 thg 6, 2026, 18:29:54
    Author     : votandat
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <jsp:include page="/WEB-INF/views/admin/layout/header.jsp" />
    <jsp:include page="/WEB-INF/views/${body}" />
    <jsp:include page="/WEB-INF/views/admin/layout/footer.jsp" />
</html>
