package controller;

import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JTable;
import model.product.Product;
import model.product.ProductCategory;
import model.product.ProductManager;
import model.supplier.Supplier;
import model.supplier.SupplierManager;
import model.warehouse.Warehouse;
import model.warehouse.WarehouseManager;
import view.HomePageView;
import view.productsview.ProductView;
import view.productsview.SupplierSelectionView;
import view.productsview.WarehouseSelectionView;

public class ProductController {

  private final ProductView productView;
  private final HomePageView homePageView;
  private final SupplierSelectionView supplierSelectionView;
  private final WarehouseSelectionView warehouseSelectionView;
  private final ProductManager productManager;
  private final SupplierManager supplierManager;
  private final WarehouseManager warehouseManager;
  private final JTable productTable;


  public ProductController(HomePageView homePageView, ProductView productView) {
    this.homePageView = homePageView;
    this.productView = productView;
    this.productManager = new ProductManager();
    this.warehouseManager = new WarehouseManager(this.productManager);
    this.supplierManager = new SupplierManager();
    this.supplierSelectionView = new SupplierSelectionView(this.productView, this.supplierManager);
    this.warehouseSelectionView = new WarehouseSelectionView(this.productView,
        this.warehouseManager);
    this.productTable = productView.getProductTable();
    attachButtonListeners();
    productView.setVisible(true);
    loadProducts();
    updateProductData();
  }


  private void attachButtonListeners() {
    productView.getBackButton().addActionListener(e -> backHomePage());
    productView.getAddButton().addActionListener(e -> addProduct());
    productView.getUpdateButton().addActionListener(e -> updateProduct());
    productView.getSearchButton().addActionListener(e -> searchProduct());
    productView.getSupplierSearchButton().addActionListener(e -> supplierIdSearch());
    productView.getWarehouseSearchButton().addActionListener(e -> warehouseIdSearch());
  }

  private void backHomePage() {
    homePageView.setVisible(true);
    productView.dispose();

  }
  private void addProduct() {
    // Get the product details from the view
    String id = productView.getId();
    String name = productView.getName();
    String priceString = productView.getPrice();
    String quantityString = productView.getQuantity();
    String supplier = productView.getSupplier();
    String warehouse = productView.getWareHouse();

    if (id.isEmpty() || name.isEmpty() || priceString.isEmpty() || quantityString.isEmpty()) {
      productView.displayErrorMessage("Please fill ID, Name, Price, Quantity required fields.\n");
      return;
    }

    double price;
    int quantity;

    try {
      price = Double.parseDouble(priceString);
      quantity = Integer.parseInt(quantityString);
    } catch (NumberFormatException ex) {
      productView.displayErrorMessage("Invalid price or quantity. Please enter valid numbers.\n");
      return;
    }

    Product newProduct = new Product(id, name, price, quantity, ProductCategory.Clothing,
        new Supplier(supplier, "", "", ""),
        new Warehouse(warehouse, "", ""));

    Product existingProduct = productManager.getProductById(id);

    if (existingProduct != null && existingProduct.isAvailable()) {
      // If a product with the same ID exists and is available, display an error message
      productView.displayErrorMessage("ID already exists.\n");
    } else {
      // If no product with the same ID exists or the existing product is not available, add the new product
      productManager.addProduct(newProduct);
      updateProductData();
      productView.clearTextField();
      saveProducts();
    }

  }

  private void updateProduct() {
    int selectedRowIndex = productTable.getSelectedRow();
    if (selectedRowIndex == -1) {
      // No row is selected
      productView.displayErrorMessage("Please select a product to update.");
      return;
    }

    String idString = productTable.getValueAt(selectedRowIndex, 0)
        .toString(); // Assuming the first column contains productId

    // Check if the product ID exists
    Product existingProduct = productManager.getProductById(idString);
    String newName = productView.getName();
    if (newName != null && !newName.isEmpty()) {
      existingProduct.setProductName(newName); // update the new name
    }

    String newPrice = productView.getPrice();
    if (newPrice != null && !newPrice.isEmpty()) {
      try {
        existingProduct.setPrice(Double.parseDouble(newPrice)); // convert int to double update
      } catch (NumberFormatException exception) {
        productView.displayErrorMessage("Invalid price. Please enter a valid number.");
        productView.clearTextField();
        return;
      }
    }

    String newQuantity = productView.getQuantity();
    if (newQuantity != null && !newQuantity.isEmpty()) {
      try {
        existingProduct.setQuantity(Integer.parseInt(newQuantity));
      } catch (NumberFormatException exception) {
        productView.displayErrorMessage("Invalid quantity. Please enter a valid integer.");
        productView.clearTextField();
        return;
      }
    }

    ProductCategory newCategory = productView.getCategory();
    if (newCategory != null) {
      existingProduct.setProductCategory(newCategory);
    }

    String newSupplier = productView.getSupplier();
    if (newSupplier != null && !newSupplier.isEmpty()) {
      Supplier updatedSupplier = supplierManager.getSupplierById(newSupplier);
      if (updatedSupplier != null) {
        // Update the supplier of the product only if the input is not empty and supplier exists
        existingProduct.setSupplier(updatedSupplier);
      } else {
        productView.displayErrorMessage("Invalid supplier ID provided.");
        productView.clearTextField();

        return;
      }
    }

    String newWarehouse = productView.getWareHouse();
    if (newWarehouse != null && !newWarehouse.isEmpty()) {
      Warehouse updatedWarehouse = warehouseManager.getWarehouseById(newWarehouse);
      if (updatedWarehouse != null) {
        existingProduct.setWarehouse(updatedWarehouse);
      } else {
        productView.displayErrorMessage("Invalid warehouse ID provided.");
        productView.clearTextField();
        return;
      }
    }

    productManager.updateProduct(existingProduct);
    saveProducts();
    updateProductData();
    productView.clearTextField();

  }


  private void searchProduct() {
    // Get the search keyword from the view
    String searchText = productView.getSearchQuery();
    if (searchText == null || searchText.trim().isEmpty()) {
      productView.displayErrorMessage("Please enter a search query.");
      updateProductData();
      return;
    }
    // Perform the search operation
    List<Product> searchResults = productManager.searchProductsByName(searchText);
    // Update the product data in the view with the search results
    productView.updateProducts(searchResults);
    productView.clearTextField();
  }

  private void supplierIdSearch () {
    supplierSelectionView.updateSuppliers(supplierManager.getSuppliers());
    supplierSelectionView.setVisible(true);
  }

  private void warehouseIdSearch() {
    warehouseSelectionView.updateWarehouses(warehouseManager.getWarehouses());
    warehouseSelectionView.setVisible(true);
  }

  public void loadProducts() {
    productManager.loadProducts();
    supplierManager.loadSuppliers();
    warehouseManager.loadWarehouses();
  }

  public void saveProducts() {
    productManager.saveProducts();
  }

  public void updateProductData() {
    productView.updateProducts(productManager.getProducts());
  }
}