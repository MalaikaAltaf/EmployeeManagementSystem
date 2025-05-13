package controllers;


import java.awt.Component;
import  java.awt.GridLayout;



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
import models.EmployeeTaskModel.Task;
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
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/your_database_name", "username", "password");
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
    JTextField titleField = new JTextField(20);
    JTextArea descriptionArea = new JTextArea(5, 20);
    descriptionArea.setLineWrap(true);
    descriptionArea.setWrapStyleWord(true);

    JComboBox<String> statusComboBox = new JComboBox<>(new String[]{"Pending", "In Progress", "Completed"});
    JTextField startDateField = new JTextField(LocalDate.now().toString());
    JTextField endDateField = new JTextField(LocalDate.now().plusDays(7).toString());
    JTextField performanceRatingField = new JTextField("5");

    JPanel panel = new JPanel(new GridLayout(0, 1));
    panel.add(new JLabel("Task Title:"));
    panel.add(titleField);
    panel.add(new JLabel("Task Description:"));
    panel.add(new JScrollPane(descriptionArea));
    panel.add(new JLabel("Status:"));
    panel.add(statusComboBox);
    panel.add(new JLabel("Start Date (YYYY-MM-DD):"));
    panel.add(startDateField);
    panel.add(new JLabel("End Date (YYYY-MM-DD):"));
    panel.add(endDateField);
    panel.add(new JLabel("Performance Rating (1-10):"));
    panel.add(performanceRatingField);

    int result = JOptionPane.showConfirmDialog(view, panel, "Add New Task",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

    if (result == JOptionPane.OK_OPTION) {
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
            } else {
                JOptionPane.showMessageDialog(view, "Failed to add task.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Invalid input: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
}
private void updatePerformanceStats() {
    List<Task> tasks = taskModel.getTasksByEmployee(empId);
    if (tasks.isEmpty()) {
        view.taskProgressBar.setValue(0);
        view.goalsCompletionLabel.setText("Goals Completion: 0%");
        return;
    }
    int totalRating = 0;
    int completedTasks = 0;
    for (Task task : tasks) {
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
    List<Task> tasks = taskModel.getTasksByEmployee(empId);
    view.taskPanel.removeAll();
    for (Task task : tasks) {
        views.TaskPanel taskPanel = new views.TaskPanel(task.title, task.description, task.status, task.performanceRating);
        view.taskPanel.add(taskPanel);
        view.taskPanel.add(Box.createVerticalStrut(10));
    }
    view.taskPanel.revalidate();
    view.taskPanel.repaint();
}





}
