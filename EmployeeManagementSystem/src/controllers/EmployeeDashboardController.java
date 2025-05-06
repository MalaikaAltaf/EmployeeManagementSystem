package controllers;

import views.EmployeeDashboardView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmployeeDashboardController {
    private EmployeeDashboardView view;

    private Timer timer;
    private int workingSeconds = 0;
    private int breakSeconds = 0;
    private boolean onBreak = false;

    public EmployeeDashboardController(String employeeName) {
        view = new EmployeeDashboardView(employeeName);
        initController();
        view.setVisible(true);
        startWorkingTimer();  // Start working timer when dashboard loads
    }

    private void initController() {
        view.logoutBtn.addActionListener(_ -> handleLogout());

        // Navigation (placeholders)
        view.dashboardBtn.addActionListener(_ -> System.out.println("Dashboard clicked"));
        view.profileBtn.addActionListener(_ -> System.out.println("Profile clicked"));
        view.leaveBtn.addActionListener(_ -> System.out.println("Leave clicked"));
        view.salaryBtn.addActionListener(_ -> System.out.println("Salary clicked"));
        view.settingBtn.addActionListener(_ -> System.out.println("Setting clicked"));

        // Timer buttons
        view.startBreakBtn.addActionListener(_ -> startBreak());
        view.endBreakBtn.addActionListener(_ -> endBreak());
    }

    private void startWorkingTimer() {
        timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (onBreak) {
                    breakSeconds++;
                    view.breakTimeLabel.setText("Break Time: " + formatTime(breakSeconds));
                } else {
                    workingSeconds++;
                    view.workingTimeLabel.setText("Working Time: " + formatTime(workingSeconds));
                }
            }
        });
        timer.start();
    }

    private void startBreak() {
        onBreak = true;
    }

    private void endBreak() {
        onBreak = false;
    }

    private String formatTime(int totalSeconds) {
        int hrs = totalSeconds / 3600;
        int mins = (totalSeconds % 3600) / 60;
        int secs = totalSeconds % 60;
        return String.format("%02d:%02d:%02d", hrs, mins, secs);
    }

    private void handleLogout() {
        timer.stop(); // Stop the timer
        view.dispose();  // Close dashboard window
        System.out.println("Logged out!");
    }
}