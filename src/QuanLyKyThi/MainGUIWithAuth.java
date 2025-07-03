package QuanLyKyThi;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Giao diện chính với tích hợp authentication và XML database
 */
public class MainGUIWithAuth extends JFrame {
    
    // Services và dữ liệu
    private AuthenticationService authService;
    private XMLDatabase database;
    private List<KyThi> danhSachKyThi;
    private List<ThiSinh> danhSachThiSinh;
    private List<GiamThi> danhSachGiamThi;
    
    // Components
    private JMenuBar menuBar;
    private JMenu menuKyThi, menuThiSinh, menuGiamThi, menuKetQua, menuSystem;
    private JDesktopPane desktopPane;
    private JLabel statusLabel;
    
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
    public MainGUIWithAuth(AuthenticationService authService) {
        this(authService, SIZE_MEDIUM[0], SIZE_MEDIUM[1]);
    }
    
    /**
     * Constructor với kích thước tùy chỉnh
     */
    public MainGUIWithAuth(AuthenticationService authService, int width, int height) {
        try {
            this.authService = authService;
            this.database = new XMLDatabase();
            
            System.out.println("MainGUIWithAuth - Bắt đầu khởi tạo giao diện...");
            
            // Khởi tạo UI với kích thước tùy chỉnh
            initComponentsWithSize(width, height);
            setupLayout();
            updateUIForUserRole();
            
            // Sau đó load dữ liệu (có thể gọi saveDataToXML)
            loadDataFromXML();
            
            System.out.println("MainGUIWithAuth - Khởi tạo thành công, hiển thị giao diện...");
            
            // Đảm bảo giao diện được hiển thị
            setVisible(true);
            toFront();
            requestFocus();
            
        } catch (Exception e) {
            System.err.println("Lỗi khi khởi tạo MainGUIWithAuth: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, 
                "Lỗi khi khởi tạo giao diện chính: " + e.getMessage(), 
                "Lỗi", JOptionPane.ERROR_MESSAGE);
            throw e; // Re-throw để caller biết có lỗi
        }
    }
    
    /**
     * Tạo MainGUIWithAuth với preset kích thước
     */
    public static MainGUIWithAuth createWithSize(AuthenticationService authService, int[] sizePreset) {
        return new MainGUIWithAuth(authService, sizePreset[0], sizePreset[1]);
    }
    
    private void loadDataFromXML() {
        try {
            // Load dữ liệu từ XML
            danhSachKyThi = database.loadKyThi();
            danhSachThiSinh = database.loadThiSinh();
            danhSachGiamThi = database.loadGiamThi();
            
            System.out.println("MainGUIWithAuth - Loaded data:");
            System.out.println("  - Kỳ thi: " + danhSachKyThi.size());
            System.out.println("  - Thí sinh: " + danhSachThiSinh.size()); 
            System.out.println("  - Giám thị: " + danhSachGiamThi.size());
            
            // Nếu chưa có dữ liệu thì tạo mẫu
            if (danhSachKyThi.isEmpty()) {
                System.out.println("MainGUIWithAuth - Creating sample data...");
                createSampleData();
                saveDataToXML();
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi load dữ liệu từ XML: " + e.getMessage());
            e.printStackTrace();
            
            // Khởi tạo dữ liệu trống để tránh NullPointerException
            if (danhSachKyThi == null) danhSachKyThi = new ArrayList<>();
            if (danhSachThiSinh == null) danhSachThiSinh = new ArrayList<>();
            if (danhSachGiamThi == null) danhSachGiamThi = new ArrayList<>();
        }
    }
    
    private void createSampleData() {
        // Tạo kỳ thi mẫu
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
    
    private void saveDataToXML() {
        try {
            database.saveKyThi(danhSachKyThi);
            database.saveThiSinh(danhSachThiSinh);  
            database.saveGiamThi(danhSachGiamThi);
            
            // Kiểm tra xem statusLabel đã được khởi tạo chưa
            if (statusLabel != null) {
                statusLabel.setText("Đã lưu dữ liệu thành công vào XML files | " + 
                                   java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss")));
            }
            
            // Chỉ hiển thị dialog nếu frame đã hiển thị (tránh popup khi khởi tạo)
            if (isVisible()) {
                JOptionPane.showMessageDialog(this, "Lưu dữ liệu thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            // Log lỗi chi tiết để debug
            System.err.println("Lỗi khi lưu dữ liệu: " + e.getMessage());
            e.printStackTrace();
            
            // Chỉ hiển thị dialog lỗi nếu frame đã hiển thị
            if (isVisible()) {
                JOptionPane.showMessageDialog(this, "Lỗi khi lưu dữ liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Khởi tạo components với kích thước tùy chỉnh
     */
    private void initComponentsWithSize(int width, int height) {
        User currentUser = authService.getCurrentUser();
        setTitle("Hệ Thống Quản Lý Kỳ Thi - " + currentUser.getHoTen() + " (" + currentUser.getRole() + ")");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
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
        
        // Thêm thông báo về tính năng resize
        System.out.println("MainGUIWithAuth - Có thể kéo thả để thay đổi kích thước. Kích thước hiện tại: " + width + "×" + height);
        
        // Window closing event
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                handleExit();
            }
        });
        
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
        // Tính toán font size dựa trên kích thước
        int menuFontSize = Math.max(12, width / 120);
        int itemFontSize = Math.max(10, width / 140);
        
        menuBar = new JMenuBar();
        
        // Menu Kỳ Thi
        menuKyThi = new JMenu("Kỳ Thi");
        menuKyThi.setFont(new Font("Arial", Font.PLAIN, menuFontSize));
        JMenuItem itemDSKyThi = new JMenuItem("Danh Sách Kỳ Thi");
        itemDSKyThi.setFont(new Font("Arial", Font.PLAIN, itemFontSize));
        JMenuItem itemThemKyThi = new JMenuItem("Thêm Kỳ Thi");
        itemThemKyThi.setFont(new Font("Arial", Font.PLAIN, itemFontSize));
        menuKyThi.add(itemDSKyThi);
        menuKyThi.add(itemThemKyThi);
        
        // Menu Thí Sinh
        menuThiSinh = new JMenu("Thí Sinh");
        menuThiSinh.setFont(new Font("Arial", Font.PLAIN, menuFontSize));
        JMenuItem itemDSThiSinh = new JMenuItem("Danh Sách Thí Sinh");
        itemDSThiSinh.setFont(new Font("Arial", Font.PLAIN, itemFontSize));
        JMenuItem itemThemThiSinh = new JMenuItem("Thêm Thí Sinh");
        itemThemThiSinh.setFont(new Font("Arial", Font.PLAIN, itemFontSize));
        JMenuItem itemDangKyThi = new JMenuItem("Đăng Ký Thi");
        itemDangKyThi.setFont(new Font("Arial", Font.PLAIN, itemFontSize));
        menuThiSinh.add(itemDSThiSinh);
        menuThiSinh.add(itemThemThiSinh);
        menuThiSinh.add(itemDangKyThi);
        
        // Menu Giám Thị
        menuGiamThi = new JMenu("Giám Thị");
        menuGiamThi.setFont(new Font("Arial", Font.PLAIN, menuFontSize));
        JMenuItem itemDSGiamThi = new JMenuItem("Danh Sách Giám Thị");
        itemDSGiamThi.setFont(new Font("Arial", Font.PLAIN, itemFontSize));
        JMenuItem itemThemGiamThi = new JMenuItem("Thêm Giám Thị");
        itemThemGiamThi.setFont(new Font("Arial", Font.PLAIN, itemFontSize));
        JMenuItem itemPhanCong = new JMenuItem("Phân Công Giám Thị");
        itemPhanCong.setFont(new Font("Arial", Font.PLAIN, itemFontSize));
        menuGiamThi.add(itemDSGiamThi);
        menuGiamThi.add(itemThemGiamThi);
        menuGiamThi.add(itemPhanCong);
        
        // Menu Kết Quả
        menuKetQua = new JMenu("Kết Quả");
        menuKetQua.setFont(new Font("Arial", Font.PLAIN, menuFontSize));
        JMenuItem itemQuanLyTrangThai = new JMenuItem("Quản Lý Trạng Thái Bài Thi");
        itemQuanLyTrangThai.setFont(new Font("Arial", Font.PLAIN, itemFontSize));
        JMenuItem itemNhapDiem = new JMenuItem("Nhập Điểm");
        itemNhapDiem.setFont(new Font("Arial", Font.PLAIN, itemFontSize));
        JMenuItem itemXemKetQua = new JMenuItem("Xem Kết Quả");
        itemXemKetQua.setFont(new Font("Arial", Font.PLAIN, itemFontSize));
        JMenuItem itemThongKe = new JMenuItem("Thống Kê");
        itemThongKe.setFont(new Font("Arial", Font.PLAIN, itemFontSize));
        menuKetQua.add(itemQuanLyTrangThai);
        menuKetQua.add(itemNhapDiem);
        menuKetQua.add(itemXemKetQua);
        menuKetQua.add(itemThongKe);
        
        // Menu System
        menuSystem = new JMenu("Hệ Thống");
        menuSystem.setFont(new Font("Arial", Font.PLAIN, menuFontSize));
        JMenuItem itemProfile = new JMenuItem("Thông Tin Tài Khoản");
        itemProfile.setFont(new Font("Arial", Font.PLAIN, itemFontSize));
        JMenuItem itemLogout = new JMenuItem("Đăng Xuất");
        itemLogout.setFont(new Font("Arial", Font.PLAIN, itemFontSize));
        JMenuItem itemExit = new JMenuItem("Thoát");
        itemExit.setFont(new Font("Arial", Font.PLAIN, itemFontSize));
        menuSystem.add(itemProfile);
        menuSystem.addSeparator();
        menuSystem.add(itemLogout);
        menuSystem.add(itemExit);
        
        // Thêm menus vào menubar
        menuBar.add(menuKyThi);
        menuBar.add(menuThiSinh);
        menuBar.add(menuGiamThi);
        menuBar.add(menuKetQua);
        menuBar.add(menuSystem);
        
        setJMenuBar(menuBar);
        
        // Setup event handlers
        setupEventHandlers(itemDSKyThi, itemThemKyThi, itemDSThiSinh, itemThemThiSinh, 
                          itemDangKyThi, itemDSGiamThi, itemThemGiamThi, itemPhanCong,
                          itemQuanLyTrangThai, itemNhapDiem, itemXemKetQua, itemThongKe,
                          itemProfile, itemLogout, itemExit);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        add(desktopPane, BorderLayout.CENTER);
        
        // Status bar
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusPanel.setBorder(BorderFactory.createLoweredBevelBorder());
        User user = authService.getCurrentUser();
        statusLabel = new JLabel("Đăng nhập: " + user.getHoTen() + " | Quyền: " + user.getRole() + " | Sẵn sàng");
        statusPanel.add(statusLabel);
        add(statusPanel, BorderLayout.SOUTH);
    }
    
    private void updateUIForUserRole() {
        // Cập nhật giao diện theo quyền của user
        boolean isGiaoVu = authService.isGiaoVu();
        
        // Chỉ admin và giáo vụ mới được thêm/sửa kỳ thi
        for (int i = 0; i < menuKyThi.getItemCount(); i++) {
            JMenuItem item = menuKyThi.getItem(i);
            if (item != null && item.getText().contains("Thêm")) {
                item.setEnabled(isGiaoVu);
            }
        }
        
        // Chỉ admin và giáo vụ mới được quản lý giám thị
        menuGiamThi.setEnabled(isGiaoVu);
        
        // Chỉ admin và giáo vụ mới được nhập điểm
        for (int i = 0; i < menuKetQua.getItemCount(); i++) {
            JMenuItem item = menuKetQua.getItem(i);
            if (item != null && item.getText().contains("Nhập Điểm")) {
                item.setEnabled(isGiaoVu);
            }
        }
    }
    
    private void setupEventHandlers(JMenuItem... items) {
        try {
            // Đảm bảo có đủ items để tránh IndexOutOfBoundsException
            if (items.length < 15) {
                System.err.println("setupEventHandlers - Không đủ menu items: " + items.length);
                return;
            }
            
            // Kỳ thi
            items[0].addActionListener(e -> openKyThiListForm());
            items[1].addActionListener(e -> openAddKyThiForm());
            
            // Thí sinh  
            items[2].addActionListener(e -> openThiSinhListForm());
            items[3].addActionListener(e -> openAddThiSinhForm());
            items[4].addActionListener(e -> openDangKyThiForm());
            
            // Giám thị
            items[5].addActionListener(e -> openGiamThiListForm());
            items[6].addActionListener(e -> openAddGiamThiForm());
            items[7].addActionListener(e -> openPhanCongForm());
            
            // Kết quả
            items[8].addActionListener(e -> openQuanLyTrangThaiBaiThiForm());
            items[9].addActionListener(e -> openNhapDiemForm());
            items[10].addActionListener(e -> openXemKetQuaForm());
            items[11].addActionListener(e -> openThongKeForm());
            
            // System (chỉ có 3 items: Profile, Logout, Exit)
            items[12].addActionListener(e -> showUserProfile());
            items[13].addActionListener(e -> handleLogout());
            items[14].addActionListener(e -> handleExit());
            
            System.out.println("setupEventHandlers - Đã thiết lập " + items.length + " event handlers");
            
        } catch (Exception e) {
            System.err.println("Lỗi trong setupEventHandlers: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // Methods để mở các form (giống MainGUI cũ)
    private void openKyThiListForm() {
        KyThiListForm form = new KyThiListForm(danhSachKyThi);
        addInternalFrame(form, "Danh Sách Kỳ Thi");
    }
    
    private void openAddKyThiForm() {
        if (!authService.hasPermission("MANAGE_KYTHI")) {
            JOptionPane.showMessageDialog(this, "Bạn không có quyền thực hiện chức năng này!", 
                                        "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
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
    
    private void openQuanLyTrangThaiBaiThiForm() {
        // Mở form quản lý trạng thái bài thi với quyền phù hợp
        QuanLyTrangThaiBaiThiForm form = new QuanLyTrangThaiBaiThiForm(authService.getCurrentUser());
        
        // Không sử dụng MDI để form này có thể hoạt động độc lập
        form.setVisible(true);
    }
    
    private void openNhapDiemForm() {
        if (!authService.hasPermission("NHAP_DIEM")) {
            JOptionPane.showMessageDialog(this, "Bạn không có quyền nhập điểm!", 
                                        "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
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
    
    /**
     * Hiển thị thông tin tài khoản người dùng
     */
    private void showUserProfile() {
        User currentUser = authService.getCurrentUser();
        AccountInfoForm accountForm = new AccountInfoForm(currentUser);
        accountForm.setVisible(true);
    }

    /**
     * Hiển thị form đổi mật khẩu
     */
    private void showChangePasswordDialog() {
        User currentUser = authService.getCurrentUser();
        ChangePasswordForm changePasswordForm = new ChangePasswordForm(currentUser);
        changePasswordForm.setVisible(true);
    }

    /**
     * Mở form quản lý tài khoản người dùng (chỉ admin)
     */
    private void openUserManagementForm() {
        if (!authService.isAdmin()) {
            JOptionPane.showMessageDialog(this, 
                "Chỉ quản trị viên mới có quyền quản lý tài khoản người dùng!", 
                "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        UserManagementForm userManagementForm = new UserManagementForm(authService.getCurrentUser());
        userManagementForm.setVisible(true);
    }

    /**
     * Mở form đăng ký tài khoản mới
     */
    private void openRegisterForm() {
        RegisterForm registerForm = new RegisterForm();
        registerForm.setVisible(true);
    }

    /**
     * Xử lý đăng xuất
     */
    private void handleLogout() {
        int choice = JOptionPane.showConfirmDialog(this,
            "Bạn có chắc chắn muốn đăng xuất?",
            "Xác nhận đăng xuất",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
        
        if (choice == JOptionPane.YES_OPTION) {
            // Lưu dữ liệu trước khi đăng xuất
            saveDataToXML();
            
            // Đóng cửa sổ hiện tại
            dispose();
            
            // Quay lại màn hình đăng nhập
            startLoginProcess();
        }
    }

    /**
     * Xử lý thoát ứng dụng
     */
    private void handleExit() {
        int choice = JOptionPane.showConfirmDialog(this,
            "Bạn có chắc chắn muốn thoát ứng dụng?",
            "Xác nhận thoát",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
        
        if (choice == JOptionPane.YES_OPTION) {
            // Lưu dữ liệu trước khi thoát
            saveDataToXML();
            
            JOptionPane.showMessageDialog(this,
                "Cảm ơn bạn đã sử dụng Hệ Thống Quản Lý Kỳ Thi!",
                "Tạm biệt", JOptionPane.INFORMATION_MESSAGE);
            
            System.exit(0);
        }
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
    
    // Getter methods
    public List<KyThi> getDanhSachKyThi() { return danhSachKyThi; }
    public List<ThiSinh> getDanhSachThiSinh() { return danhSachThiSinh; }
    public List<GiamThi> getDanhSachGiamThi() { return danhSachGiamThi; }
    public AuthenticationService getAuthService() { return authService; }
    
    /**
     * Main method - Entry point chính của ứng dụng
     * Bắt buộc đăng nhập trước khi truy cập hệ thống quản lý
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Set Look and Feel
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            // Hiển thị màn hình chào mừng
            showWelcomeMessage();
            
            // Bắt đầu quá trình đăng nhập
            startLoginProcess();
        });
    }
    
    /**
     * Hiển thị màn hình chào mừng
     */
    private static void showWelcomeMessage() {
        String message = "Chào mừng đến với Hệ Thống Quản Lý Kỳ Thi!\n\n" +
                        "Phiên bản: 2.0\n" +
                        "Tính năng:\n" +
                        "• Quản lý Kỳ thi, Thí sinh, Giám thị\n" +
                        "• Quản lý trạng thái bài thi và nhập điểm\n" +
                        "• Phân quyền người dùng\n" +
                        "• Báo cáo và thống kê\n\n" +
                        "Bạn cần đăng nhập để tiếp tục.";
        
        JOptionPane.showMessageDialog(null, message, 
            "Hệ Thống Quản Lý Kỳ Thi", JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Bắt đầu quá trình đăng nhập
     */
    private static void startLoginProcess() {
        // Tạo LoginForm với callback
        LoginForm loginForm = new LoginForm(
            // Callback khi đăng nhập thành công
            (authService) -> {
                SwingUtilities.invokeLater(() -> {
                    User currentUser = authService.getCurrentUser();
                    
                    // Hiển thị thông báo chào mừng
                    JOptionPane.showMessageDialog(null,
                        "Đăng nhập thành công!\n\n" +
                        "Chào mừng: " + currentUser.getHoTen() + "\n" +
                        "Quyền: " + currentUser.getRole() + "\n" +
                        "Email: " + currentUser.getEmail(),
                        "Chào mừng", JOptionPane.INFORMATION_MESSAGE);
                    
                    try {
                        // Khởi tạo MainGUIWithAuth
                        System.out.println("startLoginProcess - Bắt đầu tạo MainGUIWithAuth...");
                        MainGUIWithAuth mainGUI = new MainGUIWithAuth(authService);
                        System.out.println("startLoginProcess - MainGUIWithAuth đã được tạo và hiển thị thành công");
                        
                        // Đảm bảo cửa sổ được đưa lên trên cùng
                        mainGUI.toFront();
                        mainGUI.requestFocus();
                        
                    } catch (Exception e) {
                        System.err.println("startLoginProcess - Lỗi khởi tạo MainGUIWithAuth: " + e.getMessage());
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(null,
                            "Lỗi khởi tạo hệ thống: " + e.getMessage(),
                            "Lỗi hệ thống", JOptionPane.ERROR_MESSAGE);
                        System.exit(1);
                    }
                });
            },
            // Callback khi hủy đăng nhập
            () -> {
                int choice = JOptionPane.showConfirmDialog(null,
                    "Bạn đã hủy đăng nhập.\n" +
                    "Chọn 'Có' để thử lại, 'Không' để thoát.",
                    "Hủy đăng nhập",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);
                
                if (choice == JOptionPane.YES_OPTION) {
                    startLoginProcess(); // Thử lại
                } else {
                    JOptionPane.showMessageDialog(null,
                        "Cảm ơn bạn đã sử dụng Hệ Thống Quản Lý Kỳ Thi!",
                        "Tạm biệt", JOptionPane.INFORMATION_MESSAGE);
                    System.exit(0);
                }
            }
        );
        
        // Thiết lập xử lý đóng cửa sổ
        loginForm.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        loginForm.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                int confirm = JOptionPane.showConfirmDialog(loginForm,
                    "Bạn có chắc chắn muốn thoát chương trình?",
                    "Xác nhận thoát", JOptionPane.YES_NO_OPTION);
                
                if (confirm == JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(null,
                        "Cảm ơn bạn đã sử dụng Hệ Thống Quản Lý Kỳ Thi!",
                        "Tạm biệt", JOptionPane.INFORMATION_MESSAGE);
                    System.exit(0);
                }
            }
        });
        
        loginForm.setVisible(true);
    }
    
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
                System.out.println("MainGUIWithAuth - Kích thước mới: " + newWidth + "x" + newHeight);
                
                // Cập nhật font size cho menu và status bar
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
        int statusFontSize = Math.max(11, windowWidth / 130);
        
        // Cập nhật font cho menu bar
        updateMenuFontSize(menuBar, menuFontSize);
        
        // Cập nhật font cho status bar
        if (statusLabel != null) {
            statusLabel.setFont(new Font("Arial", Font.PLAIN, statusFontSize));
        }
        
        // Cập nhật kích thước tối thiểu cho desktop pane nếu cần
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
