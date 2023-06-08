package controller;


import java.awt.event.ActionListener;
import java.util.List;
import model.customer.Customer;
import model.customer.CustomerManager;
import model.order.OrderManager;
import model.product.ProductManager;
import model.shipping.Shipping;
import model.shipping.ShippingManager;
import view.HomePageView;
import view.shipping.OrderIdSelectionView;
import view.shipping.ShippingView;

public class ShippingController {
  private HomePageView homePageView;
  private ShippingView shippingView;
  private ShippingManager shippingManager;
  private OrderManager orderManager;
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
    this.orderIdSelectionView = new OrderIdSelectionView(this.shippingView, this.orderManager);

    attachButtonListeners();
    shippingView.setVisible(true);
    loadShipping();
    updateShippingData();
  }

  private void attachButtonListeners() {

    // back button listener
    ActionListener backButtonListener = e -> {
      homePageView.setVisible(true);
      shippingView.dispose();
    };

    // add button listener
    ActionListener addButtonListener = e -> {

    };
    ActionListener searchButtonListener = e -> {
      // Retrieve the search query from the search field
      String searchText = shippingView.getSearchQuery();
      if (searchText == null || searchText.trim().isEmpty()) {
        shippingView.displayMessage("Please enter a search query.");
        return;
      }
      // Perform the search and get the results and update in the view
      List<Shipping> searchResults = shippingManager.searchShipping(searchText);
      shippingView.updateShipping(searchResults);
    };



    ActionListener OrderIdSearchButtonLister = e -> {
      orderIdSelectionView.updateOrderIds(orderManager.getOrders());
      orderIdSelectionView.setVisible(true);
    };
    shippingView.getBackButton().addActionListener(backButtonListener);
    shippingView.getAddButton().addActionListener(addButtonListener);
    shippingView.getOrderIdSearchButton().addActionListener(OrderIdSearchButtonLister);
    shippingView.getSearchButton().addActionListener(searchButtonListener);

  }
  private void loadShipping() {
    shippingManager.loadShipping();
    orderManager.loadOrders();
  }

  private void saveShipping() {
    shippingManager.saveShipping();
  }

  private void updateShippingData() {
    shippingView.updateShipping(shippingManager.getShipping());
  }
}


