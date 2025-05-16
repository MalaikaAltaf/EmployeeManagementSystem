package views;

import javax.swing.*;
import java.awt.*;
import controllers.AdminDashboardController;

public class AdminDashboard extends JFrame {

    private JButton homeButton;  // <-- Added homeButton here
    private JButton employeeButton, departmentsButton, salaryButton, leaveButton, settingsButton;
    private JButton chatButton;
    private JPanel mainPanel;
    private JButton logoutButton;

    public AdminDashboard() {
        setTitle("Admin Dashboard");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // -------- Header Panel --------
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(0, 150, 136)); // Teal green
        headerPanel.setPreferredSize(new Dimension(getWidth(), 70));

        JLabel welcomeLabel = new JLabel("👑 Welcome, Admin!", SwingConstants.CENTER);
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 26));
        headerPanel.add(welcomeLabel, BorderLayout.CENTER);

        logoutButton = new JButton("Log Out");
        logoutButton.setBackground(new Color(192, 57, 43));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFont(new Font("Arial", Font.BOLD, 16));

        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 15));
        logoutPanel.setOpaque(false);
        logoutPanel.add(logoutButton);
        headerPanel.add(logoutPanel, BorderLayout.EAST);
        add(headerPanel, BorderLayout.NORTH);

        // -------- Side Panel --------
        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setBackground(new Color(44, 62, 80));
        sidePanel.setPreferredSize(new Dimension(200, getHeight()));

        homeButton = createNavButton("Home", new Color(219, 112, 147)); // Dark Pink

        employeeButton = createNavButton("Employee", new Color(52, 152, 219));
        departmentsButton = createNavButton("Departments", new Color(39, 174, 96));
        salaryButton = createNavButton("Salary", new Color(241, 196, 15));
        leaveButton = createNavButton("Leave", new Color(231, 76, 60));
        settingsButton = createNavButton("Settings", new Color(155, 89, 182));
        chatButton = createNavButton("Chat", new Color(243, 156, 18));

        sidePanel.add(Box.createVerticalStrut(20));
        sidePanel.add(homeButton);            // <-- Add Home button at top
        sidePanel.add(Box.createVerticalStrut(20));
        sidePanel.add(employeeButton);
        sidePanel.add(Box.createVerticalStrut(20));
        sidePanel.add(departmentsButton);
        sidePanel.add(Box.createVerticalStrut(20));
        sidePanel.add(salaryButton);
        sidePanel.add(Box.createVerticalStrut(20));
        sidePanel.add(leaveButton);
        sidePanel.add(Box.createVerticalStrut(20));
        sidePanel.add(settingsButton);
        sidePanel.add(Box.createVerticalStrut(20));
        sidePanel.add(chatButton);

        add(sidePanel, BorderLayout.WEST);

        // -------- Main Panel --------
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        add(mainPanel, BorderLayout.CENTER);

        // -------- Controller Initialization --------
        new AdminDashboardController(this);

        setVisible(true);
    }

    private JButton createNavButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(180, 45));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 15));
        button.setFocusPainted(false);
        return button;
    }

    // ---------- Getters ----------
    public JButton getHomeButton() {   // <-- Added getter for homeButton
        return homeButton;
    }

    public JButton getEmployeeButton() {
        return employeeButton;
    }

    public JButton getDepartmentsButton() {
        return departmentsButton;
    }

    public JButton getSalaryButton() {
        return salaryButton;
    }

    public JButton getLeaveButton() {
        return leaveButton;
    }

    public JButton getSettingsButton() {
        return settingsButton;
    }

    public JButton getChatButton() {
        return chatButton;
    }

    public JButton getLogoutButton() {
        return logoutButton;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}