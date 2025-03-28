import javax.swing.*;
import java.awt.*;

public class SupplierPanel extends JPanel {
    public SupplierPanel() {
        setLayout(new BorderLayout());

        String[] columns = {"ID", "Name", "Phone", "Email", "Address"};
        Object[][] data = {{"1", "ABC Suppliers", "9876543210", "abc@suppliers.com", "Warehouse St"}};

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
