package QuanLyKyThi;

import javax.swing.*;

/**
 * Test rapid để kiểm tra lỗi index out of bounds đã fix chưa
 */
public class QuickTest {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                
                System.out.println("=== Quick Test - Fix Index Out of Bounds ===");
                
                // Test tạo MainGUIWithAuth trực tiếp với AuthenticationService
                AuthenticationService authService = new AuthenticationService();
                
                // Login admin để test
                if (authService.login("admin", "admin123")) {
                    System.out.println("Quick Test - Login thành công");
                    
                    // Tạo MainGUIWithAuth
                    MainGUIWithAuth mainGUI = new MainGUIWithAuth(authService);
                    System.out.println("Quick Test - MainGUIWithAuth khởi tạo thành công!");
                    
                } else {
                    System.out.println("Quick Test - Login thất bại");
                }
                
            } catch (Exception e) {
                System.err.println("Quick Test - Lỗi: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }
}
