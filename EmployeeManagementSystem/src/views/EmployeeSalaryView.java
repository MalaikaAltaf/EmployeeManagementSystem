package views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class EmployeeSalaryView extends JPanel {
    public JTable salaryTable;
    public DefaultTableModel tableModel;

    public EmployeeSalaryView() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        String[] columnNames = {"Base Salary", "Bonuses", "Deductions", "Total", "Payment Date"};
        tableModel = new DefaultTableModel(columnNames, 0);
        salaryTable = new JTable(tableModel);

        add(new JScrollPane(salaryTable), BorderLayout.CENTER);
    }

    public void updateSalaryTable(java.util.List<String[]> data) {
        tableModel.setRowCount(0); // Clear table
        for (String[] row : data) {
            tableModel.addRow(row);
        }
    }
}
