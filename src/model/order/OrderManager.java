package model.order;

import data.OrderData;
import data.ProductData;
import java.util.ArrayList;
import java.util.List;
import model.customer.CustomerManager;
import model.product.Product;
import model.product.ProductManager;


public class OrderManager {

  private List<Order> orderList;
  private final OrderData orderData;
  private ProductManager productManager;
  private final CustomerManager customerManager;

  public OrderManager(ProductManager productManager, CustomerManager customerManager) {
    this.orderList = new ArrayList<>();
    this.orderData = new OrderData();
    this.productManager = productManager;
    this.customerManager = customerManager;
  }

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

  public void loadOrders() {
    orderList = orderData.readOrderData();

  }

  public void saveOrders() {
    orderData.writeOrderData(orderList);
  }
}