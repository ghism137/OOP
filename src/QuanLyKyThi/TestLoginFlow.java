package QuanLyKyThi;

import javax.swing.*;

/**
 * Test flow đăng nhập - MainGUIWithAuth connection
 */
public class TestLoginFlow {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Set Look and Feel
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            System.out.println("=== Test Login Flow ===");
            
            // Tạo LoginForm với callback để mở MainGUIWithAuth
            LoginForm loginForm = new LoginForm(
                // Callback đăng nhập thành công
                (authService) -> {
                    System.out.println("Test - Callback được gọi, user: " + authService.getCurrentUser().getHoTen());
                    
                    try {
                        // Tạo MainGUIWithAuth
                        MainGUIWithAuth mainGUI = new MainGUIWithAuth(authService);
                        System.out.println("Test - MainGUIWithAuth đã được khởi tạo và hiển thị");
                        
                    } catch (Exception e) {
                        System.err.println("Test - Lỗi khi tạo MainGUIWithAuth: " + e.getMessage());
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(null, 
                            "Lỗi khi mở MainGUIWithAuth: " + e.getMessage(),
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                },
                // Callback hủy đăng nhập
                () -> {
                    System.out.println("Test - Đăng nhập bị hủy");
                    System.exit(0);
                }
            );
            
            System.out.println("Test - LoginForm được tạo, hiển thị...");
            System.out.println("Thử đăng nhập với:");
            System.out.println("- Username: admin");
            System.out.println("- Password: admin123");
            
            // Đảm bảo user admin có trong database với mật khẩu đã băm
            XMLDatabase db = new XMLDatabase();
            try {
                List<User> users = db.loadUsers();
                boolean adminExists = users.stream().anyMatch(u -> u.getUsername().equals("admin"));
                if (!adminExists) {
                    User adminUser = new User("admin", PasswordUtil.hashPassword("admin123"), "Administrator", "admin@example.com", "admin");
                    users.add(adminUser);
                    db.saveUsers(users);
                    System.out.println("Test - Đã thêm user admin mẫu vào database.");
                }
            } catch (Exception e) {
                System.err.println("Test - Lỗi khi kiểm tra/thêm user admin mẫu: " + e.getMessage());
            }
            
            loginForm.setVisible(true);
        });
    }
}
