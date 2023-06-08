package controller;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import model.customer.Customer;
import model.customer.CustomerManager;
import model.order.OrderManager;
import model.product.ProductManager;
import view.HomePageView;
import view.CustomerView;

public class CustomersController {
  private HomePageView homePageView;
  private CustomerView customerView;
  private OrderManager orderManager;
  private CustomerManager customerManager;
  private JTable customerTable;
  private ProductManager productManager;

  public CustomersController(HomePageView homePageView, CustomerView customerView) {
    this.homePageView = homePageView;
    this.customerView = customerView;
    this.productManager = new ProductManager();
    this.orderManager = new OrderManager(this.productManager,this.customerManager);
    this.customerManager = new CustomerManager(this.orderManager);
    this.customerTable = customerView.getCustomerTable();
    attachCustomerButtonListeners();
    customerView.setVisible(true);
    loadCustomers();
    updateCustomerData();
  }

  private void attachCustomerButtonListeners() {
    // back button listener
    ActionListener backButtonListener = e -> {
      homePageView.setVisible(true);
      customerView.dispose();
    };

    ActionListener addButtonListener = e -> {
    // Get the product details from the view
      String idString = customerView.getCustomerId();
      String name = customerView.getCustomerName();
      String email = customerView.getContactInfo();
      String shippingAddress = customerView.getShippingAddress();

      // Check if the customer ID already exists
      if (customerManager.getCustomerById(idString) != null) {
        customerView.displayMessage("Customer ID already exists!");
        return; // Exit the listener
      }
      // Create a new Customer object and add the new customer to the customer manager
      Customer newCustomer = new Customer(idString, name, email, shippingAddress);
      customerManager.addCustomer(newCustomer);
      saveCustomers(); // save the update data
      updateCustomerData(); // update the table in the view
      customerView.clearTextField();
    };

    ActionListener removeButtonListener = e -> {
      int selectedRowIndex = customerTable.getSelectedRow();
      if (selectedRowIndex != -1) {
        String customerId = (String) customerTable.getValueAt(selectedRowIndex, 0);
        int dialogResult = customerView.displayConfirmDialog("Are you sure you want to "
            + "remove this customer?");
        if (dialogResult == JOptionPane.YES_OPTION) {
          Customer customer = customerManager.getCustomerById(customerId);

          if (customer != null) {
            customerManager.removeCustomer(customer);
            saveCustomers();
            updateCustomerData();
          }
        }
      }
      else {
        customerView.displayMessage("No customer is selected. Please select a supplier to "
            + "remove.");
      }
    };

    ActionListener updateButtonListener = e -> {
      int selectedRowIndex = customerTable.getSelectedRow();
      if (selectedRowIndex == -1) {
        // No row is selected
        customerView.displayMessage("Please select a customer to update.");
      }
      String idString = customerTable.getValueAt(selectedRowIndex, 0).toString(); // Assuming the first column contains customerId


      // Check if the customer ID exists
      Customer existingCustomer = customerManager.getCustomerById(idString);
      String newName = customerView.getCustomerName();
      if (newName != null && !newName.isEmpty()) {
        // Update the name of the customer only if the input is not empty
        existingCustomer.setName(newName);
      }

      String newContactInfo = customerView.getContactInfo();
      if (newContactInfo != null && !newContactInfo.isEmpty()) {
        // Update the contact info of the customer only if the input is not empty
        existingCustomer.setContactInfo(newContactInfo);
      }

      String newAddress = customerView.getShippingAddress();
      if (newAddress != null && !newAddress.isEmpty()) {
        // Update the address of the customer only if the input is not empty
        existingCustomer.setShippingAddress(newAddress);
      }
      // Update the customer in the customer manager
      customerManager.updateCustomer(existingCustomer);
      // Save the updated customer data
      saveCustomers();
      // Update the customer table in the view
      updateCustomerData();
      customerView.clearTextField();
    };



    ActionListener searchButtonListener = e -> {
      // Retrieve the search query from the search field
      String searchText = customerView.getSearchQuery();
      if (searchText == null || searchText.trim().isEmpty()) {
        customerView.displayMessage("Please enter a search query.");
        return;
      }
      // Perform the search and get the results and update in the view
      List<Customer> searchResults = customerManager.searchCustomers(searchText);
      customerView.updateCustomers(searchResults);
    };



    customerView.getBackButton().addActionListener(backButtonListener);
    customerView.getAddButton().addActionListener(addButtonListener);
    customerView.getRemoveButton().addActionListener(removeButtonListener);
    customerView.getUpdateButton().addActionListener(updateButtonListener);
    customerView.getSearchButton().addActionListener(searchButtonListener);
  }


  private void loadCustomers() {
    customerManager.loadCustomers();
  }

  private void saveCustomers() {
    customerManager.saveCustomers();
  }

  private void updateCustomerData() {
    // Update the customer table in the view
    customerView.updateCustomers(customerManager.getCustomers());
  }
}
