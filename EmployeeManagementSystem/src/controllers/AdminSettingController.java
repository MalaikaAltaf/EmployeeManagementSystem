package controllers;

import models.AdminSettingsModel;
import views.AdminSettingView;

import javax.swing.*;

public class AdminSettingController {
    private AdminSettingsModel model;
    private AdminSettingView view;

    public AdminSettingController(AdminSettingsModel model, AdminSettingView view) {
        this.model = model;
        this.view = view;

        view.getUpdateButton().addActionListener(e -> updatePassword());
    }

    private void updatePassword() {
        String username = view.getUsername();
        String currentPassword = view.getCurrentPassword();
        String newPassword = view.getNewPassword();

        if (username.isEmpty() || currentPassword.isEmpty() || newPassword.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Please fill all fields.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!model.verifyCurrentPassword(username, currentPassword)) {
            JOptionPane.showMessageDialog(view, "Incorrect current password.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean success = model.updatePassword(username, newPassword);
        if (success) {
            JOptionPane.showMessageDialog(view, "Password updated successfully.");
            clearFields();
        } else {
            JOptionPane.showMessageDialog(view, "Failed to update password.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        view.getUpdateButton().getParent().requestFocus();
        // Clear inputs
        view.getUpdateButton().getParent().repaint();
    }
}