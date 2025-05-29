package com.servlet;

import com.dao.FeePaymentDAO;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteFeePaymentServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String paymentIdStr = request.getParameter("paymentId"); // Changed from studentId to paymentId

        if (paymentIdStr == null || paymentIdStr.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Payment ID is required."); // Updated error message
            request.getRequestDispatcher("feepaymentdelete.jsp").forward(request, response);
            return;
        }

        int paymentId; // Changed variable name
        try {
            paymentId = Integer.parseInt(paymentIdStr);
            if (paymentId <= 0) {
                request.setAttribute("errorMessage", "Invalid Payment ID."); // Updated error message
                request.getRequestDispatcher("feepaymentdelete.jsp").forward(request, response);
                return;
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid Payment ID format."); // Updated error message
            request.getRequestDispatcher("feepaymentdelete.jsp").forward(request, response);
            return;
        }

        try {
            FeePaymentDAO dao = new FeePaymentDAO();
            // Call the new deletePaymentById method
            boolean success = dao.deletePaymentById(paymentId); // Changed method call
            if (success) {
                response.sendRedirect("feepaymentdelete.jsp?success=true");
            } else {
                request.setAttribute("errorMessage", "No payment found for Payment ID " + paymentId + "."); // Updated error message
                request.getRequestDispatcher("feepaymentdelete.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            request.setAttribute("errorMessage", "An unexpected error occurred: " + e.getMessage());
            request.getRequestDispatcher("feepaymentdelete.jsp").forward(request, response);
        }
    }
}
