import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class TailorManagementGUI {

    private JFrame frame;
    private JTabbedPane tabbedPane;
    private JTable table;
    private DefaultTableModel model;
    private JTextField nameField, phoneField, emailField, addressField;

    public TailorManagementGUI() {
        frame = new JFrame("Silai_Manager");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Customers", createCustomerPanel());
        tabbedPane.addTab("Orders", createOrderPanel());
        tabbedPane.addTab("Measurements", createMeasurementPanel());
        tabbedPane.addTab("Inventory", createInventoryPanel());
        tabbedPane.addTab("Billing", createBillingPanel());
        tabbedPane.addTab("Users", createUserPanel());
        tabbedPane.addTab("Reports", createReportsPanel());

        frame.add(tabbedPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private JPanel createCustomerPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Table setup
        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"ID", "Name", "Phone", "Email", "Address"});
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(5, 2));
        inputPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Phone:"));
        phoneField = new JTextField();
        inputPanel.add(phoneField);
        inputPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        inputPanel.add(emailField);
        inputPanel.add(new JLabel("Address:"));
        addressField = new JTextField();
        inputPanel.add(addressField);

        JButton addButton = new JButton("Add Customer");
        JButton deleteButton = new JButton("Delete Customer");
        inputPanel.add(addButton);
        inputPanel.add(deleteButton);

        panel.add(inputPanel, BorderLayout.SOUTH);

        // Button Actions
        addButton.addActionListener(e -> addCustomer());
        deleteButton.addActionListener(e -> deleteCustomer());

        loadCustomers();
        return panel;
    }

    private void loadCustomers() {
        model.setRowCount(0);
        String query = "SELECT * FROM customers";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"), rs.getString("name"), rs.getString("phone"),
                        rs.getString("email"), rs.getString("address")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Error loading customers: " + e.getMessage());
        }
    }

    private void addCustomer() {
        String name = nameField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();
        String address = addressField.getText();

        if (name.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Name and Phone are required!");
            return;
        }

        String query = "INSERT INTO customers (name, phone, email, address) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, phone);
            stmt.setString(3, email);
            stmt.setString(4, address);
            stmt.executeUpdate();
            loadCustomers();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Error adding customer: " + e.getMessage());
        }
    }

    private void deleteCustomer() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Select a customer to delete.");
            return;
        }

        int id = (int) model.getValueAt(selectedRow, 0);
        String query = "DELETE FROM customers WHERE id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            loadCustomers();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Error deleting customer: " + e.getMessage());
        }
    }

    private JPanel createOrderPanel() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Order Management UI Here"));
        return panel;
    }

    private JPanel createMeasurementPanel() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Measurement Management UI Here"));
        return panel;
    }

    private JPanel createInventoryPanel() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Inventory Management UI Here"));
        return panel;
    }

    private JPanel createBillingPanel() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Billing & Invoicing UI Here"));
        return panel;
    }

    private JPanel createUserPanel() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("User Management UI Here"));
        return panel;
    }

    private JPanel createReportsPanel() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Reports & Analytics UI Here"));
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TailorManagementGUI::new);
    }
}
