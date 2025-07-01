package QuanLyKyThi;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Giao diện chính của hệ thống quản lý kỳ thi
 * @author ad
 */
public class MainGUI extends JFrame {
    
    // Dữ liệu và services
    private List<KyThi> danhSachKyThi;
    private List<ThiSinh> danhSachThiSinh;
    private List<GiamThi> danhSachGiamThi;
    private XMLDatabase database;
    private AuthenticationService authService;
    
    // Components
    private JMenuBar menuBar;
    private JMenu menuKyThi, menuThiSinh, menuGiamThi, menuKetQua, menuSystem;
    private JDesktopPane desktopPane;
    
    public MainGUI() {
        initData();
        initComponents();
        setupLayout();
        setVisible(true);
    }
    
    private void initData() {
        danhSachKyThi = new ArrayList<>();
        danhSachThiSinh = new ArrayList<>();
        danhSachGiamThi = new ArrayList<>();
        
        // Tạo dữ liệu mẫu
        createSampleData();
    }
    
    private void createSampleData() {
        // Tạo kỳ thi mẫu với phí đăng ký
        KyThi kyThi1 = new KyThi("KT001", "Kỳ thi Java OOP", LocalDate.of(2025, 8, 15), 
                                "Sắp diễn ra", new ArrayList<>(), new ArrayList<>(), 150.0);
        KyThi kyThi2 = new KyThi("KT002", "Kỳ thi Web Development", LocalDate.of(2025, 7, 20), 
                                "Đang diễn ra", new ArrayList<>(), new ArrayList<>(), 200.0);
        KyThi kyThi3 = new KyThi("KT003", "Kỳ thi Database", LocalDate.of(2025, 6, 10), 
                                "Đã kết thúc", new ArrayList<>(), new ArrayList<>(), 180.0);
        
        danhSachKyThi.add(kyThi1);
        danhSachKyThi.add(kyThi2);
        danhSachKyThi.add(kyThi3);
        
        // Tạo thí sinh mẫu
        ThiSinh ts1 = new ThiSinh("TS001", "Nguyễn Văn A", LocalDate.of(2003, 5, 10), 
                                  "Nam", "Hà Nội", "0123456789");
        ThiSinh ts2 = new ThiSinh("TS002", "Trần Thị B", LocalDate.of(2003, 8, 20), 
                                  "Nữ", "Hồ Chí Minh", "0987654321");
        danhSachThiSinh.add(ts1);
        danhSachThiSinh.add(ts2);
        
        // Tạo giám thị mẫu
        GiamThi gt1 = new GiamThi("GT001", "Phạm Văn C", "Trường ĐHCN", "0111222333");
        danhSachGiamThi.add(gt1);
    }
    
    private void initComponents() {
        setTitle("Hệ Thống Quản Lý Kỳ Thi");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        // Tạo menu bar
        menuBar = new JMenuBar();
        
        // Menu Kỳ Thi
        menuKyThi = new JMenu("Kỳ Thi");
        JMenuItem itemDSKyThi = new JMenuItem("Danh Sách Kỳ Thi");
        JMenuItem itemThemKyThi = new JMenuItem("Thêm Kỳ Thi");
        menuKyThi.add(itemDSKyThi);
        menuKyThi.add(itemThemKyThi);
        
        // Menu Thí Sinh
        menuThiSinh = new JMenu("Thí Sinh");
        JMenuItem itemDSThiSinh = new JMenuItem("Danh Sách Thí Sinh");
        JMenuItem itemThemThiSinh = new JMenuItem("Thêm Thí Sinh");
        JMenuItem itemDangKyThi = new JMenuItem("Đăng Ký Thi");
        menuThiSinh.add(itemDSThiSinh);
        menuThiSinh.add(itemThemThiSinh);
        menuThiSinh.add(itemDangKyThi);
        
        // Menu Giám Thị
        menuGiamThi = new JMenu("Giám Thị");
        JMenuItem itemDSGiamThi = new JMenuItem("Danh Sách Giám Thị");
        JMenuItem itemThemGiamThi = new JMenuItem("Thêm Giám Thị");
        JMenuItem itemPhanCong = new JMenuItem("Phân Công Giám Thị");
        menuGiamThi.add(itemDSGiamThi);
        menuGiamThi.add(itemThemGiamThi);
        menuGiamThi.add(itemPhanCong);
        
        // Menu Kết Quả
        menuKetQua = new JMenu("Kết Quả");
        JMenuItem itemNhapDiem = new JMenuItem("Nhập Điểm");
        JMenuItem itemXemKetQua = new JMenuItem("Xem Kết Quả");
        JMenuItem itemThongKe = new JMenuItem("Thống Kê");
        menuKetQua.add(itemNhapDiem);
        menuKetQua.add(itemXemKetQua);
        menuKetQua.add(itemThongKe);
        
        // Thêm menu vào menu bar
        menuBar.add(menuKyThi);
        menuBar.add(menuThiSinh);
        menuBar.add(menuGiamThi);
        menuBar.add(menuKetQua);
        
        setJMenuBar(menuBar);
        
        // Desktop pane cho MDI
        desktopPane = new JDesktopPane();
        desktopPane.setBackground(new Color(240, 240, 240));
        
        // Event handlers
        setupEventHandlers(itemDSKyThi, itemThemKyThi, itemDSThiSinh, itemThemThiSinh, 
                          itemDangKyThi, itemDSGiamThi, itemThemGiamThi, itemPhanCong,
                          itemNhapDiem, itemXemKetQua, itemThongKe);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        add(desktopPane, BorderLayout.CENTER);
        
        // Status bar
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusPanel.setBorder(BorderFactory.createLoweredBevelBorder());
        JLabel statusLabel = new JLabel("Hệ Thống Quản Lý Kỳ Thi - Sẵn sàng");
        statusPanel.add(statusLabel);
        add(statusPanel, BorderLayout.SOUTH);
    }
    
    private void setupEventHandlers(JMenuItem... items) {
        // Danh sách kỳ thi
        items[0].addActionListener(e -> openKyThiListForm());
        
        // Thêm kỳ thi
        items[1].addActionListener(e -> openAddKyThiForm());
        
        // Danh sách thí sinh
        items[2].addActionListener(e -> openThiSinhListForm());
        
        // Thêm thí sinh
        items[3].addActionListener(e -> openAddThiSinhForm());
        
        // Đăng ký thi
        items[4].addActionListener(e -> openDangKyThiForm());
        
        // Danh sách giám thị
        items[5].addActionListener(e -> openGiamThiListForm());
        
        // Thêm giám thị
        items[6].addActionListener(e -> openAddGiamThiForm());
        
        // Phân công giám thị
        items[7].addActionListener(e -> openPhanCongForm());
        
        // Nhập điểm
        items[8].addActionListener(e -> openNhapDiemForm());
        
        // Xem kết quả
        items[9].addActionListener(e -> openXemKetQuaForm());
        
        // Thống kê
        items[10].addActionListener(e -> openThongKeForm());
    }
    
    // Methods để mở các form
    private void openKyThiListForm() {
        KyThiListForm form = new KyThiListForm(danhSachKyThi);
        addInternalFrame(form, "Danh Sách Kỳ Thi");
    }
    
    private void openAddKyThiForm() {
        AddKyThiForm form = new AddKyThiForm(danhSachKyThi);
        addInternalFrame(form, "Thêm Kỳ Thi");
    }
    
    private void openThiSinhListForm() {
        ThiSinhListForm form = new ThiSinhListForm(danhSachThiSinh);
        addInternalFrame(form, "Danh Sách Thí Sinh");
    }
    
    private void openAddThiSinhForm() {
        AddThiSinhForm form = new AddThiSinhForm(danhSachThiSinh);
        addInternalFrame(form, "Thêm Thí Sinh");
    }
    
    private void openDangKyThiForm() {
        DangKyThiForm form = new DangKyThiForm(danhSachThiSinh, danhSachKyThi);
        addInternalFrame(form, "Đăng Ký Thi");
    }
    
    private void openGiamThiListForm() {
        GiamThiListForm form = new GiamThiListForm(danhSachGiamThi);
        addInternalFrame(form, "Danh Sách Giám Thị");
    }
    
    private void openAddGiamThiForm() {
        AddGiamThiForm form = new AddGiamThiForm(danhSachGiamThi);
        addInternalFrame(form, "Thêm Giám Thị");
    }
    
    private void openPhanCongForm() {
        PhanCongGiamThiForm form = new PhanCongGiamThiForm(danhSachGiamThi, danhSachKyThi);
        addInternalFrame(form, "Phân Công Giám Thị");
    }
    
    private void openNhapDiemForm() {
        NhapDiemForm form = new NhapDiemForm(danhSachKyThi);
        addInternalFrame(form, "Nhập Điểm");
    }
    
    private void openXemKetQuaForm() {
        XemKetQuaForm form = new XemKetQuaForm(danhSachKyThi, danhSachThiSinh);
        addInternalFrame(form, "Xem Kết Quả");
    }
    
    private void openThongKeForm() {
        ThongKeForm form = new ThongKeForm(danhSachKyThi);
        addInternalFrame(form, "Thống Kê");
    }
    
    private void addInternalFrame(JInternalFrame frame, String title) {
        frame.setTitle(title);
        frame.setClosable(true);
        frame.setMaximizable(true);
        frame.setIconifiable(true);
        frame.setResizable(true);
        
        // Tính toán vị trí để tránh chồng lấp
        int offset = desktopPane.getAllFrames().length * 30;
        frame.setLocation(offset, offset);
        
        desktopPane.add(frame);
        frame.setVisible(true);
        
        try {
            frame.setSelected(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Getter methods cho các danh sách
    public List<KyThi> getDanhSachKyThi() { return danhSachKyThi; }
    public List<ThiSinh> getDanhSachThiSinh() { return danhSachThiSinh; }
    public List<GiamThi> getDanhSachGiamThi() { return danhSachGiamThi; }
}
