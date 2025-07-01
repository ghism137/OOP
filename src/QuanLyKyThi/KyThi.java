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
    public boolean themThiSinh(ThiSinh thisinh){
        // Kiểm tra thí sinh đã đăng ký chưa
        for (ThiSinh ts : danhSachThiSinh) {
            if (ts.getMaThisinh().equals(thisinh.getMaThisinh())) {
                return false; // Đã tồn tại
            }
        }
        
        // Kiểm tra trạng thái kỳ thi - chỉ cho phép đăng ký khi "Sắp diễn ra"
        if (!"Sắp diễn ra".equals(tinhTrang)) {
            return false; // Không thể đăng ký nếu không phải "Sắp diễn ra"
        }
        
        danhSachThiSinh.add(thisinh);
        return true; // Đăng ký thành công
    }
    
    public boolean themGiamThi(GiamThi giamthi){
        // Kiểm tra giám thị đã được phân công chưa
        for (GiamThi gt : danhSachGiamThi) {
            if (gt.getMaGiamThi().equals(giamthi.getMaGiamThi())) {
                return false; // Đã tồn tại
            }
        }
        
        danhSachGiamThi.add(giamthi);
        return true;
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
        System.out.println("Tổng số thí sinh" + danhSachThiSinh.size());
    } 
    

    // Phương thức tìm kiếm thí sinh theo tên
    public void timKiem(String keyword){
        for(ThiSinh ts: danhSachThiSinh){
            if(ts.getHoTen().toLowerCase().contains(keyword.toLowerCase())){
                System.out.println("Tìm thấy: " + ts.getHoTen());
            }
        }
    }
}
