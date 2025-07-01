package QuanLyKyThi;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Form thêm kỳ thi mới
 */
public class AddKyThiForm extends JInternalFrame {
    
    private List<KyThi> danhSachKyThi;
    private JTextField txtMaKyThi, txtTenKyThi, txtNgayToChuc, txtPhiDangKy;
    private JComboBox<String> cbTinhTrang;
    private JButton btnSave, btnCancel, btnClear;
    
    public AddKyThiForm(List<KyThi> danhSachKyThi) {
        this.danhSachKyThi = danhSachKyThi;
        initComponents();
    }
    
    private void initComponents() {
        setSize(400, 400);
        setLayout(new BorderLayout());
        
        // Panel chính
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Title
        JLabel titleLabel = new JLabel("THÊM KỲ THI MỚI", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(new Color(0, 102, 204));
        
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);
        
        // Reset gridwidth
        gbc.gridwidth = 1;
        
        // Mã kỳ thi
        gbc.gridx = 0; gbc.gridy = 1;
        mainPanel.add(new JLabel("Mã Kỳ Thi:"), gbc);
        gbc.gridx = 1;
        txtMaKyThi = new JTextField(15);
        txtMaKyThi.setText(generateKyThiId());
        txtMaKyThi.setEditable(false);
        mainPanel.add(txtMaKyThi, gbc);
        
        // Tên kỳ thi
        gbc.gridx = 0; gbc.gridy = 2;
        mainPanel.add(new JLabel("Tên Kỳ Thi:"), gbc);
        gbc.gridx = 1;
        txtTenKyThi = new JTextField(15);
        mainPanel.add(txtTenKyThi, gbc);
        
        // Ngày tổ chức
        gbc.gridx = 0; gbc.gridy = 3;
        mainPanel.add(new JLabel("Ngày Tổ Chức:"), gbc);
        gbc.gridx = 1;
        txtNgayToChuc = new JTextField(15);
        txtNgayToChuc.setToolTipText("Định dạng: dd/MM/yyyy (VD: 15/08/2025)");
        mainPanel.add(txtNgayToChuc, gbc);
        
        // Thêm label hướng dẫn
        gbc.gridx = 1; gbc.gridy = 4;
        JLabel dateHintLabel = new JLabel("(Định dạng: dd/MM/yyyy)");
        dateHintLabel.setFont(new Font("Arial", Font.ITALIC, 10));
        dateHintLabel.setForeground(Color.GRAY);
        mainPanel.add(dateHintLabel, gbc);
        
        // Tình trạng
        gbc.gridx = 0; gbc.gridy = 5;
        mainPanel.add(new JLabel("Tình Trạng:"), gbc);
        gbc.gridx = 1;
        String[] tinhTrangOptions = {"Sắp diễn ra", "Đang diễn ra", "Đã kết thúc", "Đã hủy"};
        cbTinhTrang = new JComboBox<>(tinhTrangOptions);
        mainPanel.add(cbTinhTrang, gbc);
        
        // Phí đăng ký
        gbc.gridx = 0; gbc.gridy = 6;
        mainPanel.add(new JLabel("Phí Đăng Ký (k VNĐ):"), gbc);
        gbc.gridx = 1;
        txtPhiDangKy = new JTextField(15);
        txtPhiDangKy.setText("150"); // Mặc định 150k
        mainPanel.add(txtPhiDangKy, gbc);
        
        add(mainPanel, BorderLayout.CENTER);
        
        // Panel buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        btnSave = new JButton("Lưu");
        btnClear = new JButton("Xóa Trắng");
        btnCancel = new JButton("Hủy");
        
        // Tạo màu và style cho buttons
        btnSave.setBackground(new Color(0, 153, 0));
        btnSave.setForeground(Color.WHITE);
        btnSave.setFocusPainted(false);
        
        btnClear.setBackground(new Color(255, 153, 0));
        btnClear.setForeground(Color.WHITE);
        btnClear.setFocusPainted(false);
        
        btnCancel.setBackground(new Color(204, 0, 0));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setFocusPainted(false);
        
        buttonPanel.add(btnSave);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnCancel);
        
        add(buttonPanel, BorderLayout.SOUTH);
        
        // Event handlers
        setupEventHandlers();
        
        // Focus vào tên kỳ thi
        SwingUtilities.invokeLater(() -> txtTenKyThi.requestFocus());
    }
    
    private void setupEventHandlers() {
        btnSave.addActionListener(e -> saveKyThi());
        btnClear.addActionListener(e -> clearForm());
        btnCancel.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Bạn có chắc chắn muốn hủy? Dữ liệu chưa lưu sẽ bị mất.",
                "Xác nhận hủy", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
            }
        });
        
        // Enter để lưu
        txtTenKyThi.addActionListener(e -> txtNgayToChuc.requestFocus());
        txtNgayToChuc.addActionListener(e -> cbTinhTrang.requestFocus());
    }
    
    private String generateKyThiId() {
        return "KT" + String.format("%03d", danhSachKyThi.size() + 1);
    }
    
    private void saveKyThi() {
        // Validate dữ liệu
        if (!validateInput()) {
            return;
        }
        
        try {
            String maKyThi = txtMaKyThi.getText().trim();
            String tenKyThi = txtTenKyThi.getText().trim();
            String ngayText = txtNgayToChuc.getText().trim();
            String tinhTrang = (String) cbTinhTrang.getSelectedItem();
            String phiText = txtPhiDangKy.getText().trim();
            
            // Parse ngày
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate ngayToChuc = LocalDate.parse(ngayText, formatter);
            
            // Parse phí đăng ký
            double phiDangKy = Double.parseDouble(phiText);
            
            // Tạo kỳ thi mới
            KyThi kyThi = new KyThi(maKyThi, tenKyThi, ngayToChuc, tinhTrang, 
                                   new ArrayList<>(), new ArrayList<>(), phiDangKy);
            
            // Thêm vào danh sách
            danhSachKyThi.add(kyThi);
            
            // Thông báo thành công
            JOptionPane.showMessageDialog(this, 
                "Đã thêm kỳ thi thành công!\n" +
                "Mã kỳ thi: " + maKyThi + "\n" +
                "Tên kỳ thi: " + tenKyThi,
                "Thành công", JOptionPane.INFORMATION_MESSAGE);
            
            // Clear form và tạo mã mới
            clearForm();
            txtMaKyThi.setText(generateKyThiId());
            
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, 
                "Định dạng ngày không hợp lệ!\nVui lòng nhập theo định dạng dd/MM/yyyy",
                "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtNgayToChuc.requestFocus();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Có lỗi xảy ra: " + e.getMessage(),
                "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private boolean validateInput() {
        // Kiểm tra tên kỳ thi
        if (txtTenKyThi.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên kỳ thi!", 
                                        "Thông báo", JOptionPane.WARNING_MESSAGE);
            txtTenKyThi.requestFocus();
            return false;
        }
        
        // Kiểm tra ngày
        if (txtNgayToChuc.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập ngày tổ chức!", 
                                        "Thông báo", JOptionPane.WARNING_MESSAGE);
            txtNgayToChuc.requestFocus();
            return false;
        }
        
        // Kiểm tra phí đăng ký
        if (txtPhiDangKy.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập phí đăng ký!", 
                                        "Thông báo", JOptionPane.WARNING_MESSAGE);
            txtPhiDangKy.requestFocus();
            return false;
        }
        
        try {
            double phi = Double.parseDouble(txtPhiDangKy.getText().trim());
            if (phi < 0) {
                JOptionPane.showMessageDialog(this, "Phí đăng ký không thể âm!", 
                                            "Thông báo", JOptionPane.WARNING_MESSAGE);
                txtPhiDangKy.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Phí đăng ký phải là số!", 
                                        "Thông báo", JOptionPane.WARNING_MESSAGE);
            txtPhiDangKy.requestFocus();
            return false;
        }
        String maKyThi = txtMaKyThi.getText().trim();
        for (KyThi kt : danhSachKyThi) {
            if (kt.getMaKyThi().equals(maKyThi)) {
                JOptionPane.showMessageDialog(this, "Mã kỳ thi đã tồn tại!", 
                                            "Thông báo", JOptionPane.WARNING_MESSAGE);
                return false;
            }
        }
        
        return true;
    }
    
    private void clearForm() {
        txtTenKyThi.setText("");
        txtNgayToChuc.setText("");
        txtPhiDangKy.setText("150");
        cbTinhTrang.setSelectedIndex(0);
        txtTenKyThi.requestFocus();
    }
}
