package data;

import java.util.ArrayList;
import java.util.List;
import model.supplier.Supplier;

public class SupplierData {

  private final TxtFileHandler txtFileHandler;
  private final List<Supplier> suppliers;

  public SupplierData() {
    this.txtFileHandler = new TxtFileHandler();
    this.suppliers = new ArrayList<>();
  }

  public List<Supplier> readSupplierData() {
    List<String> supplierData = txtFileHandler.readDataFromFile("supplier.txt");

    for (String line : supplierData) {
      String[] supplierFields = line.split(",");
      String supplierId = supplierFields[0];
      String supplierName = supplierFields[1];
      String email = supplierFields.length > 2 ? supplierFields[2] : "";
      String phone = supplierFields.length > 3 ? supplierFields[3] : "";
      boolean status = Boolean.parseBoolean(supplierFields[4]);

      Supplier supplier = new Supplier(supplierId, supplierName, email, phone);
      supplier.setAvailable(status);
      suppliers.add(supplier);

    }

    return suppliers;
  }

  public void writeSupplierData(List<Supplier> suppliers) {
    List<String> supplierData = new ArrayList<>();

    for (Supplier supplier : suppliers) {
      String line = supplier.getSupplierId() + ","
          + supplier.getSupplierName() + ","
          + supplier.getSupplierEmail() + ","
          + supplier.getSupplierPhone() + ","
          + supplier.isAvailable();

      supplierData.add(line);
    }

    txtFileHandler.writeDataToFile("supplier.txt", supplierData);
  }


}
