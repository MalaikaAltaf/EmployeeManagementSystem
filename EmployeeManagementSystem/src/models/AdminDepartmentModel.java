package models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminDepartmentModel {

    public List<String> getDepartments() {
        List<String> departments = new ArrayList<>();
        String query = "SELECT dept_name FROM department ORDER BY dept_name ASC";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                departments.add(rs.getString("dept_name"));
            }
        } catch (SQLException e) {
            System.out.println("❌ Error fetching departments: " + e.getMessage());
        }

        return departments;
    }

    public void addDepartment(String department) {
        String query = "INSERT INTO department (dept_name) VALUES (?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, department);
            pstmt.executeUpdate();
            System.out.println("✅ Department added: " + department);
        } catch (SQLException e) {
            System.out.println("❌ Error adding department: " + e.getMessage());
        }
    }

    public void deleteDepartment(String department) {
        String query = "DELETE FROM department WHERE dept_name = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, department);
            pstmt.executeUpdate();
            System.out.println("🗑 Department deleted: " + department);
        } catch (SQLException e) {
            System.out.println("❌ Error deleting department: " + e.getMessage());
        }
    }

    public List<String> searchDepartment(String keyword) {
        List<String> results = new ArrayList<>();
        String query = "SELECT dept_name FROM department WHERE dept_name LIKE ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, "%" + keyword + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                results.add(rs.getString("dept_name"));
            }
        } catch (SQLException e) {
            System.out.println("❌ Error searching departments: " + e.getMessage());
        }

        return results;
    }
}