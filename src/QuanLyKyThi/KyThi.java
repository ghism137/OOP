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
    
    private List<ThiSinh> danhSachThiSinh;
    private List<GiamThi> danhSachGiamThi;
    private List<KetQua> danhSachKetQua;

    public KyThi(String maKyThi, String tenKythi, LocalDate ngayToChuc, String tinhTrang, List<ThiSinh> danhSachThiSinh, List<GiamThi> danhSachGiamThi) {
        this.maKyThi = maKyThi;
        this.tenKyThi = tenKythi;
        this.ngayToChuc = ngayToChuc;
        this.tinhTrang = tinhTrang;
        this.danhSachThiSinh = danhSachThiSinh;
        this.danhSachGiamThi = danhSachGiamThi;
        this.danhSachKetQua = new ArrayList<>();
    }
    
    // Phương thức để thêm thí sinh và giám thị vào danh sách
    
    public void themThiSinh(ThiSinh thisinh){
        danhSachThiSinh.add(thisinh);
    }
    
    public void themGiamThi(GiamThi giamthi){
        danhSachGiamThi.add(giamthi);
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
