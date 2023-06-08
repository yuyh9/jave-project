package data;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import model.customer.Customer;
import model.customer.CustomerManager;
import model.order.Order;
import model.order.OrderItem;
import model.order.OrderManager;
import model.order.OrderStatus;
import model.product.Product;
import model.product.ProductManager;

public class OrderData {

  private final TxtFileHandler txtFileHandler;
  private final ProductManager productManager;
  private final CustomerManager customerManager;
  private OrderManager orderManager;

  public OrderData() {
    this.txtFileHandler = new TxtFileHandler();
    this.productManager = new ProductManager();
    this.customerManager = new CustomerManager(this.orderManager);
    productManager.loadProducts();
    customerManager.loadCustomers();
  }

  public List<Order> readOrderData() {
    List<String> data = txtFileHandler.readDataFromFile("orders.txt");
    List<Order> orderList = new ArrayList<>();

    for (String line : data) {
      String[] orderData = line.split(",");
      String orderId = orderData[0];
      String customerId = orderData[1];
      Customer customer = customerManager.getCustomerById(customerId);
      String orderDate = orderData[2];
      OrderStatus status = OrderStatus.valueOf(orderData[3]);
      List<OrderItem> orderItems = new ArrayList<>();

      // Parse the order items
      for (int i = 4; i < orderData.length; i++) {
        String[] itemData = orderData[i].split(":");
        String productName = itemData[0];
        int quantity = Integer.parseInt(itemData[1]);

        Product product = productManager.getProductByName(productName);
        OrderItem orderItem = new OrderItem(product, quantity);
        orderItems.add(orderItem);
      }

      Order order = new Order(orderId, customer, orderDate, status, orderItems);
      orderList.add(order);
    }

    return orderList;
  }

  public void writeOrderData(List<Order> orders) {
    List<String> orderData = new ArrayList<>();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    for (Order order : orders) {
      StringBuilder orderLine = new StringBuilder();
      orderLine.append(order.getOrderId()).append(",");
      orderLine.append(order.getCustomer().getCustomerId()).append(",");
      orderLine.append(dateFormat.format(order.getOrderDate())).append(","); // Format the date
      orderLine.append(order.getStatus()).append(",");

      for (OrderItem orderItem : order.getOrderItems()) {
        orderLine.append(orderItem.getProduct().getProductName()).append(":");
        orderLine.append(orderItem.getQuantity()).append(",");
      }

      orderLine.deleteCharAt(orderLine.length() - 1);

      orderData.add(orderLine.toString());
    }

    txtFileHandler.writeDataToFile("orders.txt", orderData);
  }
}
