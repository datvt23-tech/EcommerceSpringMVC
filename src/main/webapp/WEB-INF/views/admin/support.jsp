<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="container-fluid px-3 px-lg-4 py-4">
    <!-- Page Header -->
    <div class="page-heading">
        <div class="page-heading-copy">
            <span class="page-icon">
                <i class="bi bi-headset" aria-hidden="true"></i>
            </span>
            <div>
                <p class="eyebrow mb-1">Customer Service</p>
                <h1 class="h3 mb-1">Support Tickets</h1>
                <p class="text-muted mb-0">
                    Theo dõi và xử lý các yêu cầu hỗ trợ từ khách hàng.
                </p>
            </div>
        </div>
    </div>
    <!-- Alert -->
    <c:if test="${not empty success}">
        <div class="alert alert-success">
            ${success}
        </div>
    </c:if>
    <c:if test="${not empty error}">
        <div class="alert alert-danger">
            ${error}
        </div>
    </c:if>
    <!-- Ticket List -->
    <section class="row g-3">
        <c:forEach items="${tickets}" var="ticket">
            <div class="col-12">
                <form class="panel"
                      action="${pageContext.request.contextPath}/staff/support/update"
                      method="post">
                    <input type="hidden"
                           name="id"
                           value="${ticket.id}">
                    <div class="panel-header">
                        <div>
                            <h2 class="h5 mb-1 section-title">
                                <i class="bi bi-chat-dots"></i>
                                <span>
                                    #${ticket.id} - ${ticket.subject}
                                </span>
                            </h2>
                            <p class="text-muted mb-0">
                                ${ticket.customerName}
                                •
                                ${ticket.createdAt}
                            </p>
                        </div>
                        <span class="
                              badge
                              ${ticket.status == 'Mới' ? 'text-bg-danger' :
                                ticket.status == 'Đang xử lý' ? 'text-bg-warning' :
                                ticket.status == 'Đã phản hồi' ? 'text-bg-success' :
                                'text-bg-secondary'}">
                                  ${ticket.status}
                              </span>
                        </div>
                        <div class="row g-3">
                            <!-- Customer Message -->
                            <div class="col-lg-6">
                                <label class="form-label fw-bold">
                                    Nội dung khách hàng
                                </label>
                                <textarea class="form-control"
                                          rows="8"
                                          readonly>${ticket.message}</textarea>
                            </div>
                            <!-- Staff Reply -->
                            <div class="col-lg-6">
                                <label class="form-label fw-bold">
                                    Phản hồi khách hàng
                                </label>
                                <textarea class="form-control"
                                          rows="8"
                                          name="reply">${ticket.reply}</textarea>
                            </div>
                            <!-- Status -->
                            <div class="col-md-4">
                                <label class="form-label">
                                    Trạng thái
                                </label>
                                <select class="form-select"
                                        name="status">
                                    <c:forEach items="${statuses}" var="status">
                                        <option value="${status}"
                                                ${ticket.status == status ? 'selected' : ''}>
                                            ${status}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="d-flex justify-content-end mt-4">
                            <button type="submit"
                                    class="btn btn-primary">
                                <i class="bi bi-save"></i>
                                Lưu phản hồi
                            </button>
                        </div>
                    </form>
                </div>
            </c:forEach>
        </section>
    </div>