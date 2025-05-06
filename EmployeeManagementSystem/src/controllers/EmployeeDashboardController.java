package controllers;

import views.EmployeeDashboardView;

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

    public EmployeeDashboardController(String employeeName) {
        this.employeeName = employeeName;
        view = new EmployeeDashboardView(employeeName);
        initController();
        view.setVisible(true);
        startTimers(); // Start the real-time timers
    }

    private void initController() {
        view.logoutBtn.addActionListener(_ -> handleLogout());

        view.dashboardBtn.addActionListener(e -> {
            view.cardLayout.show(view.contentPanel, "Dashboard");
        });

        // Add placeholders for future views
        view.profileBtn.addActionListener(_ -> JOptionPane.showMessageDialog(null, "Profile View (Coming Soon)"));
        view.leaveBtn.addActionListener(_ -> JOptionPane.showMessageDialog(null, "Leave View (Coming Soon)"));
        view.salaryBtn.addActionListener(_ -> JOptionPane.showMessageDialog(null, "Salary View (Coming Soon)"));
        view.settingBtn.addActionListener(_ -> JOptionPane.showMessageDialog(null, "Setting View (Coming Soon)"));

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
