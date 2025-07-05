package QuanLyKyThi;

import javax.swing.*;
import java.awt.*;
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
    
    // Components
    private JMenuBar menuBar;
    private JMenu menuKyThi, menuThiSinh, menuGiamThi, menuKetQua, menuSystem;
    private JDesktopPane desktopPane;
    
    /**
     * Các preset kích thước phổ biến cho MainGUI
     */
    public static final int[] SIZE_SMALL = {1200, 700};      // Nhỏ
    public static final int[] SIZE_MEDIUM = {1400, 800};     // Trung bình - Mặc định
    public static final int[] SIZE_LARGE = {1600, 900};      // Lớn
    public static final int[] SIZE_XLARGE = {1800, 1000};    // Rất lớn
    public static final int[] SIZE_FULLHD = {1920, 1080};    // Full HD
    
    /**
     * Constructor mặc định với kích thước trung bình
     */
    public MainGUI() {
        this(SIZE_MEDIUM[0], SIZE_MEDIUM[1]);
    }
    
    /**
     * Constructor với kích thước tùy chỉnh
     */
    public MainGUI(int width, int height) {
        initData();
        initComponentsWithSize(width, height);
        setupLayout();
        setVisible(true);
    }
    
    /**
     * Tạo MainGUI với preset kích thước
     */
    public static MainGUI createWithSize(int[] sizePreset) {
        return new MainGUI(sizePreset[0], sizePreset[1]);
    }
    
    private void initData() {
        // Khởi tạo danh sách rỗng - dữ liệu sẽ được load từ XML database hoặc nhập thực tế
        danhSachKyThi = new ArrayList<>();
        danhSachThiSinh = new ArrayList<>();
        danhSachGiamThi = new ArrayList<>();
        
        // Khởi tạo XML database cho việc load/save nếu cần
        database = new XMLDatabase();
        
        System.out.println("MainGUI - Khởi tạo dữ liệu trống, sẵn sàng load từ XML hoặc nhập mới");
        
        // Tùy chọn: Có thể load dữ liệu từ XML nếu file tồn tại
        tryLoadExistingData();
    }
    
    /**
     * Thử load dữ liệu từ XML files nếu tồn tại
     */
    private void tryLoadExistingData() {
        try {
            List<KyThi> existingKyThi = database.loadKyThi();
            List<ThiSinh> existingThiSinh = database.loadThiSinh();
            List<GiamThi> existingGiamThi = database.loadGiamThi();
            
            if (!existingKyThi.isEmpty() || !existingThiSinh.isEmpty() || !existingGiamThi.isEmpty()) {
                danhSachKyThi = existingKyThi;
                danhSachThiSinh = existingThiSinh;
                danhSachGiamThi = existingGiamThi;
                
                System.out.println("MainGUI - Đã load dữ liệu từ XML:");
                System.out.println("  - Kỳ thi: " + danhSachKyThi.size());
                System.out.println("  - Thí sinh: " + danhSachThiSinh.size());
                System.out.println("  - Giám thị: " + danhSachGiamThi.size());
            }
        } catch (Exception e) {
            System.out.println("MainGUI - Chưa có dữ liệu XML hoặc lỗi load: " + e.getMessage());
            System.out.println("MainGUI - Sẽ bắt đầu với dữ liệu trống");
        }
    }
    
    
    /**
     * Khởi tạo components với kích thước tùy chỉnh
     */
    private void initComponentsWithSize(int width, int height) {
        setTitle("Hệ Thống Quản Lý Kỳ Thi - Demo Mode");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Sử dụng kích thước tùy chỉnh
        setSize(width, height);
        setLocationRelativeTo(null);
        setResizable(true); // Cho phép thay đổi kích thước bằng cách kéo thả chuột
        
        // Set kích thước tối thiểu để tránh giao diện bị vỡ
        setMinimumSize(new Dimension(1000, 600));
        
        // Thêm ComponentListener để xử lý khi người dùng thay đổi kích thước
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent evt) {
                handleWindowResize();
            }
        });
        
        // Thông báo về tính năng resize
        System.out.println("MainGUI - Có thể kéo thả để thay đổi kích thước. Kích thước hiện tại: " + width + "×" + height);
        
        // Tạo menu bar với font size responsive
        setupMenuBarWithSize(width, height);
        
        // Tạo desktop pane
        desktopPane = new JDesktopPane();
        desktopPane.setBackground(new Color(240, 240, 240));
        
        // Tính toán kích thước desktop pane
        int desktopWidth = Math.max(800, width - 100);
        int desktopHeight = Math.max(500, height - 150);
        desktopPane.setPreferredSize(new Dimension(desktopWidth, desktopHeight));
    }
    
    /**
     * Setup menu bar với kích thước responsive
     */
    private void setupMenuBarWithSize(int width, int height) {
        // Tính toán font size dựa trên kích thước cửa sổ
        int menuFontSize = Math.max(12, width / 120);
        
        // Tạo menu bar
        menuBar = new JMenuBar();
        
        // Menu Kỳ Thi
        menuKyThi = new JMenu("Kỳ Thi");
        menuKyThi.setFont(new Font("Arial", Font.PLAIN, menuFontSize));
        JMenuItem itemDSKyThi = new JMenuItem("Danh Sách Kỳ Thi");
        JMenuItem itemThemKyThi = new JMenuItem("Thêm Kỳ Thi");
        itemDSKyThi.setFont(new Font("Arial", Font.PLAIN, menuFontSize - 1));
        itemThemKyThi.setFont(new Font("Arial", Font.PLAIN, menuFontSize - 1));
        menuKyThi.add(itemDSKyThi);
        menuKyThi.add(itemThemKyThi);
        
        // Menu Thí Sinh
        menuThiSinh = new JMenu("Thí Sinh");
        menuThiSinh.setFont(new Font("Arial", Font.PLAIN, menuFontSize));
        JMenuItem itemDSThiSinh = new JMenuItem("Danh Sách Thí Sinh");
        JMenuItem itemThemThiSinh = new JMenuItem("Thêm Thí Sinh");
        JMenuItem itemDangKyThi = new JMenuItem("Đăng Ký Thi");
        itemDSThiSinh.setFont(new Font("Arial", Font.PLAIN, menuFontSize - 1));
        itemThemThiSinh.setFont(new Font("Arial", Font.PLAIN, menuFontSize - 1));
        itemDangKyThi.setFont(new Font("Arial", Font.PLAIN, menuFontSize - 1));
        menuThiSinh.add(itemDSThiSinh);
        menuThiSinh.add(itemThemThiSinh);
        menuThiSinh.add(itemDangKyThi);
        
        // Menu Giám Thị
        menuGiamThi = new JMenu("Giám Thị");
        menuGiamThi.setFont(new Font("Arial", Font.PLAIN, menuFontSize));
        JMenuItem itemDSGiamThi = new JMenuItem("Danh Sách Giám Thị");
        JMenuItem itemThemGiamThi = new JMenuItem("Thêm Giám Thị");
        JMenuItem itemPhanCong = new JMenuItem("Phân Công Giám Thị");
        itemDSGiamThi.setFont(new Font("Arial", Font.PLAIN, menuFontSize - 1));
        itemThemGiamThi.setFont(new Font("Arial", Font.PLAIN, menuFontSize - 1));
        itemPhanCong.setFont(new Font("Arial", Font.PLAIN, menuFontSize - 1));
        menuGiamThi.add(itemDSGiamThi);
        menuGiamThi.add(itemThemGiamThi);
        menuGiamThi.add(itemPhanCong);
        
        // Menu Kết Quả
        menuKetQua = new JMenu("Kết Quả");
        menuKetQua.setFont(new Font("Arial", Font.PLAIN, menuFontSize));
        JMenuItem itemNhapDiem = new JMenuItem("Nhập Điểm");
        JMenuItem itemXemKetQua = new JMenuItem("Xem Kết Quả");
        JMenuItem itemThongKe = new JMenuItem("Thống Kê");
        itemNhapDiem.setFont(new Font("Arial", Font.PLAIN, menuFontSize - 1));
        itemXemKetQua.setFont(new Font("Arial", Font.PLAIN, menuFontSize - 1));
        itemThongKe.setFont(new Font("Arial", Font.PLAIN, menuFontSize - 1));
        menuKetQua.add(itemNhapDiem);
        menuKetQua.add(itemXemKetQua);
        menuKetQua.add(itemThongKe);
        
        // Menu System
        menuSystem = new JMenu("Hệ Thống");
        menuSystem.setFont(new Font("Arial", Font.PLAIN, menuFontSize));
        JMenuItem itemLuuDuLieu = new JMenuItem("Lưu Dữ Liệu");
        JMenuItem itemTaiDuLieu = new JMenuItem("Tải Dữ Liệu");
        JMenuItem itemThoat = new JMenuItem("Thoát");
        itemLuuDuLieu.setFont(new Font("Arial", Font.PLAIN, menuFontSize - 1));
        itemTaiDuLieu.setFont(new Font("Arial", Font.PLAIN, menuFontSize - 1));
        itemThoat.setFont(new Font("Arial", Font.PLAIN, menuFontSize - 1));
        menuSystem.add(itemLuuDuLieu);
        menuSystem.add(itemTaiDuLieu);
        menuSystem.addSeparator();
        menuSystem.add(itemThoat);
        
        // Thêm menu vào menu bar
        menuBar.add(menuKyThi);
        menuBar.add(menuThiSinh);
        menuBar.add(menuGiamThi);
        menuBar.add(menuKetQua);
        menuBar.add(menuSystem);
        
        // Set menu bar cho frame
        setJMenuBar(menuBar);
        
        // Setup event handlers
        setupEventHandlers(itemDSKyThi, itemThemKyThi, itemDSThiSinh, itemThemThiSinh, 
                          itemDangKyThi, itemDSGiamThi, itemThemGiamThi, itemPhanCong,
                          itemNhapDiem, itemXemKetQua, itemThongKe, 
                          itemLuuDuLieu, itemTaiDuLieu, itemThoat);
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
        
        // Lưu dữ liệu
        items[11].addActionListener(e -> saveDataToXML());
        
        // Tải dữ liệu
        items[12].addActionListener(e -> loadDataFromXML());
        
        // Thoát
        items[13].addActionListener(e -> System.exit(0));
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
    
    /**
     * Lưu dữ liệu vào XML files
     */
    private void saveDataToXML() {
        try {
            if (database != null) {
                database.saveKyThi(danhSachKyThi);
                database.saveThiSinh(danhSachThiSinh);
                database.saveGiamThi(danhSachGiamThi);
                
                JOptionPane.showMessageDialog(this, 
                    "Lưu dữ liệu thành công!\n" +
                    "- Kỳ thi: " + danhSachKyThi.size() + "\n" +
                    "- Thí sinh: " + danhSachThiSinh.size() + "\n" +
                    "- Giám thị: " + danhSachGiamThi.size(),
                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                
                System.out.println("MainGUI - Đã lưu dữ liệu vào XML files");
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi lưu dữ liệu: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Lỗi khi lưu dữ liệu: " + e.getMessage(),
                "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Tải dữ liệu từ XML files
     */
    private void loadDataFromXML() {
        try {
            if (database != null) {
                int choice = JOptionPane.showConfirmDialog(this,
                    "Tải dữ liệu từ XML sẽ ghi đè dữ liệu hiện tại.\nBạn có chắc chắn?",
                    "Xác nhận", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                
                if (choice == JOptionPane.YES_OPTION) {
                    danhSachKyThi = database.loadKyThi();
                    danhSachThiSinh = database.loadThiSinh();
                    danhSachGiamThi = database.loadGiamThi();
                    
                    JOptionPane.showMessageDialog(this, 
                        "Tải dữ liệu thành công!\n" +
                        "- Kỳ thi: " + danhSachKyThi.size() + "\n" +
                        "- Thí sinh: " + danhSachThiSinh.size() + "\n" +
                        "- Giám thị: " + danhSachGiamThi.size(),
                        "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    
                    System.out.println("MainGUI - Đã tải dữ liệu từ XML files");
                }
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi tải dữ liệu: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Lỗi khi tải dữ liệu: " + e.getMessage(),
                "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Getter methods cho các danh sách
    public List<KyThi> getDanhSachKyThi() { return danhSachKyThi; }
    public List<ThiSinh> getDanhSachThiSinh() { return danhSachThiSinh; }
    public List<GiamThi> getDanhSachGiamThi() { return danhSachGiamThi; }
    
    /**
     * Xử lý khi người dùng thay đổi kích thước cửa sổ bằng cách kéo thả chuột
     */
    private void handleWindowResize() {
        SwingUtilities.invokeLater(() -> {
            try {
                Dimension currentSize = getSize();
                int newWidth = currentSize.width;
                int newHeight = currentSize.height;
                
                // Debug thông tin
                System.out.println("MainGUI - Kích thước mới: " + newWidth + "x" + newHeight);
                
                // Cập nhật font size cho menu và các component
                updateUIComponentSizes(newWidth, newHeight);
                
                // Refresh giao diện
                revalidate();
                repaint();
                
            } catch (Exception e) {
                System.err.println("Lỗi khi thay đổi kích thước MainGUI: " + e.getMessage());
            }
        });
    }
    
    /**
     * Cập nhật kích thước UI components khi cửa sổ thay đổi kích thước
     */
    private void updateUIComponentSizes(int windowWidth, int windowHeight) {
        // Tính toán font size mới dựa trên kích thước cửa sổ
        int menuFontSize = Math.max(12, windowWidth / 120);
        
        // Cập nhật font cho menu bar
        updateMenuFontSize(menuBar, menuFontSize);
        
        // Cập nhật kích thước desktop pane nếu cần
        if (desktopPane != null) {
            int minDesktopWidth = Math.max(800, windowWidth - 100);
            int minDesktopHeight = Math.max(500, windowHeight - 150);
            desktopPane.setPreferredSize(new Dimension(minDesktopWidth, minDesktopHeight));
        }
    }
    
    /**
     * Cập nhật font size cho menu bar một cách đệ quy
     */
    private void updateMenuFontSize(JMenuBar menuBar, int fontSize) {
        if (menuBar == null) return;
        
        for (int i = 0; i < menuBar.getMenuCount(); i++) {
            JMenu menu = menuBar.getMenu(i);
            if (menu != null) {
                // Cập nhật font cho menu chính
                menu.setFont(new Font("Arial", Font.PLAIN, fontSize));
                
                // Cập nhật font cho các menu item
                updateMenuItemFontSize(menu, fontSize - 1);
            }
        }
    }
    
    /**
     * Cập nhật font size cho menu items
     */
    private void updateMenuItemFontSize(JMenu menu, int fontSize) {
        for (int i = 0; i < menu.getItemCount(); i++) {
            JMenuItem item = menu.getItem(i);
            if (item != null) {
                item.setFont(new Font("Arial", Font.PLAIN, Math.max(10, fontSize)));
            }
        }
    }
}
