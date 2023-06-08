package model.customer;



public class Customer {
  private String customerId;
  private String name;
  private String contactInformation;
  private String shippingAddress;
  private boolean isActive;

  public Customer(String SupplierId, String name, String contactInformation,
      String shippingAddress) {
    this.customerId = SupplierId;
    this.name = name;
    this.contactInformation = contactInformation;
    this.shippingAddress = shippingAddress;
    this.isActive = true;
  }


  public String getCustomerId() {
    return customerId;
  }

  public void setCustomerId(String customerId) {
    this.customerId = customerId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getContactInfo() {
    return contactInformation;
  }

  public void setContactInfo(String contactInformation) {
    this.contactInformation = contactInformation;
  }

  public String getShippingAddress() {
    return shippingAddress;
  }

  public void setShippingAddress(String shippingAddress) {
    this.shippingAddress = shippingAddress;
  }

  public boolean isActive() {
    return isActive;
  }

  public void setActive(boolean active) {
    isActive = active;
  }
  @Override
  public String toString() {
    return customerId + " " + name;
  }

}
