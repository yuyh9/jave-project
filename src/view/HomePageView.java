package view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class HomePageView extends JFrame {

  private final JButton productManagementButton;
  private final JButton orderManagementButton;
  private final JButton customerManagementButton;
  private final JButton shippingManagementButton;
  private final JButton supplierManagementButton;
  private final JButton warehouseManagementButton;
  private final JButton businessReportButton;


  public HomePageView() {
    setTitle("Inventory Management System - Home");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(650, 450);

    setLocationRelativeTo(null); // Set frame location to the center of the screen

    JPanel homePanel = new JPanel();
    homePanel.setLayout(
        new FlowLayout(FlowLayout.LEFT, 10, 10)); // Align components to the left and add spacing

    productManagementButton = new JButton("Product Inventory");
    productManagementButton.setPreferredSize(new Dimension(200, 100)); // Set button size
    homePanel.add(productManagementButton);

    orderManagementButton = new JButton("Sales Orders");
    orderManagementButton.setPreferredSize(new Dimension(200, 100)); // Set button size
    homePanel.add(orderManagementButton);

    customerManagementButton = new JButton("Client Database");
    customerManagementButton.setPreferredSize(new Dimension(200, 100)); // Set button size
    homePanel.add(customerManagementButton);

    shippingManagementButton = new JButton("Shipment Control");
    shippingManagementButton.setPreferredSize(new Dimension(200, 100)); // Set button size
    homePanel.add(shippingManagementButton);

    supplierManagementButton = new JButton("Vendor Database");
    supplierManagementButton.setPreferredSize(new Dimension(200, 100)); // Set button size
    homePanel.add(supplierManagementButton);

    warehouseManagementButton = new JButton("Inventory Warehouse");
    warehouseManagementButton.setPreferredSize(new Dimension(200, 100)); // Set button size
    homePanel.add(warehouseManagementButton);

    businessReportButton = new JButton("Business Insights Report");
    businessReportButton.setPreferredSize(new Dimension(200, 100)); // Set button size
    homePanel.add(businessReportButton);
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

  public JButton getBusinessReportButton() {
    return businessReportButton;
  }
}
