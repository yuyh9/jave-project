package view.productsview;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import model.warehouse.Warehouse;
import model.warehouse.WarehouseManager;

public class WarehouseSelectionView extends JFrame {

  private JTable warehouseTable;
  private JButton selectButton, cancelButton, searchButton;
  private DefaultTableModel warehouseTableModel;
  private JTextField searchField;
  private final ProductView productView;
  private final WarehouseManager warehouseManager;
  private final List<Warehouse> warehouses;


  public WarehouseSelectionView(ProductView productView, WarehouseManager warehouseManager) {
    this.productView = productView;
    this.warehouseManager = warehouseManager;
    this.warehouses = new ArrayList<>();
    initializeUI();
  }

  private void initializeUI() {
    setTitle("Warehouse Selection");
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setSize(400, 300);
    setLocationRelativeTo(null);

    JPanel mainPanel = new JPanel(new BorderLayout());

    // Create the warehouse table
    String[] columnNames = {"Warehouse ID", "Warehouse Name"};
    warehouseTableModel = new DefaultTableModel(columnNames, 0);
    warehouseTable = new JTable(warehouseTableModel);
    warehouseTable.setDefaultEditor(Object.class, null);
    JScrollPane scrollPane = new JScrollPane(warehouseTable);

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
    selectButton = new JButton("Select");
    cancelButton = new JButton("Cancel");
    buttonPanel.add(selectButton);
    buttonPanel.add(cancelButton);
    // Add the panels to the main panel
    mainPanel.add(searchPanel, BorderLayout.NORTH);
    mainPanel.add(buttonPanel, BorderLayout.SOUTH);

    add(mainPanel);

    // Add ActionListener to the selectButton
    selectButton.addActionListener(e -> {
      int selectedRow = warehouseTable.getSelectedRow();
      if (selectedRow != -1) {
        String warehouseLocation = (String) warehouseTable.getValueAt(selectedRow, 0);
        productView.updateWarehouseField(warehouseLocation);
        dispose();
      } else {
        JOptionPane.showMessageDialog(
            WarehouseSelectionView.this,
            "Please select a warehouse.",
            "Warehouse Selection",
            JOptionPane.WARNING_MESSAGE
        );
      }
    });

    // Add ActionListener to the cancelButton
    cancelButton.addActionListener(e -> dispose());
    searchButton.addActionListener(e -> {
      String searchQuery = searchField.getText();
      List<Warehouse> searchResults = warehouseManager.searchWarehouse(searchQuery);
      updateWarehouses(searchResults);
    });
  }

  public void updateWarehouses(List<Warehouse> warehouses) {
    warehouseTableModel.setRowCount(0);
    for (Warehouse warehouse : warehouses) {
      Object[] rowData = {warehouse.getWarehouseId(), warehouse.getWarehouseName()};
      warehouseTableModel.addRow(rowData);
    }
  }
}
