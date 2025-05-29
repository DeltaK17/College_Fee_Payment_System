<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Fee Payment Report Results</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
    <link href="https://cdn.datatables.net/1.13.4/css/dataTables.bootstrap5.min.css" rel="stylesheet">
    <style>
        body { padding-top: 70px; background-color: #f5f5f5; }
        .card { border-radius: 10px; box-shadow: 0 4px 8px rgba(0,0,0,0.1); }
        .summary-card { background-color: #f8f9fa; border-left: 4px solid #dc3545; }
    </style>
</head>
<body>
    <jsp:include page="nav.jsp" />
    <div class="container mt-4">
        <div class="card">
            <div class="card-header bg-primary text-white">
                <h5 class="mb-0">
                    <i class="fas fa-file-alt"></i>
                    <c:choose>
                        <c:when test="${status == 'ALL'}">All Payments</c:when>
                        <c:when test="${status == 'Paid'}">Paid Students</c:when>
                        <c:when test="${status == 'Overdue'}">Overdue Payments</c:when>
                        <c:when test="${status == 'Unpaid'}">Unpaid Students</c:when>
                    </c:choose>
                    Report (${startDate} to ${endDate})
                </h5>
            </div>
            <div class="card-body">
                <c:if test="${includeSummary}">
                    <div class="row mb-4">
                        <div class="col-md-6">
                            <div class="card summary-card p-3">
                                <h6>Total Payments</h6>
                                <h4>${totalPayments}</h4>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="card summary-card p-3">
                                <h6>Total Amount</h6>
                                <%-- Set currencySymbol to Indian Rupee (₹) --%>
                                <h4><fmt:formatNumber value="${totalAmount}" type="currency" currencySymbol="₹"/></h4>
                            </div>
                        </div>
                    </div>
                </c:if>

                <c:if test="${empty payments}">
                    <div class="alert alert-info" role="alert">
                        No payments found for the selected criteria.
                    </div>
                </c:if>
                <c:if test="${not empty payments}">
                    <table id="reportTable" class="table table-striped table-bordered">
                        <thead>
                            <tr>
                                <th>Payment ID</th>
                                <th>Student ID</th>
                                <th>Student Name</th>
                                <th>Payment Date</th>
                                <th>Amount</th>
                                <th>Status</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="payment" items="${payments}">
                                <tr>
                                    <td>${payment.paymentId}</td>
                                    <td>${payment.studentId}</td>
                                    <td>${payment.studentName}</td>
                                    <td><fmt:formatDate value="${payment.paymentDate}" pattern="dd-MM-yyyy"/></td>
                                    <td>
                                        <%-- Set currencySymbol to Indian Rupee (₹) --%>
                                        <fmt:formatNumber value="${payment.amount}" type="currency" currencySymbol="₹"/>
                                    </td>
                                    <td>
                                        <span class="badge
                                            <c:choose>
                                                <c:when test="${payment.status == 'Paid'}">bg-success</c:when>
                                                <c:when test="${payment.status == 'Overdue'}">bg-warning</c:when>
                                                <c:when test="${payment.status == 'Unpaid'}">bg-danger</c:when>
                                            </c:choose>">
                                            ${payment.status}
                                        </span>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:if>
                <a href="report_form.jsp" class="btn btn-primary mt-3"><i class="fas fa-arrow-left"></i> Back to Report Form</a>
                <button onclick="window.print()" class="btn btn-secondary mt-3 ms-2"><i class="fas fa-print"></i> Print Report</button>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.datatables.net/1.13.4/js/jquery.dataTables.min.js"></script>
    <script src="https://cdn.datatables.net/1.13.4/js/dataTables.bootstrap5.min.js"></script>
    <script>
        $(document).ready(function () {
            $('#reportTable').DataTable({
                "pageLength": 10,
                "order": [[1, "asc"]], // Sort by StudentID
                "dom": '<"top"f>rt<"bottom"lip><"clear">'
            });
        });
    </script>
</body>
</html>
