package QuanLyKyThi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;

/**
 * Form hiển thị thông tin tài khoản người dùng
 */
public class AccountInfoForm extends JFrame {
    private XMLDatabase database;
    private User currentUser;
    
    // Components
    private JLabel lblUsername, lblHoTen, lblEmail, lblRole, lblLastLogin, lblCreatedDate, lblStatus;
    private JTextField txtUsername, txtHoTen, txtEmail;
    private JLabel txtRole, txtLastLogin, txtCreatedDate, txtStatus;
    private JButton btnEdit, btnChangePassword, btnClose;
    
    public AccountInfoForm(User user) {
        this.currentUser = user;
        this.database = new XMLDatabase();
        
        initComponents();
        setupLayout();
        loadUserInfo();
        setupEventHandlers();
        
        setTitle("Thông tin tài khoản - " + user.getUsername());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setResizable(false);
    }
    
    private void initComponents() {
        // Labels
        lblUsername = new JLabel("Tên đăng nhập:");
        lblHoTen = new JLabel("Họ và tên:");
        lblEmail = new JLabel("Email:");
        lblRole = new JLabel("Vai trò:");
        lblLastLogin = new JLabel("Lần đăng nhập cuối:");
        lblCreatedDate = new JLabel("Ngày tạo tài khoản:");
        lblStatus = new JLabel("Trạng thái:");
        
        // Text fields và labels
        txtUsername = new JTextField();
        txtUsername.setEditable(false);
        txtUsername.setBackground(Color.LIGHT_GRAY);
        
        txtHoTen = new JTextField();
        txtEmail = new JTextField();
        
        txtRole = new JLabel();
        txtLastLogin = new JLabel();
        txtCreatedDate = new JLabel();
        txtStatus = new JLabel();
        
        // Buttons
        btnEdit = new JButton("Cập nhật thông tin");
        btnEdit.setBackground(new Color(0, 123, 255));
        btnEdit.setForeground(Color.WHITE);
        btnEdit.setFocusPainted(false);
        
        btnChangePassword = new JButton("Đổi mật khẩu");
        btnChangePassword.setBackground(new Color(255, 193, 7));
        btnChangePassword.setForeground(Color.BLACK);
        btnChangePassword.setFocusPainted(false);
        
        btnClose = new JButton("Đóng");
        btnClose.setBackground(new Color(108, 117, 125));
        btnClose.setForeground(Color.WHITE);
        btnClose.setFocusPainted(false);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Main panel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        
        // Title
        JLabel titleLabel = new JLabel("THÔNG TIN TÀI KHOẢN", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(new Color(0, 123, 255));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 20, 0);
        mainPanel.add(titleLabel, gbc);
        
        // Reset gridwidth
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 0, 5, 10);
        
        // Username
        gbc.gridx = 0; gbc.gridy = 1;
        mainPanel.add(lblUsername, gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(txtUsername, gbc);
        
        // Họ tên
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(lblHoTen, gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(txtHoTen, gbc);
        
        // Email
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(lblEmail, gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(txtEmail, gbc);
        
        // Role
        gbc.gridx = 0; gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(lblRole, gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(txtRole, gbc);
        
        // Last login
        gbc.gridx = 0; gbc.gridy = 5; gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(lblLastLogin, gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(txtLastLogin, gbc);
        
        // Created date
        gbc.gridx = 0; gbc.gridy = 6; gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(lblCreatedDate, gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(txtCreatedDate, gbc);
        
        // Status
        gbc.gridx = 0; gbc.gridy = 7; gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(lblStatus, gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(txtStatus, gbc);
        
        add(mainPanel, BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnChangePassword);
        buttonPanel.add(btnClose);
        
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void loadUserInfo() {
        txtUsername.setText(currentUser.getUsername());
        txtHoTen.setText(currentUser.getHoTen() != null ? currentUser.getHoTen() : "");
        txtEmail.setText(currentUser.getEmail() != null ? currentUser.getEmail() : "");
        
        // Role với màu sắc
        String roleText = getRoleDisplayText(currentUser.getRole());
        txtRole.setText(roleText);
        txtRole.setForeground(getRoleColor(currentUser.getRole()));
        
        // Last login
        if (currentUser.getLastLogin() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            txtLastLogin.setText(currentUser.getLastLogin().format(formatter));
        } else {
            txtLastLogin.setText("Chưa đăng nhập");
            txtLastLogin.setForeground(Color.GRAY);
        }
        
        // Created date
        if (currentUser.getCreatedDate() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            txtCreatedDate.setText(currentUser.getCreatedDate().format(formatter));
        } else {
            txtCreatedDate.setText("Không xác định");
        }
        
        // Status
        String statusText = getStatusText();
        txtStatus.setText(statusText);
        txtStatus.setForeground(getStatusColor());
    }
    
    private String getRoleDisplayText(String role) {
        switch (role.toLowerCase()) {
            case "admin": return "Quản trị viên";
            case "giaovu": return "Giáo vụ";
            case "giamthi": return "Giám thị";
            case "user": return "Người dùng";
            default: return role;
        }
    }
    
    private Color getRoleColor(String role) {
        switch (role.toLowerCase()) {
            case "admin": return new Color(220, 53, 69); // Red
            case "giaovu": return new Color(0, 123, 255); // Blue
            case "giamthi": return new Color(255, 193, 7); // Yellow
            case "user": return new Color(40, 167, 69); // Green
            default: return Color.BLACK;
        }
    }
    
    private String getStatusText() {
        if (currentUser.isPending()) {
            return "Chờ duyệt";
        } else if (currentUser.isActive()) {
            return "Hoạt động";
        } else {
            return "Bị khóa";
        }
    }
    
    private Color getStatusColor() {
        if (currentUser.isPending()) {
            return new Color(255, 193, 7); // Yellow
        } else if (currentUser.isActive()) {
            return new Color(40, 167, 69); // Green
        } else {
            return new Color(220, 53, 69); // Red
        }
    }
    
    private void setupEventHandlers() {
        btnEdit.addActionListener(e -> updateUserInfo());
        
        btnChangePassword.addActionListener(e -> openChangePasswordForm());
        
        btnClose.addActionListener(e -> dispose());
    }
    
    private void updateUserInfo() {
        try {
            String newHoTen = txtHoTen.getText().trim();
            String newEmail = txtEmail.getText().trim();
            
            // Validation
            if (newHoTen.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Họ tên không được để trống!", 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (newEmail.isEmpty() || !isValidEmail(newEmail)) {
                JOptionPane.showMessageDialog(this, "Email không hợp lệ!", 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Update user info
            currentUser.setHoTen(newHoTen);
            currentUser.setEmail(newEmail);
            
            // Save to database
            database.updateUser(currentUser);
            
            JOptionPane.showMessageDialog(this, "Cập nhật thông tin thành công!", 
                "Thành công", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật thông tin: " + ex.getMessage(), 
                "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void openChangePasswordForm() {
        ChangePasswordForm changePasswordForm = new ChangePasswordForm(currentUser);
        changePasswordForm.setVisible(true);
    }
    
    private boolean isValidEmail(String email) {
        return email.contains("@") && email.contains(".");
    }
    
    // Main method for testing
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Test with sample user
            User testUser = new User("testuser", "password", "Nguyễn Văn Test", 
                "test@example.com", "user");
            
            AccountInfoForm form = new AccountInfoForm(testUser);
            form.setVisible(true);
        });
    }
}
