package com.servlet;

import com.dao.FeePaymentDAO;
import com.model.FeePayment;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList; // Added for placeholder data
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.http.*;


public class ReportServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String startDateStr = request.getParameter("startDate");
        String endDateStr = request.getParameter("endDate");
        String status = request.getParameter("status"); // Get the status parameter
        boolean includeSummary = true; // Always include summary statistics

        // --- Date Validation (from your original code) ---
        if (startDateStr == null || startDateStr.trim().isEmpty() ||
            endDateStr == null || endDateStr.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Start and End dates are required.");
            request.getRequestDispatcher("report_form.jsp").forward(request, response);
            return;
        }

        Date startDate, endDate;
        try {
            startDate = Date.valueOf(startDateStr);
            endDate = Date.valueOf(endDateStr);
            if (endDate.before(startDate)) {
                request.setAttribute("errorMessage", "End date must be after start date.");
                request.getRequestDispatcher("report_form.jsp").forward(request, response);
                return;
            }
        } catch (IllegalArgumentException e) {
            request.setAttribute("errorMessage", "Invalid date format. UseYYYY-MM-DD.");
            request.getRequestDispatcher("report_form.jsp").forward(request, response);
            return;
        }
        // --- End Date Validation ---

        try {
            FeePaymentDAO dao = new FeePaymentDAO();
            List<FeePayment> payments;

            // --- Logic to fetch payments based on status ---
            if ("ALL".equalsIgnoreCase(status)) {
                payments = dao.getAllPaymentsBetweenDates(startDate, endDate);
            } else {
                payments = dao.getPaymentsByStatusBetweenDates(status, startDate, endDate);
            }
            // --- End logic to fetch payments ---

            // --- Calculate summary statistics (always included) ---
            int totalPayments = payments.size();
            double totalAmount = payments.stream().mapToDouble(FeePayment::getAmount).sum();
            double averagePayment = totalPayments > 0 ? totalAmount / totalPayments : 0; // Still calculate, though not displayed in report_result.jsp
            
            request.setAttribute("totalPayments", totalPayments);
            request.setAttribute("totalAmount", totalAmount);
            request.setAttribute("averagePayment", averagePayment); // Still pass, in case it's used elsewhere or re-added later
            // --- End calculate summary statistics ---

            // Set attributes for the report results page
            request.setAttribute("payments", payments); // This will be used by report_result.jsp
            request.setAttribute("startDate", startDateStr); // Pass original string for display
            request.setAttribute("endDate", endDateStr); // Pass original string for display
            request.setAttribute("status", status); // Pass selected status for display
            request.setAttribute("includeSummary", includeSummary); // Pass flag for conditional rendering

            request.getRequestDispatcher("report_result.jsp").forward(request, response);
        } catch (SQLException e) {
            // Updated error message and forwarding to report_form.jsp for consistency
            request.setAttribute("errorMessage", "An unexpected database error occurred: " + e.getMessage());
            request.getRequestDispatcher("report_form.jsp").forward(request, response);
        } catch (Exception e) {
            // Catch any other unexpected errors
            request.setAttribute("errorMessage", "An unexpected error occurred: " + e.getMessage());
            request.getRequestDispatcher("report_form.jsp").forward(request, response);
        }
    }
}
