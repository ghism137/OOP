package QuanLyKyThi;

import javax.swing.*;

/**
 * Demo test tính năng resize cho MainGUI
 */
public class TestMainGUIResize {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            System.out.println("=== TEST RESIZE CHO MainGUI ===");
            
            // Test 1: MainGUI với kích thước mặc định
            System.out.println("1. Tạo MainGUI mặc định (1400x800)");
            MainGUI mainGUI = new MainGUI();
            
            // Test 2: MainGUI với preset kích thước
            Timer timer = new Timer(3000, e -> {
                System.out.println("2. Tạo MainGUI với preset SIZE_LARGE (1600x900)");
                MainGUI largeGUI = MainGUI.createWithSize(MainGUI.SIZE_LARGE);
                largeGUI.setLocation(100, 100);
                
                // Test 3: MainGUI với kích thước tùy chỉnh
                Timer timer2 = new Timer(2000, e2 -> {
                    System.out.println("3. Tạo MainGUI với kích thước tùy chỉnh (1500x850)");
                    MainGUI customGUI = new MainGUI(1500, 850);
                    customGUI.setLocation(200, 150);
                    
                    System.out.println("\n=== HƯỚNG DẪN SỬ DỤNG ===");
                    System.out.println("- Kéo thả các cạnh/góc cửa sổ để thay đổi kích thước");
                    System.out.println("- Menu font sẽ tự động điều chỉnh theo kích thước cửa sổ");
                    System.out.println("- Kích thước tối thiểu: 1000x600");
                    System.out.println("- Các preset có sẵn: SIZE_SMALL, SIZE_MEDIUM, SIZE_LARGE, SIZE_XLARGE, SIZE_FULLHD");
                });
                timer2.setRepeats(false);
                timer2.start();
            });
            timer.setRepeats(false);
            timer.start();
        });
    }
}
