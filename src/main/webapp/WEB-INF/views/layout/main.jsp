<%-- 
    Document   : main
    Created on : 26 thg 5, 2026, 08:48:20
    Author     : votandat
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
    
    <body class="bg-light d-flex flex-column" style="min-height: 100vh;">

        <jsp:include page="header.jsp" />

        <main>
            <jsp:include page="/WEB-INF/views/${body}" /> 
        </main>

        <jsp:include page="footer.jsp" />

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js">
        </script>
    </body>
</html>
