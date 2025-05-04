package views;

import javax.swing.*;
import java.awt.*;

public class AdminDashboard extends JFrame {

    public AdminDashboard() {
        setTitle("Admin Dashboard");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Full screen
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // -------- Header Panel --------
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(32, 178, 170)); // Sea green header
        headerPanel.setPreferredSize(new Dimension(getWidth(), 70));

        JLabel welcomeLabel = new JLabel("👑 Welcome, Admin!", SwingConstants.CENTER);
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 26));
        headerPanel.add(welcomeLabel, BorderLayout.CENTER);

        JButton logoutButton = new JButton("Log Out");
        logoutButton.setBackground(new Color(192, 57, 43));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);
        logoutButton.setFont(new Font("Arial", Font.BOLD, 16));
        logoutButton.setPreferredSize(new Dimension(120, 40)); // Bigger size
        logoutButton.setMargin(new Insets(10, 20, 10, 20));

        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 15));
        logoutPanel.setOpaque(false);
        logoutPanel.add(logoutButton);
        headerPanel.add(logoutPanel, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);

        // -------- Left Panel with Navigation Buttons --------
        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setBackground(new Color(44, 62, 80));
        sidePanel.setPreferredSize(new Dimension(200, getHeight()));

        // Button details
        String[] buttonNames = { "Employee", "Departments", "Salary", "Leave", "Settings" };
        Color[] buttonColors = {
            new Color(52, 152, 219),    // Blue
            new Color(39, 174, 96),     // Green
            new Color(241, 196, 15),    // Yellow
            new Color(231, 76, 60),     // Red
            new Color(155, 89, 182)     // Purple (Settings)
        };

        for (int i = 0; i < buttonNames.length; i++) {
            JButton button = new JButton(buttonNames[i]);
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.setMaximumSize(new Dimension(180, 45));
            button.setBackground(buttonColors[i]);
            button.setForeground(Color.WHITE);
            button.setFont(new Font("Arial", Font.BOLD, 15));
            button.setFocusPainted(false);
            sidePanel.add(Box.createVerticalStrut(20));
            sidePanel.add(button);
        }

        add(sidePanel, BorderLayout.WEST);

        // -------- Center Panel with Image --------
        JPanel imagePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon icon = new ImageIcon("images/dashboard.jpg"); // Use your actual image path
                if (icon.getIconWidth() == -1) {
                    g.setColor(Color.RED);
                    g.drawString("Image not found: images/dashboard.jpg", 50, 50);
                } else {
                    Image img = icon.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
                    g.drawImage(img, 0, 0, this);
                }
            }
        };
        imagePanel.setBackground(Color.WHITE);
        add(imagePanel, BorderLayout.CENTER);

        // -------- Show Frame --------
        setVisible(true);
    }

}