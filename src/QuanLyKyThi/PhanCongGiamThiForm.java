package QuanLyKyThi;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Form phân công giám thị cho kỳ thi
 */
public class PhanCongGiamThiForm extends JInternalFrame {
    private JComboBox<KyThi> cmbKyThi;
    private JTable tableGiamThi;
    private DefaultTableModel tableModel;
    private XMLDatabase database;
    
    public PhanCongGiamThiForm(List<GiamThi> giamThis, List<KyThi> kyThis) {
        super("Phân Công Giám Thị", true, true, true, true);
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
        JLabel titleLabel = new JLabel("PHÂN CÔNG GIÁM THỊ");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);
        
        // Panel chọn kỳ thi
        JPanel kyThiPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        kyThiPanel.setBorder(BorderFactory.createTitledBorder("Chọn Kỳ Thi"));
        kyThiPanel.add(new JLabel("Kỳ thi:"));
        
        cmbKyThi = new JComboBox<>();
        cmbKyThi.setPreferredSize(new Dimension(300, 25));
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
        
        // Panel danh sách giám thị
        JPanel giamThiPanel = new JPanel(new BorderLayout());
        giamThiPanel.setBorder(BorderFactory.createTitledBorder("Danh Sách Giám Thị"));
        
        // Tạo bảng giám thị
        String[] columnNames = {"Mã GT", "Họ Tên", "Đơn Vị", "Số ĐT", "Trạng Thái"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tableGiamThi = new JTable(tableModel);
        tableGiamThi.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(tableGiamThi);
        giamThiPanel.add(scrollPane, BorderLayout.CENTER);
        
        add(giamThiPanel, BorderLayout.CENTER);
        
        // Panel buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        JButton phanCongBtn = new JButton("Phân Công");
        phanCongBtn.addActionListener(e -> phanCongGiamThi());
        
        JButton huyPhanCongBtn = new JButton("Hủy Phân Công");
        huyPhanCongBtn.addActionListener(e -> huyPhanCong());
        
        JButton refreshBtn = new JButton("Làm mới");
        refreshBtn.addActionListener(e -> loadData());
        
        JButton closeBtn = new JButton("Đóng");
        closeBtn.addActionListener(e -> setVisible(false));
        
        buttonPanel.add(phanCongBtn);
        buttonPanel.add(huyPhanCongBtn);
        buttonPanel.add(refreshBtn);
        buttonPanel.add(closeBtn);
        
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void loadData() {
        // Load kỳ thi
        cmbKyThi.removeAllItems();
        try {
            List<KyThi> allKyThi = database.getAllKyThi();
            for (KyThi kt : allKyThi) {
                cmbKyThi.addItem(kt);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Lỗi khi tải danh sách kỳ thi: " + e.getMessage());
        }
        
        // Load giám thị
        tableModel.setRowCount(0);
        try {
            List<GiamThi> allGiamThi = database.getAllGiamThi();
            KyThi selectedKyThi = (KyThi) cmbKyThi.getSelectedItem();
            
            for (GiamThi gt : allGiamThi) {
                String trangThai = "Chưa phân công";
                if (selectedKyThi != null && selectedKyThi.getDanhSachGiamThi().contains(gt)) {
                    trangThai = "Đã phân công";
                }
                
                Object[] row = {
                    gt.getMaGiamThi(),
                    gt.getHoTen(),
                    gt.getDonVi(),
                    gt.getSDT(),
                    trangThai
                };
                tableModel.addRow(row);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Lỗi khi tải danh sách giám thị: " + e.getMessage());
        }
    }
    
    private void phanCongGiamThi() {
        KyThi selectedKyThi = (KyThi) cmbKyThi.getSelectedItem();
        int selectedRow = tableGiamThi.getSelectedRow();
        
        if (selectedKyThi == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn kỳ thi!");
            return;
        }
        
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn giám thị!");
            return;
        }
        
        String maGiamThi = (String) tableModel.getValueAt(selectedRow, 0);
        
        try {
            List<GiamThi> allGiamThi = database.getAllGiamThi();
            GiamThi giamThi = allGiamThi.stream()
                .filter(gt -> gt.getMaGiamThi().equals(maGiamThi))
                .findFirst()
                .orElse(null);
                
            if (giamThi != null) {
                boolean success = giamThi.phanCong(selectedKyThi);
                
                if (success) {
                    List<KyThi> allKyThi = database.getAllKyThi();
                    database.saveKyThi(allKyThi);
                    
                    JOptionPane.showMessageDialog(this, "Phân công thành công!");
                    loadData();
                } else {
                    JOptionPane.showMessageDialog(this, "Phân công thất bại!");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Lỗi khi phân công: " + e.getMessage());
        }
    }
    
    private void huyPhanCong() {
        JOptionPane.showMessageDialog(this, 
            "Chức năng hủy phân công đang được phát triển!");
    }
}
