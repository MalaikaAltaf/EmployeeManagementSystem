package controllers;

import models.LeaveModel;
import views.EmployeeLeaveView;

import javax.swing.*;
import java.sql.Connection;
import java.util.List;

public class EmployeeLeaveController {
    private EmployeeLeaveView view;
    private LeaveModel model;
    private int empId;

    public EmployeeLeaveController(EmployeeLeaveView view, LeaveModel model, int empId) {
        this.view = view;
        this.model = model;
        this.empId = empId;

        loadLeaves();

        view.submitBtn.addActionListener(e -> {
            String start = view.startDateField.getText();
            String end = view.endDateField.getText();
            String reason = view.reasonArea.getText();

            if (start.isEmpty() || end.isEmpty() || reason.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill all fields.");
                return;
            }

            boolean success = model.submitLeaveRequest(empId, start, end, reason);
            if (success) {
                JOptionPane.showMessageDialog(null, "Leave request submitted.");
                view.clearForm();
                loadLeaves();
            } else {
                JOptionPane.showMessageDialog(null, "Submission failed.");
            }
        });
    }

    private void loadLeaves() {
        List<String[]> leaves = model.getLeaveRequests(empId);
        view.updateLeaveTable(leaves);
    }

    public void refreshLeaveTable() {
        loadLeaves();
    }
}
