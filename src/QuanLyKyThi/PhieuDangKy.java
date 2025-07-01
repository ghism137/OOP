package QuanLyKyThi;

import java.time.LocalDate;

public class PhieuDangKy {
    private static int dem  = 0;
    private String maPhieuDangKy;
    private ThiSinh thiSinh;
    private KyThi kyThi;
    private LocalDate ngayDangKy;

    public PhieuDangKy(ThiSinh thiSinh, KyThi kyThi, LocalDate ngayDangKy) {
        this.maPhieuDangKy = "PDK" + dem++;
        this.thiSinh = thiSinh;
        this.kyThi = kyThi;
        this.ngayDangKy = ngayDangKy;
    }

    public int tinhPhi() {
        // Giả sử phí đăng ký là 150.000 VNĐ
        return 150;
    }
}
