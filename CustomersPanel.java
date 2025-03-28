import javax.swing.*;
import java.awt.*;

public class CustomersPanel extends JPanel {
    public CustomersPanel() {
        setLayout(new BorderLayout());

        String[] columns = {"ID", "Name", "Phone", "Email", "Address"};
        Object[][] data = {{"1", "Alice Smith", "1234567890", "alice@example.com", "Street 123"}};

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
