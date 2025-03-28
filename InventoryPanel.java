import javax.swing.*;
import java.awt.*;

public class InventoryPanel extends JPanel {
    public InventoryPanel() {
        setLayout(new BorderLayout());

        String[] columns = {"ID", "Item Name", "Category", "Stock", "Price"};
        Object[][] data = {{"1", "Cotton Fabric", "Fabric", "50", "$5"}};

        JTable table = new JTable(data, columns);
        JScrollPane scrollPane = new JScrollPane(table);

        JPanel panelButtons = new JPanel();
        panelButtons.add(new JButton("Add"));
        panelButtons.add(new JButton("Edit"));
        panelButtons.add(new JButton("Delete"));

        add(scrollPane, BorderLayout.CENTER);
        add(panelButtons, BorderLayout.SOUTH);
    }
}
