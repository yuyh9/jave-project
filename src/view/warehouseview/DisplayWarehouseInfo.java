package view.warehouseview;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class DisplayWarehouseInfo extends JFrame {
  private JTextArea textArea;
  private final WarehouseView warehouseView;

  public DisplayWarehouseInfo(WarehouseView warehouseView, String productsList) {
    this.warehouseView = warehouseView;
    initializeUI(productsList);
  }


  private void initializeUI(String productsList) {
    setTitle("Warehouse Information");
    setSize(400, 300);
    setLocationRelativeTo(warehouseView); // Set the location relative to the warehouseView frame

    textArea = new JTextArea(productsList);
    textArea.setEditable(false);
    textArea.setWrapStyleWord(true);
    textArea.setLineWrap(true);

    JScrollPane scrollPane = new JScrollPane(textArea);
    add(scrollPane);

    setVisible(true);
  }
}
