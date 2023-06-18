package model.order;

import data.OrderData;
import java.util.ArrayList;
import java.util.List;
import model.product.Product;
import model.product.ProductManager;


public class OrderManager {

  private List<Order> orderList;
  private final OrderData orderData;
  private ProductManager productManager;


  public OrderManager(ProductManager productManager) {
    this.orderList = new ArrayList<>();
    this.orderData = new OrderData();
    this.productManager = productManager;  }

  public List<Order> getOrders() {
    return orderList;
  }


  public Order getOrderById(String orderId) {
    // Retrieve and return the order with the specified order ID
    for (Order order : orderList) {
      if (order.getOrderId().equals(orderId)) { // change here
        return order;
      }
    }
    return null; // Order not found
  }

  public void createOrder(Order order) {
    // Create a new order and add it to the list
    orderList.add(order);
  }

  public List<Order> searchOrders(String searchText) {
    List<Order> results = new ArrayList<>();
    String searchLower = searchText.toLowerCase();
    for (Order order : orderList) {
      if (order.getOrderId().toLowerCase().contains(searchLower)) {
        results.add(order);
      }
    }
    return results;
  }

  public boolean updateOrderStatus(String orderId, OrderStatus status) {
    // Update the status of the specified order
    for (Order order : orderList) {
      if (order.getOrderId().equals(orderId)) { // change here
        order.setStatus(status);
        return true;
      }
    }
    return false; // Order not found
  }
  public void adjustQuantityBasedOnStatus(Order order) {
    OrderStatus status = order.getStatus();

    if (status == OrderStatus.PENDING) {
      // Decrease the quantity for the selected products
      for (OrderItem item : order.getOrderItems()) {
        Product product = item.getProduct();
        int quantity = item.getQuantity();

        productManager.decreaseProductQuantity(product, quantity);
      }
    } else if (status == OrderStatus.CANCELLED) {
      // Add back the quantity for the selected products
      for (OrderItem item : order.getOrderItems()) {
        Product product = item.getProduct();
        int quantity = item.getQuantity();
        System.out.println("Increasing quantity for product: " + product.getProductName() + ", Quantity: " + quantity);

        productManager.increaseProductQuantity(product, quantity);
      }
    }
  }

  public void loadOrders() {
    orderList = orderData.readOrderData();

  }

  public void saveOrders() {
    orderData.writeOrderData(orderList);
  }
}