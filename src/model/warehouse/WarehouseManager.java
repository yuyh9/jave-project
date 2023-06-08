package model.warehouse;

import data.WarehouseData;
import java.util.ArrayList;
import java.util.List;
import model.product.Product;
import model.product.ProductManager;

public class WarehouseManager {

  private List<Warehouse> warehouses;
  private List<Product> products;
  private final WarehouseData warehouseData;
  private final ProductManager productManager;

  public WarehouseManager(ProductManager productManager) {
    this.warehouses = new ArrayList<>();
    this.products = new ArrayList<>();
    this.warehouseData = new WarehouseData();
    this.productManager = productManager;

  }

  public List<Warehouse> getWarehouses() {
    return warehouses;
  }

  public void addWarehouse(Warehouse warehouse) {
    warehouses.add(warehouse);
  }

  public void removeWarehouse(Warehouse warehouse) {
    if (products.isEmpty()) {
      warehouses.remove(warehouse);
    }
  }

  public Warehouse getWarehouseById(String warehouseId) {
    for (Warehouse warehouse : warehouses) {
      if (warehouse.getWarehouseId().equals(warehouseId)) {
        return warehouse;
      }
    }
    return null; // Warehouse not found
  }


  public void updateWarehouse(Warehouse warehouse) {
    // Update the customer in the list
    for (int i = 0; i < this.warehouses.size(); i++) {
      if (this.warehouses.get(i).getWarehouseId().equals(warehouse.getWarehouseId())) {
        this.warehouses.set(i, warehouse);
        break;
      }
    }
  }

  public List<Warehouse> searchWarehouse(String searchText) {
    List<Warehouse> results = new ArrayList<>();
    String searchLower = searchText.toLowerCase();
    for (Warehouse warehouse : warehouses) {
      if (warehouse.getWarehouseId().toLowerCase().contains(searchLower) ||
          warehouse.getLocation().toLowerCase().contains(searchLower)) {
        results.add(warehouse);
      }
    }
    return results;
  }

  public void loadWarehouses() {
    warehouses = warehouseData.readWarehouseData();
    products = productManager.getProducts();

  }

  public void saveWarehouses() {
    warehouseData.writeWarehouseData(warehouses);
  }

}
