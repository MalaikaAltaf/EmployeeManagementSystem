package views;

import javax.swing.*;
import java.awt.*;

public class EmployeeDashboardView extends JFrame {

    public JPanel sidebarPanel, contentPanel, topBarPanel;
    public JLabel welcomeLabel, nameLabel;
    public JButton dashboardBtn, profileBtn, leaveBtn, salaryBtn, settingBtn, logoutBtn;

    public EmployeeDashboardView(String employeeName) {
        // Frame setup
        setTitle("Employee Dashboard");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Sidebar
        sidebarPanel = new JPanel();
        sidebarPanel.setBackground(new Color(28, 43, 54));
        sidebarPanel.setPreferredSize(new Dimension(180, getHeight()));
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setBorder(BorderFactory.createEmptyBorder(15, 10, 10, 10));

        dashboardBtn = createSidebarButton("Dashboard", new Color(33, 150, 243));
        profileBtn = createSidebarButton("My Profile", new Color(76, 175, 80));
        salaryBtn = createSidebarButton("Salary", new Color(251, 192, 45));
        leaveBtn = createSidebarButton("Leave", new Color(230, 74, 25));
        settingBtn = createSidebarButton("Setting", new Color(171, 71, 188));

        JButton[] navButtons = {dashboardBtn, profileBtn, salaryBtn, leaveBtn, settingBtn};
        for (JButton btn : navButtons) {
            sidebarPanel.add(btn);
            sidebarPanel.add(Box.createVerticalStrut(10)); // spacing
        }

        add(sidebarPanel, BorderLayout.WEST);

        // Top bar
        topBarPanel = new JPanel(new BorderLayout());
        topBarPanel.setBackground(new Color(0, 150, 136));
        topBarPanel.setPreferredSize(new Dimension(getWidth(), 50));

        welcomeLabel = new JLabel("  Welcome, " + employeeName);
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 16));

        logoutBtn = new JButton("Log Out");
        logoutBtn.setFocusPainted(false);
        logoutBtn.setBackground(new Color(211, 47, 47)); // Red
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setFont(new Font("SansSerif", Font.BOLD, 12));
        logoutBtn.setMargin(new Insets(5, 15, 5, 15));
        logoutBtn.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        logoutBtn.setPreferredSize(new Dimension(100, 35));

        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 7));
        logoutPanel.setOpaque(false);
        logoutPanel.add(logoutBtn);

        topBarPanel.add(welcomeLabel, BorderLayout.WEST);
        topBarPanel.add(logoutPanel, BorderLayout.EAST);

        add(topBarPanel, BorderLayout.NORTH);

        // Content Panel
        contentPanel = new JPanel();
        contentPanel.setLayout(new GridBagLayout());
        contentPanel.setBackground(Color.WHITE);

        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(300, 100));
        card.setBackground(new Color(240, 240, 240));
        card.setLayout(new BorderLayout());
        card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

        JLabel welcomeMsg = new JLabel("Welcome Back", SwingConstants.CENTER);
        welcomeMsg.setFont(new Font("SansSerif", Font.PLAIN, 14));

        nameLabel = new JLabel(employeeName, SwingConstants.CENTER);
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 18));

        JPanel msgPanel = new JPanel(new GridLayout(2, 1));
        msgPanel.setBackground(new Color(240, 240, 240));
        msgPanel.add(welcomeMsg);
        msgPanel.add(nameLabel);

        card.add(msgPanel, BorderLayout.CENTER);

        contentPanel.add(card);
        add(contentPanel, BorderLayout.CENTER);
    }

    private JButton createSidebarButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(160, 35));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("SansSerif", Font.BOLD, 13));
        return button;
    }
}
