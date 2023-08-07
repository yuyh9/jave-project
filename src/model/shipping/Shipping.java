package model.shipping;

import model.order.Order;

public class Shipping {

  private String shippingId;
  private Order associatedOrder;
  private String shippingAddress;
  private ShippingStatus shippingStatus;

  public Shipping(String shippingId, Order associatedOrder, String shippingAddress,
      ShippingStatus shippingStatus) {
    this.shippingId = shippingId;
    this.associatedOrder = associatedOrder;
    this.shippingAddress = shippingAddress;
    this.shippingStatus = shippingStatus;
  }

  public String getShippingId() {
    return shippingId;
  }

  public void setShippingId(String shippingId) {
    this.shippingId = shippingId;
  }

  public Order getAssociatedOrder() {
    return associatedOrder;
  }


  public ShippingStatus getShippingStatus() {
    return shippingStatus;
  }

  public void setShippingStatus(ShippingStatus shippingStatus) {
    this.shippingStatus = shippingStatus;
  }

  public String getCustomerName() {
    if (associatedOrder != null && associatedOrder.getCustomer() != null) {
      return associatedOrder.getCustomer().getName();
    } else {
      return null; // or handle the case when the associatedOrder or customer is null
    }
  }

  public String getCustomerAddress() {
    return shippingAddress;
  }

  @Override
  public String toString() {
    return "Shipping{" +
        "shippingId='" + shippingId + '\'' +
        ", associatedOrder=" + associatedOrder +
        ", shippingAddress='" + shippingAddress + '\'' +
        ", shippingStatus=" + shippingStatus +
        '}';
  }
}
