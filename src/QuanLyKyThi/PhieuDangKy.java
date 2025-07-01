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
    public boolean dongPhi() throws PaymentException {
        if (daDongPhi) {
            throw new PaymentException("Phiếu " + maPhieuDangKy + " đã được đóng phí trước đó");
        }
        
        if (kyThi == null) {
            throw new PaymentException("Kỳ thi không hợp lệ");
        }
        
        double phiCanDong = tinhPhi();
        if (phiCanDong <= 0) {
            throw new PaymentException("Phí đăng ký không hợp lệ: " + phiCanDong);
        }
        
        this.daDongPhi = true;
        System.out.println("✅ Đã xác nhận đóng phí: " + phiCanDong + " VNĐ cho phiếu " + maPhieuDangKy);
        return true;
    }
    
    // Getter methods
    public String getMaPhieuDangKy() { return maPhieuDangKy; }
    public ThiSinh getThiSinh() { return thiSinh; }
    public KyThi getKyThi() { return kyThi; }
    public LocalDate getNgayDangKy() { return ngayDangKy; }
    public boolean isDaDongPhi() { return daDongPhi; }
    
    /**
     * Xác nhận thanh toán phí đăng ký (alias cho dongPhi())
     * @return true nếu thanh toán thành công
     * @throws PaymentException nếu có lỗi thanh toán
     */
    public boolean xacNhanTT() throws PaymentException {
        return dongPhi();
    }
    
    /**
     * Tạo phiếu đăng ký mới cho thí sinh và kỳ thi
     * @param thiSinh - Thí sinh đăng ký
     * @param kyThi - Kỳ thi được đăng ký
     * @return PhieuDangKy mới được tạo
     */
    public static PhieuDangKy taoPhieu(ThiSinh thiSinh, KyThi kyThi) {
        LocalDate ngayDangKy = LocalDate.now();
        PhieuDangKy phieu = new PhieuDangKy(thiSinh, kyThi, ngayDangKy);
        System.out.println("📋 Đã tạo phiếu đăng ký: " + phieu.getMaPhieuDangKy() + 
                          " cho thí sinh " + thiSinh.getHoTen() + 
                          " kỳ thi " + kyThi.getTenKyThi());
        return phieu;
    }
}
