package model.supplier;

import java.util.ArrayList;
import java.util.List;
import model.product.Product;

public class Supplier {
  private String supplierId;
  private String supplierName;
  private String supplierEmail;
  private  String supplierPhone;


  public Supplier(String supplierId, String supplierName, String supplierEmail, String supplierPhone) {
    this.supplierId = supplierId;
    this.supplierName = supplierName;
    this.supplierEmail = supplierEmail;
    this.supplierPhone = supplierPhone;
  }

  public String getSupplierId() {
    return supplierId;
  }

  public void setSupplierId(String supplierId) {
    this.supplierId = supplierId;
  }

  public String getSupplierName() {
    return supplierName;
  }

  public void setSupplierName(String supplierName) {
    this.supplierName = supplierName;
  }

  public String getSupplierEmail() {
    return supplierEmail;
  }

  public void setSupplierEmail(String supplierEmail) {
    this.supplierEmail = supplierEmail;
  }

  public String getSupplierPhone() {
    return supplierPhone;
  }

  public void setSupplierPhone(String supplierPhone) {
    this.supplierPhone = supplierPhone;
  }

  @Override
  public String toString() {
    return this.supplierId;
  }
}
