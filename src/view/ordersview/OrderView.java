package view.ordersview;
import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.order.*;
import model.product.*;

public class OrderView extends JFrame {

  private JPanel orderPanel;
  private JTable orderTable;
  private JButton createButton, viewButton, updateButton, backButton,selectProductButton;
  private JTextField orderIdField, customerIdField, orderDateField;
  private DefaultTableModel orderTableModel;
  private JComboBox<OrderStatus> statusComboBox;
  private List<OrderItem> orderItems = new ArrayList<>();

  private JTextArea orderListField;

  public OrderView() {

    setTitle("Order Management");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(800, 600);
    setLocationRelativeTo(null);

    orderPanel = new JPanel(new BorderLayout());

    // Removing statusField from input panel and add it to the button panel
    // because status is selected using ComboBox
    JPanel inputPanel = new JPanel(new GridLayout(6, 2, 10, 10));
    inputPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
    statusComboBox = new JComboBox<>(OrderStatus.values());

    orderIdField = new JTextField();
    customerIdField = new JTextField();
    orderDateField = new JTextField();
    orderListField = new JTextArea();
    //orderListField.setEditable(true);
    orderListField.setLineWrap(true);

    JScrollPane orderListScrollPane = new JScrollPane(orderListField);
    inputPanel.add(new JLabel("Order ID:"));
    inputPanel.add(orderIdField);
    inputPanel.add(new JLabel("Customer ID:"));
    inputPanel.add(customerIdField);
    inputPanel.add(new JLabel("Order Date:"));
    inputPanel.add(orderDateField);
    inputPanel.add(new JLabel("Status:"));
    inputPanel.add(statusComboBox);
    inputPanel.add(new JLabel("Order List:"));
    inputPanel.add(orderListScrollPane);selectProductButton = new JButton("Select Products");
    inputPanel.add(selectProductButton);

    // Create the button panel
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

    createButton = new JButton("Create Order");
    viewButton = new JButton("view Order");
    updateButton = new JButton("Update Order Status");
    backButton = new JButton("Back");

    buttonPanel.add(createButton);
    buttonPanel.add(viewButton);
    buttonPanel.add(updateButton);
    buttonPanel.add(backButton);


    // Create a panel to hold the input panel and button panel
    JPanel inputButtonPanel = new JPanel(new BorderLayout());
    inputButtonPanel.add(inputPanel, BorderLayout.NORTH);
    inputButtonPanel.add(buttonPanel, BorderLayout.CENTER);

    String[] columnNames = {"Order ID", "Customer ID", "Order Date", "Status", "Order List",
        "Total Amount"};
    orderTableModel = new DefaultTableModel(columnNames, 0);
    orderTable = new JTable(orderTableModel);
    orderTable.setDefaultEditor(Object.class, null);

    JScrollPane scrollPane = new JScrollPane(orderTable);

    // Add inputButtonPanel and scroll pane to the orderPanel
    orderPanel.add(inputButtonPanel, BorderLayout.NORTH);
    orderPanel.add(scrollPane, BorderLayout.CENTER);
    orderPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
    add(orderPanel);

  }

  public void updateOrders(List<Order> orders) {
    // Clear existing data from the table
    orderTableModel.setRowCount(0);

    // Add new rows to the table model
    for (Order order : orders) {
      List<Object> rowData = new ArrayList<>();
      rowData.add(order.getOrderId());
      rowData.add(order.getCustomer().getCustomerId());
      // Format the orderDate using SimpleDateFormat
      DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
      String formattedOrderDate = dateFormat.format(order.getOrderDate());
      rowData.add(formattedOrderDate);
      rowData.add(order.getStatus());

      double totalAmount = 0.0;
      StringBuilder orderList = new StringBuilder();

      for (OrderItem orderItem : order.getOrderItems()) {
        Product product = orderItem.getProduct();
        if (product != null) {
          String productName = product.getProductName();
          int quantity = orderItem.getQuantity();
          double price = product.getPrice();
          double itemTotal = price * quantity;
          totalAmount += itemTotal;

          String orderItemString = productName + ":" + quantity;
          orderList.append(orderItemString).append(", ");
        }
      }
      // Remove the trailing comma and space
      if (orderList.length() > 0) {
        orderList.setLength(orderList.length() - 2);
      }

      rowData.add(orderList.toString());
      rowData.add(String.format("%.2f", totalAmount)); // Format totalAmount with two decimal places

      orderTableModel.addRow(rowData.toArray());
    }
  }



  public JButton getCreateButton() {
    return createButton;
  }

  public JButton getViewButton() {
    return viewButton;
  }

  public JButton getUpdateButton() {
    return updateButton;
  }


  public JButton getBackButton() {
    return backButton;
  }

  public JButton getSelectButton() {
    return selectProductButton;
  }

  public String getOrderIdField() {
    return orderIdField.getText();
  }

  public String getCustomerIdField() {
    return customerIdField.getText();
  }

  public String getOrderDateField() {
    return orderDateField.getText();
  }

  public OrderStatus getOrderStatus() {
    return (OrderStatus) statusComboBox.getSelectedItem();
  }

  public void setProductField(String product) {
    this.orderListField.setText(product);
  }

  public void addOrderItem(OrderItem orderItem) {
    orderItems.add(orderItem);
  }
  public void updateProductField() {
    StringBuilder sb = new StringBuilder();
    for (OrderItem orderItem : orderItems) {
      sb.append(orderItem.getProduct().getProductName())
          .append(":")
          .append(orderItem.getQuantity())
          .append(", ");
    }
    String productsString = sb.toString();
    if (productsString.length() > 2) {
      productsString = productsString.substring(0, productsString.length() - 2);
    }
    orderListField.setText(productsString);
  }

  public void clearFields() {
    orderIdField.setText("");
    customerIdField.setText("");
    orderDateField.setText("");
    orderListField.setText("");
    statusComboBox.setSelectedIndex(0);
  }

}


