package controllers;

import views.EmployeeDashboardView;

public class EmployeeDashboardController {
    private EmployeeDashboardView view;

    public EmployeeDashboardController(String employeeName) {
        view = new EmployeeDashboardView(employeeName);
        initController();
        view.setVisible(true);
    }

    private void initController() {
        view.logoutBtn.addActionListener(_ -> handleLogout());

        // Add more navigation logic here:
        view.dashboardBtn.addActionListener(_ -> System.out.println("Dashboard clicked"));
        view.profileBtn.addActionListener(_ -> System.out.println("Profile clicked"));
        view.leaveBtn.addActionListener(_ -> System.out.println("Leave clicked"));
        view.salaryBtn.addActionListener(_ -> System.out.println("Salary clicked"));
        view.settingBtn.addActionListener(_ -> System.out.println("Setting clicked"));
    }

    private void handleLogout() {
        view.dispose();  // Close dashboard window
        // Redirect to login view
        System.out.println("Logged out!");
    }
}
