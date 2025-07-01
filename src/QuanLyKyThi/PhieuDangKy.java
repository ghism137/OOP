package QuanLyKyThi;

import java.time.LocalDate;

public class PhieuDangKy {
    private static int dem  = 0;
    private String maPhieuDangKy;
    private ThiSinh thiSinh;
    private KyThi kyThi;
    private LocalDate ngayDangKy;
    private boolean daDongPhi; // Trạng thái đóng phí

    public PhieuDangKy(ThiSinh thiSinh, KyThi kyThi, LocalDate ngayDangKy) {
        this.maPhieuDangKy = "PDK" + String.format("%03d", ++dem);
        this.thiSinh = thiSinh;
        this.kyThi = kyThi;
        this.ngayDangKy = ngayDangKy;
        this.daDongPhi = false; // Mặc định chưa đóng phí
    }

    public double tinhPhi() {
        // Lấy phí đăng ký từ kỳ thi cụ thể
        return kyThi.getPhiDangKy();
    }
    
    // Phương thức xác nhận đóng phí
    public boolean dongPhi() {
        if (!daDongPhi) {
            this.daDongPhi = true;
            System.out.println("✅ Đã xác nhận đóng phí: " + tinhPhi() + "k VNĐ cho phiếu " + maPhieuDangKy);
            return true;
        } else {
            System.out.println("⚠️ Phiếu " + maPhieuDangKy + " đã được đóng phí trước đó!");
            return false;
        }
    }
    
    // Getter methods
    public String getMaPhieuDangKy() { return maPhieuDangKy; }
    public ThiSinh getThiSinh() { return thiSinh; }
    public KyThi getKyThi() { return kyThi; }
    public LocalDate getNgayDangKy() { return ngayDangKy; }
    public boolean isDaDongPhi() { return daDongPhi; }
}
