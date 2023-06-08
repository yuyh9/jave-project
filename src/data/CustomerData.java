package data;

import java.util.ArrayList;
import java.util.List;
import model.customer.Customer;

public class CustomerData {

  private TxtFileHandler txtFileHandler;
  private List<Customer> customers;

  public CustomerData() {
    this.txtFileHandler = new TxtFileHandler();
    this.customers = new ArrayList<>();
  }

  public List<Customer> readCustomerData() {
    List<String> data = txtFileHandler.readDataFromFile("customers.txt");

    for (String line : data) {
      String[] customerData = line.split(",");
      String id = customerData[0];
      String name = customerData.length > 1 ? customerData[1] : ""; // default to empty string if not present
      String contactInformation = customerData.length > 2 ? customerData[2] : ""; // default to empty string if not present
      String shippingAddress = customerData.length > 3 ? customerData[3] : "";
      Customer customer = new Customer(id, name, contactInformation, shippingAddress);
      customers.add(customer);
    }

    return customers;
  }

  public void writeCustomerData(List<Customer> customers) {
    List<String> customerData = new ArrayList<>();

    for (Customer customer : customers) {
      String line = customer.getCustomerId() + "," +
          customer.getName() + "," +
          customer.getContactInfo() + "," +
          customer.getShippingAddress();
      customerData.add(line);
    }

    txtFileHandler.writeDataToFile("customers.txt", customerData);
  }
}
