package view;
import java.awt.*;
import javax.swing.*;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import model.supplier.*;

public class SupplierView extends JFrame {

  private JPanel supplierPanel;
  private JTable supplierTable;
  private DefaultTableModel supplierTableModel;
  private JTextField idField, nameField, emailField, phoneField, searchField;
  private JButton addButton, updateButton, deleteButton,searchButton, backButton;


  public SupplierView() {
    setTitle("Supplier Management");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(800, 700);
    setLocationRelativeTo(null);

    supplierPanel = new JPanel(new BorderLayout());
    String[] columnNames = {"Supplier Id", "Supplier Name", "Email", "Phone"};
    supplierTableModel = new DefaultTableModel(columnNames, 0);
    supplierTable = new JTable(supplierTableModel);
    supplierTable.setDefaultEditor(Object.class, null);

    JScrollPane scrollPane = new JScrollPane(supplierTable);
    supplierPanel.add(scrollPane, BorderLayout.CENTER);

    JPanel inputPanel = new JPanel(new BorderLayout());
    idField = new JTextField(13);
    nameField = new JTextField(13);
    emailField = new JTextField(13);
    phoneField = new JTextField(15);
    searchField = new JTextField(20);
    JPanel leftInputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    leftInputPanel.add(new JLabel("Supplier ID:"));
    leftInputPanel.add(idField);
    leftInputPanel.add(new JLabel("Supplier Name:"));
    leftInputPanel.add(nameField);
    leftInputPanel.add(new JLabel("Email:"));
    leftInputPanel.add(emailField);
    JPanel rightInputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

    rightInputPanel.add(new JLabel("Phone:"));
    rightInputPanel.add(phoneField);
    rightInputPanel.add(new JLabel("Search by ID, Name:"));
    rightInputPanel.add(searchField);

    inputPanel.add(leftInputPanel, BorderLayout.NORTH);
    inputPanel.add(rightInputPanel, BorderLayout.SOUTH);

    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    addButton = new JButton("Add");
    deleteButton = new JButton("Remove");
    updateButton = new JButton("Update");
    searchButton = new JButton("Search");
    backButton = new JButton("Back");
    buttonPanel.add(addButton);
    buttonPanel.add(deleteButton);
    buttonPanel.add(updateButton);
    buttonPanel.add(searchButton);
    buttonPanel.add(backButton);

    JPanel inputButtonPanel = new JPanel(new BorderLayout());
    inputButtonPanel.add(inputPanel, BorderLayout.NORTH);
    inputButtonPanel.add(buttonPanel, BorderLayout.CENTER);

    supplierPanel.add(inputButtonPanel, BorderLayout.NORTH);
    supplierPanel.add(scrollPane, BorderLayout.CENTER);
    supplierPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

    add(supplierPanel);
  }

  public void updateSuppliers(List<Supplier> suppliers) {
    supplierTableModel.setRowCount(0);
    for (Supplier supplier : suppliers) {

      Object[] rowData = {
          supplier.getSupplierId(),
          supplier.getSupplierName(),
          supplier.getSupplierEmail(),
          supplier.getSupplierPhone(),

      };

      supplierTableModel.addRow(rowData);
    }
  }

  public String getSupplierId() {
    return idField.getText();
  }

  public String getSupplierName() {
    return nameField.getText();
  }

  public String getEmail() {
    return emailField.getText();
  }

  public String getPhone() {
    return phoneField.getText();
  }


  public String getSearchQuery() {
    return searchField.getText();
  }

  public JButton getAddButton() {
    return addButton;
  }

  public JButton getDeleteButton() {
    return deleteButton;
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

  public JTable getSupplierTable() {
    return supplierTable;
  }
  public void clearTextField() {
    idField.setText("");
    nameField.setText("");
    emailField.setText("");
    phoneField.setText("");
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
