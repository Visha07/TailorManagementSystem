import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DashboardPanel extends JPanel {

    private JLabel totalOrdersLabel, totalRevenueLabel, pendingOrdersLabel, stockLabel;

    public DashboardPanel() {
        setLayout(new GridLayout(2, 2, 10, 10));

        totalOrdersLabel = createCard("Total Orders", "Loading...");
        totalRevenueLabel = createCard("Total Revenue", "Loading...");
        pendingOrdersLabel = createCard("Pending Orders", "Loading...");
        stockLabel = createCard("Available Stock", "Loading...");

        updateDashboard();
    }

    private JLabel createCard(String title, String value) {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder(title));
        JLabel label = new JLabel(value);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(label);
        add(panel);
        return label;
    }

    private void updateDashboard() {
        try (Connection conn = DatabaseUtil.getConnection()) {
            if (conn == null) {
                JOptionPane.showMessageDialog(this, "Database Connection Failed!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Ensure table names match `silai_db` schema
            totalOrdersLabel.setText(getCount(conn, "SELECT COUNT(*) FROM orders") + " Orders");
            totalRevenueLabel.setText("₹" + getSum(conn, "SELECT SUM(total_price) FROM orders"));
            pendingOrdersLabel.setText(getCount(conn, "SELECT COUNT(*) FROM orders WHERE status='Pending'") + " Pending");
            stockLabel.setText(getSum(conn, "SELECT SUM(quantity) FROM inventory") + " Items");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int getCount(Connection conn, String query) {
        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private double getSum(Connection conn, String query) {
        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            return rs.next() ? rs.getDouble(1) : 0.0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0.0;
        }
    }
}
