import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TailorManagementGUI {

    private JFrame frame;
    private JTabbedPane tabbedPane;
    private JTable customerTable, orderTable, measurementTable, invoiceTable;
    private DefaultTableModel customerModel, orderModel, measurementModel, invoiceModel;
    private JTextField nameField, phoneField, addressField;
    private JTextField chestField, waistField, inseamField, priceField;
    private JComboBox<String> orderStatusBox;
    private JLabel statusLabel;

    public TailorManagementGUI() {
        frame = new JFrame("Silai Manager - Tailor Management System");
        frame.setSize(950, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        tabbedPane = new JTabbedPane();

        // Adding Tabs
        tabbedPane.addTab("Customers", createCustomerPanel());
        tabbedPane.addTab("Orders", createOrderPanel());
        tabbedPane.addTab("Measurements", createMeasurementPanel());
        tabbedPane.addTab("Billing", createBillingPanel());

        frame.add(tabbedPane, BorderLayout.CENTER);

        // Status Bar
        statusLabel = new JLabel("Welcome to Silai Manager!");
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        frame.add(statusLabel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private JPanel createCustomerPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        customerModel = new DefaultTableModel(new String[]{"ID", "Name", "Phone", "Address"}, 0);
        customerTable = new JTable(customerModel);
        panel.add(new JScrollPane(customerTable), BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        inputPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Phone:"));
        phoneField = new JTextField();
        inputPanel.add(phoneField);
        inputPanel.add(new JLabel("Address:"));
        addressField = new JTextField();
        inputPanel.add(addressField);

        JButton addButton = new JButton("Add Customer");
        JButton searchButton = new JButton("Search");
        inputPanel.add(addButton);
        inputPanel.add(searchButton);

        addButton.addActionListener(e -> addCustomer());
        searchButton.addActionListener(e -> searchCustomer());

        panel.add(inputPanel, BorderLayout.SOUTH);
        loadCustomers();
        return panel;
    }

    private JPanel createOrderPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        orderModel = new DefaultTableModel(new String[]{"Order ID", "Customer ID", "Date", "Status", "Price"}, 0);
        orderTable = new JTable(orderModel);
        panel.add(new JScrollPane(orderTable), BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        inputPanel.add(new JLabel("Customer ID:"));
        JTextField customerIdField = new JTextField();
        inputPanel.add(customerIdField);

        inputPanel.add(new JLabel("Status:"));
        orderStatusBox = new JComboBox<>(new String[]{"Pending", "In Progress", "Completed", "Delivered"});
        inputPanel.add(orderStatusBox);

        inputPanel.add(new JLabel("Total Price:"));
        priceField = new JTextField();
        inputPanel.add(priceField);

        JButton addOrderButton = new JButton("Add Order");
        JButton updateStatusButton = new JButton("Update Status");
        JButton searchButton = new JButton("Search");

        inputPanel.add(addOrderButton);
        inputPanel.add(updateStatusButton);
        inputPanel.add(searchButton);

        addOrderButton.addActionListener(e -> {
            try {
                int customerId = Integer.parseInt(customerIdField.getText());
                addOrder(customerId);
            } catch (NumberFormatException ex) {
                showStatus("Invalid Customer ID!", Color.RED);
            }
        });
        updateStatusButton.addActionListener(e -> updateOrderStatus());
        searchButton.addActionListener(e -> searchOrder());

        panel.add(inputPanel, BorderLayout.SOUTH);
        loadOrders();
        return panel;
    }

    private JPanel createMeasurementPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        measurementModel = new DefaultTableModel(new String[]{"ID", "Customer ID", "Chest", "Waist", "Inseam"}, 0);
        measurementTable = new JTable(measurementModel);
        panel.add(new JScrollPane(measurementTable), BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        inputPanel.add(new JLabel("Customer ID:"));
        JTextField customerIdField = new JTextField();
        inputPanel.add(customerIdField);

        inputPanel.add(new JLabel("Chest:"));
        chestField = new JTextField();
        inputPanel.add(chestField);

        inputPanel.add(new JLabel("Waist:"));
        waistField = new JTextField();
        inputPanel.add(waistField);

        inputPanel.add(new JLabel("Inseam:"));
        inseamField = new JTextField();
        inputPanel.add(inseamField);

        JButton addMeasurementButton = new JButton("Add Measurement");
        inputPanel.add(addMeasurementButton);
        addMeasurementButton.addActionListener(e -> {
            try {
                int customerId = Integer.parseInt(customerIdField.getText());
                addMeasurement(customerId);
            } catch (NumberFormatException ex) {
                showStatus("Invalid Customer ID!", Color.RED);
            }
        });

        panel.add(inputPanel, BorderLayout.SOUTH);
        loadMeasurements();
        return panel;
    }

    private JPanel createBillingPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        invoiceModel = new DefaultTableModel(new String[]{"ID", "Order ID", "Invoice Date", "Amount", "Status"}, 0);
        invoiceTable = new JTable(invoiceModel);
        panel.add(new JScrollPane(invoiceTable), BorderLayout.CENTER);

        JPanel formPanel = new JPanel(new GridLayout(4, 2));
        formPanel.add(new JLabel("Order ID:"));
        JTextField orderIdField = new JTextField();
        formPanel.add(orderIdField);

        formPanel.add(new JLabel("Amount:"));
        JTextField amountField = new JTextField();
        formPanel.add(amountField);

        formPanel.add(new JLabel("Payment Status:"));
        JComboBox<String> statusComboBox = new JComboBox<>(new String[]{"Pending", "Paid", "Cancelled"});
        formPanel.add(statusComboBox);

        JButton addInvoiceButton = new JButton("Add Invoice");
        JButton searchButton = new JButton("Search");

        formPanel.add(addInvoiceButton);
        formPanel.add(searchButton);

        addInvoiceButton.addActionListener(e -> {
            try {
                int orderId = Integer.parseInt(orderIdField.getText());
                double amount = Double.parseDouble(amountField.getText());
                addInvoice(orderId, amount, (String) statusComboBox.getSelectedItem());
            } catch (NumberFormatException ex) {
                showStatus("Invalid Order ID or Amount!", Color.RED);
            }
        });

        searchButton.addActionListener(e -> searchInvoice());

        panel.add(formPanel, BorderLayout.SOUTH);
        loadInvoices();
        return panel;
    }

    private void showStatus(String message, Color color) {
        statusLabel.setText(message);
        statusLabel.setForeground(color);
    }

    private void searchCustomer() {
        String name = JOptionPane.showInputDialog(frame, "Enter Customer Name:");
        if (name != null && !name.isEmpty()) {
            // Implement search logic here
            showStatus("Searching customer: " + name, Color.BLUE);
        }
    }

    private void searchOrder() {
        String orderId = JOptionPane.showInputDialog(frame, "Enter Order ID:");
        if (orderId != null && !orderId.isEmpty()) {
            // Implement search logic here
            showStatus("Searching order: " + orderId, Color.BLUE);
        }
    }

    private void searchInvoice() {
        String invoiceId = JOptionPane.showInputDialog(frame, "Enter Invoice ID:");
        if (invoiceId != null && !invoiceId.isEmpty()) {
            // Implement search logic here
            showStatus("Searching invoice: " + invoiceId, Color.BLUE);
        }
    }

    private void addCustomer() {
        String name = nameField.getText();
        String phone = phoneField.getText();
        String address = addressField.getText();
    
        if (name.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Name and Phone are required!");
            return;
        }
    
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO customers (name, phone, address) VALUES (?, ?, ?)")) {
            stmt.setString(1, name);
            stmt.setString(2, phone);
            stmt.setString(3, address);
            stmt.executeUpdate();
            loadCustomers();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Error adding customer: " + e.getMessage());
        }
    }
    
    private void loadCustomers() {
        customerModel.setRowCount(0);
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM customers");
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                customerModel.addRow(new Object[]{rs.getInt("id"), rs.getString("name"), rs.getString("phone"), rs.getString("address")});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Error loading customers: " + e.getMessage());
        }
    }

    private void addOrder(int customerId) {
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO orders (customer_id, status, total_price) VALUES (?, ?, ?)")) {
            stmt.setInt(1, customerId);
            stmt.setString(2, (String) orderStatusBox.getSelectedItem());
            stmt.setBigDecimal(3, new java.math.BigDecimal(priceField.getText()));
            stmt.executeUpdate();
            loadOrders();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Error adding order: " + e.getMessage());
        }
    }

    private void updateOrderStatus() {
        int selectedRow = orderTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Select an order to update.");
            return;
        }
    
        int orderId = (int) orderModel.getValueAt(selectedRow, 0);
        String newStatus = (String) orderStatusBox.getSelectedItem();
    
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE orders SET status = ? WHERE id = ?")) {
            stmt.setString(1, newStatus);
            stmt.setInt(2, orderId);
            stmt.executeUpdate();
            loadOrders();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Error updating order: " + e.getMessage());
        }
    }

    private void loadOrders() {
        orderModel.setRowCount(0);
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM orders");
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                orderModel.addRow(new Object[]{
                        rs.getInt("id"), rs.getInt("customer_id"), rs.getTimestamp("order_date"),
                        rs.getString("status"), rs.getBigDecimal("total_price")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Error loading orders: " + e.getMessage());
        }
    }

    private void addMeasurement(int customerId) {
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                 "INSERT INTO measurements (customer_id, chest, waist, inseam) VALUES (?, ?, ?, ?)")) {
            stmt.setInt(1, customerId);
            stmt.setFloat(2, Float.parseFloat(chestField.getText()));
            stmt.setFloat(3, Float.parseFloat(waistField.getText()));
            stmt.setFloat(4, Float.parseFloat(inseamField.getText()));
            stmt.executeUpdate();
            loadMeasurements();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Error adding measurement: " + e.getMessage());
        }
    }

    private void loadMeasurements() {
        measurementModel.setRowCount(0);
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM measurements");
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                measurementModel.addRow(new Object[]{
                    rs.getInt("id"), rs.getInt("customer_id"),
                    rs.getFloat("chest"), rs.getFloat("waist"), rs.getFloat("inseam")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Error loading measurements: " + e.getMessage());
        }
    }

    private void addInvoice(int orderId, double amount, String status) {
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                 "INSERT INTO invoices (order_id, amount, payment_status) VALUES (?, ?, ?)")) {
            stmt.setInt(1, orderId);
            stmt.setDouble(2, amount);
            stmt.setString(3, status);
            stmt.executeUpdate();
            loadInvoices();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Error adding invoice: " + e.getMessage());
        }
    }

    private void loadInvoices() {
        invoiceModel.setRowCount(0);
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM invoices");
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                invoiceModel.addRow(new Object[]{
                    rs.getInt("id"), rs.getInt("order_id"),
                    rs.getTimestamp("invoice_date"), rs.getDouble("amount"),
                    rs.getString("payment_status")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Error loading invoices: " + e.getMessage());
        }
    }
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TailorManagementGUI::new);
    }
}

