<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    

            <main class="dashboard-content">
                <div class="container-fluid px-3 px-lg-4 py-4">
                    <div class="page-heading">
                        <div class="page-heading-copy">
                            <span class="page-icon"><i class="bi bi-people" aria-hidden="true"></i></span>
                            <div>
                                <p class="eyebrow mb-1">Management</p>
                                <h1 class="h3 mb-1">Users</h1>
                                <p class="text-muted mb-0">Review accounts, roles, account status, and team ownership.</p>
                            </div>
                        </div>
                        <div class="heading-actions"><a class="btn btn-outline-secondary btn-sm" href="tables.html"><i class="bi bi-download" aria-hidden="true"></i> Export</a><a class="btn btn-primary btn-sm" href="${pageContext.request.contextPath}/admin/users/add"><i class="bi bi-person-plus" aria-hidden="true"></i> Add User</a></div>
                    </div>
                    <section class="panel mt-3">
                        <div class="panel-header">
                            <div>
                                <h2 class="h5 mb-1 section-title"><i class="bi bi-table" aria-hidden="true"></i><span>User List</span></h2>
                                <p class="text-muted mb-0">Search, review, and manage team member accounts.</p>
                            </div>
                            <div class="d-flex flex-wrap gap-2">
                                <input class="form-control form-control-sm table-search" type="search" placeholder="Search users" data-table-search="usersTable" aria-label="Search users">
                                <a class="btn btn-primary btn-sm" href="add-user.html"><i class="bi bi-person-plus" aria-hidden="true"></i> Add User</a>
                            </div>
                        </div>
                        <div class="table-responsive">
                            <table class="table align-middle mb-0" id="usersTable" data-searchable-table="">
                                <thead>
                                    <tr>
                                        <th>User</th>
                                        <th>Role</th>
                                        <th>Username</th>
                                        <th>Status</th>
                                        <th>ID</th>
                                        <th class="text-end">Action</th>
                                    </tr>
                                </thead>
                                <tbody>

                                    <c:forEach items="${users}" var="user" varStatus="st">

                                        <tr>

                                            <!-- User -->
                                            <td>
                                                <div class="d-flex align-items-center gap-2">

                                                    <img class="avatar-img avatar-sm"
                                                         src="${pageContext.request.contextPath}/assets/images/avatar/avatar-${(st.index % 5) + 1}.jpg"
                                                         alt="${user.fullName}">

                                                    <div>
                                                        <p class="fw-semibold mb-0">
                                                            ${user.fullName}
                                                        </p>

                                                        <p class="text-muted small mb-0">
                                                            ${user.email}
                                                        </p>
                                                    </div>

                                                </div>
                                            </td>

                                            <!-- Role -->
                                            <td>

                                                <c:choose>

                                                    <c:when test="${user.role == 'ADMIN'}">
                                                        <span class="badge text-bg-danger">
                                                            ADMIN
                                                        </span>
                                                    </c:when>

                                                    <c:when test="${user.role == 'STAFF'}">
                                                        <span class="badge text-bg-warning">
                                                            STAFF
                                                        </span>
                                                    </c:when>

                                                    <c:otherwise>
                                                        <span class="badge text-bg-primary">
                                                            CUSTOMER
                                                        </span>
                                                    </c:otherwise>

                                                </c:choose>

                                            </td>

                                            <!-- Username -->
                                            <td>
                                                ${user.username}
                                            </td>

                                            <!-- Status -->
                                            <td>

                                                <c:choose>

                                                    <c:when test="${user.active}">
                                                        <span class="badge text-bg-success">
                                                            Active
                                                        </span>
                                                    </c:when>

                                                    <c:otherwise>
                                                        <span class="badge text-bg-secondary">
                                                            Locked
                                                        </span>
                                                    </c:otherwise>

                                                </c:choose>

                                            </td>

                                            <!-- ID -->
                                            <td>
                                                #${user.id}
                                            </td>

                                            <!-- Action -->
                                            <td class="text-end">

                                                <div class="btn-group">

                                                    <!-- Đổi quyền -->
                                                    <form action="${pageContext.request.contextPath}/admin/users/update-role"
                                                          method="post">

                                                        <input type="hidden"
                                                               name="id"
                                                               value="${user.id}">

                                                        <select name="role"
                                                                class="form-select form-select-sm">

                                                            <option value="CUSTOMER"
                                                                    ${user.role=='CUSTOMER'?'selected':''}>
                                                                CUSTOMER
                                                            </option>

                                                            <option value="STAFF"
                                                                    ${user.role=='STAFF'?'selected':''}>
                                                                STAFF
                                                            </option>

                                                            <option value="ADMIN"
                                                                    ${user.role=='ADMIN'?'selected':''}>
                                                                ADMIN
                                                            </option>

                                                        </select>

                                                        <button type="submit"
                                                                class="btn btn-sm btn-primary mt-1">
                                                            Save
                                                        </button>

                                                    </form>

                                                    <!-- Khóa/Mở khóa -->
                                                    <form action="${pageContext.request.contextPath}/admin/users/toggle-active"
                                                          method="post"
                                                          class="ms-2">

                                                        <input type="hidden"
                                                               name="id"
                                                               value="${user.id}">

                                                        <input type="hidden"
                                                               name="active"
                                                               value="${!user.active}">

                                                        <button type="submit"
                                                                class="btn btn-sm ${user.active ? 'btn-danger' : 'btn-success'}">

                                                            ${user.active ? 'Lock' : 'Unlock'}

                                                        </button>

                                                    </form>

                                                </div>

                                            </td>

                                        </tr>

                                    </c:forEach>

                                </tbody>
                            </table>
                        </div>
                        <div class="d-flex flex-column flex-sm-row align-items-sm-center justify-content-between gap-3 mt-3">
                            <p class="text-muted small mb-0">Showing 1 to 5 of 124 users</p>
                            <nav aria-label="Users pagination"><ul class="pagination pagination-sm mb-0"><li class="page-item disabled"><a class="page-link" href="#">Previous</a></li><li class="page-item active"><a class="page-link" href="#">1</a></li><li class="page-item"><a class="page-link" href="#">2</a></li><li class="page-item"><a class="page-link" href="#">Next</a></li></ul></nav>
                        </div>
                    </section>
                </div>
            </main>

            
        </div>
    </div>
</html>