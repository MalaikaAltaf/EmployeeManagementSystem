package views;

import javax.swing.*;
import java.awt.*;

public class AdminHomeView extends JPanel {
    private JLabel totalEmployeesLabel;
    private JLabel totalDepartmentsLabel;
    private JLabel monthlyPayLabel;
    private JLabel leaveAppliedLabel;
    private JLabel leaveApprovedLabel;
    private JLabel leavePendingLabel;
    private JLabel leaveRejectedLabel;

    public AdminHomeView() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Title
        JLabel titleLabel = new JLabel("Dashboard Overview");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 30));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));
        add(titleLabel, BorderLayout.NORTH);

        // Top Info Cards Section
        JPanel topWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        topWrapper.setBackground(Color.WHITE);

        JPanel topPanel = new JPanel(new GridLayout(1, 3, 15, 0));
        topPanel.setBackground(Color.WHITE);
        topPanel.setPreferredSize(new Dimension(950, 130));

        topPanel.add(createInfoCard("Total Employees", "👥", new Color(0, 150, 136), 35));
        topPanel.add(createInfoCard("Total Departments", "🏢", new Color(240, 165, 0), 35));
        topPanel.add(createInfoCard("Monthly Pay", "$", new Color(211, 47, 47), 35));

        topWrapper.add(topPanel);

        // Center Panel to hold Leave Details centered
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(Color.WHITE);

        JPanel leaveSection = new JPanel();
        leaveSection.setLayout(new BoxLayout(leaveSection, BoxLayout.Y_AXIS));
        leaveSection.setBackground(Color.WHITE);
        leaveSection.setBorder(BorderFactory.createEmptyBorder(20, 15, 40, 15)); // padding around

        JLabel leaveTitle = new JLabel("Leave Details");
        leaveTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        leaveTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        leaveTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        JPanel leavePanel = new JPanel(new GridLayout(2, 2, 10, 10));
        leavePanel.setBackground(Color.WHITE);
        leavePanel.add(createLeaveCard("Leave Applied", new Color(0, 150, 136), 35));
        leavePanel.add(createLeaveCard("Leave Approved", new Color(76, 175, 80), 35));
        leavePanel.add(createLeaveCard("Leave Pending", new Color(240, 165, 0), 35));
        leavePanel.add(createLeaveCard("Leave Rejected", new Color(211, 47, 47), 35));

        leaveSection.add(leaveTitle);
        leaveSection.add(leavePanel);

        centerPanel.add(leaveSection); // Center it in the remaining space

        // Combine everything vertically
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(Color.WHITE);

        content.add(topWrapper);
        content.add(centerPanel);

        add(content, BorderLayout.CENTER);
    }

    private JPanel createInfoCard(String title, String icon, Color color, int iconSize) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)));
        panel.setPreferredSize(new Dimension(300, 100));

        JLabel iconLabel = new JLabel(icon);
        iconLabel.setOpaque(true);
        iconLabel.setBackground(color);
        iconLabel.setForeground(Color.WHITE);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, iconSize));
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        iconLabel.setPreferredSize(new Dimension(iconSize + 20, iconSize + 20));

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(Color.WHITE);
        textPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        titleLabel.setForeground(new Color(80, 80, 80));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel valueLabel = new JLabel("0");
        valueLabel.setFont(new Font("Segoe UI Semibold", Font.BOLD, 20));
        valueLabel.setForeground(Color.BLACK);
        valueLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        textPanel.add(titleLabel);
        textPanel.add(Box.createVerticalStrut(5));
        textPanel.add(valueLabel);

        panel.add(iconLabel, BorderLayout.WEST);
        panel.add(textPanel, BorderLayout.CENTER);

        switch (title) {
            case "Total Employees" -> totalEmployeesLabel = valueLabel;
            case "Total Departments" -> totalDepartmentsLabel = valueLabel;
            case "Monthly Pay" -> monthlyPayLabel = valueLabel;
        }

        return panel;
    }

    private JPanel createLeaveCard(String title, Color color, int iconSize) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        JLabel iconLabel = new JLabel();
        iconLabel.setOpaque(true);
        iconLabel.setBackground(color);
        iconLabel.setPreferredSize(new Dimension(iconSize + 15, iconSize + 15));

        switch (title) {
            case "Leave Applied" -> iconLabel.setText("📄");
            case "Leave Approved" -> iconLabel.setText("✔");
            case "Leave Pending" -> iconLabel.setText("⏳");
            case "Leave Rejected" -> iconLabel.setText("❌");
        }

        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, iconSize));
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        iconLabel.setForeground(Color.WHITE);

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(Color.WHITE);
        textPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        titleLabel.setForeground(new Color(80, 80, 80));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel valueLabel = new JLabel("0");
        valueLabel.setFont(new Font("Segoe UI Semibold", Font.BOLD, 18));
        valueLabel.setForeground(Color.BLACK);
        valueLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        textPanel.add(titleLabel);
        textPanel.add(Box.createVerticalStrut(5));
        textPanel.add(valueLabel);

        panel.add(iconLabel, BorderLayout.WEST);
        panel.add(textPanel, BorderLayout.CENTER);

        switch (title) {
            case "Leave Applied" -> leaveAppliedLabel = valueLabel;
            case "Leave Approved" -> leaveApprovedLabel = valueLabel;
            case "Leave Pending" -> leavePendingLabel = valueLabel;
            case "Leave Rejected" -> leaveRejectedLabel = valueLabel;
        }

        return panel;
    }

    // Getters
    public JLabel getTotalEmployeesLabel() { return totalEmployeesLabel; }
    public JLabel getTotalDepartmentsLabel() { return totalDepartmentsLabel; }
    public JLabel getMonthlyPayLabel() { return monthlyPayLabel; }
    public JLabel getLeaveAppliedLabel() { return leaveAppliedLabel; }
    public JLabel getLeaveApprovedLabel() { return leaveApprovedLabel; }
    public JLabel getLeavePendingLabel() { return leavePendingLabel; }
    public JLabel getLeaveRejectedLabel() { return leaveRejectedLabel; }
}