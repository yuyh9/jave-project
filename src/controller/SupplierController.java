package controller;

import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JTable;
import model.supplier.Supplier;
import model.supplier.SupplierManager;
import view.HomePageView;
import view.SupplierView;

public class SupplierController {

  private HomePageView homePageView;
  private SupplierView supplierView;
  private SupplierManager supplierManager;

  private JTable supplierTable;

  public SupplierController(HomePageView homePageView,
      SupplierView supplierView) {
    this.homePageView = homePageView;
    this.supplierView = supplierView;
    this.supplierManager = new SupplierManager();
    this.supplierTable = supplierView.getSupplierTable();
    attachButtonListeners();
    supplierView.setVisible(true);
    loadSuppliers();
    updateSupplierData();
  }
  private void attachButtonListeners() {

    // back button listener
    ActionListener backButtonListener = e -> {
      homePageView.setVisible(true);
      supplierView.dispose();
    };

    ActionListener addButtonListener = e -> {
      String id = supplierView.getSupplierId();
      String name = supplierView.getSupplierName();
      String email = supplierView.getEmail();
      String phone = supplierView.getPhone();

      if (supplierManager.getSupplierById(id) != null) {
        supplierView.displayMessage("ID already exists!");
        return;
      }
      Supplier newSupplier = new Supplier(id, name, email, phone);
      supplierManager.addSupplier(newSupplier);
      saveSuppliers();
      updateSupplierData();
      supplierView.clearTextField();

    };

    ActionListener searchButtonListener = e -> {
      // Retrieve the search query from the search field
      String searchText = supplierView.getSearchQuery();

      // Check if search query is empty
      if (searchText == null || searchText.trim().isEmpty()) {
        supplierView.displayMessage("Please enter a search query.");
        return;
      }

      // Perform the search and get the results
      List<Supplier> searchResults = supplierManager.searchSupplier(searchText);

      // Update the customer data in the view with the search results
      supplierView.updateSuppliers(searchResults);
    };



    supplierView.getAddButton().addActionListener(addButtonListener);
    supplierView.getBackButton().addActionListener(backButtonListener);
    supplierView.getSearchButton().addActionListener(searchButtonListener);
  }

  private void loadSuppliers() {
    supplierManager.loadSuppliers();
  }

  private void saveSuppliers() {
    supplierManager.saveSuppliers();
  }

  private void updateSupplierData() {
    supplierView.updateSuppliers(supplierManager.getSuppliers());
  }

}
