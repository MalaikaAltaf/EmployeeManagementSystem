package views;

import javax.swing.*;
import java.awt.*;

public class SettingsView extends JPanel {
    public JLabel currentPasswordLabel, newPasswordLabel, confirmPasswordLabel, messageLabel;
    public JPasswordField currentPasswordField, newPasswordField, confirmPasswordField;
    public JButton changePasswordBtn;

    public SettingsView() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Title panel
        JLabel titleLabel = new JLabel("Change Password", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        add(titleLabel, BorderLayout.NORTH);

        // Center form panel with GridBagLayout
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0; gbc.gridy = 0;

        // Labels
        currentPasswordLabel = new JLabel("Current Password:");
        newPasswordLabel = new JLabel("New Password:");
        confirmPasswordLabel = new JLabel("Confirm New Password:");

        // Fields
        currentPasswordField = new JPasswordField(20);
        newPasswordField = new JPasswordField(20);
        confirmPasswordField = new JPasswordField(20);

        // Add fields to form panel
        formPanel.add(currentPasswordLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(currentPasswordField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(newPasswordLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(newPasswordField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(confirmPasswordLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(confirmPasswordField, gbc);

        // Message label
        gbc.gridx = 0; gbc.gridy++;
        gbc.gridwidth = 2;
        messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setForeground(Color.RED);
        formPanel.add(messageLabel, gbc);

        // Submit button
        gbc.gridy++;
        changePasswordBtn = new JButton("Change Password");
        changePasswordBtn.setBackground(new Color(0, 153, 153));
        changePasswordBtn.setForeground(Color.WHITE);
        changePasswordBtn.setFocusPainted(false);
        changePasswordBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        changePasswordBtn.setPreferredSize(new Dimension(200, 40));
        formPanel.add(changePasswordBtn, gbc);

        add(formPanel, BorderLayout.CENTER);
    }
}
