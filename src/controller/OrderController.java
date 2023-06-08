package controller;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import model.customer.CustomerManager;
import model.order.*;
import model.product.ProductManager;
import view.*;
import view.ordersview.OrderListSelectionView;
import view.ordersview.OrderView;


public class OrderController {

  private Order currentOrder;
  private ProductManager productManager;
  private OrderView orderView;
  private HomePageView homePageView;
  private OrderManager orderManager;
  private CustomerManager customerManager;
  private OrderListSelectionView orderListSelectionView;

  public OrderController(HomePageView homePageView, OrderView orderView) {
    this.homePageView = homePageView;
    this.orderView = orderView;
    this.productManager = new ProductManager();
    this.customerManager = new CustomerManager(this.orderManager);
    this.orderManager = new OrderManager(this.productManager,this.customerManager);
    this.orderListSelectionView = new OrderListSelectionView(this.orderView, this.productManager);


    attachButtonListeners();
    orderView.setVisible(true);
    loadOrders();
    updateOrderData();
  }

  private void attachButtonListeners() {
    ActionListener backButtonListener = e -> {
      homePageView.setVisible(true);
      orderView.dispose();
    };


    ActionListener createButtonListener = e -> {

    };

    ActionListener viewOrderButtonListener = e -> {
      String orderId = JOptionPane.showInputDialog(orderView, "Enter the order ID:");

      // Assuming you have a method to get order by ID in your OrderManager class
      Order order = orderManager.getOrderById(orderId);

      if (order != null) {
        showOrderDetails(order);
      } else {
        JOptionPane.showMessageDialog(orderView, "Order not found!");
      }

    };

    ActionListener updateButtonListener = e -> {
      String orderId = JOptionPane.showInputDialog(orderView, "Enter the order ID to update status:");

      OrderStatus[] statuses = OrderStatus.values();
      String[] statusOptions = new String[statuses.length];

      for(int i = 0; i < statuses.length; i++){
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
      boolean isUpdated = orderManager.updateOrderStatus(orderId,newStatus);

      if (isUpdated) {
        JOptionPane.showMessageDialog(orderView, "Order status updated successfully!");
        updateOrderData(); // To reflect the changes in the GUI
      } else {
        JOptionPane.showMessageDialog(orderView, "Failed to update order status!");
      }
    };

    ActionListener selectButtonActionListener = e -> {
      orderListSelectionView = new OrderListSelectionView(orderView, this.productManager);
      orderListSelectionView.setVisible(true);
    };

    orderView.getBackButton().addActionListener(backButtonListener);
    orderView.getCreateButton().addActionListener(createButtonListener);
    orderView.getViewButton().addActionListener(viewOrderButtonListener);
    orderView.getUpdateButton().addActionListener(updateButtonListener);
    orderView.getSelectButton().addActionListener(selectButtonActionListener);



    // Add other button listeners and logic here
  }

  private void showOrderDetails(Order order) {

  }


  private void loadOrders() {
    orderManager.loadOrders();
    productManager.loadProducts();
  }

  private void saveOrders() {
    orderManager.saveOrders();

  }
  private void updateOrderData() {
    orderView.updateOrders(orderManager.getOrders());
  }
}
