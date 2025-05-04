package controllers;

import models.DBConnection;
import views.LoginView;
import views.AdminDashboard;
import views.EmployeeDashboard;

import javax.swing.*;
import java.sql.*;

public class LoginController {

    private LoginView view;

    public LoginController(LoginView view) {
        this.view = view;

        // Attach button listeners
        view.getLoginButton().addActionListener(e -> handleLogin());
        view.getExitButton().addActionListener(e -> System.exit(0));
    }

    private void handleLogin() {
        String role = view.getRole();
        String username = view.getUsername();
        String password = view.getPassword();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Please enter all fields.");
            return;
        }

        try {
            Connection conn = DBConnection.getConnection();
            String table = role.equals("Admin") ? "admin" : "employee";
            String query = "SELECT * FROM " + table + " WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(view, "✅ Login successful as " + role + "!");

                // Open dashboard based on role
                if (role.equals("Admin")) {
                    new AdminDashboard().setVisible(true);
                } else {
                    int empId = rs.getInt("emp_id");  // Make sure `emp_id` exists in your employee table
                    new EmployeeDashboard(empId).setVisible(true);
                }

                view.dispose(); // Close the login window
            } else {
                JOptionPane.showMessageDialog(view, "❌ Invalid credentials.");
            }

            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "DB error: " + ex.getMessage());
        }
    }
}
