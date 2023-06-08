package view;
import java.awt.*;
import javax.swing.*;

public class LoginView extends JFrame {
  private JPanel loginPanel;
  private JLabel usernameLabel;
  private JTextField usernameField;
  private JLabel passwordLabel;
  private JPasswordField passwordField;
  private JButton loginButton;

  public LoginView() {
    setTitle("Management system - Login");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(400, 300);
    setLocationRelativeTo(null);

    loginPanel = new JPanel();
    loginPanel.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.anchor = GridBagConstraints.LINE_START;
    gbc.insets = new Insets(10, 10, 5, 5);

    usernameLabel = new JLabel("Username:");
    loginPanel.add(usernameLabel, gbc);

    gbc.gridx = 1;
    gbc.gridy = 0;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.weightx = 1.0;
    gbc.insets = new Insets(10, 5, 5, 10);

    usernameField = new JTextField(20);
    loginPanel.add(usernameField, gbc);

    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.anchor = GridBagConstraints.LINE_START;
    gbc.fill = GridBagConstraints.NONE;
    gbc.weightx = 0.0;
    gbc.insets = new Insets(5, 10, 10, 5);

    passwordLabel = new JLabel("Password:");
    loginPanel.add(passwordLabel, gbc);

    gbc.gridx = 1;
    gbc.gridy = 1;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.weightx = 1.0;
    gbc.insets = new Insets(5, 5, 10, 10);

    passwordField = new JPasswordField(20);
    loginPanel.add(passwordField, gbc);

    gbc.gridx = 0;
    gbc.gridy = 2;
    gbc.gridwidth = 2;
    gbc.fill = GridBagConstraints.NONE;
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.weightx = 0.0;
    gbc.insets = new Insets(10, 10, 10, 10);

    loginButton = new JButton("Login");
    loginPanel.add(loginButton, gbc);

    add(loginPanel);
  }

  public JButton getLoginButton() {
    return loginButton;
  }

  public String getUsername() {
    return usernameField.getText();
  }

  public String getPassword() {
    return new String(passwordField.getPassword());
  }

  public void displayMessage(String message) {
    // Display an error message to the user, e.g., using JOptionPane
    JOptionPane.showMessageDialog(this, message, "Message", JOptionPane.ERROR_MESSAGE);
  }
}
