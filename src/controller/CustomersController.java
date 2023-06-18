package controller;

import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import model.customer.Customer;
import model.customer.CustomerManager;
import model.order.OrderManager;
import model.product.ProductManager;
import view.CustomerView;
import view.HomePageView;

public class CustomersController {

  private final HomePageView homePageView;
  private final CustomerView customerView;
  private final OrderManager orderManager;
  private final JTable customerTable;
  private final ProductManager productManager;
  private CustomerManager customerManager;

  public CustomersController(HomePageView homePageView, CustomerView customerView) {
    this.homePageView = homePageView;
    this.customerView = customerView;
    this.productManager = new ProductManager();
    this.orderManager = new OrderManager(this.productManager);
    this.customerManager = new CustomerManager(this.orderManager);
    this.customerTable = customerView.getCustomerTable();
    attachCustomerButtonListeners();
    customerView.setVisible(true);
    loadCustomers();
    updateCustomerData();
  }

  private void attachCustomerButtonListeners() {
    customerView.getBackButton().addActionListener(e -> backHomePage());
    customerView.getAddButton().addActionListener(e -> addCustomer());
    customerView.getActiveButton().addActionListener(e -> activeCustomer());
    customerView.getUpdateButton().addActionListener(e -> updateCustomer());
    customerView.getSearchButton().addActionListener(e -> searchCustomer());
  }

  private void backHomePage() {
    homePageView.setVisible(true);
    customerView.dispose();
  }

  private void addCustomer() {
    // add button listener
    // Get the product details from the view
    String idString = customerView.getCustomerId();
    String name = customerView.getCustomerName();
    String email = customerView.getContactInfo();
    String shippingAddress = customerView.getShippingAddress();

    Customer existingCustomer = customerManager.getCustomerById(idString);

    if (existingCustomer != null && existingCustomer.isAvailable()) {
      // If a customer with the same ID exists and is available, display an error message
      customerView.displayMessage("Customer ID already exists!");
    } else {
      // If no customer with the same ID exists or the existing customer is not available, add the new customer
      Customer newCustomer = new Customer(idString, name, email, shippingAddress);
      customerManager.addCustomer(newCustomer);
      saveCustomers(); // save the update data
      updateCustomerData(); // update the table in the view
      customerView.clearTextField();
    }


  }

  private void activeCustomer() {
    int selectedRowIndex = customerTable.getSelectedRow();
    if (selectedRowIndex != -1) {
      String customerId = (String) customerTable.getValueAt(selectedRowIndex, 0);
      int dialogResult = customerView.displayConfirmDialog(
          "Are you sure you want to Activate/Deactivate this customer?");
      if (dialogResult == JOptionPane.YES_OPTION) {
        Customer customer = customerManager.getCustomerById(customerId);

        if (customer != null) {
          customerManager.activeCustomer(customer);
          saveCustomers();
          updateCustomerData();
        }
      }
    } else {
      customerView.displayMessage("Please select a customer to Activate or Deactivate.");
    }


  }

  private void updateCustomer() {
    int selectedRowIndex = customerTable.getSelectedRow();
    if (selectedRowIndex == -1) {
      // No row is selected
      customerView.displayMessage("Please select a customer to update.");
      return;
    }
    String idString = customerTable.getValueAt(selectedRowIndex, 0)
        .toString(); // Assuming the first column contains customerId

    // Check if the customer ID exists
    Customer existingCustomer = customerManager.getCustomerById(idString);
    String newName = customerView.getCustomerName();
    if (newName != null && !newName.isEmpty()) {
      existingCustomer.setName(newName);
    }

    String newContactInfo = customerView.getContactInfo();
    if (newContactInfo != null && !newContactInfo.isEmpty()) {
      existingCustomer.setContactInfo(newContactInfo);
    }

    String newAddress = customerView.getShippingAddress();
    if (newAddress != null && !newAddress.isEmpty()) {
      existingCustomer.setShippingAddress(newAddress);
    }
    customerManager.updateCustomer(existingCustomer);
    saveCustomers();
    updateCustomerData();
    customerView.clearTextField();

  }

  private void searchCustomer() {
    // Retrieve the search query from the search field
    String searchText = customerView.getSearchQuery();
    if (searchText == null || searchText.trim().isEmpty()) {
      customerView.displayMessage("Please enter a search query.");
      updateCustomerData();
      return;
    }
    // Perform the search and get the results and update in the view
    List<Customer> searchResults = customerManager.searchCustomers(searchText);
    customerView.updateCustomers(searchResults);
  }

  public void loadCustomers() {
    customerManager.loadCustomers();
  }

  public void saveCustomers() {
    customerManager.saveCustomers();
  }

  public void updateCustomerData() {
    // Update the customer table in the view
    customerView.updateCustomers(customerManager.getCustomers());
  }
}
