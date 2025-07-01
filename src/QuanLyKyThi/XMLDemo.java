package QuanLyKyThi;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Demo tạo file XML
 */
public class XMLDemo {
    public static void main(String[] args) {
        System.out.println("🚀 Demo tạo file XML cho hệ thống Quản lý Kỳ thi");
        System.out.println("=" .repeat(60));
        
        try {
            // 1. Khởi tạo XMLDatabase (sẽ tự tạo thư mục data/ và file XML)
            System.out.println("📁 Khởi tạo XMLDatabase...");
            XMLDatabase database = new XMLDatabase();
            System.out.println("✅ XMLDatabase đã được khởi tạo thành công!");
            
            // 2. Tạo thêm một số kỳ thi mẫu
            System.out.println("\n📝 Tạo kỳ thi mẫu...");
            List<KyThi> kyThiList = database.loadKyThi();
            
            // Thêm kỳ thi mẫu nếu chưa có
            if (kyThiList.isEmpty()) {
                KyThi kt1 = new KyThi("KT001", "Kỳ thi Java OOP", 
                                     LocalDate.of(2025, 8, 15), "Sắp diễn ra", 
                                     new ArrayList<>(), new ArrayList<>(), 150.0);
                
                KyThi kt2 = new KyThi("KT002", "Kỳ thi Cấu trúc dữ liệu", 
                                     LocalDate.of(2025, 9, 10), "Sắp diễn ra", 
                                     new ArrayList<>(), new ArrayList<>(), 120.0);
                
                kyThiList.add(kt1);
                kyThiList.add(kt2);
                
                database.saveKyThi(kyThiList);
                System.out.println("✅ Đã tạo 2 kỳ thi mẫu");
            } else {
                System.out.println("ℹ️  Đã có " + kyThiList.size() + " kỳ thi trong database");
            }
            
            // 3. Tạo thêm thí sinh mẫu
            System.out.println("\n👨‍🎓 Tạo thí sinh mẫu...");
            List<ThiSinh> thiSinhList = database.loadThiSinh();
            
            if (thiSinhList.isEmpty()) {
                ThiSinh ts1 = new ThiSinh("TS001", "Nguyễn Văn An", 
                                         LocalDate.of(2003, 5, 10), "Nam", 
                                         "Hà Nội", "0123456789");
                
                ThiSinh ts2 = new ThiSinh("TS002", "Trần Thị Bình", 
                                         LocalDate.of(2003, 8, 15), "Nữ", 
                                         "TP.HCM", "0987654321");
                
                thiSinhList.add(ts1);
                thiSinhList.add(ts2);
                
                database.saveThiSinh(thiSinhList);
                System.out.println("✅ Đã tạo 2 thí sinh mẫu");
            } else {
                System.out.println("ℹ️  Đã có " + thiSinhList.size() + " thí sinh trong database");
            }
            
            // 4. Tạo giám thị mẫu
            System.out.println("\n👨‍🏫 Tạo giám thị mẫu...");
            List<GiamThi> giamThiList = database.loadGiamThi();
            
            if (giamThiList.isEmpty()) {
                GiamThi gt1 = new GiamThi("GT001", "Lê Văn Cường", 
                                         "Phòng Giáo dục", "0111222333");
                
                GiamThi gt2 = new GiamThi("GT002", "Phạm Thị Dung", 
                                         "Trường ĐHCN", "0444555666");
                
                giamThiList.add(gt1);
                giamThiList.add(gt2);
                
                database.saveGiamThi(giamThiList);
                System.out.println("✅ Đã tạo 2 giám thị mẫu");
            } else {
                System.out.println("ℹ️  Đã có " + giamThiList.size() + " giám thị trong database");
            }
            
            // 5. Tạo kết quả mẫu
            System.out.println("\n📊 Tạo kết quả thi mẫu...");
            List<KetQua> ketQuaList = database.loadKetQua();
            
            if (ketQuaList.isEmpty() && !thiSinhList.isEmpty() && !kyThiList.isEmpty()) {
                KetQua kq1 = new KetQua(thiSinhList.get(0), kyThiList.get(0), 8.5);
                KetQua kq2 = new KetQua(thiSinhList.get(1), kyThiList.get(0), 7.0);
                
                ketQuaList.add(kq1);
                ketQuaList.add(kq2);
                
                database.saveKetQua(ketQuaList);
                System.out.println("✅ Đã tạo 2 kết quả thi mẫu");
            } else {
                System.out.println("ℹ️  Đã có " + ketQuaList.size() + " kết quả thi trong database");
            }
            
            // 6. Thống kê tổng quan
            System.out.println("\n📈 THỐNG KÊ TỔNG QUAN:");
            System.out.println("=" .repeat(40));
            System.out.println("👥 Users: " + database.loadUsers().size());
            System.out.println("📝 Kỳ thi: " + database.loadKyThi().size());
            System.out.println("🎓 Thí sinh: " + database.loadThiSinh().size());
            System.out.println("👨‍🏫 Giám thị: " + database.loadGiamThi().size());
            System.out.println("📊 Kết quả: " + database.loadKetQua().size());
            
            System.out.println("\n🎉 Hoàn thành! Kiểm tra thư mục 'data/' để xem các file XML đã được tạo.");
            
        } catch (Exception e) {
            System.err.println("❌ Lỗi: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
