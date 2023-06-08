package model.customer;

import data.CustomerData;
import java.util.ArrayList;
import java.util.List;
import model.order.OrderManager;

public class CustomerManager {

  private List<Customer> customer;
  private CustomerData customerData;
  private OrderManager orderManager;

  public CustomerManager(OrderManager orderManager) {
    this.customer = new ArrayList<>();
    this.orderManager = orderManager;
    this.customerData = new CustomerData();
  }

  public List<Customer> getCustomers() {
    return customer;
  }

  public void addCustomer(Customer customer) {
    this.customer.add(customer);
  }

  public void removeCustomer(Customer customer) {
    this.customer.remove(customer);
  }

  public void updateCustomer(Customer customer) {
    // Update the customer in the list
    for (int i = 0; i < this.customer.size(); i++) {
      if (this.customer.get(i).getCustomerId() == customer.getCustomerId()) {
        this.customer.set(i, customer);
        break;
      }
    }
  }
  public Customer getCustomerById(String customerId) {
    // Retrieve and return the order with the specified order ID
    for (Customer customers : customer) {  // assuming you have a collection named customers
      if (customers.getCustomerId().contains(customerId)) {
        return customers;
      }
    }
    return null; // customer not found
  }

  public List<Customer> searchCustomers(String searchText) {
    List<Customer> results = new ArrayList<>();
    String searchLower = searchText.toLowerCase();
    for (Customer customer : customer) {
      if (customer.getCustomerId().toLowerCase().contains(searchLower) ||
          customer.getName().toLowerCase().contains(searchLower)) {
        results.add(customer);
      }
    }
    return results;
  }



  public void loadCustomers() {
    customer = customerData.readCustomerData();

  }

  public void saveCustomers() {
    customerData.writeCustomerData(customer);
  }



}
