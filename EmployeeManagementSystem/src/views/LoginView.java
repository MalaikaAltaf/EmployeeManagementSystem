package views;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleBox;
    private JButton loginBtn;
    private JButton exitBtn;

    public LoginView() {
        setTitle("Employee Management System - Login");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 2, 10, 10));

        JLabel roleLabel = new JLabel("Login As:");
        roleBox = new JComboBox<>(new String[]{"Admin", "Employee"});

        JLabel userLabel = new JLabel("Username:");
        usernameField = new JTextField();

        JLabel passLabel = new JLabel("Password:");
        passwordField = new JPasswordField();

        loginBtn = new JButton("Login");
        exitBtn = new JButton("Exit");

        add(roleLabel); add(roleBox);
        add(userLabel); add(usernameField);
        add(passLabel); add(passwordField);
        add(loginBtn); add(exitBtn);
    }

    // ✅ Getters for Controller Access
    public JButton getLoginButton() {
        return loginBtn;
    }

    public JButton getExitButton() {
        return exitBtn;
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public String getRole() {
        return (String) roleBox.getSelectedItem();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginView view = new LoginView();
            new controllers.LoginController(view); // Attach controller
            view.setVisible(true);
        });
    }
}
