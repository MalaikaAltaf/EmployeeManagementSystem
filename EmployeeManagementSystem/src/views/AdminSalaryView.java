package views;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class AdminSalaryView extends JPanel {
    public JTextField txtUsername, txtBaseSalary, txtBonuses, txtDeductions, txtTotalSalary, txtPaymentDate;
    public JButton btnAddSalary;
    public JTable salaryTable;
    public DefaultTableModel tableModel;

    public AdminSalaryView() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Header panel (reduced size and font)
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.BLACK);
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.WHITE, 2),
            BorderFactory.createEmptyBorder(10, 20, 10, 20) // Smaller padding
        ));

        JLabel headerLabel = new JLabel("Manage Salary", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 22)); // Smaller font
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel, BorderLayout.CENTER);

        add(headerPanel, BorderLayout.NORTH);

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 16); // Smaller label font
        Font inputFont = new Font("Segoe UI", Font.PLAIN, 16);
        Dimension fieldSize = new Dimension(240, 28);  // Smaller field size

        JLabel lblUsername = new JLabel("Employee Username:");
        lblUsername.setFont(labelFont);
        txtUsername = new JTextField(25);
        txtUsername.setFont(inputFont);
        txtUsername.setPreferredSize(fieldSize);

        JLabel lblBaseSalary = new JLabel("Base Salary:");
        lblBaseSalary.setFont(labelFont);
        txtBaseSalary = new JTextField(25);
        txtBaseSalary.setFont(inputFont);
        txtBaseSalary.setPreferredSize(fieldSize);

        JLabel lblBonuses = new JLabel("Bonuses:");
        lblBonuses.setFont(labelFont);
        txtBonuses = new JTextField(25);
        txtBonuses.setFont(inputFont);
        txtBonuses.setPreferredSize(fieldSize);

        JLabel lblDeductions = new JLabel("Deductions:");
        lblDeductions.setFont(labelFont);
        txtDeductions = new JTextField(25);
        txtDeductions.setFont(inputFont);
        txtDeductions.setPreferredSize(fieldSize);

        JLabel lblTotalSalary = new JLabel("Total Salary:");
        lblTotalSalary.setFont(labelFont);
        txtTotalSalary = new JTextField(25);
        txtTotalSalary.setFont(inputFont);
        txtTotalSalary.setEditable(false);
        txtTotalSalary.setBackground(Color.LIGHT_GRAY);
        txtTotalSalary.setPreferredSize(fieldSize);

        JLabel lblPaymentDate = new JLabel("Payment Date (YYYY-MM-DD):");
        lblPaymentDate.setFont(labelFont);
        txtPaymentDate = new JTextField(25);
        txtPaymentDate.setFont(inputFont);
        txtPaymentDate.setPreferredSize(fieldSize);

        btnAddSalary = new JButton("Add Salary");
        btnAddSalary.setBackground(new Color(204, 0, 0));
        btnAddSalary.setForeground(Color.WHITE);
        btnAddSalary.setFocusPainted(false);
        btnAddSalary.setFont(new Font("Segoe UI", Font.BOLD, 16)); // Slightly smaller
        btnAddSalary.setPreferredSize(new Dimension(180, 38));  // Slightly smaller

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(lblUsername, gbc);
        gbc.gridx = 1;
        formPanel.add(txtUsername, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(lblBaseSalary, gbc);
        gbc.gridx = 1;
        formPanel.add(txtBaseSalary, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(lblBonuses, gbc);
        gbc.gridx = 1;
        formPanel.add(txtBonuses, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(lblDeductions, gbc);
        gbc.gridx = 1;
        formPanel.add(txtDeductions, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(lblTotalSalary, gbc);
        gbc.gridx = 1;
        formPanel.add(txtTotalSalary, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        formPanel.add(lblPaymentDate, gbc);
        gbc.gridx = 1;
        formPanel.add(txtPaymentDate, gbc);

        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(btnAddSalary, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Table panel
        String[] columnNames = {"Username", "Base Salary", "Bonuses", "Deductions", "Total Salary", "Payment Date"};
        tableModel = new DefaultTableModel(columnNames, 0);
        salaryTable = new JTable(tableModel);

        JTableHeader tableHeader = salaryTable.getTableHeader();
        tableHeader.setBackground(new Color(0, 100, 0));
        tableHeader.setForeground(Color.WHITE);
        tableHeader.setFont(new Font("Segoe UI", Font.BOLD, 14)); // Smaller table header font

        salaryTable.setRowHeight(26);

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (isSelected) {
                    setBackground(new Color(204, 0, 0));
                    setForeground(Color.WHITE);
                } else {
                    if (row % 2 == 0) {
                        setBackground(new Color(224, 255, 224));
                    } else {
                        setBackground(new Color(240, 255, 240));
                    }
                    setForeground(Color.BLACK);
                }
                return this;
            }
        };

        for (int i = 0; i < salaryTable.getColumnCount(); i++) {
            salaryTable.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }

        JScrollPane tableScrollPane = new JScrollPane(salaryTable);
        tableScrollPane.setPreferredSize(new Dimension(0, 200)); // Slightly smaller height
        add(tableScrollPane, BorderLayout.SOUTH);

        // Auto-calculate total salary
        DocumentListener recalcListener = new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { calculateTotalSalary(); }
            public void removeUpdate(DocumentEvent e) { calculateTotalSalary(); }
            public void changedUpdate(DocumentEvent e) { calculateTotalSalary(); }
        };

        txtBaseSalary.getDocument().addDocumentListener(recalcListener);
        txtBonuses.getDocument().addDocumentListener(recalcListener);
        txtDeductions.getDocument().addDocumentListener(recalcListener);
    }

    private void calculateTotalSalary() {
        try {
            double base = Double.parseDouble(txtBaseSalary.getText());
            double bonus = Double.parseDouble(txtBonuses.getText());
            double ded = Double.parseDouble(txtDeductions.getText());
            double total = base + bonus - ded;
            txtTotalSalary.setText(String.format("%.2f", total));
        } catch (NumberFormatException e) {
            txtTotalSalary.setText("");
        }
    }

    public void addAddSalaryListener(ActionListener listener) {
        btnAddSalary.addActionListener(listener);
    }

    public void updateSalaryTable(List<String[]> data) {
        tableModel.setRowCount(0);
        for (String[] row : data) {
            tableModel.addRow(row);
        }
    }
}