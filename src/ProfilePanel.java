import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProfilePanel extends JPanel {
    private static CardLayout cardLayout;
    private static JPanel screen;
    private static Image backgroundImage;
    //Fields to display user information
    private static JLabel usernameLabel = new JLabel();
    private static JLabel emailLabel = new JLabel();
    private static JTextField ageLabel = new JTextField(10);
    private static JTextField weightLabel = new JTextField(10);
    private static JComboBox<String> genderLabel = new JComboBox<>(new String[] {"Female", "Male", "Non-binary", "Other"});
    private static JTextField bedtimeLabel = new JTextField(10);
    private JLabel status;

    public ProfilePanel(CardLayout cardLayout, JPanel screen) {
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

        //Sleep input components
        JLabel titleLabel = new JLabel("Profile");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 50));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        //Main content panel for screen adjustment
        JPanel contentPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(new Color(0, 0, 0, 100)); // Semi-transparent black
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2d.dispose();
            }
        };
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        //Constraints for GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 10, 0);

        //Save message details
        status = new JLabel("");
        status.setFont(new Font("Serif", Font.PLAIN, 16));
        status.setForeground(Color.WHITE);
        status.setHorizontalAlignment(SwingConstants.CENTER);

        //To center the status message
        JPanel statusWrapper = new JPanel();
        statusWrapper.setOpaque(false);
        statusWrapper.setLayout(new FlowLayout(FlowLayout.CENTER));
        statusWrapper.add(status);

        contentPanel.setOpaque(false);
        contentPanel.setPreferredSize(new Dimension(800, 700));
        contentPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        Font userInfoFont = new Font("Serif", Font.PLAIN, 25);
        Color userInfoColor = new Color(255,255,255);
        contentPanel.add(titleLabel, gbc);

        //Username label
        gbc.gridy++;
        usernameLabel.setFont(userInfoFont);
        usernameLabel.setForeground(userInfoColor);
        contentPanel.add(usernameLabel, gbc);

        //Email label
        gbc.gridy++;
        emailLabel.setFont(userInfoFont);
        emailLabel.setForeground(userInfoColor);
        contentPanel.add(emailLabel, gbc);

        //Age
        gbc.gridy++;
        JPanel agePanel = new JPanel(new BorderLayout());
        agePanel.setOpaque(false);
        JLabel ageText = new JLabel("Age: ");
        ageText.setFont(userInfoFont);
        ageText.setForeground(userInfoColor);
        agePanel.add(ageText, BorderLayout.WEST);
        agePanel.add(ageLabel, BorderLayout.CENTER);
        contentPanel.add(agePanel, gbc);

        //Weight
        gbc.gridy++;
        JPanel weightPanel = new JPanel(new BorderLayout());
        weightPanel.setOpaque(false);
        JLabel weightText = new JLabel("Weight: ");
        weightText.setFont(userInfoFont);
        weightText.setForeground(userInfoColor);
        weightPanel.add(weightText, BorderLayout.WEST);
        weightPanel.add(weightLabel, BorderLayout.CENTER);
        contentPanel.add(weightPanel, gbc);

        //Gender
        gbc.gridy++;
        JPanel genderPanel = new JPanel(new BorderLayout());
        genderPanel.setOpaque(false);
        JLabel genderText = new JLabel("Gender: ");
        genderText.setFont(userInfoFont);
        genderText.setForeground(userInfoColor);
        genderPanel.add(genderText, BorderLayout.WEST);
        genderPanel.add(genderLabel, BorderLayout.CENTER);
        contentPanel.add(genderPanel);

        //Bedtime
        gbc.gridy++;
        JPanel bedtimePanel = new JPanel(new BorderLayout());
        bedtimePanel.setOpaque(false);
        JLabel bedtimeText = new JLabel("Bedtime: ");
        bedtimeText.setFont(userInfoFont);
        bedtimeText.setForeground(userInfoColor);
        bedtimePanel.add(bedtimeText, BorderLayout.WEST);
        bedtimePanel.add(bedtimeLabel, BorderLayout.CENTER);
        contentPanel.add(bedtimePanel, gbc);

        //Creating a bottom panel for the buttons
        JPanel buttonPanel = new JPanel(new GridLayout(1,4,10,0));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        //Creating components
        JButton homeBtn = createCloudButton("Home");
        JButton sleepBtn = createCloudButton("Sleep");
        JButton tipsBtn = createCloudButton("Tips");
        JButton profileBtn = createCloudButton("Profile");
        JButton saveBtn = new JButton("Save");
        saveBtn.setFont(new Font("Serif", Font.BOLD, 30));
        saveBtn.setBackground(Color.WHITE);
        saveBtn.setForeground(Color.BLACK);

        //Save Button location
        gbc.gridy++;
        gbc.insets = new Insets(20, 50, 10, 50);
        contentPanel.add(saveBtn, gbc);
        gbc.gridy++;
        contentPanel.add(statusWrapper, gbc);

        //Action Listeners
        homeBtn.addActionListener((e) -> cardLayout.show(screen, "HOME"));
        sleepBtn.addActionListener((e) -> cardLayout.show(screen, "SLEEP"));
        tipsBtn.addActionListener((e) -> cardLayout.show(screen, "TIPS"));
        profileBtn.addActionListener((e) ->{
            cardLayout.show(screen, "PROFILE");
            String currentUsername = CurrentUser.getCurrentUserName();
            ProfilePanel.loadUserProfile(currentUsername);
        });

        saveBtn.addActionListener(new saveProfileDataListener());

        //Adding buttons to button panel
        buttonPanel.add(homeBtn);
        buttonPanel.add(sleepBtn);
        buttonPanel.add(tipsBtn);
        buttonPanel.add(profileBtn);

        //Adding to main panel
        //add(screen, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        add(contentPanel, BorderLayout.CENTER);
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

    public static void loadUserProfile(String username) {
        UserProfile profile = Database.getUserProfile(username);
        if (profile != null) {
            usernameLabel.setText("Username: " + profile.getUsername());
            emailLabel.setText("Email: " + profile.getEmail());
            ageLabel.setText(String.valueOf(profile.getAge()));
            weightLabel.setText(String.valueOf(profile.getWeight()));
            genderLabel.setSelectedItem(profile.getGender());
            bedtimeLabel.setText(profile.getBedtime());
        } else {
            usernameLabel.setText("Failed to load profile.");
        }
    }

    private class saveProfileDataListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String currentUsername = CurrentUser.getCurrentUserName();
            int age = Integer.parseInt(ageLabel.getText());
            double weight = Double.parseDouble(weightLabel.getText());
            String gender = genderLabel.getSelectedItem().toString();
            String bedTime = bedtimeLabel.getText();

            boolean success = Database.updateUserProfile(currentUsername, age, weight, gender, bedTime);

            // if saving sleep data successful
            if (success) {
                status.setText("Sleep data saved");
                status.setForeground(Color.WHITE);
            } else {
                status.setText("Sleep data not saved");
                status.setForeground(Color.RED);
            }
        }
    }
}