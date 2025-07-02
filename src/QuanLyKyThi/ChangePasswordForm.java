package QuanLyKyThi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Form đổi mật khẩu cho người dùng
 */
public class ChangePasswordForm extends JFrame {
    private XMLDatabase database;
    private User currentUser;
    
    // Components
    private JPasswordField txtCurrentPassword, txtNewPassword, txtConfirmPassword;
    private JButton btnSave, btnCancel;
    private JLabel lblCurrentPassword, lblNewPassword, lblConfirmPassword;
    
    public ChangePasswordForm(User user) {
        this.currentUser = user;
        this.database = new XMLDatabase();
        
        initComponents();
        setupLayout();
        setupEventHandlers();
        
        setTitle("Đổi mật khẩu - " + user.getUsername());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(false);
    }
    
    private void initComponents() {
        // Labels
        lblCurrentPassword = new JLabel("Mật khẩu hiện tại:");
        lblNewPassword = new JLabel("Mật khẩu mới:");
        lblConfirmPassword = new JLabel("Xác nhận mật khẩu mới:");
        
        // Password fields
        txtCurrentPassword = new JPasswordField();
        txtNewPassword = new JPasswordField();
        txtConfirmPassword = new JPasswordField();
        
        // Buttons
        btnSave = new JButton("Lưu thay đổi");
        btnSave.setBackground(new Color(40, 167, 69));
        btnSave.setForeground(Color.WHITE);
        btnSave.setFocusPainted(false);
        
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
        JLabel titleLabel = new JLabel("ĐỔI MẬT KHẨU", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(new Color(0, 123, 255));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 20, 0);
        mainPanel.add(titleLabel, gbc);
        
        // Reset gridwidth
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 0, 10, 10);
        
        // Current password
        gbc.gridx = 0; gbc.gridy = 1;
        mainPanel.add(lblCurrentPassword, gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        mainPanel.add(txtCurrentPassword, gbc);
        
        // New password
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        mainPanel.add(lblNewPassword, gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        mainPanel.add(txtNewPassword, gbc);
        
        // Confirm password
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        mainPanel.add(lblConfirmPassword, gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        mainPanel.add(txtConfirmPassword, gbc);
        
        // Password requirements
        JTextArea requirements = new JTextArea();
        requirements.setText("Yêu cầu mật khẩu:\n" +
                           "• Tối thiểu 6 ký tự\n" +
                           "• Nên có ít nhất 1 chữ số\n" +
                           "• Nên có ít nhất 1 ký tự đặc biệt");
        requirements.setEditable(false);
        requirements.setBackground(getBackground());
        requirements.setFont(new Font("Arial", Font.ITALIC, 11));
        requirements.setForeground(Color.GRAY);
        requirements.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(20, 0, 0, 0);
        mainPanel.add(requirements, gbc);
        
        add(mainPanel, BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);
        
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void setupEventHandlers() {
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changePassword();
            }
        });
        
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
        // Enter key để submit
        getRootPane().setDefaultButton(btnSave);
    }
    
    private void changePassword() {
        try {
            String currentPassword = new String(txtCurrentPassword.getPassword());
            String newPassword = new String(txtNewPassword.getPassword());
            String confirmPassword = new String(txtConfirmPassword.getPassword());
            
            // Validation
            if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!", 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Check current password
            if (!verifyCurrentPassword(currentPassword)) {
                JOptionPane.showMessageDialog(this, "Mật khẩu hiện tại không đúng!", 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
                txtCurrentPassword.requestFocus();
                txtCurrentPassword.selectAll();
                return;
            }
            
            // Check password strength
            if (!isValidPassword(newPassword)) {
                JOptionPane.showMessageDialog(this, 
                    "Mật khẩu mới không đủ mạnh!\nVui lòng sử dụng ít nhất 6 ký tự.", 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
                txtNewPassword.requestFocus();
                txtNewPassword.selectAll();
                return;
            }
            
            // Check password confirmation
            if (!newPassword.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this, "Mật khẩu xác nhận không khớp!", 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
                txtConfirmPassword.requestFocus();
                txtConfirmPassword.selectAll();
                return;
            }
            
            // Check if new password is different from current
            if (newPassword.equals(currentPassword)) {
                JOptionPane.showMessageDialog(this, "Mật khẩu mới phải khác mật khẩu hiện tại!", 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
                txtNewPassword.requestFocus();
                txtNewPassword.selectAll();
                return;
            }
            
            // Update password
            currentUser.setPassword(hashPassword(newPassword));
            database.updateUser(currentUser);
            
            JOptionPane.showMessageDialog(this, 
                "Đổi mật khẩu thành công!\nVui lòng đăng nhập lại với mật khẩu mới.", 
                "Thành công", JOptionPane.INFORMATION_MESSAGE);
            
            dispose();
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi đổi mật khẩu: " + ex.getMessage(), 
                "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private boolean verifyCurrentPassword(String inputPassword) {
        try {
            String hashedInput = hashPassword(inputPassword);
            return hashedInput.equals(currentUser.getPassword());
        } catch (Exception e) {
            // Fallback: compare plain text (for compatibility)
            return inputPassword.equals(currentUser.getPassword());
        }
    }
    
    private boolean isValidPassword(String password) {
        if (password.length() < 6) {
            return false;
        }
        
        // Check for at least one digit (recommended)
        // boolean hasDigit = password.matches(".*\\d.*");
        
        // Check for at least one special character (recommended)
        // boolean hasSpecial = password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*");
        
        // Return true if password meets minimum length requirement
        // Digit and special character are recommended but not required
        return true;
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
    
    // Main method for testing
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Test with sample user
            User testUser = new User("testuser", "password", "Nguyễn Văn Test", 
                "test@example.com", "user");
            
            ChangePasswordForm form = new ChangePasswordForm(testUser);
            form.setVisible(true);
        });
    }
}
