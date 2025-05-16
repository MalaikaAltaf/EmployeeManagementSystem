package controllers;


import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Font;
import java.awt.Color;

import views.EmployeeDashboardView;
import views.EmployeeLeaveView;
import controllers.EmployeeLeaveController;
import views.EmployeeProfileView;
import controllers.EmployeeProfileController;
import models.EmployeeModel;
import views.EmployeeSalaryView;
import controllers.EmployeeSalaryController;
import models.SalaryModel;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import views.SettingsView; // ✅ NEW
import controllers.SettingsController; // ✅ NEW

import models.EmployeeTaskModel;
import models.NotificationModel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class EmployeeDashboardController {
    private EmployeeDashboardView view;
    private Timer timer;
    private int workingSeconds = 0;
    private int breakSeconds = 0;
    private boolean onBreak = false;
    private String employeeName;
    private int empId;

    private String currentView = "Dashboard"; // Track current visible view

    private EmployeeTaskModel taskModel;
    private NotificationModel notificationModel;

    // ✅ Leave View and Controller
    private EmployeeLeaveView leaveView;
    private EmployeeLeaveController leaveController;

    // ✅ Profile View and Controller
    private EmployeeProfileView profileView;
    private EmployeeProfileController profileController;

    // ✅ Salary View and Controller
    private EmployeeSalaryView salaryView;
    private EmployeeSalaryController salaryController;

    // ✅ Settings View and Controller
    private SettingsView settingsView;
    private SettingsController settingsController;

    // At the top with other fields
    private views.ChatView chatView;
    private controllers.ChatController chatController;


    public EmployeeDashboardController(String employeeName, int empId) {
        this(employeeName, empId, new EmployeeDashboardView(employeeName));
    }

    public EmployeeDashboardController(String employeeName, int empId, EmployeeDashboardView view) {
        this.employeeName = employeeName;
        this.empId = empId;
        this.view = view;

        // Initialize task model with DB connection
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/EMS1", "root", "root1122");
            taskModel = new EmployeeTaskModel(conn);
            notificationModel = new NotificationModel(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // ✅ Initialize Leave View/Controller
        leaveView = new EmployeeLeaveView();
        leaveController = new EmployeeLeaveController(leaveView, new models.LeaveModel(), empId);
        view.contentPanel.add(leaveView, "Leave");

        // ✅ Initialize Profile View/Controller
        profileView = new EmployeeProfileView();
        profileController = new EmployeeProfileController(profileView, new EmployeeModel(), empId);
        view.contentPanel.add(profileView, "Profile");

        // ✅ Initialize Salary View/Controller
        salaryView = new EmployeeSalaryView();
        salaryController = new EmployeeSalaryController(salaryView, new SalaryModel(), empId);
        view.contentPanel.add(salaryView, "Salary");

        // ✅ Initialize Settings View/Controller
        settingsView = new SettingsView();
        settingsController = new SettingsController(settingsView, new EmployeeModel(), empId);
        view.contentPanel.add(settingsView, "Settings");

        // Initialize and add chat
        chatView = new views.ChatView();
        chatController = new controllers.ChatController(chatView, employeeName);
        view.contentPanel.add(chatView, "Chat");


        initController();
        view.setVisible(true);
        startTimers();
        updatePerformanceStats();
        refreshTaskList();
        refreshNotifications();
    }

    private void initController() {
        view.logoutBtn.addActionListener(_ -> handleLogout());

        view.dashboardBtn.addActionListener(e -> {
            view.cardLayout.show(view.contentPanel, "Dashboard");
            currentView = "Dashboard";
        });

        // ✅ Chat Button Handler
        view.chatButton.addActionListener(_ -> {
            view.cardLayout.show(view.contentPanel, "Chat");
            currentView = "Chat";
        });

        // ✅ Show Profile Panel when "My Profile" is clicked
        view.profileBtn.addActionListener(e -> {
            profileController.loadEmployeeProfile(); // optional: refresh data
            view.cardLayout.show(view.contentPanel, "Profile");
            currentView = "Profile";
        });

        view.leaveBtn.addActionListener(e -> {
            leaveController.refreshLeaveTable();
            view.cardLayout.show(view.contentPanel, "Leave");
            currentView = "Leave";
        });

        // ✅ Show Salary Panel when "Salary" is clicked
        view.salaryBtn.addActionListener(e -> {
            salaryController.loadSalaryData(); // optional: refresh salary data
            view.cardLayout.show(view.contentPanel, "Salary");
            currentView = "Salary";
        });

        // ✅ Show Settings Panel when "Setting" is clicked
        view.settingBtn.addActionListener(e -> {
            view.cardLayout.show(view.contentPanel, "Settings");
            currentView = "Settings";
        });

        view.startBreakBtn.addActionListener(_ -> onBreak = true);
        view.endBreakBtn.addActionListener(_ -> onBreak = false);

        // Add Task Button Handler
        view.performanceStatsPanel.getComponents();
        for (Component comp : view.performanceStatsPanel.getComponents()) {
            if (comp instanceof JButton && "addTaskButton".equals(comp.getName())) {
                ((JButton) comp).addActionListener(e -> showAddTaskDialog());
            }
        }

        // Notification Button Handler
        view.notificationBtn.addActionListener(e -> toggleNotificationPanel());
    }

    private void startTimers() {
        timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!"Dashboard".equals(currentView)) {
                    // Do not count time if not on Dashboard view
                    return;
                }
                if (onBreak) {
                    breakSeconds++;
                    view.breakTimeLabel.setText("Break Time: " + formatTime(breakSeconds));
                } else {
                    workingSeconds++;
                    view.workingTimeLabel.setText("Working Time: " + formatTime(workingSeconds));
                }

                view.dateLabel.setText("Date: " + getCurrentDate());
            }
        });
        timer.start();

        // Start notification refresh timer every 5 minutes
        new Timer(5 * 60 * 1000, e -> refreshNotifications()).start();
    }

    private String formatTime(int totalSeconds) {
        int hrs = totalSeconds / 3600;
        int mins = (totalSeconds % 3600) / 60;
        int secs = totalSeconds % 60;
        return String.format("%02d:%02d:%02d", hrs, mins, secs);
    }

    private void refreshNotifications() {
        if (notificationModel == null) return;
        List<NotificationModel.Notification> notifications = notificationModel.getAllNotifications(empId);
        view.updateNotificationList(notifications);
    }

    private void toggleNotificationPanel() {
        boolean isVisible = view.notificationPanel.isVisible();
        if (!isVisible) {
            // Show notification panel and hide task panel to avoid UI disturbance
            view.notificationPanel.setVisible(true);
            view.taskPanel.setVisible(false);
            refreshNotifications();
        } else {
            // Hide notification panel and show task panel
            view.notificationPanel.setVisible(false);
            view.taskPanel.setVisible(true);
        }
    }

    private String getCurrentDate() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMMM yyyy"));
    }

private void handleLogout() {
    int option = JOptionPane.showConfirmDialog(
        view,
        "Are you sure you want to log out?",
        "Confirm Logout",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.QUESTION_MESSAGE
    );

    if (option == JOptionPane.YES_OPTION) {
        timer.stop();
        view.dispose();
        System.out.println("Logged out!");
        // Show login page
javax.swing.SwingUtilities.invokeLater(() -> {
    views.LoginView loginView = new views.LoginView();
    new controllers.LoginController(loginView);
    loginView.setVisible(true);
});
    }
}
private void showAddTaskDialog() {
    JDialog dialog = new JDialog(view, "Add New Task", true);
    dialog.setSize(450, 480);
    dialog.setLocationRelativeTo(view);

    JPanel mainPanel = new JPanel(new GridBagLayout());
    mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
    mainPanel.setBackground(new Color(0xE3F2FD)); // Light blue background for the dialog
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(8, 8, 8, 8);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    JLabel headerLabel = new JLabel("Add New Task");
    headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
    headerLabel.setForeground(new Color(0x29B6F6));
    headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 2;
    gbc.anchor = GridBagConstraints.CENTER;
    mainPanel.add(headerLabel, gbc);

    gbc.anchor = GridBagConstraints.WEST;
    gbc.gridwidth = 1;

    gbc.gridy++;
    gbc.gridx = 0;
    JLabel titleLabel = new JLabel("Task Title:");
    titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
    mainPanel.add(titleLabel, gbc);

    gbc.gridx = 1;
    JTextField titleField = new JTextField(20);
    titleField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    titleField.setToolTipText("Enter the task title");
    mainPanel.add(titleField, gbc);

    gbc.gridy++;
    gbc.gridx = 0;
    JLabel descriptionLabel = new JLabel("Task Description:");
    descriptionLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
    mainPanel.add(descriptionLabel, gbc);

    gbc.gridx = 1;
    JTextArea descriptionArea = new JTextArea(6, 30);
    descriptionArea.setLineWrap(true);
    descriptionArea.setWrapStyleWord(true);
    descriptionArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    descriptionArea.setToolTipText("Enter the task description");
    JScrollPane descriptionScroll = new JScrollPane(descriptionArea);
    descriptionScroll.setPreferredSize(new Dimension(400, 120));
    mainPanel.add(descriptionScroll, gbc);

    gbc.gridy++;
    gbc.gridx = 0;
    JLabel statusLabel = new JLabel("Status:");
    statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
    mainPanel.add(statusLabel, gbc);

    gbc.gridx = 1;
    JComboBox<String> statusComboBox = new JComboBox<>(new String[]{"Pending", "In Progress", "Completed"});
    statusComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    statusComboBox.setToolTipText("Select the task status");
    mainPanel.add(statusComboBox, gbc);

    gbc.gridy++;
    gbc.gridx = 0;
    JLabel startDateLabel = new JLabel("Start Date (YYYY-MM-DD):");
    startDateLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
    mainPanel.add(startDateLabel, gbc);

    gbc.gridx = 1;
    JTextField startDateField = new JTextField(LocalDate.now().toString());
    startDateField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    startDateField.setToolTipText("Enter the start date");
    mainPanel.add(startDateField, gbc);

    gbc.gridy++;
    gbc.gridx = 0;
    JLabel endDateLabel = new JLabel("End Date (YYYY-MM-DD):");
    endDateLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
    mainPanel.add(endDateLabel, gbc);

    gbc.gridx = 1;
    JTextField endDateField = new JTextField(LocalDate.now().plusDays(7).toString());
    endDateField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    endDateField.setToolTipText("Enter the end date");
    mainPanel.add(endDateField, gbc);

    gbc.gridy++;
    gbc.gridx = 0;
    JLabel performanceRatingLabel = new JLabel("Performance Rating (1-10):");
    performanceRatingLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
    mainPanel.add(performanceRatingLabel, gbc);

    gbc.gridx = 1;
    JTextField performanceRatingField = new JTextField("5");
    performanceRatingField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    performanceRatingField.setToolTipText("Enter the performance rating");
    mainPanel.add(performanceRatingField, gbc);

    gbc.gridy++;
    gbc.gridx = 1;
    gbc.anchor = GridBagConstraints.EAST;
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    buttonPanel.setOpaque(false);
    JButton submitButton = new JButton("Submit");
    submitButton.setBackground(new Color(70, 130, 180));
    submitButton.setForeground(Color.WHITE);
    submitButton.setFocusPainted(false);
    JButton cancelButton = new JButton("Cancel");
    cancelButton.setBackground(new Color(220, 53, 69));
    cancelButton.setForeground(Color.WHITE);
    cancelButton.setFocusPainted(false);
    buttonPanel.add(submitButton);
    buttonPanel.add(cancelButton);
    mainPanel.add(buttonPanel, gbc);

    dialog.add(mainPanel);
    dialog.getRootPane().setDefaultButton(submitButton);

    submitButton.addActionListener(e -> {
        try {
            String title = titleField.getText().trim();
            String description = descriptionArea.getText().trim();
            String status = (String) statusComboBox.getSelectedItem();
            Date startDate = Date.valueOf(startDateField.getText().trim());
            Date endDate = Date.valueOf(endDateField.getText().trim());
            int performanceRating = Integer.parseInt(performanceRatingField.getText().trim());

            boolean success = taskModel.addTask(empId, title, description, status, startDate, endDate, performanceRating);
            if (success) {
                JOptionPane.showMessageDialog(view, "Task added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                updatePerformanceStats();
                refreshTaskList();
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(view, "Failed to add task.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Invalid input: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    });

    cancelButton.addActionListener(e -> dialog.dispose());

    dialog.setVisible(true);
}
private void updatePerformanceStats() {
    List<EmployeeTaskModel.Task> tasks = taskModel.getTasksByEmployee(empId);
    if (tasks.isEmpty()) {
        view.taskProgressBar.setValue(0);
        view.goalsCompletionLabel.setText("Goals Completion: 0%");
        return;
    }
    int totalRating = 0;
    int completedTasks = 0;
    for (EmployeeTaskModel.Task task : tasks) {
        totalRating += task.performanceRating;
        if ("Completed".equalsIgnoreCase(task.status)) {
            completedTasks++;
        }
    }
    int avgRating = totalRating / tasks.size();
    int completionPercent = (int) ((completedTasks / (double) tasks.size()) * 100);

    view.taskProgressBar.setValue(completionPercent);
    view.goalsCompletionLabel.setText("Goals Completion: " + completionPercent + "%");
}

private void refreshTaskList() {
    List<EmployeeTaskModel.Task> tasks = taskModel.getTasksByEmployee(empId);
    view.taskListPanel.removeAll();
    for (EmployeeTaskModel.Task task : tasks) {
        views.TaskPanel taskPanel = new views.TaskPanel(task.title, task.description, task.status, task.performanceRating);
        // Set edit listener for each task panel
        taskPanel.setEditListener(() -> showEditTaskDialog(task));
        // Set delete listener for each task panel
        taskPanel.setDeleteListener(() -> {
            int confirm = JOptionPane.showConfirmDialog(view, "Are you sure you want to delete this task?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                boolean success = taskModel.deleteTask(task.taskId);
                if (success) {
                    JOptionPane.showMessageDialog(view, "Task deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    updatePerformanceStats();
                    refreshTaskList();
                } else {
                    JOptionPane.showMessageDialog(view, "Failed to delete task.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        view.taskListPanel.add(taskPanel);
        view.taskListPanel.add(Box.createVerticalStrut(10));
    }
    view.taskListPanel.revalidate();
    view.taskListPanel.repaint();
}

private void showEditTaskDialog(EmployeeTaskModel.Task task) {
    JDialog dialog = new JDialog(view, "Edit Task", true);
    dialog.setSize(500, 450);
    dialog.setLocationRelativeTo(view);

    JPanel mainPanel = new JPanel(new GridBagLayout());
    mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(8, 8, 8, 8);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    JLabel headerLabel = new JLabel("Edit Task Details");
    headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
    headerLabel.setForeground(Color.BLACK);
    headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 2;
    gbc.anchor = GridBagConstraints.CENTER;
    mainPanel.add(headerLabel, gbc);

    mainPanel.setBackground(new Color(0xE3F2FD)); // Light blue background for the dialog

    gbc.anchor = GridBagConstraints.WEST;
    gbc.gridwidth = 1;

    gbc.gridy++;
    gbc.gridx = 0;
    JLabel titleLabel = new JLabel("Task Title:");
    titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
    mainPanel.add(titleLabel, gbc);

    gbc.gridx = 1;
    JTextField titleField = new JTextField(task.title, 20);
    titleField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    titleField.setToolTipText("Enter the task title");
    mainPanel.add(titleField, gbc);

    gbc.gridy++;
    gbc.gridx = 0;
    JLabel descriptionLabel = new JLabel("Task Description:");
    descriptionLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
    mainPanel.add(descriptionLabel, gbc);

    gbc.gridx = 1;
    JTextArea descriptionArea = new JTextArea(task.description, 6, 30);
    descriptionArea.setLineWrap(true);
    descriptionArea.setWrapStyleWord(true);
    descriptionArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    descriptionArea.setToolTipText("Enter the task description");
    JScrollPane descriptionScroll = new JScrollPane(descriptionArea);
    descriptionScroll.setPreferredSize(new Dimension(400, 120));
    mainPanel.add(descriptionScroll, gbc);

    gbc.gridy++;
    gbc.gridx = 0;
    JLabel statusLabel = new JLabel("Status:");
    statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
    mainPanel.add(statusLabel, gbc);

    gbc.gridx = 1;
    JComboBox<String> statusComboBox = new JComboBox<>(new String[]{"Pending", "In Progress", "Completed"});
    statusComboBox.setSelectedItem(task.status);
    statusComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    statusComboBox.setToolTipText("Select the task status");
    mainPanel.add(statusComboBox, gbc);

    gbc.gridy++;
    gbc.gridx = 0;
    JLabel startDateLabel = new JLabel("Start Date (YYYY-MM-DD):");
    startDateLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
    mainPanel.add(startDateLabel, gbc);

    gbc.gridx = 1;
    JTextField startDateField = new JTextField(task.startDate.toString());
    startDateField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    startDateField.setToolTipText("Enter the start date");
    mainPanel.add(startDateField, gbc);

    gbc.gridy++;
    gbc.gridx = 0;
    JLabel endDateLabel = new JLabel("End Date (YYYY-MM-DD):");
    endDateLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
    mainPanel.add(endDateLabel, gbc);

    gbc.gridx = 1;
    JTextField endDateField = new JTextField(task.endDate.toString());
    endDateField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    endDateField.setToolTipText("Enter the end date");
    mainPanel.add(endDateField, gbc);

    gbc.gridy++;
    gbc.gridx = 0;
    JLabel performanceRatingLabel = new JLabel("Performance Rating (1-10):");
    performanceRatingLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
    mainPanel.add(performanceRatingLabel, gbc);

    gbc.gridx = 1;
    JTextField performanceRatingField = new JTextField(String.valueOf(task.performanceRating));
    performanceRatingField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    performanceRatingField.setToolTipText("Enter the performance rating");
    mainPanel.add(performanceRatingField, gbc);

    gbc.gridy++;
    gbc.gridx = 1;
    gbc.anchor = GridBagConstraints.EAST;
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    buttonPanel.setOpaque(false);
    JButton submitButton = new JButton("Update");
    submitButton.setBackground(new Color(70, 130, 180));
    submitButton.setForeground(Color.WHITE);
    submitButton.setFocusPainted(false);
    JButton cancelButton = new JButton("Cancel");
    cancelButton.setBackground(new Color(220, 53, 69));
    cancelButton.setForeground(Color.WHITE);
    cancelButton.setFocusPainted(false);
    buttonPanel.add(submitButton);
    buttonPanel.add(cancelButton);
    mainPanel.add(buttonPanel, gbc);

    dialog.add(mainPanel);
    dialog.getRootPane().setDefaultButton(submitButton);

    submitButton.addActionListener(e -> {
        try {
            String title = titleField.getText().trim();
            String description = descriptionArea.getText().trim();
            String status = (String) statusComboBox.getSelectedItem();
            Date startDate = Date.valueOf(startDateField.getText().trim());
            Date endDate = Date.valueOf(endDateField.getText().trim());
            int performanceRating = Integer.parseInt(performanceRatingField.getText().trim());

            boolean success = taskModel.updateTask(task.taskId, title, description, status, startDate, endDate, performanceRating);
            if (success) {
                JOptionPane.showMessageDialog(view, "Task updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                updatePerformanceStats();
                refreshTaskList();
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(view, "Failed to update task.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Invalid input: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    });

    cancelButton.addActionListener(e -> dialog.dispose());

    dialog.setVisible(true);
}





}