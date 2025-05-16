package strategies;

import views.EmployeeDashboardView;
import controllers.EmployeeDashboardController;

public class EmployeeStrategy implements RoleStrategy {
    private String employeeName;
    private int empId;

    public EmployeeStrategy(String employeeName, int empId) {
        this.employeeName = employeeName;
        this.empId = empId;
    }

    @Override
    public void showDashboard() {
        // Create the view first
        EmployeeDashboardView employeeDashboardView = new EmployeeDashboardView(employeeName);
        // Pass the view to the controller using the constructor that accepts the view
        EmployeeDashboardController controller = new EmployeeDashboardController(employeeName, empId, employeeDashboardView);
        // Set the view visible
        employeeDashboardView.setVisible(true);
    }
}
