package model.product;

import data.ProductData;
import java.util.ArrayList;
import java.util.List;

public class ProductManager {

  private List<Product> products;
  private final ProductData productData;


  private final int nextProductId;


  public ProductManager() {
    this.products = new ArrayList<>();
    this.productData = new ProductData();
    this.nextProductId = 1;
  }

  public List<Product> getProducts() {
    return products;
  }

  public Product getProductByName(String name) {
    for (Product product : products) {
      if (product.getProductName().equalsIgnoreCase(name)) {
        return product;
      }
    }
    return null; // Product not found
  }

  public void addProduct(Product product) {
    products.add(product);
  }


  public void removeProductById(String productId) {
    // Find the product with the given ID and remove it from the list
    for (Product product : products) {
      if (product.getProductId().equals(productId)) {
        products.remove(product);
        break;
      }
    }
  }

  public void updateProduct(Product product) {
    // Update the product in the list
    for (int i = 0; i < products.size(); i++) {
      if (products.get(i).getProductId().equals(product.getProductId())) {
        products.set(i, product);
        break;
      }
    }
  }


  public List<Product> searchProductsByName(String searchText) {
    List<Product> searchResults = new ArrayList<>();
    String searchLower = searchText.toLowerCase();
    for (Product product : products) {
      if (product.getProductName().toLowerCase().contains(searchLower)) {
        searchResults.add(product);
      }
    }

    return searchResults;
  }


  public Product getProductById(String productId) {
    // Retrieve and return the order with the specified order ID
    for (Product product : products) {  // assuming you have a collection named customers
      if (product.getProductId().contains(productId)) {
        return product;
      }
    }
    return null; // customer not found
  }

  public List<Product> getProductsByWarehouseId(String warehouseId) {
    List<Product> productByWarehouseId = new ArrayList<>();
    for (Product product : products) {
      if (product.getWarehouse().getWarehouseId().equals(warehouseId)) {
        productByWarehouseId.add(product);
      }
    }
    return productByWarehouseId;
  }

  public void loadProducts() {
    products = productData.readProductData();
  }

  public void saveProducts() {
    productData.writeProductData(products);
  }


  @Override
  public String toString() {
    return "InventoryManager{" +
        "products=" + products +
        '}';
  }


}
