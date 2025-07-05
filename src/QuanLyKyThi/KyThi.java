package QuanLyKyThi;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

@XmlRootElement(name = "kythi")
@XmlAccessorType(XmlAccessType.FIELD)
public class KyThi {
    private String maKyThi;
    private String tenKyThi;
    @XmlJavaTypeAdapter(DateAdapters.LocalDateAdapter.class)
    private LocalDate ngayToChuc;
    private String tinhTrang;
    private double phiDangKy;

    @XmlElementWrapper(name = "danhSachThiSinh")
    @XmlElement(name = "thiSinh")
    private List<ThiSinh> danhSachThiSinh;
    @XmlElementWrapper(name = "danhSachGiamThi")
    @XmlElement(name = "giamThi")
    private List<GiamThi> danhSachGiamThi;
    @XmlElementWrapper(name = "danhSachKetQua")
    @XmlElement(name = "ketQua")
    private List<KetQua> danhSachKetQua;

    public KyThi() {
        this.danhSachThiSinh = new ArrayList<>();
        this.danhSachGiamThi = new ArrayList<>();
        this.danhSachKetQua = new ArrayList<>();
    }

    public KyThi(String maKyThi, String tenKythi, LocalDate ngayToChuc, String tinhTrang, List<ThiSinh> danhSachThiSinh, List<GiamThi> danhSachGiamThi, double phiDangKy) {
        this.maKyThi = maKyThi;
        this.tenKyThi = tenKythi;
        this.ngayToChuc = ngayToChuc;
        this.tinhTrang = tinhTrang;
        this.phiDangKy = phiDangKy;
        this.danhSachThiSinh = danhSachThiSinh;
        this.danhSachGiamThi = danhSachGiamThi;
        this.danhSachKetQua = new ArrayList<>();
    }

    public boolean themThiSinh(ThiSinh thisinh) throws Exceptions.KyThiValidationException, Exceptions.DuplicateException, Exceptions.ThiSinhValidationException {
        if (thisinh == null) {
            throw new Exceptions.KyThiValidationException("Thí sinh không được null");
        }

        if (kiemTraTrung(thisinh.getSoBaoDanh(), "THISINH")) {
            throw new Exceptions.DuplicateException("Thí sinh với mã " + thisinh.getSoBaoDanh() + " đã đăng ký kỳ thi này");
        }

        if (!"Sắp diễn ra".equals(tinhTrang)) {
            throw new Exceptions.KyThiValidationException("Không thể đăng ký kỳ thi ở trạng thái: " + tinhTrang + ". Chỉ chấp nhận trạng thái 'Sắp diễn ra'");
        }

        danhSachThiSinh.add(thisinh);
        return true;
    }

    public boolean themGiamThi(GiamThi giamthi) throws Exceptions.KyThiValidationException, Exceptions.DuplicateException {
        if (giamthi == null) {
            throw new Exceptions.KyThiValidationException("Giám thị không được null");
        }

        try {
            validateGiamThi(giamthi);
        } catch (Exceptions.GiamThiValidationException e) {
            throw new Exceptions.KyThiValidationException("Thông tin giám thị không hợp lệ: " + e.getMessage());
        }

        if (kiemTraTrung(giamthi.getMaGiamThi(), "GIAMTHI")) {
            throw new Exceptions.DuplicateException("Giám thị với mã " + giamthi.getMaGiamThi() + " đã được phân công cho kỳ thi này");
        }

        danhSachGiamThi.add(giamthi);
        return true;
    }

    private void validateGiamThi(GiamThi giamThi) throws Exceptions.GiamThiValidationException {
        if (giamThi.getMaGiamThi() == null || giamThi.getMaGiamThi().trim().isEmpty()) {
            throw new Exceptions.GiamThiValidationException("Mã giám thị không được để trống");
        }
        if (!giamThi.getMaGiamThi().matches("^GT\\d{3,}$")) {
            throw new Exceptions.GiamThiValidationException("Mã giám thị phải có định dạng GTxxx (ví dụ: GT001)");
        }
        if (giamThi.getHoTen() == null || giamThi.getHoTen().trim().isEmpty()) {
            throw new Exceptions.GiamThiValidationException("Họ tên giám thị không được để trống");
        }
        if (giamThi.getSDT() == null || !giamThi.getSDT().matches("^0\\d{9}$")) {
            throw new Exceptions.GiamThiValidationException("Số điện thoại giám thị phải có 10 số và bắt đầu bằng 0");
        }
        if (giamThi.getEmail() != null && !giamThi.getEmail().trim().isEmpty()) {
            if (!giamThi.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
                throw new Exceptions.GiamThiValidationException("Email giám thị không đúng định dạng");
            }
        }
    }

    public List<ThiSinh> getDanhSachThiSinh() {
        return danhSachThiSinh;
    }

    public List<GiamThi> getDanhSachGiamThi() {
        return danhSachGiamThi;
    }

    public List<KetQua> getDanhSachKetQua() {
        return danhSachKetQua;
    }

    public void themKetQua(KetQua ketQua) {
        danhSachKetQua.add(ketQua);
    }

    public void nhapDiem(ThiSinh thiSinh, double diem) {
        KetQua ketQua = new KetQua(thiSinh, this, diem);
        themKetQua(ketQua);
    }

    public String getMaKyThi() {
        return maKyThi;
    }

    public String getTenKyThi() {
        return tenKyThi;
    }

    public LocalDate getNgayToChuc() {
        return ngayToChuc;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public double getPhiDangKy() {
        return phiDangKy;
    }

    public void setPhiDangKy(double phiDangKy) {
        this.phiDangKy = phiDangKy;
    }

    public void setDanhSachGiamThi(List<GiamThi> danhSachGiamThi) {
        this.danhSachGiamThi = danhSachGiamThi;
    }

    public void setDanhSachKetQua(List<KetQua> danhSachKetQua) {
        this.danhSachKetQua = danhSachKetQua;
    }

    public void tinhKetQua() {
        System.out.println("Đang tính kết quả cho kỳ thi: " + tenKyThi);
    }

    public void thongke() {
        System.out.println("Tổng số thí sinh: " + danhSachThiSinh.size());
        System.out.println("Tổng số giám thị: " + danhSachGiamThi.size());
        System.out.println("Tổng số kết quả: " + danhSachKetQua.size());
    }

    public void timKiem(String keyword) {
        for (ThiSinh ts : danhSachThiSinh) {
            if (ts.getHoTen().toLowerCase().contains(keyword.toLowerCase())) {
                System.out.println("Tìm thấy: " + ts.getHoTen());
            }
        }
    }

    public boolean kiemTraTrung(String ma, String loai) {
        if ("THISINH".equals(loai)) {
            for (ThiSinh ts : danhSachThiSinh) {
                if (ts.getSoBaoDanh().equals(ma)) {
                    return true;
                }
            }
        } else if ("GIAMTHI".equals(loai)) {
            for (GiamThi gt : danhSachGiamThi) {
                if (gt.getMaGiamThi().equals(ma)) {
                    return true;
                }
            }
        }
        return false;
    }
}