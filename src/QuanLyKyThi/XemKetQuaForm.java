package QuanLyKyThi;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Form xem kết quả thi
 */
public class XemKetQuaForm extends JInternalFrame {
    private JComboBox<KyThi> cmbKyThi;
    private JTable tableKetQua;
    private DefaultTableModel tableModel;
    private XMLDatabase database;
    
    public XemKetQuaForm(List<KyThi> kyThis, List<ThiSinh> thiSinhs) {
        super("Xem Kết Quả Thi", true, true, true, true);
        this.database = new XMLDatabase();
        
        initComponents();
        loadData();
        setSize(900, 600);
        setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        
        // Panel tiêu đề
        JPanel titlePanel = new JPanel(new FlowLayout());
        JLabel titleLabel = new JLabel("XEM KẾT QUẢ THI");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);
        
        // Panel chọn kỳ thi
        JPanel kyThiPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        kyThiPanel.setBorder(BorderFactory.createTitledBorder("Chọn Kỳ Thi"));
        kyThiPanel.add(new JLabel("Kỳ thi:"));
        
        cmbKyThi = new JComboBox<>();
        cmbKyThi.setPreferredSize(new Dimension(300, 25));
        cmbKyThi.addActionListener(e -> loadKetQuaData());
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
        
        // Panel kết quả
        JPanel ketQuaPanel = new JPanel(new BorderLayout());
        ketQuaPanel.setBorder(BorderFactory.createTitledBorder("Kết Quả Thi"));
        
        // Tạo bảng kết quả
        String[] columnNames = {"Mã TS", "Họ Tên", "Điểm", "Xếp Loại", "Trạng Thái", "Thời Gian Thi"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tableKetQua = new JTable(tableModel);
        tableKetQua.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(tableKetQua);
        ketQuaPanel.add(scrollPane, BorderLayout.CENTER);
        
        add(ketQuaPanel, BorderLayout.CENTER);
        
        // Panel buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        JButton refreshBtn = new JButton("Làm mới");
        refreshBtn.addActionListener(e -> loadData());
        
        JButton exportBtn = new JButton("Xuất Excel");
        exportBtn.addActionListener(e -> exportToExcel());
        
        JButton printBtn = new JButton("In");
        printBtn.addActionListener(e -> printReport());
        
        JButton closeBtn = new JButton("Đóng");
        closeBtn.addActionListener(e -> setVisible(false));
        
        buttonPanel.add(refreshBtn);
        buttonPanel.add(exportBtn);
        buttonPanel.add(printBtn);
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
        
        loadKetQuaData();
    }
    
    private void loadKetQuaData() {
        tableModel.setRowCount(0);
        KyThi selectedKyThi = (KyThi) cmbKyThi.getSelectedItem();
        
        if (selectedKyThi == null) return;
        
        try {
            List<KetQua> danhSachKetQua = selectedKyThi.getDanhSachKetQua();
            
            for (KetQua kq : danhSachKetQua) {
                ThiSinh ts = kq.getThiSinh();
                double diem = kq.getDiem();
                
                String xepLoai = kq.getXepLoai();
                String trangThai = kq.getTrangThai().getMoTa();
                String thoiGian = "";
                
                if (kq.getThoiGianBatDauThi() != null && kq.getThoiGianNopBai() != null) {
                    long phut = java.time.Duration.between(
                        kq.getThoiGianBatDauThi(), 
                        kq.getThoiGianNopBai()
                    ).toMinutes();
                    thoiGian = phut + " phút";
                }
                
                Object[] row = {
                    ts.getSoBaoDanh(),
                    ts.getHoTen(),
                    String.format("%.2f", diem),
                    xepLoai,
                    trangThai,
                    thoiGian
                };
                tableModel.addRow(row);
            }
            
            // Sort by score descending
            sortTableByScore();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Lỗi khi tải kết quả: " + e.getMessage());
        }
    }
    
    
    
    private void sortTableByScore() {
        // Simple bubble sort for demonstration
        int rowCount = tableModel.getRowCount();
        for (int i = 0; i < rowCount - 1; i++) {
            for (int j = 0; j < rowCount - i - 1; j++) {
                String score1 = (String) tableModel.getValueAt(j, 2);
                String score2 = (String) tableModel.getValueAt(j + 1, 2);
                
                double d1 = Double.parseDouble(score1);
                double d2 = Double.parseDouble(score2);
                
                if (d1 < d2) {
                    // Swap rows
                    Object[] row1 = new Object[tableModel.getColumnCount()];
                    Object[] row2 = new Object[tableModel.getColumnCount()];
                    
                    for (int k = 0; k < tableModel.getColumnCount(); k++) {
                        row1[k] = tableModel.getValueAt(j, k);
                        row2[k] = tableModel.getValueAt(j + 1, k);
                    }
                    
                    for (int k = 0; k < tableModel.getColumnCount(); k++) {
                        tableModel.setValueAt(row2[k], j, k);
                        tableModel.setValueAt(row1[k], j + 1, k);
                    }
                }
            }
        }
    }
    
    private void exportToExcel() {
        JOptionPane.showMessageDialog(this, 
            "Chức năng xuất Excel đang được phát triển!", 
            "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void printReport() {
        JOptionPane.showMessageDialog(this, 
            "Chức năng in báo cáo đang được phát triển!", 
            "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }
}
