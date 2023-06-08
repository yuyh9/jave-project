package model.warehouse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import model.product.Product;

public class Warehouse  {
  private String warehouseId;
  private String warehouseName;
  private String location;

  public Warehouse(String warehouseId, String warehouseName, String location) {
    this.warehouseId = warehouseId;
    this.warehouseName = warehouseName;
    this.location = location;

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


  @Override
  public String toString() {
    return this.location;
  }
}
