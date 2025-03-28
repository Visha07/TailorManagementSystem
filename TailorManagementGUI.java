import javax.swing.*;
import java.awt.*;

public class TailorManagementGUI extends JFrame {
    public TailorManagementGUI() {
        setTitle("Tailor Management System");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Dashboard", new DashboardPanel());
        tabbedPane.addTab("Orders", new OrdersPanel());
        tabbedPane.addTab("Customers", new CustomersPanel());
        tabbedPane.addTab("Inventory", new InventoryPanel());
        tabbedPane.addTab("Suppliers", new SupplierPanel());
        tabbedPane.addTab("Invoices", new InvoicePanel());
        tabbedPane.addTab("Users", new UserManagementPanel());

        add(tabbedPane);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TailorManagementGUI().setVisible(true));
    }
}
