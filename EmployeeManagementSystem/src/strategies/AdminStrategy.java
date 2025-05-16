package strategies;

import views.AdminDashboard;
import controllers.AdminDashboardController;

public class AdminStrategy implements RoleStrategy {
    @Override
    public void showDashboard() {
        AdminDashboard adminDashboard = new AdminDashboard();
        AdminDashboardController controller = new AdminDashboardController(adminDashboard);
        adminDashboard.setVisible(true);
    }
}
