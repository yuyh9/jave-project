package data;

import java.util.ArrayList;
import java.util.List;
import model.product.Product;
import model.product.ProductManager;
import model.warehouse.Warehouse;


public class WarehouseData {

  private final TxtFileHandler txtFileHandler;
  private final List<Warehouse> warehouses;
  private final ProductManager productManager;


  public WarehouseData() {
    this.txtFileHandler = new TxtFileHandler();
    this.warehouses = new ArrayList<>();
    this.productManager = new ProductManager();
    this.productManager.loadProducts();
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

      List<Product> products = productManager.getProductsByWarehouseId(warehouseId);
      for (Product product : products) {
        warehouse.addToInventory(product);
      }
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
