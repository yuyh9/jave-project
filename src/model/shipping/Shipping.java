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

  public void setAssociatedOrder(Order associatedOrder) {
    this.associatedOrder = associatedOrder;
  }

  public String getShippingAddress() {
    return shippingAddress;
  }

  public void setShippingAddress(String shippingAddress) {
    this.shippingAddress = shippingAddress;
  }

  public ShippingStatus getShippingStatus() {
    return shippingStatus;
  }

  public void setShippingStatus(ShippingStatus shippingStatus) {
    this.shippingStatus = shippingStatus;
  }

  public String getCustomerName() {
    return associatedOrder.getCustomer().getName();
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
