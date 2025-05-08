import java.awt.*;
import javax.swing.*;

public class RegisterPanel extends JPanel {
    private CardLayout cardLayout;
    private JPanel screen;
    private Image backgroundImage;

    public RegisterPanel(CardLayout cardLayout, JPanel screen) {
        this.cardLayout = cardLayout;
        this.screen = screen;

        ImageIcon bgIcon = new ImageIcon("images/SleepKnight.png");
        backgroundImage = bgIcon.getImage();
        setLayout(new BorderLayout());
        setOpaque(false);

        //Creating styled labels with white text
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.WHITE);
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.WHITE);
        JLabel confirmLabel = new JLabel("Confirm Password:");
        confirmLabel.setForeground(Color.WHITE);
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setForeground(Color.WHITE);

        //Adding empty padding to make the form center
        JPanel topPadding = new JPanel();
        topPadding.setOpaque(false);
        topPadding.setPreferredSize(new Dimension(0, 350)); // 100px padding
        add(topPadding, BorderLayout.NORTH);

        //Registration form
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);

        //Creating a bottom panel for the buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setOpaque(false);

        //Creating components
        JTextField username = new JTextField(15);
        JPasswordField password = new JPasswordField(15);
        JPasswordField confirmPassword = new JPasswordField(15);
        JTextField email = new JTextField(15);
        JButton registerBtn = createCloudButton("Register");
        JButton backBtn = createCloudButton("Back");

        //Action Listeners
        registerBtn.addActionListener((e) -> {
            String enteredUsername = username.getText();
            String enteredPassword = new String(password.getPassword());
            String enteredConfirmedPassword = new String(confirmPassword.getPassword());
            String enteredEmail = email.getText();

            if (enteredUsername.isEmpty() || enteredPassword.isEmpty() || enteredConfirmedPassword.isEmpty() || enteredEmail.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields must be filled out.",
                        "Please fill in missing information.", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (!enteredPassword.equals(enteredConfirmedPassword)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match.",
                        "Please try again.", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (Database.insertUser(enteredUsername, enteredPassword, enteredEmail)) {
                JOptionPane.showMessageDialog(this, "Registration successful!");
                cardLayout.show(screen, "LOGIN");
            } else {
                JOptionPane.showMessageDialog(this, "Registration failed. Username may already exist.",
                        "Registration Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        backBtn.addActionListener((e) -> cardLayout.show(screen, "LOGIN"));

        //Setting up constraints for form panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        //Username row
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(usernameLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(username, gbc);

        //Password row
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(passwordLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(password, gbc);

        //Confirm password row
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(confirmLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(confirmPassword, gbc);

        //Email row
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(emailLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(email, gbc);

        //Adding buttons to button panel
        buttonPanel.add(registerBtn);
        buttonPanel.add(backBtn);

        //Adding to main panel
        add(formPanel, BorderLayout.CENTER);
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