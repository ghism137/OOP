package QuanLyKyThi;

import javax.swing.*;

/**
 * Demo tính năng kéo thả để thay đổi kích thước LoginForm
 */
public class ResizeDemo {
    public static void main(String[] args) {
        // Set Look and Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            // Tạo LoginForm với kích thước ban đầu
            LoginForm loginForm = new LoginForm(1200, 700);
            loginForm.setTitle("🔐 Demo Resize - Kéo thả để thay đổi kích thước");
            loginForm.setVisible(true);
            
            // Hiển thị hướng dẫn
            Timer timer = new Timer(1000, e -> showInstructions());
            timer.setRepeats(false);
            timer.start();
        });
    }
    
    private static void showInstructions() {
        String instructions = """
            🎯 HƯỚNG DẪN SỬ DỤNG TÍNH NĂNG KÉO THẢ:
            
            ✅ CÁC CÁCH THAY ĐỔI KÍCH THƯỚC:
            1. Kéo góc dưới-phải của cửa sổ để thay đổi cả chiều rộng và chiều cao
            2. Kéo cạnh phải để thay đổi chỉ chiều rộng
            3. Kéo cạnh dưới để thay đổi chỉ chiều cao
            4. Kéo các góc khác để thay đổi theo hướng tương ứng
            
            🔧 TÍNH NĂNG RESPONSIVE:
            • Font size tự động thay đổi theo kích thước cửa sổ
            • Kích thước các trường nhập liệu tự động điều chỉnh
            • Tỷ lệ brand panel (65%) và login panel (35%) được duy trì
            • Kích thước tối thiểu: 800x500 (để tránh giao diện bị vỡ)
            
            📏 CÁC PRESET KÍCH THƯỚC CÓ SẴN:
            • SIZE_SMALL: 1024×768 (4:3)
            • SIZE_MEDIUM: 1280×720 (HD)
            • SIZE_LARGE: 1366×768 (Laptop)
            • SIZE_XLARGE: 1600×900 (Widescreen)
            • SIZE_FULLHD: 1920×1080 (Full HD)
            
            💡 CÁCH SỬ DỤNG TRONG CODE:
            • new LoginForm() - Kích thước mặc định (1280×720)
            • new LoginForm(width, height) - Kích thước tùy chỉnh
            • LoginForm.createWithSize(LoginForm.SIZE_LARGE) - Dùng preset
            
            Hãy thử kéo thả cửa sổ để xem giao diện thay đổi!
            """;
        
        JOptionPane.showMessageDialog(null, instructions, 
                                    "🎯 Hướng dẫn Resize Demo", 
                                    JOptionPane.INFORMATION_MESSAGE);
    }
}
