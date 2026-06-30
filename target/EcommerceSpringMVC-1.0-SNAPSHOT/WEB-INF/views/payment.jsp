<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thanh toán — TechShop</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background: #f0f2f5; }
        .payment-card { max-width: 680px; margin: 2.5rem auto; }
        .card-chip { width: 38px; height: 28px; background: linear-gradient(135deg, #d4af37, #f5e27a); border-radius: 5px; }
        .card-preview { background: linear-gradient(135deg, #1a1a2e, #16213e); color: #fff; border-radius: 16px; padding: 1.5rem; min-height: 170px; position: relative; }
        .card-preview .card-number { font-size: 1.3rem; letter-spacing: 4px; margin: 1rem 0 0.5rem; font-family: monospace; }
        .card-preview .card-network { position: absolute; top: 1.2rem; right: 1.5rem; font-size: 1.8rem; }
        .step-badge { background: #6366f1; color: #fff; border-radius: 50%; width: 28px; height: 28px; display: inline-flex; align-items: center; justify-content: center; font-weight: 700; font-size: 0.85rem; }
    </style>
</head>
<body>
<div class="payment-card">
    <div class="d-flex align-items-center gap-2 mb-3">
        <a href="${pageContext.request.contextPath}/orders" class="btn btn-sm btn-outline-secondary">← Đơn hàng</a>
        <h5 class="mb-0 fw-bold">Xác nhận thanh toán</h5>
    </div>

    <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
    </c:if>

    <div class="row g-3">
        <!-- Tóm tắt đơn hàng -->
        <div class="col-12">
            <div class="card border-0 shadow-sm">
                <div class="card-header bg-white fw-bold">
                    <span class="step-badge me-2">1</span> Đơn hàng #${order.id}
                </div>
                <div class="card-body p-0">
                    <table class="table table-sm align-middle mb-0">
                        <thead class="table-light">
                            <tr><th>Sản phẩm</th><th class="text-center">SL</th><th class="text-end">Thành tiền</th></tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${order.items}" var="item">
                            <tr>
                                <td>${item.productName}</td>
                                <td class="text-center">${item.quantity}</td>
                                <td class="text-end"><fmt:formatNumber value="${item.price * item.quantity}" maxFractionDigits="0"/> VNĐ</td>
                            </tr>
                            </c:forEach>
                        </tbody>
                        <tfoot>
                            <tr class="fw-bold">
                                <td colspan="2" class="text-end text-danger">Tổng cộng:</td>
                                <td class="text-end text-danger"><fmt:formatNumber value="${order.totalAmount}" maxFractionDigits="0"/> VNĐ</td>
                            </tr>
                        </tfoot>
                    </table>
                </div>
            </div>
        </div>

        <!-- Form thanh toán -->
        <div class="col-12">
            <div class="card border-0 shadow-sm">
                <div class="card-header bg-white fw-bold">
                    <span class="step-badge me-2">2</span> Phương thức thanh toán:
                    <c:choose>
                        <c:when test="${order.paymentMethod eq 'CARD'}">
                            <span class="badge bg-primary ms-1">💳 Thẻ ngân hàng</span>
                        </c:when>
                        <c:otherwise>
                            <span class="badge bg-success ms-1">🚚 COD — Thanh toán khi nhận hàng</span>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="card-body">

                    <c:choose>
                        <c:when test="${order.paymentMethod eq 'CARD'}">
                            <!-- Preview thẻ -->
                            <div class="card-preview mb-4">
                                <div class="d-flex justify-content-between align-items-start">
                                    <div class="card-chip"></div>
                                    <div class="card-network">💳</div>
                                </div>
                                <div class="card-number" id="cardNumberPreview">•••• •••• •••• ••••</div>
                                <div class="d-flex justify-content-between">
                                    <small id="cardNamePreview">TÊN CHỦ THẺ</small>
                                    <small id="cardExpiryPreview">MM/YY</small>
                                </div>
                            </div>

                            <form action="${pageContext.request.contextPath}/payment/process" method="POST">
                                <input type="hidden" name="orderId" value="${order.id}">
                                <input type="hidden" name="action" value="confirm">

                                <div class="mb-3">
                                    <label class="form-label fw-semibold">Số thẻ</label>
                                    <input type="text" class="form-control form-control-lg font-monospace"
                                           id="cardNumber" name="cardNumber" maxlength="19"
                                           placeholder="1234 5678 9012 3456" required
                                           oninput="formatCard(this)">
                                </div>
                                <div class="mb-3">
                                    <label class="form-label fw-semibold">Tên chủ thẻ</label>
                                    <input type="text" class="form-control" id="cardName" name="cardName"
                                           placeholder="NGUYEN VAN A" required
                                           oninput="document.getElementById('cardNamePreview').textContent = this.value.toUpperCase() || 'TÊN CHỦ THẺ'">
                                </div>
                                <div class="row g-3">
                                    <div class="col-6">
                                        <label class="form-label fw-semibold">Ngày hết hạn</label>
                                        <input type="text" class="form-control" name="expiry" maxlength="5"
                                               placeholder="MM/YY" required
                                               oninput="formatExpiry(this)">
                                    </div>
                                    <div class="col-6">
                                        <label class="form-label fw-semibold">CVV</label>
                                        <input type="password" class="form-control" name="cvv" maxlength="3"
                                               placeholder="•••" required>
                                        <div class="form-text text-muted">Nhập <code>000</code> để test thất bại</div>
                                    </div>
                                </div>

                                <div class="d-flex gap-2 mt-4">
                                    <button type="submit" class="btn btn-primary btn-lg flex-grow-1">
                                        🔒 Thanh toán <fmt:formatNumber value="${order.totalAmount}" maxFractionDigits="0"/> VNĐ
                                    </button>
                                    <button type="submit" formaction="${pageContext.request.contextPath}/payment/process"
                                            onclick="document.querySelector('[name=action]').value='cancel'"
                                            class="btn btn-outline-danger btn-lg">Hủy</button>
                                </div>
                            </form>
                        </c:when>

                        <c:otherwise>
                            <!-- COD -->
                            <div class="alert alert-success d-flex align-items-center gap-3">
                                <span style="font-size:2rem">🚚</span>
                                <div>
                                    <strong>Thanh toán khi nhận hàng (COD)</strong>
                                    <div class="text-muted small">Bạn sẽ thanh toán <strong><fmt:formatNumber value="${order.totalAmount}" maxFractionDigits="0"/> VNĐ</strong> khi nhận được hàng.</div>
                                </div>
                            </div>
                            <div class="mb-2 text-muted small">
                                Địa chỉ: <strong>${order.address}</strong> — SĐT: <strong>${order.phone}</strong>
                            </div>
                            <form action="${pageContext.request.contextPath}/payment/process" method="POST" class="d-flex gap-2 mt-3">
                                <input type="hidden" name="orderId" value="${order.id}">
                                <input type="hidden" name="action" value="confirm">
                                <button type="submit" class="btn btn-success btn-lg flex-grow-1">✅ Xác nhận đặt hàng</button>
                                <button type="submit"
                                        onclick="document.querySelector('[name=action]').value='cancel'"
                                        class="btn btn-outline-danger btn-lg">Hủy đơn</button>
                            </form>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
function formatCard(input) {
    let v = input.value.replace(/\D/g, '').substring(0, 16);
    input.value = v.replace(/(.{4})/g, '$1 ').trim();
    document.getElementById('cardNumberPreview').textContent =
        (v + '................').substring(0, 16).replace(/(.{4})/g, '$1 ').trim().replace(/\d/g, (c, i) => i < 12 ? '•' : c);
}
function formatExpiry(input) {
    let v = input.value.replace(/\D/g, '').substring(0, 4);
    if (v.length >= 3) v = v.substring(0,2) + '/' + v.substring(2);
    input.value = v;
    document.getElementById('cardExpiryPreview').textContent = v || 'MM/YY';
}
</script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
