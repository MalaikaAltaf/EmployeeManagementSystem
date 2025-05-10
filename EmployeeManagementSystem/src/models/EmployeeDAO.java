package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EmployeeDAO {

    // Add Employee functionality (unchanged)
    public boolean addEmployee(Employee emp) {
        String query = "INSERT INTO employee (first_name, last_name, email, phone, department, designation, date_joined, salary, username, password, profile_pic) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, emp.getFirstName());
            stmt.setString(2, emp.getLastName());
            stmt.setString(3, emp.getEmail());
            stmt.setString(4, emp.getPhone());
            stmt.setString(5, emp.getDepartment());
            stmt.setString(6, emp.getDesignation());
            stmt.setString(7, emp.getDateJoined());
            stmt.setDouble(8, emp.getSalary());
            stmt.setString(9, emp.getUsername());
            stmt.setString(10, emp.getPassword());
            stmt.setBytes(11, emp.getProfilePic());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            System.out.println("❌ Error inserting employee: " + e.getMessage());
            return false;
        }
    }

    // Delete Employee functionality
    public boolean deleteEmployee(String username) {
        String query = "DELETE FROM employee WHERE username = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);

            // Check if the employee with the provided username exists
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted == 0) {
                // No rows were deleted, which means the username doesn't exist
                return false;
            }
            return true;

        } catch (SQLException e) {
            System.out.println("❌ Error deleting employee: " + e.getMessage());
            return false;
        }
    }
}