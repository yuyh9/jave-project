import controller.LoginController;
import view.HomePageView;
import view.LoginView;

public class System {

  public static void main(String[] args) {
    LoginView loginView = new LoginView();
    HomePageView homePageView = new HomePageView();
    // Create the login controller
    LoginController loginController = new LoginController(loginView, homePageView);
    // Display the login view
    loginView.setVisible(true);
  }
}
