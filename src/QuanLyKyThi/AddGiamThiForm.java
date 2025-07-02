package QuanLyKyThi;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Form thêm giám thị mới
 */
public class AddGiamThiForm extends JInternalFrame {
    private JTextField txtMaGiamThi;
    private JTextField txtHoTen;
    private JTextField txtDonVi;
    private JTextField txtSDT;
    private JTextField txtEmail;
    private JTextField txtUsername;
    private XMLDatabase database;
    
    public AddGiamThiForm(List<GiamThi> list) {
        super("Thêm Giám Thị Mới", true, true, true, true);
        this.database = new XMLDatabase();
        
        initComponents();
        setSize(500, 350);
        setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        
        // Panel tiêu đề
        JPanel titlePanel = new JPanel(new FlowLayout());
        JLabel titleLabel = new JLabel("THÊM GIÁM THỊ MỚI");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);
        
        // Panel chính
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Mã giám thị
        gbc.gridx = 0; gbc.gridy = 0;
        mainPanel.add(new JLabel("Mã giám thị:"), gbc);
        gbc.gridx = 1;
        txtMaGiamThi = new JTextField(20);
        mainPanel.add(txtMaGiamThi, gbc);
        
        // Họ tên
        gbc.gridx = 0; gbc.gridy = 1;
        mainPanel.add(new JLabel("Họ tên:"), gbc);
        gbc.gridx = 1;
        txtHoTen = new JTextField(20);
        mainPanel.add(txtHoTen, gbc);
        
        // Đơn vị
        gbc.gridx = 0; gbc.gridy = 2;
        mainPanel.add(new JLabel("Đơn vị:"), gbc);
        gbc.gridx = 1;
        txtDonVi = new JTextField(20);
        mainPanel.add(txtDonVi, gbc);
        
        // Số điện thoại
        gbc.gridx = 0; gbc.gridy = 3;
        mainPanel.add(new JLabel("Số điện thoại:"), gbc);
        gbc.gridx = 1;
        txtSDT = new JTextField(20);
        mainPanel.add(txtSDT, gbc);
        
        // Email
        gbc.gridx = 0; gbc.gridy = 4;
        mainPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        txtEmail = new JTextField(20);
        mainPanel.add(txtEmail, gbc);
        
        // Username
        gbc.gridx = 0; gbc.gridy = 5;
        mainPanel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        txtUsername = new JTextField(20);
        mainPanel.add(txtUsername, gbc);
        
        add(mainPanel, BorderLayout.CENTER);
        
        // Panel buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        JButton saveBtn = new JButton("Lưu");
        saveBtn.addActionListener(e -> saveGiamThi());
        
        JButton clearBtn = new JButton("Xóa rỗng");
        clearBtn.addActionListener(e -> clearFields());
        
        JButton cancelBtn = new JButton("Hủy");
        cancelBtn.addActionListener(e -> setVisible(false));
        
        buttonPanel.add(saveBtn);
        buttonPanel.add(clearBtn);
        buttonPanel.add(cancelBtn);
        
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void saveGiamThi() {
        try {
            if (!validateInput()) {
                return;
            }
            
            // Create new GiamThi
            GiamThi newGiamThi = new GiamThi(
                txtMaGiamThi.getText().trim(),
                txtHoTen.getText().trim(),
                txtDonVi.getText().trim(),
                txtSDT.getText().trim(),
                txtEmail.getText().trim(),
                txtUsername.getText().trim()
            );
            
            // Check if exists
            List<GiamThi> existingList = database.getAllGiamThi();
            boolean exists = existingList.stream()
                .anyMatch(gt -> gt.getMaGiamThi().equals(newGiamThi.getMaGiamThi()));
            
            if (exists) {
                JOptionPane.showMessageDialog(this, 
                    "Mã giám thị đã tồn tại!", 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Add to database
            existingList.add(newGiamThi);
            database.saveGiamThi(existingList);
            
            JOptionPane.showMessageDialog(this, 
                "Đã thêm giám thị thành công!", 
                "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            
            clearFields();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Lỗi khi thêm giám thị: " + e.getMessage(), 
                "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private boolean validateInput() {
        if (txtMaGiamThi.getText().trim().isEmpty() ||
            txtHoTen.getText().trim().isEmpty() ||
            txtDonVi.getText().trim().isEmpty() ||
            txtSDT.getText().trim().isEmpty()) {
            
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin bắt buộc!", 
                "Lỗi", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }
    
    private void clearFields() {
        txtMaGiamThi.setText("");
        txtHoTen.setText("");
        txtDonVi.setText("");
        txtSDT.setText("");
        txtEmail.setText("");
        txtUsername.setText("");
        txtMaGiamThi.requestFocus();
    }
}
