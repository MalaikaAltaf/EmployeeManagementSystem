package views;

import javax.swing.*;
import java.awt.*;

public class EmployeeProfileView extends JPanel {
    public JLabel nameLabel, emailLabel, phoneLabel, departmentLabel, designationLabel, dateJoinedLabel, salaryLabel;
    public JLabel profilePicLabel;

    public EmployeeProfileView() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel heading = new JLabel("My Profile", SwingConstants.CENTER);
        heading.setFont(new Font("SansSerif", Font.BOLD, 20));
        add(heading, BorderLayout.NORTH);

        JPanel infoPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        infoPanel.setBackground(Color.WHITE);

        nameLabel = new JLabel();
        emailLabel = new JLabel();
        phoneLabel = new JLabel();
        departmentLabel = new JLabel();
        designationLabel = new JLabel();
        dateJoinedLabel = new JLabel();
        salaryLabel = new JLabel();

        infoPanel.add(new JLabel("Name:")); infoPanel.add(nameLabel);
        infoPanel.add(new JLabel("Email:")); infoPanel.add(emailLabel);
        infoPanel.add(new JLabel("Phone:")); infoPanel.add(phoneLabel);
        infoPanel.add(new JLabel("Department:")); infoPanel.add(departmentLabel);
        infoPanel.add(new JLabel("Designation:")); infoPanel.add(designationLabel);
        infoPanel.add(new JLabel("Date Joined:")); infoPanel.add(dateJoinedLabel);
        infoPanel.add(new JLabel("Salary:")); infoPanel.add(salaryLabel);

        add(infoPanel, BorderLayout.CENTER);

        // Profile picture
        profilePicLabel = new JLabel();
        profilePicLabel.setHorizontalAlignment(SwingConstants.CENTER);
        profilePicLabel.setPreferredSize(new Dimension(150, 150));
        add(profilePicLabel, BorderLayout.EAST);
    }

    public void setProfilePic(byte[] imageData) {
        if (imageData != null) {
            ImageIcon icon = new ImageIcon(imageData);
            Image scaled = icon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            profilePicLabel.setIcon(new ImageIcon(scaled));
        } else {
            profilePicLabel.setIcon(null);
        }
    }
}
