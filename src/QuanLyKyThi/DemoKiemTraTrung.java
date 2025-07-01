package QuanLyKyThi;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Demo minh họa method kiemTraTrung() trong class KyThi
 * Giải thích ý nghĩa và cách sử dụng
 */
public class DemoKiemTraTrung {
    
    public static void main(String[] args) {
        System.out.println("=== DEMO METHOD kiemTraTrung() ===");
        System.out.println();
        
        // Tạo kỳ thi mẫu
        List<ThiSinh> dsThiSinh = new ArrayList<>();
        List<GiamThi> dsGiamThi = new ArrayList<>();
        
        KyThi kyThi = new KyThi(
            "KT001", 
            "Kỳ thi Toán học", 
            LocalDate.of(2024, 6, 15),
            "Sắp diễn ra",
            dsThiSinh,
            dsGiamThi,
            100000
        );
        
        System.out.println("📝 Đã tạo kỳ thi: " + kyThi.getTenKyThi());
        System.out.println();
        
        // === DEMO 1: Thêm thí sinh lần đầu ===
        System.out.println("🔹 DEMO 1: Thêm thí sinh lần đầu");
        ThiSinh ts1 = new ThiSinh("TS001", "Nguyễn Văn A", LocalDate.of(2000, 1, 1), 
                                  "Nam", "Hà Nội", "0123456789");
        
        // Kiểm tra trùng lặp trước khi thêm
        boolean daTonTai = kyThi.kiemTraTrung("TS001", "THISINH");
        System.out.println("❓ Kiểm tra trùng TS001: " + (daTonTai ? "ĐÃ TỒN TẠI" : "CHƯA TỒN TẠI"));
        
        // Thêm thí sinh
        boolean ketQua1 = kyThi.themThiSinh(ts1);
        System.out.println("✅ Thêm TS001: " + (ketQua1 ? "THÀNH CÔNG" : "THẤT BẠI"));
        System.out.println("📊 Số lượng thí sinh hiện tại: " + kyThi.getDanhSachThiSinh().size());
        System.out.println();
        
        // === DEMO 2: Thêm thí sinh trùng lặp ===
        System.out.println("🔹 DEMO 2: Thêm thí sinh trùng lặp");
        ThiSinh ts2 = new ThiSinh("TS001", "Nguyễn Văn B", LocalDate.of(2001, 2, 2), 
                                  "Nam", "HCM", "0987654321");
        
        // Kiểm tra trùng lặp
        daTonTai = kyThi.kiemTraTrung("TS001", "THISINH");
        System.out.println("❓ Kiểm tra trùng TS001: " + (daTonTai ? "ĐÃ TỒN TẠI" : "CHƯA TỒN TẠI"));
        
        // Thêm thí sinh (sẽ thất bại do trùng mã)
        boolean ketQua2 = kyThi.themThiSinh(ts2);
        System.out.println("❌ Thêm TS001 (trùng): " + (ketQua2 ? "THÀNH CÔNG" : "THẤT BẠI"));
        System.out.println("📊 Số lượng thí sinh hiện tại: " + kyThi.getDanhSachThiSinh().size());
        System.out.println();
        
        // === DEMO 3: Thêm thí sinh mới (không trùng) ===
        System.out.println("🔹 DEMO 3: Thêm thí sinh mới (mã khác)");
        ThiSinh ts3 = new ThiSinh("TS002", "Trần Thị C", LocalDate.of(1999, 3, 3), 
                                  "Nữ", "Đà Nẵng", "0111222333");
        
        daTonTai = kyThi.kiemTraTrung("TS002", "THISINH");
        System.out.println("❓ Kiểm tra trùng TS002: " + (daTonTai ? "ĐÃ TỒN TẠI" : "CHƯA TỒN TẠI"));
        
        boolean ketQua3 = kyThi.themThiSinh(ts3);
        System.out.println("✅ Thêm TS002: " + (ketQua3 ? "THÀNH CÔNG" : "THẤT BẠI"));
        System.out.println("📊 Số lượng thí sinh hiện tại: " + kyThi.getDanhSachThiSinh().size());
        System.out.println();
        
        // === DEMO 4: Kiểm tra trùng lặp giám thị ===
        System.out.println("🔹 DEMO 4: Kiểm tra trùng lặp giám thị");
        GiamThi gt1 = new GiamThi("GT001", "PGS.TS Nguyễn Văn X", "Khoa Toán", 
                                  "0123456789", "nvx@university.edu.vn", "giamthi01");
        
        daTonTai = kyThi.kiemTraTrung("GT001", "GIAMTHI");
        System.out.println("❓ Kiểm tra trùng GT001: " + (daTonTai ? "ĐÃ TỒN TẠI" : "CHƯA TỒN TẠI"));
        
        boolean ketQua4 = kyThi.themGiamThi(gt1);
        System.out.println("✅ Thêm GT001: " + (ketQua4 ? "THÀNH CÔNG" : "THẤT BẠI"));
        System.out.println("📊 Số lượng giám thị hiện tại: " + kyThi.getDanhSachGiamThi().size());
        System.out.println();
        
        // Thử thêm giám thị trùng
        GiamThi gt2 = new GiamThi("GT001", "TS Trần Thị Y", "Khoa Lý", 
                                  "0987654321", "tty@university.edu.vn", "giamthi02");
        
        daTonTai = kyThi.kiemTraTrung("GT001", "GIAMTHI");
        System.out.println("❓ Kiểm tra trùng GT001 (lần 2): " + (daTonTai ? "ĐÃ TỒN TẠI" : "CHƯA TỒN TẠI"));
        
        boolean ketQua5 = kyThi.themGiamThi(gt2);
        System.out.println("❌ Thêm GT001 (trùng): " + (ketQua5 ? "THÀNH CÔNG" : "THẤT BẠI"));
        System.out.println("📊 Số lượng giám thị hiện tại: " + kyThi.getDanhSachGiamThi().size());
        System.out.println();
        
        // === TỔNG KẾT ===
        System.out.println("=== TỔNG KẾT ===");
        System.out.println("🎯 Ý nghĩa method kiemTraTrung():");
        System.out.println("   ✓ Đảm bảo tính duy nhất của mã thí sinh/giám thị trong kỳ thi");
        System.out.println("   ✓ Tránh duplicate data và confusion trong quản lý");
        System.out.println("   ✓ Hỗ trợ cả 2 loại: THISINH và GIAMTHI");
        System.out.println("   ✓ Trả về boolean: true=trùng, false=không trùng");
        System.out.println();
        System.out.println("📊 Kết quả cuối cùng:");
        System.out.println("   - Số thí sinh: " + kyThi.getDanhSachThiSinh().size());
        System.out.println("   - Số giám thị: " + kyThi.getDanhSachGiamThi().size());
        System.out.println();
        System.out.println("💡 Tip: Method này được gọi bên trong themThiSinh() và themGiamThi()");
        System.out.println("    nhưng cũng có thể gọi riêng lẻ để kiểm tra trước khi thêm.");
    }
}
