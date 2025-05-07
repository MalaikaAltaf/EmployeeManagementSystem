package controllers;

import models.EmployeeModel;
import views.SettingsView;

import java.awt.Color;

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
            settingsView.messageLabel.setText("All fields are required.");
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            settingsView.messageLabel.setText("New passwords do not match.");
            return;
        }

        // Try to change password
        boolean isUpdated = employeeModel.changePassword(empId, currentPassword, newPassword);
        if (isUpdated) {
            settingsView.messageLabel.setText("Password changed successfully!");
            settingsView.messageLabel.setForeground(Color.GREEN);
        } else {
            settingsView.messageLabel.setText("Current password is incorrect.");
        }
    }
}
