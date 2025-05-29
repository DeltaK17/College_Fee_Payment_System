package com.servlet;

import com.dao.FeePaymentDAO;
import com.model.FeePayment;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.*;

public class AddFeePaymentServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String studentIdStr = request.getParameter("studentId");
        String studentName = request.getParameter("studentName");
        String paymentDateStr = request.getParameter("paymentDate");
        String amountStr = request.getParameter("amount");
        String status = request.getParameter("status");

        // Validate inputs
        if (studentIdStr == null || studentIdStr.trim().isEmpty() ||
            studentName == null || studentName.trim().isEmpty() ||
            paymentDateStr == null || paymentDateStr.trim().isEmpty() ||
            amountStr == null || amountStr.trim().isEmpty() ||
            status == null || status.trim().isEmpty()) {
            request.setAttribute("errorMessage", "All fields are required.");
            request.getRequestDispatcher("feepaymentadd.jsp").forward(request, response);
            return;
        }

        int studentId;
        double amount;
        Date paymentDate;
        try {
            studentId = Integer.parseInt(studentIdStr);
            if (studentId <= 0) {
                request.setAttribute("errorMessage", "Invalid Student ID.");
                request.getRequestDispatcher("feepaymentadd.jsp").forward(request, response);
                return;
            }
            amount = Double.parseDouble(amountStr);
            if (amount <= 0) {
                request.setAttribute("errorMessage", "Amount must be positive.");
                request.getRequestDispatcher("feepaymentadd.jsp").forward(request, response);
                return;
            }
            paymentDate = Date.valueOf(paymentDateStr);
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid number format for Student ID or Amount.");
            request.getRequestDispatcher("feepaymentadd.jsp").forward(request, response);
            return;
        } catch (IllegalArgumentException e) {
            request.setAttribute("errorMessage", "Invalid date format. Use YYYY-MM-DD.");
            request.getRequestDispatcher("feepaymentadd.jsp").forward(request, response);
            return;
        }

        if (!status.equals("Paid") && !status.equals("Overdue") && !status.equals("Unpaid")) {
            request.setAttribute("errorMessage", "Invalid status.");
            request.getRequestDispatcher("feepaymentadd.jsp").forward(request, response);
            return;
        }

        FeePayment payment = new FeePayment();
        payment.setStudentId(studentId);
        payment.setStudentName(studentName);
        payment.setPaymentDate(paymentDate);
        payment.setAmount(amount);
        payment.setStatus(status);

        try {
            FeePaymentDAO dao = new FeePaymentDAO();
            // Removed manual setting of PaymentID as it's AUTO_INCREMENT in the database
            boolean success = dao.addPayment(payment);
            if (success) {
                response.sendRedirect("feepaymentadd.jsp?success=true");
            } else {
                request.setAttribute("errorMessage", "Failed to add payment. Please try again.");
                request.getRequestDispatcher("feepaymentadd.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            if (e.getMessage().contains("A payment for Student ID")) {
                request.setAttribute("errorMessage", e.getMessage());
            } else {
                request.setAttribute("errorMessage", "An unexpected error occurred: " + e.getMessage());
            }
            request.getRequestDispatcher("feepaymentadd.jsp").forward(request, response);
        }
    }
}
