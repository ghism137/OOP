package QuanLyKyThi;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Form thống kê kết quả thi
 */
public class ThongKeForm extends JInternalFrame {
    private JTable tableThongKe;
    private DefaultTableModel tableModel;
    private JTextArea txtSummary;
    private XMLDatabase database;
    
    public ThongKeForm(List<KyThi> list) {
        super("Thống Kê Kết Quả", true, true, true, true);
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
        JLabel titleLabel = new JLabel("THỐNG KÊ KẾT QUẢ THI");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);
        
        // Split pane cho 2 phần: thống kê tổng quan và chi tiết
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        
        // Panel thống kê tổng quan
        JPanel summaryPanel = new JPanel(new BorderLayout());
        summaryPanel.setBorder(BorderFactory.createTitledBorder("Thống Kê Tổng Quan"));
        
        txtSummary = new JTextArea(8, 50);
        txtSummary.setEditable(false);
        txtSummary.setFont(new Font("Courier New", Font.PLAIN, 12));
        txtSummary.setBackground(getBackground());
        
        JScrollPane summaryScroll = new JScrollPane(txtSummary);
        summaryPanel.add(summaryScroll, BorderLayout.CENTER);
        
        splitPane.setTopComponent(summaryPanel);
        
        // Panel thống kê chi tiết theo kỳ thi
        JPanel detailPanel = new JPanel(new BorderLayout());
        detailPanel.setBorder(BorderFactory.createTitledBorder("Thống Kê Chi Tiết Theo Kỳ Thi"));
        
        // Tạo bảng thống kê
        String[] columnNames = {"Kỳ Thi", "Số TS", "Điểm TB", "Cao Nhất", "Thấp Nhất", "Đạt", "Không Đạt", "Tỷ Lệ Đạt (%)"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tableThongKe = new JTable(tableModel);
        tableThongKe.setFont(new Font("Arial", Font.PLAIN, 11));
        tableThongKe.getTableHeader().setFont(new Font("Arial", Font.BOLD, 11));
        
        JScrollPane detailScroll = new JScrollPane(tableThongKe);
        detailPanel.add(detailScroll, BorderLayout.CENTER);
        
        splitPane.setBottomComponent(detailPanel);
        splitPane.setDividerLocation(250);
        
        add(splitPane, BorderLayout.CENTER);
        
        // Panel buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        JButton refreshBtn = new JButton("Làm mới");
        refreshBtn.addActionListener(e -> loadData());
        
        JButton exportBtn = new JButton("Xuất báo cáo");
        exportBtn.addActionListener(e -> exportReport());
        
        JButton closeBtn = new JButton("Đóng");
        closeBtn.addActionListener(e -> setVisible(false));
        
        buttonPanel.add(refreshBtn);
        buttonPanel.add(exportBtn);
        buttonPanel.add(closeBtn);
        
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void loadData() {
        try {
            List<KyThi> allKyThi = database.getAllKyThi();
            List<ThiSinh> allThiSinh = database.getAllThiSinh();
            
            // Thống kê tổng quan
            generateSummaryStatistics(allKyThi, allThiSinh);
            
            // Thống kê chi tiết
            generateDetailedStatistics(allKyThi);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Lỗi khi tải dữ liệu thống kê: " + e.getMessage());
        }
    }
    
    private void generateSummaryStatistics(List<KyThi> kyThiList, List<ThiSinh> thiSinhList) {
        StringBuilder summary = new StringBuilder();
        
        // Tổng số kỳ thi và thí sinh
        summary.append("THỐNG KÊ TỔNG QUAN HỆ THỐNG\n");
        summary.append("============================\n\n");
        summary.append(String.format("📊 Tổng số kỳ thi:        %d\n", kyThiList.size()));
        summary.append(String.format("👥 Tổng số thí sinh:      %d\n", thiSinhList.size()));
        
        // Thống kê tham gia thi
        int tongLuotThi = 0;
        int tongBaiDaThi = 0;
        double tongDiem = 0;
        int soKetQua = 0;
        
        for (KyThi kt : kyThiList) {
            tongLuotThi += kt.getDanhSachThiSinh().size();
            List<KetQua> ketQuaList = kt.getDanhSachKetQua();
            
            for (KetQua kq : ketQuaList) {
                if (kq.getTrangThai() != KetQua.TrangThaiBaiThi.CHUA_THI) {
                    tongBaiDaThi++;
                    if (kq.getDiem() > 0) {
                        tongDiem += kq.getDiem();
                        soKetQua++;
                    }
                }
            }
        }
        
        summary.append(String.format("📝 Tổng lượt đăng ký:     %d\n", tongLuotThi));
        summary.append(String.format("✅ Tổng bài đã thi:       %d\n", tongBaiDaThi));
        
        if (soKetQua > 0) {
            double diemTrungBinh = tongDiem / soKetQua;
            summary.append(String.format("📈 Điểm trung bình:       %.2f\n", diemTrungBinh));
        }
        
        // Phân loại kết quả tổng thể
        Map<String, Integer> phanLoai = new HashMap<>();
        phanLoai.put("Xuất sắc", 0);
        phanLoai.put("Giỏi", 0);
        phanLoai.put("Khá", 0);
        phanLoai.put("Trung bình", 0);
        phanLoai.put("Yếu/Kém", 0);
        
        for (KyThi kt : kyThiList) {
            for (KetQua kq : kt.getDanhSachKetQua()) {
                if (kq.getDiem() > 0) {
                    String xepLoai = getXepLoai(kq.getDiem());
                    if (xepLoai.equals("Yếu") || xepLoai.equals("Kém")) {
                        phanLoai.put("Yếu/Kém", phanLoai.get("Yếu/Kém") + 1);
                    } else {
                        phanLoai.put(xepLoai, phanLoai.getOrDefault(xepLoai, 0) + 1);
                    }
                }
            }
        }
        
        summary.append("\n🏆 PHÂN LOẠI KẾT QUẢ:\n");
        for (Map.Entry<String, Integer> entry : phanLoai.entrySet()) {
            if (entry.getValue() > 0) {
                double phanTram = soKetQua > 0 ? (entry.getValue() * 100.0 / soKetQua) : 0;
                summary.append(String.format("   %-12s: %3d (%5.1f%%)\n", 
                    entry.getKey(), entry.getValue(), phanTram));
            }
        }
        
        txtSummary.setText(summary.toString());
    }
    
    private void generateDetailedStatistics(List<KyThi> kyThiList) {
        tableModel.setRowCount(0);
        
        for (KyThi kt : kyThiList) {
            List<KetQua> ketQuaList = kt.getDanhSachKetQua();
            
            int soThiSinh = kt.getDanhSachThiSinh().size();
            
            if (ketQuaList.isEmpty()) {
                Object[] row = {
                    kt.getTenKyThi(),
                    soThiSinh,
                    "N/A",
                    "N/A", 
                    "N/A",
                    0,
                    0,
                    "0.0"
                };
                tableModel.addRow(row);
                continue;
            }
            
            double tongDiem = 0;
            double diemCaoNhat = -1;
            double diemThapNhat = 11;
            int soDat = 0;
            int soKhongDat = 0;
            int soCoKetQua = 0;
            
            for (KetQua kq : ketQuaList) {
                if (kq.getDiem() >= 0) {
                    soCoKetQua++;
                    tongDiem += kq.getDiem();
                    
                    if (kq.getDiem() > diemCaoNhat) diemCaoNhat = kq.getDiem();
                    if (kq.getDiem() < diemThapNhat) diemThapNhat = kq.getDiem();
                    
                    if (kq.getDiem() >= 5.0) {
                        soDat++;
                    } else {
                        soKhongDat++;
                    }
                }
            }
            
            double diemTrungBinh = soCoKetQua > 0 ? tongDiem / soCoKetQua : 0;
            double tyLeDat = soCoKetQua > 0 ? (soDat * 100.0 / soCoKetQua) : 0;
            
            Object[] row = {
                kt.getTenKyThi(),
                soThiSinh,
                soCoKetQua > 0 ? String.format("%.2f", diemTrungBinh) : "N/A",
                diemCaoNhat >= 0 ? String.format("%.2f", diemCaoNhat) : "N/A",
                diemThapNhat <= 10 ? String.format("%.2f", diemThapNhat) : "N/A",
                soDat,
                soKhongDat,
                String.format("%.1f", tyLeDat)
            };
            tableModel.addRow(row);
        }
    }
    
    private String getXepLoai(double diem) {
        if (diem >= 9.0) return "Xuất sắc";
        if (diem >= 8.0) return "Giỏi";
        if (diem >= 7.0) return "Khá";
        if (diem >= 6.0) return "Trung bình khá";
        if (diem >= 5.0) return "Trung bình";
        if (diem >= 4.0) return "Yếu";
        return "Kém";
    }
    
    private void exportReport() {
        JOptionPane.showMessageDialog(this, 
            "Chức năng xuất báo cáo đang được phát triển!", 
            "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }
}
