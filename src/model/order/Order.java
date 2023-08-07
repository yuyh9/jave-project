package model.order;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import model.customer.Customer;
import model.product.Product;
import model.product.ProductManager;

public class Order {

  private String orderId;
  private final Customer customer;
  private Date orderDate;
  private OrderStatus status;
  private final List<OrderItem> orderItems;
  private Product product;


  public Order(String orderId, Customer customer, String orderDate, OrderStatus status,
      List<OrderItem> orderItems) {
    this.orderId = orderId;
    this.customer = customer;
    this.status = status;
    this.orderItems = orderItems;

    try {
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
      this.orderDate = dateFormat.parse(orderDate);
    } catch (ParseException e) {
      e.printStackTrace();
      this.orderDate = null;
    }
  }

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public Customer getCustomer() {
    return customer;
  }


  public Date getOrderDate() {
    return orderDate;
  }

  public OrderStatus getStatus() {
    return status;
  }

  public void setStatus(OrderStatus status) {
    this.status = status;
  }

  public List<OrderItem> getOrderItems() {
    return orderItems;
  }


  public double getTotalAmount() {
    double totalAmount = 0.0;
    for (OrderItem orderItem : orderItems) {
      Product product = orderItem.getProduct();
      int quantity = orderItem.getQuantity();
      if (product != null) {
        double price = product.getPrice();
        totalAmount += price * quantity;
      }
    }
    return totalAmount;
  }

  @Override
  public String toString() {
    return "Order{" +
        "orderId=" + orderId +
        ", customer=" + customer +
        ", orderDate=" + orderDate +
        ", status=" + status +
        ", orderItems=" + orderItems +
        '}';
  }
}
