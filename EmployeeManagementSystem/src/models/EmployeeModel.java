package models;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class EmployeeModel {
    public boolean changePassword(int empId, String currentPassword, String newPassword) {
        boolean isUpdated = false;
        try (Connection conn = DBConnection.getConnection()) {
            // First, verify the current password
            String checkPasswordSql = "SELECT password FROM employee WHERE emp_id = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkPasswordSql);
            checkStmt.setInt(1, empId);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next() && rs.getString("password").equals(currentPassword)) {
                // If current password matches, update the password
                String updatePasswordSql = "UPDATE employee SET password = ? WHERE emp_id = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updatePasswordSql);
                updateStmt.setString(1, newPassword); // You may want to hash the password here
                updateStmt.setInt(2, empId);
                int rowsAffected = updateStmt.executeUpdate();
                if (rowsAffected > 0) {
                    isUpdated = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isUpdated;
    }

    public Map<String, Object> getEmployeeProfile(int empId) {
        Map<String, Object> profileData = new HashMap<>();
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT first_name, last_name, email, phone, department, designation, date_joined, salary, profile_pic FROM employee WHERE emp_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, empId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                profileData.put("first_name", rs.getString("first_name"));
                profileData.put("last_name", rs.getString("last_name"));
                profileData.put("email", rs.getString("email"));
                profileData.put("phone", rs.getString("phone"));
                profileData.put("department", rs.getString("department"));
                profileData.put("designation", rs.getString("designation"));
                profileData.put("date_joined", rs.getDate("date_joined"));
                profileData.put("salary", rs.getBigDecimal("salary"));
                profileData.put("profile_pic", rs.getBytes("profile_pic")); // may be null
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return profileData;
    }

    public boolean updateEmail(int empId, String newEmail) {
    String query = "UPDATE employee SET email = ? WHERE emp_id = ?";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setString(1, newEmail);
        pstmt.setInt(2, empId);
        return pstmt.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
public boolean updateTheme(int empId, String theme) {
    String query = "UPDATE employee SET theme_preference = ? WHERE emp_id = ?";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setString(1, theme);
        pstmt.setInt(2, empId);
        return pstmt.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
public String getTheme(int empId) {
    String query = "SELECT theme_preference FROM employee WHERE emp_id = ?";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setInt(1, empId);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            return rs.getString("theme_preference");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return "Light"; // Default theme if not set
}

}
