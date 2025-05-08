import javax.swing.*;
import java.awt.*;

public class HomePanel extends JPanel {
    private CardLayout cardLayout;
    private JPanel screen;
    private Image backgroundImage;
    private Image graphImage;
    private Image sleepKnightLogo;

    public HomePanel(CardLayout cardLayout, JPanel screen) {
        this.cardLayout = cardLayout;
        this.screen = screen;

        ImageIcon bgIcon = new ImageIcon("images/Starrynight2.png");
        backgroundImage = bgIcon.getImage();
        setLayout(new BorderLayout());
        setOpaque(false);

        //Adding empty padding to make the form center
        JPanel topPadding = new JPanel();
        topPadding.setOpaque(false);
        topPadding.setPreferredSize(new Dimension(0, 50)); // 100px padding
        add(topPadding, BorderLayout.NORTH);

        //Loading graph image
        ImageIcon gIcon = new ImageIcon("images/sleepCycle.jpg");
        graphImage = gIcon.getImage();

        //Loading Sleep Knight Logo
        ImageIcon skIcon = new ImageIcon("images/sleepKnightTitle.png");
        sleepKnightLogo = skIcon.getImage();

        //Main content panel for screen adjustment
        JPanel contentPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(new Color(0, 0, 0, 100)); // semi-transparent black
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2d.dispose();
                int currentY = 20;
                int graphSpacing = 50;

                // Draw sleepKnightLogo first
                if (sleepKnightLogo != null) {
                    int logoX = (getWidth() - sleepKnightLogo.getWidth(this)) / 2;
                    g.drawImage(sleepKnightLogo, logoX, currentY, this);
                    currentY += sleepKnightLogo.getHeight(this) + 10; // add spacing below logo
                }

                currentY += graphSpacing;
                // Then draw graphImage below the logo
                if (graphImage != null) {
                    int graphX = (getWidth() - graphImage.getWidth(this)) / 2;
                    g.drawImage(graphImage, graphX, currentY, this);
                }
            }
        };
        contentPanel.setOpaque(false);

        add(contentPanel, BorderLayout.CENTER);

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
        profileBtn.addActionListener((e) ->{
            cardLayout.show(screen, "PROFILE");
            String currentUsername = CurrentUser.getCurrentUserName();
            ProfilePanel.loadUserProfile(currentUsername);
        });

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