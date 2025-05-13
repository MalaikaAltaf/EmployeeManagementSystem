package controllers;

import models.EmployeeModel;
import views.SettingsView;
import utils.ThemeManager;
import utils.ThemeManager.Theme;

import javax.swing.*;

public class SettingsController {

    private SettingsView settingsView;
    private EmployeeModel employeeModel;
    private int empId;

    public SettingsController(SettingsView settingsView, EmployeeModel employeeModel, int empId) {
        this.settingsView = settingsView;
        this.employeeModel = employeeModel;
        this.empId = empId;

        settingsView.changePasswordBtn.addActionListener(e -> handleChangePassword());
        settingsView.updateEmailBtn.addActionListener(e -> handleUpdateEmail());
        settingsView.applyThemeBtn.addActionListener(e -> handleThemeChange());
    }

    private void handleChangePassword() {
        String currentPassword = new String(settingsView.currentPasswordField.getPassword());
        String newPassword = new String(settingsView.newPasswordField.getPassword());
        String confirmPassword = new String(settingsView.confirmPasswordField.getPassword());

        if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(settingsView, "All fields are required.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(settingsView, "New passwords do not match.", "Mismatch Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean isUpdated = employeeModel.changePassword(empId, currentPassword, newPassword);
        if (isUpdated) {
            JOptionPane.showMessageDialog(settingsView, "Password changed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            clearPasswordFields();
        } else {
            JOptionPane.showMessageDialog(settingsView, "Current password is incorrect.", "Authentication Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleUpdateEmail() {
        String newEmail = settingsView.emailField.getText().trim();
        if (newEmail.isEmpty() || !newEmail.matches("^\\S+@\\S+\\.\\S+$")) {
            JOptionPane.showMessageDialog(settingsView, "Please enter a valid email address.", "Invalid Email", JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean isUpdated = employeeModel.updateEmail(empId, newEmail);
        if (isUpdated) {
            JOptionPane.showMessageDialog(settingsView, "Email updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            settingsView.emailField.setText("");
        } else {
            JOptionPane.showMessageDialog(settingsView, "Failed to update email. Email might already exist.", "Update Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleThemeChange() {
        String selectedTheme = (String) settingsView.themeComboBox.getSelectedItem();
        boolean updated = employeeModel.updateTheme(empId, selectedTheme);

        if (updated) {
            // Apply theme immediately
            if ("Dark".equalsIgnoreCase(selectedTheme)) {
                ThemeManager.setTheme(Theme.DARK);
            } else {
                ThemeManager.setTheme(Theme.LIGHT);
            }
            ThemeManager.applyTheme(settingsView);
            JOptionPane.showMessageDialog(settingsView, "Theme updated and applied!", "Theme Updated", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(settingsView, "Failed to update theme.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearPasswordFields() {
        settingsView.currentPasswordField.setText("");
        settingsView.newPasswordField.setText("");
        settingsView.confirmPasswordField.setText("");
    }
}
