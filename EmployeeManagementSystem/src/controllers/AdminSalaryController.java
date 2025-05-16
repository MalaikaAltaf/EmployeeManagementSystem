package controllers;

import models.AdminSalaryModel;
import views.AdminSalaryView;

import javax.swing.*;
import java.util.List;

public class AdminSalaryController {
    private AdminSalaryView view;
    private AdminSalaryModel model;

    public AdminSalaryController(AdminSalaryView view, AdminSalaryModel model) {
        this.view = view;
        this.model = model;

        view.addAddSalaryListener(e -> addSalary());

        // Load and display salary data on startup
        loadSalaryData();
    }

    private void addSalary() {
        String username = view.txtUsername.getText().trim();
        String baseStr = view.txtBaseSalary.getText().trim();
        String bonusStr = view.txtBonuses.getText().trim();
        String dedStr = view.txtDeductions.getText().trim();
        String totalStr = view.txtTotalSalary.getText().trim();
        String paymentDate = view.txtPaymentDate.getText().trim();

        if (username.isEmpty() || baseStr.isEmpty() || bonusStr.isEmpty() || dedStr.isEmpty() || totalStr.isEmpty() || paymentDate.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Please fill all fields", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double baseSalary = Double.parseDouble(baseStr);
            double bonuses = Double.parseDouble(bonusStr);
            double deductions = Double.parseDouble(dedStr);
            double totalSalary = Double.parseDouble(totalStr);

            Integer empId = model.getEmpIdByUsername(username);
            if (empId == null) {
                JOptionPane.showMessageDialog(view, "Employee not found with username: " + username, "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean success = model.addSalary(empId, baseSalary, bonuses, deductions, totalSalary, paymentDate);

            if (success) {
                JOptionPane.showMessageDialog(view, "Salary added successfully for user: " + username);
                clearFields();
                loadSalaryData();  // Refresh table data after insert
            } else {
                JOptionPane.showMessageDialog(view, "Failed to add salary.", "Database Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Please enter valid numeric values for salary fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        view.txtUsername.setText("");
        view.txtBaseSalary.setText("");
        view.txtBonuses.setText("");
        view.txtDeductions.setText("");
        view.txtTotalSalary.setText("");
        view.txtPaymentDate.setText("");
    }

    private void loadSalaryData() {
        List<String[]> salaryData = model.getAllSalariesWithUsernames();
        view.updateSalaryTable(salaryData);
    }
}