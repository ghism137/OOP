/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package QuanLyKyThi;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author ad
 */
public class KyThi {
    private String maKyThi;
    private String tenKyThi;
    private LocalDate ngayToChuc;
    private String tinhTrang;
    private double phiDangKy; // Thêm phí đăng ký riêng cho từng kỳ thi
    
    private List<ThiSinh> danhSachThiSinh;
    private List<GiamThi> danhSachGiamThi;
    private List<KetQua> danhSachKetQua;

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
    
    // Phương thức để thêm thí sinh và giám thị vào danh sách
    
    // Phương thức để thêm thí sinh với kiểm tra trùng lặp
    public boolean themThiSinh(ThiSinh thisinh) throws KyThiValidationException, DuplicateException, ThiSinhValidationException {
        // Kiểm tra null input
        if (thisinh == null) {
            throw new KyThiValidationException("Thí sinh không được null");
        }
        
        // Validate thông tin thí sinh trước
        try {
            thisinh.validate();
        } catch (ThiSinhValidationException e) {
            throw e; // Re-throw để caller xử lý
        }
        
        // Kiểm tra thí sinh đã đăng ký chưa sử dụng method kiemTraTrung()
        if (kiemTraTrung(thisinh.getMaThisinh(), "THISINH")) {
            throw new DuplicateException("Thí sinh với mã " + thisinh.getMaThisinh() + " đã đăng ký kỳ thi này");
        }
        
        // Kiểm tra trạng thái kỳ thi - chỉ cho phép đăng ký khi "Sắp diễn ra"
        if (!"Sắp diễn ra".equals(tinhTrang)) {
            throw new KyThiValidationException("Không thể đăng ký kỳ thi ở trạng thái: " + tinhTrang + ". Chỉ chấp nhận trạng thái 'Sắp diễn ra'");
        }
        
        danhSachThiSinh.add(thisinh);
        return true; // Đăng ký thành công
    }
    
    public boolean themGiamThi(GiamThi giamthi) throws KyThiValidationException, DuplicateException {
        // Kiểm tra null input
        if (giamthi == null) {
            throw new KyThiValidationException("Giám thị không được null");
        }
        
        // Validate thông tin giám thị
        try {
            validateGiamThi(giamthi);
        } catch (GiamThiValidationException e) {
            throw new KyThiValidationException("Thông tin giám thị không hợp lệ: " + e.getMessage());
        }
        
        // Kiểm tra giám thị đã được phân công chưa sử dụng method kiemTraTrung()
        if (kiemTraTrung(giamthi.getMaGiamThi(), "GIAMTHI")) {
            throw new DuplicateException("Giám thị với mã " + giamthi.getMaGiamThi() + " đã được phân công cho kỳ thi này");
        }
        
        danhSachGiamThi.add(giamthi);
        return true;
    }
    
    /**
     * Validate thông tin giám thị
     */
    private void validateGiamThi(GiamThi giamThi) throws GiamThiValidationException {
        if (giamThi.getMaGiamThi() == null || giamThi.getMaGiamThi().trim().isEmpty()) {
            throw new GiamThiValidationException("Mã giám thị không được để trống");
        }
        
        if (!giamThi.getMaGiamThi().matches("^GT\\d{3,}$")) {
            throw new GiamThiValidationException("Mã giám thị phải có định dạng GTxxx (ví dụ: GT001)");
        }
        
        if (giamThi.getHoTen() == null || giamThi.getHoTen().trim().isEmpty()) {
            throw new GiamThiValidationException("Họ tên giám thị không được để trống");
        }
        
        if (giamThi.getSDT() == null || !giamThi.getSDT().matches("^0\\d{9}$")) {
            throw new GiamThiValidationException("Số điện thoại giám thị phải có 10 số và bắt đầu bằng 0");
        }
        
        // Validate email nếu có
        if (giamThi.getEmail() != null && !giamThi.getEmail().trim().isEmpty()) {
            if (!giamThi.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
                throw new GiamThiValidationException("Email giám thị không đúng định dạng");
            }
        }
    }
    
    // Getter methods
    public List<ThiSinh> getDanhSachThiSinh() {
        return danhSachThiSinh;
    }
    
    public List<GiamThi> getDanhSachGiamThi() {
        return danhSachGiamThi;
    }
    
    public List<KetQua> getDanhSachKetQua() {
        return danhSachKetQua;
    }
    
    // Method để thêm kết quả
    public void themKetQua(KetQua ketQua) {
        danhSachKetQua.add(ketQua);
    }
    
    // Method để nhập điểm cho thí sinh
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
    
    public void tinhKetQua(){
        System.out.println("Đang tính kết quả cho kỳ thi: " + tenKyThi);
    }
    
    public void thongke(){
        System.out.println("Tổng số thí sinh: " + danhSachThiSinh.size());
        System.out.println("Tổng số giám thị: " + danhSachGiamThi.size());
        System.out.println("Tổng số kết quả: " + danhSachKetQua.size());
    } 
    

    // Phương thức tìm kiếm thí sinh theo tên
    public void timKiem(String keyword){
        for(ThiSinh ts: danhSachThiSinh){
            if(ts.getHoTen().toLowerCase().contains(keyword.toLowerCase())){
                System.out.println("Tìm thấy: " + ts.getHoTen());
            }
        }
    }
    
    /**
     * Kiểm tra trùng lặp thí sinh hoặc giám thị trong kỳ thi
     * @param ma - Mã thí sinh hoặc mã giám thị cần kiểm tra
     * @param loai - "THISINH" hoặc "GIAMTHI"
     * @return true nếu trùng lặp, false nếu không trùng
     */
    public boolean kiemTraTrung(String ma, String loai) {
        if ("THISINH".equals(loai)) {
            // Kiểm tra trùng lặp thí sinh
            for (ThiSinh ts : danhSachThiSinh) {
                if (ts.getMaThisinh().equals(ma)) {
                    return true; // Trùng lặp
                }
            }
        } else if ("GIAMTHI".equals(loai)) {
            // Kiểm tra trùng lặp giám thị
            for (GiamThi gt : danhSachGiamThi) {
                if (gt.getMaGiamThi().equals(ma)) {
                    return true; // Trùng lặp
                }
            }
        }
        return false; // Không trùng lặp
    }
}
