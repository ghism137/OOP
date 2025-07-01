package QuanLyKyThi;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Demo tạo dữ liệu mẫu với trạng thái bài thi và test form quản lý
 */
public class TrangThaiBaiThiDemo {
    
    public static void main(String[] args) {
        System.out.println("=== DEMO QUẢN LÝ TRẠNG THÁI BÀI THI ===");
        
        // 1. Tạo dữ liệu mẫu
        createSampleDataWithTrangThai();
        
        // 2. Hiển thị thông tin dữ liệu vừa tạo
        displaySampleData();
        
        // 3. Mở form quản lý trạng thái
        openTrangThaiManagementForm();
    }
    
    private static void createSampleDataWithTrangThai() {
        System.out.println("\n📝 Tạo dữ liệu mẫu với trạng thái bài thi...");
        
        XMLDatabase database = new XMLDatabase();
        
        // Tạo kỳ thi
        List<KyThi> kyThiList = new ArrayList<>();
        KyThi kyThi1 = new KyThi("KT001", "Kỳ thi Toán học", LocalDate.now().plusDays(1), 
                                "Đang diễn ra", new ArrayList<>(), new ArrayList<>(), 50000);
        KyThi kyThi2 = new KyThi("KT002", "Kỳ thi Văn học", LocalDate.now().plusDays(7), 
                                "Sắp diễn ra", new ArrayList<>(), new ArrayList<>(), 45000);
        kyThiList.add(kyThi1);
        kyThiList.add(kyThi2);
        
        // Tạo thí sinh
        List<ThiSinh> thiSinhList = new ArrayList<>();
        ThiSinh ts1 = new ThiSinh("TS001", "Nguyễn Văn An", LocalDate.of(2000, 5, 15), 
                                  "Nam", "Hà Nội", "0123456789");
        ThiSinh ts2 = new ThiSinh("TS002", "Trần Thị Bình", LocalDate.of(2001, 3, 20), 
                                  "Nữ", "TP.HCM", "0987654321");
        ThiSinh ts3 = new ThiSinh("TS003", "Lê Văn Cường", LocalDate.of(2000, 8, 10), 
                                  "Nam", "Đà Nẵng", "0123987456");
        thiSinhList.add(ts1);
        thiSinhList.add(ts2);
        thiSinhList.add(ts3);
        
        // Tạo giám thị với thông tin liên kết user
        List<GiamThi> giamThiList = new ArrayList<>();
        GiamThi gt1 = new GiamThi("GT001", "Phạm Văn Đức", "Phòng Giáo dục", "0123456999", 
                                  "giamthi1@email.com", "giamthi1");
        GiamThi gt2 = new GiamThi("GT002", "Lê Thị Hoa", "Phòng Giáo dục", "0987654999", 
                                  "giamthi2@email.com", "giamthi2");
        giamThiList.add(gt1);
        giamThiList.add(gt2);
        
        // Phân công giám thị cho kỳ thi
        kyThi1.themGiamThi(gt1);
        kyThi1.themGiamThi(gt2);
        kyThi2.themGiamThi(gt1);
        
        // Đăng ký thí sinh cho kỳ thi
        kyThi1.themThiSinh(ts1);
        kyThi1.themThiSinh(ts2);
        kyThi1.themThiSinh(ts3);
        kyThi2.themThiSinh(ts1);
        kyThi2.themThiSinh(ts2);
        
        // Tạo kết quả với các trạng thái khác nhau
        List<KetQua> ketQuaList = new ArrayList<>();
        
        // Thí sinh 1 - Đã hoàn thành và chấm điểm
        KetQua kq1 = new KetQua(ts1, kyThi1, 8.5);
        kq1.setTrangThai(KetQua.TrangThaiBaiThi.DA_CHAM);
        kq1.setNguoiCham("giaovu");
        kq1.setThoiGianBatDauThi(LocalDateTime.now().minusHours(4));
        kq1.setThoiGianNopBai(LocalDateTime.now().minusHours(2));
        kq1.setThoiGianCham(LocalDateTime.now().minusHours(1));
        kq1.setGhiChu("Bài làm tốt, có sáng tạo");
        ketQuaList.add(kq1);
        
        // Thí sinh 2 - Đã nộp bài nhưng chưa chấm
        KetQua kq2 = new KetQua(ts2, kyThi1);
        kq2.setTrangThai(KetQua.TrangThaiBaiThi.CHUA_CHAM);
        kq2.setThoiGianBatDauThi(LocalDateTime.now().minusHours(3));
        kq2.setThoiGianNopBai(LocalDateTime.now().minusHours(1));
        kq2.setGhiChu("Đã nộp bài, chờ chấm");
        ketQuaList.add(kq2);
        
        // Thí sinh 3 - Đang trong quá trình thi
        KetQua kq3 = new KetQua(ts3, kyThi1);
        kq3.setTrangThai(KetQua.TrangThaiBaiThi.DANG_THI);
        kq3.setThoiGianBatDauThi(LocalDateTime.now().minusMinutes(30));
        kq3.setGhiChu("Đang làm bài");
        ketQuaList.add(kq3);
        
        // Thí sinh cho kỳ thi 2 - chưa thi
        KetQua kq4 = new KetQua(ts1, kyThi2);
        kq4.setTrangThai(KetQua.TrangThaiBaiThi.CHUA_THI);
        kq4.setGhiChu("Sẵn sàng thi");
        ketQuaList.add(kq4);
        
        KetQua kq5 = new KetQua(ts2, kyThi2);
        kq5.setTrangThai(KetQua.TrangThaiBaiThi.CHUA_THI);
        kq5.setGhiChu("Sẵn sàng thi");
        ketQuaList.add(kq5);
        
        // Lưu dữ liệu vào XML
        database.saveKyThi(kyThiList);
        database.saveThiSinh(thiSinhList);
        database.saveGiamThi(giamThiList);
        database.saveKetQua(ketQuaList);
        
        System.out.println("✅ Đã tạo xong dữ liệu mẫu!");
        System.out.println("   - " + kyThiList.size() + " kỳ thi");
        System.out.println("   - " + thiSinhList.size() + " thí sinh");
        System.out.println("   - " + giamThiList.size() + " giám thị");
        System.out.println("   - " + ketQuaList.size() + " kết quả với trạng thái khác nhau");
    }
    
    private static void displaySampleData() {
        System.out.println("\n📊 Hiển thị dữ liệu mẫu vừa tạo...");
        
        XMLDatabase database = new XMLDatabase();
        List<KetQua> ketQuaList = database.getAllKetQua();
        
        System.out.println("\n📋 DANH SÁCH KẾT QUẢ VÀ TRẠNG THÁI:");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.printf("%-4s %-15s %-15s %-15s %-8s %-15s %-20s%n", 
                         "STT", "Thí sinh", "Kỳ thi", "Trạng thái", "Điểm", "Người chấm", "Ghi chú");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        
        for (int i = 0; i < ketQuaList.size(); i++) {
            KetQua kq = ketQuaList.get(i);
            String tenThiSinh = kq.getThiSinh().getHoTen();
            String tenKyThi = kq.getKyThi().getTenKyThi();
            String trangThai = kq.getTrangThai().getMoTa();
            String diem = kq.getTrangThai() == KetQua.TrangThaiBaiThi.DA_CHAM ? 
                         String.format("%.1f", kq.getDiem()) : "---";
            String nguoiCham = kq.getNguoiCham().isEmpty() ? "---" : kq.getNguoiCham();
            String ghiChu = kq.getGhiChu().length() > 15 ? 
                           kq.getGhiChu().substring(0, 15) + "..." : kq.getGhiChu();
            
            System.out.printf("%-4d %-15s %-15s %-15s %-8s %-15s %-20s%n", 
                             i+1, tenThiSinh, tenKyThi, trangThai, diem, nguoiCham, ghiChu);
        }
        
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        
        // Thống kê trạng thái
        System.out.println("\n📈 THỐNG KÊ TRẠNG THÁI:");
        int chuaThi = 0, dangThi = 0, daNopBai = 0, chuaCham = 0, dangCham = 0, daCham = 0;
        
        for (KetQua kq : ketQuaList) {
            switch (kq.getTrangThai()) {
                case CHUA_THI: chuaThi++; break;
                case DANG_THI: dangThi++; break;
                case DA_NOP_BAI: daNopBai++; break;
                case CHUA_CHAM: chuaCham++; break;
                case DANG_CHAM: dangCham++; break;
                case DA_CHAM: daCham++; break;
            }
        }
        
        System.out.println("   🔘 Chưa thi: " + chuaThi);
        System.out.println("   🟡 Đang thi: " + dangThi);
        System.out.println("   🟤 Đã nộp bài: " + daNopBai);
        System.out.println("   🟠 Chưa chấm: " + chuaCham);
        System.out.println("   🔵 Đang chấm: " + dangCham);
        System.out.println("   🟢 Đã chấm: " + daCham);
    }
    
    private static void openTrangThaiManagementForm() {
        System.out.println("\n🖥️  Mở form quản lý trạng thái bài thi...");
        
        javax.swing.SwingUtilities.invokeLater(() -> {
            try {
                javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            // Tạo user admin để test
            User adminUser = new User("admin", "admin123", "Quản trị viên", "admin@email.com", "admin");
            
            // Mở form quản lý trạng thái
            QuanLyTrangThaiBaiThiForm form = new QuanLyTrangThaiBaiThiForm(adminUser);
            form.setVisible(true);
            
            System.out.println("✅ Form đã được mở!");
            System.out.println("   📌 Bạn có thể:");
            System.out.println("      - Xem danh sách kết quả theo kỳ thi");
            System.out.println("      - Bắt đầu thi cho thí sinh");
            System.out.println("      - Nộp bài cho thí sinh");
            System.out.println("      - Bắt đầu chấm bài");
            System.out.println("      - Nhập điểm và hoàn thành chấm");
            System.out.println("      - Cập nhật điểm (chỉ Admin)");
        });
    }
    
    /**
     * Test phân quyền với các loại user khác nhau
     */
    public static void testUserPermissions() {
        System.out.println("\n🔐 Test phân quyền với các user khác nhau...");
        
        // Test với giáo vụ
        User giaoVuUser = new User("giaovu", "gv123", "Phòng Giáo vụ", "giaovu@email.com", "giaovu");
        QuanLyTrangThaiBaiThiForm giaoVuForm = new QuanLyTrangThaiBaiThiForm(giaoVuUser);
        giaoVuForm.setTitle("Quản Lý Trạng Thái - Giáo vụ");
        giaoVuForm.setLocation(100, 100);
        giaoVuForm.setVisible(true);
        
        // Test với giám thị
        User giamThiUser = new User("giamthi1", "gt123", "Phạm Văn Đức", "giamthi1@email.com", "giamthi");
        QuanLyTrangThaiBaiThiForm giamThiForm = new QuanLyTrangThaiBaiThiForm(giamThiUser);
        giamThiForm.setTitle("Quản Lý Trạng Thái - Giám thị");
        giamThiForm.setLocation(300, 100);
        giamThiForm.setVisible(true);
        
        System.out.println("✅ Đã mở form với 2 quyền khác nhau để so sánh!");
    }
}
