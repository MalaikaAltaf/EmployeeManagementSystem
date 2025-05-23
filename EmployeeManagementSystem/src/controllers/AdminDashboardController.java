package controllers;

import views.*;
import models.*;

import javax.swing.*;

public class AdminDashboardController {
    private AdminDashboard adminView;
    private JPanel mainPanel;

    private views.ChatView chatView;
    private controllers.ChatController chatController;

    public AdminDashboardController(AdminDashboard adminView) {
        this.adminView = adminView;
        this.mainPanel = adminView.getMainPanel();

        // Initialize chat view and controller with identifier "Admin"
        chatView = new views.ChatView();
        chatController = new controllers.ChatController(chatView, "Admin");
        mainPanel.add(chatView);

        addListeners();

        showHomePanel();  // <-- Show Home on login by default
    }

    private void addListeners() {
        adminView.getHomeButton().addActionListener(e -> showHomePanel());  // <-- Home button listener
        adminView.getEmployeeButton().addActionListener(e -> showEmployeePanel());
        adminView.getDepartmentsButton().addActionListener(e -> showDepartmentPanel());
        adminView.getLeaveButton().addActionListener(e -> showLeavePanel());
        adminView.getSettingsButton().addActionListener(e -> showSettingsPanel());
        adminView.getChatButton().addActionListener(e -> showChatPanel());
        adminView.getLogoutButton().addActionListener(e -> logout());
        adminView.getSalaryButton().addActionListener(e -> showSalaryPanel());
    }

    private void showHomePanel() {
    AdminHomeView homeView = new AdminHomeView();
    AdminHomeModel homeModel = new AdminHomeModel();   // create model instance
    AdminHomeController homeController = new AdminHomeController(homeModel, homeView);  // pass model
    setMainPanelContent(homeView);
}

    private void showEmployeePanel() {
        AddEmployeeView empView = new AddEmployeeView();
        new AddEmployeeController(empView);
        setMainPanelContent(empView);
    }

    private void showDepartmentPanel() {
        AdminDepartmentView deptView = new AdminDepartmentView();
        AdminDepartmentModel deptModel = new AdminDepartmentModel();
        new AdminDepartmentController(deptModel, deptView);
        setMainPanelContent(deptView);
    }

    private void showLeavePanel() {
        AdminLeaveView leaveView = new AdminLeaveView();
        AdminLeaveModel leaveModel = new AdminLeaveModel();
        new AdminLeaveController(leaveView, leaveModel);
        setMainPanelContent(leaveView);
    }

    private void showSettingsPanel() {
        AdminSettingsModel model = new AdminSettingsModel();
        AdminSettingView settingView = new AdminSettingView();
        AdminSettingController settingController = new AdminSettingController(model, settingView);
        setMainPanelContent(settingView);
    }
    

    private void showChatPanel() {
        // Show the chatView panel
        setMainPanelContent(chatView);
    }

    private void showSalaryPanel() {
        AdminSalaryView salaryView = new AdminSalaryView();
        AdminSalaryModel salaryModel = new AdminSalaryModel();
        new AdminSalaryController(salaryView, salaryModel);
        setMainPanelContent(salaryView);
    }

private void logout() {
    int confirm = JOptionPane.showConfirmDialog(
        adminView,
        "Are you sure you want to log out?",
        "Logout Confirmation",
        JOptionPane.YES_NO_OPTION
    );

    if (confirm == JOptionPane.YES_OPTION) {
        adminView.dispose();
        javax.swing.SwingUtilities.invokeLater(() -> {
            views.LoginView loginView = new views.LoginView();
            new controllers.LoginController(loginView);
            loginView.setVisible(true);
        });
    }
}

    private void setMainPanelContent(JPanel panel) {
        mainPanel.removeAll();
        mainPanel.add(panel);
        mainPanel.revalidate();
        mainPanel.repaint();
    }
}