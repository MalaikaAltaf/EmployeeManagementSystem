package views;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EmployeeDashboardView extends JFrame {
    public JPanel sidebarPanel, contentPanel, topBarPanel;
    public JLabel welcomeLabel, nameLabel;
    public JButton dashboardBtn, profileBtn, leaveBtn, salaryBtn, settingBtn, logoutBtn;

    public JLabel workingTimeLabel, breakTimeLabel, dateLabel;
    public JButton startBreakBtn, endBreakBtn;

    public JPanel performanceStatsPanel;
    public JProgressBar taskProgressBar;
    public JLabel taskCompletionLabel, goalsCompletionLabel;

    public JPanel taskPanel;
    public JPanel taskListPanel;
    public JLabel taskDetailsLabel;

    public JButton chatButton;

    public CardLayout cardLayout;
    public JPanel dashboardCard;
    public JPanel chatPanel;

    public EmployeeDashboardView(String employeeName) {
        setTitle("Employee Dashboard");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center window
        setLayout(new BorderLayout());

        initSidebar();
        initTopBar(employeeName);
        initContentPanel(employeeName);
    }

    private void initSidebar() {
        sidebarPanel = new JPanel();
        sidebarPanel.setBackground(new Color(33, 40, 48));
        sidebarPanel.setPreferredSize(new Dimension(180, getHeight()));
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));

        dashboardBtn = createSidebarButton("Dashboard", new Color(33, 150, 243));
        profileBtn = createSidebarButton("My Profile", new Color(76, 175, 80));
        salaryBtn = createSidebarButton("Salary", new Color(251, 192, 45));
        leaveBtn = createSidebarButton("Leave", new Color(230, 74, 25));
        settingBtn = createSidebarButton("Setting", new Color(171, 71, 188));
        chatButton = createSidebarButton("Chat", new Color(3, 169, 244)); // Chat Button added

        JButton[] navButtons = {dashboardBtn, profileBtn, salaryBtn, leaveBtn, settingBtn, chatButton};
        for (JButton btn : navButtons) {
            sidebarPanel.add(btn);
            sidebarPanel.add(Box.createVerticalStrut(15));
        }

        add(sidebarPanel, BorderLayout.WEST);
    }

    private void initTopBar(String employeeName) {
        topBarPanel = new JPanel(new BorderLayout());
        topBarPanel.setBackground(new Color(0, 150, 136));
        topBarPanel.setPreferredSize(new Dimension(getWidth(), 50));

        welcomeLabel = new JLabel("  Welcome, " + employeeName);
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));

        logoutBtn = new JButton("Log Out");
        logoutBtn.setFocusPainted(false);
        logoutBtn.setBackground(new Color(211, 47, 47));
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        logoutBtn.setPreferredSize(new Dimension(110, 35));

        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        logoutPanel.setOpaque(false);
        logoutPanel.add(logoutBtn);

        topBarPanel.add(welcomeLabel, BorderLayout.WEST);
        topBarPanel.add(logoutPanel, BorderLayout.EAST);

        add(topBarPanel, BorderLayout.NORTH);
    }

    private void initContentPanel(String employeeName) {
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(new Color(250, 250, 250));

        dashboardCard = new JPanel(new GridBagLayout());
        dashboardCard.setBackground(new Color(245, 245, 245));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(30, 30, 30, 30);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        JPanel timerPanel = createTimerPanel(employeeName);
        dashboardCard.add(timerPanel, gbc);

        gbc.gridx++;
        JPanel statsPanel = createPerformanceStatsPanel();
        dashboardCard.add(statsPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        JPanel taskPanel = createTaskPanel();
        dashboardCard.add(taskPanel, gbc);

        // Add dashboard card
        contentPanel.add(dashboardCard, "Dashboard");

        // Placeholder chat panel
        chatPanel = new JPanel();
        chatPanel.setBackground(new Color(250, 250, 250));
        chatPanel.setLayout(new BorderLayout());
        chatPanel.add(new JLabel("Chat with Admin (coming soon)", SwingConstants.CENTER), BorderLayout.CENTER);

        // Add chat card
        contentPanel.add(chatPanel, "Chat");

        add(contentPanel, BorderLayout.CENTER);
    }

    private JPanel createTimerPanel(String employeeName) {
        JPanel panel = new JPanel(new GridLayout(7, 1, 8, 8));
        panel.setPreferredSize(new Dimension(350, 270));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Work Session Info"),
                BorderFactory.createLineBorder(new Color(100, 149, 237), 2)
        ));

        JLabel nameDisplay = new JLabel(employeeName, SwingConstants.CENTER);
        nameDisplay.setFont(new Font("Segoe UI", Font.BOLD, 20));
        nameDisplay.setForeground(new Color(33, 40, 48));

        dateLabel = new JLabel("Date: " + getCurrentDate(), SwingConstants.CENTER);
        dateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        dateLabel.setForeground(new Color(80, 80, 80));

        workingTimeLabel = new JLabel("Working Time: 00:00:00", SwingConstants.CENTER);
        workingTimeLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        workingTimeLabel.setForeground(new Color(30, 144, 255)); // Dodger Blue

        breakTimeLabel = new JLabel("Break Time: 00:00:00", SwingConstants.CENTER);
        breakTimeLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        breakTimeLabel.setForeground(new Color(255, 69, 0)); // Orange Red

        startBreakBtn = createActionButton("Start Break");
        endBreakBtn = createActionButton("End Break");

        panel.add(nameDisplay);
        panel.add(dateLabel);
        panel.add(workingTimeLabel);
        panel.add(breakTimeLabel);
        panel.add(startBreakBtn);
        panel.add(endBreakBtn);

        return panel;
    }

    private JPanel createPerformanceStatsPanel() {
        performanceStatsPanel = new JPanel();
        performanceStatsPanel.setLayout(new BoxLayout(performanceStatsPanel, BoxLayout.Y_AXIS));
        performanceStatsPanel.setPreferredSize(new Dimension(350, 270));
        performanceStatsPanel.setBackground(Color.WHITE);
        performanceStatsPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Performance Stats"),
                BorderFactory.createLineBorder(new Color(100, 149, 237), 2)
        ));

        taskCompletionLabel = new JLabel("Task Completion:");
        taskCompletionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        taskCompletionLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        taskCompletionLabel.setForeground(new Color(33, 40, 48));

        taskProgressBar = new JProgressBar(0, 100);
        taskProgressBar.setValue(60);
        taskProgressBar.setStringPainted(true);
        taskProgressBar.setAlignmentX(Component.CENTER_ALIGNMENT);

        goalsCompletionLabel = new JLabel("Goals Completion: 75%");
        goalsCompletionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        goalsCompletionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        goalsCompletionLabel.setForeground(new Color(33, 40, 48));

        JButton addTaskButton = new JButton("Add Task");
        addTaskButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        addTaskButton.setBackground(new Color(70, 130, 180));
        addTaskButton.setForeground(Color.WHITE);
        addTaskButton.setFocusPainted(false);
        addTaskButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        addTaskButton.setMaximumSize(new Dimension(140, 40));
        addTaskButton.setName("addTaskButton");

        performanceStatsPanel.add(taskCompletionLabel);
        performanceStatsPanel.add(taskProgressBar);
        performanceStatsPanel.add(goalsCompletionLabel);
        performanceStatsPanel.add(Box.createVerticalStrut(15));
        performanceStatsPanel.add(addTaskButton);

        return performanceStatsPanel;
    }

    private JPanel createTaskPanel() {
        taskPanel = new JPanel();
        taskPanel.setLayout(new BorderLayout());
        taskPanel.setPreferredSize(new Dimension(740, 250));
        taskPanel.setBackground(Color.WHITE);
        taskPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Tasks"),
                BorderFactory.createLineBorder(new Color(100, 149, 237), 2)
        ));

        taskListPanel = new JPanel();
        taskListPanel.setLayout(new BoxLayout(taskListPanel, BoxLayout.Y_AXIS));
        taskListPanel.setBackground(new Color(245, 245, 245));

        taskListPanel.add(new TaskPanel("UI Redesign", "Refactor the TaskPanel layout to be more compact and stylish.", "In Progress", 7));
        taskListPanel.add(Box.createVerticalStrut(10));
        taskListPanel.add(new TaskPanel("Bug Fix", "Resolve the layout overflow issue in task section.", "Pending", 5));
        taskListPanel.add(Box.createVerticalStrut(10));
        taskListPanel.add(new TaskPanel("Feature Add", "Add real-time notifications for task changes.", "Completed", 10));

        JScrollPane scrollPane = new JScrollPane(taskListPanel);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(12);

        taskPanel.add(scrollPane, BorderLayout.CENTER);
        return taskPanel;
    }

    private JButton createSidebarButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(160, 45));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 15));
        button.setBorder(BorderFactory.createEmptyBorder(10, 14, 10, 14));
        return button;
    }

    private JButton createActionButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 15));
        button.setPreferredSize(new Dimension(130, 40));
        return button;
    }

    private String getCurrentDate() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMMM yyyy"));
    }
}
