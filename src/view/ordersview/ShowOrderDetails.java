package view.ordersview;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.*;
import model.order.Order;
import model.order.OrderItem;
import model.product.Product;

public class ShowOrderDetails {

  public static void showOrderDetails(Order order) {
    JFrame orderDetailsFrame = new JFrame("Order Details");
    orderDetailsFrame.setSize(400, 300);
    orderDetailsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    orderDetailsFrame.setLocationRelativeTo(null);

    JPanel mainPanel = new JPanel(new BorderLayout());
    mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    JPanel orderDetailsPanel = new JPanel(new GridLayout(7, 2, 10, 10));
    orderDetailsPanel.setBackground(Color.WHITE);
    orderDetailsPanel.add(new JLabel("Order ID:"));
    orderDetailsPanel.add(new JLabel(order.getOrderId()));
    orderDetailsPanel.add(new JLabel("Customer ID:"));
    orderDetailsPanel.add(new JLabel(order.getCustomer().getCustomerId()));
    orderDetailsPanel.add(new JLabel("Customer Name:"));
    orderDetailsPanel.add(new JLabel(order.getCustomer().getName()));
    orderDetailsPanel.add(new JLabel("Order Date:"));
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String formattedDate = dateFormat.format(order.getOrderDate());
    JLabel orderDateLabel = new JLabel(formattedDate);
    orderDateLabel.setFont(orderDateLabel.getFont().deriveFont(Font.BOLD));
    orderDetailsPanel.add(orderDateLabel);
    orderDetailsPanel.add(new JLabel("Order Status:"));
    orderDetailsPanel.add(new JLabel(order.getStatus().toString()));
    orderDetailsPanel.add(new JLabel("Order List:"));

    List<OrderItem> orderItems = order.getOrderItems();
    StringBuilder orderItemsText = new StringBuilder();

    for (OrderItem orderItem : orderItems) {
      Product product = orderItem.getProduct();
      if (product != null) {
        String productName = product.getProductName();
        int quantity = orderItem.getQuantity();
        orderItemsText.append(productName).append(": ").append(quantity).append("\n");
      }
    }

    JTextArea orderListTextArea = new JTextArea(orderItemsText.toString());
    orderListTextArea.setEditable(false);
    JScrollPane orderListScrollPane = new JScrollPane(orderListTextArea);

    orderDetailsPanel.add(orderListScrollPane);

    orderDetailsPanel.add(new JLabel("Total Amount:"));
    JLabel totalAmountLabel = new JLabel(String.format("%.2f", order.getTotalAmount()));
    totalAmountLabel.setFont(totalAmountLabel.getFont().deriveFont(Font.BOLD));
    orderDetailsPanel.add(totalAmountLabel);

    mainPanel.add(orderDetailsPanel, BorderLayout.CENTER);
    orderDetailsFrame.add(mainPanel);
    orderDetailsFrame.setVisible(true);
  }
}