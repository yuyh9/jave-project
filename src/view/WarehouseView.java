package view;
import java.awt.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.warehouse.Warehouse;
import java.util.List;

public class WarehouseView extends JFrame {
  private JPanel warehousePanel;
  private JTable warehouseTable;
  private DefaultTableModel warehouseTableModel;
  private JTextField idField, nameField, locationField,searchField;
  private JButton addButton, updateButton, deleteButton, searchButton, backButton;

  public WarehouseView() {
    setTitle("Warehouse Management");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(800, 700);
    setLocationRelativeTo(null);

    warehousePanel = new JPanel(new BorderLayout());
    String[] columnNames = {"Id", "Name", "Location"};
    warehouseTableModel = new DefaultTableModel(columnNames, 0);
    warehouseTable = new JTable(warehouseTableModel);
    warehouseTable.setDefaultEditor(Object.class, null);

    JScrollPane scrollPane = new JScrollPane(warehouseTable);
    warehousePanel.add(scrollPane, BorderLayout.CENTER);

    JPanel inputPanel = new JPanel(new BorderLayout());
    idField = new JTextField(20);
    nameField = new JTextField(20);
    locationField = new JTextField(20);
    searchField = new JTextField(20);

    JPanel leftInputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    leftInputPanel.add(new JLabel("Warehouse ID:"));
    leftInputPanel.add(idField);
    leftInputPanel.add(new JLabel("Warehouse Name:"));
    leftInputPanel.add(nameField);

    JPanel rightInputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    rightInputPanel.add(new JLabel("Location:"));
    rightInputPanel.add(locationField);
    rightInputPanel.add(new JLabel("Search by ID, Location:"));
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

    warehousePanel.add(inputButtonPanel, BorderLayout.NORTH);
    warehousePanel.add(scrollPane, BorderLayout.CENTER);
    warehousePanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

    add(warehousePanel);
  }
  public void updateWarehouses(List<Warehouse> warehouses) {
    warehouseTableModel.setRowCount(0);

    for (Warehouse warehouse : warehouses) {
      Object[] rowData = {
          warehouse.getWarehouseId(),
          warehouse.getWarehouseName(),
          warehouse.getLocation(),
      };

      warehouseTableModel.addRow(rowData);
    }
  }

  public String getWarehouseId() {
    return idField.getText();
  }

  public String getWarehouseName() {
    return nameField.getText();
  }

  public String getWarehouseLocation() {
    return locationField.getText();
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
  public JTable getWarehouseTable() {
    return warehouseTable;
  }
  public void clearTextField() {
    idField.setText("");
    nameField.setText("");
    locationField.setText("");
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