import javax.swing.*;
import java.awt.*;

public class OrdersPanel extends JPanel {
    public OrdersPanel() {
        setLayout(new BorderLayout());

        String[] columns = {"ID", "Customer", "Date", "Status", "Total Price"};
        Object[][] data = {{"1", "John Doe", "2025-03-28", "Pending", "$100"}};

        JTable table = new JTable(data, columns);
        JScrollPane scrollPane = new JScrollPane(table);

        JPanel panelButtons = new JPanel();
        panelButtons.add(new JButton("Add"));
        panelButtons.add(new JButton("Edit"));
        panelButtons.add(new JButton("Delete"));
        panelButtons.add(new JButton("Search"));

        add(scrollPane, BorderLayout.CENTER);
        add(panelButtons, BorderLayout.SOUTH);
    }
}
