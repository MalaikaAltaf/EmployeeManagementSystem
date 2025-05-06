package models;

import java.sql.*;
import java.util.HashMap;

public class EmployeeModel {

    // Existing method: Get employee data by email
    public HashMap<String, Object> getEmployeeDataByEmail(String email) {
        HashMap<String, Object> data = new HashMap<>();

        try (Connection con = DBConnection.getConnection()) {
            String query = "SELECT * FROM employee WHERE email = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                data.put("emp_id", rs.getInt("emp_id"));
                data.put("first_name", rs.getString("first_name"));
                data.put("last_name", rs.getString("last_name"));
                data.put("email", rs.getString("email"));
                data.put("phone", rs.getString("phone"));
                data.put("department", rs.getString("department"));
                data.put("designation", rs.getString("designation"));
                data.put("date_joined", rs.getDate("date_joined"));
                data.put("salary", rs.getBigDecimal("salary"));
                data.put("profile_pic", rs.getBytes("profile_pic"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }

    // New method: Get employee data by employee ID
    public HashMap<String, Object> getEmployeeDataById(int empId) {
        HashMap<String, Object> data = new HashMap<>();

        try (Connection con = DBConnection.getConnection()) {
            String query = "SELECT * FROM employee WHERE emp_id = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, empId);  // Use the empId parameter to fetch data
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                data.put("emp_id", rs.getInt("emp_id"));
                data.put("first_name", rs.getString("first_name"));
                data.put("last_name", rs.getString("last_name"));
                data.put("email", rs.getString("email"));
                data.put("phone", rs.getString("phone"));
                data.put("department", rs.getString("department"));
                data.put("designation", rs.getString("designation"));
                data.put("date_joined", rs.getDate("date_joined"));
                data.put("salary", rs.getBigDecimal("salary"));
                data.put("profile_pic", rs.getBytes("profile_pic"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }

    // Existing method: Save the profile picture
    public void saveProfilePicture(int empId, byte[] imageBytes) {
        try (Connection con = DBConnection.getConnection()) {
            String sql = "UPDATE employee SET profile_pic = ? WHERE emp_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setBytes(1, imageBytes);
            ps.setInt(2, empId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
