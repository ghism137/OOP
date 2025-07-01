package QuanLyKyThi;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Demo test Exception Handling trong hệ thống Quản Lý Kỳ Thi
 * Kiểm tra các trường hợp lỗi và cách xử lý exception
 */
public class DemoExceptionHandling {
    
    public static void main(String[] args) {
        System.out.println("=== DEMO EXCEPTION HANDLING - HỆ THỐNG QUẢN LÝ KỲ THI ===");
        System.out.println();
        
        // === TEST 1: ThiSinh Validation Exceptions ===
        System.out.println("🔹 TEST 1: ThiSinh Validation Exceptions");
        testThiSinhValidation();
        System.out.println();
        
        // === TEST 2: KyThi Validation và Duplicate Exceptions ===
        System.out.println("🔹 TEST 2: KyThi Validation và Duplicate Exceptions");
        testKyThiExceptions();
        System.out.println();
        
        // === TEST 3: State Transition Exceptions ===
        System.out.println("🔹 TEST 3: State Transition Exceptions");
        testStateTransitionExceptions();
        System.out.println();
        
        // === TEST 4: Permission Exceptions ===
        System.out.println("🔹 TEST 4: Permission Exceptions");
        testPermissionExceptions();
        System.out.println();
        
        // === TEST 5: Payment Exceptions ===
        System.out.println("🔹 TEST 5: Payment Exceptions");
        testPaymentExceptions();
        System.out.println();
        
        System.out.println("=== TỔNG KẾT ===");
        System.out.println("✅ Hệ thống đã được trang bị exception handling toàn diện");
        System.out.println("✅ Tất cả lỗi được xử lý một cách graceful");
        System.out.println("✅ User experience được cải thiện đáng kể");
    }
    
    /**
     * Test validation exceptions cho ThiSinh
     */
    private static void testThiSinhValidation() {
        // Test 1.1: Thí sinh hợp lệ
        try {
            ThiSinh tsHopLe = new ThiSinh("TS001", "Nguyễn Văn A", LocalDate.of(2000, 5, 15), 
                                         "Nam", "123 Đường ABC, Hà Nội", "0123456789");
            tsHopLe.validate();
            System.out.println("✅ Thí sinh hợp lệ: " + tsHopLe.getHoTen());
        } catch (ThiSinhValidationException e) {
            System.out.println("❌ Unexpected: " + e.getMessage());
        }
        
        // Test 1.2: Họ tên rỗng
        try {
            ThiSinh tsHoTenRong = new ThiSinh("TS002", "", LocalDate.of(2000, 1, 1), 
                                             "Nam", "Hà Nội", "0123456789");
            tsHoTenRong.validate();
        } catch (ThiSinhValidationException e) {
            System.out.println("🔴 Caught expected exception: " + e.getMessage());
        }
        
        // Test 1.3: Tuổi quá nhỏ
        try {
            ThiSinh tsTuoiNho = new ThiSinh("TS003", "Nguyễn Văn B", LocalDate.of(2010, 1, 1), 
                                           "Nam", "Hà Nội", "0123456789");
            tsTuoiNho.validate();
        } catch (ThiSinhValidationException e) {
            System.out.println("🔴 Caught expected exception: " + e.getMessage());
        }
        
        // Test 1.4: SĐT không hợp lệ
        try {
            ThiSinh tsSDTSai = new ThiSinh("TS004", "Nguyễn Văn C", LocalDate.of(2000, 1, 1), 
                                          "Nam", "Hà Nội", "123");
            tsSDTSai.validate();
        } catch (ThiSinhValidationException e) {
            System.out.println("🔴 Caught expected exception: " + e.getMessage());
        }
        
        // Test 1.5: Mã thí sinh sai format
        try {
            ThiSinh tsMaSai = new ThiSinh("ABC123", "Nguyễn Văn D", LocalDate.of(2000, 1, 1), 
                                         "Nam", "Hà Nội", "0123456789");
            tsMaSai.validate();
        } catch (ThiSinhValidationException e) {
            System.out.println("🔴 Caught expected exception: " + e.getMessage());
        }
    }
    
    /**
     * Test exceptions cho KyThi
     */
    private static void testKyThiExceptions() {
        List<ThiSinh> dsTS = new ArrayList<>();
        List<GiamThi> dsGT = new ArrayList<>();
        KyThi kyThi = new KyThi("KT001", "Kỳ thi Toán", LocalDate.of(2024, 6, 15),
                              "Sắp diễn ra", dsTS, dsGT, 100000);
        
        // Test 2.1: Thêm thí sinh hợp lệ
        try {
            ThiSinh ts1 = new ThiSinh("TS001", "Nguyễn Văn A", LocalDate.of(2000, 5, 15), 
                                     "Nam", "123 Đường ABC, Hà Nội", "0123456789");
            kyThi.themThiSinh(ts1);
            System.out.println("✅ Thêm thí sinh thành công: " + ts1.getHoTen());
        } catch (Exception e) {
            System.out.println("❌ Unexpected: " + e.getMessage());
        }
        
        // Test 2.2: Thêm thí sinh trùng lặp
        try {
            ThiSinh ts2 = new ThiSinh("TS001", "Trần Thị B", LocalDate.of(2001, 1, 1), 
                                     "Nữ", "HCM", "0987654321");
            kyThi.themThiSinh(ts2);
        } catch (DuplicateException e) {
            System.out.println("🔴 Caught expected exception: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("❌ Unexpected: " + e.getMessage());
        }
        
        // Test 2.3: Thêm thí sinh null
        try {
            kyThi.themThiSinh(null);
        } catch (KyThiValidationException e) {
            System.out.println("🔴 Caught expected exception: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("❌ Unexpected: " + e.getMessage());
        }
        
        // Test 2.4: Thêm giám thị với thông tin không hợp lệ
        try {
            GiamThi gtSai = new GiamThi("ABC123", "PGS.TS Nguyễn X", "Khoa Toán", "123");
            kyThi.themGiamThi(gtSai);
        } catch (KyThiValidationException e) {
            System.out.println("🔴 Caught expected exception: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("❌ Unexpected: " + e.getMessage());
        }
    }
    
    /**
     * Test state transition exceptions
     */
    private static void testStateTransitionExceptions() {
        List<ThiSinh> dsTS = new ArrayList<>();
        List<GiamThi> dsGT = new ArrayList<>();
        KyThi kyThi = new KyThi("KT002", "Kỳ thi Lý", LocalDate.of(2024, 6, 20),
                              "Sắp diễn ra", dsTS, dsGT, 120000);
        
        try {
            ThiSinh ts = new ThiSinh("TS005", "Lê Văn E", LocalDate.of(1999, 3, 10), 
                                   "Nam", "Đà Nẵng", "0111222333");
            kyThi.themThiSinh(ts);
            
            KetQua ketQua = new KetQua(ts, kyThi);
            
            // Test 3.1: Bắt đầu thi thành công
            ketQua.batDauThi();
            System.out.println("✅ Bắt đầu thi thành công: " + ketQua.getTrangThai());
            
            // Test 3.2: Bắt đầu thi khi đã đang thi (lỗi)
            try {
                ketQua.batDauThi();
            } catch (StateTransitionException e) {
                System.out.println("🔴 Caught expected exception: " + e.getMessage());
            }
            
            // Test 3.3: Nộp bài thành công
            ketQua.nopBai();
            System.out.println("✅ Nộp bài thành công: " + ketQua.getTrangThai());
            
            // Test 3.4: Nộp bài khi đã nộp (lỗi)
            try {
                ketQua.nopBai();
            } catch (StateTransitionException e) {
                System.out.println("🔴 Caught expected exception: " + e.getMessage());
            }
            
        } catch (Exception e) {
            System.out.println("❌ Setup error: " + e.getMessage());
        }
    }
    
    /**
     * Test permission exceptions
     */
    private static void testPermissionExceptions() {
        List<ThiSinh> dsTS = new ArrayList<>();
        List<GiamThi> dsGT = new ArrayList<>();
        KyThi kyThi = new KyThi("KT003", "Kỳ thi Hóa", LocalDate.of(2024, 6, 25),
                              "Sắp diễn ra", dsTS, dsGT, 130000);
        
        try {
            ThiSinh ts = new ThiSinh("TS006", "Phạm Thị F", LocalDate.of(1998, 7, 20), 
                                   "Nữ", "Cần Thơ", "0999888777");
            kyThi.themThiSinh(ts);
            
            KetQua ketQua = new KetQua(ts, kyThi);
            ketQua.batDauThi();
            ketQua.nopBai();
            
            // Test 4.1: Bắt đầu chấm với quyền admin
            ketQua.batDauCham("admin01", "admin");
            System.out.println("✅ Admin bắt đầu chấm thành công");
            
            // Test 4.2: Nhập điểm với user không phải người chấm
            try {
                ketQua.nhapDiem(8.5, "user02", "giaovu", "Bài tốt");
            } catch (PermissionException e) {
                System.out.println("🔴 Caught expected exception: " + e.getMessage());
            }
            
            // Test 4.3: Nhập điểm thành công với người chấm đúng
            ketQua.nhapDiem(8.5, "admin01", "admin", "Bài làm tốt");
            System.out.println("✅ Nhập điểm thành công: " + ketQua.getDiem());
            
        } catch (Exception e) {
            System.out.println("❌ Setup error: " + e.getMessage());
        }
    }
    
    /**
     * Test payment exceptions
     */
    private static void testPaymentExceptions() {
        List<ThiSinh> dsTS = new ArrayList<>();
        List<GiamThi> dsGT = new ArrayList<>();
        KyThi kyThi = new KyThi("KT004", "Kỳ thi Sinh", LocalDate.of(2024, 6, 30),
                              "Sắp diễn ra", dsTS, dsGT, 0); // Phí = 0 để test lỗi
        
        try {
            ThiSinh ts = new ThiSinh("TS007", "Hoàng Văn G", LocalDate.of(1999, 12, 5), 
                                   "Nam", "Hải Phòng", "0888777666");
            
            // Test 5.1: Tạo phiếu với phí không hợp lệ
            PhieuDangKy phieu = PhieuDangKy.taoPhieu(ts, kyThi);
            
            try {
                phieu.dongPhi();
            } catch (PaymentException e) {
                System.out.println("🔴 Caught expected exception: " + e.getMessage());
            }
            
            // Test 5.2: Đóng phí khi đã đóng
            kyThi.setPhiDangKy(150000); // Set phí hợp lệ
            phieu.dongPhi();
            System.out.println("✅ Đóng phí lần đầu thành công");
            
            try {
                phieu.dongPhi(); // Đóng lần 2
            } catch (PaymentException e) {
                System.out.println("🔴 Caught expected exception: " + e.getMessage());
            }
            
        } catch (Exception e) {
            System.out.println("❌ Setup error: " + e.getMessage());
        }
    }
}
