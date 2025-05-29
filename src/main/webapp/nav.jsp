<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
    <div class="container-fluid">
        <a class="navbar-brand" href="#"><i class="fas fa-university"></i> College Fee System</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item">
                    <a class="nav-link" href="feepaymentadd.jsp"><i class="fas fa-plus"></i> Add Payment</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="feepaymentupdate.jsp"><i class="fas fa-edit"></i> Update Payment</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="feepaymentdelete.jsp"><i class="fas fa-trash"></i> Delete Payment</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="DisplayFeePaymentsServlet"><i class="fas fa-list"></i> View Payments</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="report_form.jsp"><i class="fas fa-file-alt"></i> Reports</a>
                </li>
            </ul>
        </div>
    </div>
</nav>