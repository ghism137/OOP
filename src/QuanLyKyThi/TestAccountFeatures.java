package QuanLyKyThi;

/**
 * Test các tính năng tài khoản mới
 */
public class TestAccountFeatures {
    public static void main(String[] args) {
        System.out.println("=== TEST ACCOUNT FEATURES ===");
        
        AuthenticationService auth = new AuthenticationService();
        
        // Test 1: Đăng nhập admin
        System.out.println("\n1. Test đăng nhập admin:");
        boolean loginSuccess = auth.login("admin", "admin123");
        System.out.println("Đăng nhập admin: " + loginSuccess);
        
        if (loginSuccess) {
            // Test 2: Đăng ký tài khoản thường
            System.out.println("\n2. Test đăng ký tài khoản thường:");
            auth.logout();
            boolean registerSuccess = auth.register("testuser", "123456", "Test User", "test@email.com", "thisinh");
            System.out.println("Đăng ký thành công: " + registerSuccess);
            
            // Test 3: Đăng nhập với tài khoản vừa tạo
            System.out.println("\n3. Test đăng nhập tài khoản vừa tạo:");
            boolean loginNewUser = auth.login("testuser", "123456");
            System.out.println("Đăng nhập testuser: " + loginNewUser);
            
            // Test 4: Đổi mật khẩu
            System.out.println("\n4. Test đổi mật khẩu:");
            boolean changePasswordSuccess = auth.changePassword("123456", "newpassword");
            System.out.println("Đổi mật khẩu thành công: " + changePasswordSuccess);
            
            // Test 5: Đăng nhập với mật khẩu mới
            System.out.println("\n5. Test đăng nhập với mật khẩu mới:");
            auth.logout();
            boolean loginNewPassword = auth.login("testuser", "newpassword");
            System.out.println("Đăng nhập với mật khẩu mới: " + loginNewPassword);
            
            // Test 6: Đăng ký tài khoản admin (cần duyệt)
            System.out.println("\n6. Test đăng ký tài khoản admin:");
            auth.logout();
            boolean registerAdminSuccess = auth.register("newadmin", "123456", "New Admin", "newadmin@email.com", "admin");
            System.out.println("Đăng ký admin thành công: " + registerAdminSuccess);
            
            // Test 7: Đăng nhập admin để duyệt tài khoản
            System.out.println("\n7. Test duyệt tài khoản admin:");
            auth.login("admin", "admin123");
            if (auth.isAdmin()) {
                var pendingUsers = auth.getPendingUsers();
                System.out.println("Số tài khoản chờ duyệt: " + (pendingUsers != null ? pendingUsers.size() : 0));
                
                if (pendingUsers != null && !pendingUsers.isEmpty()) {
                    boolean approveSuccess = auth.approveUser("newadmin");
                    System.out.println("Duyệt tài khoản thành công: " + approveSuccess);
                }
            }
            
            // Test 8: Đăng nhập với tài khoản admin vừa được duyệt
            System.out.println("\n8. Test đăng nhập admin vừa được duyệt:");
            auth.logout();
            boolean loginApprovedAdmin = auth.login("newadmin", "123456");
            System.out.println("Đăng nhập newadmin: " + loginApprovedAdmin);
        }
        
        System.out.println("\n=== TEST COMPLETED ===");
    }
}
