package controller;

import java.util.List;
import model.customer.*;
import model.order.*;
import model.product.*;
import model.shipping.*;
import model.supplier.*;
import model.warehouse.*;
import view.BusinessReportView;
import view.HomePageView;

public class ReportController {
  private final BusinessReportView businessReportView;
  private final HomePageView homePageView;
  private final ProductManager productManager;
  private final CustomerManager customerManager;
  private final OrderManager orderManager;
  private final ShippingManager shippingManager;
  private final SupplierManager supplierManager;
  private final WarehouseManager warehouseManager;


  public ReportController(HomePageView homePageView, BusinessReportView businessReportView) {
    this.businessReportView = businessReportView;
    this.homePageView = homePageView;
    this.productManager = new ProductManager();
    this.productManager.loadProducts(); // Load products data
    this.orderManager = new OrderManager(this.productManager, null);
    this.orderManager.loadOrders();
    this.customerManager = new CustomerManager(this.orderManager);
    this.customerManager.loadCustomers();
    this.shippingManager = new ShippingManager(this.orderManager, this.customerManager);
    this.shippingManager.loadShipping();
    this.supplierManager = new SupplierManager();
    this.supplierManager.loadSuppliers();
    this.warehouseManager = new WarehouseManager(this.productManager);
    this.warehouseManager.loadWarehouses();
    attachButtonListeners();
  }


  private void attachButtonListeners() {
    businessReportView.getBackToHomePageButton().addActionListener(e -> backHomePage());
    businessReportView.getGenerateProductReportButton().addActionListener(e-> productReport());
    businessReportView.getGenerateOrderReportButton().addActionListener(e -> orderReport());
    businessReportView.getGenerateCustomerReportButton().addActionListener(e -> customerReport());
    businessReportView.getGenerateShippingReportButton().addActionListener(e -> shippingReport());
    businessReportView.getGenerateSupplierReportButton().addActionListener(e -> supplierReport());
    businessReportView.getGenerateWarehouseReportButton().addActionListener(e -> warehouseReport());
  }

  private void backHomePage() {
    homePageView.setVisible(true);
    businessReportView.dispose();
  }

  private void productReport() {
    // 1. Get the product data from the ProductManager
    List<Product> products = productManager.getProducts();

    // 2. Calculate summary information
    int totalProducts = products.size();
    int totalAvailableQuantity = products.stream().mapToInt(Product::getQuantity).sum();
    double totalValue = products.stream()
        .mapToDouble(product -> product.getPrice() * product.getQuantity()).sum();
    String formattedTotalValue = String.format("%.2f", totalValue);
    // 3. Format the report text
    String reportText = "Product Report:\n"
        + "Total Products: " + totalProducts + "\n"
        + "Total Available Quantity: " + totalAvailableQuantity + "\n"
        + "Total Value: 1" + formattedTotalValue + "\n";
    // 4. Display the report in the BusinessReportView
    businessReportView.displayReport(reportText);
  }
  private void orderReport() {
    List<Order> orders = orderManager.getOrders();

    int totalOrders = orders.size();
    int totalConfirmedOrders = (int) orders.stream()
        .filter(order -> order.getStatus() == OrderStatus.CONFIRMED)
        .count();
    double totalRevenue = orders.stream()
        .filter(order -> order.getStatus() == OrderStatus.CONFIRMED)
        .mapToDouble(Order::getTotalAmount)
        .sum();
    String formattedTotalRevenue = String.format("%.2f", totalRevenue);

    String reportText = "Order Report:\n"
        + "Total Orders: " + totalOrders + "\n"
        + "Total Confirmed Orders: " + totalConfirmedOrders + "\n"
        + "Total Revenue: $" + formattedTotalRevenue + "\n";

    businessReportView.displayReport(reportText);
  }


  private void customerReport() {
    List<Customer> customers = customerManager.getCustomers();

    int totalCustomers = customers.size();
    int totalActiveCustomers = (int) customers.stream()
        .filter(Customer::isAvailable)
        .count();

    String reportText = "Customer Report:\n"
        + "Total Customers: " + totalCustomers + "\n"
        + "Total Active Customers: " + totalActiveCustomers + "\n";

    businessReportView.displayReport(reportText);

  }

  private void shippingReport() {
    List<Shipping> shippings = shippingManager.getShipping();

    long totalPendingShipments = shippings.stream()
        .filter(shipping -> shipping.getShippingStatus() == ShippingStatus.PENDING)
        .count();
    long totalInTransitShipments = shippings.stream()
        .filter(shipping -> shipping.getShippingStatus() == ShippingStatus.IN_TRANSIT)
        .count();
    long totalDeliveredShipments = shippings.stream()
        .filter(shipping -> shipping.getShippingStatus() == ShippingStatus.DELIVERED)
        .count();
    long totalCancelledShipments = shippings.stream()
        .filter(shipping -> shipping.getShippingStatus() == ShippingStatus.CANCELLED)
        .count();

    String reportText = "Shipping Report:\n"
        + "Total Pending Shipments: " + totalPendingShipments + "\n"
        + "Total In-Transit Shipments: " + totalInTransitShipments + "\n"
        + "Total Delivered Shipments: " + totalDeliveredShipments + "\n"
        + "Total Cancelled Shipments: " + totalCancelledShipments + "\n";

    businessReportView.displayReport(reportText);
  }

  private void supplierReport() {
    List<Supplier> suppliers = supplierManager.getSuppliers();

    int totalSupplies = suppliers.size();
    int totalActiveSupplies = (int) suppliers.stream()
        .filter(Supplier::isAvailable)
        .count();

    String reportText = "Supplies Report:\n"
        + "Total Supplies: " + totalSupplies + "\n"
        + "Total Active Supplies: " + totalActiveSupplies + "\n";

    businessReportView.displayReport(reportText);


  }
  private void warehouseReport() {
    List<Warehouse> warehouses = warehouseManager.getWarehouses();

    int totalWarehouse = warehouses.size();

    String reportText = "Warehouse Report:\n"
        + "Total Warehouses: " + totalWarehouse + "\n";

    businessReportView.displayReport(reportText);
  }
}
