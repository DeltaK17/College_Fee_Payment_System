<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>College Fee System</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            background: #f2f4f7;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
        }

        .container {
            background: #ffffff;
            padding: 30px 40px;
            border-radius: 12px;
            box-shadow: 0 10px 25px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 400px;
            text-align: center;
        }

        h1 {
            margin-bottom: 25px;
            color: #333;
            font-size: 24px;
        }

        .menu a {
            display: block;
            margin: 10px 0;
            padding: 12px;
            background: #007bff;
            color: white;
            text-decoration: none;
            border-radius: 8px;
            font-weight: 500;
            transition: background 0.3s ease;
        }

        .menu a:hover {
            background: #0056b3;
        }

        .menu a i {
            margin-right: 8px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>College Fee Payment System</h1>
        <div class="menu">
            <a href="feepaymentadd.jsp"><i class="bi bi-plus-circle"></i> Add New Payment</a>
            <a href="feepaymentupdate.jsp"><i class="bi bi-pencil-square"></i> Update Payment</a>
            <a href="feepaymentdelete.jsp"><i class="bi bi-trash"></i> Delete Payment</a>
            <a href="DisplayFeePaymentsServlet"><i class="bi bi-list-ul"></i> View All Payments</a>
            <a href="report_form.jsp"><i class="bi bi-file-earmark-bar-graph"></i> Generate Reports</a>
        </div>
    </div>
</body>
</html>
