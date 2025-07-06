package QuanLyKyThi;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Form đăng ký thi cho thí sinh
 */
public class DangKyThiForm extends JInternalFrame {
    private JComboBox<ThiSinh> cmbThiSinh;
    private JTable tableKyThi;
    private DefaultTableModel tableModel;
    private XMLDatabase database;
    
    public DangKyThiForm(List<ThiSinh> thiSinhs, List<KyThi> kyThis) {
        super("Đăng Ký Thi", true, true, true, true);
        this.database = new XMLDatabase();
        
        initComponents();
        loadData();
        setSize(800, 600);
        setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        
        // Panel tiêu đề
        JPanel titlePanel = new JPanel(new FlowLayout());
        JLabel titleLabel = new JLabel("ĐĂNG KÝ THI");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);
        
        // Panel chọn thí sinh
        JPanel thiSinhPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        thiSinhPanel.setBorder(BorderFactory.createTitledBorder("Chọn Thí Sinh"));
        thiSinhPanel.add(new JLabel("Thí sinh:"));
        
        cmbThiSinh = new JComboBox<>();
        cmbThiSinh.setPreferredSize(new Dimension(300, 25));
        cmbThiSinh.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, 
                    int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof ThiSinh) {
                    ThiSinh ts = (ThiSinh) value;
                    setText(ts.getSoBaoDanh() + " - " + ts.getHoTen());
                }
                return this;
            }
        });
        thiSinhPanel.add(cmbThiSinh);
        
        add(thiSinhPanel, BorderLayout.NORTH);
        
        // Panel danh sách kỳ thi
        JPanel kyThiPanel = new JPanel(new BorderLayout());
        kyThiPanel.setBorder(BorderFactory.createTitledBorder("Danh Sách Kỳ Thi"));
        
        // Tạo bảng kỳ thi
        String[] columnNames = {"Mã Kỳ Thi", "Tên Kỳ Thi", "Ngày Thi", "Môn Thi", "Phí (VNĐ)", "Trạng Thái"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tableKyThi = new JTable(tableModel);
        tableKyThi.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableKyThi.setFont(new Font("Arial", Font.PLAIN, 12));
        tableKyThi.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        
        JScrollPane scrollPane = new JScrollPane(tableKyThi);
        kyThiPanel.add(scrollPane, BorderLayout.CENTER);
        
        add(kyThiPanel, BorderLayout.CENTER);
        
        // Panel buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        JButton dangKyBtn = new JButton("Đăng Ký");
        dangKyBtn.addActionListener(e -> dangKyThi());
        
        JButton huyDangKyBtn = new JButton("Hủy Đăng Ký");
        huyDangKyBtn.addActionListener(e -> huyDangKy());
        
        JButton refreshBtn = new JButton("Làm mới");
        refreshBtn.addActionListener(e -> loadData());
        
        JButton closeBtn = new JButton("Đóng");
        closeBtn.addActionListener(e -> setVisible(false));
        
        buttonPanel.add(dangKyBtn);
        buttonPanel.add(huyDangKyBtn);
        buttonPanel.add(refreshBtn);
        buttonPanel.add(closeBtn);
        
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void loadData() {
        // Load thí sinh
        cmbThiSinh.removeAllItems();
        try {
            List<ThiSinh> allThiSinh = database.loadThiSinh();
            for (ThiSinh ts : allThiSinh) {
                cmbThiSinh.addItem(ts);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Lỗi khi tải danh sách thí sinh: " + e.getMessage(), 
                "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        
        // Load kỳ thi
        tableModel.setRowCount(0);
        try {
            List<KyThi> allKyThi = database.loadKyThi();
            for (KyThi kt : allKyThi) {
                String trangThai = "Chưa đăng ký";
                if (cmbThiSinh.getSelectedItem() instanceof ThiSinh) {
                    ThiSinh selectedThiSinh = (ThiSinh) cmbThiSinh.getSelectedItem();
                    if (kt.getDanhSachThiSinh().stream().anyMatch(ts -> ts.getSoBaoDanh().equals(selectedThiSinh.getSoBaoDanh()))) {
                        trangThai = "Đã đăng ký";
                    }
                }
                    
                Object[] row = {
                    kt.getMaKyThi(),
                    kt.getTenKyThi(),
                    kt.getNgayToChuc(),
                    "Chung", // Môn thi mặc định
                    kt.getPhiDangKy(),
                    trangThai
                };
                tableModel.addRow(row);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Lỗi khi tải danh sách kỳ thi: " + e.getMessage(), 
                "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void dangKyThi() {
        ThiSinh selectedThiSinh = (ThiSinh) cmbThiSinh.getSelectedItem();
        int selectedRow = tableKyThi.getSelectedRow();
        
        if (selectedThiSinh == null) {
            JOptionPane.showMessageDialog(this, 
                "Vui lòng chọn thí sinh!", 
                "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                "Vui lòng chọn kỳ thi cần đăng ký!", 
                "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String maKyThi = (String) tableModel.getValueAt(selectedRow, 0);
        String trangThai = (String) tableModel.getValueAt(selectedRow, 5);
        
        if ("Đã đăng ký".equals(trangThai)) {
            JOptionPane.showMessageDialog(this, 
                "Thí sinh đã đăng ký kỳ thi này rồi!", 
                "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            // Tìm kỳ thi
            List<KyThi> allKyThi = database.loadKyThi();
            KyThi kyThi = allKyThi.stream()
                .filter(kt -> kt.getMaKyThi().equals(maKyThi))
                .findFirst()
                .orElse(null);
                
            if (kyThi == null) {
                JOptionPane.showMessageDialog(this, 
                    "Không tìm thấy kỳ thi!", 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Đăng ký
            boolean success = kyThi.themThiSinh(selectedThiSinh);
            
            if (success) {
                // Lưu lại vào database
                database.saveKyThi(allKyThi);
                
                JOptionPane.showMessageDialog(this, 
                    "Đăng ký thành công!", 
                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                loadData(); // Refresh data
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Đăng ký thất bại! Vui lòng thử lại.", 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Lỗi khi đăng ký: " + e.getMessage(), 
                "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void huyDangKy() {
        ThiSinh selectedThiSinh = (ThiSinh) cmbThiSinh.getSelectedItem();
        int selectedRow = tableKyThi.getSelectedRow();
        
        if (selectedThiSinh == null) {
            JOptionPane.showMessageDialog(this, 
                "Vui lòng chọn thí sinh!", 
                "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                "Vui lòng chọn kỳ thi cần hủy đăng ký!", 
                "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String maKyThi = (String) tableModel.getValueAt(selectedRow, 0);
        String trangThai = (String) tableModel.getValueAt(selectedRow, 5);
        
        if (!"Đã đăng ký".equals(trangThai)) {
            JOptionPane.showMessageDialog(this, 
                "Thí sinh chưa đăng ký kỳ thi này!", 
                "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Bạn có chắc chắn muốn hủy đăng ký kỳ thi này?", 
            "Xác nhận", JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                // Tìm kỳ thi và xóa thí sinh
                List<KyThi> allKyThi = database.loadKyThi();
                KyThi kyThi = allKyThi.stream()
                    .filter(kt -> kt.getMaKyThi().equals(maKyThi))
                    .findFirst()
                    .orElse(null);
                    
                if (kyThi != null) {
                    kyThi.getDanhSachThiSinh().removeIf(ts -> ts.getSoBaoDanh().equals(selectedThiSinh.getSoBaoDanh()));
                    database.saveKyThi(allKyThi);
                    
                    JOptionPane.showMessageDialog(this, 
                        "Đã hủy đăng ký thành công!", 
                        "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    loadData(); // Refresh data
                }
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, 
                    "Lỗi khi hủy đăng ký: " + e.getMessage(), 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
