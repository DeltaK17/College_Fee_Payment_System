<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Error - Generate Report</title>
    <style>
        .container { max-width: 600px; margin: 20px auto; padding: 20px; }
        .error { padding: 10px; margin: 10px 0; background: #f2dede; color: #a94442; }
        a { color: #007bff; text-decoration: none; }
        a:hover { text-decoration: underline; }
    </style>
</head>
<body>
    <div class="container">
        <jsp:include page="nav.jsp" />
        <h2>Error Generating Report</h2>
        <div class="error">
            <c:choose>
                <c:when test="${not empty errorMessage}">
                    ${errorMessage}
                </c:when>
                <c:otherwise>
                    An error occurred while generating the report. Please try again.
                </c:otherwise>
            </c:choose>
        </div>
        <a href="report_form.jsp">Back to Report Form</a>
    </div>
</body>
</html>