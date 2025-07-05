package QuanLyKyThi;

import javax.swing.*;

/**
 * Test MainGUI sau khi xóa createSampleData
 */
public class TestMainGUIClean {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                
                System.out.println("=== Test MainGUI Clean (No Sample Data) ===");
                
                // Test tạo MainGUI với dữ liệu clean
                MainGUI mainGUI = new MainGUI();
                
                System.out.println("Test - MainGUI khởi tạo thành công với dữ liệu clean!");
                System.out.println("Test - Có thể sử dụng menu 'Hệ Thống' để load/save XML data");
                
            } catch (Exception e) {
                System.err.println("Test - Lỗi: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }
}
