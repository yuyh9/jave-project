package data;

import java.util.ArrayList;
import java.util.List;
import model.customer.*;
import model.order.*;
import model.product.ProductManager;
import model.shipping.*;

public class ShippingData {
  private TxtFileHandler txtFileHandler;
  private OrderManager orderManager;
  private List<Shipping> shippings;
  private ProductManager productManager;
  private CustomerManager customerManager;
  public ShippingData() {
    this.txtFileHandler = new TxtFileHandler();
    this.productManager = new ProductManager();
    this.orderManager = new OrderManager(this.productManager, this.customerManager);
    this.customerManager = new CustomerManager(this.orderManager);
    this.shippings = new ArrayList<>();
    orderManager.loadOrders();
    customerManager.loadCustomers();
  }

  public List<Shipping> readShippingData() {
    List<String> data = txtFileHandler.readDataFromFile("shipping.txt");

    for (String line : data) {
      String[] shippingData = line.split(",");
      String shippingId = shippingData[0];
      String orderId = shippingData[1];
      ShippingStatus status = ShippingStatus.valueOf(shippingData[3]);
      Order associatedOrder = orderManager.getOrderById(orderId);
      Customer customer = associatedOrder.getCustomer();
      String shippingAddress = customer.getShippingAddress();
      Shipping shipping = new Shipping(shippingId, associatedOrder, shippingAddress, status);
      this.shippings.add(shipping);
    }

    return this.shippings;
  }


  public void writeShippingData(List<Shipping> shippings) {
    List<String> shippingData = new ArrayList<>();

    for (Shipping shipping : shippings) {
      Order associatedOrder = shipping.getAssociatedOrder();
      String line = shipping.getShippingId() + ","
          + associatedOrder.getOrderId() + ","
          + shipping.getShippingStatus();

      shippingData.add(line);
    }

    txtFileHandler.writeDataToFile("shipping.txt", shippingData);
  }

}
