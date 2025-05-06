package controllers;

import views.EmployeeDashboardView;
import views.EmployeeProfileView;
import models.EmployeeModel;
import controllers.EmployeeProfileController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EmployeeDashboardController {
    private EmployeeDashboardView view;
    private Timer timer;
    private int workingSeconds = 0;
    private int breakSeconds = 0;
    private boolean onBreak = false;
    private String employeeName;
    private int empId; // Employee ID passed for profile data

    private EmployeeProfileView profileView; // Reference to profile view
    private EmployeeProfileController profileController; // Reference to profile controller

    public EmployeeDashboardController(String employeeName, int empId) {
        this.employeeName = employeeName;
        this.empId = empId;

        // Initialize the main dashboard view and the profile view
        view = new EmployeeDashboardView(employeeName);
        profileView = new EmployeeProfileView(empId); // Initialize profile view with employee ID
        profileController = new EmployeeProfileController(profileView, empId); // Initialize profile controller

        // Add the profile view panel to CardLayout
        view.contentPanel.add(profileView, "My Profile");

        initController();
        view.setVisible(true);
        startTimers(); // Start working timer
    }

    private void initController() {
        view.logoutBtn.addActionListener(_ -> handleLogout());

        // Handle Dashboard button click
        view.dashboardBtn.addActionListener(e -> {
            view.cardLayout.show(view.contentPanel, "Dashboard");
        });

        // Handle Profile button click
        view.profileBtn.addActionListener(e -> {
            profileController.openProfileWindow(); // Open profile in new window
        });

        // Placeholder buttons for future functionality
        view.leaveBtn.addActionListener(_ -> JOptionPane.showMessageDialog(null, "Leave View (Coming Soon)"));
        view.salaryBtn.addActionListener(_ -> JOptionPane.showMessageDialog(null, "Salary View (Coming Soon)"));
        view.settingBtn.addActionListener(_ -> JOptionPane.showMessageDialog(null, "Setting View (Coming Soon)"));

        // Break timer functionality
        view.startBreakBtn.addActionListener(_ -> onBreak = true);
        view.endBreakBtn.addActionListener(_ -> onBreak = false);
    }

    private void startTimers() {
        timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (onBreak) {
                    breakSeconds++;
                    view.breakTimeLabel.setText("Break Time: " + formatTime(breakSeconds));
                } else {
                    workingSeconds++;
                    view.workingTimeLabel.setText("Working Time: " + formatTime(workingSeconds));
                }

                // Update date every second just in case the date changes at midnight
                view.dateLabel.setText("Date: " + getCurrentDate());
            }
        });
        timer.start();
    }

    private String formatTime(int totalSeconds) {
        int hrs = totalSeconds / 3600;
        int mins = (totalSeconds % 3600) / 60;
        int secs = totalSeconds % 60;
        return String.format("%02d:%02d:%02d", hrs, mins, secs);
    }

    private String getCurrentDate() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMMM yyyy"));
    }

    private void handleLogout() {
        timer.stop();
        view.dispose();
        System.out.println("Logged out!");
    }
}
