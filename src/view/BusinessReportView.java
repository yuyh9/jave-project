package view;

import javax.swing.*;
import java.awt.*;
public class BusinessReportView extends JFrame {

  private final JButton generateProductReportButton;
  private final JButton generateOrderReportButton;
  private final JButton generateCustomerReportButton;
  private final JButton generateShippingReportButton;
  private final JButton generateSupplierReportButton;
  private final JButton generateWarehouseReportButton;
  private final JButton backToHomePageButton;
  private final JTextArea reportTextArea;


  public BusinessReportView() {
    setTitle("Business Reports");
    setSize(600, 500);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);

    JPanel mainPanel = new JPanel(new BorderLayout());

    JPanel buttonPanel = new JPanel(new GridLayout(7, 1));
    generateProductReportButton = new JButton("Generate Product Report");
    generateOrderReportButton = new JButton("Generate Order Report");
    generateCustomerReportButton = new JButton("Generate Customer Report");
    generateShippingReportButton = new JButton("Generate Shipping Report");
    generateSupplierReportButton = new JButton("Generate Supplier Report");
    generateWarehouseReportButton = new JButton("Generate Warehouse Report");
    backToHomePageButton = new JButton("Back to Home Page");

    buttonPanel.add(generateProductReportButton);
    buttonPanel.add(generateOrderReportButton);
    buttonPanel.add(generateCustomerReportButton);
    buttonPanel.add(generateShippingReportButton);
    buttonPanel.add(generateSupplierReportButton);
    buttonPanel.add(generateWarehouseReportButton);
    buttonPanel.add(backToHomePageButton);

    mainPanel.add(buttonPanel, BorderLayout.WEST);

    reportTextArea = new JTextArea();
    reportTextArea.setEditable(false);
    JScrollPane reportScrollPane = new JScrollPane(reportTextArea);
    mainPanel.add(reportScrollPane, BorderLayout.CENTER);
    add(mainPanel);

  }

  public JButton getGenerateProductReportButton() {
    return generateProductReportButton;
  }
  public JButton getGenerateOrderReportButton() {
    return generateOrderReportButton;
  }

  public JButton getGenerateCustomerReportButton() {
    return generateCustomerReportButton;
  }

  public JButton getGenerateShippingReportButton() {
    return generateShippingReportButton;
  }

  public JButton getGenerateSupplierReportButton() {
    return generateSupplierReportButton;
  }

  public JButton getGenerateWarehouseReportButton() {
    return generateWarehouseReportButton;
  }

  public JButton getBackToHomePageButton() {
    return backToHomePageButton;
  }

  public void displayReport(String reportText) {
    reportTextArea.setText(reportText);
  }

}
