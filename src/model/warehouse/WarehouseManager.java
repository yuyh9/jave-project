package model.warehouse;

import data.WarehouseData;
import java.util.ArrayList;
import java.util.List;
import model.product.*;
import model.supplier.Supplier;


public class WarehouseManager {
  private List<Warehouse> warehouses;
  private WarehouseData warehouseData;


  public WarehouseManager() {
    this.warehouses = new ArrayList<>();
    this.warehouseData = new WarehouseData();

  }

  public List<Warehouse> getWarehouses() {
    return warehouses;
  }

  public void addWarehouse(Warehouse warehouse) {
    warehouses.add(warehouse);
  }

  public void removeWarehouse(Warehouse warehouse) {
    warehouses.remove(warehouse);
  }

  public Warehouse getWarehouseById(String warehouseId) {
    for (Warehouse warehouse : warehouses) {
      if (warehouse.getWarehouseId().equals(warehouseId)) {
        return warehouse;
      }
    }
    return null; // Warehouse not found
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

  }


  public void saveWarehouses() {
    warehouseData.writeWarehouseData(warehouses);
  }


}
