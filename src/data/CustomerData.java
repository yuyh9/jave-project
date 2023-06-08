package data;

import java.util.ArrayList;
import java.util.List;
import model.customer.Customer;

public class CustomerData {

  private final TxtFileHandler txtFileHandler;
  private final List<Customer> customers;

  public CustomerData() {
    this.txtFileHandler = new TxtFileHandler();
    this.customers = new ArrayList<>();
  }

  public List<Customer> readCustomerData() {
    List<String> data = txtFileHandler.readDataFromFile("customers.txt");

    for (String line : data) {
      String[] customerData = line.split(",");
      String id = customerData[0];
      String name =
          customerData.length > 1 ? customerData[1] : ""; // default to empty string if not present
      String contactInformation = customerData.length > 2 ? customerData[2] : "";
      String shippingAddress = customerData.length > 3 ? customerData[3] : "";
      boolean status = Boolean.parseBoolean(customerData[4]);

      Customer customer = new Customer(id, name, contactInformation, shippingAddress);
      customer.setAvailable(status);
      customers.add(customer);
    }

    return customers;
  }

  public void writeCustomerData(List<Customer> customers) {
    List<String> customerData = new ArrayList<>();

    for (Customer customer : customers) {
      String line = customer.getCustomerId() + ","
          + customer.getName() + ","
          + customer.getContactInfo() + ","
          + customer.getShippingAddress() + ","
          + customer.isAvailable();
      customerData.add(line);
    }

    txtFileHandler.writeDataToFile("customers.txt", customerData);
  }
}
