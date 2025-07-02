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
    
    public MainGUIWithAuth(AuthenticationService authService) {
        this.authService = authService;
        this.database = new XMLDatabase();
        
        loadDataFromXML();
        initComponents();
        setupLayout();
        updateUIForUserRole();
        setVisible(true);
    }
    
    private void loadDataFromXML() {
        // Load dữ liệu từ XML
        danhSachKyThi = database.loadKyThi();
        danhSachThiSinh = database.loadThiSinh();
        danhSachGiamThi = database.loadGiamThi();
        
        // Nếu chưa có dữ liệu thì tạo mẫu
        if (danhSachKyThi.isEmpty()) {
            createSampleData();
            saveDataToXML();
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
            statusLabel.setText("Đã lưu dữ liệu thành công vào XML files | " + 
                               java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss")));
            JOptionPane.showMessageDialog(this, "Lưu dữ liệu thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi lưu dữ liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void initComponents() {
        User currentUser = authService.getCurrentUser();
        setTitle("Hệ Thống Quản Lý Kỳ Thi - " + currentUser.getHoTen() + " (" + currentUser.getRole() + ")");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        // Window closing event
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                handleExit();
            }
        });
        
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
        JMenuItem itemQuanLyTrangThai = new JMenuItem("Quản Lý Trạng Thái Bài Thi");
        JMenuItem itemNhapDiem = new JMenuItem("Nhập Điểm");
        JMenuItem itemXemKetQua = new JMenuItem("Xem Kết Quả");
        JMenuItem itemThongKe = new JMenuItem("Thống Kê");
        menuKetQua.add(itemQuanLyTrangThai);
        menuKetQua.add(itemNhapDiem);
        menuKetQua.add(itemXemKetQua);
        menuKetQua.add(itemThongKe);
        
        // Menu System
        menuSystem = new JMenu("Hệ Thống");
        JMenuItem itemProfile = new JMenuItem("Thông Tin Tài Khoản");
        JMenuItem itemChangePassword = new JMenuItem("Đổi Mật Khẩu");
        JMenuItem itemUserManagement = new JMenuItem("Quản Lý Tài Khoản");
        JMenuItem itemRegister = new JMenuItem("Đăng Ký Tài Khoản Mới");
        JMenuItem itemSaveData = new JMenuItem("Lưu Dữ Liệu");
        JMenuItem itemLoadData = new JMenuItem("Tải Lại Dữ Liệu");
        JMenuItem itemLogout = new JMenuItem("Đăng Xuất");
        JMenuItem itemExit = new JMenuItem("Thoát");
        
        menuSystem.add(itemProfile);
        menuSystem.add(itemChangePassword);
        menuSystem.addSeparator();
        
        // Chỉ admin mới có quyền quản lý tài khoản
        if (authService.isAdmin()) {
            menuSystem.add(itemUserManagement);
            menuSystem.add(itemRegister);
            menuSystem.addSeparator();
        }
        
        menuSystem.add(itemSaveData);
        menuSystem.add(itemLoadData);
        menuSystem.addSeparator();
        menuSystem.add(itemLogout);
        menuSystem.add(itemExit);
        
        // Thêm menu vào menu bar
        menuBar.add(menuKyThi);
        menuBar.add(menuThiSinh);
        menuBar.add(menuGiamThi);
        menuBar.add(menuKetQua);
        menuBar.add(menuSystem);
        
        setJMenuBar(menuBar);
        
        // Desktop pane cho MDI
        desktopPane = new JDesktopPane();
        desktopPane.setBackground(new Color(240, 240, 240));
        
        // Event handlers
        setupEventHandlers(itemDSKyThi, itemThemKyThi, itemDSThiSinh, itemThemThiSinh, 
                          itemDangKyThi, itemDSGiamThi, itemThemGiamThi, itemPhanCong,
                          itemQuanLyTrangThai, itemNhapDiem, itemXemKetQua, itemThongKe,
                          itemProfile, itemChangePassword, itemUserManagement, itemRegister,
                          itemSaveData, itemLoadData, itemLogout, itemExit);
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
        
        // System
        items[12].addActionListener(e -> showUserProfile());
        items[13].addActionListener(e -> showChangePasswordDialog());
        items[14].addActionListener(e -> openUserManagementForm());
        items[15].addActionListener(e -> openRegisterForm());
        items[16].addActionListener(e -> saveDataToXML());
        items[17].addActionListener(e -> loadDataFromXML());
        items[18].addActionListener(e -> handleLogout());
        items[19].addActionListener(e -> handleExit());
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
                        new MainGUIWithAuth(authService);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null,
                            "Lỗi khởi tạo hệ thống: " + e.getMessage(),
                            "Lỗi hệ thống", JOptionPane.ERROR_MESSAGE);
                        e.printStackTrace();
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
    
}
