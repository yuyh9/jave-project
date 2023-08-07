package view.ordersview;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.order.*;
import model.product.Product;


public class OrderView extends JFrame {

  private final JPanel orderPanel;
  private final JTable orderTable;
  private final JButton createButton, viewButton, updateButton, backButton, selectProductButton,
      customerIdButton, clearButton;
  private final JTextField orderIdField, customerIdField, orderDateField;
  private final DefaultTableModel orderTableModel;
  private final JComboBox<OrderStatus> statusComboBox;
  private final List<OrderItem> orderItems = new ArrayList<>();
  private final JTextArea orderListField;

  public OrderView() {

    setTitle("Order Management");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(850, 700);
    setLocationRelativeTo(null);

    orderPanel = new JPanel(new BorderLayout());
    String[] columnNames = {"Order ID", "Customer ID", "Order Date", "Status", "Order List"};
    orderTableModel = new DefaultTableModel(columnNames, 0);
    orderTable = new JTable(orderTableModel);
    orderTable.setDefaultEditor(Object.class, null);

    JScrollPane scrollPane = new JScrollPane(orderTable);
    orderPanel.add(scrollPane, BorderLayout.CENTER);

    JPanel inputPanel = new JPanel(new BorderLayout());
    statusComboBox = new JComboBox<>(OrderStatus.values());
    orderIdField = new JTextField(10);
    customerIdField = new JTextField(10);
    orderDateField = new JTextField(10);
    orderListField = new JTextArea(5, 20);
    orderListField.setEditable(true);
    orderListField.setLineWrap(true);
    JScrollPane orderListScrollPane = new JScrollPane(orderListField);

    JPanel leftInputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    leftInputPanel.add(new JLabel("Order ID:"));
    leftInputPanel.add(orderIdField);
    leftInputPanel.add(new JLabel("Customer ID:"));
    leftInputPanel.add(customerIdField);
    customerIdButton = new JButton("search customer ID");
    leftInputPanel.add(customerIdButton);
    JPanel rightInputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

    rightInputPanel.add(new JLabel("Order Date:"));
    rightInputPanel.add(orderDateField);

    rightInputPanel.add(new JLabel("Status:"));
    rightInputPanel.add(statusComboBox);
    rightInputPanel.add(new JLabel("Order List:"));
    rightInputPanel.add(orderListScrollPane);
    selectProductButton = new JButton("Select");
    rightInputPanel.add(selectProductButton);
    inputPanel.add(leftInputPanel, BorderLayout.NORTH);
    inputPanel.add(rightInputPanel, BorderLayout.SOUTH);

    // Create the button panel
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    createButton = new JButton("Create Order");
    viewButton = new JButton("view Order");
    updateButton = new JButton("Update Order Status");
    backButton = new JButton("Back");
    clearButton = new JButton("Clear OrderList");

    buttonPanel.add(createButton);
    buttonPanel.add(viewButton);
    buttonPanel.add(updateButton);
    buttonPanel.add(backButton);
    buttonPanel.add(clearButton);

    JPanel inputButtonPanel = new JPanel(new BorderLayout());
    inputButtonPanel.add(inputPanel, BorderLayout.NORTH);
    inputButtonPanel.add(buttonPanel, BorderLayout.CENTER);

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

      StringBuilder orderList = new StringBuilder();
      for (OrderItem orderItem : order.getOrderItems()) {
        Product product = orderItem.getProduct();
        if (product != null) {
          String productName = product.getProductName();
          int quantity = orderItem.getQuantity();

          String orderItemString = productName + ":" + quantity;
          orderList.append(orderItemString).append(", ");
        }
      }
      // Remove the trailing comma and space
      if (orderList.length() > 0) {
        orderList.setLength(orderList.length() - 2);
      }

      rowData.add(orderList.toString());
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

  public JButton getCustomerIdButton() {
    return customerIdButton;
  }

  public JButton getClearButton() {
    return clearButton;
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

  public String getOrderList() {
    return orderListField.getText();
  }

  public void setProductField(String product) {
    this.orderListField.setText(product);
  }

  public void addOrderItem(OrderItem orderItem) {
    orderItems.add(orderItem);
  }

  public void updateCustomerIdField(String customerId) {
    customerIdField.setText(customerId);
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

  public void clearOrderListFields() {
    orderItems.clear();
    orderListField.setText("");
  }

}


