package controller;

import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import model.product.*;
import model.supplier.Supplier;
import model.supplier.SupplierManager;
import model.warehouse.Warehouse;
import model.warehouse.WarehouseManager;
import view.*;
import view.productsview.ProductView;
import view.productsview.SupplierSelectionView;
import view.productsview.WarehouseSelectionView;

public class ProductController {
  private ProductView productView;
  private HomePageView homePageView;
  private SupplierSelectionView supplierSelectionView;
  private WarehouseSelectionView warehouseSelectionView;
  private ProductManager productManager;
  private SupplierManager supplierManager;
  private WarehouseManager warehouseManager;
  private JTable productTable;


  public ProductController(HomePageView homePageView, ProductView productView) {
    this.homePageView = homePageView;
    this.productView = productView;
    this.productManager = new ProductManager();
    this.warehouseManager = new WarehouseManager();
    this.supplierManager = new SupplierManager();
    this.supplierSelectionView = new SupplierSelectionView(this.productView, this.supplierManager);
    this.warehouseSelectionView = new WarehouseSelectionView(this.productView, this.warehouseManager);
    this.productTable = productView.getProductTable();
    attachButtonListeners();
    productView.setVisible(true);
    loadProducts();
    updateProductData();
  }
  private void attachButtonListeners() {

    // back button listener
    ActionListener backButtonListener = e -> {
      homePageView.setVisible(true);
      productView.dispose();
    };
    // add button listener
    ActionListener addButtonListener = e -> {
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
          new Supplier(supplier, "", "",""),
          new Warehouse(warehouse, "", ""));

      if (productManager.getProductById(id) != null) {
        productView.displayErrorMessage("ID already exists.\n");
      } else {
        productManager.addProduct(newProduct);
        updateProductData();
        productView.clearTextField();
        saveProducts();
      }
    };

    // remove button listener
    ActionListener removeButtonListener = e -> {
      int selectedRow = productTable.getSelectedRow();
      if (selectedRow != -1) {
        String productName = (String) productTable.getValueAt(selectedRow, 1);
        Product productToRemove = productManager.getProductByName(productName);
        int confirmDialogResult = productView.displayConfirmDialog("Are you sure you want to remove this product?");
        if (confirmDialogResult == JOptionPane.YES_OPTION) {
          productManager.removeProduct(productToRemove);
          updateProductData();
          saveProducts();
        }
      } else {
        productView.displayErrorMessage("Please select a product to remove.");
      }
    };

    // update button listener
    ActionListener updateButtonListener = e -> {

    };


    // search button listener
    ActionListener searchButtonListener = e -> {
      // Get the search keyword from the view
      String searchText = productView.getSearchQuery();
      if (searchText == null || searchText.trim().isEmpty()) {
        productView.displayErrorMessage("Please enter a search query.");
        return;
      }
      // Perform the search operation
      List<Product> searchResults = productManager.searchProductsByName(searchText);
      // Update the product data in the view with the search results
      productView.updateProducts(searchResults);
      productView.clearSearchKeyword();
    };


    // supplier id search button lister
    ActionListener supplierSearchButtonLister = e -> {

      supplierSelectionView.updateSuppliers(supplierManager.getSuppliers());
      supplierSelectionView.setVisible(true);
    };
    // warehouse id search button lister
    ActionListener warehouseSearchButtonLister = e -> {
      warehouseSelectionView.updateWarehouses(warehouseManager.getWarehouses());
      warehouseSelectionView.setVisible(true);
    };


    productView.getBackButton().addActionListener(backButtonListener);
    productView.getAddButton().addActionListener(addButtonListener);
    productView.getDeleteButton().addActionListener(removeButtonListener);
    productView.getUpdateButton().addActionListener(updateButtonListener);
    productView.getSearchButton().addActionListener(searchButtonListener);
    productView.getSupplierSearchButton().addActionListener(supplierSearchButtonLister);
    productView.getWarehouseSearchButton().addActionListener(warehouseSearchButtonLister);
  }


  private void loadProducts() {
    productManager.loadProducts();
    supplierManager.loadSuppliers();
    warehouseManager.loadWarehouses();
  }

  private void saveProducts() {
    productManager.saveProducts();
  }

  private void updateProductData() {
    productView.updateProducts(productManager.getProducts());
  }
}
