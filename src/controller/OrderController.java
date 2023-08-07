package controller;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.customer.*;
import model.order.*;
import model.product.*;
import view.HomePageView;
import view.ordersview.CustomerIdSelectionView;
import view.ordersview.OrderListSelectionView;
import view.ordersview.OrderView;
import view.ordersview.ShowOrderDetails;
import view.productsview.ProductView;


public class OrderController {

  private final ProductManager productManager;
  private final OrderView orderView;
  private final HomePageView homePageView;
  private OrderManager orderManager;
  private final CustomerManager customerManager;
  private OrderListSelectionView orderListSelectionView;
  private CustomerIdSelectionView customerIdSelectionView;
  private ShowOrderDetails showOrderDetails;
  private final ProductView productView;


  public OrderController(HomePageView homePageView, OrderView orderView) {
    this.homePageView = homePageView;
    this.orderView = orderView;
    this.productManager = new ProductManager();
    this.customerManager = new CustomerManager(this.orderManager);
    this.orderManager = new OrderManager(this.productManager, this.customerManager);
    this.orderListSelectionView = new OrderListSelectionView(this.orderView, this.productManager);
    this.customerIdSelectionView = new CustomerIdSelectionView(this.orderView,
        this.customerManager);
    this.showOrderDetails = new ShowOrderDetails();
    this.productView = new ProductView();
    attachOrderButtonListeners();
    orderView.setVisible(true);
    loadOrders();
    updateOrderData();
  }

  private void attachOrderButtonListeners() {
    orderView.getBackButton().addActionListener(e -> backHomePage());
    orderView.getCreateButton().addActionListener(e -> addOrder());
    orderView.getViewButton().addActionListener(e -> viewOrder());
    orderView.getUpdateButton().addActionListener(e -> updateOrderStatus());
    orderView.getSelectButton().addActionListener(e -> orderListSelect());
    orderView.getCustomerIdButton().addActionListener(e -> customerIdSelect());
    orderView.getClearButton().addActionListener(e -> clearOrderList());

  }

  private void backHomePage() {
    homePageView.setVisible(true);
    orderView.dispose();
  }

  private void clearOrderList() {
    orderView.clearOrderListFields();
  }

  private void addOrder() {
    String orderId = orderView.getOrderIdField();
    String customerId = orderView.getCustomerIdField();
    String orderDateStr = orderView.getOrderDateField();
    OrderStatus status = orderView.getOrderStatus();
    String orderListString = orderView.getOrderList();

    // Get Customer object from customer ID
    Customer customer = customerManager.getCustomerById(customerId);

    // Parse order items from the order list string
    List<OrderItem> orderItems = parseOrderItems(orderListString);

    Order newOrder = new Order(orderId, customer, orderDateStr, status, orderItems);

    // Create the new Order in the system
    orderManager.createOrder(newOrder);

    // Only decrease quantity if the status is not 'CANCELLED'
    if (status != OrderStatus.CANCELLED) {
      // Decrease the quantity for the selected products
      for (OrderItem item : newOrder.getOrderItems()) {
        Product product = item.getProduct();
        int quantity = item.getQuantity();

        productManager.decreaseProductQuantity(product, quantity);
      }
    }

    updateOrderData();
    saveOrders();
    orderView.clearFields();
  }

  private List<OrderItem> parseOrderItems(String orderListString) {
    List<OrderItem> orderItems = new ArrayList<>();
    String[] orderItemStrings = orderListString.split(", ");

    for (String orderItemString : orderItemStrings) {
      String[] itemData = orderItemString.split(":");
      String productName = itemData[0];
      int quantity = Integer.parseInt(itemData[1]);

      // Fetch the product by name
      Product product = productManager.getProductByName(productName);

      // Create OrderItem with the product and quantity
      OrderItem orderItem = new OrderItem(product, quantity);
      orderItems.add(orderItem);
      orderView.clearOrderListFields();
    }

    return orderItems;
  }


  private void viewOrder() {
    String orderId = JOptionPane.showInputDialog(orderView, "Enter the order ID:");

    // Assuming you have a method to get order by ID in your OrderManager class
    Order order = orderManager.getOrderById(orderId);

    if (order != null) {
      ShowOrderDetails.showOrderDetails(order);
    } else {
      JOptionPane.showMessageDialog(orderView, "Order not found!");
    }
  }

  private void updateOrderStatus() {
    String orderId = JOptionPane.showInputDialog(orderView,
        "Enter the order ID to update status:");

    Order order = orderManager.getOrderById(orderId);

    if (order == null) {
      JOptionPane.showMessageDialog(orderView, "Order not found!");
      return;
    }

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
    if (order.getStatus() == OrderStatus.CANCELLED || order.getStatus() == OrderStatus.CONFIRMED) {
      JOptionPane.showMessageDialog(orderView, "Order status update not allowed for current status.");
      return;
    }

    boolean isUpdated = orderManager.updateOrderStatus(orderId, newStatus);

    if (isUpdated) {
      if (newStatus == OrderStatus.CONFIRMED) {
        // Decrease the quantity for the selected products
        for (OrderItem item : order.getOrderItems()) {
          Product product = item.getProduct();
          int quantity = item.getQuantity();

          System.out.println("Before decrease - Product: " + product.getProductName() + ", Order Quantity: " + quantity + ", Current Quantity: " + product.getQuantity());
          productManager.decreaseProductQuantity(product, quantity);
          item.updateQuantity(product.getQuantity());
          System.out.println("After decrease - Product: " + product.getProductName() + ", New Quantity: " + product.getQuantity());
          updateOrderData();
          saveOrders();
        }
      }

      if (newStatus == OrderStatus.CANCELLED) {
        // Decrease the quantity for the selected products
        for (OrderItem item : order.getOrderItems()) {
          Product product = item.getProduct();
          int quantity = item.getQuantity();
          productManager.increaseProductQuantity(product, quantity);
        }
      }
      updateOrderData();
      saveOrders();
      productView.updateProducts(productManager.getProducts());

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


  public void loadOrders() {
    orderManager.loadOrders();
    productManager.loadProducts();
    customerManager.loadCustomers();
  }

  public void saveOrders() {
    orderManager.saveOrders();
    productManager.saveProducts();
    customerManager.saveCustomers();
  }

  public void updateOrderData() {
    orderView.updateOrders(orderManager.getOrders());
    productView.updateProducts(productManager.getProducts());
  }

}