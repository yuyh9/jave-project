package view.shipping;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import model.order.Order;
import model.order.OrderManager;


public class OrderIdSelectionView extends JFrame {

  private JTable orderIdTable;
  private DefaultTableModel orderIdTableModel;
  private JButton selectButton, cancelButton,searchButton;
  private JTextField searchField;
  private ShippingView shippingView;
  private OrderManager orderManager;

  public OrderIdSelectionView(ShippingView shippingView, OrderManager orderManager) {
    this.shippingView = shippingView;
    this.orderManager = orderManager;
    initializeUI();
  }

  private void initializeUI() {
    setTitle("Order ID Selection");
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setSize(400, 300);
    setLocationRelativeTo(null);

    JPanel mainPanel = new JPanel(new BorderLayout());

    // Create the order ID table
    String[] columnNames = {"Order ID", "Customer ID"};
    orderIdTableModel = new DefaultTableModel(columnNames, 0);
    orderIdTable = new JTable(orderIdTableModel);
    orderIdTable.setDefaultEditor(Object.class, null);
    JScrollPane scrollPane = new JScrollPane(orderIdTable);

    mainPanel.add(scrollPane, BorderLayout.CENTER);

    // Create the search panel
    JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    searchField = new JTextField(10);
    searchButton = new JButton("Search");
    searchPanel.add(new JLabel("Search by Order ID:"));
    searchPanel.add(searchField);
    searchPanel.add(searchButton);

    // Create the button panel
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    selectButton = new JButton("Select");
    cancelButton = new JButton("Cancel");
    buttonPanel.add(selectButton);
    buttonPanel.add(cancelButton);

    // Add the panels to the order ID panel
    mainPanel.add(searchPanel, BorderLayout.NORTH);
    mainPanel.add(buttonPanel, BorderLayout.SOUTH);

    add(mainPanel);

    // Add ActionListener to the selectButton
    selectButton.addActionListener(e -> {
      int selectedRow = orderIdTable.getSelectedRow();
      if (selectedRow != -1) {
        String orderId = (String) orderIdTable.getValueAt(selectedRow, 0);
        shippingView.updateShippingField(orderId);
        // Perform action with selected order ID
        dispose();
      } else {
        JOptionPane.showMessageDialog(
            OrderIdSelectionView.this,
            "Please select an Order ID.",
            "Order ID Selection",
            JOptionPane.WARNING_MESSAGE
        );
      }
    });

    // Add ActionListener to the cancelButton
    cancelButton.addActionListener(e -> dispose());

    // Add ActionListener to the searchField
    searchButton.addActionListener(e -> {
      String searchQuery = searchField.getText();
      List<Order> searchResults = orderManager.searchOrders(searchQuery);
      updateOrderIds(searchResults);
    });
  }

  public void updateOrderIds(List<Order> orderIds) {
    orderIdTableModel.setRowCount(0);
    for (Order order : orderIds) {
      Object[] rowData = {order.getOrderId(), order.getCustomer()};
      orderIdTableModel.addRow(rowData);
    }
  }
}
