package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginView extends JFrame {

  private final JPanel loginPanel;
  private final JTextField usernameField;
  private final JPasswordField passwordField;
  private final JButton loginButton;

  public LoginView() {
    setTitle("Management system - Login");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(400, 300);
    setLocationRelativeTo(null);

    loginPanel = new JPanel(new BorderLayout());
    JPanel inputPanel = new JPanel(new BorderLayout());
    usernameField = new JTextField(20);
    passwordField = new JPasswordField(20);
    JPanel leftInputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    leftInputPanel.add(new JLabel("Username:"));
    leftInputPanel.add(usernameField);
    JPanel rightInputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    rightInputPanel.add(new JLabel("Password:"));
    rightInputPanel.add(passwordField);
    inputPanel.add(leftInputPanel, BorderLayout.NORTH);
    inputPanel.add(rightInputPanel, BorderLayout.CENTER);

    //loginPanel.add(inputPanel, BorderLayout.CENTER);

    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    loginButton = new JButton("Login");
    buttonPanel.add(loginButton);

    JPanel inputButtonPanel = new JPanel(new BorderLayout());
    inputButtonPanel.add(inputPanel, BorderLayout.NORTH);
    inputButtonPanel.add(buttonPanel, BorderLayout.CENTER);

    loginPanel.add(inputButtonPanel, BorderLayout.CENTER);

    loginPanel.setBorder(BorderFactory.createEmptyBorder(80, 20, 15, 15));

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
