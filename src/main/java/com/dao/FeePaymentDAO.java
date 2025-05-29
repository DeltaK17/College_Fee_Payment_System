package com.dao;

import com.model.FeePayment;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FeePaymentDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/CollegeFeeDB?useSSL=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = ""; // Replace with your MySQL password

    protected Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Database driver not found", e);
        }
        return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }

    public boolean addPayment(FeePayment payment) throws SQLException {
        // Removed PaymentID from the INSERT statement as it's AUTO_INCREMENT
        String sql = "INSERT INTO FeePayments (StudentID, StudentName, PaymentDate, Amount, Status) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            // PaymentID is auto-generated, so we don't set it here
            statement.setInt(1, payment.getStudentId());
            statement.setString(2, payment.getStudentName());
            statement.setDate(3, new java.sql.Date(payment.getPaymentDate().getTime()));
            statement.setDouble(4, payment.getAmount());
            statement.setString(5, payment.getStatus());
            return statement.executeUpdate() > 0;
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new SQLException("A payment for Student ID " + payment.getStudentId() + " already exists.", e);
        }
    }

    public boolean updatePayment(FeePayment payment) throws SQLException {
        // Changed WHERE clause to use PaymentID and removed PaymentID from SET clause
        String sql = "UPDATE FeePayments SET StudentID = ?, StudentName = ?, PaymentDate = ?, Amount = ?, Status = ? WHERE PaymentID = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, payment.getStudentId());
            statement.setString(2, payment.getStudentName());
            statement.setDate(3, new java.sql.Date(payment.getPaymentDate().getTime()));
            statement.setDouble(4, payment.getAmount());
            statement.setString(5, payment.getStatus());
            statement.setInt(6, payment.getPaymentId()); // Use PaymentID in WHERE clause
            return statement.executeUpdate() > 0;
        }
    }

    // Renamed original deletePayment to deletePaymentByStudentId for clarity
    public boolean deletePaymentByStudentId(int studentId) throws SQLException {
        String sql = "DELETE FROM FeePayments WHERE StudentID = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, studentId);
            return statement.executeUpdate() > 0;
        }
    }

    // New method to delete by PaymentID
    public boolean deletePaymentById(int paymentId) throws SQLException {
        String sql = "DELETE FROM FeePayments WHERE PaymentID = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, paymentId);
            return statement.executeUpdate() > 0;
        }
    }

    public FeePayment getPaymentByStudentId(int studentId) throws SQLException {
        String sql = "SELECT PaymentID, StudentID, StudentName, PaymentDate, Amount, Status FROM FeePayments WHERE StudentID = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, studentId);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return extractPaymentFromResultSet(rs);
                }
            }
        }
        return null;
    }

    public List<FeePayment> getAllPayments() throws SQLException {
        List<FeePayment> payments = new ArrayList<>();
        String sql = "SELECT PaymentID, StudentID, StudentName, PaymentDate, Amount, Status FROM FeePayments";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                payments.add(extractPaymentFromResultSet(rs));
            }
        }
        return payments;
    }

    /**
     * Fetches all fee payments within a specified date range.
     *
     * @param startDate The start date of the report period.
     * @param endDate The end date of the report period.
     * @return A list of FeePayment objects.
     * @throws SQLException if a database access error occurs.
     */
    public List<FeePayment> getAllPaymentsBetweenDates(java.sql.Date startDate, java.sql.Date endDate) throws SQLException {
        List<FeePayment> payments = new ArrayList<>();
        String sql = "SELECT PaymentID, StudentID, StudentName, PaymentDate, Amount, Status " +
                     "FROM FeePayments WHERE PaymentDate BETWEEN ? AND ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDate(1, startDate);
            statement.setDate(2, endDate);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    payments.add(extractPaymentFromResultSet(rs));
                }
            }
        }
        return payments;
    }

    /**
     * Fetches fee payments of a specific status within a specified date range.
     *
     * @param status The status of the payments to fetch (e.g., "Paid", "Overdue", "Unpaid").
     * @param startDate The start date of the report period.
     * @param endDate The end date of the report period.
     * @return A list of FeePayment objects matching the criteria.
     * @throws SQLException if a database access error occurs.
     */
    public List<FeePayment> getPaymentsByStatusBetweenDates(String status, java.sql.Date startDate, java.sql.Date endDate) throws SQLException {
        List<FeePayment> payments = new ArrayList<>();
        String sql = "SELECT PaymentID, StudentID, StudentName, PaymentDate, Amount, Status " +
                     "FROM FeePayments WHERE Status = ? AND PaymentDate BETWEEN ? AND ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, status);
            statement.setDate(2, startDate);
            statement.setDate(3, endDate);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    payments.add(extractPaymentFromResultSet(rs));
                }
            }
        }
        return payments;
    }

    public List<FeePayment> getUnpaidStudents(java.util.Date startDate, java.util.Date endDate) throws SQLException {
        List<FeePayment> payments = new ArrayList<>();
        String sql = "SELECT PaymentID, StudentID, StudentName, PaymentDate, Amount, Status " +
                     "FROM FeePayments WHERE Status = 'Unpaid' AND PaymentDate BETWEEN ? AND ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDate(1, new java.sql.Date(startDate.getTime()));
            statement.setDate(2, new java.sql.Date(endDate.getTime()));
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    payments.add(extractPaymentFromResultSet(rs));
                }
            }
        }
        return payments;
    }

    public List<FeePayment> getOverduePayments(java.util.Date startDate, java.util.Date endDate) throws SQLException {
        List<FeePayment> payments = new ArrayList<>();
        String sql = "SELECT PaymentID, StudentID, StudentName, PaymentDate, Amount, Status " +
                     "FROM FeePayments WHERE Status = 'Overdue' AND PaymentDate BETWEEN ? AND ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDate(1, new java.sql.Date(startDate.getTime()));
            statement.setDate(2, new java.sql.Date(endDate.getTime()));
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    payments.add(extractPaymentFromResultSet(rs));
                }
            }
        }
        return payments;
    }

    public double getTotalCollection(java.util.Date startDate, java.util.Date endDate) throws SQLException {
        String sql = "SELECT COALESCE(SUM(Amount), 0) AS total FROM FeePayments " +
                     "WHERE Status = 'Paid' AND PaymentDate BETWEEN ? AND ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDate(1, new java.sql.Date(startDate.getTime()));
            statement.setDate(2, new java.sql.Date(endDate.getTime()));
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("total");
                }
            }
        }
        return 0.0;
    }

    public int getMaxPaymentId() throws SQLException {
        String sql = "SELECT COALESCE(MAX(PaymentID), 0) FROM FeePayments";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    private FeePayment extractPaymentFromResultSet(ResultSet rs) throws SQLException {
        FeePayment payment = new FeePayment();
        payment.setPaymentId(rs.getInt("PaymentID"));
        payment.setStudentId(rs.getInt("StudentID"));
        payment.setStudentName(rs.getString("StudentName"));
        payment.setPaymentDate(rs.getDate("PaymentDate"));
        payment.setAmount(rs.getDouble("Amount"));
        payment.setStatus(rs.getString("Status"));
        return payment;
    }
}
