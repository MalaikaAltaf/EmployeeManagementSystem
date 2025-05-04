import views.LoginView;
import controllers.LoginController;

import javax.swing.SwingUtilities;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginView view = new LoginView();
            new LoginController(view); // Attach controller logic
            view.setVisible(true);     // Show the login screen
        });
    }
}