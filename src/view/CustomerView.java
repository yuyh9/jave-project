package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import model.customer.Customer;

public class CustomerView extends JFrame {

  private final JPanel customerPanel;
  private final JTable customerTable;
  private final JButton addButton, activeButton, updateButton,searchButton, backButton;
  private final DefaultTableModel customerTableModel;
  private final JTextField idField, nameField, contactField, addressField, searchField;

  public CustomerView() {
    setTitle("Customer Management");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(800, 700);
    setLocationRelativeTo(null);

    customerPanel = new JPanel(new BorderLayout());
    JPanel inputPanel = new JPanel(new BorderLayout());
    idField = new JTextField(10);
    nameField = new JTextField(10);
    contactField = new JTextField(10);
    addressField = new JTextField(20);
    searchField = new JTextField(20);

    JPanel leftInputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    leftInputPanel.add(new JLabel("Customer ID:"));
    leftInputPanel.add(idField);
    leftInputPanel.add(new JLabel("Customer Name:"));
    leftInputPanel.add(nameField);
    leftInputPanel.add(new JLabel("Email / Phone:"));
    leftInputPanel.add(contactField);

    JPanel rightInputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    rightInputPanel.add(new JLabel("Shipping Address:"));
    rightInputPanel.add(addressField);
    rightInputPanel.add(new JLabel("Search by ID, Name:"));
    rightInputPanel.add(searchField);

    inputPanel.add(leftInputPanel, BorderLayout.NORTH);
    inputPanel.add(rightInputPanel, BorderLayout.SOUTH);
    // Create the button panel
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    addButton = new JButton("Add Customer");
    activeButton = new JButton("Activate");
    updateButton = new JButton("Update Information");
    searchButton = new JButton("Search");
    backButton = new JButton("Back");
    buttonPanel.add(addButton);
    buttonPanel.add(activeButton);
    buttonPanel.add(updateButton);
    buttonPanel.add(searchButton);
    buttonPanel.add(backButton);

    // Create the table and scroll pane
    String[] columnNames = {"Customer ID", "Customer Name", "Email / Phone", "Shipping Address",
        "Active/Inactive"};
    customerTableModel = new DefaultTableModel(columnNames, 0);
    customerTable = new JTable(customerTableModel);
    customerTable.setDefaultEditor(Object.class, null);
    // Set the preferred width for the "Shipping Address" column
    TableColumn shippingAddressColumn = customerTable.getColumnModel().getColumn(3);
    shippingAddressColumn.setPreferredWidth(300); // Adjust the width as needed

    JScrollPane scrollPane = new JScrollPane(customerTable);

    customerPanel.add(scrollPane, BorderLayout.SOUTH);

    // Add the inputButtonPanel and scroll pane to the customerPanel
    JPanel inputButtonPanel = new JPanel(new BorderLayout());
    inputButtonPanel.add(inputPanel, BorderLayout.NORTH);
    inputButtonPanel.add(buttonPanel, BorderLayout.CENTER);

    customerPanel.add(inputButtonPanel, BorderLayout.NORTH);
    customerPanel.add(scrollPane, BorderLayout.CENTER);
    customerPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

    add(customerPanel);
  }

  public void updateCustomers(List<Customer> customers) {
    // Clear existing data from the table
    customerTableModel.setRowCount(0);

    // Add new rows to the table model
    for (Customer customer : customers) {
      Object[] rowData = {
          customer.getCustomerId(),
          customer.getName(),
          customer.getContactInfo(),
          customer.getShippingAddress(),
          customer.isAvailable(),
      };
      customerTableModel.addRow(rowData);
    }
  }

  public String getCustomerId() {
    // Retrieve the id from the text field
    return idField.getText();
  }

  public String getCustomerName() {
    return nameField.getText();
  }

  public String getContactInfo() {
    return contactField.getText();
  }

  public String getShippingAddress() {
    return addressField.getText();
  }

  public String getSearchQuery() {
    return searchField.getText();
  }

  public JButton getAddButton() {
    return addButton;
  }

  public JButton getActiveButton() {
    return activeButton;
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


  public JTable getCustomerTable() {
    return customerTable;
  }

  public void clearTextField() {
    idField.setText("");
    nameField.setText("");
    contactField.setText("");
    addressField.setText("");
    searchField.setText("");
  }

  public void displayMessage(String s) {
    // Display an error message to the user, e.g., using JOptionPane
    JOptionPane.showMessageDialog(this, s, "Message", JOptionPane.ERROR_MESSAGE);
  }

  public int displayConfirmDialog(String s) {
    // Display a confirmation dialog to the user and return the result
    return JOptionPane.showConfirmDialog(this, s, "Confirmation", JOptionPane.YES_NO_OPTION);
  }
}
