package controller;

import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import model.supplier.Supplier;
import model.supplier.SupplierManager;
import view.HomePageView;
import view.SupplierView;

public class SupplierController {

  private final HomePageView homePageView;
  private final SupplierView supplierView;
  private final SupplierManager supplierManager;
  private final JTable supplierTable;

  public SupplierController(HomePageView homePageView,
      SupplierView supplierView) {
    this.homePageView = homePageView;
    this.supplierView = supplierView;
    this.supplierManager = new SupplierManager();
    this.supplierTable = supplierView.getSupplierTable();
    attachSupplierButtonListeners();
    supplierView.setVisible(true);
    loadSuppliers();
    updateSupplierData();
  }

  private void attachSupplierButtonListeners() {
    supplierView.getBackButton().addActionListener(e -> backHomePage());
    supplierView.getAddButton().addActionListener(e -> addSupplier());
    supplierView.getActiveButton().addActionListener(e -> activeSupplier());
    supplierView.getUpdateButton().addActionListener(e -> updateSupplier());
    supplierView.getSearchButton().addActionListener(e -> searchSupplier());
  }

  private void backHomePage() {
    homePageView.setVisible(true);
    supplierView.dispose();
  }

  private void addSupplier() {

    String id = supplierView.getSupplierId();
    String name = supplierView.getSupplierName();
    String email = supplierView.getEmail();
    String phone = supplierView.getPhone();

    // Check if either email or phone number is provided
    if (email.isEmpty() && phone.isEmpty()) {
      supplierView.displayMessage("Please provide either an email or a phone number.");
      return; // Stop execution if both email and phone are empty
    }

    Supplier existingSupplier = supplierManager.getSupplierById(id);

    if (existingSupplier != null && existingSupplier.isAvailable()) {
      // If a customer with the same ID exists and is available, display an error message
      supplierView.displayMessage("Supplier ID already exists!");
    } else {
      // If no customer with the same ID exists or the existing customer is not available, add the new customer
      Supplier newSupplier = new Supplier(id, name, email, phone);
      supplierManager.addSupplier(newSupplier);
      saveSuppliers();
      updateSupplierData();
      supplierView.clearTextField();
    }

  }

  private void activeSupplier() {

    int selectedRowIndex = supplierTable.getSelectedRow();
    if (selectedRowIndex != -1) {
      String supplierId = (String) supplierTable.getValueAt(selectedRowIndex, 0);
      int dialogResult = supplierView.displayConfirmDialog("Are you sure you want to "
          + "Active/Inactive this supplier?");
      if (dialogResult == JOptionPane.YES_OPTION) {
        Supplier supplier = supplierManager.getSupplierById(supplierId);

        if (supplier != null) {
          supplierManager.activeSupplier(supplier);
          saveSuppliers();
          updateSupplierData();
        }
      }
    } else {
      supplierView.displayMessage("No customer is selected. Please select a supplier to "
          + "remove.");
    }

  }

  private void updateSupplier() {

    int selectedRowIndex = supplierTable.getSelectedRow();
    if (selectedRowIndex == -1) {
      supplierView.displayMessage("Please select a customer to update.");
    }
    String idString = supplierTable.getValueAt(selectedRowIndex, 0)
        .toString(); // Assuming the first

    // Check if the customer ID exists
    Supplier existingSupplier = supplierManager.getSupplierById(idString);
    String newName = supplierView.getSupplierName();
    if (newName != null && !newName.isEmpty()) {
      existingSupplier.setSupplierName(newName);
    }
    String newEmail = supplierView.getEmail();
    if (newEmail != null && !newEmail.isEmpty()) {
      existingSupplier.setSupplierEmail(newEmail);
    }
    String newPhone = supplierView.getPhone();
    if (newPhone != null && !newPhone.isEmpty()) {
      existingSupplier.setSupplierPhone(newPhone);
    }
    supplierManager.updateSupplier(existingSupplier);
    saveSuppliers();
    updateSupplierData();
    supplierView.clearTextField();

  }

  private void searchSupplier() {

    // Retrieve the search query from the search field
    String searchText = supplierView.getSearchQuery();

    // Check if search query is empty
    if (searchText == null || searchText.trim().isEmpty()) {
      supplierView.displayMessage("Please enter a search query.");
      updateSupplierData();
      return;
    }
    // Perform the search and get the results
    List<Supplier> searchResults = supplierManager.searchSupplier(searchText);

    // Update the customer data in the view with the search results
    supplierView.updateSuppliers(searchResults);

  }

  public void loadSuppliers() {
    supplierManager.loadSuppliers();
  }

  public void saveSuppliers() {
    supplierManager.saveSuppliers();
  }

  public void updateSupplierData() {
    supplierView.updateSuppliers(supplierManager.getSuppliers());
  }
}
