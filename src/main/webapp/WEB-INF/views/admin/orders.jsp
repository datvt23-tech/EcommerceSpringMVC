<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<main class="dashboard-content">
   <div class="container-fluid px-3 px-lg-4 py-4">
      <!-- Page Heading -->
      <div class="page-heading">
         <div class="page-heading-copy">
            <span class="page-icon">
            <i class="bi bi-cart-check" aria-hidden="true"></i>
            </span>
            <div>
               <p class="eyebrow mb-1">Order Management</p>
               <h1 class="h3 mb-1">Orders</h1>
               <p class="text-muted mb-0">
                  Manage customer orders, shipping progress and delivery status.
               </p>
            </div>
         </div>
      </div>
      <!-- Notifications -->
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
      <!-- Orders Table -->
      <section class="panel">
         <div class="panel-header">
            <div>
               <h2 class="h5 mb-1 section-title">
                  <i class="bi bi-table" aria-hidden="true"></i>
                  <span>Order List</span>
               </h2>
               <p class="text-muted mb-0">
                  Search, review and update customer orders.
               </p>
            </div>
            <input
               class="form-control form-control-sm table-search"
               type="search"
               placeholder="Search orders..."
               data-table-search="ordersTable"
               aria-label="Search orders">
         </div>
         <div class="table-responsive">
            <table class="table align-middle mb-0"
               id="ordersTable"
               data-searchable-table>
               <thead>
                  <tr>
                     <th>Order ID</th>
                     <th>Customer</th>
                     <th>Contact</th>
                     <th>Products</th>
                     <th>Total Amount</th>
                     <th>Status</th>
                     <th class="text-end">Action</th>
                  </tr>
               </thead>
               <tbody>
                  <c:forEach items="${orders}" var="order">
                     <tr>
                        <!-- Order ID -->
                        <td class="fw-semibold">
                           #${order.id}
                        </td>
                        <!-- Customer -->
                        <td>
                           <div>
                              <div class="fw-semibold">
                                 ${order.customerName}
                              </div>
                              <small class="text-muted">
                              ${order.address}
                              </small>
                           </div>
                        </td>
                        <!-- Contact -->
                        <td>
                           ${order.phone}
                        </td>
                        <!-- Products -->
                        <td>
                           <c:forEach items="${order.items}" var="item">
                              <div>
                                 ${item.productName}
                                 <span class="text-muted">
                                 × ${item.quantity}
                                 </span>
                              </div>
                           </c:forEach>
                        </td>
                        <!-- Total -->
                        <td class="fw-bold text-danger">
                           <fmt:formatNumber
                              value="${order.totalAmount}"
                              maxFractionDigits="0"/>
                           VND
                        </td>
                        <!-- Status -->
                        <td>
                           <span class="badge text-bg-primary">
                           ${order.status}
                           </span>
                        </td>
                        <!-- Action -->
                        <td class="text-end">
                           <form action="${pageContext.request.contextPath}/staff/orders/update-status"
                              method="POST"
                              class="d-flex justify-content-end gap-2">
                              <input type="hidden"
                                 name="id"
                                 value="${order.id}">
                              <select name="status"
                                 class="form-select form-select-sm"
                                 style="width: 170px;">
                                 <c:forEach items="${statuses}" var="status">
                                    <option value="${status}"
                                    ${order.status == status ? 'selected' : ''}>
                                    ${status}
                                    </option>
                                 </c:forEach>
                              </select>
                              <button type="submit"
                                 class="btn btn-primary btn-sm">
                              Save
                              </button>
                           </form>
                        </td>
                     </tr>
                  </c:forEach>
               </tbody>
            </table>
         </div>
      </section>
   </div>
</main>