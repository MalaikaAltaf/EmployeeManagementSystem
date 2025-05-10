package views;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class EmployeeLeaveView extends JPanel {
    public JTextField startDateField, endDateField;
    public JTextArea reasonArea;
    public JButton submitBtn;
    public JTable leaveTable;
    public DefaultTableModel tableModel;

    public EmployeeLeaveView() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);

        // === FORM CARD ===
        JPanel formCard = new JPanel(new GridBagLayout());
        formCard.setBackground(Color.WHITE);
        formCard.setBorder(new CompoundBorder(
                new EmptyBorder(15, 15, 15, 15),
                new TitledBorder(new LineBorder(new Color(0, 150, 136), 2, true), "Request Leave",
                        TitledBorder.LEFT, TitledBorder.TOP, new Font("SansSerif", Font.BOLD, 16), new Color(0, 150, 136))
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;

        startDateField = new JTextField(15);
        endDateField = new JTextField(15);
        reasonArea = new JTextArea(4, 20);
        reasonArea.setLineWrap(true);
        reasonArea.setWrapStyleWord(true);
        JScrollPane reasonScroll = new JScrollPane(reasonArea);

        submitBtn = new JButton("Submit Request");
        submitBtn.setBackground(new Color(0, 150, 136));
        submitBtn.setForeground(Color.WHITE);
        submitBtn.setFocusPainted(false);
        submitBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        submitBtn.setPreferredSize(new Dimension(160, 40));

        gbc.gridx = 0; gbc.gridy = 0;
        formCard.add(new JLabel("Start Date (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1;
        formCard.add(startDateField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formCard.add(new JLabel("End Date (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1;
        formCard.add(endDateField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formCard.add(new JLabel("Reason:"), gbc);
        gbc.gridx = 1;
        formCard.add(reasonScroll, gbc);

        gbc.gridx = 1; gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        formCard.add(submitBtn, gbc);

        // === TABLE PANEL ===
        tableModel = new DefaultTableModel(new String[]{"Start", "End", "Reason", "Status", "Requested"}, 0);
        leaveTable = new JTable(tableModel);
        leaveTable.setRowHeight(28);
        leaveTable.setFont(new Font("SansSerif", Font.PLAIN, 14));
        leaveTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        leaveTable.getTableHeader().setBackground(new Color(0, 150, 136));
        leaveTable.getTableHeader().setForeground(Color.WHITE);

        JScrollPane tableScroll = new JScrollPane(leaveTable);
        tableScroll.setBorder(new TitledBorder(new LineBorder(new Color(0, 150, 136), 1),
                "Leave History", TitledBorder.LEFT, TitledBorder.TOP,
                new Font("SansSerif", Font.BOLD, 14), new Color(0, 150, 136)));

        // === ADD TO MAIN PANEL ===
        add(formCard, BorderLayout.NORTH);
        add(tableScroll, BorderLayout.CENTER);
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
