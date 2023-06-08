package view.ordersview;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
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
import model.customer.Customer;
import model.customer.CustomerManager;

public class CustomerIdSelectionView extends JFrame {

  private JTable customerIdTable;
  private JButton addButton, cancelButton, searchButton;
  private DefaultTableModel customerTableModel;
  private JTextField searchField;
  private final OrderView orderView;
  private final CustomerManager customerManager;

  public CustomerIdSelectionView(OrderView orderView, CustomerManager customerManager) {
    this.orderView = orderView;
    this.customerManager = customerManager;
    initializeUI();
  }

  private void initializeUI() {
    setTitle("CustomerId Selection");
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setSize(400, 300);
    setLocationRelativeTo(null);

    JPanel mainPanel = new JPanel(new BorderLayout());
    // Create the supplier table
    String[] columnNames = {"Customer ID", "Customer Name"};
    customerTableModel = new DefaultTableModel(columnNames, 0);
    customerIdTable = new JTable(customerTableModel);
    customerIdTable.setDefaultEditor(Object.class, null);
    JScrollPane scrollPane = new JScrollPane(customerIdTable);

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
      int selectedRow = customerIdTable.getSelectedRow();
      if (selectedRow != -1) {
        String customerId = (String) customerIdTable.getValueAt(selectedRow, 0);
        orderView.updateCustomerIdField(customerId);
        dispose();
      } else {
        JOptionPane.showMessageDialog(
            CustomerIdSelectionView.this,
            "Please select a customer.",
            "customer Selection",
            JOptionPane.WARNING_MESSAGE
        );
      }
    });
    // Add ActionListener to the cancelButton
    cancelButton.addActionListener(e -> dispose());

    searchButton.addActionListener(e -> {
      String searchQuery = searchField.getText();
      List<Customer> searchResults = customerManager.searchCustomers(searchQuery);
      updateCustomerId(searchResults);
    });

  }

  public void updateCustomerId(List<Customer> customers) {
    customerTableModel.setRowCount(0);
    for (Customer customer : customers) {
      if (customer.isAvailable()) {
        Object[] rowData = {customer.getCustomerId(), customer.getName()};
        customerTableModel.addRow(rowData);
      }
    }
  }
}
