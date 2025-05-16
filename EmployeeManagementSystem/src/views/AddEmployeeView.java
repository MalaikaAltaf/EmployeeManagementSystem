package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;

public class AddEmployeeView extends JPanel {
    public JTextField txtFirstName, txtLastName, txtEmail, txtPhone, txtDepartment,
            txtDesignation, txtDateJoined, txtSalary, txtUsername;
    public JPasswordField txtPassword;
    public JButton btnSubmit, btnUploadImage, btnDeleteEmployee;
    public JLabel lblImagePreview;
    public String selectedImagePath;

    public JTextField txtDeleteUsername;

    public AddEmployeeView() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Header Panel
        JPanel headerPanel = new JPanel(new GridBagLayout());
        headerPanel.setBackground(new Color(45, 45, 45));
        headerPanel.setPreferredSize(new Dimension(70, 60));

        JLabel headerLabel = new JLabel("Add New Employee");
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        headerPanel.add(headerLabel);

        // Center Panel (Form + Image)
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // Left Panel - Form
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("Segoe UI", Font.BOLD, 16);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 16);
        Dimension fieldSize = new Dimension(350, 40);

        txtFirstName = createTextField(fieldFont, fieldSize);
        txtLastName = createTextField(fieldFont, fieldSize);
        txtEmail = createTextField(fieldFont, fieldSize);
        txtPhone = createTextField(fieldFont, fieldSize);
        txtDepartment = createTextField(fieldFont, fieldSize);
        txtDesignation = createTextField(fieldFont, fieldSize);
        txtDateJoined = createTextField(fieldFont, fieldSize);
        txtSalary = createTextField(fieldFont, fieldSize);
        txtUsername = createTextField(fieldFont, fieldSize);

        txtPassword = new JPasswordField();
        txtPassword.setFont(fieldFont);
        txtPassword.setPreferredSize(fieldSize);

        int row = 0;
        row = addRow(formPanel, gbc, row, "First Name:", txtFirstName, labelFont);
        row = addRow(formPanel, gbc, row, "Last Name:", txtLastName, labelFont);
        row = addRow(formPanel, gbc, row, "Email:", txtEmail, labelFont);
        row = addRow(formPanel, gbc, row, "Phone:", txtPhone, labelFont);
        row = addRow(formPanel, gbc, row, "Department:", txtDepartment, labelFont);
        row = addRow(formPanel, gbc, row, "Designation:", txtDesignation, labelFont);
        row = addRow(formPanel, gbc, row, "Date Joined:", txtDateJoined, labelFont);
        row = addRow(formPanel, gbc, row, "Salary:", txtSalary, labelFont);
        row = addRow(formPanel, gbc, row, "Username:", txtUsername, labelFont);
        row = addRow(formPanel, gbc, row, "Password:", txtPassword, labelFont);

        btnSubmit = new JButton("Add Employee");
        styleButton(btnSubmit, new Color(33, 150, 243));
        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(btnSubmit, gbc);

        // Right Panel - Image Upload (Styled like shared image)
        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.Y_AXIS));
        imagePanel.setBackground(Color.WHITE);
        imagePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblProfilePic = new JLabel("Profile Picture");
        lblProfilePic.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        lblProfilePic.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblImagePreview = new JLabel();
        lblImagePreview.setPreferredSize(new Dimension(180, 180));
        lblImagePreview.setMaximumSize(new Dimension(180, 180));
        lblImagePreview.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        lblImagePreview.setOpaque(true);
        lblImagePreview.setBackground(Color.WHITE);
        lblImagePreview.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnUploadImage = new JButton("Upload Image");
        btnUploadImage.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnUploadImage.setPreferredSize(new Dimension(140, 35));
        btnUploadImage.setMaximumSize(new Dimension(140, 35));
        btnUploadImage.setFocusPainted(false);
        btnUploadImage.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnUploadImage.setBackground(new Color(240, 240, 240));
        btnUploadImage.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        btnUploadImage.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int option = fileChooser.showOpenDialog(this);
            if (option == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                selectedImagePath = file.getAbsolutePath();
                ImageIcon icon = new ImageIcon(new ImageIcon(selectedImagePath)
                        .getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH));
                lblImagePreview.setIcon(icon);
            }
        });

        imagePanel.add(lblProfilePic);
        imagePanel.add(Box.createVerticalStrut(15));
        imagePanel.add(lblImagePreview);
        imagePanel.add(Box.createVerticalStrut(15));
        imagePanel.add(btnUploadImage);

        centerPanel.add(formPanel);
        centerPanel.add(imagePanel);

        // Bottom Panel - Delete Section
        JPanel deletePanel = new JPanel(new GridBagLayout());
        deletePanel.setBackground(new Color(245, 245, 245));
        deletePanel.setBorder(BorderFactory.createTitledBorder("Delete Employee"));

        GridBagConstraints gbcDelete = new GridBagConstraints();
        gbcDelete.insets = new Insets(10, 10, 10, 10);
        gbcDelete.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblDelete = new JLabel("Username:");
        lblDelete.setFont(labelFont);

        txtDeleteUsername = new JTextField();
        txtDeleteUsername.setFont(fieldFont);
        txtDeleteUsername.setPreferredSize(new Dimension(200, 35));

        btnDeleteEmployee = new JButton("Delete");
        styleButton(btnDeleteEmployee, new Color(244, 67, 54));

        gbcDelete.gridx = 0;
        gbcDelete.gridy = 0;
        deletePanel.add(lblDelete, gbcDelete);

        gbcDelete.gridx = 1;
        deletePanel.add(txtDeleteUsername, gbcDelete);

        gbcDelete.gridx = 2;
        deletePanel.add(btnDeleteEmployee, gbcDelete);

        // Assemble All
        add(headerPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(deletePanel, BorderLayout.SOUTH);
    }

    private int addRow(JPanel panel, GridBagConstraints gbc, int row, String label, JComponent field, Font font) {
        gbc.gridx = 0;
        gbc.gridy = row;
        JLabel lbl = new JLabel(label);
        lbl.setFont(font);
        panel.add(lbl, gbc);

        gbc.gridx = 1;
        panel.add(field, gbc);
        return row + 1;
    }

    private JTextField createTextField(Font font, Dimension size) {
        JTextField tf = new JTextField();
        tf.setFont(font);
        tf.setPreferredSize(size);
        return tf;
    }

    private void styleButton(JButton btn, Color bgColor) {
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(160, 40));
    }

    public void addSubmitButtonListener(ActionListener listener) {
        btnSubmit.addActionListener(listener);
    }

    public void addDeleteButtonListener(ActionListener listener) {
        btnDeleteEmployee.addActionListener(listener);
    }
}