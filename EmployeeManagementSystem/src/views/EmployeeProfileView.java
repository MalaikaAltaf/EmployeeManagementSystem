package views;

import javax.swing.*;
import java.awt.*;
import models.EmployeeModel;
import java.util.HashMap;

public class EmployeeProfileView extends JPanel {
    public JLabel profilePicLabel;
    public JLabel nameValue, idValue, emailValue, phoneValue, deptValue, desigValue, joinDateValue, salaryValue;
    public JButton uploadPicBtn;

    private int empId;
    private EmployeeModel model;

    public EmployeeProfileView(int empId) {
        this.empId = empId;
        this.model = new EmployeeModel(); // Initialize model to fetch data
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(400, 600));

        // Profile Card Setup
        JPanel profileCard = new JPanel(new GridBagLayout());
        profileCard.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.anchor = GridBagConstraints.WEST;

        // Profile Picture Section
        profilePicLabel = new JLabel();
        profilePicLabel.setPreferredSize(new Dimension(150, 150));
        profilePicLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        profilePicLabel.setHorizontalAlignment(JLabel.CENTER);
        profilePicLabel.setOpaque(true);
        profilePicLabel.setBackground(Color.LIGHT_GRAY);

        uploadPicBtn = new JButton("Upload Picture");
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        profileCard.add(profilePicLabel, gbc);
        gbc.gridy++;
        profileCard.add(uploadPicBtn, gbc);

        // Profile data labels
        nameValue = new JLabel();
        idValue = new JLabel();
        emailValue = new JLabel();
        phoneValue = new JLabel();
        deptValue = new JLabel();
        desigValue = new JLabel();
        joinDateValue = new JLabel();
        salaryValue = new JLabel();

        addProfileData(profileCard, gbc);

        add(profileCard, BorderLayout.CENTER);
    }

    private void addProfileData(JPanel panel, GridBagConstraints gbc) {
        // Add labels and values to the profile card
        int row = 2;
        gbc.gridwidth = 1;

        // Add label and value in two columns per row
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel("Full Name:"), gbc);
        gbc.gridx = 1;
        panel.add(nameValue, gbc);

        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel("Employee ID:"), gbc);
        gbc.gridx = 1;
        panel.add(idValue, gbc);

        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        panel.add(emailValue, gbc);

        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel("Phone:"), gbc);
        gbc.gridx = 1;
        panel.add(phoneValue, gbc);

        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel("Department:"), gbc);
        gbc.gridx = 1;
        panel.add(deptValue, gbc);

        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel("Designation:"), gbc);
        gbc.gridx = 1;
        panel.add(desigValue, gbc);

        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel("Date Joined:"), gbc);
        gbc.gridx = 1;
        panel.add(joinDateValue, gbc);

        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel("Salary:"), gbc);
        gbc.gridx = 1;
        panel.add(salaryValue, gbc);
    }

    public void refreshData() {
        // Fetch data from the model using empId
        HashMap<String, Object> data = model.getEmployeeDataById(empId);

        if (!data.isEmpty()) {
            nameValue.setText(data.get("first_name") + " " + data.get("last_name"));
            idValue.setText(String.valueOf(data.get("emp_id")));
            emailValue.setText((String) data.get("email"));
            phoneValue.setText((String) data.get("phone"));
            deptValue.setText((String) data.get("department"));
            desigValue.setText((String) data.get("designation"));
            joinDateValue.setText(data.get("date_joined").toString());
            salaryValue.setText(data.get("salary").toString());

            byte[] imgBytes = (byte[]) data.get("profile_pic");
            if (imgBytes != null) {
                ImageIcon icon = new ImageIcon(new ImageIcon(imgBytes).getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH));
                profilePicLabel.setIcon(icon);
            }
        }
    }
}
