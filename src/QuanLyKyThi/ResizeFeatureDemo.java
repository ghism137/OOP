package QuanLyKyThi;

import javax.swing.*;

/**
 * Demo test tính năng resize cho các giao diện chính
 */
public class ResizeFeatureDemo {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            System.out.println("=== DEMO TÍNH NĂNG RESIZE ===");
            
            // Test 1: MainGUI với các kích thước khác nhau
            System.out.println("\n1. Testing MainGUI với preset sizes:");
            
            // MainGUI Small
            MainGUI smallMain = MainGUI.createWithSize(MainGUI.SIZE_SMALL);
            smallMain.setTitle(smallMain.getTitle() + " - Small (1200x700)");
            smallMain.setLocation(50, 50);
            System.out.println("   - Created MainGUI Small: " + MainGUI.SIZE_SMALL[0] + "x" + MainGUI.SIZE_SMALL[1]);
            
            // MainGUI Medium
            MainGUI mediumMain = new MainGUI();
            mediumMain.setTitle(mediumMain.getTitle() + " - Medium (Default)");
            mediumMain.setLocation(150, 100);
            System.out.println("   - Created MainGUI Medium: " + MainGUI.SIZE_MEDIUM[0] + "x" + MainGUI.SIZE_MEDIUM[1]);
            
            // MainGUI Custom
            MainGUI customMain = new MainGUI(1500, 850);
            customMain.setTitle(customMain.getTitle() + " - Custom (1500x850)");
            customMain.setLocation(250, 150);
            System.out.println("   - Created MainGUI Custom: 1500x850");
            
            // Test 2: MainGUIWithAuth (cần authentication service)
            System.out.println("\n2. Testing MainGUIWithAuth:");
            
            try {
                // Tạo AuthenticationService với user admin để test
                AuthenticationService authService = new AuthenticationService();
                if (authService.login("admin", "admin")) {
                    System.out.println("   - Login thành công với admin");
                    
                    // MainGUIWithAuth với preset
                    MainGUIWithAuth authMain = MainGUIWithAuth.createWithSize(authService, MainGUIWithAuth.SIZE_LARGE);
                    authMain.setTitle(authMain.getTitle() + " - Large (1600x900)");
                    authMain.setLocation(350, 200);
                    System.out.println("   - Created MainGUIWithAuth Large: " + MainGUIWithAuth.SIZE_LARGE[0] + "x" + MainGUIWithAuth.SIZE_LARGE[1]);
                    
                } else {
                    System.out.println("   - Login thất bại, không thể test MainGUIWithAuth");
                }
            } catch (Exception e) {
                System.err.println("   - Lỗi khi test MainGUIWithAuth: " + e.getMessage());
            }
            
            System.out.println("\n=== HƯỚNG DẪN SỬ DỤNG ===");
            System.out.println("✓ Tất cả cửa sổ có thể RESIZE bằng cách kéo thả cạnh/góc");
            System.out.println("✓ Font size và layout tự động điều chỉnh theo kích thước");
            System.out.println("✓ Kích thước tối thiểu: MainGUI(1000x600), MainGUIWithAuth(1000x600)");
            System.out.println("✓ Thử thay đổi kích thước bằng cách kéo thả để xem responsive design!");
            
            System.out.println("\n=== PRESET KÍCH THƯỚC ===");
            System.out.println("MainGUI & MainGUIWithAuth:");
            System.out.println("  SIZE_SMALL:  " + MainGUI.SIZE_SMALL[0] + "x" + MainGUI.SIZE_SMALL[1]);
            System.out.println("  SIZE_MEDIUM: " + MainGUI.SIZE_MEDIUM[0] + "x" + MainGUI.SIZE_MEDIUM[1] + " (mặc định)");
            System.out.println("  SIZE_LARGE:  " + MainGUI.SIZE_LARGE[0] + "x" + MainGUI.SIZE_LARGE[1]);
            System.out.println("  SIZE_XLARGE: " + MainGUI.SIZE_XLARGE[0] + "x" + MainGUI.SIZE_XLARGE[1]);
            System.out.println("  SIZE_FULLHD: " + MainGUI.SIZE_FULLHD[0] + "x" + MainGUI.SIZE_FULLHD[1]);
        });
    }
}
