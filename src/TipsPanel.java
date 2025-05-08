import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TipsPanel extends JPanel {
    private CardLayout cardLayout;
    private JPanel screen;
    private Image backgroundImage;
    private JTextArea causeDisplayArea;
    private JTextArea tipDisplayArea;
    private JComboBox<String> skillLevelComboBox;

    // Map to hold both causes and recommendations
    private Map<String, TipContent> tipCollections = new HashMap<>();

    public TipsPanel(CardLayout cardLayout, JPanel screen) {
        this.cardLayout = cardLayout;
        this.screen = screen;

        // Initialize tip collections
        initializeTips();

        // Setup background
        ImageIcon bgIcon = new ImageIcon("images/Starrynight2.png");
        backgroundImage = bgIcon.getImage();
        setLayout(new BorderLayout());
        setOpaque(false);

        //Adding empty padding to make the form center
        JPanel topPadding = new JPanel();
        topPadding.setOpaque(false);
        topPadding.setPreferredSize(new Dimension(0, 50)); // 100px padding
        add(topPadding, BorderLayout.NORTH);

        // Create main content panel
        JPanel contentPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(new Color(0, 0, 0, 100)); // Semi-transparent black
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2d.dispose();
            }
        };
        contentPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);

        //Tips label
        JLabel titleLabel = new JLabel("Sleep Information");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 50));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        contentPanel.setOpaque(false);
        contentPanel.add(titleLabel, gbc);

        // Skill Level ComboBox
        JLabel skillLabel = new JLabel();
        skillLabel.setFont(new Font("Arial", Font.BOLD, 16));
        skillLabel.setForeground(Color.WHITE);
        skillLevelComboBox = new JComboBox<>(new String[]{"Insomnia", "Sleep Apnea", "Restless Legs Syndrome", "Narcolepsy"});
        skillLevelComboBox.addActionListener(e -> updateTips());
        skillLevelComboBox.setFont(new Font("Serif", Font.BOLD, 25));

        // Cause Display Area
        causeDisplayArea = new JTextArea(8, 40);
        causeDisplayArea.setEditable(false);
        causeDisplayArea.setLineWrap(true);
        causeDisplayArea.setWrapStyleWord(true);
        JScrollPane causeScrollPane = new JScrollPane(causeDisplayArea);
        causeScrollPane.setBorder(BorderFactory.createTitledBorder("Causes"));

        // Recommendation Display Area
        tipDisplayArea = new JTextArea(8, 40);
        tipDisplayArea.setEditable(false);
        tipDisplayArea.setLineWrap(true);
        tipDisplayArea.setWrapStyleWord(true);
        JScrollPane recommendationScrollPane = new JScrollPane(tipDisplayArea);
        recommendationScrollPane.setBorder(BorderFactory.createTitledBorder("Recommendations"));

        // Layout Components
        GridBagConstraints tbc = new GridBagConstraints();
        tbc.insets = new Insets(10, 10, 10, 10);
        tbc.gridx = 0;
        tbc.gridy = 0;
        contentPanel.add(skillLabel, tbc);

        tbc.gridy = 1;
        contentPanel.add(skillLevelComboBox, tbc);

        tbc.gridy = 2;
        contentPanel.add(causeScrollPane, tbc);

        tbc.gridy = 3;
        contentPanel.add(recommendationScrollPane, tbc);

        // Button Panel
        JPanel buttonPanel = createButtonPanel();

        add(contentPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Load default tips
        updateTips();
    }

    private void initializeTips() {
        //All information was taken from https://www.mayoclinic.org/diseases-conditions/insomnia/symptoms-causes/syc-20355167
        tipCollections.put("Insomnia", new TipContent(
                loadTipsArrayFromFile("SleepTipFiles/Insomnia"),
                loadTipsArrayFromFile("SleepTipFiles/Insomnia_Recommendations")
        ));
        //All information was taken from https://www.mayoclinic.org/diseases-conditions/sleep-apnea/symptoms-causes/syc-20377631
        tipCollections.put("Sleep Apnea", new TipContent(
                loadTipsArrayFromFile("SleepTipFiles/Sleep_Apnea"),
                loadTipsArrayFromFile("SleepTipFiles/Sleep_Apnea_Recommendations")
        ));
        //All information was taken from https://www.mayoclinic.org/diseases-conditions/restless-legs-syndrome/symptoms-causes/syc-20377168
        tipCollections.put("Restless Legs Syndrome", new TipContent(
                loadTipsArrayFromFile("SleepTipFiles/Restless_Legs_Syndrome"),
                loadTipsArrayFromFile("SleepTipFiles/Restless_Legs_Syndrome_Recommendations")
        ));
        //All information was taken from https://www.mayoclinic.org/diseases-conditions/narcolepsy/symptoms-causes/syc-20375497
        tipCollections.put("Narcolepsy", new TipContent(
                loadTipsArrayFromFile("SleepTipFiles/Narcolepsy"),
                loadTipsArrayFromFile("SleepTipFiles/Narcolepsy_Recommendations")
        ));
    }

    private void updateTips() {
        String selectedLevel = (String) skillLevelComboBox.getSelectedItem();
        TipContent tipContent = tipCollections.get(selectedLevel);

        if (tipContent == null) {
            causeDisplayArea.setText("No cause information available.");
            tipDisplayArea.setText("No recommendation available.");
            return;
        }

        // Causes section
        StringBuilder causeText = new StringBuilder("What causes " + selectedLevel + "?\n\n");
        for (String cause : tipContent.causes) {
            causeText.append("").append(cause).append("\n");
        }
        causeDisplayArea.setText(causeText.toString());

        // Recommendations section
        StringBuilder recommendationText = new StringBuilder("Recommendations for " + selectedLevel + ":\n\n");
        for (String tip : tipContent.recommendations) {
            recommendationText.append("").append(tip).append("\n");
        }
        tipDisplayArea.setText(recommendationText.toString());
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 10, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton homeBtn = createCloudButton("Home");
        JButton sleepBtn = createCloudButton("Sleep");
        JButton tipsBtn = createCloudButton("Tips");
        JButton profileBtn = createCloudButton("Profile");

        homeBtn.addActionListener(e -> cardLayout.show(screen, "HOME"));
        sleepBtn.addActionListener(e -> cardLayout.show(screen, "SLEEP"));
        tipsBtn.addActionListener(e -> cardLayout.show(screen, "TIPS"));
        profileBtn.addActionListener((e) ->{
            cardLayout.show(screen, "PROFILE");
            String currentUsername = CurrentUser.getCurrentUserName();
            ProfilePanel.loadUserProfile(currentUsername);
        });

        buttonPanel.add(homeBtn);
        buttonPanel.add(sleepBtn);
        buttonPanel.add(tipsBtn);
        buttonPanel.add(profileBtn);

        return buttonPanel;
    }

    private static JButton createCloudButton(String text) {
        ImageIcon cloudIcon = new ImageIcon("images/SleepKnightCloud2.png");
        JButton button = new JButton(text, cloudIcon);
        button.setHorizontalTextPosition(0);
        button.setVerticalTextPosition(0);
        button.setFont(new Font("Serif", Font.BOLD, 14));
        button.setForeground(new Color(153, 101, 21));
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setIconTextGap(0);
        return button;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    private String[] loadTipsArrayFromFile(String fileName) {
        java.util.List<String> tips = new java.util.ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                tips.add(line);
            }
        } catch (IOException e) {
            tips.add("Failed to load tips from file: " + fileName);
            e.printStackTrace();
        }
        return tips.toArray(new String[0]);
    }

    private static class TipContent {
        String[] causes;
        String[] recommendations;

        TipContent(String[] causes, String[] recommendations) {
            this.causes = causes;
            this.recommendations = recommendations;
        }
    }
}
