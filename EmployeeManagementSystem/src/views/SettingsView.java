package views;

import javax.swing.*;
import java.awt.*;

public class SettingsView extends JPanel {
    public JLabel currentPasswordLabel, newPasswordLabel, confirmPasswordLabel, messageLabel;
    public JPasswordField currentPasswordField, newPasswordField, confirmPasswordField;
    public JButton changePasswordBtn;

    public SettingsView() {
        setLayout(new GridLayout(5, 2, 10, 10));
        setBackground(Color.WHITE);

        currentPasswordLabel = new JLabel("Current Password: ");
        newPasswordLabel = new JLabel("New Password: ");
        confirmPasswordLabel = new JLabel("Confirm New Password: ");
        messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setForeground(Color.RED);

        currentPasswordField = new JPasswordField();
        newPasswordField = new JPasswordField();
        confirmPasswordField = new JPasswordField();

        changePasswordBtn = new JButton("Change Password");

        add(currentPasswordLabel);
        add(currentPasswordField);
        add(newPasswordLabel);
        add(newPasswordField);
        add(confirmPasswordLabel);
        add(confirmPasswordField);
        add(messageLabel);
        add(changePasswordBtn);
    }
}
