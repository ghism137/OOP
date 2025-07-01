package QuanLyKyThi;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service xử lý xác thực người dùng
 */
public class AuthenticationService {
    private XMLDatabase database;
    private User currentUser;
    
    public AuthenticationService() {
        this.database = new XMLDatabase();
        this.currentUser = null;
    }
    
    /**
     * Đăng nhập người dùng
     */
    public boolean login(String username, String password) {
        List<User> users = database.loadUsers();
        
        for (User user : users) {
            if (user.getUsername().equals(username) && 
                user.getPassword().equals(password) && 
                user.isActive()) {
                
                // Cập nhật thời gian đăng nhập cuối
                user.setLastLogin(LocalDateTime.now());
                this.currentUser = user;
                
                // Lưu lại thông tin đăng nhập
                database.saveUsers(users);
                
                System.out.println("✅ Đăng nhập thành công: " + user.getHoTen() + " (" + user.getRole() + ")");
                return true;
            }
        }
        
        System.out.println("❌ Đăng nhập thất bại: Sai username/password hoặc tài khoản bị khóa");
        return false;
    }
    
    /**
     * Đăng xuất người dùng
     */
    public void logout() {
        if (currentUser != null) {
            System.out.println("👋 Đăng xuất: " + currentUser.getHoTen());
            currentUser = null;
        }
    }
    
    /**
     * Kiểm tra xem đã đăng nhập chưa
     */
    public boolean isLoggedIn() {
        return currentUser != null;
    }
    
    /**
     * Lấy thông tin user hiện tại
     */
    public User getCurrentUser() {
        return currentUser;
    }
    
    /**
     * Kiểm tra quyền admin
     */
    public boolean isAdmin() {
        return currentUser != null && "admin".equals(currentUser.getRole());
    }
    
    /**
     * Kiểm tra quyền giáo vụ
     */
    public boolean isGiaoVu() {
        return currentUser != null && 
               ("admin".equals(currentUser.getRole()) || "giaovu".equals(currentUser.getRole()));
    }
    
    /**
     * Kiểm tra quyền truy cập chức năng
     */
    public boolean hasPermission(String feature) {
        if (currentUser == null) return false;
        
        String role = currentUser.getRole();
        
        switch (feature) {
            case "MANAGE_USERS":
                return "admin".equals(role);
                
            case "MANAGE_KYTHI":
            case "MANAGE_GIAMTHI":
            case "NHAP_DIEM":
                return "admin".equals(role) || "giaovu".equals(role);
                
            case "VIEW_KYTHI":
            case "DANGKY_THI":
            case "VIEW_KETQUA":
                return true; // Tất cả user đều có quyền
                
            default:
                return false;
        }
    }
    
    /**
     * Đổi mật khẩu
     */
    public boolean changePassword(String oldPassword, String newPassword) {
        if (currentUser == null) return false;
        
        if (!currentUser.getPassword().equals(oldPassword)) {
            System.out.println("❌ Mật khẩu cũ không chính xác");
            return false;
        }
        
        currentUser.setPassword(newPassword);
        
        // Cập nhật vào database
        List<User> users = database.loadUsers();
        for (User user : users) {
            if (user.getUsername().equals(currentUser.getUsername())) {
                user.setPassword(newPassword);
                break;
            }
        }
        database.saveUsers(users);
        
        System.out.println("✅ Đổi mật khẩu thành công");
        return true;
    }
    
    /**
     * Đăng ký user mới (chỉ admin)
     */
    public boolean registerUser(String username, String password, String hoTen, 
                               String email, String role) {
        if (!isAdmin()) {
            System.out.println("❌ Chỉ admin mới có quyền tạo user");
            return false;
        }
        
        List<User> users = database.loadUsers();
        
        // Kiểm tra username đã tồn tại
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                System.out.println("❌ Username đã tồn tại");
                return false;
            }
        }
        
        // Tạo user mới
        User newUser = new User(username, password, hoTen, email, role);
        users.add(newUser);
        database.saveUsers(users);
        
        System.out.println("✅ Tạo user thành công: " + hoTen);
        return true;
    }
    
    /**
     * Lấy danh sách tất cả users (chỉ admin)
     */
    public List<User> getAllUsers() {
        if (!isAdmin()) {
            System.out.println("❌ Chỉ admin mới có quyền xem danh sách user");
            return null;
        }
        
        return database.loadUsers();
    }
}
