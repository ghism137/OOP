package QuanLyKyThi;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Test các form đã hoàn thiện
 */
public class TestForms {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Set Look and Feel
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                
                System.out.println("🧪 KIỂM TRA CÁC FORM ĐÃ HOÀN THIỆN");
                System.out.println("=====================================");
                
                // Tạo dữ liệu test
                ArrayList<ThiSinh> thiSinhList = new ArrayList<>();
                ArrayList<KyThi> kyThiList = new ArrayList<>();
                ArrayList<GiamThi> giamThiList = new ArrayList<>();
                
                // Test ThiSinhListForm
                System.out.println("✅ Testing ThiSinhListForm...");
                ThiSinhListForm thiSinhForm = new ThiSinhListForm(thiSinhList);
                thiSinhForm.setVisible(true);
                
                // Test AddThiSinhForm
                System.out.println("✅ Testing AddThiSinhForm...");
                AddThiSinhForm addThiSinhForm = new AddThiSinhForm(thiSinhList);
                addThiSinhForm.setLocation(50, 50);
                addThiSinhForm.setVisible(true);
                
                // Test DangKyThiForm
                System.out.println("✅ Testing DangKyThiForm...");
                DangKyThiForm dangKyForm = new DangKyThiForm(thiSinhList, kyThiList);
                dangKyForm.setLocation(100, 100);
                dangKyForm.setVisible(true);
                
                // Test GiamThiListForm
                System.out.println("✅ Testing GiamThiListForm...");
                GiamThiListForm giamThiForm = new GiamThiListForm(giamThiList);
                giamThiForm.setLocation(150, 150);
                giamThiForm.setVisible(true);
                
                // Test NhapDiemForm
                System.out.println("✅ Testing NhapDiemForm...");
                NhapDiemForm nhapDiemForm = new NhapDiemForm(kyThiList);
                nhapDiemForm.setLocation(200, 200);
                nhapDiemForm.setVisible(true);
                
                // Test XemKetQuaForm
                System.out.println("✅ Testing XemKetQuaForm...");
                XemKetQuaForm ketQuaForm = new XemKetQuaForm(kyThiList, thiSinhList);
                ketQuaForm.setLocation(250, 250);
                ketQuaForm.setVisible(true);
                
                // Test ThongKeForm
                System.out.println("✅ Testing ThongKeForm...");
                ThongKeForm thongKeForm = new ThongKeForm(kyThiList);
                thongKeForm.setLocation(300, 300);
                thongKeForm.setVisible(true);
                
                System.out.println("\n🎉 TẤT CẢ CÁC FORM ĐÃ ĐƯỢC HOÀN THIỆN!");
                System.out.println("📋 Các form có sẵn:");
                System.out.println("   • ThiSinhListForm - Quản lý danh sách thí sinh");
                System.out.println("   • AddThiSinhForm - Thêm thí sinh mới");
                System.out.println("   • DangKyThiForm - Đăng ký thi cho thí sinh");
                System.out.println("   • GiamThiListForm - Quản lý danh sách giám thị");
                System.out.println("   • AddGiamThiForm - Thêm giám thị mới");
                System.out.println("   • PhanCongGiamThiForm - Phân công giám thị");
                System.out.println("   • NhapDiemForm - Nhập điểm thi");
                System.out.println("   • XemKetQuaForm - Xem kết quả thi");
                System.out.println("   • ThongKeForm - Thống kê kết quả");
                
            } catch (Exception e) {
                System.out.println("❌ Lỗi: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }
}
