import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TailorManagementGUI {
    private JFrame frame;
    private JTabbedPane tabbedPane;
    
    public TailorManagementGUI() {
        frame = new JFrame("Tailor Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Customers", createCustomerPanel());
        tabbedPane.addTab("Orders", createOrderPanel());
        tabbedPane.addTab("Measurements", createMeasurementPanel());
        
        frame.add(tabbedPane);
        frame.setVisible(true);
    }
    
    private JPanel createCustomerPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        
        JTextArea customerList = new JTextArea();
        customerList.setEditable(false);
        
        JButton addButton = new JButton("Add Customer");
        addButton.addActionListener(e -> {
            String name = JOptionPane.showInputDialog("Enter Customer Name:");
            if (name != null && !name.isEmpty()) {
                customerList.append(name + "\n");
            }
        });
        
        panel.add(new JScrollPane(customerList), BorderLayout.CENTER);
        panel.add(addButton, BorderLayout.SOUTH);
        return panel;
    }
    
    private JPanel createOrderPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        
        JTextArea orderList = new JTextArea();
        orderList.setEditable(false);
        
        JButton addButton = new JButton("Add Order");
        addButton.addActionListener(e -> {
            String order = JOptionPane.showInputDialog("Enter Order Details:");
            if (order != null && !order.isEmpty()) {
                orderList.append(order + "\n");
            }
        });
        
        panel.add(new JScrollPane(orderList), BorderLayout.CENTER);
        panel.add(addButton, BorderLayout.SOUTH);
        return panel;
    }
    
    private JPanel createMeasurementPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        
        JTextArea measurementList = new JTextArea();
        measurementList.setEditable(false);
        
        JButton addButton = new JButton("Add Measurement");
        addButton.addActionListener(e -> {
            String measurement = JOptionPane.showInputDialog("Enter Measurement Details:");
            if (measurement != null && !measurement.isEmpty()) {
                measurementList.append(measurement + "\n");
            }
        });
        
        panel.add(new JScrollPane(measurementList), BorderLayout.CENTER);
        panel.add(addButton, BorderLayout.SOUTH);
        return panel;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(TailorManagementGUI::new);
    }
}
