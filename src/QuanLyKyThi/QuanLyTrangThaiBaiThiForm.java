package QuanLyKyThi;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Form quản lý trạng thái bài thi và nhập điểm
 * Phân quyền theo role: Admin, Giáo vụ, Giám thị
 */
public class QuanLyTrangThaiBaiThiForm extends JFrame {
    private User currentUser;
    private XMLDatabase xmlDatabase;
    private JTable tableKetQua;
    private DefaultTableModel tableModel;
    private JComboBox<String> comboKyThi;
    private JTextField txtDiem;
    private JTextArea txtGhiChu;
    private JButton btnBatDauThi, btnNopBai, btnBatDauCham, btnNhapDiem, btnCapNhatDiem;
    private JLabel lblCurrentUser;
    private List<KetQua> danhSachKetQua;
    
    public QuanLyTrangThaiBaiThiForm(User currentUser) {
        this.currentUser = currentUser;
        this.xmlDatabase = new XMLDatabase();
        this.danhSachKetQua = new ArrayList<>();
        
        initComponents();
        loadData();
        setupPermissions();
    }
    
    private void initComponents() {
        setTitle("Quản Lý Trạng Thái Bài Thi - " + currentUser.getFullName());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Panel thông tin người dùng
        JPanel panelUser = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lblCurrentUser = new JLabel("Đăng nhập: " + currentUser.getFullName() + " (" + currentUser.getRole() + ")");
        lblCurrentUser.setFont(new Font("Arial", Font.BOLD, 12));
        panelUser.add(lblCurrentUser);
        add(panelUser, BorderLayout.NORTH);
        
        // Panel chọn kỳ thi
        JPanel panelKyThi = new JPanel(new FlowLayout());
        panelKyThi.add(new JLabel("Chọn kỳ thi:"));
        comboKyThi = new JComboBox<>();
        comboKyThi.addActionListener(e -> loadKetQuaByKyThi());
        panelKyThi.add(comboKyThi);
        
        JButton btnRefresh = new JButton("Làm mới");
        btnRefresh.addActionListener(e -> loadData());
        panelKyThi.add(btnRefresh);
        
        add(panelKyThi, BorderLayout.NORTH);
        
        // Bảng kết quả
        String[] columnNames = {"STT", "Thí sinh", "Kỳ thi", "Trạng thái", "Điểm", "Người chấm", "Thời gian chấm", "Ghi chú"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableKetQua = new JTable(tableModel);
        tableKetQua.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(tableKetQua);
        scrollPane.setPreferredSize(new Dimension(800, 300));
        add(scrollPane, BorderLayout.CENTER);
        
        // Panel thao tác
        JPanel panelThaoTac = createThaoTacPanel();
        add(panelThaoTac, BorderLayout.SOUTH);
        
        
    }
    
    private JPanel createThaoTacPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        panel.setBorder(BorderFactory.createTitledBorder("Thao tác"));
        
        // Nút thao tác trạng thái
        gbc.gridx = 0; gbc.gridy = 0;
        btnBatDauThi = new JButton("Bắt đầu thi");
        btnBatDauThi.addActionListener(e -> batDauThi());
        panel.add(btnBatDauThi, gbc);
        
        gbc.gridx = 1;
        btnNopBai = new JButton("Nộp bài");
        btnNopBai.addActionListener(e -> nopBai());
        panel.add(btnNopBai, gbc);
        
        gbc.gridx = 2;
        btnBatDauCham = new JButton("Bắt đầu chấm");
        btnBatDauCham.addActionListener(e -> batDauCham());
        panel.add(btnBatDauCham, gbc);
        
        // Nhập điểm
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Điểm (0-10):"), gbc);
        
        gbc.gridx = 1;
        txtDiem = new JTextField(10);
        panel.add(txtDiem, gbc);
        
        gbc.gridx = 2;
        btnNhapDiem = new JButton("Nhập điểm");
        btnNhapDiem.addActionListener(e -> nhapDiem());
        panel.add(btnNhapDiem, gbc);
        
        gbc.gridx = 3;
        btnCapNhatDiem = new JButton("Cập nhật điểm");
        btnCapNhatDiem.addActionListener(e -> capNhatDiem());
        panel.add(btnCapNhatDiem, gbc);
        
        // Ghi chú
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Ghi chú:"), gbc);
        
        gbc.gridx = 1; gbc.gridwidth = 3; gbc.fill = GridBagConstraints.HORIZONTAL;
        txtGhiChu = new JTextArea(2, 30);
        txtGhiChu.setBorder(BorderFactory.createLoweredBevelBorder());
        panel.add(new JScrollPane(txtGhiChu), gbc);
        
        return panel;
    }
    
    private void loadData() {
        // Load danh sách kỳ thi
        comboKyThi.removeAllItems();
        List<KyThi> danhSachKyThi = xmlDatabase.getAllKyThi();
        for (KyThi kyThi : danhSachKyThi) {
            comboKyThi.addItem(kyThi.getTenKyThi());
        }
        
        if (comboKyThi.getItemCount() > 0) {
            comboKyThi.setSelectedIndex(0);
            loadKetQuaByKyThi();
        }
    }
    
    private void loadKetQuaByKyThi() {
        String tenKyThi = (String) comboKyThi.getSelectedItem();
        if (tenKyThi == null) return;
        
        // Clear table
        tableModel.setRowCount(0);
        danhSachKetQua.clear();
        
        // Load kết quả cho kỳ thi được chọn
        List<KetQua> allKetQua = xmlDatabase.getAllKetQua();
        int stt = 1;
        
        for (KetQua ketQua : allKetQua) {
            if (ketQua.getKyThi().getTenKyThi().equals(tenKyThi)) {
                danhSachKetQua.add(ketQua);
                Object[] row = {
                    stt++,
                    ketQua.getThiSinh().getHoTen(),
                    ketQua.getKyThi().getTenKyThi(),
                    ketQua.getTrangThai().getMoTa(),
                    ketQua.getTrangThai() == KetQua.TrangThaiBaiThi.DA_CHAM ? 
                        String.format("%.1f", ketQua.getDiem()) : "Chưa có",
                    ketQua.getNguoiCham().isEmpty() ? "Chưa có" : ketQua.getNguoiCham(),
                    ketQua.getThoiGianCham() != null ? 
                        ketQua.getThoiGianCham().toString() : "Chưa có",
                    ketQua.getGhiChu()
                };
                tableModel.addRow(row);
            }
        }
    }
    
    private void setupPermissions() {
        String role = currentUser.getRole().toLowerCase();
        
        switch (role) {
            case "admin":
                // Admin có tất cả quyền
                enableAllButtons(true);
                break;
            case "giaovu":
                // Giáo vụ có tất cả quyền trừ bắt đầu thi/nộp bài
                btnBatDauThi.setEnabled(false);
                btnNopBai.setEnabled(false);
                btnBatDauCham.setEnabled(true);
                btnNhapDiem.setEnabled(true);
                btnCapNhatDiem.setEnabled(true);
                break;
            case "giamthi":
                // Giám thị chỉ được bắt đầu thi, nộp bài và chấm bài được phân công
                btnBatDauThi.setEnabled(true);
                btnNopBai.setEnabled(true);
                btnBatDauCham.setEnabled(true);
                btnNhapDiem.setEnabled(true);
                btnCapNhatDiem.setEnabled(false); // Chỉ được cập nhật bài mình chấm
                break;
            default:
                enableAllButtons(false);
                break;
        }
    }
    
    private void enableAllButtons(boolean enabled) {
        btnBatDauThi.setEnabled(enabled);
        btnNopBai.setEnabled(enabled);
        btnBatDauCham.setEnabled(enabled);
        btnNhapDiem.setEnabled(enabled);
        btnCapNhatDiem.setEnabled(enabled);
    }
    
    private KetQua getSelectedKetQua() {
        int selectedRow = tableKetQua.getSelectedRow();
        if (selectedRow >= 0 && selectedRow < danhSachKetQua.size()) {
            return danhSachKetQua.get(selectedRow);
        }
        return null;
    }
    
    private void batDauThi() {
        KetQua ketQua = getSelectedKetQua();
        if (ketQua == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một kết quả!");
            return;
        }
        
        try {
            ketQua.batDauThi();
            JOptionPane.showMessageDialog(this, "Bắt đầu thi thành công!");
            xmlDatabase.saveKetQua(xmlDatabase.getAllKetQua());
            loadKetQuaByKyThi();
        } catch (Exceptions.StateTransitionException e) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi không xác định: " + e.getMessage());
        }
    }
    
    private void nopBai() {
        KetQua ketQua = getSelectedKetQua();
        if (ketQua == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một kết quả!");
            return;
        }
        
        try {
            ketQua.nopBai();
            JOptionPane.showMessageDialog(this, "Nộp bài thành công!");
            xmlDatabase.saveKetQua(xmlDatabase.getAllKetQua());
            loadKetQuaByKyThi();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Không thể nộp bài: " + e.getMessage());
        }
    }
    
    private void batDauCham() {
        KetQua ketQua = getSelectedKetQua();
        if (ketQua == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một kết quả!");
            return;
        }
        
        try {
            ketQua.batDauCham(currentUser.getUsername(), currentUser.getRole());
            JOptionPane.showMessageDialog(this, "Bắt đầu chấm bài thành công!");
            xmlDatabase.saveKetQua(xmlDatabase.getAllKetQua());
            loadKetQuaByKyThi();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Không thể bắt đầu chấm bài: " + e.getMessage());
        }
    }
    
    private void nhapDiem() {
        KetQua ketQua = getSelectedKetQua();
        if (ketQua == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một kết quả!");
            return;
        }
        
        try {
            double diem = Double.parseDouble(txtDiem.getText().trim());
            String ghiChu = txtGhiChu.getText().trim();
            
            ketQua.nhapDiem(diem, currentUser.getUsername(), currentUser.getRole(), ghiChu);
            JOptionPane.showMessageDialog(this, "Nhập điểm thành công!");
            xmlDatabase.saveKetQua(xmlDatabase.getAllKetQua());
            loadKetQuaByKyThi();
            txtDiem.setText("");
            txtGhiChu.setText("");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Điểm phải là số từ 0 đến 10!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Không thể nhập điểm: " + e.getMessage());
        }
    }
    
    private void capNhatDiem() {
        KetQua ketQua = getSelectedKetQua();
        if (ketQua == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một kết quả!");
            return;
        }
        
        try {
            double diem = Double.parseDouble(txtDiem.getText().trim());
            String ghiChu = txtGhiChu.getText().trim();
            
            ketQua.capNhatDiem(diem, currentUser.getUsername(), currentUser.getRole(), ghiChu);
            JOptionPane.showMessageDialog(this, "Cập nhật điểm thành công!");
            xmlDatabase.saveKetQua(xmlDatabase.getAllKetQua());
            loadKetQuaByKyThi();
            txtDiem.setText("");
            txtGhiChu.setText("");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Điểm phải là số từ 0 đến 10!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Không thể cập nhật điểm: " + e.getMessage());
        }
    }
    
    // Main method for testing
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            // Test với user admin
            User testUser = new User("admin", "admin123", "Administrator", "admin@email.com", "admin");
            new QuanLyTrangThaiBaiThiForm(testUser).setVisible(true);
        });
    }
}
