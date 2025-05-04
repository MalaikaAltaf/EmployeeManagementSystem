package controllers;

import models.DBConnection;
import views.LoginView;
import views.AdminDashboard;
import views.EmployeeDashboardView;

import javax.swing.*;
import java.sql.*;

public class LoginController {

    private LoginView view;

    public LoginController(LoginView view) {
        this.view = view;

        view.getLoginButton().addActionListener(_ -> handleLogin());
        view.getExitButton().addActionListener(_ -> System.exit(0));
    }

    private void handleLogin() {
        String role = view.getRole();
        String username = view.getUsername();
        String password = view.getPassword();

        if (username.isEmpty() || password.isEmpty()) {
            showStyledMessage("Please enter all fields.", "Missing Information", JOptionPane.WARNING_MESSAGE);
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
                showStyledMessage("✅ Login successful as " + role + "!", "Success", JOptionPane.INFORMATION_MESSAGE);

                if (role.equals("Admin")) {
                    new AdminDashboard().setVisible(true);
                } else {
                    String firstName = rs.getString("first_name");
                    String lastName = rs.getString("last_name");
                    String empName = firstName + " " + lastName;

                    new EmployeeDashboardView(empName).setVisible(true);
                }

                view.dispose(); // Close the login window
            } else {
                showStyledMessage("❌ Invalid credentials.", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }

            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            showStyledMessage("❌ DB error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showStyledMessage(String message, String title, int messageType) {
        UIManager.put("OptionPane.messageForeground", new java.awt.Color(0, 121, 107)); // teal/dark green
        UIManager.put("Panel.background", java.awt.Color.white);
        JOptionPane.showMessageDialog(view, message, title, messageType);
    }
}
