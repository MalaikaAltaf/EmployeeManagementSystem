package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;

public class AddEmployeeView extends JPanel {
    public JTextField txtFirstName, txtLastName, txtEmail, txtPhone, txtDepartment, txtDesignation, txtDateJoined, txtSalary, txtUsername;
    public JPasswordField txtPassword;
    public JButton btnSubmit, btnUploadImage, btnDeleteEmployee;
    public JLabel lblImagePreview;
    public String selectedImagePath;

    public JTextField txtDeleteUsername;

    public AddEmployeeView() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // ------- Header Panel -------
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.BLACK);
        headerPanel.setPreferredSize(new Dimension(70, 50));
        headerPanel.setBorder(BorderFactory.createLineBorder(Color.white, 5));

        JLabel headerLabel = new JLabel(" Add New Employee");
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 30));
        headerPanel.setLayout(new GridBagLayout());
        headerPanel.add(headerLabel);

        // ------- Center Panel with Form and Image Upload -------
        JPanel centerPanel = new JPanel(new GridLayout(1, 2)); // Split left and right
        centerPanel.setBackground(Color.WHITE);

        // ------- Left Panel: Form Fields -------
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 15, 12, 15);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("Arial", Font.PLAIN, 18);
        Font fieldFont = new Font("Arial", Font.PLAIN, 18);
        Dimension fieldSize = new Dimension(500, 60);

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

        btnSubmit = new JButton("Add Employee");
        btnSubmit.setBackground(new Color(0, 150, 136));
        btnSubmit.setForeground(Color.WHITE);
        btnSubmit.setFont(new Font("Arial", Font.BOLD, 18));
        btnSubmit.setFocusPainted(false);
        btnSubmit.setPreferredSize(new Dimension(200, 60));

        int row = 0;
        row = addRow(formPanel, gbc, row, "First Name:", txtFirstName, labelFont);
        row = addRow(formPanel, gbc, row, "Last Name:", txtLastName, labelFont);
        row = addRow(formPanel, gbc, row, "Email:", txtEmail, labelFont);
        row = addRow(formPanel, gbc, row, "Phone:", txtPhone, labelFont);
        row = addRow(formPanel, gbc, row, "Department:", txtDepartment, labelFont);
        row = addRow(formPanel, gbc, row, "Designation:", txtDesignation, labelFont);
        row = addRow(formPanel, gbc, row, "Date Joined (YYYY-MM-DD):", txtDateJoined, labelFont);
        row = addRow(formPanel, gbc, row, "Salary:", txtSalary, labelFont);
        row = addRow(formPanel, gbc, row, "Username:", txtUsername, labelFont);
        row = addRow(formPanel, gbc, row, "Password:", txtPassword, labelFont);

        gbc.gridx = 1;
        gbc.gridy = row++;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(btnSubmit, gbc);

        // ------- Right Panel: Image Upload -------
        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.Y_AXIS));
        imagePanel.setBackground(Color.WHITE);
       

        JLabel lblProfilePic = new JLabel("Profile Picture:");
        lblProfilePic.setFont(labelFont);
        lblProfilePic.setAlignmentX(Component.LEFT_ALIGNMENT);

        lblImagePreview = new JLabel("No Image Selected", SwingConstants.CENTER);
        lblImagePreview.setPreferredSize(new Dimension(180, 180));
        lblImagePreview.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        lblImagePreview.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblImagePreview.setHorizontalTextPosition(SwingConstants.CENTER);
        lblImagePreview.setVerticalTextPosition(SwingConstants.CENTER);

        btnUploadImage = new JButton("Upload Profile Picture");
        btnUploadImage.setFont(new Font("Arial", Font.PLAIN, 16));
        btnUploadImage.setFocusPainted(false);
        btnUploadImage.setBackground(new Color(70, 130, 180));
        btnUploadImage.setForeground(Color.WHITE);
        btnUploadImage.setAlignmentX(Component.LEFT_ALIGNMENT);

        btnUploadImage.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int option = fileChooser.showOpenDialog(AddEmployeeView.this);
            if (option == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                selectedImagePath = file.getAbsolutePath();
                ImageIcon icon = new ImageIcon(new ImageIcon(selectedImagePath).getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH));
                lblImagePreview.setText("");
                lblImagePreview.setIcon(icon);
            }
        });

        imagePanel.add(lblProfilePic);
        imagePanel.add(Box.createVerticalStrut(10));
        imagePanel.add(lblImagePreview);
        imagePanel.add(Box.createVerticalStrut(10));
        imagePanel.add(btnUploadImage);

        // Add form and image panels to center panel
        centerPanel.add(formPanel);
        centerPanel.add(imagePanel);

        // ------- Bottom Panel: Delete Employee -------
        JPanel bottomPanel = new JPanel(new GridBagLayout());
        bottomPanel.setBackground(Color.WHITE);
        GridBagConstraints gbcDelete = new GridBagConstraints();
        gbcDelete.insets = new Insets(12, 15, 12, 15);
        gbcDelete.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblDeleteEmployee = new JLabel("Enter Username to Delete:");
        lblDeleteEmployee.setFont(labelFont);
        lblDeleteEmployee.setForeground(Color.RED);
        txtDeleteUsername = new JTextField();
        txtDeleteUsername.setFont(fieldFont);
        txtDeleteUsername.setPreferredSize(new Dimension(300, 35));

        btnDeleteEmployee = new JButton("Delete Employee");
        btnDeleteEmployee.setBackground(new Color(255, 69, 0));
        btnDeleteEmployee.setForeground(Color.WHITE);
        btnDeleteEmployee.setFont(new Font("Arial", Font.BOLD, 18));
        btnDeleteEmployee.setFocusPainted(false);

        gbcDelete.gridx = 0;
        gbcDelete.gridy = 0;
        bottomPanel.add(lblDeleteEmployee, gbcDelete);
        gbcDelete.gridx = 1;
        bottomPanel.add(txtDeleteUsername, gbcDelete);
        gbcDelete.gridx = 1;
        gbcDelete.gridy = 1;
        bottomPanel.add(btnDeleteEmployee, gbcDelete);

        // ------- Assemble Everything -------
        add(headerPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private int addRow(JPanel panel, GridBagConstraints gbc, int row, String label, JComponent field, Font labelFont) {
        gbc.gridx = 0;
        gbc.gridy = row;
        JLabel lbl = new JLabel(label);
        lbl.setFont(labelFont);
        lbl.setBackground(new Color(0, 150, 136));
        lbl.setOpaque(true);
        lbl.setForeground(Color.WHITE);
        lbl.setPreferredSize(new Dimension(200, 35));
        panel.add(lbl, gbc);

        gbc.gridx = 1;
        panel.add(field, gbc);

        return row + 1;
    }

    private JTextField createTextField(Font font, Dimension size) {
        JTextField tf = new JTextField();
        tf.setFont(font);
        tf.setPreferredSize(new Dimension(size.width, size.height + 10));
        return tf;
    }

    public void addSubmitButtonListener(ActionListener listener) {
        btnSubmit.addActionListener(listener);
    }

    public void addDeleteButtonListener(ActionListener listener) {
        btnDeleteEmployee.addActionListener(listener);
    }
}