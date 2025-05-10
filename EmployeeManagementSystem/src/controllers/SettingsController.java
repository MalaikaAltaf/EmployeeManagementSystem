package controllers;

import models.EmployeeModel;
import views.SettingsView;

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
    }

    private void handleChangePassword() {
        String currentPassword = new String(settingsView.currentPasswordField.getPassword());
        String newPassword = new String(settingsView.newPasswordField.getPassword());
        String confirmPassword = new String(settingsView.confirmPasswordField.getPassword());

        if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(settingsView,
                    "All fields are required.",
                    "Input Error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(settingsView,
                    "New passwords do not match.",
                    "Mismatch Error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Try to change password
        boolean isUpdated = employeeModel.changePassword(empId, currentPassword, newPassword);
        if (isUpdated) {
            JOptionPane.showMessageDialog(settingsView,
                    "Password changed successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            clearFields();
        } else {
            JOptionPane.showMessageDialog(settingsView,
                    "Current password is incorrect.",
                    "Authentication Failed",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        settingsView.currentPasswordField.setText("");
        settingsView.newPasswordField.setText("");
        settingsView.confirmPasswordField.setText("");
    }
}
