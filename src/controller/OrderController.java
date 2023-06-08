package controller;

import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import model.customer.CustomerManager;
import model.order.Order;
import model.order.OrderManager;
import model.order.OrderStatus;
import model.product.ProductManager;
import view.HomePageView;
import view.ordersview.CustomerIdSelectionView;
import view.ordersview.OrderListSelectionView;
import view.ordersview.OrderView;


public class OrderController {

  private final ProductManager productManager;
  private final OrderView orderView;
  private final HomePageView homePageView;
  private OrderManager orderManager;
  private final CustomerManager customerManager;
  private OrderListSelectionView orderListSelectionView;
  private final CustomerIdSelectionView customerIdSelectionView;

  public OrderController(HomePageView homePageView, OrderView orderView) {
    this.homePageView = homePageView;
    this.orderView = orderView;
    this.productManager = new ProductManager();
    this.customerManager = new CustomerManager(this.orderManager);
    this.orderManager = new OrderManager(this.productManager, this.customerManager);
    this.orderListSelectionView = new OrderListSelectionView(this.orderView, this.productManager);
    this.customerIdSelectionView = new CustomerIdSelectionView(this.orderView,
        this.customerManager);
    attachOrderButtonListeners();
    //attachButtonListeners();
    orderView.setVisible(true);
    loadOrders();
    updateOrderData();
  }

  private void attachOrderButtonListeners() {
    orderView.getBackButton().addActionListener(e -> backHomePage());
    orderView.getCreateButton().addActionListener(e -> addOrder());
    orderView.getViewButton().addActionListener(e -> viewOrder());
    orderView.getUpdateButton().addActionListener(e -> updateOrder());
    orderView.getSelectButton().addActionListener(e -> orderListSelect());
    orderView.getCustomerIdButton().addActionListener(e -> customerIdSelect());
  }

  private void backHomePage() {
    homePageView.setVisible(true);
    orderView.dispose();
  }

  private void addOrder() {

  }
  private void viewOrder() {
    String orderId = JOptionPane.showInputDialog(orderView, "Enter the order ID:");

    // Assuming you have a method to get order by ID in your OrderManager class
    Order order = orderManager.getOrderById(orderId);

    if (order != null) {
      showOrderDetails(order);
    } else {
      JOptionPane.showMessageDialog(orderView, "Order not found!");
    }

  }

  private void updateOrder() {
    String orderId = JOptionPane.showInputDialog(orderView,
        "Enter the order ID to update status:");

    OrderStatus[] statuses = OrderStatus.values();
    String[] statusOptions = new String[statuses.length];

    for (int i = 0; i < statuses.length; i++) {
      statusOptions[i] = statuses[i].name();
    }

    String newStatusInput = (String) JOptionPane.showInputDialog(orderView,
        "Choose the new order status:",
        "Order Status",
        JOptionPane.QUESTION_MESSAGE,
        null,
        statusOptions,
        statusOptions[0]
    );

    OrderStatus newStatus = OrderStatus.valueOf(newStatusInput);
    boolean isUpdated = orderManager.updateOrderStatus(orderId, newStatus);

    if (isUpdated) {
      JOptionPane.showMessageDialog(orderView, "Order status updated successfully!");
      updateOrderData(); // To reflect the changes in the GUI
    } else {
      JOptionPane.showMessageDialog(orderView, "Failed to update order status!");
    }
  }

  private void orderListSelect() {
    orderListSelectionView = new OrderListSelectionView(orderView, this.productManager);
    orderListSelectionView.setVisible(true);

  }
  private void customerIdSelect() {
    customerIdSelectionView.updateCustomerId(customerManager.getCustomers());
    customerIdSelectionView.setVisible(true);
  }

  private void showOrderDetails(Order order) {

  }

  public void loadOrders() {
    orderManager.loadOrders();
    productManager.loadProducts();
    customerManager.loadCustomers();
  }

  public void saveOrders() {
    orderManager.saveOrders();

  }

  public void updateOrderData() {
    orderView.updateOrders(orderManager.getOrders());
  }
}