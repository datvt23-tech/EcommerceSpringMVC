<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="container py-5">
    <div class="mb-4">
        <h2 class="fw-bold mb-1">Chăm sóc khách hàng</h2>
        <p class="text-muted mb-0">Gửi yêu cầu bảo hành, đổi trả hoặc khiếu nại để nhân viên hỗ trợ.</p>
    </div>

    <c:if test="${not empty success}">
        <div class="alert alert-success">${success}</div>
    </c:if>
    <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
    </c:if>

    <div class="row g-4">
        <div class="col-lg-5">
            <div class="card border-0 shadow-sm">
                <div class="card-header bg-dark text-white fw-bold">Gửi yêu cầu mới</div>
                <div class="card-body">
                    <form action="${pageContext.request.contextPath}/support/create" method="POST">
                        <div class="mb-3">
                            <label class="form-label">Tiêu đề</label>
                            <input type="text" name="subject" class="form-control" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Nội dung</label>
                            <textarea name="message" class="form-control" rows="5" required></textarea>
                        </div>
                        <button type="submit" class="btn btn-dark">Gửi yêu cầu</button>
                    </form>
                </div>
            </div>
        </div>
        <div class="col-lg-7">
            <div class="card border-0 shadow-sm">
                <div class="card-header bg-white fw-bold">Yêu cầu của bạn</div>
                <div class="card-body">
                    <c:choose>
                        <c:when test="${not empty tickets}">
                            <c:forEach items="${tickets}" var="ticket">
                                <div class="border-bottom pb-3 mb-3">
                                    <div class="d-flex justify-content-between">
                                        <h6 class="fw-bold mb-1">${ticket.subject}</h6>
                                        <span class="badge bg-secondary">${ticket.status}</span>
                                    </div>
                                    <p class="text-muted mb-2">${ticket.message}</p>
                                    <c:if test="${not empty ticket.reply}">
                                        <div class="alert alert-light border mb-0">
                                            <b>Phản hồi:</b> ${ticket.reply}
                                        </div>
                                    </c:if>
                                </div>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <div class="text-muted">Bạn chưa gửi yêu cầu hỗ trợ nào.</div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>
</div>
