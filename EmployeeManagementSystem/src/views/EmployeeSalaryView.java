package views;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class EmployeeSalaryView extends JPanel {
    public JTable salaryTable;
    public DefaultTableModel tableModel;
    public JComboBox<String> statusComboBox;
    public JButton filterButton, downloadPayslipButton;

    public EmployeeSalaryView() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(15, 15, 15, 15));

        // Panel for filter options with improved layout and spacing
        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 10));
        filterPanel.setBackground(Color.WHITE);
        filterPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel statusLabel = new JLabel("Status: ");
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        statusComboBox = new JComboBox<>(new String[]{"Pending", "Paid"});
        statusComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        statusComboBox.setToolTipText("Select payment status");

        filterButton = new JButton("Filter");
        filterButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        filterButton.setBackground(new Color(70, 130, 180));
        filterButton.setForeground(Color.WHITE);
        filterButton.setFocusPainted(false);
        filterButton.setToolTipText("Filter salary records by status");

        downloadPayslipButton = new JButton("Download Payslip");
        downloadPayslipButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        downloadPayslipButton.setBackground(new Color(34, 139, 34));
        downloadPayslipButton.setForeground(Color.WHITE);
        downloadPayslipButton.setFocusPainted(false);
        downloadPayslipButton.setToolTipText("Download selected employee's payslip");

        // Adding components to the filter panel
        filterPanel.add(statusLabel);
        filterPanel.add(statusComboBox);
        filterPanel.add(filterButton);
        filterPanel.add(downloadPayslipButton);

        // Table for salary data with improved appearance
        String[] columnNames = {"Base Salary", "Bonuses", "Deductions", "Total Salary", "Payment Date", "Actions"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Make all cells non-editable
                return false;
            }
        };
        salaryTable = new JTable(tableModel);
        salaryTable.setFillsViewportHeight(true);
        salaryTable.setRowHeight(28);
        salaryTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        salaryTable.setSelectionBackground(new Color(173, 216, 230));
        salaryTable.setSelectionForeground(Color.BLACK);
        salaryTable.getTableHeader().setReorderingAllowed(false);

        // Customize table header font and background
        JTableHeader header = salaryTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 15));
        header.setBackground(new Color(220, 220, 220));
        header.setForeground(Color.DARK_GRAY);

        // Center align all columns except Actions
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < salaryTable.getColumnCount() - 1; i++) {
            salaryTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Set preferred widths for columns
        salaryTable.getColumnModel().getColumn(0).setPreferredWidth(100); // Base Salary
        salaryTable.getColumnModel().getColumn(1).setPreferredWidth(80);  // Bonuses
        salaryTable.getColumnModel().getColumn(2).setPreferredWidth(90);  // Deductions
        salaryTable.getColumnModel().getColumn(3).setPreferredWidth(100); // Total Salary
        salaryTable.getColumnModel().getColumn(4).setPreferredWidth(110); // Payment Date
        salaryTable.getColumnModel().getColumn(5).setPreferredWidth(120); // Actions

        // Adding filter panel and salary table to the main panel
        add(filterPanel, BorderLayout.NORTH);
        add(new JScrollPane(salaryTable), BorderLayout.CENTER);
    }

    // Update the salary table with new data
    public void updateSalaryTable(List<String[]> data) {
        tableModel.setRowCount(0); // Clear existing data
        for (String[] row : data) {
            tableModel.addRow(row);
        }
    }

    // Method to get the selected status from combo box
    public String getSelectedStatus() {
        return (String) statusComboBox.getSelectedItem();
    }
}
