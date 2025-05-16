package views;

import javax.swing.*;
import java.awt.*;

public class AdminSettingView extends JPanel {
    private JTextField usernameField;
    private JPasswordField currentPasswordField;
    private JPasswordField newPasswordField;
    private JButton updateButton;

    public AdminSettingView() {
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Card panel for inputs
        JPanel card = new JPanel(new GridBagLayout());
        card.setPreferredSize(new Dimension(600, 400));
        card.setBackground(new Color(232, 245, 233)); // Light green
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 2),
                BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));

        GridBagConstraints cardGbc = new GridBagConstraints();
        cardGbc.insets = new Insets(15, 15, 15, 15);
        cardGbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Update Password");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        cardGbc.gridx = 0;
        cardGbc.gridy = 0;
        cardGbc.gridwidth = 2;
        card.add(title, cardGbc);

        cardGbc.gridwidth = 1;
        cardGbc.gridy++;

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        cardGbc.gridx = 0;
        card.add(usernameLabel, cardGbc);

        usernameField = new JTextField(20);
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        cardGbc.gridx = 1;
        card.add(usernameField, cardGbc);

        cardGbc.gridy++;

        JLabel currentPasswordLabel = new JLabel("Current Password:");
        currentPasswordLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        cardGbc.gridx = 0;
        card.add(currentPasswordLabel, cardGbc);

        currentPasswordField = new JPasswordField(20);
        currentPasswordField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        cardGbc.gridx = 1;
        card.add(currentPasswordField, cardGbc);

        cardGbc.gridy++;

        JLabel newPasswordLabel = new JLabel("New Password:");
        newPasswordLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        cardGbc.gridx = 0;
        card.add(newPasswordLabel, cardGbc);

        newPasswordField = new JPasswordField(20);
        newPasswordField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        cardGbc.gridx = 1;
        card.add(newPasswordField, cardGbc);

        cardGbc.gridy++;

        updateButton = new JButton("Update Password");
        updateButton.setFont(new Font("Segoe UI", Font.BOLD, 18));
        updateButton.setBackground(new Color(0, 150, 136)); // Teal green
        updateButton.setForeground(Color.WHITE);
        updateButton.setFocusPainted(false);
        updateButton.setPreferredSize(new Dimension(200, 40));

        cardGbc.gridx = 0;
        cardGbc.gridwidth = 2;
        cardGbc.anchor = GridBagConstraints.CENTER;
        card.add(updateButton, cardGbc);

        // Add card to main panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(card, gbc);
    }

    // Getters
    public String getUsername() {
        return usernameField.getText().trim();
    }

    public String getCurrentPassword() {
        return new String(currentPasswordField.getPassword());
    }

    public String getNewPassword() {
        return new String(newPasswordField.getPassword());
    }

    public JButton getUpdateButton() {
        return updateButton;
    }
}