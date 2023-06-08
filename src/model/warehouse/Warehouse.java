package model.warehouse;

import java.util.ArrayList;
import java.util.List;
import model.product.Product;

public class Warehouse {

  private String warehouseId;
  private String warehouseName;
  private String location;
  private final List<Product> products;

  public Warehouse(String warehouseId, String warehouseName, String location) {
    this.warehouseId = warehouseId;
    this.warehouseName = warehouseName;
    this.location = location;
    this.products = new ArrayList<>();

  }

  public String getWarehouseId() {
    return warehouseId;
  }

  public void setWarehouseId(String warehouseId) {
    this.warehouseId = warehouseId;
  }

  public String getWarehouseName() {
    return warehouseName;
  }

  public void setWarehouseName(String warehouseName) {
    this.warehouseName = warehouseName;
  }

  public String getLocation() {
    return this.location;
  }

  public void setLocation(String location) {
    this.location = location;
  }


  public List<Product> getWarehouseProducts() {
    return products;
  }

  public void addToInventory(Product product) {
    products.add(product);
  }

  public void removeFromInventory(Product product) {
    products.remove(product);
  }

  public List<String> getProductNames() {
    List<String> productNames = new ArrayList<>();
    for (Product product : products) {
      productNames.add(product.getProductName());
    }
    return productNames;
  }

  @Override
  public String toString() {
    return this.location;
  }
}
