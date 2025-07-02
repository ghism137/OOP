package QuanLyKyThi;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Form quản lý tài khoản người dùng - Dành cho Admin
 */
public class UserManagementForm extends JFrame {
    private XMLDatabase database;
    private User currentUser;
    
    // Components
    private JTable userTable;
    private DefaultTableModel tableModel;
    private JButton btnAdd, btnEdit, btnDelete, btnRefresh, btnActivate, btnDeactivate;
    private JTextField txtSearch;
    private JComboBox<String> cbRoleFilter;
    
    public UserManagementForm(User currentUser) {
        this.currentUser = currentUser;
        this.database = new XMLDatabase();
        
        initComponents();
        setupLayout();
        loadUserData();
        setupEventHandlers();
        
        setTitle("Quản lý Tài khoản Người dùng");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        // Table
        String[] columnNames = {"Username", "Họ tên", "Email", "Vai trò", "Trạng thái", "Lần đăng nhập cuối", "Ngày tạo"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        userTable = new JTable(tableModel);
        userTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        userTable.getTableHeader().setReorderingAllowed(false);
        
        // Set column widths
        userTable.getColumnModel().getColumn(0).setPreferredWidth(100); // Username
        userTable.getColumnModel().getColumn(1).setPreferredWidth(150); // Họ tên
        userTable.getColumnModel().getColumn(2).setPreferredWidth(200); // Email
        userTable.getColumnModel().getColumn(3).setPreferredWidth(80);  // Vai trò
        userTable.getColumnModel().getColumn(4).setPreferredWidth(80);  // Trạng thái
        userTable.getColumnModel().getColumn(5).setPreferredWidth(120); // Lần đăng nhập cuối
        userTable.getColumnModel().getColumn(6).setPreferredWidth(120); // Ngày tạo
        
        // Buttons
        btnAdd = new JButton("Thêm mới");
        btnAdd.setBackground(new Color(40, 167, 69));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFocusPainted(false);
        
        btnEdit = new JButton("Chỉnh sửa");
        btnEdit.setBackground(new Color(0, 123, 255));
        btnEdit.setForeground(Color.WHITE);
        btnEdit.setFocusPainted(false);
        
        btnDelete = new JButton("Xóa");
        btnDelete.setBackground(new Color(220, 53, 69));
        btnDelete.setForeground(Color.WHITE);
        btnDelete.setFocusPainted(false);
        
        btnActivate = new JButton("Kích hoạt");
        btnActivate.setBackground(new Color(40, 167, 69));
        btnActivate.setForeground(Color.WHITE);
        btnActivate.setFocusPainted(false);
        
        btnDeactivate = new JButton("Vô hiệu hóa");
        btnDeactivate.setBackground(new Color(255, 193, 7));
        btnDeactivate.setForeground(Color.BLACK);
        btnDeactivate.setFocusPainted(false);
        
        btnRefresh = new JButton("Làm mới");
        btnRefresh.setBackground(new Color(108, 117, 125));
        btnRefresh.setForeground(Color.WHITE);
        btnRefresh.setFocusPainted(false);
        
        // Search and filter
        txtSearch = new JTextField();
        txtSearch.setPreferredSize(new Dimension(200, 25));
        
        cbRoleFilter = new JComboBox<>(new String[]{"Tất cả", "admin", "giaovu", "giamthi", "user"});
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Title panel
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel titleLabel = new JLabel("QUẢN LÝ TÀI KHOẢN NGƯỜI DÙNG");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(new Color(0, 123, 255));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);
        
        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        searchPanel.add(new JLabel("Tìm kiếm:"));
        searchPanel.add(txtSearch);
        searchPanel.add(new JLabel("Lọc theo vai trò:"));
        searchPanel.add(cbRoleFilter);
        searchPanel.add(btnRefresh);
        
        add(searchPanel, BorderLayout.NORTH);
        
        // Table panel
        JScrollPane scrollPane = new JScrollPane(userTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Danh sách tài khoản"));
        add(scrollPane, BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnActivate);
        buttonPanel.add(btnDeactivate);
        
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void loadUserData() {
        try {
            List<User> users = database.getAllUsers();
            tableModel.setRowCount(0); // Clear existing data
            
            String roleFilter = (String) cbRoleFilter.getSelectedItem();
            String searchText = txtSearch.getText().toLowerCase();
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            
            for (User user : users) {
                // Apply filters
                if (!"Tất cả".equals(roleFilter) && !user.getRole().equals(roleFilter)) {
                    continue;
                }
                
                if (!searchText.isEmpty() && 
                    !user.getUsername().toLowerCase().contains(searchText) &&
                    !user.getHoTen().toLowerCase().contains(searchText) &&
                    !user.getEmail().toLowerCase().contains(searchText)) {
                    continue;
                }
                
                Object[] row = {
                    user.getUsername(),
                    user.getHoTen() != null ? user.getHoTen() : "",
                    user.getEmail() != null ? user.getEmail() : "",
                    getRoleDisplayText(user.getRole()),
                    getStatusText(user),
                    user.getLastLogin() != null ? user.getLastLogin().format(formatter) : "Chưa đăng nhập",
                    user.getCreatedDate() != null ? user.getCreatedDate().format(formatter) : "Không xác định"
                };
                tableModel.addRow(row);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu: " + e.getMessage(), 
                "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private String getRoleDisplayText(String role) {
        switch (role.toLowerCase()) {
            case "admin": return "Quản trị viên";
            case "giaovu": return "Giáo vụ";
            case "giamthi": return "Giám thị";
            case "user": return "Người dùng";
            default: return role;
        }
    }
    
    private String getStatusText(User user) {
        if (user.isPending()) {
            return "Chờ duyệt";
        } else if (user.isActive()) {
            return "Hoạt động";
        } else {
            return "Bị khóa";
        }
    }
    
    private void setupEventHandlers() {
        btnAdd.addActionListener(e -> openAddUserForm());
        btnEdit.addActionListener(e -> openEditUserForm());
        btnDelete.addActionListener(e -> deleteUser());
        btnActivate.addActionListener(e -> changeUserStatus(true));
        btnDeactivate.addActionListener(e -> changeUserStatus(false));
        btnRefresh.addActionListener(e -> loadUserData());
        
        // Search functionality
        txtSearch.addActionListener(e -> loadUserData());
        cbRoleFilter.addActionListener(e -> loadUserData());
    }
    
    private void openAddUserForm() {
        RegisterForm registerForm = new RegisterForm(this);
        registerForm.setVisible(true);
    }
    
    private void openEditUserForm() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một tài khoản để chỉnh sửa!", 
                "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String username = (String) tableModel.getValueAt(selectedRow, 0);
        User user = database.findUserByUsername(username);
        
        if (user != null) {
            AccountInfoForm accountForm = new AccountInfoForm(user);
            accountForm.setVisible(true);
        }
    }
    
    private void deleteUser() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một tài khoản để xóa!", 
                "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String username = (String) tableModel.getValueAt(selectedRow, 0);
        
        // Không cho phép xóa tài khoản admin đầu tiên
        if ("admin".equals(username)) {
            JOptionPane.showMessageDialog(this, "Không thể xóa tài khoản admin chính!", 
                "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int result = JOptionPane.showConfirmDialog(this, 
            "Bạn có chắc chắn muốn xóa tài khoản '" + username + "'?", 
            "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        
        if (result == JOptionPane.YES_OPTION) {
            try {
                database.deleteUser(username);
                loadUserData();
                JOptionPane.showMessageDialog(this, "Xóa tài khoản thành công!", 
                    "Thành công", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lỗi khi xóa tài khoản: " + e.getMessage(), 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void changeUserStatus(boolean activate) {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một tài khoản!", 
                "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String username = (String) tableModel.getValueAt(selectedRow, 0);
        User user = database.findUserByUsername(username);
        
        if (user != null) {
            try {
                user.setActive(activate);
                database.updateUser(user);
                loadUserData();
                
                String action = activate ? "kích hoạt" : "vô hiệu hóa";
                JOptionPane.showMessageDialog(this, "Đã " + action + " tài khoản thành công!", 
                    "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, 
                    "Lỗi khi thay đổi trạng thái tài khoản: " + e.getMessage(), 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    // Method để refresh data sau khi thêm user mới
    public void refreshData() {
        loadUserData();
    }
    
    // Main method for testing
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Test with admin user
            User adminUser = new User("admin", "admin123", "Administrator", 
                "admin@example.com", "admin");
            
            UserManagementForm form = new UserManagementForm(adminUser);
            form.setVisible(true);
        });
    }
}
