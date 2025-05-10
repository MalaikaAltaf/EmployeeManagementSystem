package views;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class EmployeeProfileView extends JPanel {
    public JLabel nameLabel, emailLabel, phoneLabel, departmentLabel, designationLabel, dateJoinedLabel, salaryLabel;
    public JLabel profilePicLabel;

    public EmployeeProfileView() {
        setLayout(new BorderLayout(15, 15));
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(30, 50, 30, 50)); // Better padding

        // === Heading ===
        JLabel heading = new JLabel("My Profile", SwingConstants.CENTER);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 26));
        heading.setForeground(new Color(0, 150, 136));
        heading.setBorder(new EmptyBorder(10, 0, 20, 0));
        add(heading, BorderLayout.NORTH);

        // === Profile Panel (Card style) ===
        JPanel profilePanel = new JPanel(new BorderLayout(30, 0));
        profilePanel.setBackground(new Color(250, 250, 250));
        profilePanel.setBorder(new CompoundBorder(
                new LineBorder(new Color(0, 150, 136), 1, true),
                new EmptyBorder(25, 30, 25, 30)
        ));

        // === Profile Picture Panel ===
        profilePicLabel = new JLabel();
        profilePicLabel.setHorizontalAlignment(SwingConstants.CENTER);
        profilePicLabel.setPreferredSize(new Dimension(150, 150));
        profilePicLabel.setBorder(new LineBorder(Color.LIGHT_GRAY, 2, true));

        JPanel picPanel = new JPanel(new BorderLayout());
        picPanel.setBackground(new Color(250, 250, 250));
        picPanel.setBorder(new EmptyBorder(0, 0, 0, 20));
        picPanel.add(profilePicLabel, BorderLayout.NORTH);

        // === Info Panel ===
        JPanel infoPanel = new JPanel(new GridBagLayout());
        infoPanel.setBackground(new Color(250, 250, 250));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 10, 12, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("Segoe UI", Font.BOLD, 16);
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
            values[i].setFont(valueFont);
            gbc.gridx = 0; gbc.gridy = i;
            infoPanel.add(labels[i], gbc);
            gbc.gridx = 1;
            gbc.weightx = 1;
            infoPanel.add(values[i], gbc);
        }

        profilePanel.add(picPanel, BorderLayout.WEST);
        profilePanel.add(infoPanel, BorderLayout.CENTER);
        add(profilePanel, BorderLayout.CENTER);
    }

    public void setProfilePic(byte[] imageData) {
        if (imageData != null) {
            ImageIcon icon = new ImageIcon(imageData);
            Image scaled = icon.getImage().getScaledInstance(140, 140, Image.SCALE_SMOOTH);
            profilePicLabel.setIcon(new ImageIcon(scaled));
        } else {
            profilePicLabel.setIcon(null);
        }
    }
}
