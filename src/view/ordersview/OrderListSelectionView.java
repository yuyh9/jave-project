package view.ordersview;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.order.Order;
import model.order.OrderItem;
import model.product.Product;
import model.product.ProductManager;

import java.util.List;
import model.supplier.Supplier;

public class OrderListSelectionView extends JFrame {
  private JPanel mainPanel;
  private JTable productTable;
  private JTextField searchField, quantityField;
  private DefaultTableModel productTableModel;
  private ProductManager productManager;
  private JButton selectButton, cancelButton, searchButton;
  private OrderView orderView;
  private List<OrderItem> orderItems;


  public OrderListSelectionView(OrderView orderView, ProductManager productManager) {
    this.orderView = orderView;
    this.productManager = productManager;
    this.orderItems = new ArrayList<>();
    initializeUI();
  }

  private void initializeUI() {
    setTitle("Product Selection");
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setSize(600, 400);
    setLocationRelativeTo(null);

    mainPanel = new JPanel(new BorderLayout());

    // Search field
    JPanel searchPanel = new JPanel(new FlowLayout());
    searchField = new JTextField(20);
    searchButton = new JButton("Search");
    searchPanel.add(new JLabel("Search by product name:"));
    searchPanel.add(searchField);
    searchPanel.add(searchButton);


    // Product Table
    String[] columnNames = {"Product ID", "Product Name", "Price", "Quantity"};
    productTableModel = new DefaultTableModel(columnNames, 0);
    productTable = new JTable(productTableModel);
    productTable.setDefaultEditor(Object.class, null);

    // Update table with products
    updateProductTable(productManager.getProducts());

    // Quantity Field
    JPanel quantityPanel = new JPanel(new FlowLayout());
    quantityField = new JTextField(5);
    quantityPanel.add(new JLabel("Quantity:"));
    quantityPanel.add(quantityField);

    // Buttons
    JPanel buttonPanel = new JPanel();
    selectButton = new JButton("Select");
    cancelButton = new JButton("Cancel");
    buttonPanel.add(selectButton);
    buttonPanel.add(cancelButton);

    mainPanel.add(searchPanel, BorderLayout.NORTH);
    mainPanel.add(new JScrollPane(productTable), BorderLayout.CENTER);
    mainPanel.add(quantityPanel, BorderLayout.EAST);
    mainPanel.add(buttonPanel, BorderLayout.SOUTH);

    add(mainPanel);

    selectButton.addActionListener(e -> {
      int selectedRow = productTable.getSelectedRow();
      if (selectedRow != -1) {
        String productId = (String) productTable.getValueAt(selectedRow, 0);
        int quantity = Integer.parseInt(quantityField.getText());
        // Fetch the selected product
        Product selectedProduct = productManager.getProductById(productId);
        // Create OrderItem with the product and quantity
        OrderItem orderItem = new OrderItem(selectedProduct, quantity);
        // Add OrderItem to the orderItems list in OrderView
        orderView.addOrderItem(orderItem);

        // Update the product field in OrderView
        orderView.updateProductField();

        dispose();
      } else {
        JOptionPane.showMessageDialog(
            OrderListSelectionView.this,
            "Please select a product.",
            "Product Selection",
            JOptionPane.WARNING_MESSAGE
        );
      }
    });

    // Add ActionListener to the cancelButton
    cancelButton.addActionListener(e -> dispose());
    searchButton.addActionListener(e -> {
      String searchQuery = searchField.getText();
      List<Product> searchResults = productManager.searchProductsByName(searchQuery);
      updateProductTable(searchResults);
    });

  }

  private void updateProductTable(List<Product> products) {
    // Clear existing data from the table
    productTableModel.setRowCount(0);

    // Add new rows to the table
    for (Product product : products) {
      if (product.isAvailable()) {
        productTableModel.addRow(new Object[] {product.getProductId(), product.getProductName(),
            product.getPrice(), product.getQuantity()});
      }
    }
  }

}
