package QuanLyKyThi;

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
    
    public LoginForm() {
        this.authService = new AuthenticationService();
        initComponents();
    }
    
    private void initComponents() {
        setTitle("Đăng Nhập - Hệ Thống Quản Lý Kỳ Thi");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 350);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Panel chính với gradient background
        JPanel mainPanel = new JPanel() {
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
        mainPanel.setLayout(new GridBagLayout());
        
        // Panel form đăng nhập
        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBackground(Color.WHITE);
        loginPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createRaisedBevelBorder(),
            BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Logo/Title
        JLabel titleLabel = new JLabel("ĐĂNG NHẬP HỆ THỐNG");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(0, 102, 204));
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        loginPanel.add(titleLabel, gbc);
        
        // Icon
        try {
            JLabel iconLabel = new JLabel("🔐");
            iconLabel.setFont(new Font("Arial", Font.PLAIN, 48));
            gbc.gridy = 1;
            loginPanel.add(iconLabel, gbc);
        } catch (Exception e) {
            // Nếu không có icon thì bỏ qua
        }
        
        // Reset gridwidth
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        
        // Username
        gbc.gridx = 0; gbc.gridy = 2;
        JLabel lblUsername = new JLabel("Tên đăng nhập:");
        lblUsername.setFont(new Font("Arial", Font.BOLD, 12));
        loginPanel.add(lblUsername, gbc);
        
        gbc.gridx = 1;
        txtUsername = new JTextField(15);
        txtUsername.setFont(new Font("Arial", Font.PLAIN, 12));
        txtUsername.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLoweredBevelBorder(),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        loginPanel.add(txtUsername, gbc);
        
        // Password
        gbc.gridx = 0; gbc.gridy = 3;
        JLabel lblPassword = new JLabel("Mật khẩu:");
        lblPassword.setFont(new Font("Arial", Font.BOLD, 12));
        loginPanel.add(lblPassword, gbc);
        
        gbc.gridx = 1;
        txtPassword = new JPasswordField(15);
        txtPassword.setFont(new Font("Arial", Font.PLAIN, 12));
        txtPassword.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLoweredBevelBorder(),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        loginPanel.add(txtPassword, gbc);
        
        // Status label
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        lblStatus = new JLabel(" ");
        lblStatus.setFont(new Font("Arial", Font.ITALIC, 11));
        lblStatus.setForeground(Color.RED);
        loginPanel.add(lblStatus, gbc);
        
        // Buttons
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setOpaque(false);
        
        btnLogin = new JButton("Đăng Nhập");
        btnLogin.setBackground(new Color(0, 153, 0));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFont(new Font("Arial", Font.BOLD, 12));
        btnLogin.setFocusPainted(false);
        btnLogin.setPreferredSize(new Dimension(100, 35));
        
        btnExit = new JButton("Thoát");
        btnExit.setBackground(new Color(204, 0, 0));
        btnExit.setForeground(Color.WHITE);
        btnExit.setFont(new Font("Arial", Font.BOLD, 12));
        btnExit.setFocusPainted(false);
        btnExit.setPreferredSize(new Dimension(100, 35));
        
        buttonPanel.add(btnLogin);
        buttonPanel.add(btnExit);
        loginPanel.add(buttonPanel, gbc);
        
        // Thông tin tài khoản mẫu
        gbc.gridy = 6;
        gbc.fill = GridBagConstraints.NONE;
        JLabel infoLabel = new JLabel("<html><div style='text-align: center;'>" +
            "<small>Tài khoản mẫu:<br/>" +
            "<b>Admin:</b> admin/admin123<br/>" +
            "<b>Giáo vụ:</b> giaovu/gv123<br/>" +
            "<b>User:</b> user1/user123<br/>" +
            "<b>Thí sinh:</b> thisinh1/ts123</small></div></html>");
        infoLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        infoLabel.setForeground(Color.GRAY);
        loginPanel.add(infoLabel, gbc);
        
        // Thêm vào main panel
        mainPanel.add(loginPanel);
        add(mainPanel);
        
        // Event handlers
        setupEventHandlers();
        
        // Focus vào username
        SwingUtilities.invokeLater(() -> txtUsername.requestFocus());
    }
    
    private void setupEventHandlers() {
        btnLogin.addActionListener(e -> performLogin());
        btnExit.addActionListener(e -> System.exit(0));
        
        // Enter để đăng nhập
        KeyListener enterKeyListener = new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    performLogin();
                }
            }
            
            @Override
            public void keyTyped(KeyEvent e) {}
            
            @Override
            public void keyReleased(KeyEvent e) {}
        };
        
        txtUsername.addKeyListener(enterKeyListener);
        txtPassword.addKeyListener(enterKeyListener);
        
        // Tab để chuyển focus
        txtUsername.addActionListener(e -> txtPassword.requestFocus());
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
                
                // Chuyển hướng theo role
                SwingUtilities.invokeLater(() -> {
                    redirectBasedOnRole(currentUser);
                    dispose();
                });
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
        
        String role = user.getRole().toLowerCase();
        String welcomeMessage = "Chào mừng " + user.getHoTen() + " (" + user.getRole() + ")";
        
        switch (role) {
            case "admin":
                // Admin: Có quyền cao nhất, vào MainGUIWithAuth
                JOptionPane.showMessageDialog(null, welcomeMessage + "\nTruy cập với quyền Quản trị viên", 
                                            "Đăng nhập thành công", JOptionPane.INFORMATION_MESSAGE);
                new MainGUIWithAuth(authService);
                break;
                
            case "giaovu":
                // Giáo vụ: Quản lý kỳ thi, nhập điểm, thống kê
                JOptionPane.showMessageDialog(null, welcomeMessage + "\nTruy cập với quyền Giáo vụ", 
                                            "Đăng nhập thành công", JOptionPane.INFORMATION_MESSAGE);
                new MainGUIWithAuth(authService);
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
}
