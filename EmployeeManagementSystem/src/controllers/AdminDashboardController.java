package controllers;

import views.AdminDashboard;
import views.AddEmployeeView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminDashboardController {
    private AdminDashboard adminView;
    private JPanel mainPanel;

    public AdminDashboardController(AdminDashboard adminView) {
        this.adminView = adminView;
        this.mainPanel = adminView.getMainPanel();

        addListeners();
    }

    private void addListeners() {
        // Employee button action
        adminView.getEmployeeButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showEmployeePanel();
            }
        });

        // Logout button action
        adminView.getLogoutButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(
                    adminView,
                    "Are you sure you want to log out?",
                    "Logout Confirmation",
                    JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    adminView.dispose(); // Close current window
                    // new LoginView().setVisible(true); // Uncomment if LoginView is implemented
                }
            }
        });

        // You can add more listeners here without affecting logout or employee functionality
    }

    private void showEmployeePanel() {
        AddEmployeeView empPanel = new AddEmployeeView();
        new AddEmployeeController(empPanel);
        mainPanel.removeAll();
        mainPanel.add(empPanel);
        mainPanel.revalidate();
        mainPanel.repaint();
    }
}