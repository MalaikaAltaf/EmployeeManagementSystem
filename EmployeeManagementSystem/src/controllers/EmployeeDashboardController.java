package controllers;

import views.EmployeeDashboardView;
import views.EmployeeLeaveView;
import controllers.EmployeeLeaveController;
import views.EmployeeProfileView;
import controllers.EmployeeProfileController;
import models.EmployeeModel;
import views.EmployeeSalaryView;
import controllers.EmployeeSalaryController;
import models.SalaryModel;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import views.SettingsView; // ✅ NEW
import controllers.SettingsController; // ✅ NEW

public class EmployeeDashboardController {
    private EmployeeDashboardView view;
    private Timer timer;
    private int workingSeconds = 0;
    private int breakSeconds = 0;
    private boolean onBreak = false;
    private String employeeName;
    private int empId;

    // ✅ Leave View and Controller
    private EmployeeLeaveView leaveView;
    private EmployeeLeaveController leaveController;

    // ✅ Profile View and Controller
    private EmployeeProfileView profileView;
    private EmployeeProfileController profileController;

    // ✅ Salary View and Controller
    private EmployeeSalaryView salaryView;
    private EmployeeSalaryController salaryController;

    // ✅ Settings View and Controller
    private SettingsView settingsView;
    private SettingsController settingsController;

    public EmployeeDashboardController(String employeeName, int empId) {
        this.employeeName = employeeName;
        this.empId = empId;

        view = new EmployeeDashboardView(employeeName);

        // ✅ Initialize Leave View/Controller
        leaveView = new EmployeeLeaveView();
        leaveController = new EmployeeLeaveController(leaveView, new models.LeaveModel(), empId);
        view.contentPanel.add(leaveView, "Leave");

        // ✅ Initialize Profile View/Controller
        profileView = new EmployeeProfileView();
        profileController = new EmployeeProfileController(profileView, new EmployeeModel(), empId);
        view.contentPanel.add(profileView, "Profile");

        // ✅ Initialize Salary View/Controller
        salaryView = new EmployeeSalaryView();
        salaryController = new EmployeeSalaryController(salaryView, new SalaryModel(), empId);
        view.contentPanel.add(salaryView, "Salary");

        // ✅ Initialize Settings View/Controller
        settingsView = new SettingsView();
        settingsController = new SettingsController(settingsView, new EmployeeModel(), empId);
        view.contentPanel.add(settingsView, "Settings");

        initController();
        view.setVisible(true);
        startTimers();
    }

    private void initController() {
        view.logoutBtn.addActionListener(_ -> handleLogout());

        view.dashboardBtn.addActionListener(e -> {
            view.cardLayout.show(view.contentPanel, "Dashboard");
        });

        // ✅ Show Profile Panel when "My Profile" is clicked
        view.profileBtn.addActionListener(e -> {
            profileController.loadEmployeeProfile(); // optional: refresh data
            view.cardLayout.show(view.contentPanel, "Profile");
        });

        view.leaveBtn.addActionListener(e -> {
            leaveController.refreshLeaveTable();
            view.cardLayout.show(view.contentPanel, "Leave");
        });

        // ✅ Show Salary Panel when "Salary" is clicked
        view.salaryBtn.addActionListener(e -> {
            salaryController.loadSalaryData(); // optional: refresh salary data
            view.cardLayout.show(view.contentPanel, "Salary");
        });

        // ✅ Show Settings Panel when "Setting" is clicked
        view.settingBtn.addActionListener(e -> {
            view.cardLayout.show(view.contentPanel, "Settings");
        });

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
