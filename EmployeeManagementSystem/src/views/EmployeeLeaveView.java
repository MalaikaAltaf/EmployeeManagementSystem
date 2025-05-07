package views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class EmployeeLeaveView extends JPanel {
    public JTextField startDateField, endDateField;
    public JTextArea reasonArea;
    public JButton submitBtn;
    public JTable leaveTable;
    public DefaultTableModel tableModel;

    public EmployeeLeaveView() {
        setLayout(new BorderLayout());
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Request Leave"));

        startDateField = new JTextField();
        endDateField = new JTextField();
        reasonArea = new JTextArea(3, 20);
        submitBtn = new JButton("Submit Request");

        formPanel.add(new JLabel("Start Date (YYYY-MM-DD):"));
        formPanel.add(startDateField);
        formPanel.add(new JLabel("End Date (YYYY-MM-DD):"));
        formPanel.add(endDateField);
        formPanel.add(new JLabel("Reason:"));
        formPanel.add(new JScrollPane(reasonArea));
        formPanel.add(new JLabel());
        formPanel.add(submitBtn);

        add(formPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"Start", "End", "Reason", "Status", "Requested"}, 0);
        leaveTable = new JTable(tableModel);
        add(new JScrollPane(leaveTable), BorderLayout.CENTER);
    }

    public void clearForm() {
        startDateField.setText("");
        endDateField.setText("");
        reasonArea.setText("");
    }

    public void updateLeaveTable(java.util.List<String[]> leaves) {
        tableModel.setRowCount(0);
        for (String[] row : leaves) {
            tableModel.addRow(row);
        }
    }
}
