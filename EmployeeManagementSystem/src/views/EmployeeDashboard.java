package views;

import javax.swing.*;
import java.awt.*;

public class EmployeeDashboard extends JFrame {

    private int employeeId;

    public EmployeeDashboard(int empId) {
        this.employeeId = empId;

        setTitle("Employee Dashboard");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel("👷 Welcome, Employee #" + empId, SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));

        add(welcomeLabel, BorderLayout.CENTER);
    }
}
