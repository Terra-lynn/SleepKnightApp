import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

public class SleepPanel extends JPanel {
    private CardLayout cardLayout;
    private JPanel screen;
    private Image backgroundImage;
    private JSpinner bedTimeSpinner, wakeUpSpinner;
    private JLabel status;
    private JLabel sleepDurationLabel;


    public SleepPanel(CardLayout cardLayout, JPanel screen) {
        this.cardLayout = cardLayout;
        this.screen = screen;

        ImageIcon bgIcon = new ImageIcon("images/Starrynight2.png");
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

        //Sleep input components
        JLabel titleLabel = new JLabel("Track Your Sleep");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 30));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        //Sleep duration details
        sleepDurationLabel = new JLabel("Last night's sleep: ");
        sleepDurationLabel.setFont(new Font("Serif", Font.BOLD, 25));
        sleepDurationLabel.setForeground(Color.WHITE);
        sleepDurationLabel.setHorizontalAlignment(SwingConstants.CENTER);

        //time input panel
        JPanel timeInputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        timeInputPanel.setOpaque(false);

        JLabel bedTimeLabel = new JLabel("Bed Time");
        bedTimeLabel.setFont(new Font("Serif", Font.BOLD, 30));
        bedTimeLabel.setForeground(Color.WHITE);

        JLabel wakeUpLabel = new JLabel("Wake-up Time");
        wakeUpLabel.setFont(new Font("Serif", Font.BOLD, 30));
        wakeUpLabel.setForeground(Color.WHITE);

        // spinners
        bedTimeSpinner = createTimeSpinner(22, 0);
        wakeUpSpinner = createTimeSpinner(7, 0);

        timeInputPanel.add(bedTimeLabel);
        timeInputPanel.add(bedTimeSpinner);
        timeInputPanel.add(wakeUpLabel);
        timeInputPanel.add(wakeUpSpinner);

        //save button
        JButton saveButton = new JButton("Save Sleep Data");
        saveButton.setFont(new Font("Serif", Font.BOLD, 30));
        saveButton.addActionListener(new SaveSleepDataListener());
        saveButton.setBackground(Color.WHITE);
        saveButton.setForeground(Color.BLACK);

        status = new JLabel("");
        status.setFont(new Font("Serif", Font.PLAIN, 16));
        status.setForeground(Color.WHITE);
        status.setHorizontalAlignment(SwingConstants.CENTER);

        //To center the title
        JPanel titleWrapper = new JPanel();
        titleWrapper.setOpaque(false);
        titleWrapper.setLayout(new FlowLayout(FlowLayout.CENTER));
        titleWrapper.add(titleLabel);

        //To center the save button
        JPanel saveButtonWrapper = new JPanel();
        saveButtonWrapper.setOpaque(false);
        saveButtonWrapper.setLayout(new FlowLayout(FlowLayout.CENTER));
        saveButtonWrapper.add(saveButton);

        //To center the status message
        JPanel statusWrapper = new JPanel();
        statusWrapper.setOpaque(false);
        statusWrapper.setLayout(new FlowLayout(FlowLayout.CENTER));
        statusWrapper.add(status);

        //To left the hours slept message
        JPanel sleepDurationWrapper = new JPanel();
        sleepDurationWrapper.setOpaque(false);
        sleepDurationWrapper.setLayout(new FlowLayout(FlowLayout.LEFT));
        sleepDurationWrapper.add(sleepDurationLabel);

        //Transparent black panel behind the sleep tracker components
        JPanel transparentPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(new Color(0, 0, 0, 100)); // semi-transparent black
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2d.dispose();
                super.paintComponent(g);
            }
        };
        transparentPanel.setOpaque(false);
        transparentPanel.setLayout(new BoxLayout(transparentPanel, BoxLayout.Y_AXIS));
        transparentPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Add components to transparent panel
        transparentPanel.add(titleWrapper);
        transparentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        transparentPanel.add(timeInputPanel);
        transparentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        transparentPanel.add(saveButtonWrapper);
        transparentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        transparentPanel.add(statusWrapper);
        transparentPanel.add(sleepDurationWrapper);


        contentPanel.add(transparentPanel, gbc);

        //Creating a bottom panel for the buttons
        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 10, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //Creating components
        JButton homeBtn = createCloudButton("Home");
        JButton sleepBtn = createCloudButton("Sleep");
        JButton tipsBtn = createCloudButton("Tips");
        JButton profileBtn = createCloudButton("Profile");

        //Action Listeners
        homeBtn.addActionListener((e) -> {
            // Main.getHomePanel().refreshChart();
            cardLayout.show(screen, "HOME");

        });
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
        add(contentPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

    }

    private JSpinner createTimeSpinner(int hour, int minute) {
        SpinnerDateModel model = new SpinnerDateModel();
        JSpinner spinner = new JSpinner(model);
        JSpinner.DateEditor editor = new JSpinner.DateEditor(spinner, "hh:mm a");
        spinner.setEditor(editor);


        // set initial time
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        spinner.setValue(calendar.getTime());

        return spinner;
    }

    private class SaveSleepDataListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // get current user ID
            // String username = "";
            String currentUsername = CurrentUser.getCurrentUserName();

            // get time from spinners
            Date bedTime = ((SpinnerDateModel) bedTimeSpinner.getModel()).getDate();
            Date wakeUpTime = ((SpinnerDateModel) wakeUpSpinner.getModel()).getDate();

            // calculate duration
            long durationMillis = wakeUpTime.getTime() - bedTime.getTime();
            if(durationMillis < 0){
                durationMillis += 24 * 60 * 60 * 1000;
            }
            double durationHours = durationMillis / (1000.0 * 60 * 60);

            //Formatting to 2 decimal places
            DecimalFormat df = new DecimalFormat("#.##");
            String formattedHours = df.format(durationHours);

            boolean success = Database.savesSleepData(currentUsername,
                    new Time(bedTime.getTime()),
                    new Time(wakeUpTime.getTime()),
                    durationHours);

            // if saving sleep data successful
            if (success) {
                status.setText("Sleep data saved");
                status.setForeground(Color.WHITE);
                sleepDurationLabel.setText("Last night's sleep: " + formattedHours + " hours");

            } else {
                status.setText("Sleep data not saved");
                status.setForeground(Color.RED);
            }
        }
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