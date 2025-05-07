package views;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EmployeeDashboardView extends JFrame {
    public JPanel sidebarPanel, contentPanel, topBarPanel;
    public JLabel welcomeLabel, nameLabel;
    public JButton dashboardBtn, profileBtn, leaveBtn, salaryBtn, settingBtn, logoutBtn;

    // Timer UI elements
    public JLabel workingTimeLabel, breakTimeLabel, dateLabel;
    public JButton startBreakBtn, endBreakBtn;

    public CardLayout cardLayout;
    public JPanel dashboardCard;

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
            sidebarPanel.add(Box.createVerticalStrut(10));
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
        logoutBtn.setBackground(new Color(211, 47, 47));
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setFont(new Font("SansSerif", Font.BOLD, 12));
        logoutBtn.setPreferredSize(new Dimension(100, 35));

        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 7));
        logoutPanel.setOpaque(false);
        logoutPanel.add(logoutBtn);

        topBarPanel.add(welcomeLabel, BorderLayout.WEST);
        topBarPanel.add(logoutPanel, BorderLayout.EAST);
        add(topBarPanel, BorderLayout.NORTH);

        // Content Panel with CardLayout
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(Color.WHITE);

        // Dashboard Card
        dashboardCard = new JPanel(new GridBagLayout());
        dashboardCard.setBackground(Color.WHITE);

        JPanel timerPanel = new JPanel();
        timerPanel.setPreferredSize(new Dimension(350, 240));
        timerPanel.setBackground(new Color(240, 240, 240));
        timerPanel.setLayout(new GridLayout(7, 1, 5, 5));
        timerPanel.setBorder(BorderFactory.createTitledBorder("Work Session Info"));

        JLabel nameDisplay = new JLabel(employeeName, SwingConstants.CENTER);
        nameDisplay.setFont(new Font("SansSerif", Font.BOLD, 18));
        nameDisplay.setForeground(new Color(28, 43, 54));

        dateLabel = new JLabel("Date: " + getCurrentDate(), SwingConstants.CENTER);
        dateLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));

        workingTimeLabel = new JLabel("Working Time: 00:00:00", SwingConstants.CENTER);
        workingTimeLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));

        breakTimeLabel = new JLabel("Break Time: 00:00:00", SwingConstants.CENTER);
        breakTimeLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));

        startBreakBtn = new JButton("Start Break");
        endBreakBtn = new JButton("End Break");

        styleTimerButton(startBreakBtn);
        styleTimerButton(endBreakBtn);

        timerPanel.add(nameDisplay);
        timerPanel.add(dateLabel);
        timerPanel.add(workingTimeLabel);
        timerPanel.add(breakTimeLabel);
        timerPanel.add(startBreakBtn);
        timerPanel.add(endBreakBtn);

        dashboardCard.add(timerPanel);

        contentPanel.add(dashboardCard, "Dashboard");

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

    private void styleTimerButton(JButton button) {
        button.setFocusPainted(false);
        button.setBackground(new Color(100, 181, 246));
        button.setForeground(Color.BLACK);
        button.setFont(new Font("SansSerif", Font.BOLD, 13));
        button.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
    }

    private String getCurrentDate() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMMM yyyy"));
    }
}