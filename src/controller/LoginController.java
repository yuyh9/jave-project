package controller;

import view.*;

public class LoginController {
  private LoginView loginView;
  private HomePageView homePageView;

  public LoginController(LoginView loginView, HomePageView homePageView) {
    this.loginView = loginView;
    this.homePageView = homePageView;
    attachLoginButtonListener();

  }
    private void attachLoginButtonListener() {
      loginView.getLoginButton().addActionListener(e -> {
        String username = loginView.getUsername();
        String password = loginView.getPassword();

        if (authenticate(username, password)) {
          // Successful login, perform necessary actions
          navigateToHomePage();
        } else {
          // Invalid credentials, display error message
          loginView.displayMessage("Invalid username or password");
        }
      });
    }

    private boolean authenticate(String username, String password) {
      // Replace this with your authentication logic
      return username.equals("1") && password.equals("1");
    }

  private void navigateToHomePage() {
    // Create the home page controller
    HomePageController homePageController = new HomePageController(homePageView);

    // Display the home page view
    homePageView.setVisible(true);
    loginView.dispose();
  }
}

