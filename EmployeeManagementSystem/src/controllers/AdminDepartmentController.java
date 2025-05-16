package controllers;

import models.AdminDepartmentModel;
import views.AdminDepartmentView;

import javax.swing.*;
import java.awt.event.*;
import java.util.List;

public class AdminDepartmentController {
    private AdminDepartmentModel model;
    private AdminDepartmentView view;

    public AdminDepartmentController(AdminDepartmentModel model, AdminDepartmentView view) {
        this.model = model;
        this.view = view;

        // Load all departments initially
        loadTable(model.getDepartments());

        // Add listeners
        view.addAddDepartmentListener(new AddDepartmentListener());
       

        // Table click listener for delete action
        view.tableDepartments.addMouseListener(new TableClickListener());
    }

    // Loads departments into the JTable
    private void loadTable(List<String> departments) {
        Object[][] data = new Object[departments.size()][3];
        for (int i = 0; i < departments.size(); i++) {
            data[i][0] = i + 1;
            data[i][1] = departments.get(i);
            data[i][2] = "Delete";
        }
        view.setTableData(data);
    }

    // Listener for the "Add New Department" button
    class AddDepartmentListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String dept = JOptionPane.showInputDialog(view, "Enter new department name:");
            if (dept != null && !dept.trim().isEmpty()) {
                model.addDepartment(dept.trim());
                loadTable(model.getDepartments());
            }
        }
    }

    // Mouse listener to handle delete button clicks in the JTable
    class TableClickListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            int row = view.tableDepartments.rowAtPoint(e.getPoint());
            int column = view.tableDepartments.columnAtPoint(e.getPoint());

            // If "Action" column is clicked
            if (column == 2) {
                String department = view.tableDepartments.getValueAt(row, 1).toString();
                int confirm = JOptionPane.showConfirmDialog(view, "Delete department '" + department + "'?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    model.deleteDepartment(department);
                    loadTable(model.getDepartments());
                }
            }
        }
    }
}