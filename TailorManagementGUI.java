import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TailorManagementGUI {
    private JFrame frame;
    private JTabbedPane tabbedPane;
    private DefaultTableModel customerTableModel, orderTableModel, measurementTableModel;
    
    public TailorManagementGUI() {
        frame = new JFrame("Tailor Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 500);
        
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Customers", createCustomerPanel());
        tabbedPane.addTab("Orders", createOrderPanel());
        tabbedPane.addTab("Measurements", createMeasurementPanel());
        
        frame.add(tabbedPane);
        frame.setVisible(true);
    }
    
    private JPanel createCustomerPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Table Model for Customers
        customerTableModel = new DefaultTableModel(new String[]{"Name", "Phone", "Email"}, 0);
        JTable customerTable = new JTable(customerTableModel);
        
        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        JTextField nameField = new JTextField();
        JTextField phoneField = new JTextField();
        JTextField emailField = new JTextField();
        
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Phone:"));
        inputPanel.add(phoneField);
        inputPanel.add(new JLabel("Email:"));
        inputPanel.add(emailField);

        JButton addButton = new JButton("Add Customer");
        addButton.addActionListener(e -> {
            String name = nameField.getText();
            String phone = phoneField.getText();
            String email = emailField.getText();
            if (!name.isEmpty() && !phone.isEmpty()) {
                customerTableModel.addRow(new Object[]{name, phone, email});
                nameField.setText("");
                phoneField.setText("");
                emailField.setText("");
            } else {
                JOptionPane.showMessageDialog(frame, "Name and Phone are required!");
            }
        });

        panel.add(new JScrollPane(customerTable), BorderLayout.CENTER);
        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(addButton, BorderLayout.SOUTH);
        return panel;
    }
    
    private JPanel createOrderPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Table Model for Orders
        orderTableModel = new DefaultTableModel(new String[]{"Order Details", "Status"}, 0);
        JTable orderTable = new JTable(orderTableModel);
        
        JTextField orderField = new JTextField();
        JComboBox<String> statusBox = new JComboBox<>(new String[]{"Pending", "In Progress", "Completed"});
        
        JPanel inputPanel = new JPanel(new GridLayout(2, 2));
        inputPanel.add(new JLabel("Order Details:"));
        inputPanel.add(orderField);
        inputPanel.add(new JLabel("Status:"));
        inputPanel.add(statusBox);

        JButton addButton = new JButton("Add Order");
        addButton.addActionListener(e -> {
            String order = orderField.getText();
            String status = (String) statusBox.getSelectedItem();
            if (!order.isEmpty()) {
                orderTableModel.addRow(new Object[]{order, status});
                orderField.setText("");
            } else {
                JOptionPane.showMessageDialog(frame, "Order details cannot be empty!");
            }
        });

        panel.add(new JScrollPane(orderTable), BorderLayout.CENTER);
        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(addButton, BorderLayout.SOUTH);
        return panel;
    }
    
    private JPanel createMeasurementPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Table Model for Measurements
        measurementTableModel = new DefaultTableModel(new String[]{"Customer", "Chest", "Waist", "Inseam", "Sleeve"}, 0);
        JTable measurementTable = new JTable(measurementTableModel);
        
        JTextField customerField = new JTextField();
        JTextField chestField = new JTextField();
        JTextField waistField = new JTextField();
        JTextField inseamField = new JTextField();
        JTextField sleeveField = new JTextField();
        
        JPanel inputPanel = new JPanel(new GridLayout(5, 2));
        inputPanel.add(new JLabel("Customer Name:"));
        inputPanel.add(customerField);
        inputPanel.add(new JLabel("Chest:"));
        inputPanel.add(chestField);
        inputPanel.add(new JLabel("Waist:"));
        inputPanel.add(waistField);
        inputPanel.add(new JLabel("Inseam:"));
        inputPanel.add(inseamField);
        inputPanel.add(new JLabel("Sleeve:"));
        inputPanel.add(sleeveField);

        JButton addButton = new JButton("Add Measurement");
        addButton.addActionListener(e -> {
            String customer = customerField.getText();
            String chest = chestField.getText();
            String waist = waistField.getText();
            String inseam = inseamField.getText();
            String sleeve = sleeveField.getText();
            if (!customer.isEmpty() && !chest.isEmpty() && !waist.isEmpty()) {
                measurementTableModel.addRow(new Object[]{customer, chest, waist, inseam, sleeve});
                customerField.setText("");
                chestField.setText("");
                waistField.setText("");
                inseamField.setText("");
                sleeveField.setText("");
            } else {
                JOptionPane.showMessageDialog(frame, "Customer name, chest, and waist measurements are required!");
            }
        });

        panel.add(new JScrollPane(measurementTable), BorderLayout.CENTER);
        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(addButton, BorderLayout.SOUTH);
        return panel;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(TailorManagementGUI::new);
    }
}
