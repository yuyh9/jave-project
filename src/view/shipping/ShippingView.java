package view.shipping;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.shipping.Shipping;
import model.shipping.ShippingStatus;

public class ShippingView extends JFrame {

  private final JPanel shippingPanel;
  private final JTable shippingTable;
  private final DefaultTableModel shippingTableModel;
  private final JTextField idField;
  private final JTextField orderIdField;
  private final JTextField searchField;
  private final JButton addButton;
  private final JButton updateButton;
  private final JButton searchButton;
  private final JButton backButton;
  private final JButton orderIdSearchButton;
  private final JComboBox<ShippingStatus> statusComboBox;

  public ShippingView() {
    setTitle("Shipping Management");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(800, 700);
    setLocationRelativeTo(null);

    shippingPanel = new JPanel(new BorderLayout());
    String[] columnNames = {"shipping Id", "order Id", "customer Name", "shipping Address",
        "shipping Status"};
    shippingTableModel = new DefaultTableModel(columnNames, 0);
    shippingTable = new JTable(shippingTableModel);
    shippingTable.setDefaultEditor(Object.class, null);

    JScrollPane scrollPane = new JScrollPane(shippingTable);
    shippingPanel.add(scrollPane, BorderLayout.CENTER);

    JPanel inputPanel = new JPanel(new BorderLayout());
    idField = new JTextField(10);
    orderIdField = new JTextField(10);
    statusComboBox = new JComboBox<>(ShippingStatus.values());
    searchField = new JTextField(10);

    JPanel leftInputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    leftInputPanel.add(new JLabel("Shipping ID:"));
    leftInputPanel.add(idField);
    leftInputPanel.add(new JLabel("Order ID:"));
    leftInputPanel.add(orderIdField);
    orderIdSearchButton = new JButton("Search OrderId");
    leftInputPanel.add(orderIdSearchButton);

    JPanel rightInputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    rightInputPanel.add(new JLabel("Status:"));
    rightInputPanel.add(statusComboBox);
    rightInputPanel.add(new JLabel("Search by ID, Name:"));
    rightInputPanel.add(searchField);

    inputPanel.add(leftInputPanel, BorderLayout.NORTH);
    inputPanel.add(rightInputPanel, BorderLayout.SOUTH);

    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    addButton = new JButton("Add");
    updateButton = new JButton("Update Shipping Status");
    searchButton = new JButton("Search");
    backButton = new JButton("Back");
    buttonPanel.add(addButton);
    buttonPanel.add(updateButton);
    buttonPanel.add(searchButton);
    buttonPanel.add(backButton);

    JPanel inputButtonPanel = new JPanel(new BorderLayout());
    inputButtonPanel.add(inputPanel, BorderLayout.NORTH);
    inputButtonPanel.add(buttonPanel, BorderLayout.CENTER);

    shippingPanel.add(inputButtonPanel, BorderLayout.NORTH);
    shippingPanel.add(scrollPane, BorderLayout.CENTER);
    shippingPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

    add(shippingPanel);
  }

  public void updateShipping(List<Shipping> shippingList) {
    shippingTableModel.setRowCount(0);

    // Populate the table with shipping data
    for (Shipping shipping : shippingList) {
      String shippingId = shipping.getShippingId();
      String orderId = shipping.getAssociatedOrder().getOrderId();
      String customerName = shipping.getAssociatedOrder().getCustomer().getName();
      String customerAddress = shipping.getCustomerAddress();
      String status = shipping.getShippingStatus().toString();
      shippingTableModel.addRow(
          new Object[]{shippingId, orderId, customerName, customerAddress, status});
    }
  }

  public void updateShippingField(String supplierId) {
    orderIdField.setText(supplierId);
  }

  public JTextField getIdField() {
    return idField;
  }


  public JTextField getOrderIdField() {
    return orderIdField;
  }

  public ShippingStatus getShippingStatus() {
    return (ShippingStatus) statusComboBox.getSelectedItem();
  }

  public String getSearchQuery() {
    return searchField.getText();
  }

  public JButton getAddButton() {
    return addButton;
  }

  public JButton getUpdateButton() {
    return updateButton;
  }

  public JButton getSearchButton() {
    return searchButton;
  }

  public JButton getBackButton() {
    return backButton;
  }

  public JButton getOrderIdSearchButton() {
    return orderIdSearchButton;
  }

  public void clearTextField() {
    idField.setText("");
    orderIdField.setText("");
    searchField.setText("");
  }

  public void displayMessage(String s) {
    // Display an error message to the user, e.g., using JOptionPane
    JOptionPane.showMessageDialog(this, s, "Message", JOptionPane.ERROR_MESSAGE);
  }

}