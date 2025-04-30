import java.awt.CardLayout;
import java.awt.Component;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class MainFrame extends JFrame {
    CardLayout cardLayout;
    JPanel mainPanel;
    HashMap<String, String> users = new HashMap();

    public MainFrame() {
        this.setTitle("Sleep Knight - Login System");
        this.setSize(400, 300);
        this.setDefaultCloseOperation(3);
        this.setLocationRelativeTo((Component)null);
        this.cardLayout = new CardLayout();
        this.mainPanel = new JPanel(this.cardLayout);
        //this.mainPanel.add(new LoginPanel(this), "login");
        //this.mainPanel.add(new RegisterPanel(this), "register");
        this.add(this.mainPanel);
        this.cardLayout.show(this.mainPanel, "login");
        this.setVisible(true);
    }

    public void showPanel(String name) {
        this.cardLayout.show(this.mainPanel, name);
    }

    public HashMap<String, String> getUsers() {
        return this.users;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new);
    }
}