package controller;


import java.awt.event.ActionListener;
import view.CustomerView;
import view.HomePageView;
import view.SupplierView;
import view.WarehouseView;
import view.ordersview.OrderView;
import view.productsview.ProductView;
import view.shipping.ShippingView;

public class HomePageController {

  private final HomePageView homePageView;

  public HomePageController(HomePageView homePageView) {
    this.homePageView = homePageView;
    attachManagementButtonListeners();
  }

  private void attachManagementButtonListeners() {
    homePageView.getProductManagementButton().addActionListener(e -> navigateToProductManagementView());
    homePageView.getOrderManagementButton().addActionListener(e -> navigateToOrderManagementView());
    homePageView.getCustomerManagementButton().addActionListener(e -> navigateToCustomerManagementView());
    homePageView.getShippingManagementButton().addActionListener(e -> navigateToShippingManagementView());
    homePageView.getSupplierManagementButton().addActionListener(e -> navigateToSupplierManagementView());
    homePageView.getWarehouseManagementButton().addActionListener(e -> navigateToWarehouseManagementView());
  }

  private void navigateToProductManagementView() {
    // Create the product management view and controller
    ProductView productView = new ProductView();
    ProductController productController = new ProductController(homePageView,
        productView);

    // Hide the home page view
    homePageView.setVisible(false);

    // Show the product management view
    productView.setVisible(true);
  }

  private void navigateToOrderManagementView() {
    OrderView orderView = new OrderView(); // Pass the productManager
    OrderController orderController = new OrderController(homePageView,
        orderView);

    homePageView.setVisible(false);
    orderView.setVisible(true);
  }

  private void navigateToCustomerManagementView() {
    CustomerView customerView = new CustomerView();
    CustomersController customersController = new CustomersController(homePageView,
        customerView);

    homePageView.setVisible(false);
    customerView.setVisible(true);

  }

  private void navigateToShippingManagementView() {
    ShippingView shippingView = new ShippingView();
    ShippingController shippingController = new ShippingController(homePageView,
        shippingView);

    homePageView.setVisible(false);
    shippingView.setVisible(true);

  }

  private void navigateToSupplierManagementView() {
    SupplierView supplierView = new SupplierView();
    SupplierController supplierController = new SupplierController(homePageView,
        supplierView);

    homePageView.setVisible(false);
    supplierView.setVisible(true);

  }

  private void navigateToWarehouseManagementView() {
    WarehouseView warehouseView = new WarehouseView();
    WarehouseController warehouseController = new WarehouseController(homePageView, warehouseView);

    homePageView.setVisible(false);
    warehouseView.setVisible(true);
  }
}