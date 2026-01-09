package iseProject;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.io.*;
import java.text.SimpleDateFormat;
import com.toedter.calendar.JDateChooser;
import org.jfree.chart.*;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.*;
import org.jfree.data.general.*;
import org.jfree.chart.ui.RectangleInsets;


public class UImanager {
    private static JPanel mainPanel;
    private CardLayout cardLayout;
    private JPanel contentArea;
    public static JFrame frame;
    private static UserManager userManager;
    private static AdminManager adminManager;
    private RevenueManager revenueManager;
    private GrowthManager growthManager;
    private static ContentManager cm;
    private AudienceManager audienceManager;
    private TimeManager timeManager;

    // Green theme colors
    private Color darkGreen = new Color(20, 53, 45);
    private Color primaryGreen = new Color(34, 139, 34);
    private Color lightGreen = new Color(102, 187, 106);
    private Color accentGreen = new Color(16, 185, 129);
    private Color mintGreen = new Color(67, 160, 71);
    private Color backgroundColor = new Color(247, 250, 252);
    private Color cardColor = Color.WHITE;
    private Color textPrimary = new Color(26, 32, 44);
    private Color textSecondary = new Color(113, 128, 150);

    public UImanager(UserManager userManager, AdminManager adminManager) {
        this.userManager = userManager;
        this.adminManager = adminManager;

// Initialize managers
        this.revenueManager = new RevenueManager();
        this.growthManager = new GrowthManager();
        this.cm = new ContentManager();
        this.audienceManager = new AudienceManager();
        this.timeManager = new TimeManager();
        frame = new JFrame("CreatorHub"); //frame name
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // exit button click krne pr close kerdo
        frame.setSize(1250, 670); //size of the frame
        frame.setResizable(false);// resize nahi hoskta
        frame.setLayout(null); // for absolute positioning
        frame.setVisible(true);
        mainPanel = new JPanel();
        mainPanel.setBounds(0, 0, 1250, 670);
        mainPanel.setLayout(null);
        frame.add(mainPanel);
    }

    public static Font bingoWoodFont(float size) {
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, UImanager.class.getResourceAsStream("bingoWood.otf"));
            return font.deriveFont(size); //font create krke return kerdo
        } catch (Exception e) {
            e.printStackTrace();
            return new Font("SansSerif", Font.BOLD, (int) size); // fallback font
        }
    }

    public static void showWelcomeUI() {
        mainPanel.removeAll();
        JPanel bColor = null;

        try {
            BufferedImage bg = ImageIO.read(UImanager.class.getResource("bgrnd.png"));

            bColor = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
                }
            };

            bColor.setBounds(0, 0, 1250, 670);
            bColor.setLayout(null);
            mainPanel.add(bColor);
            //frame.setComponentZOrder(bColor, 0);

        } catch (IOException e) {
            System.out.println("Image not loaded: " + e.getMessage());
        }

        // everything below is EXACTLY your code
        JLabel LoginText = new JLabel();
        JLabel tagline = new JLabel();
        tagline.setFont(bingoWoodFont(30f));
        LoginText.setFont(bingoWoodFont(50f));
        tagline.setBounds(465, 250, 600, 50);
        LoginText.setBounds(490, 200, 300, 50);
        tagline.setForeground(Color.WHITE);
        tagline.setText("Create more. Stress less.");
        LoginText.setForeground(Color.WHITE);
        LoginText.setText("CreatorHub");
        LoginText.setOpaque(false);
        bColor.add(LoginText);
        bColor.add(tagline);

        RoundedCornerButton button1 = new RoundedCornerButton("sign up");
        button1.setFont(bingoWoodFont(20f));
        button1.setBounds(560, 330, 110, 40);
        button1.setForeground(Color.WHITE);


        RoundedCornerButton button2 = new RoundedCornerButton("login");
        button2.setFont(bingoWoodFont(20f));
        button2.setBounds(560, 380, 110, 40);
        button2.setForeground(Color.WHITE);
        button1.addActionListener(e -> {
            mainPanel.removeAll();
            showSignUpUI();         // show signup page

            mainPanel.revalidate();
            mainPanel.repaint();
        });

        button2.addActionListener(e -> {// remove welcome panel
            mainPanel.removeAll();
            showLoginUI();          // show login page

            mainPanel.revalidate();
            mainPanel.repaint();
        });

        bColor.add(button1);
        bColor.add(button2);


        mainPanel.revalidate();
        mainPanel.repaint();
    }

    public static void showLoginUI() {
        mainPanel.removeAll(); // clear previous UI
        mainPanel.setLayout(null);
        JPanel bColor = new JPanel();
        bColor.setBounds(0, 0, 1250, 670);
        bColor.setBackground(Color.decode("#f0f0f0"));
        bColor.setLayout(null);
        bColor.setOpaque(true);
        mainPanel.add(bColor);

        try {
            BufferedImage cover = ImageIO.read(UImanager.class.getResource("side.png"));
            Image scaledCover = cover.getScaledInstance(625, 670, Image.SCALE_SMOOTH);
            JLabel coverBackground = new JLabel(new ImageIcon(scaledCover));
            coverBackground.setBounds(0, 0, 625, 670);
            mainPanel.add(coverBackground);
            mainPanel.setComponentZOrder(coverBackground, 1);
        } catch (IOException e) {
            System.err.println("Image could not be loaded: " + e.getMessage());
        }

        JPanel innerBox = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                try {
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(Color.decode("#e8e8e8"));
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                } finally {
                    g2.dispose();
                }
            }
        };

        innerBox.setBounds(775, 130, 320, 410);
        innerBox.setLayout(null);
        mainPanel.add(innerBox);

        JLabel LoginText = new JLabel();
        LoginText.setFont(bingoWoodFont(24f));
        LoginText.setBounds(100, 20, 150, 40);
        LoginText.setBackground(Color.decode("#e8e8e8"));
        LoginText.setForeground(Color.BLACK);
        LoginText.setText(" User Login");
        innerBox.add(LoginText);
        JLabel back = new JLabel("<HTML>← back</HTML>");
        back.setBounds(15, 30, 100, 20);
        back.setFont(bingoWoodFont(12f));
        back.setForeground(new Color(70, 70, 70)); // Softer than pure black
        back.setCursor(new Cursor(Cursor.HAND_CURSOR));

        back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                back.setForeground(new Color(40, 40, 40));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                back.setForeground(new Color(70, 70, 70));
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                mainPanel.removeAll();
                showWelcomeUI();
                mainPanel.revalidate();
                mainPanel.repaint();
            }
        });

        innerBox.add(back);
        JTextField emailField = new JTextField("  Email ID");
        emailField.setBounds(80, 80, 200, 40);
        emailField.setFont(new Font("Arial", Font.PLAIN, 14));
        emailField.setForeground(Color.GRAY); // Placeholder color
        emailField.setBorder(null);
        emailField.setBackground(Color.decode("#f8f8f8"));
        innerBox.add(emailField);

        try {
            // Load image from resource folder or same package
            BufferedImage emailImg = ImageIO.read(UImanager.class.getResource("mail.png"));

            // Resize to 40x40
            Image scaledEmail = emailImg.getScaledInstance(30, 30, Image.SCALE_SMOOTH);

            // Set to JLabel
            JLabel emailIcon = new JLabel(new ImageIcon(scaledEmail));
            emailIcon.setBounds(40, 80, 40, 40); // x, y inside innerBox
            emailIcon.setBackground(Color.decode("#32620e"));
            innerBox.add(emailIcon);
            emailIcon.setOpaque(true);

            innerBox.setComponentZOrder(emailIcon, 0);

        } catch (IOException e) {
            System.err.println("Couldn't load email icon: " + e.getMessage());
        }
        try {
            // Load image from resource folder or same package
            BufferedImage emailImg = ImageIO.read(UImanager.class.getResource("lock.png"));

            // Resize to 40x40
            Image scaledLock = emailImg.getScaledInstance(25, 25, Image.SCALE_SMOOTH);

            // Set to JLabel
            JLabel passwordIcon = new JLabel(new ImageIcon(scaledLock));
            passwordIcon.setBounds(40, 140, 40, 40); // x, y inside innerBox
            passwordIcon.setBackground(Color.decode("#32620e"));
            innerBox.add(passwordIcon);
            passwordIcon.setOpaque(true);
            innerBox.setComponentZOrder(passwordIcon, 0);

        } catch (IOException e) {
            System.err.println("Couldn't load password icon: " + e.getMessage());
        }

        emailField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (emailField.getText().equals("  Email ID")) {
                    emailField.setText("");
                    emailField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (emailField.getText().isEmpty()) {
                    emailField.setText("  Email ID");
                    emailField.setForeground(Color.GRAY);
                }
            }
        });
// Password Input Field
        JPasswordField passwordField = new JPasswordField("  Password");
        passwordField.setBounds(80, 140, 200, 40);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setForeground(Color.GRAY);
        passwordField.setEchoChar((char) 0); // Show plain text
        passwordField.setBorder(null);
        passwordField.setBackground(Color.decode("#f8f8f8"));
        innerBox.add(passwordField);

// Placeholder logic
        passwordField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (String.valueOf(passwordField.getPassword()).equals("  Password")) {
                    passwordField.setText("");
                    passwordField.setForeground(Color.BLACK);
                    passwordField.setEchoChar('●'); // Typical password mask
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (String.valueOf(passwordField.getPassword()).isEmpty()) {
                    passwordField.setText("  Password");
                    passwordField.setForeground(Color.GRAY);
                    passwordField.setEchoChar((char) 0); // Show plain text again
                }
            }
        });
        JCheckBox rememberMe = new JCheckBox("Remember me");

// Position: left-aligned below the password field
        rememberMe.setBounds(42, 200, 120, 20); // Adjust y as needed
        rememberMe.setFont(new Font("Arial", Font.PLAIN, 12)); // Small text
        rememberMe.setBackground(Color.decode("#e8e8e8")); // Match innerBox background
        rememberMe.setFocusPainted(false); // Clean UI look
        rememberMe.setBorder(null);
        rememberMe.setOpaque(false); // Make it blend with background
        JLabel forgotPassword = new JLabel("<HTML>Forgot password?</HTML>");
        forgotPassword.setBounds(180, 200, 120, 20); // Same y as checkbox, to align on one line
        forgotPassword.setFont(bingoWoodFont(12f));
        forgotPassword.setForeground(Color.BLACK);
        forgotPassword.setCursor(new Cursor(Cursor.HAND_CURSOR));

// Optional: Add click behavior
        forgotPassword.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mainPanel.removeAll();
                showForgotPasswordUI();
                mainPanel.revalidate();
                mainPanel.repaint();
            }
        });


        JLabel signupinstead = new JLabel("<HTML>Don't have an account? Sign-up instead</HTML>");
        signupinstead.setBounds(45, 340, 250, 20); // Same y as checkbox, to align on one line
        signupinstead.setFont(bingoWoodFont(12f));
        signupinstead.setForeground(Color.BLACK);
        signupinstead.setCursor(new Cursor(Cursor.HAND_CURSOR));

// Optional: Add click behavior
        signupinstead.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mainPanel.removeAll();
                showSignUpUI();
                mainPanel.revalidate();
                mainPanel.repaint();
            }
        });
// Add both to innerBox (not frame!)


        RoundedCornerButton enterButton = new RoundedCornerButton("login");
        enterButton.setForeground(Color.WHITE);
        enterButton.setFont(bingoWoodFont(20f));
        JLabel loginErrorLabel = new JLabel();
        loginErrorLabel.setBounds(100, 300, 300, 20);
        loginErrorLabel.setFont(bingoWoodFont(12f));
        loginErrorLabel.setForeground(Color.RED);
        innerBox.add(loginErrorLabel);

        innerBox.add(forgotPassword);
        innerBox.add(signupinstead);
        innerBox.add(rememberMe);
        enterButton.setBounds(115, 250, 90, 40);
        enterButton.addActionListener(e -> {
            String userInput = emailField.getText().trim();
            String pass = new String(passwordField.getPassword());

            if (userManager.login(userInput, pass)) {
                loginErrorLabel.setText("");

                // Update status if Remember Me is checked
                if (rememberMe.isSelected()) {
                    if (userInput.equalsIgnoreCase("admin") || userInput.equalsIgnoreCase("ayesha.naveed3101@gmail.com")) {
                        AdminManager.updateAdminStatus("active");
                    } else {
                        UserManager.updateUserStatus(userInput, "active");
                    }
                }

                // Open dashboard based on admin check
                if (userInput.equalsIgnoreCase("admin") || userInput.equalsIgnoreCase("ayesha.naveed3101@gmail.com")) {
                    openAdminDashboard();
                } else {
                    openCreatorDashboard(userInput);
                }

            } else {
                loginErrorLabel.setText("Invalid credentials");
            }
        });
        mainPanel.setComponentZOrder(innerBox, 0); // bring innerBox to front

        mainPanel.setComponentZOrder(bColor, 2);

        innerBox.add(enterButton);
        mainPanel.revalidate();
        mainPanel.repaint();
        frame.setVisible(true);

    }

    public static void showSignUpUI() {
        mainPanel.removeAll(); // Clear previous UI
        mainPanel.setLayout(null);
        JPanel bColor = new JPanel();
        bColor.setBounds(0, 0, 1250, 670);
        bColor.setBackground(Color.decode("#f0f0f0"));
        bColor.setLayout(null);
        bColor.setOpaque(true);
        mainPanel.add(bColor);

        try {
            BufferedImage cover = ImageIO.read(UImanager.class.getResource("side.png"));
            Image scaledCover = cover.getScaledInstance(625, 670, Image.SCALE_SMOOTH);
            JLabel coverBackground = new JLabel(new ImageIcon(scaledCover));
            coverBackground.setBounds(0, 0, 625, 670);
            mainPanel.add(coverBackground);
            mainPanel.setComponentZOrder(coverBackground, 0);
        } catch (IOException e) {
            System.err.println("Image could not be loaded: " + e.getMessage());
        }

        JPanel innerBox = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                try {
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(Color.decode("#e8e8e8"));
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                } finally {
                    g2.dispose();
                }
            }
        };
        innerBox.setBounds(775, 130, 320, 410);
        innerBox.setLayout(null);
        innerBox.setOpaque(true);
        mainPanel.add(innerBox);
        mainPanel.setComponentZOrder(innerBox, 0);

        JLabel LoginText = new JLabel();
        LoginText.setFont(bingoWoodFont(24f));
        LoginText.setBounds(115, 20, 150, 40);
        LoginText.setBackground(Color.decode("#e8e8e8"));
        LoginText.setForeground(Color.BLACK);
        LoginText.setText("Sign up");
        innerBox.add(LoginText);
        innerBox.setComponentZOrder(LoginText, 0);

        JLabel back = new JLabel("<HTML>← back</HTML>");
        back.setBounds(15, 30, 100, 20);
        back.setFont(bingoWoodFont(12f));
        back.setForeground(new Color(70, 70, 70)); // Softer than pure black
        back.setCursor(new Cursor(Cursor.HAND_CURSOR));

        back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                back.setForeground(new Color(40, 40, 40));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                back.setForeground(new Color(70, 70, 70));
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                mainPanel.removeAll();
                showWelcomeUI();
                mainPanel.revalidate();
                mainPanel.repaint();
            }
        });

        innerBox.add(back);

        // Add these JLabels at the top of innerBox
        JLabel usernameError = new JLabel();
        usernameError.setBounds(80, 120, 220, 20);
        usernameError.setForeground(Color.RED);
        usernameError.setFont(new Font("Arial", Font.PLAIN, 12));
        innerBox.add(usernameError);

        JLabel emailError = new JLabel();
        emailError.setBounds(80, 180, 220, 20);
        emailError.setForeground(Color.RED);
        emailError.setFont(new Font("Arial", Font.PLAIN, 12));
        innerBox.add(emailError);

        JLabel passwordError = new JLabel();
        passwordError.setBounds(80, 240, 220, 20);
        passwordError.setForeground(Color.RED);
        passwordError.setFont(new Font("Arial", Font.PLAIN, 12));
        innerBox.add(passwordError);


        JTextField usernameField = new JTextField("  Username");
        usernameField.setBounds(80, 80, 200, 40);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameField.setForeground(Color.GRAY); // Placeholder color
        usernameField.setBorder(null);
        usernameField.setBackground(Color.decode("#f8f8f8"));

        JTextField emailField = new JTextField("  Email ID");
        emailField.setBounds(80, 140, 200, 40);
        emailField.setFont(new Font("Arial", Font.PLAIN, 14));
        emailField.setForeground(Color.GRAY); // Placeholder color
        emailField.setBorder(null);
        emailField.setBackground(Color.decode("#f8f8f8"));

        try {
            // Load image from resource folder or same package
            BufferedImage emailImg = ImageIO.read(UImanager.class.getResource("username2.png"));
            // Resize to 40x40
            Image scaledEmail = emailImg.getScaledInstance(35, 35, Image.SCALE_SMOOTH);
            JLabel emailIcon = new JLabel(new ImageIcon(scaledEmail));
            emailIcon.setBounds(40, 80, 40, 40); // x, y inside innerBox
            emailIcon.setBackground(Color.decode("#32620e"));
            innerBox.add(emailIcon);
            emailIcon.setOpaque(true);
            innerBox.setComponentZOrder(emailIcon, 0);

        } catch (IOException e) {
            System.err.println("Couldn't load email icon: " + e.getMessage());
        }


        try {
            BufferedImage emailImg = ImageIO.read(UImanager.class.getResource("mail.png"));
            Image scaledEmail = emailImg.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            JLabel emailIcon = new JLabel(new ImageIcon(scaledEmail));
            emailIcon.setBounds(40, 140, 40, 40); // x, y inside innerBox
            emailIcon.setBackground(Color.decode("#32620e"));
            innerBox.add(emailIcon);
            emailIcon.setOpaque(true);
            innerBox.setComponentZOrder(emailIcon, 0);

        } catch (IOException e) {
            System.err.println("Couldn't load email icon: " + e.getMessage());
        }
        try {
            BufferedImage emailImg = ImageIO.read(UImanager.class.getResource("lock.png"));

            Image scaledLock = emailImg.getScaledInstance(25, 25, Image.SCALE_SMOOTH);

            JLabel passwordIcon = new JLabel(new ImageIcon(scaledLock));
            passwordIcon.setBounds(40, 200, 40, 40); // x, y inside innerBox
            passwordIcon.setBackground(Color.decode("#32620e"));
            innerBox.add(passwordIcon);
            passwordIcon.setOpaque(true);
            innerBox.setComponentZOrder(passwordIcon, 0);

        } catch (IOException e) {
            System.err.println("Couldn't load password icon: " + e.getMessage());
        }


// Placeholder logic
        emailField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (emailField.getText().equals("  Email ID")) {
                    emailField.setText("");
                    emailField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (emailField.getText().isEmpty()) {
                    emailField.setText("  Email ID");
                    emailField.setForeground(Color.GRAY);
                }
            }
        });
// Password Input Field
        JPasswordField passwordField = new JPasswordField("  Password");
        passwordField.setBounds(80, 200, 200, 40);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setForeground(Color.GRAY);
        passwordField.setEchoChar((char) 0); // Show plain text
        passwordField.setBorder(null);
        passwordField.setBackground(Color.decode("#f8f8f8"));


// Placeholder logic
        passwordField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (String.valueOf(passwordField.getPassword()).equals("  Password")) {
                    passwordField.setText("");
                    passwordField.setForeground(Color.BLACK);
                    passwordField.setEchoChar('●'); // Typical password mask
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (String.valueOf(passwordField.getPassword()).isEmpty()) {
                    passwordField.setText("  Password");
                    passwordField.setForeground(Color.GRAY);
                    passwordField.setEchoChar((char) 0); // Show plain text again
                }
            }
        });

        JLabel signupinstead = new JLabel("<HTML>Already have an account? login instead</HTML>");
        signupinstead.setBounds(55, 340, 250, 20); // Same y as checkbox, to align on one line
        signupinstead.setFont(bingoWoodFont(12f));
        signupinstead.setForeground(Color.BLACK);
        signupinstead.setCursor(new Cursor(Cursor.HAND_CURSOR));

        signupinstead.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mainPanel.removeAll();
                showLoginUI();
                mainPanel.revalidate();
                mainPanel.repaint();
            }
        });
        innerBox.add(signupinstead);
        RoundedCornerButton enterButton = new RoundedCornerButton("sign up");
        enterButton.setForeground(Color.WHITE);
        enterButton.setFont(bingoWoodFont(20f));
        innerBox.add(emailField);
        innerBox.add(passwordField);
        innerBox.add(usernameField);
        enterButton.setBounds(105, 260, 110, 40);
        enterButton.addActionListener(e -> {
            String email = emailField.getText().trim();
            String username = usernameField.getText().trim();
            String password = String.valueOf(passwordField.getPassword());

            boolean validUsername = userManager.userVerification(username);
            boolean validEmail = userManager.emailVerification(email);
            boolean validPassword = userManager.passVerification(password);

            // Show errors
            usernameError.setText(validUsername ? "" : "Invalid username!");
            emailError.setText(validEmail ? "" : "Invalid email!");
            passwordError.setText(validPassword ? "" : "Password must be 8+ chars!");

            if (userManager.isEmailTaken(email)) {
                emailError.setText("Email already registered!");
                validEmail = false;
            } else {
                emailError.setText("");
            }
            if (userManager.isUsernameTaken(username)) {
                usernameError.setText("Username already taken!");
                validUsername = false;
            } else {
                usernameError.setText(""); // clear error
            }


            if (validUsername && validEmail && validPassword) {
                String otp = OTPstore.generateOTP();
                OTPstore.storeOTP(email, otp);
                new Thread(() -> SendEmail.sendEmail(email, otp)).start();
                mainPanel.removeAll();
                showOtpUI(email, username, password); // pass email to OTP UI for verification
                mainPanel.revalidate();
                mainPanel.repaint();
            }
        });

        innerBox.add(enterButton);
        innerBox.setOpaque(true);

        mainPanel.revalidate();
        mainPanel.repaint();
        frame.setVisible(true);
    }

    public static void showOtpUI(String email, String username, String password) {

        frame.getContentPane().removeAll();
        frame.setLayout(null);

        // background panel
        JPanel bColor = new JPanel();
        bColor.setBounds(0, 0, 1250, 670);
        bColor.setBackground(Color.decode("#f0f0f0"));
        bColor.setLayout(null);
        bColor.setOpaque(true);
        frame.add(bColor);

        // side cover image added to bColor (background)
        try {
            BufferedImage cover = ImageIO.read(UImanager.class.getResource("side.png"));
            Image scaledCover = cover.getScaledInstance(625, 670, Image.SCALE_SMOOTH);
            JLabel coverBackground = new JLabel(new ImageIcon(scaledCover));
            coverBackground.setBounds(0, 0, 625, 670);
            frame.add(coverBackground);
            frame.setComponentZOrder(coverBackground, 0);
        } catch (IOException e) {
            System.err.println("Image could not be loaded: " + e.getMessage());
        }

        // inner rounded box (paintComponent draws rounded rect)
        JPanel innerBox = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                try {
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(Color.decode("#e8e8e8"));
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                } finally {
                    g2.dispose();
                }
            }
        };

        innerBox.setOpaque(false);
        innerBox.setBounds(775, 130, 320, 410);
        innerBox.setLayout(null);
        bColor.add(innerBox);

        JLabel LoginText = new JLabel("OTP Code");
        LoginText.setFont(bingoWoodFont(24f));
        LoginText.setBounds(105, 20, 150, 40);
        innerBox.add(LoginText);

        JLabel back = new JLabel("<HTML>← back</HTML>");
        back.setBounds(15, 30, 100, 20);
        back.setFont(bingoWoodFont(12f));
        back.setForeground(new Color(70, 70, 70)); // Softer than pure black
        back.setCursor(new Cursor(Cursor.HAND_CURSOR));

        back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                back.setForeground(new Color(40, 40, 40));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                back.setForeground(new Color(70, 70, 70));
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                mainPanel.removeAll();
                showSignUpUI();
                mainPanel.revalidate();
                mainPanel.repaint();
            }
        });

        innerBox.add(back);

        // Error label for OTP feedback
        JLabel otpError = new JLabel();
        otpError.setBounds(40, 220, 240, 20);
        otpError.setHorizontalAlignment(JLabel.CENTER);
        otpError.setForeground(Color.RED);
        otpError.setFont(new Font("Arial", Font.PLAIN, 12));
        innerBox.add(otpError);

        JLabel infoText = new JLabel("We have sent the otp to your mail");
        infoText.setFont(bingoWoodFont(12f));
        infoText.setBounds(70, 50, 180, 40);
        innerBox.add(infoText);

        JTextField[] otpFields = new JTextField[6];
        int startX = 35;
        for (int i = 0; i < 6; i++) {
            JTextField tf = new JTextField();
            tf.setFont(new Font("Arial", Font.BOLD, 22));
            tf.setHorizontalAlignment(JTextField.CENTER);
            tf.setBounds(startX + (i * 42), 100, 35, 40);
            tf.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            tf.setBackground(Color.decode("#f8f8f8"));
            tf.addKeyListener(new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {
                    if (!Character.isDigit(e.getKeyChar()) || tf.getText().length() >= 1) {
                        e.consume();
                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {
                    if (tf.getText().length() == 1) {
                        tf.transferFocus();
                    }
                }
            });
            otpFields[i] = tf;
            innerBox.add(tf);
        }

        RoundedCornerButton verifyBtn = new RoundedCornerButton("Verify");
        verifyBtn.setBounds(100, 170, 120, 40);
        verifyBtn.setFont(bingoWoodFont(18f));
        verifyBtn.setForeground(Color.WHITE);
        innerBox.add(verifyBtn);

        verifyBtn.addActionListener(ev -> {
            String enteredOtp = "";
            for (JTextField f : otpFields) enteredOtp += f.getText();
            if (enteredOtp.length() < 6) {
                otpError.setText("Please enter all 6 digits.");
                return;
            }

            if (OTPstore.verifyOTP(email, enteredOtp)) {
                OTPstore.removeOTP(email);
                userManager.signup(username, email, password);
                JOptionPane.showMessageDialog(frame, "Account created successfully!");
                showLoginUI();
            } else {
                otpError.setText("Incorrect OTP. Try again.");
            }
        });

        // Resend link with timer
        JLabel resend = new JLabel();
        resend.setBounds(50, 260, 220, 20);
        resend.setFont(bingoWoodFont(12f));
        resend.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        resend.setHorizontalAlignment(JLabel.CENTER);
        innerBox.add(resend);

        // Timer reference array to make it accessible in the mouse listener
        final Timer[] countdownTimer = {null};

        // Start initial countdown immediately
        resend.setEnabled(false);
        Timer initialCountdown = new Timer(1000, null);
        countdownTimer[0] = initialCountdown;

        initialCountdown.addActionListener(new ActionListener() {
            int secondsLeft = 60;

            @Override
            public void actionPerformed(ActionEvent evt) {
                secondsLeft--;
                if (secondsLeft > 0) {
                    resend.setText("<HTML>Didn't get OTP? <font color='#666666'>Resend in " + secondsLeft + "s</font></HTML>");
                } else {
                    resend.setText("<HTML>Didn't get OTP? <font color='#32620e'><u>Resend</u></font></HTML>");
                    resend.setEnabled(true);
                    resend.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    initialCountdown.stop();
                }
            }
        });
        initialCountdown.start();

        // Set initial text
        resend.setText("<HTML>Didn't get OTP? <font color='gray'>Resend in 60s</font></HTML>");
        resend.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Only allow click if enabled
                if (!resend.isEnabled()) {
                    return;
                }
                // Clear error and fields
                otpError.setText("");
                for (JTextField f : otpFields) f.setText("");

                // Generate and send new OTP
                String newOtp = OTPstore.generateOTP();
                OTPstore.storeOTP(email, newOtp);
                SendEmail.sendEmail(email, newOtp);

                JOptionPane.showMessageDialog(frame, "New OTP has been sent to your email!");
                otpFields[0].requestFocus();

                // Disable resend link and start countdown
                resend.setEnabled(false);
                resend.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                // Stop any existing timer
                if (countdownTimer[0] != null) {
                    countdownTimer[0].stop();
                }
                Timer countdown = new Timer(1000, null);
                countdownTimer[0] = countdown;
                countdown.addActionListener(new ActionListener() {
                    int secondsLeft = 60;
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        secondsLeft--;
                        if (secondsLeft > 0) {
                            resend.setText("<HTML>Didn't get OTP? <font color='gray'>Resend in " + secondsLeft + "s</font></HTML>");
                        } else {
                            resend.setText("<HTML>Didn't get OTP? <font color='#32620e'><u>Resend</u></font></HTML>");
                            resend.setEnabled(true);
                            resend.setCursor(new Cursor(Cursor.HAND_CURSOR));
                            countdown.stop();
                        }
                    }
                });
                countdown.start();
            }
        });

        frame.setComponentZOrder(innerBox, 0);
        frame.setComponentZOrder(bColor, 2);

        frame.revalidate();
        frame.repaint();
    }


    public static void showForgotPasswordUI() {
        mainPanel.removeAll();
        mainPanel.setLayout(null);

        JPanel bColor = new JPanel();
        bColor.setBounds(0, 0, 1250, 670);
        bColor.setBackground(Color.decode("#f0f0f0"));
        bColor.setLayout(null);
        bColor.setOpaque(true);
        mainPanel.add(bColor);

        try {
            BufferedImage cover = ImageIO.read(UImanager.class.getResource("side.png"));
            Image scaledCover = cover.getScaledInstance(625, 670, Image.SCALE_SMOOTH);
            JLabel coverBackground = new JLabel(new ImageIcon(scaledCover));
            coverBackground.setBounds(0, 0, 625, 670);
            mainPanel.add(coverBackground);
            mainPanel.setComponentZOrder(coverBackground, 1);
        } catch (IOException e) {
            System.err.println("Image could not be loaded: " + e.getMessage());
        }

        JPanel innerBox = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                try {
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(Color.decode("#e8e8e8"));
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                } finally {
                    g2.dispose();
                }
            }
        };

        innerBox.setOpaque(false);
        innerBox.setBounds(775, 130, 320, 410);
        innerBox.setLayout(null);
        mainPanel.add(innerBox);

        // Icon
        try {
            BufferedImage icon = ImageIO.read(UImanager.class.getResource("forgotpass.png"));
            Image scaledIcon = icon.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
            JLabel iconLabel = new JLabel(new ImageIcon(scaledIcon));
            iconLabel.setBounds(130, 20, 60, 60);
            innerBox.add(iconLabel);
        } catch (IOException e) {
            System.err.println("Icon not loaded: " + e.getMessage());
        }

        JLabel ForgotText = new JLabel("Forgot Password?");
        ForgotText.setFont(bingoWoodFont(24f));
        ForgotText.setBounds(70, 90, 250, 30);
        ForgotText.setForeground(Color.BLACK);
        innerBox.add(ForgotText);

        JLabel infoText = new JLabel("<html><center>Enter your email to receive<br>verification code</center></html>");
        infoText.setFont(bingoWoodFont(12f));
        infoText.setBounds(70, 125, 180, 40);
        infoText.setHorizontalAlignment(JLabel.CENTER);
        infoText.setForeground(Color.decode("#64748b"));
        innerBox.add(infoText);

        // Email field
        JTextField emailField = new JTextField("  Email ID");
        emailField.setBounds(80, 180, 200, 40);
        emailField.setFont(new Font("Arial", Font.PLAIN, 14));
        emailField.setForeground(Color.GRAY);
        emailField.setBorder(null);
        emailField.setBackground(Color.decode("#f8f8f8"));
        innerBox.add(emailField);

        // Email icon
        try {
            BufferedImage emailImg = ImageIO.read(UImanager.class.getResource("mail.png"));
            Image scaledEmail = emailImg.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            JLabel emailIcon = new JLabel(new ImageIcon(scaledEmail));
            emailIcon.setBounds(40, 180, 40, 40);
            emailIcon.setBackground(Color.decode("#32620e"));
            emailIcon.setOpaque(true);
            innerBox.add(emailIcon);
            innerBox.setComponentZOrder(emailIcon, 0);
        } catch (IOException e) {
            System.err.println("Email icon not loaded: " + e.getMessage());
        }

        emailField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (emailField.getText().equals("  Email ID")) {
                    emailField.setText("");
                    emailField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (emailField.getText().isEmpty()) {
                    emailField.setText("  Email ID");
                    emailField.setForeground(Color.GRAY);
                }
            }
        });

        // Error label
        JLabel errorLabel = new JLabel();
        errorLabel.setBounds(40, 230, 240, 20);
        errorLabel.setFont(bingoWoodFont(12f));
        errorLabel.setForeground(Color.RED);
        innerBox.add(errorLabel);

        // Send button
        RoundedCornerButton sendButton = new RoundedCornerButton("send code");
        sendButton.setForeground(Color.WHITE);
        sendButton.setFont(bingoWoodFont(18f));
        sendButton.setBounds(90, 260, 140, 40);
        innerBox.add(sendButton);

        sendButton.addActionListener(e -> {
            String email = emailField.getText().trim();

            if (email.isEmpty() || email.equals("Email ID")) {
                errorLabel.setText("Please enter your email!");
                return;
            }

            // Check if it's admin
            boolean isAdmin = email.equalsIgnoreCase("admin") ||
                    email.equalsIgnoreCase("ayesha.naveed3101@gmail.com");

            // Check if account exists
            boolean accountExists = false;
            if (isAdmin) {
                accountExists = adminManager.checkAdminEmail(email);
            } else {
                accountExists = userManager.isEmailRegistered(email);
            }

            if (!accountExists) {
                errorLabel.setText("No account found with this email!");
                return;
            }

            // Generate and send OTP
            String otp = OTPstore.generateOTP();
            OTPstore.storeOTP(email, otp);
            new Thread(() -> SendEmail.sendEmail(email, otp)).start();

            errorLabel.setText("");
            mainPanel.removeAll();
            showPasswordResetOtpUI(email, isAdmin);
            mainPanel.revalidate();
            mainPanel.repaint();
        });

        // Back button
        RoundedCornerButton backButton = new RoundedCornerButton("back");
        backButton.setForeground(Color.WHITE);
        backButton.setFont(bingoWoodFont(18f));
        backButton.setBounds(90, 310, 140, 40);
        innerBox.add(backButton);

        backButton.addActionListener(e -> {
            mainPanel.removeAll();
            showLoginUI();
            mainPanel.revalidate();
            mainPanel.repaint();
        });

        mainPanel.setComponentZOrder(innerBox, 0);
        mainPanel.setComponentZOrder(bColor, 2);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    // 3. NEW METHOD: OTP verification for password reset
    public static void showPasswordResetOtpUI(String email, boolean isAdmin) {
        mainPanel.removeAll();
        mainPanel.setLayout(null);

        JPanel bColor = new JPanel();
        bColor.setBounds(0, 0, 1250, 670);
        bColor.setBackground(Color.decode("#f0f0f0"));
        bColor.setLayout(null);
        bColor.setOpaque(true);
        mainPanel.add(bColor);

        try {
            BufferedImage cover = ImageIO.read(UImanager.class.getResource("side.png"));
            Image scaledCover = cover.getScaledInstance(625, 670, Image.SCALE_SMOOTH);
            JLabel coverBackground = new JLabel(new ImageIcon(scaledCover));
            coverBackground.setBounds(0, 0, 625, 670);
            mainPanel.add(coverBackground);
            mainPanel.setComponentZOrder(coverBackground, 1);
        } catch (IOException e) {
            System.err.println("Image could not be loaded: " + e.getMessage());
        }

        JPanel innerBox = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                try {
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(Color.decode("#e8e8e8"));
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                } finally {
                    g2.dispose();
                }
            }
        };

        innerBox.setOpaque(false);
        innerBox.setBounds(775, 130, 320, 410);
        innerBox.setLayout(null);
        mainPanel.add(innerBox);

        JLabel LoginText = new JLabel("Verify OTP");
        LoginText.setFont(bingoWoodFont(24f));
        LoginText.setBounds(100, 20, 150, 40);
        innerBox.add(LoginText);

        JLabel infoText = new JLabel("<html><center>Enter the 6-digit code sent to<br>" + email + "</center></html>");
        infoText.setFont(bingoWoodFont(11f));
        infoText.setBounds(60, 60, 200, 40);
        infoText.setHorizontalAlignment(JLabel.CENTER);
        innerBox.add(infoText);

        // Error label
        JLabel otpError = new JLabel();
        otpError.setBounds(40, 180, 240, 20);
        otpError.setForeground(Color.RED);
        otpError.setFont(new Font("Arial", Font.PLAIN, 12));
        innerBox.add(otpError);

        // OTP fields
        JTextField[] otpFields = new JTextField[6];
        int startX = 35;
        for (int i = 0; i < 6; i++) {
            JTextField tf = new JTextField();
            tf.setFont(new Font("Arial", Font.BOLD, 22));
            tf.setHorizontalAlignment(JTextField.CENTER);
            tf.setBounds(startX + (i * 42), 120, 35, 40);
            tf.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            tf.setBackground(Color.decode("#f8f8f8"));

            final int index = i;
            tf.addKeyListener(new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {
                    if (!Character.isDigit(e.getKeyChar()) || tf.getText().length() >= 1) {
                        e.consume();
                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {
                    if (tf.getText().length() == 1 && index < 5) {
                        otpFields[index + 1].requestFocus();
                    }
                }
            });

            otpFields[i] = tf;
            innerBox.add(tf);
        }

        // Verify button
        RoundedCornerButton verifyBtn = new RoundedCornerButton("Verify");
        verifyBtn.setBounds(100, 210, 120, 40);
        verifyBtn.setFont(bingoWoodFont(18f));
        verifyBtn.setForeground(Color.WHITE);
        innerBox.add(verifyBtn);

        verifyBtn.addActionListener(e -> {
            String enteredOtp = "";
            for (JTextField f : otpFields) enteredOtp += f.getText();

            if (enteredOtp.length() < 6) {
                otpError.setText("Please enter all 6 digits!");
                return;
            }

            if (OTPstore.verifyOTP(email, enteredOtp)) {
                OTPstore.removeOTP(email);
                otpError.setText("");
                mainPanel.removeAll();
                showResetPasswordUI(email, isAdmin);
                mainPanel.revalidate();
                mainPanel.repaint();
            } else {
                otpError.setText("Incorrect OTP! Try again.");
                for (JTextField f : otpFields) f.setText("");
                otpFields[0].requestFocus();
            }
        });

        // Resend OTP
        JLabel resend = new JLabel("<HTML>Didn't get OTP? <font color='#32620e'><u>Resend</u></font></HTML>");
        resend.setBounds(90, 270, 200, 20);
        resend.setFont(bingoWoodFont(12f));
        resend.setCursor(new Cursor(Cursor.HAND_CURSOR));
        innerBox.add(resend);

        resend.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String newOtp = OTPstore.generateOTP();
                OTPstore.storeOTP(email, newOtp);
                new Thread(() -> SendEmail.sendEmail(email, newOtp)).start();
                JOptionPane.showMessageDialog(frame, "New OTP sent to your email!");
                otpError.setText("");
                for (JTextField f : otpFields) f.setText("");
                otpFields[0].requestFocus();
            }
        });

        // Back button
        JLabel back = new JLabel("<HTML><font color='red'><u>← Back</u></font></HTML>");
        back.setBounds(120, 300, 100, 20);
        back.setFont(bingoWoodFont(12f));
        back.setCursor(new Cursor(Cursor.HAND_CURSOR));
        innerBox.add(back);

        back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                OTPstore.removeOTP(email);
                mainPanel.removeAll();
                showForgotPasswordUI();
                mainPanel.revalidate();
                mainPanel.repaint();
            }
        });

        mainPanel.setComponentZOrder(innerBox, 0);
        mainPanel.setComponentZOrder(bColor, 2);
        mainPanel.revalidate();
        mainPanel.repaint();
    }
    public static void showResetPasswordUI(String email, boolean isAdmin) {
        mainPanel.removeAll();
        mainPanel.setLayout(null);

        JPanel bColor = new JPanel();
        bColor.setBounds(0, 0, 1250, 670);
        bColor.setBackground(Color.decode("#f0f0f0"));
        bColor.setLayout(null);
        bColor.setOpaque(true);
        mainPanel.add(bColor);

        try {
            BufferedImage cover = ImageIO.read(UImanager.class.getResource("side.png"));
            Image scaledCover = cover.getScaledInstance(625, 670, Image.SCALE_SMOOTH);
            JLabel coverBackground = new JLabel(new ImageIcon(scaledCover));
            coverBackground.setBounds(0, 0, 625, 670);
            mainPanel.add(coverBackground);
            mainPanel.setComponentZOrder(coverBackground, 1);
        } catch (IOException e) {
            System.err.println("Image could not be loaded: " + e.getMessage());
        }

        JPanel innerBox = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                try {
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(Color.decode("#e8e8e8"));
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                } finally {
                    g2.dispose();
                }
            }
        };

        innerBox.setOpaque(false);
        innerBox.setBounds(775, 130, 320, 410);
        innerBox.setLayout(null);
        mainPanel.add(innerBox);

        JLabel titleText = new JLabel("Reset Password");
        titleText.setFont(bingoWoodFont(24f));
        titleText.setBounds(70, 20, 200, 40);
        titleText.setForeground(Color.BLACK);
        innerBox.add(titleText);

        JLabel infoText = new JLabel("Enter your new password");
        infoText.setFont(bingoWoodFont(12f));
        infoText.setBounds(80, 60, 180, 20);
        infoText.setForeground(Color.decode("#64748b"));
        innerBox.add(infoText);

        // New password field
        JPasswordField newPasswordField = new JPasswordField("  New Password");
        newPasswordField.setBounds(80, 100, 200, 40);
        newPasswordField.setFont(new Font("Arial", Font.PLAIN, 14));
        newPasswordField.setForeground(Color.GRAY);
        newPasswordField.setEchoChar((char) 0);
        newPasswordField.setBorder(null);
        newPasswordField.setBackground(Color.decode("#f8f8f8"));
        innerBox.add(newPasswordField);

        // Lock icon for new password
        try {
            BufferedImage lockImg = ImageIO.read(UImanager.class.getResource("lock.png"));
            Image scaledLock = lockImg.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
            JLabel lockIcon = new JLabel(new ImageIcon(scaledLock));
            lockIcon.setBounds(40, 100, 40, 40);
            lockIcon.setBackground(Color.decode("#32620e"));
            lockIcon.setOpaque(true);
            innerBox.add(lockIcon);
            innerBox.setComponentZOrder(lockIcon, 0);
        } catch (IOException e) {
            System.err.println("Lock icon not loaded: " + e.getMessage());
        }

        // Confirm password field
        JPasswordField confirmPasswordField = new JPasswordField("  Confirm Password");
        confirmPasswordField.setBounds(80, 160, 200, 40);
        confirmPasswordField.setFont(new Font("Arial", Font.PLAIN, 14));
        confirmPasswordField.setForeground(Color.GRAY);
        confirmPasswordField.setEchoChar((char) 0);
        confirmPasswordField.setBorder(null);
        confirmPasswordField.setBackground(Color.decode("#f8f8f8"));
        innerBox.add(confirmPasswordField);

        // Lock icon for confirm password
        try {
            BufferedImage lockImg = ImageIO.read(UImanager.class.getResource("lock.png"));
            Image scaledLock = lockImg.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
            JLabel lockIcon2 = new JLabel(new ImageIcon(scaledLock));
            lockIcon2.setBounds(40, 160, 40, 40);
            lockIcon2.setBackground(Color.decode("#32620e"));
            lockIcon2.setOpaque(true);
            innerBox.add(lockIcon2);
            innerBox.setComponentZOrder(lockIcon2, 0);
        } catch (IOException e) {
            System.err.println("Lock icon not loaded: " + e.getMessage());
        }

        // Focus listeners for new password
        newPasswordField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (String.valueOf(newPasswordField.getPassword()).equals("  New Password")) {
                    newPasswordField.setText("");
                    newPasswordField.setForeground(Color.BLACK);
                    newPasswordField.setEchoChar('●');
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (String.valueOf(newPasswordField.getPassword()).isEmpty()) {
                    newPasswordField.setText("  New Password");
                    newPasswordField.setForeground(Color.GRAY);
                    newPasswordField.setEchoChar((char) 0);
                }
            }
        });

        // Focus listeners for confirm password
        confirmPasswordField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (String.valueOf(confirmPasswordField.getPassword()).equals("  Confirm Password")) {
                    confirmPasswordField.setText("");
                    confirmPasswordField.setForeground(Color.BLACK);
                    confirmPasswordField.setEchoChar('●');
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (String.valueOf(confirmPasswordField.getPassword()).isEmpty()) {
                    confirmPasswordField.setText("  Confirm Password");
                    confirmPasswordField.setForeground(Color.GRAY);
                    confirmPasswordField.setEchoChar((char) 0);
                }
            }
        });

        // Error label
        JLabel errorLabel = new JLabel();
        errorLabel.setBounds(40, 210, 240, 20);
        errorLabel.setFont(bingoWoodFont(12f));
        errorLabel.setForeground(Color.RED);
        innerBox.add(errorLabel);

        // Reset button
        RoundedCornerButton resetButton = new RoundedCornerButton("reset password");
        resetButton.setForeground(Color.WHITE);
        resetButton.setFont(bingoWoodFont(16f));
        resetButton.setBounds(70, 250, 180, 40);
        innerBox.add(resetButton);

        resetButton.addActionListener(e -> {
            String newPassword = String.valueOf(newPasswordField.getPassword()).trim();
            String confirmPassword = String.valueOf(confirmPasswordField.getPassword()).trim();

            // Validate passwords
            if (newPassword.isEmpty() || newPassword.equals("New Password")) {
                errorLabel.setText("Please enter new password!");
                return;
            }

            if (confirmPassword.isEmpty() || confirmPassword.equals("Confirm Password")) {
                errorLabel.setText("Please confirm your password!");
                return;
            }

            if (newPassword.length() != 8) {
                errorLabel.setText("Password must be exactly 8 characters!");
                return;
            }

            if (!newPassword.equals(confirmPassword)) {
                errorLabel.setText("Passwords do not match!");
                return;
            }

            // Reset password
            boolean success = false;
            if (isAdmin) {
                success = adminManager.resetAdminPassword(email, newPassword);
            } else {
                success = userManager.resetPassword(email, newPassword);
            }

            if (success) {
                JOptionPane.showMessageDialog(frame,
                        "Password reset successful!\nPlease login with your new password.",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                mainPanel.removeAll();
                showLoginUI();
                mainPanel.revalidate();
                mainPanel.repaint();
            } else {
                errorLabel.setText("Error resetting password!");
            }
        });

        mainPanel.setComponentZOrder(innerBox, 0);
        mainPanel.setComponentZOrder(bColor, 2);
        mainPanel.revalidate();
        mainPanel.repaint();
    }


    public static void openAdminDashboard() {
        mainPanel.removeAll();
        mainPanel.setLayout(new BorderLayout());

        // ---------------- SIDE PANEL ----------------
        JPanel sidePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(20, 53, 45));
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        sidePanel.setPreferredSize(new Dimension(280, 0));
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));

        // Brand section
        JPanel brandPanel = new JPanel();
        brandPanel.setOpaque(false);
        brandPanel.setLayout(new BoxLayout(brandPanel, BoxLayout.Y_AXIS));
        brandPanel.setMaximumSize(new Dimension(280, 80));

        JLabel brandLabel = new JLabel("Admin Portal");
        brandLabel.setForeground(Color.WHITE);
        brandLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        brandLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel roleLabel = new JLabel("Administrator");
        roleLabel.setForeground(new Color(102, 187, 106));
        roleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        roleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        brandPanel.add(brandLabel);
        brandPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        brandPanel.add(roleLabel);

        sidePanel.add(brandPanel);
        sidePanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Navigation buttons
        String[] btnNames = {"Dashboard", "View All Creators", "Search Creator",
                "Broadcast Message", "View Messages", "Update Password", "Logout"};

        String[] btnIcons = {"🏠", "👥", "🔍", "📢", "📬", "🔐", "🚪"};

        for (int i = 0; i < btnNames.length; i++) {
            final String name = btnNames[i];

            JButton btn = new JButton("  " + btnIcons[i] + "   " + name);
            btn.setFocusPainted(false);
            btn.setForeground(new Color(226, 232, 240));
            btn.setBackground(new Color(20, 53, 45));
            btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            btn.setHorizontalAlignment(SwingConstants.LEFT);
            btn.setBorder(BorderFactory.createEmptyBorder(14, 20, 14, 20));
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btn.setMaximumSize(new Dimension(280, 50));
            btn.setAlignmentX(Component.LEFT_ALIGNMENT);

            btn.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    btn.setBackground(new Color(34, 139, 34));
                    btn.setForeground(Color.WHITE);
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    btn.setBackground(new Color(20, 53, 45));
                    btn.setForeground(new Color(226, 232, 240));
                }
            });

            btn.addActionListener(e -> {
                if (name.equals("Dashboard")) {
                    mainPanel.remove(1);
                    mainPanel.add(createAdminDashboardHome(), BorderLayout.CENTER);
                } else if (name.equals("View All Creators")) {
                    mainPanel.remove(1);
                    mainPanel.add(createViewAllCreatorsPanel(), BorderLayout.CENTER);
                } else if (name.equals("Search Creator")) {
                    mainPanel.remove(1);
                    mainPanel.add(createSearchCreatorPanel(), BorderLayout.CENTER);
                } else if (name.equals("Broadcast Message")) {
                    showBroadcastMessageDialog();
                }
                else if (name.equals("View Messages")) {  // ADD THIS
                    mainPanel.remove(1);
                    mainPanel.add(createAdminMessagesPanel(), BorderLayout.CENTER);}else if (name.equals("Update Password")) {
                    showUpdatePasswordDialog();
                } else if (name.equals("Logout")) {
                    AdminManager.updateAdminStatus("not active");  // Set to not active
                    showWelcomeUI();
                    return;
                }

                mainPanel.revalidate();
                mainPanel.repaint();
            });

            sidePanel.add(btn);
            sidePanel.add(Box.createRigidArea(new Dimension(0, 8)));
        }

        // ---------------- CONTENT PANEL ----------------
        JPanel contentPanel = createAdminDashboardHome();

        mainPanel.add(sidePanel, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        mainPanel.revalidate();
        mainPanel.repaint();
    }
    // ============================================================================
// ADMIN MESSAGES PANEL - View and delete broadcast messages
// ============================================================================

    private static JPanel createAdminMessagesPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(247, 250, 252));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        JLabel title = new JLabel("Broadcast Messages");
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        title.setForeground(new Color(26, 32, 44));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setOpaque(false);

        JButton refreshBtn = new JButton("🔄 Refresh");
        refreshBtn.setFocusPainted(false);
        refreshBtn.setForeground(Color.WHITE);
        refreshBtn.setBackground(new Color(34, 139, 34));
        refreshBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        refreshBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        refreshBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        refreshBtn.addActionListener(e -> {
            mainPanel.remove(1);
            mainPanel.add(createAdminMessagesPanel(), BorderLayout.CENTER);
            mainPanel.revalidate();
            mainPanel.repaint();
        });

        JButton newMsgBtn = new JButton("+ New Message");
        newMsgBtn.setFocusPainted(false);
        newMsgBtn.setForeground(Color.WHITE);
        newMsgBtn.setBackground(new Color(16, 185, 129));
        newMsgBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        newMsgBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        newMsgBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        newMsgBtn.addActionListener(e -> showBroadcastMessageDialog());

        buttonPanel.add(refreshBtn);
        buttonPanel.add(newMsgBtn);

        headerPanel.add(title, BorderLayout.WEST);
        headerPanel.add(buttonPanel, BorderLayout.EAST);
        panel.add(headerPanel, BorderLayout.NORTH);

        // Table Card
        JPanel tableCard = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
            }
        };
        tableCard.setOpaque(false);
        tableCard.setLayout(new BorderLayout());
        tableCard.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel tableTitle = new JLabel("All Messages");
        tableTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        tableTitle.setForeground(new Color(26, 32, 44));
        tableTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        tableCard.add(tableTitle, BorderLayout.NORTH);

        // Table
        String[] columns = {"Timestamp", "Message"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(model);
        styleTable(table);

        // Set column widths
        table.getColumnModel().getColumn(0).setPreferredWidth(150);
        table.getColumnModel().getColumn(1).setPreferredWidth(600);

        // Load messages
        ArrayList<String[]> messages = loadAllBroadcastMessages();
        for (int i = messages.size() - 1; i >= 0; i--) {
            String[] msg = messages.get(i);
            model.addRow(new Object[]{msg[0], msg[1]});
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);

        // Bottom buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        bottomPanel.setOpaque(false);

        JButton deleteBtn = new JButton("🗑️ Delete Selected");
        deleteBtn.setFocusPainted(false);
        deleteBtn.setForeground(Color.WHITE);
        deleteBtn.setBackground(new Color(239, 68, 68));
        deleteBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        deleteBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        deleteBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        deleteBtn.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                String timestamp = (String) table.getValueAt(selectedRow, 0);
                String message = (String) table.getValueAt(selectedRow, 1);

                int confirm = JOptionPane.showConfirmDialog(frame,
                        "Delete message sent at " + timestamp + "?",
                        "Confirm Delete", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    if (deleteBroadcastMessage(timestamp)) {
                        model.removeRow(selectedRow);
                        JOptionPane.showMessageDialog(frame, "Message deleted!");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Error deleting message!");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a message!");
            }
        });

        JButton clearAllBtn = new JButton("🗑️ Clear All Messages");
        clearAllBtn.setFocusPainted(false);
        clearAllBtn.setForeground(Color.WHITE);
        clearAllBtn.setBackground(new Color(220, 38, 38));
        clearAllBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        clearAllBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        clearAllBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        clearAllBtn.addActionListener(e -> {
            if (model.getRowCount() == 0) {
                JOptionPane.showMessageDialog(frame, "No messages to delete!");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(frame,
                    "Delete ALL messages? This cannot be undone!",
                    "Confirm Clear All", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

            if (confirm == JOptionPane.YES_OPTION) {
                if (clearAllBroadcastMessages()) {
                    model.setRowCount(0);
                    JOptionPane.showMessageDialog(frame, "All messages cleared!");
                } else {
                    JOptionPane.showMessageDialog(frame, "Error clearing messages!");
                }
            }
        });

        bottomPanel.add(deleteBtn);
        bottomPanel.add(clearAllBtn);

        JPanel mainArea = new JPanel(new BorderLayout(0, 15));
        mainArea.setOpaque(false);
        mainArea.add(scrollPane, BorderLayout.CENTER);
        mainArea.add(bottomPanel, BorderLayout.SOUTH);

        tableCard.add(mainArea, BorderLayout.CENTER);

        JPanel contentWrapper = new JPanel(new BorderLayout());
        contentWrapper.setOpaque(false);
        contentWrapper.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
        contentWrapper.add(tableCard, BorderLayout.CENTER);

        panel.add(contentWrapper, BorderLayout.CENTER);
        return panel;
    }

    // Delete a specific broadcast message by timestamp
    private static boolean deleteBroadcastMessage(String timestampToDelete) {
        File inputFile = new File("messages.txt");
        File tempFile = new File("temp_messages.txt");

        if (!inputFile.exists()) {
            return false;
        }

        boolean found = false;

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile));
             FileWriter writer = new FileWriter(tempFile)) {

            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    writer.write(line + "\n");
                    continue;
                }

                String[] parts = line.split("\\|", 2);
                if (parts.length >= 1 && parts[0].equals(timestampToDelete)) {
                    found = true;
                    // Skip this line (don't write it)
                } else {
                    writer.write(line + "\n");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        if (found) {
            inputFile.delete();
            tempFile.renameTo(inputFile);
            return true;
        } else {
            tempFile.delete();
            return false;
        }
    }

    private static boolean clearAllBroadcastMessages() {
        File file = new File("messages.txt");
        if (file.exists()) {
            return file.delete();
        }
        return true;
    }
    private static void showCreatorUpdatePasswordDialog(String username) {
        JPanel dialogPanel = new JPanel(new GridLayout(0, 2, 10, 15));
        dialogPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        dialogPanel.add(new JLabel("Current Password:"));
        JPasswordField oldPasswordField = new JPasswordField(20);
        dialogPanel.add(oldPasswordField);

        dialogPanel.add(new JLabel("New Password:"));
        JPasswordField newPasswordField = new JPasswordField(20);
        dialogPanel.add(newPasswordField);

        dialogPanel.add(new JLabel("Confirm New Password:"));
        JPasswordField confirmPasswordField = new JPasswordField(20);
        dialogPanel.add(confirmPasswordField);

        int result = JOptionPane.showConfirmDialog(frame, dialogPanel,
                "Update Password", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String oldPassword = new String(oldPasswordField.getPassword());
            String newPassword = new String(newPasswordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            if (oldPassword.isEmpty() || newPassword.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "All fields are required!");
                return;
            }

            if (!newPassword.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(frame, "New passwords do not match!");
                return;
            }

            if (newPassword.length() != 8) {
                JOptionPane.showMessageDialog(frame, "New password must be exactly 8 characters!");
                return;
            }

            // Get user's email from creator.txt
            String userEmail = getUserEmail(username);
            if (userEmail == null) {
                JOptionPane.showMessageDialog(frame, "Error: User not found!");
                return;
            }

            // Verify old password first
            if (!userManager.login(username, oldPassword)) {
                JOptionPane.showMessageDialog(frame, "Current password is incorrect!");
                return;
            }

            // Update password
            if (userManager.resetPassword(userEmail, newPassword)) {
                JOptionPane.showMessageDialog(frame,
                        "Password updated successfully!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame,
                        "Error updating password!",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Helper method to get user email from username
    private static String getUserEmail(String username) {
        try (BufferedReader br = new BufferedReader(new FileReader("creator.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split("\\s+");
                if (parts.length >= 2 && parts[0].equals(username)) {
                    return parts[1];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

// ============================================================================
// AUTO-LOGIN CHECK - Check if user has "Remember Me" enabled
// ============================================================================

    public static void checkAutoLogin() {
        // Check admin status first
        String adminStatus = getAdminStatus();
        if (adminStatus != null && adminStatus.equals("active")) {
            openAdminDashboard();
            return;
        }

        // Check creator status
        String activeCreator = getActiveCreator();
        if (activeCreator != null) {
            openCreatorDashboard(activeCreator);
            return;
        }

        // No active user found, show welcome page
        showWelcomeUI();
    }

    // Get admin status from admin_data.txt
    private static String getAdminStatus() {
        File file = new File("admin_data.txt");
        if (!file.exists()) {
            return null;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split("\\s+");
                if (parts.length >= 4) {
                    // Format: username email password status
                    String status = parts[3];
                    if (status.equals("active")) {
                        return "active";
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Get active creator username from creator.txt
    private static String getActiveCreator() {
        File file = new File("creator.txt");
        if (!file.exists()) {
            return null;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split("\\s+");
                if (parts.length >= 4) {
                    // Format: username email password status
                    String username = parts[0];
                    String status = parts[3];

                    if (status.equals("active")) {
                        return username;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static JPanel createAdminDashboardHome() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(247, 250, 252));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setOpaque(false);
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));

        JLabel welcomeTitle = new JLabel("Welcome back, Admin");
        welcomeTitle.setForeground(new Color(26, 32, 44));
        welcomeTitle.setFont(new Font("Segoe UI", Font.BOLD, 32));
        welcomeTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitle = new JLabel("Here's what's happening with your platform today");
        subtitle.setForeground(new Color(113, 128, 150));
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        headerPanel.add(welcomeTitle);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        headerPanel.add(subtitle);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        panel.add(headerPanel, BorderLayout.NORTH);

        // Stats Cards
        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        statsPanel.setOpaque(false);
        statsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

        int creatorCount = adminManager.getCreatorCount();

        statsPanel.add(createStatCardSimple("Total Creators", String.valueOf(creatorCount), new Color(34, 139, 34)));
        statsPanel.add(createStatCardSimple("Active Users", "0", new Color(59, 130, 246)));
        statsPanel.add(createStatCardSimple("Messages Sent", "0", new Color(139, 92, 246)));

        JPanel statsContainer = new JPanel();
        statsContainer.setOpaque(false);
        statsContainer.setLayout(new BorderLayout());
        statsContainer.add(statsPanel, BorderLayout.NORTH);
        statsContainer.add(Box.createRigidArea(new Dimension(0, 30)), BorderLayout.CENTER);

        // Quick Actions
        JPanel actionsPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        actionsPanel.setOpaque(false);

        JPanel viewCreatorsCard = createActionCard(
                "Manage Creators",
                "View, search, and manage all creator accounts",
                "View All",
                new Color(34, 139, 34),
                () -> {
                    mainPanel.remove(1);
                    mainPanel.add(createViewAllCreatorsPanel(), BorderLayout.CENTER);
                    mainPanel.revalidate();
                    mainPanel.repaint();
                }
        );

        JPanel broadcastCard = createActionCard(
                "Broadcast Message",
                "Send announcements to all creators on the platform",
                "Send Message",
                new Color(16, 185, 129),
                () -> showBroadcastMessageDialog()
        );

        actionsPanel.add(viewCreatorsCard);
        actionsPanel.add(broadcastCard);

        JPanel mainContent = new JPanel(new BorderLayout());
        mainContent.setOpaque(false);
        mainContent.add(statsContainer, BorderLayout.NORTH);
        mainContent.add(actionsPanel, BorderLayout.CENTER);

        panel.add(mainContent, BorderLayout.CENTER);
        return panel;
    }

// ============ VIEW ALL CREATORS PANEL ============

    private static JPanel createViewAllCreatorsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(247, 250, 252));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        JLabel title = new JLabel("All Creators");
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        title.setForeground(new Color(26, 32, 44));

        JButton refreshBtn = new JButton("🔄 Refresh");
        refreshBtn.setFocusPainted(false);
        refreshBtn.setForeground(Color.WHITE);
        refreshBtn.setBackground(new Color(34, 139, 34));
        refreshBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        refreshBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        refreshBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        headerPanel.add(title, BorderLayout.WEST);
        headerPanel.add(refreshBtn, BorderLayout.EAST);
        panel.add(headerPanel, BorderLayout.NORTH);

        // Table Card
        JPanel tableCard = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
            }
        };
        tableCard.setOpaque(false);
        tableCard.setLayout(new BorderLayout());
        tableCard.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel tableTitle = new JLabel("Creator Accounts");
        tableTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        tableTitle.setForeground(new Color(26, 32, 44));
        tableTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        tableCard.add(tableTitle, BorderLayout.NORTH);

        // Table
        String[] columns = {"Username", "Email"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(model);
        styleTable(table);

        // Load data
        ArrayList<AdminManager.CreatorInfo> creators = adminManager.getAllCreators();
        for (AdminManager.CreatorInfo creator : creators) {
            model.addRow(new Object[]{creator.username, creator.email});
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);

        // Bottom buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        bottomPanel.setOpaque(false);

        JButton deleteBtn = new JButton("🗑️ Delete Selected");
        deleteBtn.setFocusPainted(false);
        deleteBtn.setForeground(Color.WHITE);
        deleteBtn.setBackground(new Color(239, 68, 68));
        deleteBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        deleteBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        deleteBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        deleteBtn.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                String email = (String) table.getValueAt(selectedRow, 1);
                String username = (String) table.getValueAt(selectedRow, 0);

                int confirm = JOptionPane.showConfirmDialog(frame,
                        "Delete creator: " + username + "?",
                        "Confirm Delete", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    if (adminManager.deleteCreatorByEmail(email)) {
                        model.removeRow(selectedRow);
                        JOptionPane.showMessageDialog(frame, "Creator deleted!");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a creator!");
            }
        });

        bottomPanel.add(deleteBtn);

        JPanel mainArea = new JPanel(new BorderLayout(0, 15));
        mainArea.setOpaque(false);
        mainArea.add(scrollPane, BorderLayout.CENTER);
        mainArea.add(bottomPanel, BorderLayout.SOUTH);

        tableCard.add(mainArea, BorderLayout.CENTER);

        JPanel contentWrapper = new JPanel(new BorderLayout());
        contentWrapper.setOpaque(false);
        contentWrapper.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
        contentWrapper.add(tableCard, BorderLayout.CENTER);

        panel.add(contentWrapper, BorderLayout.CENTER);

        // Refresh button action
        refreshBtn.addActionListener(e -> {
            mainPanel.remove(1);
            mainPanel.add(createViewAllCreatorsPanel(), BorderLayout.CENTER);
            mainPanel.revalidate();
            mainPanel.repaint();
        });

        return panel;
    }

// ============ SEARCH CREATOR PANEL ============

    private static JPanel createSearchCreatorPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(247, 250, 252));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // Header
        JLabel title = new JLabel("Search Creator");
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        title.setForeground(new Color(26, 32, 44));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.add(title, BorderLayout.WEST);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        panel.add(headerPanel, BorderLayout.NORTH);

        // Search Card
        JPanel searchCard = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
            }
        };
        searchCard.setOpaque(false);
        searchCard.setLayout(new BorderLayout());
        searchCard.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Search input
        JPanel searchInputPanel = new JPanel(new BorderLayout(15, 0));
        searchInputPanel.setOpaque(false);

        JTextField searchField = new JTextField();
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 2),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        JButton searchBtn = new JButton("🔍 Search");
        searchBtn.setFocusPainted(false);
        searchBtn.setForeground(Color.WHITE);
        searchBtn.setBackground(new Color(34, 139, 34));
        searchBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        searchBtn.setBorder(BorderFactory.createEmptyBorder(12, 30, 12, 30));
        searchBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        searchInputPanel.add(searchField, BorderLayout.CENTER);
        searchInputPanel.add(searchBtn, BorderLayout.EAST);

        // Results table
        String[] columns = {"Username", "Email"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable resultsTable = new JTable(model);
        styleTable(resultsTable);

        JScrollPane scrollPane = new JScrollPane(resultsTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setPreferredSize(new Dimension(0, 300));

        JLabel resultsLabel = new JLabel("Search results will appear here");
        resultsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        resultsLabel.setForeground(new Color(113, 128, 150));
        resultsLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 15, 0));

        JPanel resultsPanel = new JPanel(new BorderLayout());
        resultsPanel.setOpaque(false);
        resultsPanel.add(resultsLabel, BorderLayout.NORTH);
        resultsPanel.add(scrollPane, BorderLayout.CENTER);

        searchCard.add(searchInputPanel, BorderLayout.NORTH);
        searchCard.add(resultsPanel, BorderLayout.CENTER);

        panel.add(searchCard, BorderLayout.CENTER);

        // Search button action
        searchBtn.addActionListener(e -> {
            String query = searchField.getText().trim();
            if (query.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter a search query!");
                return;
            }

            model.setRowCount(0); // Clear previous results
            ArrayList<AdminManager.CreatorInfo> results = adminManager.searchCreators(query);

            if (results.isEmpty()) {
                resultsLabel.setText("No creators found matching: \"" + query + "\"");
            } else {
                resultsLabel.setText("Found " + results.size() + " creator(s) matching: \"" + query + "\"");
                for (AdminManager.CreatorInfo creator : results) {
                    model.addRow(new Object[]{creator.username, creator.email});
                }
            }
        });

        // Search on Enter key
        searchField.addActionListener(e -> searchBtn.doClick());

        return panel;
    }

// ============ BROADCAST MESSAGE DIALOG ============
private static void showBroadcastMessageDialog() {
    JPanel dialogPanel = new JPanel(new BorderLayout(0, 15));
    dialogPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

    JLabel label = new JLabel("Enter message to send to all creators:");
    label.setFont(new Font("Segoe UI", Font.BOLD, 14));

    JTextArea messageArea = new JTextArea(6, 30);
    messageArea.setFont(new Font("Segoe UI", Font.PLAIN, 13));
    messageArea.setLineWrap(true);
    messageArea.setWrapStyleWord(true);
    messageArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)
    ));

    JScrollPane scrollPane = new JScrollPane(messageArea);
    scrollPane.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240), 1));

    dialogPanel.add(label, BorderLayout.NORTH);
    dialogPanel.add(scrollPane, BorderLayout.CENTER);

    int result = JOptionPane.showConfirmDialog(frame, dialogPanel,
            "Broadcast Message", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

    if (result == JOptionPane.OK_OPTION) {
        String message = messageArea.getText().trim();
        if (message.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Message cannot be empty!");
            return;
        }

        // Check if there are creators
        ArrayList<AdminManager.CreatorInfo> creators = adminManager.getAllCreators();
        if (creators.isEmpty()) {
            JOptionPane.showMessageDialog(frame,
                    "No creators found to send message to!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Get current timestamp
        String timestamp = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(new java.util.Date());

        // Save message to messages.txt
        if (saveBroadcastMessageToFile(timestamp, message)) {
            JOptionPane.showMessageDialog(frame,
                    "Message sent to all creators successfully!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(frame,
                    "Failed to send message!",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
// ============================================================================
// CREATOR MESSAGES PANEL - View broadcast messages from admin
// ============================================================================

    private static JPanel createCreatorMessagesPanel(String username) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(247, 250, 252));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        JLabel title = new JLabel("Admin Messages");
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        title.setForeground(new Color(26, 32, 44));

        JButton refreshBtn = new JButton("🔄 Refresh");
        refreshBtn.setFocusPainted(false);
        refreshBtn.setForeground(Color.WHITE);
        refreshBtn.setBackground(new Color(34, 139, 34));
        refreshBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        refreshBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        refreshBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        refreshBtn.addActionListener(e -> {
            mainPanel.remove(1);
            mainPanel.add(createCreatorMessagesPanel(username), BorderLayout.CENTER);
            mainPanel.revalidate();
            mainPanel.repaint();
        });

        headerPanel.add(title, BorderLayout.WEST);
        headerPanel.add(refreshBtn, BorderLayout.EAST);
        panel.add(headerPanel, BorderLayout.NORTH);

        // Main content area
        JPanel contentArea = new JPanel();
        contentArea.setOpaque(false);
        contentArea.setLayout(new BoxLayout(contentArea, BoxLayout.Y_AXIS));

        // Load messages from messages.txt
        ArrayList<String[]> messages = loadAllBroadcastMessages();

        if (messages.isEmpty()) {
            // No messages card
            JPanel noMessagesCard = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(Color.WHITE);
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                }
            };
            noMessagesCard.setOpaque(false);
            noMessagesCard.setLayout(new BorderLayout());
            noMessagesCard.setBorder(BorderFactory.createEmptyBorder(60, 40, 60, 40));
            noMessagesCard.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));

            JLabel noMsgLabel = new JLabel("📭 No messages yet");
            noMsgLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
            noMsgLabel.setForeground(new Color(148, 163, 184));
            noMsgLabel.setHorizontalAlignment(JLabel.CENTER);

            JLabel infoLabel = new JLabel("You'll see broadcast messages from admin here");
            infoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            infoLabel.setForeground(new Color(148, 163, 184));
            infoLabel.setHorizontalAlignment(JLabel.CENTER);

            JPanel textPanel = new JPanel();
            textPanel.setOpaque(false);
            textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
            noMsgLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            textPanel.add(noMsgLabel);
            textPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            textPanel.add(infoLabel);

            noMessagesCard.add(textPanel, BorderLayout.CENTER);
            contentArea.add(noMessagesCard);

        } else {
            // Display messages (newest first)
            for (int i = messages.size() - 1; i >= 0; i--) {
                String[] msgData = messages.get(i);
                String timestamp = msgData[0];
                String message = msgData[1];

                JPanel messageCard = createSimpleMessageCard(timestamp, message);
                contentArea.add(messageCard);
                contentArea.add(Box.createRigidArea(new Dimension(0, 15)));
            }
        }

        JScrollPane scrollPane = new JScrollPane(contentArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
        scrollPane.getViewport().setBackground(new Color(247, 250, 252));
        scrollPane.setOpaque(false);

        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    // Create individual message card (simplified - no read/unread tracking)
    private static JPanel createSimpleMessageCard(String timestamp, String message) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);

                // Green accent bar on left
                g2.setColor(new Color(34, 139, 34));
                g2.fillRoundRect(0, 0, 4, getHeight(), 4, 4);
            }
        };
        card.setOpaque(false);
        card.setLayout(new BorderLayout());
        card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));

        // Header with timestamp
        JLabel timestampLabel = new JLabel("📢 Admin Broadcast • " + timestamp);
        timestampLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        timestampLabel.setForeground(new Color(71, 85, 105));
        timestampLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        // Message content
        JTextArea messageArea = new JTextArea(message);
        messageArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        messageArea.setForeground(new Color(30, 41, 59));
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        messageArea.setEditable(false);
        messageArea.setOpaque(false);

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setOpaque(false);
        contentPanel.add(timestampLabel, BorderLayout.NORTH);
        contentPanel.add(messageArea, BorderLayout.CENTER);

        card.add(contentPanel, BorderLayout.CENTER);
        return card;
    }

    // Load all broadcast messages from messages.txt
    private static ArrayList<String[]> loadAllBroadcastMessages() {
        ArrayList<String[]> messages = new ArrayList<>();
        File file = new File("messages.txt");

        if (!file.exists()) {
            return messages;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                // Format: timestamp|message
                String[] parts = line.split("\\|", 2);
                if (parts.length >= 2) {
                    messages.add(new String[]{parts[0], parts[1]});
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return messages;
    }
    // Helper method to save broadcast message to messages.txt
    private static boolean saveBroadcastMessageToFile(String timestamp, String message) {
        try (FileWriter writer = new FileWriter("messages.txt", true)) {
            // Format: timestamp|message
            writer.write(timestamp + "|" + message + "\n");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
// ============ UPDATE PASSWORD DIALOG ============

    private static void showUpdatePasswordDialog() {
        JPanel dialogPanel = new JPanel(new GridLayout(0, 2, 10, 15));
        dialogPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        dialogPanel.add(new JLabel("Current Password:"));
        JPasswordField oldPasswordField = new JPasswordField(20);
        dialogPanel.add(oldPasswordField);

        dialogPanel.add(new JLabel("New Password:"));
        JPasswordField newPasswordField = new JPasswordField(20);
        dialogPanel.add(newPasswordField);

        dialogPanel.add(new JLabel("Confirm New Password:"));
        JPasswordField confirmPasswordField = new JPasswordField(20);
        dialogPanel.add(confirmPasswordField);

        int result = JOptionPane.showConfirmDialog(frame, dialogPanel,
                "Update Admin Password", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String oldPassword = new String(oldPasswordField.getPassword());
            String newPassword = new String(newPasswordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            if (oldPassword.isEmpty() || newPassword.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "All fields are required!");
                return;
            }

            if (!newPassword.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(frame, "New passwords do not match!");
                return;
            }

            if (newPassword.length() < 8) {
                JOptionPane.showMessageDialog(frame, "New password must be at least 8 characters!");
                return;
            }

            if (adminManager.updateAdminPassword(oldPassword, newPassword)) {
                JOptionPane.showMessageDialog(frame,
                        "Password updated successfully!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame,
                        "Current password is incorrect!",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

// ============ HELPER: ACTION CARD WITH CALLBACK ============

    private static JPanel createActionCard(String title, String description, String buttonText,
                                           Color accentColor, Runnable action) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
            }
        };
        card.setOpaque(false);
        card.setLayout(new BorderLayout());
        card.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        JPanel contentPanel = new JPanel();
        contentPanel.setOpaque(false);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(new Color(26, 32, 44));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel descLabel = new JLabel("<html>" + description + "</html>");
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        descLabel.setForeground(new Color(100, 116, 139));
        descLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        contentPanel.add(titleLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        contentPanel.add(descLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JButton actionBtn = new JButton(buttonText);
        actionBtn.setFocusPainted(false);
        actionBtn.setForeground(Color.WHITE);
        actionBtn.setBackground(accentColor);
        actionBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        actionBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        actionBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        actionBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        actionBtn.addActionListener(e -> action.run());

        actionBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            Color original = accentColor;
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                actionBtn.setBackground(new Color(
                        Math.min(255, original.getRed() + 20),
                        Math.min(255, original.getGreen() + 20),
                        Math.min(255, original.getBlue() + 20)
                ));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                actionBtn.setBackground(original);
            }
        });

        contentPanel.add(actionBtn);
        card.add(contentPanel, BorderLayout.CENTER);

        return card;
    }


    public static void openCreatorDashboard(String username) {
        // ---------------- MAIN DASHBOARD ----------------
        mainPanel.removeAll();
        mainPanel.setLayout(new BorderLayout());

        // ---------------- SIDE PANEL ----------------
        JPanel sidePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // Dark green sidebar
                g2.setColor(new Color(20, 53, 45));
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        sidePanel.setPreferredSize(new Dimension(280, 0));
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));

        // Logo/Brand section with username
        JPanel brandPanel = new JPanel();
        brandPanel.setOpaque(false);
        brandPanel.setLayout(new BoxLayout(brandPanel, BoxLayout.Y_AXIS));
        brandPanel.setMaximumSize(new Dimension(280, 80));
        brandPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel brandLabel = new JLabel("Creator Studio");
        brandLabel.setForeground(Color.WHITE);
        brandLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        brandLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel userLabel = new JLabel("@" + username);
        userLabel.setForeground(new Color(102, 187, 106));
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        userLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        brandPanel.add(brandLabel);
        brandPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        brandPanel.add(userLabel);

        sidePanel.add(brandPanel);
        sidePanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Navigation buttons
        String[] btnNames = {"Manage Content", "Time Management", "Revenue Tracking",
                "Audience Demographics", "Channel Growth", "Messages", "Update Password", "Logout"};

        String[] btnIcons = {"📁", "⏰", "💰", "👥", "📈", "📬", "🔐", "🚪"};

        for (int i = 0; i < btnNames.length; i++) {
            final String name = btnNames[i];
            final int index = i + 1; // For menu choice (1-6)

            JButton btn = new JButton("  " + btnIcons[i] + "   " + name);
            btn.setFocusPainted(false);
            btn.setForeground(new Color(226, 232, 240));
            btn.setBackground(new Color(20, 53, 45));
            btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            btn.setHorizontalAlignment(SwingConstants.LEFT);
            btn.setBorder(BorderFactory.createEmptyBorder(14, 20, 14, 20));
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btn.setMaximumSize(new Dimension(280, 50));
            btn.setAlignmentX(Component.LEFT_ALIGNMENT);

            // Modern hover effect
            btn.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    btn.setBackground(new Color(34, 139, 34));
                    btn.setForeground(Color.WHITE);
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    btn.setBackground(new Color(20, 53, 45));
                    btn.setForeground(new Color(226, 232, 240));
                }
            });

            // Action listener for button clicks
            btn.addActionListener(e -> {
                if (name.equals("Manage Content")) {
                    mainPanel.remove(1);
                    mainPanel.add(createContentManagementPanel(username), BorderLayout.CENTER);
                    mainPanel.revalidate();
                    mainPanel.repaint();
                }
                else if (name.equals("Audience Demographics")) {  // ADD THIS
                    mainPanel.remove(1);
                    mainPanel.add(createAudienceAnalyticsPanel(username), BorderLayout.CENTER);
                    mainPanel.revalidate();
                    mainPanel.repaint();
                }
                else if (name.equals("Revenue Tracking")) {
                    mainPanel.remove(1);
                    mainPanel.add(createRevenueTrackingPanel(username), BorderLayout.CENTER);
                    mainPanel.revalidate();
                    mainPanel.repaint();
                }
                else if (name.equals("Time Management")) {
                    mainPanel.remove(1);
                    mainPanel.add(createTimeManagementPanel(username), BorderLayout.CENTER);
                    mainPanel.revalidate();
                    mainPanel.repaint();
                }
                else if (name.equals("Channel Growth")) {
                    mainPanel.remove(1);
                    mainPanel.add(createChannelGrowthPanel(username), BorderLayout.CENTER);
                    mainPanel.revalidate();
                    mainPanel.repaint();
                }
                else if (name.equals("Messages")) {
                    mainPanel.remove(1);
                    mainPanel.add(createCreatorMessagesPanel(username), BorderLayout.CENTER);
                    mainPanel.revalidate();
                    mainPanel.repaint();
                }
                else if (name.equals("Update Password")) {
                    showCreatorUpdatePasswordDialog(username);
                }
                else if (name.equals("Logout")) {
                    UserManager.updateUserStatus(username, "not active");  // ADD THIS LINE
                    showWelcomeUI();
                    return;
                }
            });

            sidePanel.add(btn);
            sidePanel.add(Box.createRigidArea(new Dimension(0, 8)));
        }

        // ---------------- CONTENT PANEL ----------------
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(new Color(247, 250, 252));
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // ---------- HEADER SECTION ----------
        JPanel headerPanel = new JPanel();
        headerPanel.setOpaque(false);
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));

        JLabel welcomeTitle = new JLabel("Welcome back, " + username);
        welcomeTitle.setForeground(new Color(26, 32, 44));
        welcomeTitle.setFont(new Font("Segoe UI", Font.BOLD, 32));
        welcomeTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitle = new JLabel("Let's create something amazing today");
        subtitle.setForeground(new Color(113, 128, 150));
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        headerPanel.add(welcomeTitle);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        headerPanel.add(subtitle);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        contentPanel.add(headerPanel, BorderLayout.NORTH);

        // ---------- STATS CARDS ----------
        JPanel statsPanel = new JPanel();
        statsPanel.setOpaque(false);
        statsPanel.setLayout(new GridLayout(1, 4, 20, 0));
        statsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

        // Load actual data from files
        GrowthManager gm = new GrowthManager();
        gm.loadGrowthData(username);

        ContentManager cm = new ContentManager();
        int totalVideos = cm.getAllContent(username).size();

        RevenueManager rm = new RevenueManager();
        rm.loadRevenueData(username);

        String[] statTitles = {"Total Content", "Total Views", "Total Revenue", "Subscribers"};
        String[] statValues = {
                String.valueOf(totalVideos),
                String.format("%,d", gm.totalViews),
                "$" + String.format("%.2f", rm.totalRevenue),
                String.format("%,d", gm.totalSubscribers)
        };
        Color[] statColors = {
                new Color(34, 139, 34),
                new Color(16, 185, 129),
                new Color(102, 187, 106),
                new Color(67, 160, 71)
        };

        for (int i = 0; i < statTitles.length; i++) {
            JPanel statCard = createStatCard1(statTitles[i], statValues[i], statColors[i]);
            statsPanel.add(statCard);
        }

        JPanel statsContainer = new JPanel();
        statsContainer.setOpaque(false);
        statsContainer.setLayout(new BorderLayout());
        statsContainer.add(statsPanel, BorderLayout.NORTH);
        statsContainer.add(Box.createRigidArea(new Dimension(0, 30)), BorderLayout.CENTER);

        // ---------- QUICK ACTIONS CARDS ----------
        JPanel actionsPanel = new JPanel();
        actionsPanel.setOpaque(false);
        actionsPanel.setLayout(new GridLayout(1, 2, 20, 0));

        // Recent Content Card
        // Recent Content Card
        JPanel contentCard = createActionCard(
                "Recent Content",
                "Manage your uploaded videos and track performance",
                "View All",
                new Color(34, 139, 34),
                () -> {
                    mainPanel.remove(1);
                    mainPanel.add(createContentManagementPanel(username), BorderLayout.CENTER);
                    mainPanel.revalidate();
                    mainPanel.repaint();
                }
        );

// Analytics Card
        JPanel analyticsCard = createActionCard(
                "Quick Analytics",
                "Monitor your channel growth and audience engagement",
                "View Details",
                new Color(16, 185, 129),
                () -> {
                    mainPanel.remove(1);
                    mainPanel.add(createChannelGrowthPanel(username), BorderLayout.CENTER);
                    mainPanel.revalidate();
                    mainPanel.repaint();
                }
        );
        actionsPanel.add(contentCard);
        actionsPanel.add(analyticsCard);

        JPanel mainContent = new JPanel();
        mainContent.setOpaque(false);
        mainContent.setLayout(new BorderLayout());
        mainContent.add(statsContainer, BorderLayout.NORTH);
        mainContent.add(actionsPanel, BorderLayout.CENTER);

        contentPanel.add(mainContent, BorderLayout.CENTER);

        // ---------------- ADD PANELS TO MAIN ----------------
        mainPanel.add(sidePanel, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private static JPanel createStatCard1(String title, String value, Color accentColor) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);

                // Accent bar on left
                g2.setColor(accentColor);
                g2.fillRoundRect(0, 0, 4, getHeight(), 4, 4);
            }
        };
        card.setOpaque(false);
        card.setLayout(new BorderLayout());
        card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        titleLabel.setForeground(new Color(100, 116, 139));

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        valueLabel.setForeground(new Color(26, 32, 44));

        JPanel textPanel = new JPanel();
        textPanel.setOpaque(false);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.add(titleLabel);
        textPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        textPanel.add(valueLabel);

        card.add(textPanel, BorderLayout.CENTER);

        return card;
    }

    private static JPanel createActionCard(String title, String description, String buttonText, Color accentColor) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
            }
        };
        card.setOpaque(false);
        card.setLayout(new BorderLayout());
        card.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        JPanel contentPanel = new JPanel();
        contentPanel.setOpaque(false);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(new Color(26, 32, 44));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel descLabel = new JLabel("<html>" + description + "</html>");
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        descLabel.setForeground(new Color(100, 116, 139));
        descLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        contentPanel.add(titleLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        contentPanel.add(descLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JButton actionBtn = new JButton(buttonText);
        actionBtn.setFocusPainted(false);
        actionBtn.setForeground(Color.WHITE);
        actionBtn.setBackground(accentColor);
        actionBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        actionBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        actionBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        actionBtn.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Hover effect
        actionBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            Color original = accentColor;

            public void mouseEntered(java.awt.event.MouseEvent evt) {
                actionBtn.setBackground(new Color(
                        Math.min(255, original.getRed() + 20),
                        Math.min(255, original.getGreen() + 20),
                        Math.min(255, original.getBlue() + 20)
                ));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                actionBtn.setBackground(original);
            }
        });

        contentPanel.add(actionBtn);
        card.add(contentPanel, BorderLayout.CENTER);

        return card;
    }




    private static JPanel createContentManagementPanel(String username) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(247, 250, 252));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        JLabel title = new JLabel("Content Management");
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        title.setForeground(new Color(26, 32, 44));

        JButton addBtn = new JButton("+ Add Content");
        addBtn.setFocusPainted(false);
        addBtn.setForeground(Color.WHITE);
        addBtn.setBackground(new Color(34, 139, 34));
        addBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        addBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        addBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addBtn.addActionListener(e -> showAddContentDialog(username));

        headerPanel.add(title, BorderLayout.WEST);
        headerPanel.add(addBtn, BorderLayout.EAST);
        panel.add(headerPanel, BorderLayout.NORTH);

        // Table
        JPanel tableCard = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
            }
        };
        tableCard.setOpaque(false);
        tableCard.setLayout(new BorderLayout());
        tableCard.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        String[] columns = {"Title", "Status", "Upload Date", "Type"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);

        // Style table
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(40);
        table.setShowGrid(false);
        table.setBackground(Color.WHITE);
        table.setForeground(new Color(26, 32, 44));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(248, 250, 252));
        table.getTableHeader().setForeground(new Color(100, 116, 139));

        // Load data
        ContentManager cm = new ContentManager();
        ArrayList<String[]> contentList = cm.getAllContent(username);
        for (String[] content : contentList) {
            model.addRow(content);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);
        tableCard.add(scrollPane, BorderLayout.CENTER);

        // Bottom buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        bottomPanel.setOpaque(false);

        JButton refreshBtn = new JButton("🔄 Refresh");
        refreshBtn.setFocusPainted(false);
        refreshBtn.setForeground(Color.WHITE);
        refreshBtn.setBackground(new Color(16, 185, 129));
        refreshBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        refreshBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        refreshBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        refreshBtn.addActionListener(e -> {
            model.setRowCount(0);
            ArrayList<String[]> newList = cm.getAllContent(username);
            for (String[] content : newList) {
                model.addRow(content);
            }
        });

        JButton deleteBtn = new JButton("🗑️ Delete Selected");
        deleteBtn.setFocusPainted(false);
        deleteBtn.setForeground(Color.WHITE);
        deleteBtn.setBackground(new Color(239, 68, 68));
        deleteBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        deleteBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        deleteBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        deleteBtn.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                String titleToDelete = (String) table.getValueAt(selectedRow, 0);
                int confirm = JOptionPane.showConfirmDialog(frame,
                        "Delete content: " + titleToDelete + "?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    if (cm.deleteContent(username, titleToDelete)) {
                        model.removeRow(selectedRow);
                        JOptionPane.showMessageDialog(frame, "Content deleted!");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Error deleting content!");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a row!");
            }
        });

        bottomPanel.add(refreshBtn);
        bottomPanel.add(deleteBtn);

        JPanel mainContent = new JPanel(new BorderLayout(0, 20));
        mainContent.setOpaque(false);
        mainContent.add(tableCard, BorderLayout.CENTER);
        mainContent.add(bottomPanel, BorderLayout.SOUTH);

        panel.add(mainContent, BorderLayout.CENTER);
        return panel;
    }

    private static void showAddContentDialog(String username) {
        JPanel dialogPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        dialogPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField titleField = new JTextField(20);
        JComboBox<String> statusBox = new JComboBox<>(new String[]{
                "Draft", "Editing", "Ready", "Published", "Scheduled"
        });
        JTextField dateField = new JTextField(20);
        JComboBox<String> typeBox = new JComboBox<>(new String[]{
                "Video", "Short", "Stream", "Post", "Story"
        });

        dialogPanel.add(new JLabel("Title:"));
        dialogPanel.add(titleField);
        dialogPanel.add(new JLabel("Status:"));
        dialogPanel.add(statusBox);
        dialogPanel.add(new JLabel("Upload Date (DD/MM/YYYY):"));
        dialogPanel.add(dateField);
        dialogPanel.add(new JLabel("Type:"));
        dialogPanel.add(typeBox);

        int result = JOptionPane.showConfirmDialog(frame, dialogPanel,
                "Add New Content", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String title = titleField.getText().trim();
            String status = (String) statusBox.getSelectedItem();
            String date = dateField.getText().trim();
            String type = (String) typeBox.getSelectedItem();

            if (title.isEmpty() || date.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Title and Date cannot be empty!");
                return;
            }

            if (cm.addContent(username, title, status, date, type)) {
                JOptionPane.showMessageDialog(frame, "Content added successfully!");
                // Refresh the content management panel instead of going to dashboard
                mainPanel.remove(1);
                mainPanel.add(createContentManagementPanel(username), BorderLayout.CENTER);
                mainPanel.revalidate();
                mainPanel.repaint();
            } else {
                JOptionPane.showMessageDialog(frame, "Error adding content!");
            }
        }
    }

    private static JPanel createAudienceAnalyticsPanel(String username) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(247, 250, 252));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        JLabel title = new JLabel("Audience Analytics");
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        title.setForeground(new Color(26, 32, 44));

        JButton addBtn = new JButton("+ Add Audience Data");
        addBtn.setFocusPainted(false);
        addBtn.setForeground(Color.WHITE);
        addBtn.setBackground(new Color(34, 139, 34));
        addBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        addBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        addBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addBtn.addActionListener(e -> showAddAudienceDialog(username));

        headerPanel.add(title, BorderLayout.WEST);
        headerPanel.add(addBtn, BorderLayout.EAST);
        panel.add(headerPanel, BorderLayout.NORTH);

        // Get audience stats
        AudienceManager am = new AudienceManager();
        am.loadAudienceStats(username);  // Load the stats

        // Main content - 2 rows
        JPanel mainContent = new JPanel(new GridLayout(2, 1, 0, 20));
        mainContent.setOpaque(false);

        // Top row - Stats cards
        JPanel statsRow = new JPanel(new GridLayout(1, 3, 20, 0));
        statsRow.setOpaque(false);

        statsRow.add(createStatCardSimple("Total Audience", String.valueOf(am.totalAudience), new Color(34, 139, 34)));
        statsRow.add(createStatCardSimple("Male", String.valueOf(am.maleCount), new Color(59, 130, 246)));
        statsRow.add(createStatCardSimple("Female", String.valueOf(am.femaleCount), new Color(236, 72, 153)));

        // Bottom row - Charts
        JPanel chartsRow = new JPanel(new GridLayout(1, 2, 20, 0));
        chartsRow.setOpaque(false);

        // Age Distribution Pie Chart
        JPanel ageChartCard = createChartCard("Age Distribution",
                createAgePieChart(am));

        // Gender Bar Chart
        JPanel genderChartCard = createChartCard("Gender Distribution",
                createGenderBarChart(am));

        chartsRow.add(ageChartCard);
        chartsRow.add(genderChartCard);

        mainContent.add(statsRow);
        mainContent.add(chartsRow);

        panel.add(mainContent, BorderLayout.CENTER);
        return panel;
    }

    // Create Age Distribution Pie Chart
    private static ChartPanel createAgePieChart(AudienceManager am) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("18-24", am.age18_24);
        dataset.setValue("25-34", am.age25_34);
        dataset.setValue("35-44", am.age35_44);
        dataset.setValue("45+", am.age45plus);

        JFreeChart chart = ChartFactory.createPieChart(
                null,  // No title (we have card title)
                dataset,
                true,  // legend
                true,  // tooltips
                false  // urls
        );

        // Customize chart
        chart.setBackgroundPaint(Color.WHITE);
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setOutlineVisible(false);
        plot.setShadowPaint(null);

        // Set colors for age groups
        plot.setSectionPaint("18-24", new Color(34, 197, 94));
        plot.setSectionPaint("25-34", new Color(59, 130, 246));
        plot.setSectionPaint("35-44", new Color(249, 115, 22));
        plot.setSectionPaint("45+", new Color(236, 72, 153));

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(400, 300));
        chartPanel.setBackground(Color.WHITE);
        return chartPanel;
    }

    // Create Gender Bar Chart
    private static ChartPanel createGenderBarChart(AudienceManager am) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(am.maleCount, "Count", "Male");
        dataset.addValue(am.femaleCount, "Count", "Female");

        JFreeChart chart = ChartFactory.createBarChart(
                null,  // No title
                null,  // X-axis label
                "Count",  // Y-axis label
                dataset,
                PlotOrientation.VERTICAL,
                false,  // legend
                true,   // tooltips
                false   // urls
        );

        // Customize chart
        chart.setBackgroundPaint(Color.WHITE);
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setOutlineVisible(false);
        plot.setRangeGridlinePaint(new Color(226, 232, 240));

        // Set bar colors
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(34, 139, 34));
        renderer.setBarPainter(new StandardBarPainter()); // Remove gradient

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(400, 300));
        chartPanel.setBackground(Color.WHITE);
        return chartPanel;
    }

    // Create chart card wrapper
    private static JPanel createChartCard(String title, ChartPanel chart) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
            }
        };
        card.setOpaque(false);
        card.setLayout(new BorderLayout());
        card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(new Color(26, 32, 44));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(chart, BorderLayout.CENTER);

        return card;
    }

    // Simple stat card (without accent bar)
    private static JPanel createStatCardSimple(String title, String value, Color color) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
            }
        };
        card.setOpaque(false);
        card.setLayout(new BorderLayout());
        card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel textPanel = new JPanel();
        textPanel.setOpaque(false);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        titleLabel.setForeground(new Color(100, 116, 139));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        valueLabel.setForeground(color);
        valueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        textPanel.add(titleLabel);
        textPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        textPanel.add(valueLabel);

        card.add(textPanel, BorderLayout.CENTER);
        return card;
    }

    // Add audience dialog
    private static void showAddAudienceDialog(String username) {
        JPanel dialogPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        dialogPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel infoLabel = new JLabel("<html><b>Add Audience Members</b><br/>Enter details for each member:</html>");
        infoLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        JSpinner countSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));

        dialogPanel.add(infoLabel);
        dialogPanel.add(new JLabel(""));
        dialogPanel.add(new JLabel("Number of members:"));
        dialogPanel.add(countSpinner);

        int result = JOptionPane.showConfirmDialog(frame, dialogPanel,
                "Add Audience Data", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            int count = (Integer) countSpinner.getValue();
            AudienceManager am = new AudienceManager();
            int added = 0;

            for (int i = 0; i < count; i++) {
                JPanel memberPanel = new JPanel(new GridLayout(0, 2, 10, 10));
                memberPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                JLabel memberLabel = new JLabel("<html><b>Member " + (i + 1) + " of " + count + "</b></html>");
                JSpinner ageSpinner = new JSpinner(new SpinnerNumberModel(25, 18, 100, 1));
                JComboBox<String> genderBox = new JComboBox<>(new String[]{"Male", "Female"});

                memberPanel.add(memberLabel);
                memberPanel.add(new JLabel(""));
                memberPanel.add(new JLabel("Age:"));
                memberPanel.add(ageSpinner);
                memberPanel.add(new JLabel("Gender:"));
                memberPanel.add(genderBox);

                int memberResult = JOptionPane.showConfirmDialog(frame, memberPanel,
                        "Audience Member " + (i + 1), JOptionPane.OK_CANCEL_OPTION);

                if (memberResult == JOptionPane.OK_OPTION) {
                    int age = (Integer) ageSpinner.getValue();
                    char gender = genderBox.getSelectedIndex() == 0 ? 'M' : 'F';

                    if (am.addAudienceMember(username, age, gender)) {
                        added++;
                    }
                } else {
                    break; // User cancelled
                }
            }

            if (added > 0) {
                JOptionPane.showMessageDialog(frame, added + " audience member(s) added!");
                // Refresh the panel
                mainPanel.remove(1);
                mainPanel.add(createAudienceAnalyticsPanel(username), BorderLayout.CENTER);
                mainPanel.revalidate();
                mainPanel.repaint();
            }
        }
    }
    // ========== PASTE THIS AT THE BOTTOM OF YOUR UImanager CLASS ==========

    private static JPanel createRevenueTrackingPanel(String username) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(247, 250, 252));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        JLabel title = new JLabel("Revenue Tracking");
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        title.setForeground(new Color(26, 32, 44));

        JButton addBtn = new JButton("+ Add Revenue");
        addBtn.setFocusPainted(false);
        addBtn.setForeground(Color.WHITE);
        addBtn.setBackground(new Color(34, 139, 34));
        addBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        addBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        addBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addBtn.addActionListener(e -> showAddRevenueDialog(username));

        headerPanel.add(title, BorderLayout.WEST);
        headerPanel.add(addBtn, BorderLayout.EAST);
        panel.add(headerPanel, BorderLayout.NORTH);

        // Load revenue data
        RevenueManager rm = new RevenueManager();
        rm.loadRevenueData(username);

        // Main content - 2 rows
        JPanel mainContent = new JPanel(new GridLayout(2, 1, 0, 20));
        mainContent.setOpaque(false);

        // Top row - Stats cards
        JPanel statsRow = new JPanel(new GridLayout(1, 3, 20, 0));
        statsRow.setOpaque(false);

        statsRow.add(createStatCardSimple("Total Revenue", "$" + String.format("%.2f", rm.totalRevenue), new Color(34, 139, 34)));
        statsRow.add(createStatCardSimple("Total Expenses", "$" + String.format("%.2f", rm.totalExpenses), new Color(239, 68, 68)));
        statsRow.add(createStatCardSimple("Net Profit", "$" + String.format("%.2f", rm.totalProfit), new Color(59, 130, 246)));

        // Bottom row - Chart and Table
        JPanel bottomRow = new JPanel(new GridLayout(1, 2, 20, 0));
        bottomRow.setOpaque(false);

        // Revenue Chart
        JPanel chartCard = createChartCard("Revenue vs Expenses",
                createRevenueBarChart(rm));

        // Revenue Table
        JPanel tableCard = createRevenueTableCard(username, rm);

        bottomRow.add(chartCard);
        bottomRow.add(tableCard);

        mainContent.add(statsRow);
        mainContent.add(bottomRow);

        panel.add(mainContent, BorderLayout.CENTER);
        return panel;
    }

    // Create Revenue Bar Chart
    private static ChartPanel createRevenueBarChart(RevenueManager rm) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Add data for each month
        for (RevenueManager.RevenueEntry entry : rm.revenueList) {
            String label = entry.month + " " + entry.year;
            dataset.addValue(entry.revenue, "Revenue", label);
            dataset.addValue(entry.expenses, "Expenses", label);
        }

        JFreeChart chart = ChartFactory.createBarChart(
                null,  // No title
                null,  // X-axis label
                "Amount ($)",  // Y-axis label
                dataset,
                PlotOrientation.VERTICAL,
                true,  // legend
                true,  // tooltips
                false  // urls
        );

        // Customize chart
        chart.setBackgroundPaint(Color.WHITE);
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setOutlineVisible(false);
        plot.setRangeGridlinePaint(new Color(226, 232, 240));

        // Set bar colors
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(34, 139, 34)); // Revenue - Green
        renderer.setSeriesPaint(1, new Color(239, 68, 68)); // Expenses - Red
        renderer.setBarPainter(new StandardBarPainter());

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(400, 300));
        chartPanel.setBackground(Color.WHITE);
        return chartPanel;
    }

    // Create Revenue Table Card
    private static JPanel createRevenueTableCard(String username, RevenueManager rm) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
            }
        };
        card.setOpaque(false);
        card.setLayout(new BorderLayout());
        card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Revenue History");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(new Color(26, 32, 44));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        card.add(titleLabel, BorderLayout.NORTH);

        // Table
        String[] columns = {"Month", "Year", "Revenue", "Expenses", "Profit"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);

        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setRowHeight(35);
        table.setShowGrid(false);
        table.setBackground(Color.WHITE);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(248, 250, 252));

        // Load data
        for (RevenueManager.RevenueEntry entry : rm.revenueList) {
            model.addRow(new Object[]{
                    entry.month,
                    entry.year,
                    entry.currency + String.format("%.2f", entry.revenue),
                    entry.currency + String.format("%.2f", entry.expenses),
                    entry.currency + String.format("%.2f", entry.profit)
            });
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);

        card.add(scrollPane, BorderLayout.CENTER);

        // Delete button
        JButton deleteBtn = new JButton("🗑️ Delete Selected");
        deleteBtn.setFocusPainted(false);
        deleteBtn.setForeground(Color.WHITE);
        deleteBtn.setBackground(new Color(239, 68, 68));
        deleteBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        deleteBtn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        deleteBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        deleteBtn.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                String month = (String) table.getValueAt(selectedRow, 0);
                int year = (int) table.getValueAt(selectedRow, 1);

                int confirm = JOptionPane.showConfirmDialog(frame,
                        "Delete revenue entry for " + month + " " + year + "?",
                        "Confirm Delete", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    if (rm.deleteRevenue(username, month, year)) {
                        model.removeRow(selectedRow);
                        JOptionPane.showMessageDialog(frame, "Revenue entry deleted!");
                        // Refresh panel
                        mainPanel.remove(1);
                        mainPanel.add(createRevenueTrackingPanel(username), BorderLayout.CENTER);
                        mainPanel.revalidate();
                        mainPanel.repaint();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a row!");
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        buttonPanel.add(deleteBtn);
        card.add(buttonPanel, BorderLayout.SOUTH);

        return card;
    }

    // Add Revenue Dialog with Calendar and Currency
    private static void showAddRevenueDialog(String username) {
        JPanel dialogPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        dialogPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Month dropdown
        String[] months = {"January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};
        JComboBox<String> monthBox = new JComboBox<>(months);

        // Year spinner
        JSpinner yearSpinner = new JSpinner(new SpinnerNumberModel(2024, 2020, 2030, 1));

        // Revenue field
        JTextField revenueField = new JTextField();

        // Expenses field
        JTextField expensesField = new JTextField();

        // Currency dropdown
        String[] currencies = {"$ (USD)", "€ (EUR)", "£ (GBP)", "¥ (JPY)", "₹ (INR)", "₨ (PKR)"};
        JComboBox<String> currencyBox = new JComboBox<>(currencies);

        dialogPanel.add(new JLabel("Month:"));
        dialogPanel.add(monthBox);
        dialogPanel.add(new JLabel("Year:"));
        dialogPanel.add(yearSpinner);
        dialogPanel.add(new JLabel("Revenue:"));
        dialogPanel.add(revenueField);
        dialogPanel.add(new JLabel("Expenses:"));
        dialogPanel.add(expensesField);
        dialogPanel.add(new JLabel("Currency:"));
        dialogPanel.add(currencyBox);

        int result = JOptionPane.showConfirmDialog(frame, dialogPanel,
                "Add Revenue Data", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String month = (String) monthBox.getSelectedItem();
                int year = (Integer) yearSpinner.getValue();
                float revenue = Float.parseFloat(revenueField.getText().trim());
                float expenses = Float.parseFloat(expensesField.getText().trim());
                String currencySymbol = ((String) currencyBox.getSelectedItem()).substring(0, 1);

                RevenueManager rm = new RevenueManager();
                if (rm.addRevenue(username, month, year, revenue, expenses, currencySymbol)) {
                    JOptionPane.showMessageDialog(frame, "Revenue added successfully!");
                    // Refresh panel
                    mainPanel.remove(1);
                    mainPanel.add(createRevenueTrackingPanel(username), BorderLayout.CENTER);
                    mainPanel.revalidate();
                    mainPanel.repaint();
                } else {
                    JOptionPane.showMessageDialog(frame, "Error adding revenue!");
                }

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Please enter valid numbers!");
            }
        }
    }
    // ============================================================================
// ADD THIS METHOD TO YOUR UIManager CLASS
// Also add the button action listener in openCreatorDashboard for "Time Management"
// ============================================================================

    private static JPanel createTimeManagementPanel(String username) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(247, 250, 252));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // Header with title and buttons
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        JLabel title = new JLabel("Time Management");
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        title.setForeground(new Color(26, 32, 44));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setOpaque(false);

        JButton addScheduleBtn = createHeaderButton("+ Add Schedule", new Color(34, 139, 34));
        addScheduleBtn.addActionListener(e -> showAddScheduleDialog(username));

        JButton addReminderBtn = createHeaderButton("+ Add Reminder", new Color(16, 185, 129));
        addReminderBtn.addActionListener(e -> showAddReminderDialog(username));

        JButton timerBtn = createHeaderButton("⏱️ Timer", new Color(59, 130, 246));
        timerBtn.addActionListener(e -> showCountdownTimerDialog());

        buttonPanel.add(addScheduleBtn);
        buttonPanel.add(addReminderBtn);
        buttonPanel.add(timerBtn);

        headerPanel.add(title, BorderLayout.WEST);
        headerPanel.add(buttonPanel, BorderLayout.EAST);
        panel.add(headerPanel, BorderLayout.NORTH);

        // Main content - Split into 2 sections
        JPanel mainContent = new JPanel(new GridLayout(1, 2, 20, 0));
        mainContent.setOpaque(false);
        mainContent.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));

        // Left: Work Sessions/Schedules
        JPanel schedulesCard = createSchedulesCard(username);

        // Right: Reminders
        JPanel remindersCard = createRemindersCard(username);

        mainContent.add(schedulesCard);
        mainContent.add(remindersCard);

        panel.add(mainContent, BorderLayout.CENTER);
        return panel;
    }

    // Create header button helper
    private static JButton createHeaderButton(String text, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setForeground(Color.WHITE);
        btn.setBackground(bgColor);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            Color original = bgColor;
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(
                        Math.min(255, original.getRed() + 20),
                        Math.min(255, original.getGreen() + 20),
                        Math.min(255, original.getBlue() + 20)
                ));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(original);
            }
        });

        return btn;
    }

    // Schedules Card
    private static JPanel createSchedulesCard(String username) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
            }
        };
        card.setOpaque(false);
        card.setLayout(new BorderLayout());
        card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("📅 Work Sessions");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(new Color(26, 32, 44));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        card.add(titleLabel, BorderLayout.NORTH);

        // Table
        String[] columns = {"Title", "Date", "Start", "End"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(model);

        styleTable(table);

        // Load data
        TimeManager tm = new TimeManager();
        ArrayList<TimeManager.ScheduleEntry> schedules = tm.getAllSchedules(username);
        for (TimeManager.ScheduleEntry s : schedules) {
            model.addRow(new Object[]{s.title, s.date, s.startTime, s.endTime});
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);

        // Bottom panel with delete button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setOpaque(false);

        JButton deleteBtn = new JButton("🗑️ Delete");
        deleteBtn.setFocusPainted(false);
        deleteBtn.setForeground(Color.WHITE);
        deleteBtn.setBackground(new Color(239, 68, 68));
        deleteBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        deleteBtn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        deleteBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        deleteBtn.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                String titleToDelete = (String) table.getValueAt(selectedRow, 0);
                int confirm = JOptionPane.showConfirmDialog(frame,
                        "Delete schedule: " + titleToDelete + "?",
                        "Confirm Delete", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    if (tm.deleteSchedule(username, titleToDelete)) {
                        model.removeRow(selectedRow);
                        JOptionPane.showMessageDialog(frame, "Schedule deleted!");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a row!");
            }
        });

        bottomPanel.add(deleteBtn);

        JPanel mainArea = new JPanel(new BorderLayout());
        mainArea.setOpaque(false);
        mainArea.add(scrollPane, BorderLayout.CENTER);
        mainArea.add(bottomPanel, BorderLayout.SOUTH);

        card.add(mainArea, BorderLayout.CENTER);
        return card;
    }

    // Reminders Card
    private static JPanel createRemindersCard(String username) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
            }
        };
        card.setOpaque(false);
        card.setLayout(new BorderLayout());
        card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("🔔 Reminders");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(new Color(26, 32, 44));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        card.add(titleLabel, BorderLayout.NORTH);

        // Table
        String[] columns = {"Description", "Date", "Time"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(model);

        styleTable(table);

        // Load data
        TimeManager tm = new TimeManager();
        ArrayList<TimeManager.ReminderEntry> reminders = tm.getAllReminders(username);
        for (TimeManager.ReminderEntry r : reminders) {
            model.addRow(new Object[]{r.description, r.date, r.time});
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);

        // Bottom panel with delete button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setOpaque(false);

        JButton deleteBtn = new JButton("🗑️ Delete");
        deleteBtn.setFocusPainted(false);
        deleteBtn.setForeground(Color.WHITE);
        deleteBtn.setBackground(new Color(239, 68, 68));
        deleteBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        deleteBtn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        deleteBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        deleteBtn.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                String descToDelete = (String) table.getValueAt(selectedRow, 0);
                int confirm = JOptionPane.showConfirmDialog(frame,
                        "Delete reminder: " + descToDelete + "?",
                        "Confirm Delete", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    if (tm.deleteReminder(username, descToDelete)) {
                        model.removeRow(selectedRow);
                        JOptionPane.showMessageDialog(frame, "Reminder deleted!");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a row!");
            }
        });

        bottomPanel.add(deleteBtn);

        JPanel mainArea = new JPanel(new BorderLayout());
        mainArea.setOpaque(false);
        mainArea.add(scrollPane, BorderLayout.CENTER);
        mainArea.add(bottomPanel, BorderLayout.SOUTH);

        card.add(mainArea, BorderLayout.CENTER);
        return card;
    }

    // Style table helper
    private static void styleTable(JTable table) {
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(40);
        table.setShowGrid(false);
        table.setBackground(Color.WHITE);
        table.setForeground(new Color(26, 32, 44));
        table.setSelectionBackground(new Color(241, 245, 249));
        table.setSelectionForeground(new Color(26, 32, 44));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(248, 250, 252));
        table.getTableHeader().setForeground(new Color(100, 116, 139));
    }

    // Add Schedule Dialog with Time Picker
    private static void showAddScheduleDialog(String username) {
        JPanel dialogPanel = new JPanel(new GridBagLayout());
        dialogPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Title
        gbc.gridx = 0; gbc.gridy = 0;
        dialogPanel.add(new JLabel("Title:"), gbc);

        gbc.gridx = 1;
        JTextField titleField = new JTextField(20);
        dialogPanel.add(titleField, gbc);

        // Date picker
        gbc.gridx = 0; gbc.gridy = 1;
        dialogPanel.add(new JLabel("Date:"), gbc);

        gbc.gridx = 1;
        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("dd/MM/yyyy");
        dateChooser.setDate(new Date());
        dialogPanel.add(dateChooser, gbc);

        // Start time
        gbc.gridx = 0; gbc.gridy = 2;
        dialogPanel.add(new JLabel("Start Time:"), gbc);

        gbc.gridx = 1;
        JPanel startTimePanel = createTimePicker();
        dialogPanel.add(startTimePanel, gbc);

        // End time
        gbc.gridx = 0; gbc.gridy = 3;
        dialogPanel.add(new JLabel("End Time:"), gbc);

        gbc.gridx = 1;
        JPanel endTimePanel = createTimePicker();
        dialogPanel.add(endTimePanel, gbc);

        int result = JOptionPane.showConfirmDialog(frame, dialogPanel,
                "Add Work Schedule", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String title = titleField.getText().trim();
            Date date = dateChooser.getDate();

            if (title.isEmpty() || date == null) {
                JOptionPane.showMessageDialog(frame, "Please fill all fields!");
                return;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String dateStr = sdf.format(date);

            String startTime = getTimeFromPicker(startTimePanel);
            String endTime = getTimeFromPicker(endTimePanel);

            TimeManager tm = new TimeManager();
            if (tm.addSchedule(username, title, startTime, endTime, dateStr)) {
                JOptionPane.showMessageDialog(frame, "Schedule added successfully!");
                // Refresh panel
                mainPanel.remove(1);
                mainPanel.add(createTimeManagementPanel(username), BorderLayout.CENTER);
                mainPanel.revalidate();
                mainPanel.repaint();
            }
        }
    }

    // Add Reminder Dialog
    private static void showAddReminderDialog(String username) {
        JPanel dialogPanel = new JPanel(new GridBagLayout());
        dialogPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Description
        gbc.gridx = 0; gbc.gridy = 0;
        dialogPanel.add(new JLabel("Description:"), gbc);

        gbc.gridx = 1;
        JTextField descField = new JTextField(20);
        dialogPanel.add(descField, gbc);

        // Date picker
        gbc.gridx = 0; gbc.gridy = 1;
        dialogPanel.add(new JLabel("Date:"), gbc);

        gbc.gridx = 1;
        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("dd/MM/yyyy");
        dateChooser.setDate(new Date());
        dialogPanel.add(dateChooser, gbc);

        // Time
        gbc.gridx = 0; gbc.gridy = 2;
        dialogPanel.add(new JLabel("Time:"), gbc);

        gbc.gridx = 1;
        JPanel timePanel = createTimePicker();
        dialogPanel.add(timePanel, gbc);

        int result = JOptionPane.showConfirmDialog(frame, dialogPanel,
                "Add Reminder", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String description = descField.getText().trim();
            Date date = dateChooser.getDate();

            if (description.isEmpty() || date == null) {
                JOptionPane.showMessageDialog(frame, "Please fill all fields!");
                return;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String dateStr = sdf.format(date);
            String time = getTimeFromPicker(timePanel);

            TimeManager tm = new TimeManager();
            if (tm.setReminder(username, description, time, dateStr)) {
                JOptionPane.showMessageDialog(frame, "Reminder added successfully!");
                // Refresh panel
                mainPanel.remove(1);
                mainPanel.add(createTimeManagementPanel(username), BorderLayout.CENTER);
                mainPanel.revalidate();
                mainPanel.repaint();
            }
        }
    }

    // Create Time Picker (HH:MM with AM/PM)
    private static JPanel createTimePicker() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));

        // Hour spinner (1-12)
        SpinnerNumberModel hourModel = new SpinnerNumberModel(9, 1, 12, 1);
        JSpinner hourSpinner = new JSpinner(hourModel);
        hourSpinner.setPreferredSize(new Dimension(50, 25));

        JLabel colon = new JLabel(":");

        // Minute spinner (0-59)
        SpinnerNumberModel minModel = new SpinnerNumberModel(0, 0, 59, 1);
        JSpinner minSpinner = new JSpinner(minModel);
        minSpinner.setPreferredSize(new Dimension(50, 25));

        // AM/PM dropdown
        JComboBox<String> ampmBox = new JComboBox<>(new String[]{"AM", "PM"});
        ampmBox.setPreferredSize(new Dimension(60, 25));

        panel.add(hourSpinner);
        panel.add(colon);
        panel.add(minSpinner);
        panel.add(ampmBox);

        return panel;
    }

    // Get time string from time picker panel
    private static String getTimeFromPicker(JPanel timePanel) {
        Component[] components = timePanel.getComponents();
        JSpinner hourSpinner = (JSpinner) components[0];
        JSpinner minSpinner = (JSpinner) components[2];
        JComboBox ampmBox = (JComboBox) components[3];

        int hour = (int) hourSpinner.getValue();
        int min = (int) minSpinner.getValue();
        String ampm = (String) ampmBox.getSelectedItem();

        return String.format("%02d:%02d %s", hour, min, ampm);
    }

    // Countdown Timer Dialog
    private static void showCountdownTimerDialog() {
        JPanel dialogPanel = new JPanel(new GridBagLayout());
        dialogPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel label = new JLabel("Set Timer Duration:");
        label.setFont(new Font("Segoe UI", Font.BOLD, 16));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        dialogPanel.add(label, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;

        // Minutes spinner
        gbc.gridx = 0;
        dialogPanel.add(new JLabel("Minutes:"), gbc);

        gbc.gridx = 1;
        SpinnerNumberModel model = new SpinnerNumberModel(5, 1, 120, 1);
        JSpinner minutesSpinner = new JSpinner(model);
        minutesSpinner.setPreferredSize(new Dimension(80, 30));
        dialogPanel.add(minutesSpinner, gbc);

        int result = JOptionPane.showConfirmDialog(frame, dialogPanel,
                "Countdown Timer", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            int minutes = (int) minutesSpinner.getValue();
            startCountdownTimer(minutes);
        }
    }

    // Start countdown timer in separate window
    private static void startCountdownTimer(int totalMinutes) {
        JDialog timerDialog = new JDialog(frame, "Countdown Timer", false);
        timerDialog.setSize(400, 250);
        timerDialog.setLocationRelativeTo(frame);
        timerDialog.setLayout(new BorderLayout());

        JPanel contentPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(34, 139, 34),
                        0, getHeight(), new Color(16, 185, 129)
                );
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        contentPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel timeLabel = new JLabel(totalMinutes + ":00");
        timeLabel.setFont(new Font("Segoe UI", Font.BOLD, 60));
        timeLabel.setForeground(Color.WHITE);
        gbc.gridy = 0;
        contentPanel.add(timeLabel, gbc);

        JLabel statusLabel = new JLabel("Timer Running...");
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        statusLabel.setForeground(Color.WHITE);
        gbc.gridy = 1;
        gbc.insets = new Insets(20, 0, 0, 0);
        contentPanel.add(statusLabel, gbc);

        JButton stopBtn = new JButton("Stop Timer");
        stopBtn.setFocusPainted(false);
        stopBtn.setBackground(new Color(239, 68, 68));
        stopBtn.setForeground(Color.WHITE);
        stopBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        stopBtn.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        gbc.gridy = 2;
        gbc.insets = new Insets(30, 0, 0, 0);
        contentPanel.add(stopBtn, gbc);

        timerDialog.add(contentPanel);
        timerDialog.setVisible(true);

        // Timer thread
        Thread timerThread = new Thread(() -> {
            int totalSeconds = totalMinutes * 60;
            for (int remaining = totalSeconds; remaining >= 0; remaining--) {
                final int mins = remaining / 60;
                final int secs = remaining % 60;

                SwingUtilities.invokeLater(() -> {
                    timeLabel.setText(String.format("%02d:%02d", mins, secs));
                });

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    SwingUtilities.invokeLater(() -> {
                        statusLabel.setText("Timer Stopped");
                        stopBtn.setText("Close");
                    });
                    return;
                }
            }

            SwingUtilities.invokeLater(() -> {
                statusLabel.setText("Time's Up! 🎉");
                stopBtn.setText("Close");
                JOptionPane.showMessageDialog(timerDialog,
                        "Timer completed! Take a break.",
                        "Time's Up!",
                        JOptionPane.INFORMATION_MESSAGE);
            });
        });

        stopBtn.addActionListener(e -> {
            if (stopBtn.getText().equals("Stop Timer")) {
                timerThread.interrupt();
            } else {
                timerDialog.dispose();
            }
        });

        timerThread.start();
    }

// ============================================================================
// ADD THIS METHOD TO YOUR UIManager CLASS
// Also add the button action listener in openCreatorDashboard for "Channel Growth"
// ============================================================================

    private static JPanel createChannelGrowthPanel(String username) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(247, 250, 252));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        JLabel title = new JLabel("Channel Growth Tracking");
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        title.setForeground(new Color(26, 32, 44));

        JButton addBtn = new JButton("+ Add Growth Data");
        addBtn.setFocusPainted(false);
        addBtn.setForeground(Color.WHITE);
        addBtn.setBackground(new Color(34, 139, 34));
        addBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        addBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        addBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addBtn.addActionListener(e -> showAddGrowthDialog(username));

        headerPanel.add(title, BorderLayout.WEST);
        headerPanel.add(addBtn, BorderLayout.EAST);
        panel.add(headerPanel, BorderLayout.NORTH);

        // Load growth data
        GrowthManager gm = new GrowthManager();
        gm.loadGrowthData(username);

        // Main content - 3 sections
        JPanel mainContent = new JPanel(new BorderLayout(0, 20));
        mainContent.setOpaque(false);
        mainContent.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));

        // Top: Stats cards
        JPanel statsRow = new JPanel(new GridLayout(1, 2, 20, 0));
        statsRow.setOpaque(false);
        statsRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

        statsRow.add(createStatCardSimple("Total Subscribers",
                String.format("%,d", gm.totalSubscribers), new Color(34, 139, 34)));
        statsRow.add(createStatCardSimple("Total Views",
                String.format("%,d", gm.totalViews), new Color(59, 130, 246)));

        // Middle: Date filter + Charts
        JPanel chartsSection = new JPanel(new BorderLayout(0, 15));
        chartsSection.setOpaque(false);

        // Date filter panel
        JPanel filterPanel = createDateFilterPanel(username, gm);
        chartsSection.add(filterPanel, BorderLayout.NORTH);

        // Charts (2 line graphs side by side)
        JPanel chartsRow = new JPanel(new GridLayout(1, 2, 20, 0));
        chartsRow.setOpaque(false);

        // Subscribers Growth Chart - createGrowthLineChart now returns JPanel
        JPanel subsChartCard = createChartCardForGrowth("Subscribers Growth Trend",
                createGrowthLineChart(gm, "Subscribers", true));

        // Views Growth Chart - createGrowthLineChart now returns JPanel
        JPanel viewsChartCard = createChartCardForGrowth("Views Growth Trend",
                createGrowthLineChart(gm, "Views", false));

        chartsRow.add(subsChartCard);
        chartsRow.add(viewsChartCard);
        chartsSection.add(chartsRow, BorderLayout.CENTER);

        // Bottom: Data table
        JPanel tableCard = createGrowthTableCard(username, gm);

        // Assemble everything
        JPanel topSection = new JPanel(new BorderLayout(0, 20));
        topSection.setOpaque(false);
        topSection.add(statsRow, BorderLayout.NORTH);
        topSection.add(chartsSection, BorderLayout.CENTER);

        mainContent.add(topSection, BorderLayout.NORTH);
        mainContent.add(tableCard, BorderLayout.CENTER);

        panel.add(mainContent, BorderLayout.CENTER);
        return panel;
    }

    // Create Date Filter Panel
    private static JPanel createDateFilterPanel(String username, GrowthManager gm) {
        JPanel filterCard = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
            }
        };
        filterCard.setOpaque(false);
        filterCard.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 15));
        filterCard.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        JLabel filterLabel = new JLabel("📊 Filter Growth Data:");
        filterLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        filterLabel.setForeground(new Color(26, 32, 44));

        // Get available months
        ArrayList<String> availableMonths = gm.getAvailableMonthYears(username);

        if (availableMonths.isEmpty()) {
            JLabel noDataLabel = new JLabel("No data available. Add growth data to see charts.");
            noDataLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            noDataLabel.setForeground(new Color(113, 128, 150));
            filterCard.add(noDataLabel);
            return filterCard;
        }

        JLabel fromLabel = new JLabel("From:");
        fromLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JComboBox<String> startDateBox = new JComboBox<>(availableMonths.toArray(new String[0]));
        startDateBox.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        startDateBox.setPreferredSize(new Dimension(150, 30));

        JLabel toLabel = new JLabel("To:");
        toLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JComboBox<String> endDateBox = new JComboBox<>(availableMonths.toArray(new String[0]));
        endDateBox.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        endDateBox.setPreferredSize(new Dimension(150, 30));
        endDateBox.setSelectedIndex(availableMonths.size() - 1); // Select last date by default

        JButton applyBtn = new JButton("Apply Filter");
        applyBtn.setFocusPainted(false);
        applyBtn.setForeground(Color.WHITE);
        applyBtn.setBackground(new Color(34, 139, 34));
        applyBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        applyBtn.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        applyBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        applyBtn.addActionListener(e -> {
            String startMonthYear = (String) startDateBox.getSelectedItem();
            String endMonthYear = (String) endDateBox.getSelectedItem();

            if (startMonthYear != null && endMonthYear != null) {
                // Parse month and year
                String[] startParts = startMonthYear.split(" ");
                String[] endParts = endMonthYear.split(" ");

                String startMonth = startParts[0];
                int startYear = Integer.parseInt(startParts[1]);
                String endMonth = endParts[0];
                int endYear = Integer.parseInt(endParts[1]);

                // Apply filter and refresh panel
                mainPanel.remove(1);
                mainPanel.add(createFilteredChannelGrowthPanel(username, startMonth, startYear, endMonth, endYear),
                        BorderLayout.CENTER);
                mainPanel.revalidate();
                mainPanel.repaint();
            }
        });

        JButton resetBtn = new JButton("Reset");
        resetBtn.setFocusPainted(false);
        resetBtn.setForeground(new Color(100, 116, 139));
        resetBtn.setBackground(new Color(226, 232, 240));
        resetBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        resetBtn.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        resetBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        resetBtn.addActionListener(e -> {
            mainPanel.remove(1);
            mainPanel.add(createChannelGrowthPanel(username), BorderLayout.CENTER);
            mainPanel.revalidate();
            mainPanel.repaint();
        });

        filterCard.add(filterLabel);
        filterCard.add(fromLabel);
        filterCard.add(startDateBox);
        filterCard.add(toLabel);
        filterCard.add(endDateBox);
        filterCard.add(applyBtn);
        filterCard.add(resetBtn);

        return filterCard;
    }

    // Create filtered panel (after date selection)
    private static JPanel createFilteredChannelGrowthPanel(String username, String startMonth, int startYear,
                                                           String endMonth, int endYear) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(247, 250, 252));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        JLabel title = new JLabel("Channel Growth Tracking");
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        title.setForeground(new Color(26, 32, 44));

        JButton addBtn = new JButton("+ Add Growth Data");
        addBtn.setFocusPainted(false);
        addBtn.setForeground(Color.WHITE);
        addBtn.setBackground(new Color(34, 139, 34));
        addBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        addBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        addBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addBtn.addActionListener(e -> showAddGrowthDialog(username));

        headerPanel.add(title, BorderLayout.WEST);
        headerPanel.add(addBtn, BorderLayout.EAST);
        panel.add(headerPanel, BorderLayout.NORTH);

        // Load FILTERED growth data
        GrowthManager gm = new GrowthManager();
        ArrayList<GrowthManager.GrowthEntry> filteredData = gm.getGrowthDataBetweenDates(
                username, startMonth, startYear, endMonth, endYear);

        // Calculate totals for filtered data
        int filteredSubs = 0;
        int filteredViews = 0;
        for (GrowthManager.GrowthEntry entry : filteredData) {
            filteredSubs += entry.subscribers;
            filteredViews += entry.views;
        }

        // Main content
        JPanel mainContent = new JPanel(new BorderLayout(0, 20));
        mainContent.setOpaque(false);
        mainContent.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));

        // Stats for filtered period
        JPanel statsRow = new JPanel(new GridLayout(1, 2, 20, 0));
        statsRow.setOpaque(false);
        statsRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

        statsRow.add(createStatCardSimple("Subscribers (" + startMonth + " " + startYear + " - " + endMonth + " " + endYear + ")",
                String.format("%,d", filteredSubs), new Color(34, 139, 34)));
        statsRow.add(createStatCardSimple("Views (" + startMonth + " " + startYear + " - " + endMonth + " " + endYear + ")",
                String.format("%,d", filteredViews), new Color(59, 130, 246)));

        // Charts section
        JPanel chartsSection = new JPanel(new BorderLayout(0, 15));
        chartsSection.setOpaque(false);

        // Date filter panel
        gm.loadGrowthData(username); // Reload full data for filter dropdowns
        JPanel filterPanel = createDateFilterPanel(username, gm);
        chartsSection.add(filterPanel, BorderLayout.NORTH);

        // Charts with filtered data
        JPanel chartsRow = new JPanel(new GridLayout(1, 2, 20, 0));
        chartsRow.setOpaque(false);

        JPanel subsChartCard = createChartCardForGrowth("Subscribers Growth Trend",
                createFilteredGrowthLineChart(filteredData, "Subscribers", true));

        JPanel viewsChartCard = createChartCardForGrowth("Views Growth Trend",
                createFilteredGrowthLineChart(filteredData, "Views", false));

        chartsRow.add(subsChartCard);
        chartsRow.add(viewsChartCard);
        chartsSection.add(chartsRow, BorderLayout.CENTER);

        // Table with filtered data
        JPanel tableCard = createFilteredGrowthTableCard(username, filteredData);

        JPanel topSection = new JPanel(new BorderLayout(0, 20));
        topSection.setOpaque(false);
        topSection.add(statsRow, BorderLayout.NORTH);
        topSection.add(chartsSection, BorderLayout.CENTER);

        mainContent.add(topSection, BorderLayout.NORTH);
        mainContent.add(tableCard, BorderLayout.CENTER);

        panel.add(mainContent, BorderLayout.CENTER);
        return panel;
    }

    // Create Line Chart for Growth (All data)
    private static JPanel createGrowthLineChart(GrowthManager gm, String metric, boolean isSubscribers) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (GrowthManager.GrowthEntry entry : gm.growthList) {
            String label = entry.month.substring(0, 3) + " " + entry.year;
            int value = isSubscribers ? entry.cumulativeSubscribers : entry.cumulativeViews;
            dataset.addValue(value, metric, label);
        }

        JFreeChart chart = ChartFactory.createLineChart(
                null, // No title
                "Month/Year",
                metric,
                dataset,
                PlotOrientation.VERTICAL,
                false, // legend
                true, // tooltips
                false // urls
        );

        customizeLineChart(chart, isSubscribers);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(Math.max(450, dataset.getColumnCount() * 80), 280));
        chartPanel.setBackground(Color.WHITE);
        chartPanel.setDomainZoomable(false);
        chartPanel.setRangeZoomable(false);

        // Wrap in scroll pane
        JScrollPane scrollPane = new JScrollPane(chartPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(Color.WHITE);
        wrapper.add(scrollPane, BorderLayout.CENTER);

        return wrapper;
    }

    // Create Line Chart for Growth (Filtered data)
    private static JPanel createFilteredGrowthLineChart(ArrayList<GrowthManager.GrowthEntry> filteredData, String metric, boolean isSubscribers) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (GrowthManager.GrowthEntry entry : filteredData) {
            String label = entry.month.substring(0, 3) + " " + entry.year;
            int value = isSubscribers ? entry.cumulativeSubscribers : entry.cumulativeViews;
            dataset.addValue(value, metric, label);
        }

        JFreeChart chart = ChartFactory.createLineChart(
                null,
                "Month/Year",
                metric,
                dataset,
                PlotOrientation.VERTICAL,
                false,
                true,
                false
        );

        customizeLineChart(chart, isSubscribers);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(Math.max(450, dataset.getColumnCount() * 80), 280));
        chartPanel.setBackground(Color.WHITE);
        chartPanel.setDomainZoomable(false);
        chartPanel.setRangeZoomable(false);

        // Wrap in scroll pane
        JScrollPane scrollPane = new JScrollPane(chartPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(Color.WHITE);
        wrapper.add(scrollPane, BorderLayout.CENTER);

        return wrapper;
    }

    // Customize line chart appearance
    private static void customizeLineChart(JFreeChart chart, boolean isSubscribers) {
        chart.setBackgroundPaint(Color.WHITE);
        chart.setPadding(new org.jfree.chart.ui.RectangleInsets(10, 10, 20, 10));

        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setOutlineVisible(false);
        plot.setRangeGridlinePaint(new Color(226, 232, 240));
        plot.setDomainGridlinePaint(new Color(226, 232, 240));
        plot.setInsets(new org.jfree.chart.ui.RectangleInsets(10, 10, 40, 10)); // Extra bottom space

        // Line color
        org.jfree.chart.renderer.category.LineAndShapeRenderer renderer =
                (org.jfree.chart.renderer.category.LineAndShapeRenderer) plot.getRenderer();
        Color lineColor = isSubscribers ? new Color(34, 139, 34) : new Color(59, 130, 246);
        renderer.setSeriesPaint(0, lineColor);
        renderer.setSeriesStroke(0, new java.awt.BasicStroke(3.0f));
        renderer.setSeriesShapesVisible(0, true);
        renderer.setSeriesShape(0, new java.awt.geom.Ellipse2D.Double(-4, -4, 8, 8));

        // X-axis labels with smaller font
        org.jfree.chart.axis.CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(org.jfree.chart.axis.CategoryLabelPositions.STANDARD);
        domainAxis.setTickLabelFont(new Font("Segoe UI", Font.PLAIN, 10)); // Smaller font
        domainAxis.setLabelFont(new Font("Segoe UI", Font.BOLD, 12));
        domainAxis.setLowerMargin(0.02);
        domainAxis.setUpperMargin(0.02);
        domainAxis.setCategoryMargin(0.2);

        // Y-axis font
        org.jfree.chart.axis.ValueAxis rangeAxis = plot.getRangeAxis();
        rangeAxis.setTickLabelFont(new Font("Segoe UI", Font.PLAIN, 10));
        rangeAxis.setLabelFont(new Font("Segoe UI", Font.BOLD, 12));
    }

    // NEW METHOD: Create chart card specifically for growth charts (accepts JPanel)
    private static JPanel createChartCardForGrowth(String title, JPanel chartPanel) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
            }
        };
        card.setOpaque(false);
        card.setLayout(new BorderLayout());
        card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(new Color(26, 32, 44));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(chartPanel, BorderLayout.CENTER);

        return card;
    }

    private static JPanel createGrowthTableCard(String username, GrowthManager gm) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
            }
        };
        card.setOpaque(false);
        card.setLayout(new BorderLayout());
        card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Growth History");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(new Color(26, 32, 44));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        card.add(titleLabel, BorderLayout.NORTH);

        // Table
        String[] columns = {"Month", "Year", "Subscribers", "Views", "Cumulative Subs", "Cumulative Views"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);

        styleTable(table);

        // Load data
        for (GrowthManager.GrowthEntry entry : gm.growthList) {
            model.addRow(new Object[]{
                    entry.month,
                    entry.year,
                    String.format("%,d", entry.subscribers),
                    String.format("%,d", entry.views),
                    String.format("%,d", entry.cumulativeSubscribers),
                    String.format("%,d", entry.cumulativeViews)
            });
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);

        // Delete button
        JButton deleteBtn = new JButton("🗑️ Delete Selected");
        deleteBtn.setFocusPainted(false);
        deleteBtn.setForeground(Color.WHITE);
        deleteBtn.setBackground(new Color(239, 68, 68));
        deleteBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        deleteBtn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        deleteBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        deleteBtn.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                String month = (String) table.getValueAt(selectedRow, 0);
                int year = (int) table.getValueAt(selectedRow, 1);

                int confirm = JOptionPane.showConfirmDialog(frame,
                        "Delete growth data for " + month + " " + year + "?",
                        "Confirm Delete", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    if (gm.deleteGrowthData(username, month, year)) {
                        JOptionPane.showMessageDialog(frame, "Growth data deleted!");
                        // Refresh panel
                        mainPanel.remove(1);
                        mainPanel.add(createChannelGrowthPanel(username), BorderLayout.CENTER);
                        mainPanel.revalidate();
                        mainPanel.repaint();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a row!");
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        buttonPanel.add(deleteBtn);

        JPanel mainArea = new JPanel(new BorderLayout());
        mainArea.setOpaque(false);
        mainArea.add(scrollPane, BorderLayout.CENTER);
        mainArea.add(buttonPanel, BorderLayout.SOUTH);

        card.add(mainArea, BorderLayout.CENTER);
        return card;
    }

    // Create Filtered Growth Table Card
    private static JPanel createFilteredGrowthTableCard(String username, ArrayList<GrowthManager.GrowthEntry> filteredData) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
            }
        };
        card.setOpaque(false);
        card.setLayout(new BorderLayout());
        card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Growth History (Filtered)");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(new Color(26, 32, 44));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        card.add(titleLabel, BorderLayout.NORTH);

        String[] columns = {"Month", "Year", "Subscribers", "Views", "Cumulative Subs", "Cumulative Views"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);

        styleTable(table);

        for (GrowthManager.GrowthEntry entry : filteredData) {
            model.addRow(new Object[]{
                    entry.month,
                    entry.year,
                    String.format("%,d", entry.subscribers),
                    String.format("%,d", entry.views),
                    String.format("%,d", entry.cumulativeSubscribers),
                    String.format("%,d", entry.cumulativeViews)
            });
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);

        card.add(scrollPane, BorderLayout.CENTER);
        return card;
    }

    // Add Growth Data Dialog
    private static void showAddGrowthDialog(String username) {
        JPanel dialogPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        dialogPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Month dropdown
        String[] months = {"January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};
        JComboBox<String> monthBox = new JComboBox<>(months);

        // Year spinner
        JSpinner yearSpinner = new JSpinner(new SpinnerNumberModel(2024, 2020, 2030, 1));

        // Subscribers field
        JTextField subscribersField = new JTextField();

        // Views field
        JTextField viewsField = new JTextField();

        dialogPanel.add(new JLabel("Month:"));
        dialogPanel.add(monthBox);
        dialogPanel.add(new JLabel("Year:"));
        dialogPanel.add(yearSpinner);
        dialogPanel.add(new JLabel("Subscribers Gained:"));
        dialogPanel.add(subscribersField);
        dialogPanel.add(new JLabel("Views:"));
        dialogPanel.add(viewsField);

        int result = JOptionPane.showConfirmDialog(frame, dialogPanel,
                "Add Growth Data", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String month = (String) monthBox.getSelectedItem();
                int year = (Integer) yearSpinner.getValue();
                int subscribers = Integer.parseInt(subscribersField.getText().trim());
                int views = Integer.parseInt(viewsField.getText().trim());

                GrowthManager gm = new GrowthManager();
                if (gm.addGrowthData(username, month, year, subscribers, views)) {
                    JOptionPane.showMessageDialog(frame, "Growth data added successfully!");
                    // Refresh panel
                    mainPanel.remove(1);
                    mainPanel.add(createChannelGrowthPanel(username), BorderLayout.CENTER);
                    mainPanel.revalidate();
                    mainPanel.repaint();
                }

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Please enter valid numbers!");
            }
        }
    }


}

















