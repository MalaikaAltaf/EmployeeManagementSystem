package views;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AdminLeaveView extends JPanel {
    public JButton btnPending, btnApproved, btnRejected;
    public JTable leaveTable;
    public DefaultTableModel tableModel;
    public JPanel detailPanel;
    public JLabel lblEmpName, lblEmail, lblStartDate, lblEndDate, lblReason, lblStatus;
    public JButton btnAccept, btnReject;

    public AdminLeaveView() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Filter Buttons Panel (Top)
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        filterPanel.setBackground(new Color(232, 245, 243)); // Soft light teal

        // Define buttons with specific colors
        btnPending = new JButton("Pending");
        btnApproved = new JButton("Approved");
        btnRejected = new JButton("Rejected");

        // Set colors for each button
        btnPending.setBackground(new Color(255, 99, 71)); // Red
        btnApproved.setBackground(new Color(255, 165, 0)); // Orange
        btnRejected.setBackground(new Color(34, 139, 34)); // Green

        // Set common button properties
        for (JButton btn : new JButton[]{btnPending, btnApproved, btnRejected}) {
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setFont(new Font("SansSerif", Font.BOLD, 14));
            btn.setPreferredSize(new Dimension(120, 35));
            btn.setBorder(new RoundedBorder(10)); // Rounded button edges
            filterPanel.add(btn);
        }
        add(filterPanel, BorderLayout.NORTH);

        // Leave Table Section (Center)
        tableModel = new DefaultTableModel(new Object[]{"ID", "Employee", "Start", "End", "Reason", "Action"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5; // Only Action column editable
            }
        };
        leaveTable = new JTable(tableModel);
        leaveTable.setRowHeight(30);
        leaveTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        leaveTable.getTableHeader().setBackground(new Color(0, 153, 136));
        leaveTable.getTableHeader().setForeground(Color.WHITE);
        JScrollPane tableScroll = new JScrollPane(leaveTable);

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(0, 153, 136), 2),
                "Leave Requests",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("SansSerif", Font.BOLD, 16),
                new Color(0, 102, 85)
        ));
        tablePanel.add(tableScroll, BorderLayout.CENTER);
        add(tablePanel, BorderLayout.CENTER);

        // Detail Panel (Bottom)
        detailPanel = new JPanel();
        detailPanel.setLayout(new BoxLayout(detailPanel, BoxLayout.Y_AXIS));
        detailPanel.setBackground(Color.WHITE);
        detailPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(new Color(0, 153, 136), 2),
                        "Leave Details",
                        TitledBorder.LEFT,
                        TitledBorder.TOP,
                        new Font("SansSerif", Font.BOLD, 16),
                        new Color(0, 102, 85)
                ),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        // Only details (No labels)
        lblEmpName = new JLabel();
        lblEmail = new JLabel();
        lblStartDate = new JLabel();
        lblEndDate = new JLabel();
        lblReason = new JLabel();
        lblStatus = new JLabel();

        Font valueFont = new Font("SansSerif", Font.PLAIN, 14);
        Color titleColor = new Color(0, 102, 204);

        detailPanel.add(createDetailField(lblEmpName, valueFont, titleColor));
        detailPanel.add(createDetailField(lblEmail, valueFont, titleColor));
        detailPanel.add(createDetailField(lblStartDate, valueFont, titleColor));
        detailPanel.add(createDetailField(lblEndDate, valueFont, titleColor));
        detailPanel.add(createDetailField(lblReason, valueFont, titleColor));
        detailPanel.add(createDetailField(lblStatus, valueFont, titleColor));

        // Accept/Reject Buttons
        JPanel actionPanel = new JPanel();
        actionPanel.setBackground(Color.WHITE);
        btnAccept = new JButton("Accept");
        btnReject = new JButton("Reject");

        btnAccept.setBackground(new Color(0, 153, 0)); // Green
        btnAccept.setForeground(Color.WHITE);
        btnReject.setBackground(new Color(204, 0, 0)); // Red
        btnReject.setForeground(Color.WHITE);

        for (JButton btn : new JButton[]{btnAccept, btnReject}) {
            btn.setFocusPainted(false);
            btn.setFont(new Font("SansSerif", Font.BOLD, 13));
            btn.setPreferredSize(new Dimension(100, 30));
            actionPanel.add(btn);
        }

        detailPanel.add(Box.createVerticalStrut(10));
        detailPanel.add(actionPanel);

        detailPanel.setVisible(false); // Hide initially
        add(detailPanel, BorderLayout.SOUTH);
    }

    // Helper Method to Create Detail Panels (No labels, just the details)
    private JPanel createDetailField(JLabel valueLabel, Font font, Color titleColor) {
        JPanel panel = new JPanel(new BorderLayout(10, 0));
        panel.setBackground(Color.WHITE);
        valueLabel.setFont(font);
        valueLabel.setForeground(titleColor);
        panel.add(valueLabel, BorderLayout.CENTER);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        return panel;
    }

    // Rounded Border class to add rounded edges to buttons
    class RoundedBorder implements Border {
        private int radius;

        RoundedBorder(int radius) {
            this.radius = radius;
        }

        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius + 1, this.radius + 1, this.radius + 2, this.radius);
        }

        public boolean isBorderOpaque() {
            return true;
        }

        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.setColor(c.getForeground());
            g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }
    }
}