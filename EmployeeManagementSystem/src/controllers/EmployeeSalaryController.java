package controllers;

import models.SalaryModel;
import views.EmployeeSalaryView;
import javax.swing.*;
import java.util.List;

public class EmployeeSalaryController {
    private EmployeeSalaryView view;
    private SalaryModel model;
    private int empId;

    public EmployeeSalaryController(EmployeeSalaryView view, SalaryModel model, int empId) {
        this.view = view;
        this.model = model;
        this.empId = empId;

        // Adding listeners to the buttons
        view.filterButton.addActionListener(e -> loadSalaryDataByStatus());
        view.downloadPayslipButton.addActionListener(e -> downloadPayslip());
    }

    // Load all salary data for a specific employee
    public void loadSalaryData() {
        List<String[]> data = model.fetchSalaryByEmpId(empId);
        view.updateSalaryTable(data);
    }

    // Load salary data based on the selected status
    public void loadSalaryDataByStatus() {
        String status = view.getSelectedStatus();

        if (status == null || status.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Please select a status.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        List<String[]> filteredData = model.fetchSalaryByStatus(empId, status);
        if (filteredData.isEmpty()) {
            JOptionPane.showMessageDialog(view, "No salary records found for the selected status.", "No Data", JOptionPane.INFORMATION_MESSAGE);
        } else {
            view.updateSalaryTable(filteredData);
        }
    }

    // Handle payslip download as a .txt file
    public void downloadPayslip() {
        int row = view.salaryTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(view, "Please select a salary record to download payslip.", "No Record Selected", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String paymentDate = (String) view.tableModel.getValueAt(row, 4); // Payment date column
        boolean result = model.generatePayslip(empId, paymentDate);

        if (result) {
            JOptionPane.showMessageDialog(view, "Payslip downloaded successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(view, "Failed to download payslip.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
