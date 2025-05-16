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
    public JComboBox<String> monthComboBox;
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

        JLabel monthLabel = new JLabel("Month: ");
        monthLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        monthComboBox = new JComboBox<>(new String[]{
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        });
        monthComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        monthComboBox.setToolTipText("Select month to filter salary records");

        filterButton = new JButton("Filter");
        filterButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        filterButton.setBackground(new Color(70, 130, 180));
        filterButton.setForeground(Color.WHITE);
        filterButton.setFocusPainted(false);
        filterButton.setToolTipText("Filter salary records by month");

        downloadPayslipButton = new JButton("Download Payslip");
        downloadPayslipButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        downloadPayslipButton.setBackground(new Color(34, 139, 34));
        downloadPayslipButton.setForeground(Color.WHITE);
        downloadPayslipButton.setFocusPainted(false);
        downloadPayslipButton.setToolTipText("Download selected employee's payslip");

        // Adding components to the filter panel
        filterPanel.add(monthLabel);
        filterPanel.add(monthComboBox);
        filterPanel.add(filterButton);
        filterPanel.add(downloadPayslipButton);

        // Table for salary data with improved appearance
        String[] columnNames = {"Base Salary", "Bonuses", "Deductions", "Total Salary", "Payment Date"};
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

        // Center align all columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < salaryTable.getColumnCount(); i++) {
            salaryTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Set preferred widths for columns
        salaryTable.getColumnModel().getColumn(0).setPreferredWidth(100); // Base Salary
        salaryTable.getColumnModel().getColumn(1).setPreferredWidth(80);  // Bonuses
        salaryTable.getColumnModel().getColumn(2).setPreferredWidth(90);  // Deductions
        salaryTable.getColumnModel().getColumn(3).setPreferredWidth(100); // Total Salary
        salaryTable.getColumnModel().getColumn(4).setPreferredWidth(110); // Payment Date

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

    // Method to get the selected month from combo box
    public String getSelectedMonth() {
        return (String) monthComboBox.getSelectedItem();
    }
}
