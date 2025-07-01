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
        try {
            // Tạo phiếu đăng ký trước
            PhieuDangKy phieu = new PhieuDangKy(this, kythi, LocalDate.now());
            
            // Hiển thị thông tin phí và xác nhận thanh toán
            System.out.println("📋 Phiếu đăng ký: " + phieu.getMaPhieuDangKy());
            System.out.println("💰 Phí đăng ký: " + phieu.tinhPhi() + " VNĐ");
            System.out.println("🏦 Vui lòng đóng phí để hoàn tất đăng ký...");
            
            // Giả lập xác nhận đóng phí (trong thực tế sẽ có giao diện thanh toán)
            boolean xacNhanDongPhi = phieu.dongPhi(); // Tự động xác nhận đóng phí
            
            if (xacNhanDongPhi) {
                // Chỉ khi đã đóng phí mới thêm vào kỳ thi
                kythi.themThiSinh(this);
                System.out.println("✅ Đăng ký thành công kỳ thi: " + kythi.getTenKyThi());
                return true;
            } else {
                System.out.println("❌ Đăng ký thất bại: Chưa xác nhận đóng phí!");
                return false;
            }
        } catch (PaymentException e) {
            System.out.println("❌ Lỗi thanh toán: " + e.getMessage());
            return false;
        } catch (DuplicateException e) {
            System.out.println("❌ Lỗi trùng lặp: " + e.getMessage());
            return false;
        } catch (KyThiValidationException e) {
            System.out.println("❌ Lỗi validation kỳ thi: " + e.getMessage());
            return false;
        } catch (ThiSinhValidationException e) {
            System.out.println("❌ Lỗi thông tin thí sinh: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println("❌ Lỗi không xác định: " + e.getMessage());
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
    
    /**
     * Tính tuổi thí sinh dựa trên ngày sinh
     * @return tuổi của thí sinh
     * @throws ThiSinhValidationException nếu ngày sinh null
     */
    public int getAge() throws ThiSinhValidationException {
        if (ngaysinh == null) {
            throw new ThiSinhValidationException("Ngày sinh không được để trống");
        }
        
        LocalDate now = LocalDate.now();
        int tuoi = now.getYear() - ngaysinh.getYear();
        
        // Điều chỉnh nếu chưa đến sinh nhật trong năm
        if (now.getDayOfYear() < ngaysinh.getDayOfYear()) {
            tuoi--;
        }
        
        return tuoi;
    }
    
    /**
     * Kiểm tra tính hợp lệ của thông tin thí sinh
     * @throws ThiSinhValidationException nếu thông tin không hợp lệ
     */
    public void validate() throws ThiSinhValidationException {
        // Kiểm tra họ tên không rỗng
        if (HoTen == null || HoTen.trim().isEmpty()) {
            throw new ThiSinhValidationException("Họ tên không được để trống");
        }
        
        if (HoTen.trim().length() < 2) {
            throw new ThiSinhValidationException("Họ tên phải có ít nhất 2 ký tự");
        }
        
        // Kiểm tra họ tên chỉ chứa chữ cái và khoảng trắng
        if (!HoTen.trim().matches("^[a-zA-ZÀ-ỹ\\s]+$")) {
            throw new ThiSinhValidationException("Họ tên chỉ được chứa chữ cái và khoảng trắng");
        }
        
        // Kiểm tra tuổi (18-35)
        try {
            int tuoi = getAge();
            if (tuoi < 18) {
                throw new ThiSinhValidationException("Tuổi phải từ 18 trở lên (hiện tại: " + tuoi + " tuổi)");
            }
            if (tuoi > 35) {
                throw new ThiSinhValidationException("Tuổi không được quá 35 (hiện tại: " + tuoi + " tuổi)");
            }
        } catch (ThiSinhValidationException e) {
            throw e; // Re-throw validation exception từ getAge()
        }
        
        // Kiểm tra số điện thoại (10 số, bắt đầu bằng 0)
        if (SDT == null || SDT.trim().isEmpty()) {
            throw new ThiSinhValidationException("Số điện thoại không được để trống");
        }
        
        if (!SDT.matches("^0\\d{9}$")) {
            throw new ThiSinhValidationException("Số điện thoại phải có 10 số và bắt đầu bằng 0");
        }
        
        // Kiểm tra địa chỉ không rỗng
        if (diachi == null || diachi.trim().isEmpty()) {
            throw new ThiSinhValidationException("Địa chỉ không được để trống");
        }
        
        if (diachi.trim().length() < 5) {
            throw new ThiSinhValidationException("Địa chỉ phải có ít nhất 5 ký tự");
        }
        
        // Kiểm tra giới tính
        if (gioitinh == null || gioitinh.trim().isEmpty()) {
            throw new ThiSinhValidationException("Giới tính không được để trống");
        }
        
        if (!gioitinh.trim().toLowerCase().matches("^(nam|nữ|nu)$")) {
            throw new ThiSinhValidationException("Giới tính phải là 'Nam' hoặc 'Nữ'");
        }
        
        // Kiểm tra mã thí sinh
        if (maThisinh == null || maThisinh.trim().isEmpty()) {
            throw new ThiSinhValidationException("Mã thí sinh không được để trống");
        }
        
        if (!maThisinh.matches("^TS\\d{3,}$")) {
            throw new ThiSinhValidationException("Mã thí sinh phải có định dạng TSxxx (ví dụ: TS001)");
        }
    }
    
    
}
