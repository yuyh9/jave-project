package view.productsview;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.product.*;

public class ProductView extends JFrame {
  private JPanel productPanel;
  private JTable productTable;
  private JButton addButton, updateButton, deleteButton, backButton, searchButton;
  private DefaultTableModel  productTableModel;
  private JTextField idField, priceField,nameField, quantityField,searchField;
  private JComboBox<ProductCategory> categoryComboBox;
  private JButton supplierSearchButton, warehouseSearchButton;
  private JTextField supplierField, warehouseField;


  public ProductView() {

    setTitle("Product Management");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(800, 700);
    setLocationRelativeTo(null);


    productPanel = new JPanel(new BorderLayout());
    String[] columnNames = {"Id", "Product Name", "Price", "Quantity", "Category", "Supplier ID",
        "Warehouse ID"};
    productTableModel = new DefaultTableModel(columnNames, 0);
    // Create the productTable using the productTableModel
    productTable = new JTable(productTableModel);
    productTable.setDefaultEditor(Object.class, null);

    JScrollPane scrollPane = new JScrollPane(productTable);
    productPanel.add(scrollPane, BorderLayout.CENTER);
    productPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

    // Create the input panel
    JPanel inputPanel = new JPanel(new GridLayout(0, 2, 5, 10));
    inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    idField = new JTextField();
    priceField = new JTextField();
    nameField = new JTextField();
    quantityField = new JTextField();
    searchField = new JTextField();
    supplierField = new JTextField();
    warehouseField = new JTextField();
    supplierSearchButton = new JButton("Search");
    warehouseSearchButton = new JButton("Search");
    categoryComboBox = new JComboBox<>(ProductCategory.values());

    inputPanel.add(createFieldPanel("Product ID:", idField));
    inputPanel.add(createFieldPanel("Product Name:", nameField));
    inputPanel.add(createFieldPanel("Product Price:", priceField));
    inputPanel.add(createFieldPanel("Product Quantity:", quantityField));
    inputPanel.add(createComboBoxPanel("Product Category:", categoryComboBox));
    inputPanel.add(createFieldPanel("Supplier ID:", supplierField, supplierSearchButton));
    inputPanel.add(createFieldPanel("Warehouse ID:", warehouseField, warehouseSearchButton));
    inputPanel.add(createFieldPanel("Search by Name:", searchField));



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

    productPanel.add(inputButtonPanel, BorderLayout.NORTH);
    productPanel.add(scrollPane, BorderLayout.CENTER);

    add(productPanel);

  }

  private JPanel createFieldPanel(String label, JTextField textField) {
    JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    panel.add(new JLabel(label));
    textField.setColumns(15);
    panel.add(textField);
    return panel;
  }

  private JPanel createFieldPanel(String label, JTextField textField, JButton searchButton) {
    JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    panel.add(new JLabel(label));
    textField.setColumns(10);
    panel.add(textField);
    panel.add(searchButton);

    return panel;
  }

  private JPanel createComboBoxPanel(String label, JComboBox<?> comboBox) {
    JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    panel.add(new JLabel(label));
    panel.add(comboBox);
    return panel;
  }

  public void updateProducts(List<Product> products) {
    productTableModel.setRowCount(0);

    for (Product product : products) {
      if (product.isAvailable()) {
        Object[] rowData = {
            product.getProductId(),
            product.getProductName(),
            product.getPrice(),
            product.getQuantity(),
            product.getProductCategory(),
            product.getSupplier().getSupplierId(),
            product.getWarehouse().getWarehouseId(),
            product.isAvailable(),
        };
        productTableModel.addRow(rowData);
      }
    }
  }

  public String getId() {
    // Retrieve the ID from the text field
    return idField.getText();
  }

  public String getName() {
    // Retrieve the name from the text field
    return nameField.getText();
  }

  public String getPrice() {
    return priceField.getText();
  }

  public String getQuantity() {
    return quantityField.getText();
  }

  public String getSupplier() {
    return supplierField.getText();
  }

  public String getWareHouse() {
    return warehouseField.getText();
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

  public JButton getBackButton() {
    return backButton;
  }

  public JButton getSearchButton() {
    return searchButton;
  }

  public JButton getSupplierSearchButton(){
    return supplierSearchButton;
  }

  public JButton getWarehouseSearchButton(){
    return warehouseSearchButton;
  }

  public void updateSupplierField(String supplierId, String supplierName) {
    supplierField.setText(supplierId);
  }

  public void updateWarehouseField(String warehouseLocation) {
    warehouseField.setText(warehouseLocation);
  }

  public void setSupplierSelection(String supplierId, String supplierName) {
    supplierField.setText(supplierId);
  }

  public void setWarehouseSelection(String warehouseLocation) {
    warehouseField.setText(warehouseLocation);
  }


  public void clearTextField() {
    idField.setText("");
    nameField.setText("");
    priceField.setText("");
    quantityField.setText("");
    supplierField.setText("");
    warehouseField.setText("");
  }
  public JTable getProductTable() {
    return productTable;
  }
  public void clearSearchKeyword() {
    searchField.setText("");
  }
  public void updateSupplierField(String supplierId) {
    supplierField.setText(supplierId);
  }

  public ProductCategory getCategory() {
    return (ProductCategory) categoryComboBox.getSelectedItem();
  }

  public void displayErrorMessage(String s) {
    // Display an error message to the user, e.g., using JOptionPane
    JOptionPane.showMessageDialog(this, s, "Message", JOptionPane.ERROR_MESSAGE);
  }


  public int displayConfirmDialog(String s) {
    // Display a confirmation dialog to the user and return the result
    return JOptionPane.showConfirmDialog(this, s, "Confirmation", JOptionPane.YES_NO_OPTION);
  }

}
