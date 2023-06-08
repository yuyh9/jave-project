package controller;

import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import model.product.ProductManager;
import model.warehouse.Warehouse;
import model.warehouse.WarehouseManager;
import view.HomePageView;
import view.WarehouseView;

public class WarehouseController {

  private final HomePageView homePageView;
  private final WarehouseView warehouseView;
  private final WarehouseManager warehouseManager;
  private final ProductManager productManager;
  private final JTable warehouseTable;

  public WarehouseController(HomePageView homePageView,
      WarehouseView warehouseView) {
    this.homePageView = homePageView;
    this.warehouseView = warehouseView;
    this.productManager = new ProductManager();
    this.warehouseManager = new WarehouseManager(this.productManager);
    this.warehouseTable = warehouseView.getWarehouseTable();

    attachWarehouseButtonListeners();
    warehouseView.setVisible(true);
    loadWarehouse(); // Call the method without passing the products list
    updateWarehouseData();
  }

  private void attachWarehouseButtonListeners() {
    warehouseView.getBackButton().addActionListener(e -> backHomePage());
    warehouseView.getAddButton().addActionListener(e -> addWarehouse());
    warehouseView.getRemoveButton().addActionListener(e -> removeWarehouse());
    warehouseView.getUpdateButton().addActionListener(e -> updateWarehouse());
    warehouseView.getSearchButton().addActionListener(e -> searchWarehouse());
  }

  private void backHomePage() {
    homePageView.setVisible(true);
    warehouseView.dispose();
  }

  private void addWarehouse() {
    String id = warehouseView.getWarehouseId();
    String name = warehouseView.getWarehouseName();
    String location = warehouseView.getWarehouseLocation();
    Warehouse existingWarehouse = warehouseManager.getWarehouseById(id);

    if (warehouseManager.getWarehouseById(id) != null) {
      warehouseView.displayMessage("ID already exists!");
      return;
    }
    Warehouse newWarehouse = new Warehouse(id, name, location);
    warehouseManager.addWarehouse(newWarehouse);
    saveWarehouse();
    updateWarehouseData();
    warehouseView.clearTextField();

  }

  private void removeWarehouse() {
    int selectedRowIndex = warehouseTable.getSelectedRow();
    if (selectedRowIndex != -1) {
      String productId = (String) warehouseTable.getValueAt(selectedRowIndex, 0);
      int dialogResult = warehouseView.displayConfirmDialog(
          "Are you sure you want to remove this warehouse?");
      if (dialogResult == JOptionPane.YES_OPTION) {
        String productList = (String) warehouseTable.getValueAt(selectedRowIndex, 3);
        if (productList == null || productList.isEmpty()) {
          Warehouse warehouse = warehouseManager.getWarehouseById(productId);
          if (warehouse != null) {
            warehouseManager.removeWarehouse(warehouse);
            saveWarehouse();
            updateWarehouseData();
          }
        } else {
          warehouseView.displayMessage(
              "Cannot remove the warehouse as it is associated with products.");
        }
      }
    } else {
      warehouseView.displayMessage("Please select a warehouse to remove.");
    }
  }

  private void updateWarehouse() {
    int selectedRowIndex = warehouseTable.getSelectedRow();
    if (selectedRowIndex == -1) {
      warehouseView.displayMessage("Please select a customer to update.");
    }
    String idString = warehouseTable.getValueAt(selectedRowIndex, 0)
        .toString(); // Assuming the first column contains customerId

    // Check if the customer ID exists
    Warehouse existingWarehouse = warehouseManager.getWarehouseById(idString);
    String newName = warehouseView.getWarehouseName();
    if (newName != null && !newName.isEmpty()) {
      existingWarehouse.setWarehouseName(newName);
    }
    String newLocation = warehouseView.getWarehouseLocation();
    if (newLocation != null && !newLocation.isEmpty()) {
      existingWarehouse.setLocation(newLocation);
    }
    warehouseManager.updateWarehouse(existingWarehouse);
    saveWarehouse();
    updateWarehouseData();
    warehouseView.clearTextField();
  }

  private void searchWarehouse() {
    // Retrieve the search query from the search field
    String searchText = warehouseView.getSearchQuery();
    if (searchText == null || searchText.trim().isEmpty()) {
      warehouseView.displayMessage("Please enter a search query.");
      return;
    }
    // Perform the search and get the results
    List<Warehouse> searchResults = warehouseManager.searchWarehouse(searchText);

    // Update the customer data in the view with the search results
    warehouseView.updateWarehouses(searchResults);
  }

  public void loadWarehouse() {
    warehouseManager.loadWarehouses();
  }

  public void saveWarehouse() {
    warehouseManager.saveWarehouses();
  }

  public void updateWarehouseData() {
    warehouseView.updateWarehouses(warehouseManager.getWarehouses());
  }
}
