package controllers;

import models.AdminLeaveModel;
import views.AdminLeaveView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.List;

public class AdminLeaveController {
    private final AdminLeaveView view;
    private final AdminLeaveModel model;

    public AdminLeaveController(AdminLeaveView view, AdminLeaveModel model) {
        this.view = view;
        this.model = model;

        initController();
        loadLeaveData("Pending");
    }

    private void initController() {
        view.btnPending.addActionListener(e -> loadLeaveData("Pending"));
        view.btnApproved.addActionListener(e -> loadLeaveData("Approved"));
        view.btnRejected.addActionListener(e -> loadLeaveData("Rejected"));

        view.btnAccept.addActionListener(e -> updateLeave("Approved"));
        view.btnReject.addActionListener(e -> updateLeave("Rejected"));

        view.leaveTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = view.leaveTable.rowAtPoint(e.getPoint());
                int col = view.leaveTable.columnAtPoint(e.getPoint());
                if (col == 5) { // 'View' button
                    int leaveId = (int) view.leaveTable.getValueAt(row, 0);
                    showLeaveDetails(leaveId);
                }
            }
        });
    }

    private void loadLeaveData(String status) {
        List<Object[]> leaves = model.getLeavesByStatus(status);
        DefaultTableModel tm = view.tableModel;
        tm.setRowCount(0); // clear
        for (Object[] row : leaves) {
            tm.addRow(row);
        }
        view.detailPanel.setVisible(false);
    }

    private void showLeaveDetails(int leaveId) {
        Object[] details = model.getLeaveDetails(leaveId);
        if (details != null) {
            view.lblEmpName.setText("Name: " + details[1]);
            view.lblEmail.setText("Email: " + details[2]);
            view.lblStartDate.setText("Start: " + details[3]);
            view.lblEndDate.setText("End: " + details[4]);
            view.lblReason.setText("Reason: " + details[5]);
            view.detailPanel.putClientProperty("leaveId", leaveId);
            view.detailPanel.setVisible(true);
        }
    }

    private void updateLeave(String status) {
        Integer leaveId = (Integer) view.detailPanel.getClientProperty("leaveId");
        if (leaveId != null) {
            boolean success = model.updateLeaveStatus(leaveId, status);
            if (success) {
                JOptionPane.showMessageDialog(view, "Leave status updated to " + status);
                loadLeaveData("Pending"); // Refresh to remove from current view
                view.detailPanel.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(view, "Failed to update leave status.");
            }
        }
    }
}