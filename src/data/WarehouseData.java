package data;

import java.util.ArrayList;
import java.util.List;
import model.product.*;
import model.warehouse.Warehouse;


public class WarehouseData {

  private TxtFileHandler txtFileHandler;
  private List<Warehouse> warehouses;


  public WarehouseData() {
    this.txtFileHandler = new TxtFileHandler();
    this.warehouses = new ArrayList<>();
  }

  public List<Warehouse> readWarehouseData() {

    List<String> data = txtFileHandler.readDataFromFile("warehouse.txt");

    for (String line : data) {
      String[] warehouseData = line.split(",");
      String warehouseId = warehouseData[0];
      String warehouseName = warehouseData[1];
      String location = warehouseData[2];

      Warehouse warehouse = new Warehouse(warehouseId, warehouseName, location);
      warehouses.add(warehouse);
    }

    return warehouses;
  }

  public void writeWarehouseData(List<Warehouse> warehouses) {
    List<String> warehouseData = new ArrayList<>();

    for (Warehouse warehouse : warehouses) {

      String line = warehouse.getWarehouseId() + ","
          + warehouse.getWarehouseName() + ","
          + warehouse.getLocation();

      warehouseData.add(line);
    }

    txtFileHandler.writeDataToFile("warehouse.txt", warehouseData);
  }

}
