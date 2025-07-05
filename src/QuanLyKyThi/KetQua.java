package QuanLyKyThi;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDateTime;

@XmlRootElement(name = "ketqua")
@XmlAccessorType(XmlAccessType.FIELD)
public class KetQua {

    public enum TrangThaiBaiThi {
        CHUA_THI("Chưa thi"),
        DANG_THI("Đang thi"),
        DA_NOP_BAI("Đã nộp bài"),
        CHUA_CHAM("Chưa chấm bài"),
        DANG_CHAM("Đang chấm bài"),
        DA_CHAM("Đã chấm bài");

        private final String moTa;

        TrangThaiBaiThi(String moTa) {
            this.moTa = moTa;
        }

        public String getMoTa() {
            return moTa;
        }
    }

    @XmlTransient
    private ThiSinh thiSinh;
    @XmlTransient
    private KyThi kyThi;

    private String maThiSinh;
    private String maKyThi;

    private double diem;
    @XmlElement
    private TrangThaiBaiThi trangThai;
    private String nguoiCham;
    @XmlJavaTypeAdapter(DateAdapters.LocalDateTimeAdapter.class)
    private LocalDateTime thoiGianBatDauThi;
    @XmlJavaTypeAdapter(DateAdapters.LocalDateTimeAdapter.class)
    private LocalDateTime thoiGianNopBai;
    @XmlJavaTypeAdapter(DateAdapters.LocalDateTimeAdapter.class)
    private LocalDateTime thoiGianCham;
    private String ghiChu;

    public KetQua() {}

    public KetQua(ThiSinh thiSinh, KyThi kyThi) {
        this.thiSinh = thiSinh;
        this.kyThi = kyThi;
        this.maThiSinh = thiSinh.getSoBaoDanh();
        this.maKyThi = kyThi.getMaKyThi();
        this.diem = 0.0;
        this.trangThai = TrangThaiBaiThi.CHUA_THI;
        this.nguoiCham = "";
        this.ghiChu = "";
    }

    public KetQua(ThiSinh thiSinh, KyThi kyThi, double diem) {
        this(thiSinh, kyThi);
        this.diem = diem;
        this.trangThai = TrangThaiBaiThi.DA_CHAM;
    }

    public ThiSinh getThiSinh() {
        return thiSinh;
    }

    public KyThi getKyThi() {
        return kyThi;
    }

    public double getDiem() {
        return diem;
    }

    public TrangThaiBaiThi getTrangThai() {
        return trangThai;
    }

    public String getNguoiCham() {
        return nguoiCham;
    }

    public LocalDateTime getThoiGianBatDauThi() {
        return thoiGianBatDauThi;
    }

    public LocalDateTime getThoiGianNopBai() {
        return thoiGianNopBai;
    }

    public LocalDateTime getThoiGianCham() {
        return thoiGianCham;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setKyThi(KyThi kyThi) {
        this.kyThi = kyThi;
    }

    public void setTrangThai(TrangThaiBaiThi trangThai) {
        this.trangThai = trangThai;
    }

    public void setNguoiCham(String nguoiCham) {
        this.nguoiCham = nguoiCham;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public void setThoiGianBatDauThi(LocalDateTime thoiGianBatDauThi) {
        this.thoiGianBatDauThi = thoiGianBatDauThi;
    }

    public void setThoiGianNopBai(LocalDateTime thoiGianNopBai) {
        this.thoiGianNopBai = thoiGianNopBai;
    }

    public void setThoiGianCham(LocalDateTime thoiGianCham) {
        this.thoiGianCham = thoiGianCham;
    }

    public void batDauThi() throws Exceptions.StateTransitionException {
        if (trangThai != TrangThaiBaiThi.CHUA_THI) {
            throw new Exceptions.StateTransitionException("Không thể bắt đầu thi từ trạng thái: " + trangThai + ". Chỉ có thể bắt đầu từ trạng thái CHƯA_THI");
        }
        this.trangThai = TrangThaiBaiThi.DANG_THI;
        this.thoiGianBatDauThi = LocalDateTime.now();
    }

    public void nopBai() throws Exceptions.StateTransitionException {
        if (trangThai != TrangThaiBaiThi.DANG_THI) {
            throw new Exceptions.StateTransitionException("Không thể nộp bài từ trạng thái: " + trangThai + ". Chỉ có thể nộp khi đang thi");
        }
        this.trangThai = TrangThaiBaiThi.DA_NOP_BAI;
        this.thoiGianNopBai = LocalDateTime.now();
        this.trangThai = TrangThaiBaiThi.CHUA_CHAM;
    }

    public void batDauCham(String nguoiChamUsername, String roleNguoiCham) throws Exceptions.StateTransitionException, Exceptions.PermissionException {
        if (trangThai != TrangThaiBaiThi.CHUA_CHAM) {
            throw new Exceptions.StateTransitionException("Không thể bắt đầu chấm từ trạng thái: " + trangThai + ". Chỉ có thể chấm khi ở trạng thái CHƯA_CHẤM");
        }
        if (nguoiChamUsername == null || nguoiChamUsername.trim().isEmpty()) {
            throw new Exceptions.PermissionException("Username người chấm không được để trống");
        }
        if (!kiemTraQuyenCham(roleNguoiCham, nguoiChamUsername)) {
            throw new Exceptions.PermissionException("User " + nguoiChamUsername + " với role " + roleNguoiCham + " không có quyền chấm bài thi này");
        }
        this.trangThai = TrangThaiBaiThi.DANG_CHAM;
        this.nguoiCham = nguoiChamUsername;
        this.thoiGianCham = LocalDateTime.now();
    }

    public void nhapDiem(double diem, String nguoiChamUsername, String roleNguoiCham, String ghiChu) throws Exceptions.StateTransitionException, Exceptions.PermissionException, Exceptions.QuanLyKyThiException {
        if (trangThai != TrangThaiBaiThi.DANG_CHAM) {
            throw new Exceptions.StateTransitionException("Không thể nhập điểm từ trạng thái: " + trangThai + ". Chỉ có thể nhập điểm khi đang chấm");
        }
        if (nguoiCham == null || !nguoiCham.equals(nguoiChamUsername)) {
            throw new Exceptions.PermissionException("Chỉ người đã bắt đầu chấm (" + nguoiCham + ") mới có thể nhập điểm");
        }
        if (!kiemTraQuyenCham(roleNguoiCham, nguoiChamUsername)) {
            throw new Exceptions.PermissionException("User " + nguoiChamUsername + " không có quyền nhập điểm");
        }
        if (diem < 0 || diem > 10) {
            throw new Exceptions.QuanLyKyThiException("Điểm phải trong khoảng 0-10 (điểm nhập: " + diem + ")");
        }
        this.diem = diem;
        this.trangThai = TrangThaiBaiThi.DA_CHAM;
        this.thoiGianCham = LocalDateTime.now();
        this.ghiChu = ghiChu != null ? ghiChu : "";
    }

    public void capNhatDiem(double diem, String nguoiCapNhatUsername, String roleNguoiCapNhat, String ghiChu) throws Exceptions.StateTransitionException, Exceptions.PermissionException, Exceptions.QuanLyKyThiException {
        if (trangThai != TrangThaiBaiThi.DA_CHAM) {
            throw new Exceptions.StateTransitionException("Chỉ có thể cập nhật điểm khi đã chấm xong");
        }
        boolean coQuyen = "admin".equals(roleNguoiCapNhat) || "giaovu".equals(roleNguoiCapNhat) || (nguoiCham != null && nguoiCham.equals(nguoiCapNhatUsername));
        if (!coQuyen) {
            throw new Exceptions.PermissionException("Chỉ Admin, Giáo vụ hoặc người đã chấm mới có thể cập nhật điểm");
        }
        if (diem < 0 || diem > 10) {
            throw new Exceptions.QuanLyKyThiException("Điểm phải trong khoảng 0-10 (điểm cập nhật: " + diem + ")");
        }
        this.diem = diem;
        this.thoiGianCham = LocalDateTime.now();
        this.ghiChu = ghiChu != null ? ghiChu : "";
    }

    private boolean kiemTraQuyenCham(String role, String username) {
        switch (role.toLowerCase()) {
            case "admin":
            case "giaovu":
                return true;
            case "giamthi":
                return kiemTraGiamThiDuocPhanCong(username);
            default:
                return false;
        }
    }

    private boolean kiemTraGiamThiDuocPhanCong(String usernameGiamThi) {
        if (kyThi.getDanhSachGiamThi() != null) {
            for (GiamThi giamThi : kyThi.getDanhSachGiamThi()) {
                if (giamThi.getUsername().equals(usernameGiamThi) || giamThi.getMaGiamThi().equals(usernameGiamThi) || giamThi.getEmail().equals(usernameGiamThi)) {
                    return true;
                }
            }
        }
        return false;
    }

    public String getXepLoai() {
        if (trangThai != TrangThaiBaiThi.DA_CHAM) {
            return "Chưa có kết quả";
        }
        if (diem >= 9.0) return "Xuất sắc";
        else if (diem >= 8.0) return "Giỏi";
        else if (diem >= 6.5) return "Khá";
        else if (diem >= 5.0) return "Trung bình";
        else return "Yếu";
    }

    public boolean coTheXemKetQua() {
        return trangThai == TrangThaiBaiThi.DA_CHAM;
    }

    @Override
    public String toString() {
        return String.format("KetQua{thiSinh=%s, kyThi=%s, diem=%.1f, trangThai=%s, nguoiCham='%s'}", thiSinh.getHoTen(), kyThi.getTenKyThi(), diem, trangThai.getMoTa(), nguoiCham);
    }
}