package model.shipping;

import data.ShippingData;
import java.util.ArrayList;
import java.util.List;


public class ShippingManager {

  private List<Shipping> shippings;
  private final ShippingData shippingData;


  public ShippingManager() {
    this.shippings = new ArrayList<>();
    this.shippingData = new ShippingData();
  }

  public List<Shipping> getShipping() {
    return shippings;
  }

  public boolean updateShippingStatus(String shippingId, ShippingStatus status) {
    // Update the status of the specified order
    for (Shipping shipping : shippings) {
      if (shipping.getShippingId().equalsIgnoreCase(shippingId)) { // change here
        shipping.setShippingStatus(status);
        return true;
      }
    }
    return false; // Order not found
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
