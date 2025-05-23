import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Main {
    private static JPanel screen;
    private static CardLayout cardLayout;
    private static HomePanel homePanel;

    public Main() {
    }

    public static void main(String[] args) {
        Database.initializingDatabase();
        SwingUtilities.invokeLater(Main::displayGUI);
    }

    public static void displayGUI() {
        //MainFrame frame = new MainFrame();
        JFrame frame = new JFrame("Sleep Knight App");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(3);
        frame.setSize(600, 800);
        cardLayout = new CardLayout();
        homePanel = new HomePanel(cardLayout, screen);
        screen = new JPanel(cardLayout);
        screen.add(new LoginPanel(cardLayout, screen), "LOGIN");
        screen.add(new RegisterPanel(cardLayout, screen), "REGISTER");
        screen.add(new HomePanel(cardLayout, screen), "HOME");
        screen.add(new SleepPanel(cardLayout, screen), "SLEEP");
        screen.add(new ProfilePanel(cardLayout, screen), "PROFILE");
        screen.add(new TipsPanel(cardLayout, screen), "TIPS");
        frame.add(screen);
        frame.setLocationRelativeTo((Component) null);
        frame.setVisible(true);
        cardLayout.show(screen, "LOGIN");
    }

    public static HomePanel getHomePanel() {return homePanel;}
}