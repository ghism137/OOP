package QuanLyKyThi;

import javax.swing.*;

/**
 * Test class để demo các tính năng mới của LoginForm
 */
public class LoginFormTest {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Test 1: LoginForm với kích thước mặc định
            System.out.println("=== TEST 1: LoginForm mặc định (1280x720) ===");
            LoginForm defaultForm = new LoginForm();
            defaultForm.setVisible(true);
            
            // Delay một chút rồi test các preset khác
            Timer timer = new Timer(3000, e -> testOtherSizes());
            timer.setRepeats(false);
            timer.start();
        });
    }
    
    private static void testOtherSizes() {
        // Test 2: Các preset kích thước
        System.out.println("\n=== TEST 2: Các preset kích thước ===");
        
        // HD (1280x720)
        System.out.println("Tạo LoginForm HD (1280x720)");
        LoginForm hdForm = LoginForm.createWithSize(LoginForm.SIZE_MEDIUM);
        hdForm.setTitle(hdForm.getTitle() + " - HD");
        hdForm.setLocation(100, 100);
        hdForm.setVisible(true);
        
        // Laptop phổ biến (1366x768)
        System.out.println("Tạo LoginForm Laptop (1366x768)");
        LoginForm laptopForm = LoginForm.createWithSize(LoginForm.SIZE_LARGE);
        laptopForm.setTitle(laptopForm.getTitle() + " - Laptop");
        laptopForm.setLocation(200, 150);
        laptopForm.setVisible(true);
        
        // Full HD (1920x1080)
        System.out.println("Tạo LoginForm Full HD (1920x1080)");
        LoginForm fhdForm = LoginForm.createWithSize(LoginForm.SIZE_FULLHD);
        fhdForm.setTitle(fhdForm.getTitle() + " - Full HD");
        fhdForm.setLocation(300, 200);
        fhdForm.setVisible(true);
        
        // Test 3: Kích thước tùy chỉnh
        Timer timer2 = new Timer(2000, e -> testCustomSize());
        timer2.setRepeats(false);
        timer2.start();
    }
    
    private static void testCustomSize() {
        System.out.println("\n=== TEST 3: Kích thước tùy chỉnh ===");
        
        // Kích thước tùy chỉnh
        System.out.println("Tạo LoginForm tùy chỉnh (1500x800)");
        LoginForm customForm = new LoginForm(1500, 800);
        customForm.setTitle(customForm.getTitle() + " - Custom 1500x800");
        customForm.setLocation(400, 250);
        customForm.setVisible(true);
        
        // Kích thước nhỏ hơn
        System.out.println("Tạo LoginForm nhỏ (1024x768)");
        LoginForm smallForm = LoginForm.createWithSize(LoginForm.SIZE_SMALL);
        smallForm.setTitle(smallForm.getTitle() + " - Small");
        smallForm.setLocation(500, 300);
        smallForm.setVisible(true);
        
        System.out.println("\n=== THÔNG TIN CÁC PRESET KÍCH THƯỚC ===");
        System.out.println("SIZE_SMALL: " + LoginForm.SIZE_SMALL[0] + "x" + LoginForm.SIZE_SMALL[1]);
        System.out.println("SIZE_MEDIUM: " + LoginForm.SIZE_MEDIUM[0] + "x" + LoginForm.SIZE_MEDIUM[1]);
        System.out.println("SIZE_LARGE: " + LoginForm.SIZE_LARGE[0] + "x" + LoginForm.SIZE_LARGE[1]);
        System.out.println("SIZE_XLARGE: " + LoginForm.SIZE_XLARGE[0] + "x" + LoginForm.SIZE_XLARGE[1]);
        System.out.println("SIZE_FULLHD: " + LoginForm.SIZE_FULLHD[0] + "x" + LoginForm.SIZE_FULLHD[1]);
        
        System.out.println("\n=== HƯỚNG DẪN SỬ DỤNG ===");
        System.out.println("1. Tất cả các cửa sổ đều có thể thay đổi kích thước bằng cách kéo");
        System.out.println("2. Giao diện sẽ tự động điều chỉnh font size và kích thước theo tỷ lệ");
        System.out.println("3. Để tạo với kích thước tùy chỉnh: new LoginForm(width, height)");
        System.out.println("4. Để tạo với preset: LoginForm.createWithSize(LoginForm.SIZE_MEDIUM)");
    }
}
