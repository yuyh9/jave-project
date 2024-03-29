package model.order;

import model.product.Product;

public class OrderItem {

  private Product product;
  private int quantity;

  public OrderItem(Product product, int quantity) {
    this.product = product;
    this.quantity = quantity;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public void updateQuantity(int quantity) {
    product.setQuantity(quantity);
  }

  @Override
  public String toString() {
    return "Item: " +
        "product: " + product +
        ", quantity: " + quantity + "\n";
  }
}