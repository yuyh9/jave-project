package model.shipping;

import data.ShippingData;
import java.util.ArrayList;
import java.util.List;
import model.customer.*;
import model.order.*;


public class ShippingManager {
  private List<Shipping> shippings;
  private ShippingData shippingData;
  private OrderManager orderManager;
  private CustomerManager customerManager;

  public ShippingManager(OrderManager orderManager, CustomerManager customerManager) {
    this.shippings = new ArrayList<>();
    this.shippingData = new ShippingData();
    this.orderManager = orderManager;
    this.customerManager = customerManager;
  }

  public List<Shipping> getShipping() {
    return shippings;
  }


  public List<Shipping> searchShipping(String searchText) {
    List<Shipping> results = new ArrayList<>();
    String searchLower = searchText.toLowerCase();
    for (Shipping shipping : shippings) {
      if (shipping.getShippingId().toLowerCase().contains(searchLower) ||
          shipping.getCustomerName().toLowerCase().contains(searchLower)) {
        results.add(shipping);
      }
    }
    return results;
  }

  public void loadShipping() {
    shippings = shippingData.readShippingData();
  }


  public void saveShipping() {
    shippingData.writeShippingData(shippings);
  }


}
