<%-- 
    Document   : users-form
    Created on : 11 thg 6, 2026, 23:33:18
    Author     : votandat
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
                <main class="dashboard-content">
                    <div class="container-fluid px-3 px-lg-4 py-4">
                        <div class="page-heading">
                            <div class="page-heading-copy">
                                <span class="page-icon"><i class="bi bi-person-plus" aria-hidden="true"></i></span>
                                <div>
                                    <p class="eyebrow mb-1">Management</p>
                                    <h1 class="h3 mb-1">Add User</h1>
                                    <p class="text-muted mb-0">Create a new user account with role and team assignments.</p>
                                </div>
                            </div>
                            <div class="heading-actions"><a class="btn btn-outline-secondary btn-sm" href="${pageContext.request.contextPath}/admin/users"><i class="bi bi-arrow-left" aria-hidden="true"></i> Back to Users</a></div>
                        </div>

                        <section class="row g-3">
                            <!-- Form --> 
                            <div class="col-12 col-xl-8">
                                <form action="${pageContext.request.contextPath}/admin/users/save" method="post" class="panel">
                                    <div class="panel-header">
                                        <div>
                                            <h2 class="h5 mb-1 section-title"> <i class="bi bi-person-plus"></i> <span>User Information</span> </h2>
                                            <p class="text-muted mb-0"> Create a user account. </p>
                                        </div>
                                    </div>
                                    
                                    <div class="row g-3">
                                        <!-- Full Name --> 
                                        <div class="col-md-6"> <label class="form-label"> Full Name </label> <input type="text" name="fullName" class="form-control" required> </div>
                                        <!-- Username --> 
                                        <div class="col-md-6"> <label class="form-label"> Username </label> <input type="text" name="username" class="form-control" required> </div>
                                        <!-- Email --> 
                                        <div class="col-md-6"> <label class="form-label"> Email </label> <input type="email" name="email" class="form-control" required> </div>
                                        <!-- Password --> 
                                        <div class="col-md-6"> <label class="form-label"> Password </label> <input type="password" name="password" class="form-control" required> </div>
                                        <!-- Role --> 
                                        <div class="col-md-6">
                                            <label class="form-label"> Role </label> 
                                            <select name="role" class="form-select">
                                                <option value="CUSTOMER"> CUSTOMER </option>
                                                <option value="STAFF"> STAFF </option>
                                                <option value="ADMIN"> ADMIN </option>
                                            </select>
                                        </div>
                                        <!-- Active --> 
                                        <div class="col-md-6">
                                            <label class="form-label"> Status </label> 
                                            <select name="active" class="form-select">
                                                <option value="true"> Active </option>
                                                <option value="false"> Locked </option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="d-flex flex-wrap justify-content-end gap-2 mt-4"> <a href="${pageContext.request.contextPath}/admin/users" class="btn btn-outline-secondary"> Cancel </a> <button class="btn btn-primary" type="submit"> <i class="bi bi-person-check"></i> Create User </button> </div>
                                </form>
                            </div>
                            <!-- Side Panel --> 
                            <div class="col-12 col-xl-4">
                                <div class="panel h-100">
                                    <h2 class="h5 mb-3 section-title"> <i class="bi bi-list-check"></i> <span>Access Checklist</span> </h2>
                                    <div class="activity-list">
                                        <div class="activity-item">
                                            <span class="activity-dot bg-success"></span> 
                                            <div>
                                                <p class="mb-1 fw-semibold"> Assign role </p>
                                                <p class="text-muted small mb-0"> Use CUSTOMER for normal users. </p>
                                            </div>
                                        </div>
                                        <div class="activity-item">
                                            <span class="activity-dot bg-primary"></span> 
                                            <div>
                                                <p class="mb-1 fw-semibold"> Staff access </p>
                                                <p class="text-muted small mb-0"> STAFF can manage orders. </p>
                                            </div>
                                        </div>
                                        <div class="activity-item">
                                            <span class="activity-dot bg-danger"></span> 
                                            <div>
                                                <p class="mb-1 fw-semibold"> Admin access </p>
                                                <p class="text-muted small mb-0"> ADMIN has full permissions. </p>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </section>
                    </div>
                </main>

                
            </div>
        </div>

        


    </body>
</html>
