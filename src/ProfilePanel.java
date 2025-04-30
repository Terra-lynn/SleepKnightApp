import javax.swing.*;
import java.awt.*;

public class ProfilePanel extends JPanel {
    private CardLayout cardLayout;
    private JPanel screen;
    private Image backgroundImage;

    public ProfilePanel(CardLayout cardLayout, JPanel screen) {
        this.cardLayout = cardLayout;
        this.screen = screen;

        ImageIcon bgIcon = new ImageIcon("images/pink.jpg");
        backgroundImage = bgIcon.getImage();
        setLayout(new BorderLayout());
        setOpaque(false);

        //Main content panel for screen adjustment
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setOpaque(false);

        //Constraints for GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 50, 10, 50);

        //Creating a bottom panel for the buttons
        JPanel buttonPanel = new JPanel(new GridLayout(1,4,10,0));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        //Creating components
        JButton homeBtn = createCloudButton("Home");
        JButton sleepBtn = createCloudButton("Sleep");
        JButton tipsBtn = createCloudButton("Tips");
        JButton profileBtn = createCloudButton("Profile");

        //Action Listeners
        homeBtn.addActionListener((e) -> cardLayout.show(screen, "HOME"));
        sleepBtn.addActionListener((e) -> cardLayout.show(screen, "SLEEP"));
        tipsBtn.addActionListener((e) -> cardLayout.show(screen, "TIPS"));
        profileBtn.addActionListener((e) -> cardLayout.show(screen, "PROFILE"));

        //Adding buttons to button panel
        buttonPanel.add(homeBtn);
        buttonPanel.add(sleepBtn);
        buttonPanel.add(tipsBtn);
        buttonPanel.add(profileBtn);

        //Adding to main panel
        //add(screen, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the background image scaled to the panel size
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    private static JButton createCloudButton(String text) {
        ImageIcon cloudIcon = new ImageIcon("images/SleepKnightCloud2.png");
        JButton button = new JButton(text, cloudIcon);
        button.setHorizontalTextPosition(0);
        button.setVerticalTextPosition(0);
        button.setFont(new Font("Serif", 1, 14));
        button.setForeground(new Color(153, 101, 21));
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setIconTextGap(0);
        return button;
    }
}