package model.product;

import model.supplier.Supplier;
import model.warehouse.Warehouse;

public class Product {

  private String productId;
  private String productName;
  private double price;
  private int quantity;
  private ProductCategory productCategory;
  private Supplier supplier;
  private Warehouse warehouse;

  private boolean available;

  public Product(String productId, String productName, double price, int quantity,
      ProductCategory productCategory,
      Supplier supplier, Warehouse warehouse) {
    this.productId = productId;
    this.productName = productName;
    this.price = price;
    this.quantity = quantity;
    this.productCategory = productCategory;
    this.supplier = supplier;
    this.warehouse = warehouse;
    this.available = true;
  }

  public String getProductId() {
    return productId;
  }

  public void setProductId(String productId) {
    if (productId != null && !productId.isEmpty()) {
      this.productId = productId;
    } else {
      throw new IllegalArgumentException("ProductId cannot be empty.");
    }
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }


  public ProductCategory getProductCategory() {
    return productCategory;
  }

  public void setProductCategory(ProductCategory productCategory) {
    this.productCategory = productCategory;
  }

  public Supplier getSupplier() {
    return supplier;
  }

  public void setSupplier(Supplier supplier) {
    this.supplier = supplier;
  }

  public Warehouse getWarehouse() {
    return warehouse;
  }

  public void setWarehouse(Warehouse warehouse) {
    this.warehouse = warehouse;
  }

  public boolean isAvailable() {
    return available;
  }

  public void setAvailable(boolean available) {
    this.available = available;
  }

  @Override
  public String toString() {
    return this.productName;
  }

}
