package QuanLyKyThi;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Form nhập điểm cho kỳ thi
 */
public class NhapDiemForm extends JInternalFrame {
    private JComboBox<KyThi> cmbKyThi;
    private JTable tableThiSinh;
    private DefaultTableModel tableModel;
    private XMLDatabase database;
    
    public NhapDiemForm(List<KyThi> list) {
        super("Nhập Điểm Thi", true, true, true, true);
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
        JLabel titleLabel = new JLabel("NHẬP ĐIỂM THI");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);
        
        // Panel chọn kỳ thi
        JPanel kyThiPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        kyThiPanel.setBorder(BorderFactory.createTitledBorder("Chọn Kỳ Thi"));
        kyThiPanel.add(new JLabel("Kỳ thi:"));
        
        cmbKyThi = new JComboBox<>();
        cmbKyThi.setPreferredSize(new Dimension(300, 25));
        cmbKyThi.addActionListener(e -> loadThiSinhData());
        cmbKyThi.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, 
                    int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof KyThi) {
                    KyThi kt = (KyThi) value;
                    setText(kt.getMaKyThi() + " - " + kt.getTenKyThi());
                }
                return this;
            }
        });
        kyThiPanel.add(cmbKyThi);
        
        add(kyThiPanel, BorderLayout.NORTH);
        
        // Panel danh sách thí sinh
        JPanel thiSinhPanel = new JPanel(new BorderLayout());
        thiSinhPanel.setBorder(BorderFactory.createTitledBorder("Danh Sách Thí Sinh"));
        
        // Tạo bảng thí sinh
        String[] columnNames = {"Mã TS", "Họ Tên", "Điểm", "Trạng Thái"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2; // Chỉ cho phép edit cột điểm
            }
            
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 2) return Double.class;
                return String.class;
            }
        };
        
        tableThiSinh = new JTable(tableModel);
        tableThiSinh.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(tableThiSinh);
        thiSinhPanel.add(scrollPane, BorderLayout.CENTER);
        
        add(thiSinhPanel, BorderLayout.CENTER);
        
        // Panel buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        JButton saveBtn = new JButton("Lưu Điểm");
        saveBtn.addActionListener(e -> saveDiem());
        
        JButton refreshBtn = new JButton("Làm mới");
        refreshBtn.addActionListener(e -> loadData());
        
        JButton closeBtn = new JButton("Đóng");
        closeBtn.addActionListener(e -> setVisible(false));
        
        buttonPanel.add(saveBtn);
        buttonPanel.add(refreshBtn);
        buttonPanel.add(closeBtn);
        
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void loadData() {
        // Load kỳ thi
        cmbKyThi.removeAllItems();
        try {
            List<KyThi> allKyThi = database.loadKyThi();
            for (KyThi kt : allKyThi) {
                cmbKyThi.addItem(kt);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Lỗi khi tải danh sách kỳ thi: " + e.getMessage());
        }
        
        loadThiSinhData();
    }
    
    private void loadThiSinhData() {
        tableModel.setRowCount(0);
        KyThi selectedKyThi = (KyThi) cmbKyThi.getSelectedItem();
        
        if (selectedKyThi == null) return;
        
        try {
            // Load thí sinh đã đăng ký kỳ thi này
            List<ThiSinh> danhSachThiSinh = selectedKyThi.getDanhSachThiSinh();
            List<KetQua> danhSachKetQua = selectedKyThi.getDanhSachKetQua();
            
            for (ThiSinh ts : danhSachThiSinh) {
                // Tìm kết quả tương ứng
                KetQua ketQua = danhSachKetQua.stream()
                    .filter(kq -> kq.getMaThiSinh().equals(ts.getSoBaoDanh()))
                    .findFirst()
                    .orElse(null);
                
                double diem = ketQua != null ? ketQua.getDiem() : 0.0;
                String trangThai = ketQua != null ? "Đã nhập" : "Chưa nhập";
                
                Object[] row = {
                    ts.getSoBaoDanh(),
                    ts.getHoTen(),
                    diem,
                    trangThai
                };
                tableModel.addRow(row);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Lỗi khi tải danh sách thí sinh: " + e.getMessage());
        }
    }
    
    private void saveDiem() {
        KyThi selectedKyThi = (KyThi) cmbKyThi.getSelectedItem();
        
        if (selectedKyThi == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn kỳ thi!");
            return;
        }
        
        try {
            // Stop cell editing
            if (tableThiSinh.isEditing()) {
                tableThiSinh.getCellEditor().stopCellEditing();
            }
            
            List<KetQua> danhSachKetQua = selectedKyThi.getDanhSachKetQua();
            List<ThiSinh> danhSachThiSinh = selectedKyThi.getDanhSachThiSinh();
            
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                String maThiSinh = (String) tableModel.getValueAt(i, 0);
                Object diemObj = tableModel.getValueAt(i, 2);
                
                double diem = 0.0;
                if (diemObj instanceof Number) {
                    diem = ((Number) diemObj).doubleValue();
                } else if (diemObj instanceof String) {
                    try {
                        diem = Double.parseDouble((String) diemObj);
                    } catch (NumberFormatException e) {
                        continue; // Skip invalid scores
                    }
                }
                
                // Validate score range
                if (diem < 0 || diem > 10) {
                    JOptionPane.showMessageDialog(this, 
                        "Điểm phải trong khoảng 0-10! Thí sinh: " + maThiSinh);
                    return;
                }
                
                // Find ThiSinh
                ThiSinh thiSinh = danhSachThiSinh.stream()
                    .filter(ts -> ts.getSoBaoDanh().equals(maThiSinh))
                    .findFirst()
                    .orElse(null);
                
                if (thiSinh != null) {
                    // Find or create KetQua
                    KetQua ketQua = danhSachKetQua.stream()
                        .filter(kq -> kq.getMaThiSinh().equals(maThiSinh))
                        .findFirst()
                        .orElse(null);
                    
                    if (ketQua == null) {
                        ketQua = new KetQua(thiSinh, selectedKyThi, diem);
                        danhSachKetQua.add(ketQua);
                    } else {
                        ketQua.setDiem(diem); // Update existing score
                        ketQua.setTrangThai(KetQua.TrangThaiBaiThi.DA_CHAM);
                    }
                }
            }
            
            // Save to database
            List<KyThi> allKyThi = database.loadKyThi();
            // Find the selectedKyThi in allKyThi and update its ketQua list
            allKyThi.stream()
                    .filter(kt -> kt.getMaKyThi().equals(selectedKyThi.getMaKyThi()))
                    .findFirst()
                    .ifPresent(kt -> kt.setDanhSachKetQua(danhSachKetQua));
            
            database.saveKyThi(allKyThi);
            
            JOptionPane.showMessageDialog(this, "Đã lưu điểm thành công!");
            loadThiSinhData(); // Refresh data
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Lỗi khi lưu điểm: " + e.getMessage());
        }
    }
}
