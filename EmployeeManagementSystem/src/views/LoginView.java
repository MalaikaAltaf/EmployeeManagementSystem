package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginView extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleBox;
    private JButton loginBtn;
    private JButton exitBtn;
    private JCheckBox rememberMe;

    // Custom JPanel to draw the image scaled to fill the panel
    class ImagePanel extends JPanel {
        private Image image;

        public ImagePanel(String imagePath) {
            try {
                ImageIcon icon = new ImageIcon(imagePath);
                this.image = icon.getImage();
            } catch (Exception e) {
                System.out.println("Error loading image: " + e.getMessage());
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (image != null) {
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

    public LoginView() {
        setTitle("EMS Login");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Left panel with background image
        ImagePanel leftPanel = new ImagePanel("C:/Users/hp/Desktop/EMS/EmployeeManagementSystem/lib/6tarslogo.PNG");
        leftPanel.setPreferredSize(new Dimension(400, 0));

        // Right panel
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(null);
        rightPanel.setBackground(Color.WHITE);

        JLabel heading = new JLabel("EMS Login");
        heading.setFont(new Font("SansSerif", Font.BOLD, 22));
        heading.setForeground(new Color(0, 150, 136));
        heading.setBounds(300, 30, 200, 30);
        rightPanel.add(heading);

        JLabel roleLabel = new JLabel("Login As");
        roleLabel.setBounds(250, 90, 150, 25);
        rightPanel.add(roleLabel);

        roleBox = new JComboBox<>(new String[]{"Admin", "Employee"});
        roleBox.setBounds(250, 115, 300, 35);
        rightPanel.add(roleBox);

        JLabel userLabel = new JLabel("Username");
        userLabel.setBounds(250, 160, 150, 25);
        rightPanel.add(userLabel);

        usernameField = new JTextField();
        usernameField.setBounds(250, 185, 300, 35);
        rightPanel.add(usernameField);

        JLabel passLabel = new JLabel("Password");
        passLabel.setBounds(250, 230, 150, 25);
        rightPanel.add(passLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(250, 255, 300, 35);
        rightPanel.add(passwordField);

        rememberMe = new JCheckBox("Remember me");
        rememberMe.setBounds(250, 295, 150, 25);
        rememberMe.setBackground(Color.WHITE);
        rightPanel.add(rememberMe);

        JLabel forgotLabel = new JLabel("<html><u>Forgot password?</u></html>");
        forgotLabel.setForeground(new Color(0, 150, 136));
        forgotLabel.setBounds(400, 295, 150, 25);
        forgotLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        forgotLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(LoginView.this, "Please contact your administrator to reset your password.");
            }
        });
        rightPanel.add(forgotLabel);

        loginBtn = new JButton("Login");
        loginBtn.setBackground(new Color(0, 150, 136));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFocusPainted(false);
        loginBtn.setBounds(250, 330, 140, 40);
        rightPanel.add(loginBtn);

        exitBtn = new JButton("Exit");
        exitBtn.setBackground(Color.LIGHT_GRAY);
        exitBtn.setBounds(410, 330, 140, 40);
        rightPanel.add(exitBtn);

        JLabel noAccountLabel = new JLabel("Don't have an account? ");
        noAccountLabel.setBounds(250, 385, 200, 20);
        rightPanel.add(noAccountLabel);

        JLabel contactAdminLabel = new JLabel("<html><u>Contact administrator</u></html>");
        contactAdminLabel.setForeground(new Color(0, 150, 136));
        contactAdminLabel.setBounds(400, 385, 200, 20);
        contactAdminLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        contactAdminLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(LoginView.this, "Please contact admin@example.com");
            }
        });
        rightPanel.add(contactAdminLabel);

        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);
    }

    // Getters
    public JButton getLoginButton() {
        return loginBtn;
    }

    public JButton getExitButton() {
        return exitBtn;
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public String getRole() {
        return (String) roleBox.getSelectedItem();
    }

    public boolean isRememberMeSelected() {
        return rememberMe.isSelected();
    }
}
