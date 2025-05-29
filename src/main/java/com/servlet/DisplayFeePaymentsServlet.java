package com.servlet;

import com.dao.FeePaymentDAO;
import com.model.FeePayment;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DisplayFeePaymentsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            FeePaymentDAO dao = new FeePaymentDAO();
            List<FeePayment> payments = dao.getAllPayments();
            request.setAttribute("payments", payments);
            request.getRequestDispatcher("feepayments.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("errorMessage", "An unexpected error occurred: " + e.getMessage());
            request.getRequestDispatcher("feepayments.jsp").forward(request, response);
        }
    }
}