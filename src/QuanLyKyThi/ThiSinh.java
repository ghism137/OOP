/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package QuanLyKyThi;

import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author ad
 */
public class ThiSinh {
    private String maThisinh;
    private String HoTen;
    private LocalDate ngaysinh;
    private String gioitinh;
    private String diachi;
    private String SDT;
  
    public ThiSinh(String maThisinh, String hoten, LocalDate ngaysinh, String gioitinh, String diachi, String sdt) {
        this.maThisinh = maThisinh;
        this.HoTen = hoten;
        this.ngaysinh = ngaysinh;
        this.gioitinh = gioitinh;
        this.diachi = diachi;
        this.SDT = sdt;
        //this.<error> = <error>;
    }

    public String getMaThisinh() {
        return maThisinh;
    }

    public String getHoTen() {
        return HoTen;
    }

    public LocalDate getNgaysinh() {
        return ngaysinh;
    }

    public String getGioitinh() {
        return gioitinh;
    }

    public String getDiachi() {
        return diachi;
    }

    public String getSdt() {
        return SDT;
    }
    
    public void dangKythi(KyThi kythi){
        PhieuDangKy phieu = new PhieuDangKy(this, kythi, LocalDate.now());
        kythi.themThiSinh(this);
        System.out.println("Đã đăng ký kỳ thi: "+ kythi.getTenKyThi());
    }

    public void xemketqua(KyThi kyThi){
        List<KetQua> danhSachKetQua = kyThi.getDanhSachKetQua();
        boolean timThay = false;
        for(KetQua kq : danhSachKetQua){
            if(kq.getThiSinh().equals(this)){
                System.out.println("Kỳ thi: "+ kq.getKyThi().getTenKyThi() + " | Điểm: " + kq.getDiem());
                timThay = true;
            }
        }
        if(!timThay) {
            System.out.println("Chưa có kết quả cho kỳ thi: " + kyThi.getTenKyThi());
        }
    }
    
    
}
