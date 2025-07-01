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
    
    public boolean dangKythi(KyThi kythi){
        // Tạo phiếu đăng ký trước
        PhieuDangKy phieu = new PhieuDangKy(this, kythi, LocalDate.now());
        
        // Hiển thị thông tin phí và xác nhận thanh toán
        System.out.println("📋 Phiếu đăng ký: " + phieu.getMaPhieuDangKy());
        System.out.println("💰 Phí đăng ký: " + phieu.tinhPhi() + "k VNĐ");
        System.out.println("🏦 Vui lòng đóng phí để hoàn tất đăng ký...");
        
        // Giả lập xác nhận đóng phí (trong thực tế sẽ có giao diện thanh toán)
        boolean xacNhanDongPhi = phieu.dongPhi(); // Tự động xác nhận đóng phí
        
        if (xacNhanDongPhi) {
            // Chỉ khi đã đóng phí mới thêm vào kỳ thi
            if (kythi.themThiSinh(this)) {
                System.out.println("✅ Đăng ký thành công kỳ thi: " + kythi.getTenKyThi());
                return true;
            } else {
                System.out.println("❌ Không thể đăng ký kỳ thi: " + kythi.getTenKyThi() + 
                                 " (Đã đăng ký hoặc kỳ thi không còn nhận đăng ký)");
                return false;
            }
        } else {
            System.out.println("❌ Đăng ký thất bại: Chưa xác nhận đóng phí!");
            return false;
        }
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
