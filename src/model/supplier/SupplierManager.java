package model.supplier;

import data.SupplierData;
import java.util.ArrayList;
import java.util.List;


public class SupplierManager {

  private List<Supplier> suppliers;
  private final SupplierData supplierData;

  public SupplierManager() {
    this.suppliers = new ArrayList<>();
    this.supplierData = new SupplierData();
  }

  public List<Supplier> getSuppliers() {
    return suppliers;
  }

  public void addSupplier(Supplier supplier) {
    suppliers.add(supplier);
  }

  public void activeSupplier(Supplier supplier) {
    boolean currentAvailability = supplier.isAvailable();
    supplier.setAvailable(!currentAvailability);
  }

  public Supplier getSupplierById(String supplierId) {
    for (Supplier supplier : suppliers) {
      if (supplier.getSupplierId().equals(supplierId)) {
        return supplier;
      }
    }
    return null; // Supplier with the given ID was not found
  }

  public void updateSupplier(Supplier supplier) {
    // Update the supplier in the list
    for (int i = 0; i < this.suppliers.size(); i++) {
      if (this.suppliers.get(i).getSupplierId().equals(supplier.getSupplierId())) {
        this.suppliers.set(i, supplier);
        break;
      }
    }
  }

  public List<Supplier> searchSupplier(String searchText) {
    List<Supplier> results = new ArrayList<>();
    String searchLower = searchText.toLowerCase();
    for (Supplier supplier : suppliers) {
      if (supplier.getSupplierId().toLowerCase().contains(searchLower) ||
          supplier.getSupplierName().toLowerCase().contains(searchLower)) {
        results.add(supplier);
      }
    }
    return results;
  }

  public void loadSuppliers() {
    suppliers = supplierData.readSupplierData();

  }

  public void saveSuppliers() {
    supplierData.writeSupplierData(suppliers);
  }

}