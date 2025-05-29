<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Update Fee Payment</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
    <style>
        body { padding-top: 70px; background-color: #f5f5f5; }
        .card { border-radius: 10px; box-shadow: 0 4px 8px rgba(0,0,0,0.1); }
        .btn-primary { background-color: #dc3545; border-color: #dc3545; }
        .btn-primary:hover { background-color: #c82333; border-color: #c82333; }
        .btn-secondary { background-color: #0d6efd; border-color: #0d6efd; }
        .btn-secondary:hover { background-color: #0a58ca; border-color: #0a58ca; }
    </style>
</head>
<body>
    <jsp:include page="nav.jsp" />
    <div class="container mt-4">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card">
                    <div class="card-header bg-primary text-white">
                        <h5 class="mb-0"><i class="fas fa-edit"></i> Update Fee Payment</h5>
                    </div>
                    <div class="card-body">
                        <c:if test="${not empty errorMessage}">
                            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                ${errorMessage}
                                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                            </div>
                        </c:if>
                        <c:if test="${param.success == 'true'}">
                            <div class="alert alert-success alert-dismissible fade show" role="alert">
                                Payment updated successfully!
                                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                            </div>
                        </c:if>
                        <form action="UpdateFeePaymentServlet" method="get" class="mb-4">
                            <div class="mb-3">
                                <label for="studentId" class="form-label">Student ID</label>
                                <input type="number" class="form-control" id="studentId" name="studentId" required min="1" value="${param.studentId}">
                                <div class="invalid-feedback">Please enter a valid Student ID.</div>
                            </div>
                            <button type="submit" class="btn btn-secondary"><i class="fas fa-search"></i> Load Payment</button>
                        </form>
                        <c:if test="${not empty payment}">
                            <form action="UpdateFeePaymentServlet" method="post" class="needs-validation" novalidate>
                                <%-- Payment ID is kept as a hidden input as it's required by the servlet for update --%>
                                <input type="hidden" name="paymentId" value="${payment.paymentId}">
                                <input type="hidden" name="studentId" value="${payment.studentId}">
                                <div class="mb-3">
                                    <label for="studentName" class="form-label">Student Name</label>
                                    <input type="text" class="form-control" id="studentName" name="studentName" required value="${payment.studentName}">
                                    <div class="invalid-feedback">Please enter a student name.</div>
                                </div>
                                <div class="mb-3">
                                    <label for="paymentDate" class="form-label">Payment Date</label>
                                    <input type="date" class="form-control" id="paymentDate" name="paymentDate" required value="<fmt:formatDate value='${payment.paymentDate}' pattern='yyyy-MM-dd'/>">
                                    <div class="invalid-feedback">Please select a payment date.</div>
                                </div>
                                <div class="mb-3">
                                    <label for="amount" class="form-label">Amount</label>
                                    <%-- Formats the amount to remove decimal places for display --%>
                                    <input type="number" class="form-control" id="amount" name="amount" required min="0.01" step="0.01" value="<fmt:formatNumber value='${payment.amount}' pattern='#0'/>">
                                    <div class="invalid-feedback">Please enter a valid amount.</div>
                                </div>
                                <div class="mb-3">
                                    <label for="status" class="form-label">Status</label>
                                    <select class="form-select" id="status" name="status" required>
                                        <option value="Paid" ${payment.status == 'Paid' ? 'selected' : ''}>Paid</option>
                                        <option value="Overdue" ${payment.status == 'Overdue' ? 'selected' : ''}>Overdue</option>
                                        <option value="Unpaid" ${payment.status == 'Unpaid' ? 'selected' : ''}>Unpaid</option>
                                    </select>
                                    <div class="invalid-feedback">Please select a status.</div>
                                </div>
                                <button type="submit" class="btn btn-primary" onclick="return confirm('Are you sure you want to update this payment?');"><i class="fas fa-save"></i> Update Payment</button>
                            </form>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        (function () {
            'use strict';
            const forms = document.querySelectorAll('.needs-validation');
            Array.from(forms).forEach(form => {
                form.addEventListener('submit', event => {
                    if (!form.checkValidity()) {
                        event.preventDefault();
                        event.stopPropagation();
                    }
                    form.classList.add('was-validated');
                }, false);
            });
        })();
    </script>
</body>
</html>
