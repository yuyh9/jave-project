package view;
import javax.swing.*;
import java.awt.*;

public class HomePageView extends JFrame {
  private JButton productManagementButton;
  private JButton orderManagementButton;
  private JButton reportGeneratorButton;

  private JButton customerManagementButton;

  private JButton shippingManagementButton;
  private JButton supplierManagementButton;
  private JButton warehouseManagementButton;


  public HomePageView() {
    setTitle("Inventory Management System - Home");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(650, 400);

    setLocationRelativeTo(null); // Set frame location to the center of the screen

    JPanel homePanel = new JPanel();
    homePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10)); // Align components to the left and add spacing

    productManagementButton = new JButton("Product Management");
    productManagementButton.setPreferredSize(new Dimension(200, 100)); // Set button size
    homePanel.add(productManagementButton);

    orderManagementButton = new JButton("Order Management");
    orderManagementButton.setPreferredSize(new Dimension(200, 100)); // Set button size
    homePanel.add(orderManagementButton);

    customerManagementButton = new JButton("Customer Management");
    customerManagementButton.setPreferredSize(new Dimension(200, 100)); // Set button size
    homePanel.add(customerManagementButton);

    shippingManagementButton = new JButton("Shipping Management");
    shippingManagementButton.setPreferredSize(new Dimension(200, 100)); // Set button size
    homePanel.add(shippingManagementButton);

    supplierManagementButton = new JButton("Supplier Management");
    supplierManagementButton.setPreferredSize(new Dimension(200, 100)); // Set button size
    homePanel.add(supplierManagementButton);

    warehouseManagementButton = new JButton("Warehouse Management");
    warehouseManagementButton.setPreferredSize(new Dimension(200, 100)); // Set button size
    homePanel.add(warehouseManagementButton);

    reportGeneratorButton = new JButton("Report Generator");
    reportGeneratorButton.setPreferredSize(new Dimension(200, 100)); // Set button size
    homePanel.add(reportGeneratorButton);

    
    add(homePanel);
  }


  public JButton getProductManagementButton() {
    return productManagementButton;
  }
  public JButton getOrderManagementButton() {
    return orderManagementButton;
  }
  public JButton getCustomerManagementButton() {
    return customerManagementButton;
  }
  public JButton getShippingManagementButton() {
    return shippingManagementButton;
  }
  public JButton getSupplierManagementButton() {
    return supplierManagementButton;
  }
  public JButton getWarehouseManagementButton() {
    return warehouseManagementButton;
  }
  public JButton getReportGeneratorButton() {
    return reportGeneratorButton;
  }
}
