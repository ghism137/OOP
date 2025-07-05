package QuanLyKyThi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Form đăng ký tài khoản mới
 */
public class RegisterForm extends JFrame {
    private XMLDatabase database;
    private UserManagementForm parentForm;
    
    // Components
    private JTextField txtUsername, txtHoTen, txtEmail;
    private JPasswordField txtPassword, txtConfirmPassword;
    private JComboBox<String> cbRole;
    private JButton btnRegister, btnCancel;
    private JCheckBox chkTerms;
    
    public RegisterForm(UserManagementForm parent) {
        this.parentForm = parent;
        this.database = new XMLDatabase();
        
        initComponents();
        setupLayout();
        setupEventHandlers();
        
        setTitle("Đăng ký tài khoản mới");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(450, 500);
        setLocationRelativeTo(parent);
        setResizable(false);
    }
    
    private void initComponents() {
        // Text fields
        txtUsername = new JTextField();
        txtHoTen = new JTextField();
        txtEmail = new JTextField();
        txtPassword = new JPasswordField();
        txtConfirmPassword = new JPasswordField();
        
        // Role combo box
        cbRole = new JComboBox<>(new String[]{"user", "giaovu", "giamthi", "admin"});
        cbRole.setSelectedIndex(0); // Default to "user"
        
        // Checkbox
        chkTerms = new JCheckBox("Tôi đồng ý với điều khoản sử dụng");
        
        // Buttons
        btnRegister = new JButton("Đăng ký");
        btnRegister.setBackground(new Color(40, 167, 69));
        btnRegister.setForeground(Color.WHITE);
        btnRegister.setFocusPainted(false);
        
        btnCancel = new JButton("Hủy");
        btnCancel.setBackground(new Color(108, 117, 125));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setFocusPainted(false);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Main panel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        
        // Title
        JLabel titleLabel = new JLabel("ĐĂNG KÝ TÀI KHOẢN MỚI", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(new Color(0, 123, 255));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 20, 0);
        mainPanel.add(titleLabel, gbc);
        
        // Reset gridwidth
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(8, 0, 8, 10);
        
        // Username
        gbc.gridx = 0; gbc.gridy = 1;
        mainPanel.add(new JLabel("Tên đăng nhập: *"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        mainPanel.add(txtUsername, gbc);
        
        // Họ tên
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        mainPanel.add(new JLabel("Họ và tên: *"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        mainPanel.add(txtHoTen, gbc);
        
        // Email
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        mainPanel.add(new JLabel("Email: *"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        mainPanel.add(txtEmail, gbc);
        
        // Role
        gbc.gridx = 0; gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        mainPanel.add(new JLabel("Vai trò: *"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        mainPanel.add(cbRole, gbc);
        
        // Password
        gbc.gridx = 0; gbc.gridy = 5; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        mainPanel.add(new JLabel("Mật khẩu: *"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        mainPanel.add(txtPassword, gbc);
        
        // Confirm Password
        gbc.gridx = 0; gbc.gridy = 6; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        mainPanel.add(new JLabel("Xác nhận mật khẩu: *"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        mainPanel.add(txtConfirmPassword, gbc);
        
        // Admin role warning
        JTextArea adminWarning = new JTextArea();
        adminWarning.setText("⚠️ Lưu ý: Tài khoản với vai trò 'admin' sẽ cần được duyệt bởi quản trị viên.\n" +
                            "Các vai trò khác sẽ được kích hoạt ngay lập tức.");
        adminWarning.setEditable(false);
        adminWarning.setBackground(getBackground());
        adminWarning.setFont(new Font("Arial", Font.ITALIC, 11));
        adminWarning.setForeground(new Color(255, 138, 0)); // Orange
        adminWarning.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        adminWarning.setLineWrap(true);
        adminWarning.setWrapStyleWord(true);
        
        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(adminWarning, gbc);
        
        // Terms checkbox
        gbc.gridx = 0; gbc.gridy = 8; gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 0, 10, 0);
        mainPanel.add(chkTerms, gbc);
        
        // Required fields note
        JLabel requiredNote = new JLabel("* Trường bắt buộc");
        requiredNote.setFont(new Font("Arial", Font.ITALIC, 10));
        requiredNote.setForeground(Color.RED);
        gbc.gridx = 0; gbc.gridy = 9; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(requiredNote, gbc);
        
        add(mainPanel, BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        buttonPanel.add(btnRegister);
        buttonPanel.add(btnCancel);
        
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void setupEventHandlers() {
        btnRegister.addActionListener(e -> registerUser());
        
        btnCancel.addActionListener(e -> dispose());
        
        // Enter key để submit
        getRootPane().setDefaultButton(btnRegister);
    }
    
    private void registerUser() {
        try {
            String username = txtUsername.getText().trim();
            String hoTen = txtHoTen.getText().trim();
            String email = txtEmail.getText().trim();
            String role = (String) cbRole.getSelectedItem();
            String password = new String(txtPassword.getPassword());
            String confirmPassword = new String(txtConfirmPassword.getPassword());
            
            // Validation
            if (username.isEmpty() || hoTen.isEmpty() || email.isEmpty() || 
                password.isEmpty() || confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin bắt buộc!", 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Username validation
            if (username.length() < 3) {
                JOptionPane.showMessageDialog(this, "Tên đăng nhập phải có ít nhất 3 ký tự!", 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
                txtUsername.requestFocus();
                return;
            }
            
            // Check if username already exists
            if (database.findUserByUsername(username) != null) {
                JOptionPane.showMessageDialog(this, "Tên đăng nhập đã tồn tại!", 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
                txtUsername.requestFocus();
                txtUsername.selectAll();
                return;
            }
            
            // Email validation
            if (!isValidEmail(email)) {
                JOptionPane.showMessageDialog(this, "Email không hợp lệ!", 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
                txtEmail.requestFocus();
                return;
            }
            
            // Password validation
            if (password.length() < 6) {
                JOptionPane.showMessageDialog(this, "Mật khẩu phải có ít nhất 6 ký tự!", 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
                txtPassword.requestFocus();
                return;
            }
            
            // Password confirmation
            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this, "Mật khẩu xác nhận không khớp!", 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
                txtConfirmPassword.requestFocus();
                txtConfirmPassword.selectAll();
                return;
            }
            
            // Terms agreement
            if (!chkTerms.isSelected()) {
                JOptionPane.showMessageDialog(this, "Vui lòng đồng ý với điều khoản sử dụng!", 
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Create new user
            User newUser = new User(username, hashPassword(password), hoTen, email, role);
            
            // Add user to database
            java.util.List<User> users = database.loadUsers();
            users.add(newUser);
            database.saveUsers(users);
            
            // Success message
            String message;
            if ("admin".equals(role)) {
                message = "Đăng ký thành công!\nTài khoản admin sẽ cần được duyệt bởi quản trị viên.";
            } else {
                message = "Đăng ký thành công!\nTài khoản đã được kích hoạt và có thể sử dụng ngay.";
            }
            
            JOptionPane.showMessageDialog(this, message, "Thành công", JOptionPane.INFORMATION_MESSAGE);
            
            // Refresh parent form if available
            if (parentForm != null) {
                parentForm.refreshData();
            }
            
            dispose();
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi đăng ký tài khoản: " + ex.getMessage(), 
                "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private boolean isValidEmail(String email) {
        return email.contains("@") && email.contains(".") && email.length() > 5;
    }
    
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            // Fallback: return plain text if hashing fails
            return password;
        }
    }
    
    // Constructor for standalone use
    public RegisterForm() {
        this(null);
    }
    
    // Main method for testing
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RegisterForm form = new RegisterForm();
            form.setVisible(true);
        });
    }
}
