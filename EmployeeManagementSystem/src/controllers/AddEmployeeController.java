package controllers;

import models.Employee;
import models.EmployeeDAO;
import views.AddEmployeeView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class AddEmployeeController {
    private AddEmployeeView view;
    private EmployeeDAO dao;

    public AddEmployeeController(AddEmployeeView view) {
        this.view = view;
        this.dao = new EmployeeDAO();

        // Handle "Add Employee" button click
        this.view.btnSubmit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addEmployee();
            }
        });

        // Handle "Delete Employee" button click
        this.view.btnDeleteEmployee.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteEmployee();
            }
        });
    }

    // Add Employee functionality
    private void addEmployee() {
        try {
            String firstName = view.txtFirstName.getText().trim();
            String lastName = view.txtLastName.getText().trim();
            String email = view.txtEmail.getText().trim();
            String phone = view.txtPhone.getText().trim();
            String department = view.txtDepartment.getText().trim();
            String designation = view.txtDesignation.getText().trim();
            String dateJoined = view.txtDateJoined.getText().trim();
            String username = view.txtUsername.getText().trim();
            String password = new String(view.txtPassword.getPassword()).trim();

            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() ||
                phone.isEmpty() || department.isEmpty() || designation.isEmpty() ||
                dateJoined.isEmpty() || username.isEmpty() || password.isEmpty()) {

                JOptionPane.showMessageDialog(view, "⚠ Please fill in all required fields.");
                return;
            }

            if (!email.contains("@")) {
                JOptionPane.showMessageDialog(view, "⚠ Please enter a valid email address.");
                return;
            }

            if (phone.length() != 11 || !phone.matches("\\d+")) {
                JOptionPane.showMessageDialog(view, "⚠ Please enter a valid phone number with exactly 11 digits.");
                return;
            }

            double salary;
            try {
                salary = Double.parseDouble(view.txtSalary.getText().trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view, "⚠ Please enter a valid number for salary.");
                return;
            }

            // Load profile picture if selected
            byte[] imageBytes = null;
            if (view.selectedImagePath != null && !view.selectedImagePath.isEmpty()) {
                File imageFile = new File(view.selectedImagePath);
                try (FileInputStream fis = new FileInputStream(imageFile)) {
                    imageBytes = fis.readAllBytes();
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(view, "⚠ Failed to read image file.");
                    return;
                }
            }

            Employee emp = new Employee(firstName, lastName, email, phone,
                    department, designation, dateJoined, salary, username, password, imageBytes);

            boolean success = dao.addEmployee(emp);

            if (success) {
                JOptionPane.showMessageDialog(view, "✅ Employee added successfully!");
                clearForm();
            } else {
                JOptionPane.showMessageDialog(view, "❌ Failed to add employee.");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "❌ Error: " + ex.getMessage());
        }
    }

    // Delete Employee functionality
    private void deleteEmployee() {
        String username = view.txtDeleteUsername.getText().trim();

        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(view, "⚠ Please enter a username to delete.");
            return;
        }

        // Check if the employee exists and delete them
        boolean success = dao.deleteEmployee(username);
        if (success) {
            JOptionPane.showMessageDialog(view, "✅ Employee deleted successfully.");
        } else {
            JOptionPane.showMessageDialog(view, "❌ Failed to delete employee. Username may not exist.");
        }
    }

    // Clear form fields
    private void clearForm() {
        view.txtFirstName.setText("");
        view.txtLastName.setText("");
        view.txtEmail.setText("");
        view.txtPhone.setText("");
        view.txtDepartment.setText("");
        view.txtDesignation.setText("");
        view.txtDateJoined.setText("");
        view.txtSalary.setText("");
        view.txtUsername.setText("");
        view.txtPassword.setText("");
        view.lblImagePreview.setIcon(null);
        view.selectedImagePath = null;
    }
}