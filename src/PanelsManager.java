//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelsManager {
    public PanelsManager() {
    }

    public static void main(String[] args) {
        PanelsManager panelsManager = new PanelsManager();
        panelsManager.showGUI();
    }

    public void showGUI() {
        JFrame frame = new JFrame("PanelsManager");
        frame.setDefaultCloseOperation(3);
        frame.setSize(800, 600);
        String LOGIN_PANEL = "LoginPanel";
        String REGISTER_PANEL = "RegisterPanel";
        String HOME_PANEL = "HomePanel";
        String SLEEP_PANEL = "SleepPanel";
        String PROFILE_PANEL = "ProfilePanel";
        String TIPS_PANEL = "TipsPanel";
        CardLayout cardLayout = new CardLayout();
        JPanel parentPanel = new JPanel(cardLayout);
        BackgroundPanel logInPanel = new BackgroundPanel("images/SleepKnight.png");
        JPanel signUpPanel = new JPanel();
        signUpPanel.add(new JLabel("Sign Up Panel"));
        JPanel homePanel = new JPanel() {
            ImageIcon logInPage = new ImageIcon("");

            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (this.logInPage.getImage() != null) {
                    g.drawImage(this.logInPage.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
                }

            }
        };
        parentPanel.add(logInPanel);
        parentPanel.add(homePanel);
        JPanel firstButtonPanel = new JPanel(new FlowLayout());
        firstButtonPanel.setOpaque(false);
        JButton logInButton = new JButton("Log In");
        JButton registerButton = new JButton("Register");
        firstButtonPanel.add(logInButton);
        firstButtonPanel.add(registerButton);
        logInPanel.add(firstButtonPanel);
        JPanel secondButtonPanel = new JPanel(new FlowLayout());
        JButton homeButton = new JButton("Home");
        JButton sleepButton = new JButton("Sleep");
        JButton profileButton = new JButton("Profile");
        JButton tipsButton = new JButton("Tips");
        logInButton.addActionListener((e) -> cardLayout.show(parentPanel, "loginPanel"));
        registerButton.addActionListener((e) -> cardLayout.show(parentPanel, "loginPanel"));
        homeButton.addActionListener((e) -> cardLayout.show(parentPanel, "homePanel"));
        sleepButton.addActionListener((e) -> cardLayout.show(parentPanel, "sleepPanel"));
        secondButtonPanel.add(homeButton);
        secondButtonPanel.add(sleepButton);
        secondButtonPanel.add(profileButton);
        secondButtonPanel.add(tipsButton);
        JPanel controlPanel = new JPanel();
        controlPanel.add(logInButton);
        controlPanel.add(homeButton);
        frame.add(logInPanel);
        frame.setVisible(true);
    }

    class BackgroundPanel extends JPanel {
        private Image background;

        public BackgroundPanel(String imagePath) {
            this.background = (new ImageIcon(imagePath)).getImage();
            this.setLayout(new GridLayout());
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (this.background != null) {
                g.drawImage(this.background, 0, 0, this.getWidth(), this.getHeight(), this);
            }

        }
    }
}
