package QuanLyKyThi;

// Thêm import cho callback interface
import java.util.function.Consumer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Form đăng nhập hệ thống
 */
public class LoginForm extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin, btnExit;
    private JLabel lblStatus;
    private AuthenticationService authService;
    
    // Callback để xử lý kết quả đăng nhập
    private Consumer<AuthenticationService> loginSuccessCallback;
    private Runnable loginCancelCallback;
    
    public LoginForm() {
        this.authService = new AuthenticationService();
        initComponents();
    }
    
    // Constructor với callback
    public LoginForm(Consumer<AuthenticationService> onLoginSuccess, Runnable onLoginCancel) {
        this();
        this.loginSuccessCallback = onLoginSuccess;
        this.loginCancelCallback = onLoginCancel;
    }
    
    /**
     * Constructor với kích thước tùy chỉnh
     */
    public LoginForm(int width, int height) {
        this.authService = new AuthenticationService();
        initComponentsWithSize(width, height);
    }
    
    /**
     * Constructor với callback và kích thước tùy chỉnh
     */
    public LoginForm(int width, int height, Consumer<AuthenticationService> onLoginSuccess, Runnable onLoginCancel) {
        this.authService = new AuthenticationService();
        this.loginSuccessCallback = onLoginSuccess;
        this.loginCancelCallback = onLoginCancel;
        initComponentsWithSize(width, height);
    }
    
    /**
     * Các preset kích thước phổ biến
     */
    public static final int[] SIZE_SMALL = {1024, 768};      // 4:3 - Nhỏ
    public static final int[] SIZE_MEDIUM = {1280, 720};     // 16:9 - Trung bình
    public static final int[] SIZE_LARGE = {1366, 768};      // Laptop phổ biến
    public static final int[] SIZE_XLARGE = {1600, 900};     // Widescreen
    public static final int[] SIZE_FULLHD = {1920, 1080};    // Full HD
    
    /**
     * Tạo LoginForm với preset kích thước
     */
    public static LoginForm createWithSize(int[] sizePreset) {
        return new LoginForm(sizePreset[0], sizePreset[1]);
    }
    
    /**
     * Tạo LoginForm với preset kích thước và callback
     */
    public static LoginForm createWithSize(int[] sizePreset, Consumer<AuthenticationService> onLoginSuccess, Runnable onLoginCancel) {
        return new LoginForm(sizePreset[0], sizePreset[1], onLoginSuccess, onLoginCancel);
    }
    
    /**
     * Khởi tạo components với kích thước tùy chỉnh
     */
    private void initComponentsWithSize(int width, int height) {
        setTitle("Đăng Nhập - Hệ Thống Quản Lý Kỳ Thi");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(width, height);
        setLocationRelativeTo(null);
        setResizable(true); // Cho phép thay đổi kích thước bằng cách kéo thả chuột
        
        // Set kích thước tối thiểu để tránh giao diện bị vỡ
        setMinimumSize(new Dimension(800, 500));
        
        // Thêm ComponentListener để xử lý khi người dùng thay đổi kích thước
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent evt) {
                handleWindowResize();
            }
        });
        
        // Thêm thông báo về tính năng resize
        System.out.println("LoginForm - Có thể kéo thả để thay đổi kích thước. Kích thước hiện tại: " + width + "×" + height);
        
        // Tính toán kích thước các panel dựa trên tỷ lệ
        int brandWidth = (int)(width * 0.65); // 65% cho brand panel
        int loginWidth = (int)(width * 0.35); // 35% cho login panel
        
        // Panel chính sử dụng BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Panel bên trái - Logo/Brand với gradient background
        JPanel brandPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                int w = getWidth(), h = getHeight();
                Color color1 = new Color(0, 102, 204);
                Color color2 = new Color(102, 178, 255);
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, h, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        brandPanel.setLayout(new GridBagLayout());
        brandPanel.setPreferredSize(new Dimension(brandWidth, height));
        
        // Thêm nội dung brand panel
        setupBrandPanel(brandPanel);
        
        // Panel form đăng nhập bên phải
        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBackground(Color.WHITE);
        loginPanel.setPreferredSize(new Dimension(loginWidth, height));
        
        // Tính toán padding dựa trên kích thước
        int padding = Math.max(20, width / 50);
        loginPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createRaisedBevelBorder(),
            BorderFactory.createEmptyBorder(padding, padding, padding, padding)
        ));
        
        // Setup login form với kích thước responsive
        setupLoginForm(loginPanel, width, height);
        
        // Thêm các panel vào main panel
        mainPanel.add(brandPanel, BorderLayout.WEST);
        mainPanel.add(loginPanel, BorderLayout.CENTER);
        
        add(mainPanel);
        
        // Event handlers
        setupEventHandlers();
        
        // Focus vào username field
        SwingUtilities.invokeLater(() -> txtUsername.requestFocus());
    }
    
    /**
     * Setup login form với kích thước responsive
     */
    private void setupLoginForm(JPanel loginPanel, int width, int height) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Tính toán font size dựa trên kích thước
        int titleFontSize = Math.max(16, width / 80);
        int labelFontSize = Math.max(14, width / 90);
        int fieldFontSize = Math.max(14, width / 90);
        int buttonFontSize = Math.max(12, width / 100);
        
        // Tính toán kích thước field
        int fieldWidth = Math.min(350, width / 4);
        int fieldHeight = Math.max(35, height / 20);
        
        // Logo/Title cho form đăng nhập
        JLabel titleLabel = new JLabel("ĐĂNG NHẬP");
        titleLabel.setFont(new Font("Arial", Font.BOLD, titleFontSize));
        titleLabel.setForeground(new Color(0, 102, 204));
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        loginPanel.add(titleLabel, gbc);
        
        // Icon
        try {
            JLabel iconLabel = new JLabel("🔐");
            iconLabel.setFont(new Font("Arial", Font.PLAIN, titleFontSize + 16));
            gbc.gridy = 1;
            loginPanel.add(iconLabel, gbc);
        } catch (Exception e) {
            // Nếu không có icon thì bỏ qua
        }
        
        // Reset gridwidth
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        
        // Username Label
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        JLabel lblUsername = new JLabel("Tên đăng nhập:");
        lblUsername.setFont(new Font("Arial", Font.BOLD, labelFontSize));
        loginPanel.add(lblUsername, gbc);
        
        // Username Field
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        txtUsername = new JTextField(20);
        txtUsername.setFont(new Font("Arial", Font.PLAIN, fieldFontSize));
        txtUsername.setPreferredSize(new Dimension(fieldWidth, fieldHeight));
        txtUsername.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLoweredBevelBorder(),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        loginPanel.add(txtUsername, gbc);
        
        // Password Label
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(20, 10, 5, 10);
        JLabel lblPassword = new JLabel("Mật khẩu:");
        lblPassword.setFont(new Font("Arial", Font.BOLD, labelFontSize));
        loginPanel.add(lblPassword, gbc);
        
        // Password Field
        gbc.gridx = 0; gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 10, 10);
        txtPassword = new JPasswordField(20);
        txtPassword.setFont(new Font("Arial", Font.PLAIN, fieldFontSize));
        txtPassword.setPreferredSize(new Dimension(fieldWidth, fieldHeight));
        txtPassword.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLoweredBevelBorder(),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        loginPanel.add(txtPassword, gbc);
        
        // Status label
        gbc.gridx = 0; gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(10, 10, 10, 10);
        lblStatus = new JLabel(" ");
        lblStatus.setFont(new Font("Arial", Font.ITALIC, labelFontSize - 2));
        lblStatus.setForeground(Color.RED);
        lblStatus.setPreferredSize(new Dimension(fieldWidth, 25));
        loginPanel.add(lblStatus, gbc);
        
        // Buttons
        gbc.gridy = 7;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(15, 10, 10, 10);
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        buttonPanel.setOpaque(false);
        
        int buttonWidth = Math.max(100, fieldWidth / 3);
        int buttonHeight = Math.max(35, fieldHeight);
        
        btnLogin = new JButton("ĐĂNG NHẬP");
        btnLogin.setBackground(new Color(0, 153, 0));
        btnLogin.setForeground(Color.BLACK);
        btnLogin.setFont(new Font("Arial", Font.BOLD, buttonFontSize));
        btnLogin.setFocusPainted(false);
        btnLogin.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        btnLogin.setBorder(BorderFactory.createRaisedBevelBorder());
        
        btnExit = new JButton("THOÁT");
        btnExit.setBackground(new Color(204, 0, 0));
        btnExit.setForeground(Color.BLACK);
        btnExit.setFont(new Font("Arial", Font.BOLD, buttonFontSize));
        btnExit.setFocusPainted(false);
        btnExit.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        btnExit.setBorder(BorderFactory.createRaisedBevelBorder());
        
        buttonPanel.add(btnLogin);
        buttonPanel.add(btnExit);
        loginPanel.add(buttonPanel, gbc);
    }
    
    private void initComponents() {
        // Sử dụng kích thước mặc định 1280x720
        initComponentsWithSize(1280, 720);
    }
    
    /**
     * Thiết lập nội dung cho brand panel bên trái
     */
    private void setupBrandPanel(JPanel brandPanel) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.anchor = GridBagConstraints.CENTER;
        
        // Logo chính - có thể thay bằng hình ảnh thực
        JLabel logoLabel = new JLabel("🎓");
        logoLabel.setFont(new Font("Arial", Font.PLAIN, 80));
        logoLabel.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 0;
        brandPanel.add(logoLabel, gbc);
        
        // Tên hệ thống
        JLabel systemNameLabel = new JLabel("HỆ THỐNG QUẢN LÝ KỲ THI");
        systemNameLabel.setFont(new Font("Arial", Font.BOLD, 24));
        systemNameLabel.setForeground(Color.WHITE);
        systemNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 20, 10, 20);
        brandPanel.add(systemNameLabel, gbc);
        
        // Phiên bản
        JLabel versionLabel = new JLabel("Phiên bản 2.0");
        versionLabel.setFont(new Font("Arial", Font.ITALIC, 16));
        versionLabel.setForeground(new Color(220, 220, 220));
        versionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 2;
        gbc.insets = new Insets(5, 20, 20, 20);
        brandPanel.add(versionLabel, gbc);
        
        // Mô tả tính năng
        JLabel featuresLabel = new JLabel("<html><div style='text-align: center; line-height: 1.6;'>" +
            "✓ Quản lý Kỳ thi và Thí sinh<br/>" +
            "✓ Phân quyền người dùng<br/>" +
            "✓ Nhập điểm và Báo cáo<br/>" +
            "✓ Thống kê và Phân tích<br/>" +
            "✓ Bảo mật và Đăng nhập</div></html>");
        featuresLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        featuresLabel.setForeground(new Color(240, 240, 240));
        featuresLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 3;
        gbc.insets = new Insets(30, 20, 20, 20);
        brandPanel.add(featuresLabel, gbc);
        
        // Copyright/Footer
        JLabel footerLabel = new JLabel("<html><div style='text-align: center; line-height: 1.4;'>" +
            "© 2025 Hệ Thống Quản Lý Kỳ Thi<br/>" +
            "Phát triển bởi Java Swing</div></html>");
        footerLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        footerLabel.setForeground(new Color(200, 200, 200));
        footerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 4;
        gbc.insets = new Insets(40, 20, 20, 20);
        brandPanel.add(footerLabel, gbc);
    }
    
    private void setupEventHandlers() {
        btnLogin.addActionListener(e -> performLogin());
        btnExit.addActionListener(e -> System.exit(0));
        
        // Enter để đăng nhập
        txtUsername.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    performLogin();
                }
            }
        });
        txtPassword.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    performLogin();
                }
            }
        });
        
        // Tab để chuyển focus
        txtUsername.addActionListener(e -> txtPassword.requestFocus());
        
        // Thêm hiệu ứng hover cho buttons
        addButtonHoverEffects();
    }
    
    private void addButtonHoverEffects() {
        // Hiệu ứng hover cho button Đăng nhập
        btnLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLogin.setBackground(new Color(0, 180, 0)); // Xanh sáng hơn
                btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLogin.setBackground(new Color(0, 153, 0)); // Xanh ban đầu
                btnLogin.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }

            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnLogin.setBackground(new Color(0, 120, 0)); // Xanh đậm khi nhấn
            }

            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btnLogin.setBackground(new Color(0, 180, 0)); // Quay lại màu hover
            }
        });
        
        // Hiệu ứng hover cho button Thoát
        btnExit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnExit.setBackground(new Color(230, 0, 0)); // Đỏ sáng hơn
                btnExit.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnExit.setBackground(new Color(204, 0, 0)); // Đỏ ban đầu
                btnExit.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }

            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnExit.setBackground(new Color(180, 0, 0)); // Đỏ đậm khi nhấn
            }

            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btnExit.setBackground(new Color(230, 0, 0)); // Quay lại màu hover
            }
        });
    }
    
    private void performLogin() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());
        
        if (username.isEmpty()) {
            showStatus("Vui lòng nhập tên đăng nhập!", Color.RED);
            txtUsername.requestFocus();
            return;
        }
        
        if (password.isEmpty()) {
            showStatus("Vui lòng nhập mật khẩu!", Color.RED);
            txtPassword.requestFocus();
            return;
        }
        
        // Hiển thị loading
        btnLogin.setEnabled(false);
        btnLogin.setText("Đang đăng nhập...");
        showStatus("Đang xác thực...", Color.BLUE);
        
        // Simulate loading delay
        Timer timer = new Timer(1000, e -> {
            if (authService.login(username, password)) {
                showStatus("Đăng nhập thành công!", Color.GREEN);
                
                // Lấy thông tin user hiện tại
                User currentUser = authService.getCurrentUser();
                System.out.println("LoginForm - Đăng nhập thành công: " + currentUser.getHoTen() + " (" + currentUser.getRole() + ")");
                
                // Gọi callback nếu có
                if (loginSuccessCallback != null) {
                    System.out.println("LoginForm - Sử dụng callback để mở MainGUIWithAuth");
                    
                    // Đóng LoginForm trước khi mở MainGUIWithAuth
                    SwingUtilities.invokeLater(() -> {
                        try {
                            loginSuccessCallback.accept(authService);
                            dispose(); // Đóng LoginForm sau khi callback thành công
                        } catch (Exception ex) {
                            System.err.println("Lỗi khi thực hiện callback: " + ex.getMessage());
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(LoginForm.this,
                                "Lỗi khi mở giao diện chính: " + ex.getMessage(),
                                "Lỗi", JOptionPane.ERROR_MESSAGE);
                        }
                    });
                } else {
                    System.out.println("LoginForm - Sử dụng logic mặc định để chuyển hướng");
                    // Chuyển hướng theo role
                    SwingUtilities.invokeLater(() -> {
                        try {
                            redirectBasedOnRole(currentUser);
                            dispose();
                        } catch (Exception ex) {
                            System.err.println("Lỗi khi chuyển hướng: " + ex.getMessage());
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(LoginForm.this,
                                "Lỗi khi mở giao diện: " + ex.getMessage(),
                                "Lỗi", JOptionPane.ERROR_MESSAGE);
                        }
                    });
                }
            } else {
                showStatus("Sai tên đăng nhập hoặc mật khẩu!", Color.RED);
                txtPassword.setText("");
                txtUsername.requestFocus();
            }
            
            btnLogin.setEnabled(true);
            btnLogin.setText("Đăng Nhập");
        });
        timer.setRepeats(false);
        timer.start();
    }
    
    private void showStatus(String message, Color color) {
        lblStatus.setText(message);
        lblStatus.setForeground(color);
    }
    
    /**
     * Chuyển hướng người dùng đến giao diện phù hợp với role
     */
    private void redirectBasedOnRole(User user) {
        if (user == null) {
            JOptionPane.showMessageDialog(this, "Lỗi: Không thể xác định thông tin người dùng!", 
                                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Nếu có callback, sử dụng callback thay vì tự mở giao diện
        if (loginSuccessCallback != null) {
            loginSuccessCallback.accept(authService);
            return;
        }
        
        // Logic mặc định nếu không có callback
        String role = user.getRole().toLowerCase();
        String welcomeMessage = "Chào mừng " + user.getHoTen() + " (" + user.getRole() + ")";
        
        switch (role) {
            case "admin":
                // Admin: Có quyền cao nhất, vào MainGUIWithAuth
                JOptionPane.showMessageDialog(null, welcomeMessage + "\nTruy cập với quyền Quản trị viên", 
                                            "Đăng nhập thành công", JOptionPane.INFORMATION_MESSAGE);
                SwingUtilities.invokeLater(() -> {
                    try {
                        MainGUIWithAuth mainGUI = new MainGUIWithAuth(authService);
                        mainGUI.setVisible(true);
                        System.out.println("LoginForm - MainGUIWithAuth đã được mở cho Admin");
                    } catch (Exception e) {
                        System.err.println("Lỗi khi mở MainGUIWithAuth cho Admin: " + e.getMessage());
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Lỗi khi mở giao diện: " + e.getMessage());
                    }
                });
                break;
                
            case "giaovu":
                // Giáo vụ: Quản lý kỳ thi, nhập điểm, thống kê
                JOptionPane.showMessageDialog(null, welcomeMessage + "\nTruy cập với quyền Giáo vụ", 
                                            "Đăng nhập thành công", JOptionPane.INFORMATION_MESSAGE);
                SwingUtilities.invokeLater(() -> {
                    try {
                        MainGUIWithAuth mainGUI = new MainGUIWithAuth(authService);
                        mainGUI.setVisible(true);
                        System.out.println("LoginForm - MainGUIWithAuth đã được mở cho Giáo vụ");
                    } catch (Exception e) {
                        System.err.println("Lỗi khi mở MainGUIWithAuth cho Giáo vụ: " + e.getMessage());
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Lỗi khi mở giao diện: " + e.getMessage());
                    }
                });
                break;
                
            case "user":
                // User thường: Chỉ xem kết quả, đăng ký thi
                JOptionPane.showMessageDialog(null, welcomeMessage + "\nTruy cập với quyền hạn chế", 
                                            "Đăng nhập thành công", JOptionPane.INFORMATION_MESSAGE);
                openUserLimitedGUI(user);
                break;
                
            case "thisinh":
                // Thí sinh: Chỉ xem kết quả của mình
                JOptionPane.showMessageDialog(null, welcomeMessage + "\nTruy cập dành cho Thí sinh", 
                                            "Đăng nhập thành công", JOptionPane.INFORMATION_MESSAGE);
                openThiSinhGUI(user);
                break;
                
            default:
                // Role không xác định: Vào demo mode hoặc basic GUI
                JOptionPane.showMessageDialog(null, welcomeMessage + "\nTruy cập với quyền cơ bản", 
                                            "Đăng nhập thành công", JOptionPane.INFORMATION_MESSAGE);
                openDemoMode();
                break;
        }
    }
    
    /**
     * Mở giao diện hạn chế cho user thường
     */
    private void openUserLimitedGUI(User user) {
        // Có thể tạo UserLimitedGUI hoặc dùng MainGUI với một số tính năng bị ẩn
        try {
            // Tạm thời dùng MainGUI, sau này có thể tạo GUI riêng
            new MainGUI();  // Không có quyền admin
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khởi tạo giao diện: " + e.getMessage(), 
                                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            openDemoMode();
        }
    }
    
    /**
     * Mở giao diện dành cho thí sinh
     */
    private void openThiSinhGUI(User user) {
        try {
            // Có thể tạo ThiSinhGUI riêng, hiện tại dùng MainGUI
            new MainGUI();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khởi tạo giao diện: " + e.getMessage(), 
                                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            openDemoMode();
        }
    }
    
    /**
     * Mở Demo mode khi có lỗi hoặc role không xác định
     */
    private void openDemoMode() {
        try {
            // Mở MainGUI với quyền demo
            SwingUtilities.invokeLater(() -> {
                try {
                    // Tạo MainGUI với quyền basic
                    new MainGUI();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Không thể mở chế độ demo: " + ex.getMessage());
                }
            });
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi nghiêm trọng: Không thể khởi tạo bất kỳ giao diện nào!", 
                                        "Lỗi hệ thống", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    /**
     * Entry point riêng cho LoginForm - Điểm khởi chạy có authentication
     * 
     * Workflow sau khi đăng nhập thành công:
     * - Admin: → MainGUIWithAuth (quyền đầy đủ)
     * - Giáo vụ: → MainGUIWithAuth (quản lý kỳ thi, nhập điểm)
     * - User: → MainGUI (quyền hạn chế)
     * - Thí sinh: → MainGUI (chỉ xem kết quả)
     * - Khác: → Demo mode (fallback)
     * 
     * Entry point chính không có auth: Demo.main()
     */
    public static void main(String[] args) {
        // Set Look and Feel để giao diện đẹp hơn
        try {
            // Sử dụng Look and Feel của hệ điều hành (Windows/Mac/Linux)
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | 
                 IllegalAccessException | UnsupportedLookAndFeelException e) {
            // Nếu lỗi thì dùng Look and Feel mặc định (Metal)
            System.err.println("Không thể thiết lập System Look and Feel: " + e.getMessage());
            try {
                UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        
        // Khởi chạy ứng dụng trên Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            new LoginForm().setVisible(true);
        });
    }
    
    /**
     * Xử lý khi người dùng thay đổi kích thước cửa sổ bằng cách kéo thả chuột
     */
    private void handleWindowResize() {
        SwingUtilities.invokeLater(() -> {
            try {
                Dimension currentSize = getSize();
                int newWidth = currentSize.width;
                int newHeight = currentSize.height;
                
                // Debug thông tin
                System.out.println("LoginForm - Kích thước mới: " + newWidth + "x" + newHeight);
                
                // Tính toán lại kích thước các panel theo tỷ lệ
                updatePanelSizes(newWidth, newHeight);
                
                // Refresh giao diện
                revalidate();
                repaint();
                
            } catch (Exception e) {
                System.err.println("Lỗi khi thay đổi kích thước: " + e.getMessage());
            }
        });
    }
    
    /**
     * Cập nhật kích thước các panel khi cửa sổ thay đổi kích thước
     */
    private void updatePanelSizes(int newWidth, int newHeight) {
        // Tìm các panel trong giao diện
        Container contentPane = getContentPane();
        if (contentPane instanceof JPanel) {
            JPanel mainPanel = (JPanel) contentPane;
            Component[] components = mainPanel.getComponents();
            
            for (Component comp : components) {
                if (comp instanceof JPanel) {
                    JPanel panel = (JPanel) comp;
                    
                    // Xác định panel brand (bên trái) và login (bên phải)
                    if (panel.getBackground() == null) { // Brand panel có background gradient (null)
                        // Brand panel - 65% chiều rộng
                        int brandWidth = (int)(newWidth * 0.65);
                        panel.setPreferredSize(new Dimension(brandWidth, newHeight));
                        
                    } else if (panel.getBackground() == Color.WHITE) { // Login panel có background trắng
                        // Login panel - 35% chiều rộng
                        int loginWidth = (int)(newWidth * 0.35);
                        panel.setPreferredSize(new Dimension(loginWidth, newHeight));
                        
                        // Cập nhật font size và kích thước field cho login panel
                        updateLoginPanelComponents(panel, newWidth, newHeight);
                    }
                }
            }
        }
    }
    
    /**
     * Cập nhật font size và kích thước các component trong login panel
     */
    private void updateLoginPanelComponents(JPanel loginPanel, int windowWidth, int windowHeight) {
        // Tính toán font size mới dựa trên kích thước cửa sổ
        int titleFontSize = Math.max(16, windowWidth / 80);
        int labelFontSize = Math.max(14, windowWidth / 90);
        int fieldFontSize = Math.max(14, windowWidth / 90);
        int buttonFontSize = Math.max(12, windowWidth / 100);
        
        // Tính toán kích thước field mới
        int fieldWidth = Math.min(350, windowWidth / 4);
        int fieldHeight = Math.max(35, windowHeight / 20);
        int buttonWidth = Math.max(100, fieldWidth / 3);
        int buttonHeight = Math.max(35, fieldHeight);
        
        // Cập nhật các component
        updateComponentsRecursively(loginPanel, titleFontSize, labelFontSize, fieldFontSize, 
                                   buttonFontSize, fieldWidth, fieldHeight, buttonWidth, buttonHeight);
    }
    
    /**
     * Cập nhật font và kích thước các component một cách đệ quy
     */
    private void updateComponentsRecursively(Container container, int titleFontSize, int labelFontSize, 
                                           int fieldFontSize, int buttonFontSize, int fieldWidth, 
                                           int fieldHeight, int buttonWidth, int buttonHeight) {
        for (Component comp : container.getComponents()) {
            if (comp instanceof JLabel) {
                JLabel label = (JLabel) comp;
                String text = label.getText();
                
                if ("ĐĂNG NHẬP".equals(text) || "🔐".equals(text)) {
                    // Title và icon
                    int fontSize = "🔐".equals(text) ? titleFontSize + 16 : titleFontSize;
                    label.setFont(new Font("Arial", label.getFont().getStyle(), fontSize));
                } else if (text.contains("đăng nhập") || text.contains("khẩu")) {
                    // Label cho field
                    label.setFont(new Font("Arial", Font.BOLD, labelFontSize));
                } else {
                    // Status label và các label khác
                    label.setFont(new Font("Arial", label.getFont().getStyle(), labelFontSize - 2));
                    if (label == lblStatus) {
                        label.setPreferredSize(new Dimension(fieldWidth, 25));
                    }
                }
                
            } else if (comp instanceof JTextField || comp instanceof JPasswordField) {
                // Text field và password field
                comp.setFont(new Font("Arial", Font.PLAIN, fieldFontSize));
                comp.setPreferredSize(new Dimension(fieldWidth, fieldHeight));
                
            } else if (comp instanceof JButton) {
                // Buttons
                JButton button = (JButton) comp;
                button.setFont(new Font("Arial", Font.BOLD, buttonFontSize));
                button.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
                
            } else if (comp instanceof Container) {
                // Đệ quy cho các container con
                updateComponentsRecursively((Container) comp, titleFontSize, labelFontSize, 
                                          fieldFontSize, buttonFontSize, fieldWidth, fieldHeight, 
                                          buttonWidth, buttonHeight);
            }
        }
    }
}
