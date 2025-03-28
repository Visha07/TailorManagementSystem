import javax.swing.*;

public class InvoicePanel extends JPanel {
    public InvoicePanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(new JLabel("Invoice Management"));
    }
}
