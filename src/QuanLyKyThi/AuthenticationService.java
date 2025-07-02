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
     * Đăng ký tài khoản mới
     */
    public boolean register(String username, String password, String hoTen, String email, String role) {
        try {
            List<User> users = database.loadUsers();
            
            // Kiểm tra username đã tồn tại
            for (User user : users) {
                if (user.getUsername().equals(username)) {
                    System.out.println("❌ Username đã tồn tại");
                    return false;
                }
            }
            
            // Kiểm tra email đã tồn tại
            for (User user : users) {
                if (user.getEmail().equals(email)) {
                    System.out.println("❌ Email đã được sử dụng");
                    return false;
                }
            }
            
            // Tạo user mới
            User newUser = new User(username, password, hoTen, email, role);
            
            // Nếu role là admin thì cần duyệt, các role khác được active luôn
            if ("admin".equals(role)) {
                newUser.setPending(true);
                newUser.setActive(false);
                System.out.println("✅ Đăng ký thành công! Tài khoản admin đang chờ duyệt từ admin khác.");
            } else {
                newUser.setPending(false);
                newUser.setActive(true);
                System.out.println("✅ Đăng ký thành công! Bạn có thể đăng nhập ngay.");
            }
            
            users.add(newUser);
            database.saveUsers(users);
            
            return true;
        } catch (Exception e) {
            System.err.println("❌ Lỗi khi đăng ký: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Lấy danh sách tài khoản chờ duyệt (chỉ admin)
     */
    public List<User> getPendingUsers() {
        if (!isAdmin()) {
            System.out.println("❌ Chỉ admin mới có quyền xem danh sách tài khoản chờ duyệt");
            return null;
        }
        
        List<User> users = database.loadUsers();
        return users.stream()
                   .filter(User::isPending)
                   .collect(java.util.stream.Collectors.toList());
    }
    
    /**
     * Duyệt tài khoản (chỉ admin)
     */
    public boolean approveUser(String username) {
        if (!isAdmin()) {
            System.out.println("❌ Chỉ admin mới có quyền duyệt tài khoản");
            return false;
        }
        
        List<User> users = database.loadUsers();
        
        for (User user : users) {
            if (user.getUsername().equals(username) && user.isPending()) {
                user.setPending(false);
                user.setActive(true);
                database.saveUsers(users);
                System.out.println("✅ Đã duyệt tài khoản: " + user.getHoTen());
                return true;
            }
        }
        
        System.out.println("❌ Không tìm thấy tài khoản chờ duyệt với username: " + username);
        return false;
    }
    
    /**
     * Từ chối tài khoản (chỉ admin)
     */
    public boolean rejectUser(String username) {
        if (!isAdmin()) {
            System.out.println("❌ Chỉ admin mới có quyền từ chối tài khoản");
            return false;
        }
        
        List<User> users = database.loadUsers();
        
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            if (user.getUsername().equals(username) && user.isPending()) {
                users.remove(i);
                database.saveUsers(users);
                System.out.println("✅ Đã từ chối tài khoản: " + user.getHoTen());
                return true;
            }
        }
        
        System.out.println("❌ Không tìm thấy tài khoản chờ duyệt với username: " + username);
        return false;
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
