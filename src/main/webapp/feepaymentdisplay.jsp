<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>All Payments</title>
    <style>
        table { border-collapse: collapse; width: 100%; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
    </style>
</head>
<body>
    <div class="container">
        <jsp:include page="nav.jsp" />
        <h2>All Fee Payments</h2>
        
        <table>
            <tr>
                <th>Payment ID</th>
                <th>Student ID</th>
                <th>Student Name</th>
                <th>Payment Date</th>
                <th>Amount</th>
                <th>Status</th>
            </tr>
            <c:forEach var="payment" items="${payments}">
                <tr>
                    <td>${payment.paymentId}</td>
                    <td>${payment.studentId}</td>
                    <td>${payment.studentName}</td>
                    <td>${payment.paymentDate}</td>
                    <td>${payment.amount}</td>
                    <td>${payment.status}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
</body>
</html>