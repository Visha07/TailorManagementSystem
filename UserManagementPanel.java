import javax.swing.*;

public class UserManagementPanel extends JPanel {
    public UserManagementPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(new JLabel("User Management"));
    }
}
