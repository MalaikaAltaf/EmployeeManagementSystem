package views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionListener;

public class AdminDepartmentView extends JPanel {
    public JButton btnAddDepartment;
    public JTable tableDepartments;
    public DefaultTableModel tableModel;

    public AdminDepartmentView() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // ---------- Header ----------
        JLabel lblHeader = new JLabel("Manage Departments", SwingConstants.CENTER);
        lblHeader.setFont(new Font("Arial", Font.BOLD, 28)); // Larger and bold header font
        lblHeader.setForeground(new Color(0, 102, 102)); // Change header text color if needed
        lblHeader.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        // Center header and ensure it adjusts correctly within the panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.add(lblHeader, BorderLayout.CENTER);
        
        // Add the header panel to the NORTH region of the layout
        add(headerPanel, BorderLayout.NORTH);

        // ---------- Top Button Panel ----------
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        topPanel.setBackground(Color.WHITE);

        btnAddDepartment = new JButton("Add New Department");
        btnAddDepartment.setBackground(new Color(0, 153, 136));
        btnAddDepartment.setForeground(Color.WHITE);
        btnAddDepartment.setFont(new Font("Arial", Font.BOLD, 16));
        btnAddDepartment.setFocusPainted(false);
        btnAddDepartment.setPreferredSize(new Dimension(200, 35));

        topPanel.add(btnAddDepartment, BorderLayout.EAST);
        add(topPanel, BorderLayout.BEFORE_FIRST_LINE);

        // ---------- Table ----------
        tableModel = new DefaultTableModel(new Object[]{"S No", "Department", "Action"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2; // Only "Action" column is editable for buttons
            }
        };
        tableDepartments = new JTable(tableModel);
        tableDepartments.setRowHeight(35);

        // Make table headers bold and large
        JTableHeader header = tableDepartments.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 18)); // Larger and bold header font
        header.setForeground(new Color(0, 102, 102)); // Optional: Change header text color

        // Set custom renderer for the Action column
        tableDepartments.getColumnModel().getColumn(2).setCellRenderer(new ButtonRenderer());

        JScrollPane scrollPane = new JScrollPane(tableDepartments);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        add(scrollPane, BorderLayout.CENTER);
    }

    public void addAddDepartmentListener(ActionListener listener) {
        btnAddDepartment.addActionListener(listener);
    }

    // Optional: you can add a method to populate the table
    public void setTableData(Object[][] data) {
        tableModel.setRowCount(0); // Clear previous data
        for (int i = 0; i < data.length; i++) {
            tableModel.addRow(data[i]);
        }
    }

    // Custom Button Renderer to style the "Delete" button
    public class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
            setFont(new Font("Arial", Font.BOLD, 14));
            setBackground(Color.WHITE); // Set default button background to white
            setForeground(Color.BLACK); // Set button text color to black
            setText("Delete");
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            setBackground(Color.WHITE); // Apply white background for all buttons
            return this;
        }
    }
}