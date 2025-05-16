package views;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class EmployeeProfileView extends JPanel {
    public JLabel nameLabel, emailLabel, phoneLabel, departmentLabel, designationLabel, dateJoinedLabel, salaryLabel;
    public JLabel profilePicLabel;

    public EmployeeProfileView() {
        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(245, 247, 250)); // Soft light background
        setBorder(new EmptyBorder(30, 50, 30, 50)); // Better padding

        // === Heading ===
        JLabel heading = new JLabel("My Profile", SwingConstants.CENTER);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 28));
        heading.setForeground(new Color(0, 120, 140));
        heading.setBorder(new EmptyBorder(10, 0, 30, 0));
        add(heading, BorderLayout.NORTH);

        // === Profile Panel (Card style) ===
        JPanel profilePanel = new JPanel(new BorderLayout(30, 0));
        profilePanel.setBackground(Color.WHITE);
        profilePanel.setBorder(new CompoundBorder(
                new CompoundBorder(
                    new LineBorder(new Color(0, 120, 140, 150), 2, true),
                    new EmptyBorder(30, 30, 30, 30)
                ),
                new LineBorder(new Color(200, 200, 200, 100), 5, true)
        ));
        profilePanel.setOpaque(true);
        profilePanel.setPreferredSize(new Dimension(800, 300));
        profilePanel.setMaximumSize(new Dimension(800, 300));
        profilePanel.setMinimumSize(new Dimension(800, 300));
        profilePanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // === Profile Picture Panel ===
        profilePicLabel = new JLabel();
        profilePicLabel.setHorizontalAlignment(SwingConstants.CENTER);
        profilePicLabel.setPreferredSize(new Dimension(200, 200));
        profilePicLabel.setBorder(new CompoundBorder(
            new LineBorder(new Color(0, 120, 140), 4, true),
            new EmptyBorder(8, 8, 8, 8)
        ));
        profilePicLabel.setOpaque(true);
        profilePicLabel.setBackground(Color.WHITE);
        profilePicLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        profilePicLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                profilePicLabel.setBackground(new Color(230, 245, 250));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                profilePicLabel.setBackground(Color.WHITE);
            }
        });

        JPanel picPanel = new JPanel(new BorderLayout());
        picPanel.setBackground(new Color(250, 250, 250));
        picPanel.setBorder(new CompoundBorder(
            new EmptyBorder(0, 20, 0, 0),
            new LineBorder(new Color(200, 200, 200), 1, true)
        ));
        picPanel.add(profilePicLabel, BorderLayout.NORTH);

        // === Info Panel ===
        JPanel infoPanel = new JPanel(new GridBagLayout());
        infoPanel.setBackground(new Color(250, 250, 250));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 10, 12, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("Segoe UI", Font.BOLD, 18);
        Font valueFont = new Font("Segoe UI", Font.PLAIN, 16);

        nameLabel = new JLabel();
        emailLabel = new JLabel();
        phoneLabel = new JLabel();
        departmentLabel = new JLabel();
        designationLabel = new JLabel();
        dateJoinedLabel = new JLabel();
        salaryLabel = new JLabel();

        JLabel[] labels = {
            new JLabel("Name:"), new JLabel("Email:"), new JLabel("Phone:"),
            new JLabel("Department:"), new JLabel("Designation:"),
            new JLabel("Date Joined:"), new JLabel("Salary:")
        };

        JLabel[] values = {
            nameLabel, emailLabel, phoneLabel,
            departmentLabel, designationLabel,
            dateJoinedLabel, salaryLabel
        };

        for (int i = 0; i < labels.length; i++) {
            labels[i].setFont(labelFont);
            labels[i].setToolTipText("Employee " + labels[i].getText().replace(":", ""));
            values[i].setFont(valueFont);
            values[i].setToolTipText("Employee " + labels[i].getText().replace(":", "") + " value");
            gbc.gridx = 0; gbc.gridy = i * 2;
            infoPanel.add(labels[i], gbc);
            gbc.gridx = 1;
            infoPanel.add(values[i], gbc);
            if (i < labels.length - 1) {
                gbc.gridx = 0; gbc.gridy = i * 2 + 1; gbc.gridwidth = 2;
                JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
                separator.setForeground(new Color(200, 200, 200));
                infoPanel.add(separator, gbc);
                gbc.gridwidth = 1;
            }
        }

        profilePanel.add(picPanel, BorderLayout.EAST);
        profilePanel.add(infoPanel, BorderLayout.CENTER);
        add(profilePanel, BorderLayout.CENTER);
    }

    public void setProfilePic(byte[] imageData) {
        if (imageData != null) {
            ImageIcon icon = new ImageIcon(imageData);
            Image scaled = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            profilePicLabel.setIcon(new ImageIcon(scaled));
        } else {
            profilePicLabel.setIcon(null);
        }
    }
}
