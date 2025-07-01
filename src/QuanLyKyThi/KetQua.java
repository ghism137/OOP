package QuanLyKyThi;

import java.time.LocalDateTime;

public class KetQua {
    // Enum trạng thái bài thi
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
    
    private ThiSinh thiSinh;
    private KyThi kyThi;
    private double diem;
    private TrangThaiBaiThi trangThai;
    private String nguoiCham; // Username của người chấm điểm
    private LocalDateTime thoiGianBatDauThi;
    private LocalDateTime thoiGianNopBai;
    private LocalDateTime thoiGianCham;
    private String ghiChu;

    // Constructor mặc định - trạng thái chưa thi
    public KetQua(ThiSinh thiSinh, KyThi kyThi) {
        this.thiSinh = thiSinh;
        this.kyThi = kyThi;
        this.diem = 0.0;
        this.trangThai = TrangThaiBaiThi.CHUA_THI;
        this.nguoiCham = "";
        this.ghiChu = "";
    }
    
    // Constructor với điểm (cho dữ liệu có sẵn)
    public KetQua(ThiSinh thiSinh, KyThi kyThi, double diem) {
        this(thiSinh, kyThi);
        this.diem = diem;
        this.trangThai = TrangThaiBaiThi.DA_CHAM;
    }

    // Getters
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

    // Setters
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

    // Business methods
    
    /**
     * Bắt đầu làm bài thi
     */
    public boolean batDauThi() {
        if (trangThai == TrangThaiBaiThi.CHUA_THI) {
            this.trangThai = TrangThaiBaiThi.DANG_THI;
            this.thoiGianBatDauThi = LocalDateTime.now();
            return true;
        }
        return false;
    }
    
    /**
     * Nộp bài thi
     */
    public boolean nopBai() {
        if (trangThai == TrangThaiBaiThi.DANG_THI) {
            this.trangThai = TrangThaiBaiThi.DA_NOP_BAI;
            this.thoiGianNopBai = LocalDateTime.now();
            // Tự động chuyển sang trạng thái chưa chấm
            this.trangThai = TrangThaiBaiThi.CHUA_CHAM;
            return true;
        }
        return false;
    }
    
    /**
     * Bắt đầu chấm bài (chỉ Admin, Giáo vụ, Giám thị được phân công)
     */
    public boolean batDauCham(String nguoiChamUsername, String roleNguoiCham) {
        if (trangThai == TrangThaiBaiThi.CHUA_CHAM && 
            kiemTraQuyenCham(roleNguoiCham, nguoiChamUsername)) {
            this.trangThai = TrangThaiBaiThi.DANG_CHAM;
            this.nguoiCham = nguoiChamUsername;
            return true;
        }
        return false;
    }
    
    /**
     * Nhập điểm và hoàn thành chấm bài
     */
    public boolean nhapDiem(double diem, String nguoiChamUsername, String roleNguoiCham, String ghiChu) {
        if (trangThai == TrangThaiBaiThi.DANG_CHAM && 
            nguoiCham.equals(nguoiChamUsername) &&
            kiemTraQuyenCham(roleNguoiCham, nguoiChamUsername)) {
            
            if (diem >= 0 && diem <= 10) {
                this.diem = diem;
                this.trangThai = TrangThaiBaiThi.DA_CHAM;
                this.thoiGianCham = LocalDateTime.now();
                this.ghiChu = ghiChu != null ? ghiChu : "";
                return true;
            }
        }
        return false;
    }
    
    /**
     * Cập nhật điểm (chỉ người đã chấm hoặc Admin)
     */
    public boolean capNhatDiem(double diem, String nguoiCapNhatUsername, String roleNguoiCapNhat, String ghiChu) {
        if (trangThai == TrangThaiBaiThi.DA_CHAM && 
            (nguoiCham.equals(nguoiCapNhatUsername) || "admin".equals(roleNguoiCapNhat)) &&
            diem >= 0 && diem <= 10) {
            
            this.diem = diem;
            this.thoiGianCham = LocalDateTime.now();
            this.ghiChu = ghiChu != null ? ghiChu : "";
            return true;
        }
        return false;
    }
    
    /**
     * Kiểm tra quyền chấm bài
     */
    private boolean kiemTraQuyenCham(String role, String username) {
        switch (role.toLowerCase()) {
            case "admin":
                return true; // Admin có thể chấm tất cả
            case "giaovu":
                return true; // Giáo vụ có thể chấm tất cả
            case "giamthi":
                // Giám thị chỉ chấm kỳ thi được phân công
                return kiemTraGiamThiDuocPhanCong(username);
            default:
                return false;
        }
    }
    
    /**
     * Kiểm tra giám thị có được phân công cho kỳ thi này không
     */
    private boolean kiemTraGiamThiDuocPhanCong(String usernameGiamThi) {
        // Kiểm tra giám thị có trong danh sách giám thị của kỳ thi
        if (kyThi.getDanhSachGiamThi() != null) {
            for (GiamThi giamThi : kyThi.getDanhSachGiamThi()) {
                // Kiểm tra theo username, mã giám thị hoặc email
                if (giamThi.getUsername().equals(usernameGiamThi) ||
                    giamThi.getMaGiamThi().equals(usernameGiamThi) ||
                    giamThi.getEmail().equals(usernameGiamThi)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Lấy xếp loại dựa trên điểm
     */
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
    
    /**
     * Kiểm tra có thể xem kết quả không
     */
    public boolean coTheXemKetQua() {
        return trangThai == TrangThaiBaiThi.DA_CHAM;
    }
    
    @Override
    public String toString() {
        return String.format("KetQua{thiSinh=%s, kyThi=%s, diem=%.1f, trangThai=%s, nguoiCham='%s'}", 
                           thiSinh.getHoTen(), kyThi.getTenKyThi(), diem, trangThai.getMoTa(), nguoiCham);
    }
}
