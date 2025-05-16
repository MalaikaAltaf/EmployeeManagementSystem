package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminHomeModel {

    // Get total number of employees
    public int getTotalEmployees() throws SQLException {
        int total = 0;
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT COUNT(*) FROM employee";  // ✅ correct table name
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                total = rs.getInt(1);
            }
        }
        return total;
    }

    // Get total number of departments (distinct values in employee table)
    public int getTotalDepartments() throws SQLException {
        int total = 0;
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT COUNT(DISTINCT department) FROM employee";  // ✅ fix this
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                total = rs.getInt(1);
            }
        }
        return total;
    }

    // Get total monthly pay sum of all employees
    public double getTotalMonthlyPay() throws SQLException {
        double totalPay = 0.0;
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT SUM(salary) FROM employee";  // ✅ correct table
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                totalPay = rs.getDouble(1);
            }
        }
        return totalPay;
    }

    // Get count of leave requests by their status
    public int getLeaveCountByStatus(String status) throws SQLException {
        int count = 0;
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT COUNT(*) FROM leave_requests WHERE status = ?";  // ✅ correct
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, status);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        }
        return count;
    }
}