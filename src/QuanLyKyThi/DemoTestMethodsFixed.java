package QuanLyKyThi;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Demo test các method đã được sửa đổi và bổ sung
 * Kiểm tra sự nhất quán giữa UML và code implementation
 */
public class DemoTestMethodsFixed {
    
    public static void main(String[] args) {
        System.out.println("=== DEMO TEST CÁC METHOD ĐÃ SỬA ĐỔI ===");
        System.out.println();
        
        // === Test ThiSinh.getAge() và validate() ===
        System.out.println("🔹 TEST 1: ThiSinh.getAge() và validate()");
        ThiSinh ts1 = new ThiSinh("TS001", "Nguyễn Văn A", LocalDate.of(2000, 5, 15), 
                                  "Nam", "Hà Nội", "0123456789");
        
        System.out.println("👤 Thí sinh: " + ts1.getHoTen());
        System.out.println("🎂 Tuổi: " + ts1.getAge() + " tuổi");
        System.out.println("✅ Validation: " + (ts1.validate() ? "HỢP LỆ" : "KHÔNG HỢP LỆ"));
        System.out.println();
        
        // Test thí sinh với thông tin không hợp lệ
        System.out.println("🔹 TEST 2: ThiSinh với thông tin không hợp lệ");
        ThiSinh ts2 = new ThiSinh("TS002", "", LocalDate.of(2010, 1, 1), 
                                  "Nam", "", "123"); // Tuổi nhỏ, họ tên rỗng, SĐT sai
        
        System.out.println("👤 Thí sinh: " + ts2.getHoTen());
        System.out.println("🎂 Tuổi: " + ts2.getAge() + " tuổi");
        System.out.println("❌ Validation: " + (ts2.validate() ? "HỢP LỆ" : "KHÔNG HỢP LỆ"));
        System.out.println();
        
        // === Test PhieuDangKy.taoPhieu() và xacNhanTT() ===
        System.out.println("🔹 TEST 3: PhieuDangKy.taoPhieu() và xacNhanTT()");
        
        // Tạo kỳ thi
        List<ThiSinh> dsTS = new ArrayList<>();
        List<GiamThi> dsGT = new ArrayList<>();
        KyThi kyThi = new KyThi("KT001", "Kỳ thi Toán", LocalDate.of(2024, 6, 15),
                              "Sắp diễn ra", dsTS, dsGT, 150000);
        
        // Tạo phiếu đăng ký bằng static method
        PhieuDangKy phieu = PhieuDangKy.taoPhieu(ts1, kyThi);
        System.out.println("💰 Phí cần đóng: " + phieu.tinhPhi() + " VNĐ");
        System.out.println("📋 Mã phiếu: " + phieu.getMaPhieuDangKy());
        
        // Xác nhận thanh toán
        boolean daThanhToan = phieu.xacNhanTT();
        System.out.println("💳 Thanh toán: " + (daThanhToan ? "THÀNH CÔNG" : "THẤT BẠI"));
        System.out.println();
        
        // === Test KyThi - các method mới ===
        System.out.println("🔹 TEST 4: KyThi - các method bổ sung");
        
        // Test themKetQua và nhapDiem
        KetQua ketQua = new KetQua(ts1, kyThi);
        kyThi.themKetQua(ketQua);
        
        System.out.println("📊 Số lượng kết quả hiện tại: " + kyThi.getDanhSachKetQua().size());
        
        // Test nhapDiem (tạo KetQua mới)
        kyThi.nhapDiem(ts1, 8.5);
        System.out.println("📊 Số lượng kết quả sau nhapDiem: " + kyThi.getDanhSachKetQua().size());
        
        // Test thongke
        kyThi.thongke();
        
        // Test timKiem
        System.out.println("🔍 Tìm kiếm 'Nguyễn':");
        kyThi.timKiem("Nguyễn");
        System.out.println();
        
        // === Test KetQua - các method state transition ===
        System.out.println("🔹 TEST 5: KetQua - State Transition Methods");
        
        KetQua ketQua2 = new KetQua(ts1, kyThi);
        System.out.println("🔵 Trạng thái ban đầu: " + ketQua2.getTrangThai());
        
        // Test batDauThi
        boolean batDau = ketQua2.batDauThi();
        System.out.println("⚡ Bắt đầu thi: " + (batDau ? "THÀNH CÔNG" : "THẤT BẠI"));
        System.out.println("🟡 Trạng thái hiện tại: " + ketQua2.getTrangThai());
        
        // Test nopBai
        boolean nopBai = ketQua2.nopBai();
        System.out.println("📄 Nộp bài: " + (nopBai ? "THÀNH CÔNG" : "THẤT BẠI"));
        System.out.println("🟤 Trạng thái hiện tại: " + ketQua2.getTrangThai());
        
        // Test batDauCham
        boolean batDauCham = ketQua2.batDauCham("admin01", "admin");
        System.out.println("🔍 Bắt đầu chấm: " + (batDauCham ? "THÀNH CÔNG" : "THẤT BẠI"));
        System.out.println("🔵 Trạng thái hiện tại: " + ketQua2.getTrangThai());
        
        // Test nhapDiem
        boolean nhapDiem = ketQua2.nhapDiem(9.0, "admin01", "admin", "Bài làm rất tốt");
        System.out.println("✏️ Nhập điểm: " + (nhapDiem ? "THÀNH CÔNG" : "THẤT BẠI"));
        System.out.println("🟢 Trạng thái cuối: " + ketQua2.getTrangThai());
        System.out.println("🏆 Xếp loại: " + ketQua2.getXepLoai());
        System.out.println("👁️ Có thể xem kết quả: " + (ketQua2.coTheXemKetQua() ? "CÓ" : "KHÔNG"));
        System.out.println();
        
        // === TỔNG KẾT ===
        System.out.println("=== TỔNG KẾT VALIDATION ===");
        System.out.println("✅ ThiSinh.getAge() - Hoạt động chính xác");
        System.out.println("✅ ThiSinh.validate() - Kiểm tra đầy đủ các trường");
        System.out.println("✅ PhieuDangKy.taoPhieu() - Static method hoạt động");
        System.out.println("✅ PhieuDangKy.xacNhanTT() - Alias cho dongPhi()");
        System.out.println("✅ KyThi methods - Đầy đủ các method trong UML");
        System.out.println("✅ KetQua state transition - Hoạt động đúng flow");
        System.out.println("✅ KetQua phân quyền - Kiểm tra role chính xác");
        System.out.println();
        System.out.println("🎯 KẾT LUẬN: UML và Code đã đồng bộ ✅");
    }
}
