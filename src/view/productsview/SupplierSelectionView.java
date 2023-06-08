package view.productsview;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.supplier.Supplier;
import model.supplier.SupplierManager;

public class SupplierSelectionView extends JFrame {
  private JTable supplierTable;
  private JButton addButton, cancelButton, searchButton;
  private DefaultTableModel supplierTableModel;
  private JTextField searchField;
  private ProductView productView;
  private SupplierManager supplierManager;


  public SupplierSelectionView(ProductView productView, SupplierManager supplierManager) {
    this.productView = productView;
    this.supplierManager = supplierManager;
    initializeUI();
  }

  private void initializeUI() {
    setTitle("Supplier Selection");
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setSize(400, 300);
    setLocationRelativeTo(null);

    JPanel mainPanel = new JPanel(new BorderLayout());

    // Create the supplier table
    String[] columnNames = {"Supplier ID", "Supplier Name"};
    supplierTableModel = new DefaultTableModel(columnNames, 0);
    supplierTable = new JTable(supplierTableModel);
    supplierTable.setDefaultEditor(Object.class, null);
    JScrollPane scrollPane = new JScrollPane(supplierTable);

    mainPanel.add(scrollPane, BorderLayout.CENTER);

    // Create the search panel
    JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    searchField = new JTextField(15);
    searchButton = new JButton("Search");
    searchPanel.add(new JLabel("Search by Name:"));
    searchPanel.add(searchField);
    searchPanel.add(searchButton);

    // Create the button panel
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    addButton = new JButton("Add");
    cancelButton = new JButton("Cancel");
    buttonPanel.add(addButton);
    buttonPanel.add(cancelButton);

    // Add the panels to the main panel
    mainPanel.add(searchPanel, BorderLayout.NORTH);
    mainPanel.add(buttonPanel, BorderLayout.SOUTH);

    add(mainPanel);

    // Add ActionListener to the addButton
    addButton.addActionListener(e -> {
      int selectedRow = supplierTable.getSelectedRow();
      if (selectedRow != -1) {
        String supplierId = (String) supplierTable.getValueAt(selectedRow, 0);
        productView.updateSupplierField(supplierId);
        dispose();
      } else {
        JOptionPane.showMessageDialog(
            SupplierSelectionView.this,
            "Please select a supplier.",
            "Supplier Selection",
            JOptionPane.WARNING_MESSAGE
        );
      }
    });

    // Add ActionListener to the cancelButton
    cancelButton.addActionListener(e -> dispose());

    searchButton.addActionListener(e -> {
      String searchQuery = searchField.getText();
      List<Supplier> searchResults = supplierManager.searchSupplier(searchQuery);
      updateSuppliers(searchResults);
    });

  }

  public void updateSuppliers(List<Supplier> suppliers) {
    supplierTableModel.setRowCount(0);
    for (Supplier supplier : suppliers) {
      Object[] rowData = {supplier.getSupplierId(), supplier.getSupplierName()};
      supplierTableModel.addRow(rowData);
    }
  }
}
