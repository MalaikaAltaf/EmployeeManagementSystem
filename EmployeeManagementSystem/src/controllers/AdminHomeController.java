package controllers;

import models.AdminHomeModel;
import views.AdminHomeView;

import javax.swing.*;

public class AdminHomeController {
    private AdminHomeModel model;
    private AdminHomeView view;

    public AdminHomeController(AdminHomeModel model, AdminHomeView view) {
        this.model = model;
        this.view = view;
        loadDashboardData();
    }

    private void loadDashboardData() {
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                try {
                    int totalEmployees = model.getTotalEmployees();
                    int totalDepartments = model.getTotalDepartments();
                    double monthlyPay = model.getTotalMonthlyPay();
                    int leaveApplied = model.getLeaveCountByStatus("Applied");
                    int leaveApproved = model.getLeaveCountByStatus("Approved");
                    int leavePending = model.getLeaveCountByStatus("Pending");
                    int leaveRejected = model.getLeaveCountByStatus("Rejected");

                    // Update UI on the Event Dispatch Thread
                    SwingUtilities.invokeLater(() -> {
                        view.getTotalEmployeesLabel().setText(String.valueOf(totalEmployees));
                        view.getTotalDepartmentsLabel().setText(String.valueOf(totalDepartments));
                        view.getMonthlyPayLabel().setText("$" + String.format("%.2f", monthlyPay));

                        view.getLeaveAppliedLabel().setText(String.valueOf(leaveApplied));
                        view.getLeaveApprovedLabel().setText(String.valueOf(leaveApproved));
                        view.getLeavePendingLabel().setText(String.valueOf(leavePending));
                        view.getLeaveRejectedLabel().setText(String.valueOf(leaveRejected));
                    });
                } catch (Exception ex) {
                    // Catch any unexpected runtime exceptions
                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(view, "Error loading dashboard data: " + ex.getMessage(),
                                "Error", JOptionPane.ERROR_MESSAGE);
                    });
                }
                return null;
            }
        };
        worker.execute();
    }
}