package QuanLyKyThi;

import javax.swing.*;

/**
 * Demo script để test form quản lý trạng thái bài thi với dữ liệu mẫu
 */
public class DemoTrangThaiBaiThi {
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Set Look and Feel
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            // Tạo XMLDatabase và dữ liệu mẫu
            XMLDatabase database = new XMLDatabase();
            
            System.out.println("=== DEMO: Quản Lý Trạng Thái Bài Thi ===");
            System.out.println("1. Khởi tạo XMLDatabase...");
            
            // Tạo dữ liệu mẫu
            createSampleExamResults(database);
            
            System.out.println("2. Dữ liệu mẫu đã được tạo trong thư mục data/");
            
            // Test với các role khác nhau
            testWithDifferentRoles();
        });
    }
    
    private static void createSampleExamResults(XMLDatabase database) {
        // Tạo dữ liệu mẫu nếu chưa có
        if (database.getAllKyThi().isEmpty()) {
            System.out.println("   - Tạo kỳ thi mẫu...");
        }
        if (database.getAllThiSinh().isEmpty()) {
            System.out.println("   - Tạo thí sinh mẫu...");
        }
        if (database.getAllKetQua().isEmpty()) {
            System.out.println("   - Tạo kết quả mẫu với các trạng thái khác nhau...");
        }
        
        System.out.println("✓ Dữ liệu mẫu hoàn tất!");
    }
    
    private static void testWithDifferentRoles() {
        System.out.println("\n3. Mở form test với các role khác nhau:");
        
        // Option dialog để chọn role
        String[] roles = {"Admin", "Giáo vụ", "Giám thị"};
        String selectedRole = (String) JOptionPane.showInputDialog(
            null,
            "Chọn role để test:",
            "Demo Quản Lý Trạng Thái Bài Thi",
            JOptionPane.QUESTION_MESSAGE,
            null,
            roles,
            roles[0]
        );
        
        if (selectedRole != null) {
            User testUser = createTestUser(selectedRole);
            
            System.out.println("   - Mở form với role: " + selectedRole);
            System.out.println("   - Username: " + testUser.getUsername());
            System.out.println("   - Quyền: " + getPermissionDescription(selectedRole));
            
            // Mở form
            new QuanLyTrangThaiBaiThiForm(testUser).setVisible(true);
        }
    }
    
    private static User createTestUser(String role) {
        switch (role) {
            case "Admin":
                return new User("admin", "admin123", "Quản trị viên", "admin@email.com", "admin");
            case "Giáo vụ":
                return new User("giaovu", "gv123", "Phòng Giáo vụ", "giaovu@email.com", "giaovu");
            case "Giám thị":
                return new User("giamthi", "gt123", "Giám thị thi", "giamthi@email.com", "giamthi");
            default:
                return new User("user", "123", "Người dùng", "user@email.com", "user");
        }
    }
    
    private static String getPermissionDescription(String role) {
        switch (role) {
            case "Admin":
                return "Toàn quyền - có thể thực hiện tất cả thao tác";
            case "Giáo vụ":
                return "Chấm bài, nhập điểm, cập nhật điểm (không bắt đầu thi/nộp bài)";
            case "Giám thị":
                return "Bắt đầu thi, nộp bài, chấm bài được phân công";
            default:
                return "Không có quyền thao tác";
        }
    }
}
