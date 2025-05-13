package views;

import javax.swing.*;
import java.awt.*;


import utils.ThemeManager;
import utils.ThemeManager.Theme;

import javax.swing.*;
import java.awt.*;

public class SettingsView extends JPanel {
    public JLabel currentPasswordLabel, newPasswordLabel, confirmPasswordLabel, messageLabel;
    public JPasswordField currentPasswordField, newPasswordField, confirmPasswordField;
    public JButton changePasswordBtn;

    public JLabel emailLabel;
    public JTextField emailField;
    public JButton updateEmailBtn;

    public JLabel themeLabel;
    public JComboBox<String> themeComboBox;
    public JButton applyThemeBtn;

    public SettingsView() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Settings", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0; gbc.gridy = 0;

        // Password Change
        currentPasswordLabel = new JLabel("Current Password:");
        newPasswordLabel = new JLabel("New Password:");
        confirmPasswordLabel = new JLabel("Confirm New Password:");
        currentPasswordField = new JPasswordField(20);
        newPasswordField = new JPasswordField(20);
        confirmPasswordField = new JPasswordField(20);

        formPanel.add(currentPasswordLabel, gbc); gbc.gridx = 1;
        formPanel.add(currentPasswordField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(newPasswordLabel, gbc); gbc.gridx = 1;
        formPanel.add(newPasswordField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(confirmPasswordLabel, gbc); gbc.gridx = 1;
        formPanel.add(confirmPasswordField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        gbc.gridwidth = 2;
        messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setForeground(Color.RED);
        formPanel.add(messageLabel, gbc);

        gbc.gridy++;
        changePasswordBtn = new JButton("Change Password");
        changePasswordBtn.setBackground(new Color(0, 153, 153));
        changePasswordBtn.setForeground(Color.WHITE);
        changePasswordBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        formPanel.add(changePasswordBtn, gbc);

        // Email Update
        gbc.gridx = 0; gbc.gridy++; gbc.gridwidth = 1;
        emailLabel = new JLabel("Update Email:");
        formPanel.add(emailLabel, gbc); gbc.gridx = 1;
        emailField = new JTextField(20);
        formPanel.add(emailField, gbc);

        gbc.gridy++;
        updateEmailBtn = new JButton("Update Email");
        updateEmailBtn.setBackground(new Color(70, 130, 180));
        updateEmailBtn.setForeground(Color.WHITE);
        updateEmailBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        formPanel.add(updateEmailBtn, gbc);

        // Theme Toggle
        gbc.gridx = 0; gbc.gridy++;
        themeLabel = new JLabel("Select Theme:");
        formPanel.add(themeLabel, gbc); gbc.gridx = 1;
        themeComboBox = new JComboBox<>(new String[]{"Light", "Dark"});
        formPanel.add(themeComboBox, gbc);

        gbc.gridy++;
        applyThemeBtn = new JButton("Apply Theme");
        applyThemeBtn.setBackground(new Color(128, 0, 128));
        applyThemeBtn.setForeground(Color.WHITE);
        applyThemeBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        formPanel.add(applyThemeBtn, gbc);

        add(formPanel, BorderLayout.CENTER);
    }
}
