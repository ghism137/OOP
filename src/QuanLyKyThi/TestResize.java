package QuanLyKyThi;

import javax.swing.*;

/**
 * Test class để kiểm tra tính năng resize
 */
public class TestResize {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            System.out.println("=== Test Tính Năng Resize ===");
            
            // Test LoginForm với kích thước mặc định
            System.out.println("1. Mở LoginForm với kích thước mặc định...");
            LoginForm loginForm = new LoginForm();
            loginForm.setVisible(true);
            
            // Hướng dẫn cho người dùng
            JOptionPane.showMessageDialog(null, 
                "Hướng dẫn test:\n\n" +
                "1. LoginForm đã mở - Hãy thử kéo thả để thay đổi kích thước\n" +
                "2. Kiểm tra font và layout có co giãn theo không\n" +
                "3. Thử các preset kích thước bằng cách double-click title bar\n" +
                "4. Sau khi test xong, đóng LoginForm để tiếp tục\n\n" +
                "Nhấn OK để tiếp tục...", 
                "Test Resize Feature", 
                JOptionPane.INFORMATION_MESSAGE);
        });
    }
}
