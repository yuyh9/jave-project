package controller;


import java.util.List;
import javax.swing.JOptionPane;
import model.customer.CustomerManager;
import model.order.Order;
import model.order.OrderManager;
import model.product.ProductManager;
import model.shipping.Shipping;
import model.shipping.ShippingManager;
import model.shipping.ShippingStatus;
import view.HomePageView;
import view.shipping.OrderIdSelectionView;
import view.shipping.ShippingView;

public class ShippingController {

  private final HomePageView homePageView;
  private final ShippingView shippingView;
  private final ShippingManager shippingManager;
  private final OrderManager orderManager;
  private CustomerManager customerManager;
  private ProductManager productManager;
  private OrderIdSelectionView orderIdSelectionView;

  public ShippingController(HomePageView homePageView, ShippingView shippingView) {
    this.homePageView = homePageView;
    this.shippingView = shippingView;
    this.productManager = new ProductManager();
    this.orderManager = new OrderManager(this.productManager, this.customerManager);
    this.customerManager = new CustomerManager(this.orderManager);
    this.shippingManager = new ShippingManager(this.orderManager, this.customerManager);
    this.orderIdSelectionView = new OrderIdSelectionView(this.shippingView, this.orderManager,
        this.shippingManager);

    attachShippingButtonListeners();
    shippingView.setVisible(true);
    loadShipping();
    updateShippingData();
  }

  private void attachShippingButtonListeners() {
    shippingView.getBackButton().addActionListener(e -> backHomePage());
    shippingView.getAddButton().addActionListener(e -> addShipping());
    shippingView.getUpdateButton().addActionListener(e -> updateShipping());
    shippingView.getSearchButton().addActionListener(e -> searchShipping());
    shippingView.getOrderIdSearchButton().addActionListener(e -> OrderIdSearch());
  }

  private void backHomePage() {
    homePageView.setVisible(true);
    shippingView.dispose();
  }

  private void addShipping() {
    String shippingId = shippingView.getIdField().getText();
    String orderId = shippingView.getOrderIdField().getText();
    ShippingStatus status = shippingView.getShippingStatus();

    Order associatedOrder = orderManager.getOrderById(orderId);
    String customerName = associatedOrder.getCustomer().getName();
    String shippingAddress = associatedOrder.getCustomer().getShippingAddress();

    Shipping shipping = new Shipping(shippingId, associatedOrder, shippingAddress, status);
    shippingManager.getShipping().add(shipping);
    saveShipping();
    updateShippingData();
    shippingView.clearTextField();
    shippingView.displayMessage(
        "Shipping added successfully!" +
            "\nCustomer Name: " + customerName +
            "\nShipping Address: " + shippingAddress);


  }

  private void updateShipping() {
    String shippingId = JOptionPane.showInputDialog(shippingView, "Enter the shipping ID"
        + " to update status:");

    ShippingStatus[] statuses = ShippingStatus.values();
    String[] statusOptions = new String[statuses.length];

    for (int i = 0; i < statuses.length; i++) {
      statusOptions[i] = statuses[i].name();
    }

    String newStatusInput = (String) JOptionPane.showInputDialog(shippingView,
        "Choose the new shipping status:",
        "Shipping Status",
        JOptionPane.QUESTION_MESSAGE,
        null,
        statusOptions,
        statusOptions[0]
    );

    ShippingStatus newStatus = ShippingStatus.valueOf(newStatusInput);
    boolean isUpdated = shippingManager.updateShippingStatus(shippingId, newStatus);

    if (isUpdated) {
      shippingView.displayMessage("Shipping status updated successfully!");
      updateShippingData(); // To reflect the changes in the GUI
    } else {
      shippingView.displayMessage("Failed to update Shipping status!");
    }
  }

  private void searchShipping() {
    // Retrieve the search query from the search field
    String searchText = shippingView.getSearchQuery();
    if (searchText == null || searchText.trim().isEmpty()) {
      shippingView.displayMessage("Please enter a search query.");
      updateShippingData();
      return;
    }
    // Perform the search and get the results and update in the view
    List<Shipping> searchResults = shippingManager.searchShipping(searchText);
    shippingView.updateShipping(searchResults);
  }

  private void OrderIdSearch() {
    orderIdSelectionView.updateOrderIds(orderManager.getOrders());
    orderIdSelectionView.setVisible(true);
  }

  public void loadShipping() {
    shippingManager.loadShipping();
    orderManager.loadOrders();
  }

  public void saveShipping() {
    shippingManager.saveShipping();
  }

  public void updateShippingData() {
    shippingView.updateShipping(shippingManager.getShipping());
  }
}

