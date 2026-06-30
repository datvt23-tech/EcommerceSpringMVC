<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<main class="dashboard-content">
    <div class="container-fluid px-3 px-lg-4 py-4">

        <!-- Heading -->
        <div class="page-heading">
            <div class="page-heading-copy">
                <span class="page-icon">
                    <i class="bi bi-box-seam"></i>
                </span>

                <div>
                    <p class="eyebrow mb-1">Products</p>

                    <h1 class="h3 mb-1">
                        <c:choose>
                            <c:when test="${product.id == 0}">
                                Add Product
                            </c:when>
                            <c:otherwise>
                                Edit Product
                            </c:otherwise>
                        </c:choose>
                    </h1>

                    <p class="text-muted mb-0">
                        Create and manage products in your inventory.
                    </p>
                </div>
            </div>
        </div>

        <section class="row g-3">

            <!-- Form -->
            <div class="col-12 col-xl-8">

                <form action="${pageContext.request.contextPath}/product/save"
                      method="POST"
                      class="panel">

                    <input type="hidden"
                           name="id"
                           value="${product.id}">

                    <div class="panel-header">
                        <div>
                            <h2 class="h5 mb-1 section-title">
                                <i class="bi bi-pencil-square"></i>
                                <span>Product Information</span>
                            </h2>

                            <p class="text-muted mb-0">
                                Enter product details below.
                            </p>
                        </div>
                    </div>

                    <c:if test="${not empty error}">
                        <div class="alert alert-danger">
                            ${error}
                        </div>
                    </c:if>

                    <div class="row g-3">

                        <!-- Product Name -->
                        <div class="col-md-6">
                            <label class="form-label">
                                Product Name
                            </label>

                            <input type="text"
                                   name="name"
                                   value="${product.name}"
                                   class="form-control"
                                   required
                                   placeholder="Enter product name">
                        </div>

                        <!-- Price -->
                        <div class="col-md-6">
                            <label class="form-label">
                                Price (VNĐ)
                            </label>

                            <input type="number"
                                   name="price"
                                   value="${product.price}"
                                   class="form-control"
                                   required
                                   placeholder="Enter price">
                        </div>

                        <!-- Category -->
                        <div class="col-md-6">
                            <label class="form-label">
                                Category
                            </label>

                            <select name="categoryId"
                                    class="form-select"
                                    required>

                                <option value="">
                                    Select Category
                                </option>

                                <c:forEach var="category"
                                           items="${categories}">

                                    <option value="${category.id}"
                                            ${product.categoryId == category.id ? 'selected' : ''}>

                                        ${category.name}

                                    </option>

                                </c:forEach>

                            </select>
                        </div>

                        <!-- Image URL -->
                        <div class="col-md-6">
                            <label class="form-label">
                                Image URL
                            </label>

                            <input type="text"
                                   name="imageUrl"
                                   value="${product.imageUrl}"
                                   class="form-control"
                                   placeholder="https://image-url.com">
                        </div>

                        <!-- Description -->
                        <div class="col-12">
                            <label class="form-label">
                                Description
                            </label>

                            <textarea name="description"
                                      rows="5"
                                      class="form-control"
                                      placeholder="Enter product description...">${product.description}</textarea>
                        </div>

                    </div>

                    <div class="d-flex justify-content-end gap-2 mt-4">

                        <a href="${pageContext.request.contextPath}/product/admin/list"
                           class="btn btn-outline-secondary">
                            Cancel
                        </a>

                        <button type="submit"
                                class="btn btn-primary">

                            <i class="bi bi-save"></i>
                            Save Product

                        </button>

                    </div>

                </form>

            </div>

            <!-- Right Panel -->
            <div class="col-12 col-xl-4">

                <div class="panel h-100">

                    <h2 class="h5 mb-3 section-title">
                        <i class="bi bi-info-circle"></i>
                        <span>Product Guide</span>
                    </h2>

                    <div class="mb-3">
                        <strong>Product Name</strong>
                        <p class="text-muted small mb-0">
                            Use a clear and recognizable product title.
                        </p>
                    </div>

                    <div class="mb-3">
                        <strong>Category</strong>
                        <p class="text-muted small mb-0">
                            Choose the correct category for better organization.
                        </p>
                    </div>

                    <div class="mb-3">
                        <strong>Image</strong>
                        <p class="text-muted small mb-0">
                            Use a high-quality image URL for best presentation.
                        </p>
                    </div>

                    <div>
                        <strong>Description</strong>
                        <p class="text-muted small mb-0">
                            Include key features and specifications.
                        </p>
                    </div>

                </div>

            </div>

        </section>

    </div>
</main>