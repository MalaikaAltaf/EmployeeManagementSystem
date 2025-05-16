package models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminSalaryModel {

    // Get employee id by username (assuming username is unique)
    public Integer getEmpIdByUsername(String username) {
        String query = "SELECT emp_id FROM employee WHERE username = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("emp_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;  // Not found
    }

    public boolean addSalary(int empId, double baseSalary, double bonuses, double deductions, double totalSalary, String paymentDate) {
        String query = "INSERT INTO salary (emp_id, base_salary, bonuses, deductions, total_salary, payment_date) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, empId);
            stmt.setDouble(2, baseSalary);
            stmt.setDouble(3, bonuses);
            stmt.setDouble(4, deductions);
            stmt.setDouble(5, totalSalary);
            stmt.setString(6, paymentDate);

            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // New method: Get all salaries with usernames for displaying in table
    public List<String[]> getAllSalariesWithUsernames() {
        List<String[]> salaryList = new ArrayList<>();
        String query = "SELECT e.username, s.base_salary, s.bonuses, s.deductions, s.total_salary, s.payment_date " +
                       "FROM salary s " +
                       "JOIN employee e ON s.emp_id = e.emp_id " +
                       "ORDER BY s.payment_date DESC";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String username = rs.getString("username");
                String baseSalary = String.format("%.2f", rs.getDouble("base_salary"));
                String bonuses = String.format("%.2f", rs.getDouble("bonuses"));
                String deductions = String.format("%.2f", rs.getDouble("deductions"));
                String totalSalary = String.format("%.2f", rs.getDouble("total_salary"));
                String paymentDate = rs.getString("payment_date");

                salaryList.add(new String[] { username, baseSalary, bonuses, deductions, totalSalary, paymentDate });
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return salaryList;
    }
}