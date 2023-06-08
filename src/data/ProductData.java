package data;

import java.util.ArrayList;
import java.util.List;
import model.product.Product;
import model.product.ProductCategory;
import model.supplier.Supplier;
import model.warehouse.Warehouse;

public class ProductData {

  private final TxtFileHandler txtFileHandler;
  private final List<Product> products;

  public ProductData() {
    this.txtFileHandler = new TxtFileHandler();
    this.products = new ArrayList<>();
  }

  public List<Product> readProductData() {
    List<String> data = txtFileHandler.readDataFromFile("products.txt");

    for (String line : data) {
      String[] productData = line.split(",");
      String productId = productData[0];
      String productName = productData[1];
      double price = Double.parseDouble(productData[2]);
      int quantity = Integer.parseInt(productData[3]);
      ProductCategory category = ProductCategory.valueOf(productData[4]);
      String supplierId = productData.length > 5 ? productData[5] : "";
      String warehouseId = productData.length > 6 ? productData[6] : "";
      boolean status = Boolean.parseBoolean(productData[7]);

      Supplier supplier = new Supplier(supplierId, "", "", "");
      Warehouse warehouse = new Warehouse(warehouseId, "", "");
      Product product = new Product(productId, productName, price, quantity, category, supplier,
          warehouse);
      product.setAvailable(status);
      products.add(product);
    }

    return products;
  }

  public void writeProductData(List<Product> products) {
    List<String> productData = new ArrayList<>();

    for (Product product : products) {
      String line = product.getProductId() + ","
          + product.getProductName() + ","
          + product.getPrice() + ","
          + product.getQuantity() + ","
          + product.getProductCategory() + ","
          + product.getSupplier().getSupplierId() + ","
          + product.getWarehouse().getWarehouseId() + ","
          + product.isAvailable();
      productData.add(line);
    }

    txtFileHandler.writeDataToFile("products.txt", productData);
  }
}
