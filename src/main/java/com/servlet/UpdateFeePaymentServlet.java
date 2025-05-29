package com.servlet;

import com.dao.FeePaymentDAO;
import com.model.FeePayment;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException; // Added missing import
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateFeePaymentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String studentIdStr = request.getParameter("studentId");
        if (studentIdStr == null || studentIdStr.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Student ID is required.");
            request.getRequestDispatcher("feepaymentupdate.jsp").forward(request, response);
            return;
        }

        int studentId;
        try {
            studentId = Integer.parseInt(studentIdStr);
            if (studentId <= 0) {
                request.setAttribute("errorMessage", "Invalid Student ID.");
                request.getRequestDispatcher("feepaymentupdate.jsp").forward(request, response);
                return;
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid Student ID format.");
            request.getRequestDispatcher("feepaymentupdate.jsp").forward(request, response);
            return;
        }

        try {
            FeePaymentDAO dao = new FeePaymentDAO();
            FeePayment payment = dao.getPaymentByStudentId(studentId);
            if (payment == null) {
                request.setAttribute("errorMessage", "No payment found for Student ID " + studentId + ".");
            } else {
                request.setAttribute("payment", payment);
            }
            request.getRequestDispatcher("feepaymentupdate.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("errorMessage", "An unexpected error occurred: " + e.getMessage());
            request.getRequestDispatcher("feepaymentupdate.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String paymentIdStr = request.getParameter("paymentId");
        String studentIdStr = request.getParameter("studentId");
        String studentName = request.getParameter("studentName");
        String paymentDateStr = request.getParameter("paymentDate");
        String amountStr = request.getParameter("amount");
        String status = request.getParameter("status");

        // Validate inputs
        if (paymentIdStr == null || paymentIdStr.trim().isEmpty() ||
            studentIdStr == null || studentIdStr.trim().isEmpty() ||
            studentName == null || studentName.trim().isEmpty() ||
            paymentDateStr == null || paymentDateStr.trim().isEmpty() ||
            amountStr == null || amountStr.trim().isEmpty() ||
            status == null || status.trim().isEmpty()) {
            request.setAttribute("errorMessage", "All fields are required.");
            request.getRequestDispatcher("feepaymentupdate.jsp").forward(request, response);
            return;
        }

        int paymentId, studentId;
        double amount;
        Date paymentDate;
        try {
            paymentId = Integer.parseInt(paymentIdStr);
            if (paymentId <= 0) {
                request.setAttribute("errorMessage", "Invalid Payment ID.");
                request.getRequestDispatcher("feepaymentupdate.jsp").forward(request, response);
                return;
            }
            studentId = Integer.parseInt(studentIdStr);
            if (studentId <= 0) {
                request.setAttribute("errorMessage", "Invalid Student ID.");
                request.getRequestDispatcher("feepaymentupdate.jsp").forward(request, response);
                return;
            }
            amount = Double.parseDouble(amountStr);
            if (amount <= 0) {
                request.setAttribute("errorMessage", "Amount must be positive.");
                request.getRequestDispatcher("feepaymentupdate.jsp").forward(request, response);
                return;
            }
            paymentDate = Date.valueOf(paymentDateStr);
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid number format for Payment ID, Student ID, or Amount.");
            request.getRequestDispatcher("feepaymentupdate.jsp").forward(request, response);
            return;
        } catch (IllegalArgumentException e) {
            request.setAttribute("errorMessage", "Invalid date format. Use YYYY-MM-DD.");
            request.getRequestDispatcher("feepaymentupdate.jsp").forward(request, response);
            return;
        }

        if (!status.equals("Paid") && !status.equals("Overdue") && !status.equals("Unpaid")) {
            request.setAttribute("errorMessage", "Invalid status.");
            request.getRequestDispatcher("feepaymentupdate.jsp").forward(request, response);
            return;
        }

        FeePayment payment = new FeePayment();
        payment.setPaymentId(paymentId);
        payment.setStudentId(studentId);
        payment.setStudentName(studentName);
        payment.setPaymentDate(paymentDate);
        payment.setAmount(amount);
        payment.setStatus(status);

        try {
            FeePaymentDAO dao = new FeePaymentDAO();
            boolean success = dao.updatePayment(payment);
            if (success) {
                response.sendRedirect("feepaymentupdate.jsp?success=true");
            } else {
                request.setAttribute("errorMessage", "Failed to update payment. No payment found for Payment ID " + paymentId + ".");
                request.getRequestDispatcher("feepaymentupdate.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            request.setAttribute("errorMessage", "An unexpected error occurred: " + e.getMessage());
            request.getRequestDispatcher("feepaymentupdate.jsp").forward(request, response);
        }
    }
}
