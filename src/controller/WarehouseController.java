package controller;

import java.awt.event.ActionListener;

import java.util.List;
import model.customer.*;
import model.order.*;
import model.product.*;
import model.supplier.Supplier;
import model.warehouse.*;
import view.*;

public class WarehouseController {
  private HomePageView homePageView;
  private WarehouseView warehouseView;
  private WarehouseManager warehouseManager;
  public WarehouseController(HomePageView homePageView,
      WarehouseView warehouseView) {
    this.homePageView = homePageView;
    this.warehouseView = warehouseView;
    this.warehouseManager = new WarehouseManager();
    attachButtonListeners();
    warehouseView.setVisible(true);
    loadWarehouse(); // Call the method without passing the products list
    updateWarehouseData();
  }
  private void attachButtonListeners() {

    // back button listener
    ActionListener backButtonListener = e -> {
      homePageView.setVisible(true);
      warehouseView.dispose();
    };
    ActionListener addButtonListener = e -> {
      String id = warehouseView.getWarehouseId();
      String name = warehouseView.getWarehouseName();
      String location = warehouseView.getWarehouseLocation();

      if (warehouseManager.getWarehouseById(id) != null) {
        warehouseView.displayMessage("ID already exists!");
        return;
      }
      Warehouse newWarehouse = new Warehouse(id, name, location);
      warehouseManager.addWarehouse(newWarehouse);
      saveWarehouse();
      updateWarehouseData();
      warehouseView.clearTextField();

    };

    ActionListener searchButtonListener = e -> {
      // Retrieve the search query from the search field
      String searchText = warehouseView.getSearchQuery();

      // Check if search query is empty
      if (searchText == null || searchText.trim().isEmpty()) {
        warehouseView.displayMessage("Please enter a search query.");
        return;
      }

      // Perform the search and get the results
      List<Warehouse> searchResults = warehouseManager.searchWarehouse(searchText);

      // Update the customer data in the view with the search results
      warehouseView.updateWarehouses(searchResults);
    };

    warehouseView.getBackButton().addActionListener(backButtonListener);
    warehouseView.getAddButton().addActionListener(addButtonListener);
    warehouseView.getSearchButton().addActionListener(searchButtonListener);

  }


  private void loadWarehouse() {
    warehouseManager.loadWarehouses();
  }

  private void saveWarehouse() {
    warehouseManager.saveWarehouses();
  }

  private void updateWarehouseData() {
    warehouseView.updateWarehouses(warehouseManager.getWarehouses());
  }

}
