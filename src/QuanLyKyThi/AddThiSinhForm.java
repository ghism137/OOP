package QuanLyKyThi;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * Form thêm thí sinh mới
 */
public class AddThiSinhForm extends JInternalFrame {
    private JTextField txtMaThiSinh;
    private JTextField txtHoTen;
    private JTextField txtNgaySinh;
    private JComboBox<String> cmbGioiTinh;
    private JTextField txtDiaChi;
    private JTextField txtSDT;
    private XMLDatabase database;
    
    public AddThiSinhForm(List<ThiSinh> list) {
        super("Thêm Thí Sinh Mới", true, true, true, true);
        this.database = new XMLDatabase();
        
        initComponents();
        setSize(500, 400);
        setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
        
        // Center the form
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        
        // Panel tiêu đề
        JPanel titlePanel = new JPanel(new FlowLayout());
        JLabel titleLabel = new JLabel("THÊM THÍ SINH MỚI");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);
        
        // Panel chính
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Mã thí sinh
        gbc.gridx = 0; gbc.gridy = 0;
        mainPanel.add(new JLabel("Mã thí sinh:"), gbc);
        gbc.gridx = 1;
        txtMaThiSinh = new JTextField(20);
        mainPanel.add(txtMaThiSinh, gbc);
        
        // Họ tên
        gbc.gridx = 0; gbc.gridy = 1;
        mainPanel.add(new JLabel("Họ tên:"), gbc);
        gbc.gridx = 1;
        txtHoTen = new JTextField(20);
        mainPanel.add(txtHoTen, gbc);
        
        // Ngày sinh
        gbc.gridx = 0; gbc.gridy = 2;
        mainPanel.add(new JLabel("Ngày sinh (dd/MM/yyyy):"), gbc);
        gbc.gridx = 1;
        txtNgaySinh = new JTextField(20);
        txtNgaySinh.setToolTipText("Nhập theo định dạng: dd/MM/yyyy");
        mainPanel.add(txtNgaySinh, gbc);
        
        // Giới tính
        gbc.gridx = 0; gbc.gridy = 3;
        mainPanel.add(new JLabel("Giới tính:"), gbc);
        gbc.gridx = 1;
        cmbGioiTinh = new JComboBox<>(new String[]{"Nam", "Nữ", "Khác"});
        mainPanel.add(cmbGioiTinh, gbc);
        
        // Địa chỉ
        gbc.gridx = 0; gbc.gridy = 4;
        mainPanel.add(new JLabel("Địa chỉ:"), gbc);
        gbc.gridx = 1;
        txtDiaChi = new JTextField(20);
        mainPanel.add(txtDiaChi, gbc);
        
        // Số điện thoại
        gbc.gridx = 0; gbc.gridy = 5;
        mainPanel.add(new JLabel("Số điện thoại:"), gbc);
        gbc.gridx = 1;
        txtSDT = new JTextField(20);
        mainPanel.add(txtSDT, gbc);
        
        add(mainPanel, BorderLayout.CENTER);
        
        // Panel buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        JButton saveBtn = new JButton("Lưu");
        saveBtn.addActionListener(e -> saveThiSinh());
        
        JButton clearBtn = new JButton("Xóa rỗng");
        clearBtn.addActionListener(e -> clearFields());
        
        JButton cancelBtn = new JButton("Hủy");
        cancelBtn.addActionListener(e -> setVisible(false));
        
        buttonPanel.add(saveBtn);
        buttonPanel.add(clearBtn);
        buttonPanel.add(cancelBtn);
        
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void saveThiSinh() {
        try {
            // Validate input
            if (!validateInput()) {
                return;
            }
            
            // Parse date
            LocalDate ngaySinh;
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                ngaySinh = LocalDate.parse(txtNgaySinh.getText().trim(), formatter);
            } catch (DateTimeParseException e) {
                JOptionPane.showMessageDialog(this, 
                    "Ngày sinh không hợp lệ! Vui lòng nhập theo định dạng dd/MM/yyyy", 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Create new ThiSinh
            ThiSinh newThiSinh = new ThiSinh(
                txtMaThiSinh.getText().trim(),
                txtHoTen.getText().trim(),
                convertToDate(ngaySinh),
                (String) cmbGioiTinh.getSelectedItem(),
                txtDiaChi.getText().trim(),
                txtSDT.getText().trim()
            );
            
            // Check if MaThiSinh already exists
            List<ThiSinh> existingList = database.loadThiSinh();
            boolean exists = existingList.stream()
                .anyMatch(ts -> ts.getSoBaoDanh().equals(newThiSinh.getSoBaoDanh()));
            
            if (exists) {
                JOptionPane.showMessageDialog(this, 
                    "Mã thí sinh đã tồn tại! Vui lòng nhập mã khác.", 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Add to database
            existingList.add(newThiSinh);
            database.saveThiSinh(existingList);
            
            JOptionPane.showMessageDialog(this, 
                "Đã thêm thí sinh thành công!", 
                "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            
            clearFields();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Lỗi khi thêm thí sinh: " + e.getMessage(), 
                "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private boolean validateInput() {
        if (txtMaThiSinh.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã thí sinh!", 
                "Lỗi", JOptionPane.WARNING_MESSAGE);
            txtMaThiSinh.requestFocus();
            return false;
        }
        
        if (txtHoTen.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập họ tên!", 
                "Lỗi", JOptionPane.WARNING_MESSAGE);
            txtHoTen.requestFocus();
            return false;
        }
        
        if (txtNgaySinh.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập ngày sinh!", 
                "Lỗi", JOptionPane.WARNING_MESSAGE);
            txtNgaySinh.requestFocus();
            return false;
        }
        
        if (txtDiaChi.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập địa chỉ!", 
                "Lỗi", JOptionPane.WARNING_MESSAGE);
            txtDiaChi.requestFocus();
            return false;
        }
        
        if (txtSDT.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số điện thoại!", 
                "Lỗi", JOptionPane.WARNING_MESSAGE);
            txtSDT.requestFocus();
            return false;
        }
        
        // Validate phone number (basic check)
        String sdt = txtSDT.getText().trim();
        if (!sdt.matches("\\d{10,11}")) {
            JOptionPane.showMessageDialog(this, 
                "Số điện thoại phải có 10-11 chữ số!", 
                "Lỗi", JOptionPane.WARNING_MESSAGE);
            txtSDT.requestFocus();
            return false;
        }
        
        return true;
    }
    
    private void clearFields() {
        txtMaThiSinh.setText("");
        txtHoTen.setText("");
        txtNgaySinh.setText("");
        cmbGioiTinh.setSelectedIndex(0);
        txtDiaChi.setText("");
        txtSDT.setText("");
        txtMaThiSinh.requestFocus();
    }
}
