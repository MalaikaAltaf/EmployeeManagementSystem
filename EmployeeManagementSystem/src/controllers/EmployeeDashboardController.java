package controllers;


import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;



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

    private EmployeeTaskModel taskModel;

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
        this.employeeName = employeeName;
        this.empId = empId;

        view = new EmployeeDashboardView(employeeName);

        // Initialize task model with DB connection
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/EMS1", "root", "root1122");
            taskModel = new EmployeeTaskModel(conn);
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

        
    }

    private void initController() {
        view.logoutBtn.addActionListener(_ -> handleLogout());

        view.dashboardBtn.addActionListener(e -> {
            view.cardLayout.show(view.contentPanel, "Dashboard");
        });

        // ✅ Chat Button Handler
        view.chatButton.addActionListener(_ -> {
            view.cardLayout.show(view.contentPanel, "Chat");
        });

        // ✅ Show Profile Panel when "My Profile" is clicked
        view.profileBtn.addActionListener(e -> {
            profileController.loadEmployeeProfile(); // optional: refresh data
            view.cardLayout.show(view.contentPanel, "Profile");
        });

        view.leaveBtn.addActionListener(e -> {
            leaveController.refreshLeaveTable();
            view.cardLayout.show(view.contentPanel, "Leave");
        });

        // ✅ Show Salary Panel when "Salary" is clicked
        view.salaryBtn.addActionListener(e -> {
            salaryController.loadSalaryData(); // optional: refresh salary data
            view.cardLayout.show(view.contentPanel, "Salary");
        });

        // ✅ Show Settings Panel when "Setting" is clicked
        view.settingBtn.addActionListener(e -> {
            view.cardLayout.show(view.contentPanel, "Settings");
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
    }

    private void startTimers() {
        timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
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
    }

    private String formatTime(int totalSeconds) {
        int hrs = totalSeconds / 3600;
        int mins = (totalSeconds % 3600) / 60;
        int secs = totalSeconds % 60;
        return String.format("%02d:%02d:%02d", hrs, mins, secs);
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
    }
}
private void showAddTaskDialog() {
    JDialog dialog = new JDialog(view, "Add New Task", true);
    dialog.setSize(400, 400);
    dialog.setLocationRelativeTo(view);

    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    JTextField titleField = new JTextField(20);
    JTextArea descriptionArea = new JTextArea(3, 20);
    descriptionArea.setLineWrap(true);
    descriptionArea.setWrapStyleWord(true);

    JComboBox<String> statusComboBox = new JComboBox<>(new String[]{"Pending", "In Progress", "Completed"});
    JTextField startDateField = new JTextField(LocalDate.now().toString());
    JTextField endDateField = new JTextField(LocalDate.now().plusDays(7).toString());
    JTextField performanceRatingField = new JTextField("5");

    mainPanel.add(new JLabel("Task Title:"));
    mainPanel.add(titleField);
    mainPanel.add(Box.createVerticalStrut(10));

    mainPanel.add(new JLabel("Task Description:"));
    JScrollPane descriptionScroll = new JScrollPane(descriptionArea);
    descriptionScroll.setPreferredSize(new Dimension(350, 60));
    mainPanel.add(descriptionScroll);
    mainPanel.add(Box.createVerticalStrut(10));

    JPanel statusDatePanel = new JPanel(new GridLayout(2, 2, 10, 10));
    statusDatePanel.add(new JLabel("Status:"));
    statusDatePanel.add(statusComboBox);
    statusDatePanel.add(new JLabel("Start Date (YYYY-MM-DD):"));
    statusDatePanel.add(startDateField);
    statusDatePanel.add(new JLabel("End Date (YYYY-MM-DD):"));
    statusDatePanel.add(endDateField);
    mainPanel.add(statusDatePanel);
    mainPanel.add(Box.createVerticalStrut(10));

    mainPanel.add(new JLabel("Performance Rating (1-10):"));
    mainPanel.add(performanceRatingField);
    mainPanel.add(Box.createVerticalStrut(20));

    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    JButton submitButton = new JButton("Submit");
    JButton cancelButton = new JButton("Cancel");
    buttonPanel.add(submitButton);
    buttonPanel.add(cancelButton);

    mainPanel.add(buttonPanel);

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
        view.taskListPanel.add(taskPanel);
        view.taskListPanel.add(Box.createVerticalStrut(10));
    }
    view.taskListPanel.revalidate();
    view.taskListPanel.repaint();
}

private void showEditTaskDialog(EmployeeTaskModel.Task task) {
    JDialog dialog = new JDialog(view, "Edit Task", true);
    dialog.setSize(400, 400);
    dialog.setLocationRelativeTo(view);

    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    JTextField titleField = new JTextField(task.title, 20);
    JTextArea descriptionArea = new JTextArea(task.description, 3, 20);
    descriptionArea.setLineWrap(true);
    descriptionArea.setWrapStyleWord(true);

    JComboBox<String> statusComboBox = new JComboBox<>(new String[]{"Pending", "In Progress", "Completed"});
    statusComboBox.setSelectedItem(task.status);

    JTextField startDateField = new JTextField(task.startDate.toString());
    JTextField endDateField = new JTextField(task.endDate.toString());
    JTextField performanceRatingField = new JTextField(String.valueOf(task.performanceRating));

    mainPanel.add(new JLabel("Task Title:"));
    mainPanel.add(titleField);
    mainPanel.add(Box.createVerticalStrut(10));

    mainPanel.add(new JLabel("Task Description:"));
    JScrollPane descriptionScroll = new JScrollPane(descriptionArea);
    descriptionScroll.setPreferredSize(new Dimension(350, 60));
    mainPanel.add(descriptionScroll);
    mainPanel.add(Box.createVerticalStrut(10));

    JPanel statusDatePanel = new JPanel(new GridLayout(2, 2, 10, 10));
    statusDatePanel.add(new JLabel("Status:"));
    statusDatePanel.add(statusComboBox);
    statusDatePanel.add(new JLabel("Start Date (YYYY-MM-DD):"));
    statusDatePanel.add(startDateField);
    statusDatePanel.add(new JLabel("End Date (YYYY-MM-DD):"));
    statusDatePanel.add(endDateField);
    mainPanel.add(statusDatePanel);
    mainPanel.add(Box.createVerticalStrut(10));

    mainPanel.add(new JLabel("Performance Rating (1-10):"));
    mainPanel.add(performanceRatingField);
    mainPanel.add(Box.createVerticalStrut(20));

    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    JButton submitButton = new JButton("Update");
    JButton cancelButton = new JButton("Cancel");
    buttonPanel.add(submitButton);
    buttonPanel.add(cancelButton);

    mainPanel.add(buttonPanel);

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
