## 🗂️ Cấu trúc XML Database - Hệ thống Quản lý Kỳ thi

### **📁 Tổng quan file XML:**
```
data/
├── users.xml       - Thông tin tài khoản người dùng
├── kythi.xml       - Danh sách kỳ thi (có thí sinh & giám thị)
├── thisinh.xml     - Danh sách thí sinh (có kỳ thi đã đăng ký)
├── giamthi.xml     - Danh sách giám thị (có kỳ thi được phân công)
└── ketqua.xml      - Kết quả thi của từng thí sinh
```

---

### **👥 users.xml**
```xml
<?xml version="1.0" encoding="UTF-8"?>
<users>
    <user>
        <username>admin</username>
        <password>admin123</password>
        <hoTen>Quản trị viên hệ thống</hoTen>
        <email>admin@email.com</email>
        <role>admin</role>
        <isActive>true</isActive>
        <lastLogin>2024-12-15T10:30:00</lastLogin>
    </user>
    <user>
        <username>giaovu</username>
        <password>gv123</password>
        <hoTen>Phòng Giáo vụ</hoTen>
        <email>giaovu@email.com</email>
        <role>giaovu</role>
        <isActive>true</isActive>
    </user>
</users>
```

---

### **📋 kythi.xml - Kỳ thi với danh sách thí sinh & giám thị**
```xml
<?xml version="1.0" encoding="UTF-8"?>
<kyThiList>
    <kyThi>
        <maKyThi>KT001</maKyThi>
        <tenKyThi>Kỳ thi Toán học</tenKyThi>
        <ngayToChuc>2024-12-15</ngayToChuc>
        <tinhTrang>Đang diễn ra</tinhTrang>
        <phiDangKy>50000.0</phiDangKy>
        
        <!-- Danh sách thí sinh tham gia kỳ thi này -->
        <danhSachThiSinh>
            <thiSinh>
                <maThiSinh>TS001</maThiSinh>
                <hoTen>Nguyễn Văn An</hoTen>
            </thiSinh>
            <thiSinh>
                <maThiSinh>TS002</maThiSinh>
                <hoTen>Trần Thị Bình</hoTen>
            </thiSinh>
        </danhSachThiSinh>
        
        <!-- Danh sách giám thị cho kỳ thi này -->
        <danhSachGiamThi>
            <giamThi>
                <maGiamThi>GT001</maGiamThi>
                <hoTen>Lê Văn Cường</hoTen>
            </giamThi>
        </danhSachGiamThi>
    </kyThi>
    
    <kyThi>
        <maKyThi>KT002</maKyThi>
        <tenKyThi>Kỳ thi Văn học</tenKyThi>
        <ngayToChuc>2024-12-20</ngayToChuc>
        <tinhTrang>Sắp diễn ra</tinhTrang>
        <phiDangKy>45000.0</phiDangKy>
        <danhSachThiSinh>
            <!-- Thí sinh khác cho kỳ thi Văn -->
        </danhSachThiSinh>
        <danhSachGiamThi>
            <!-- Giám thị khác cho kỳ thi Văn -->
        </danhSachGiamThi>
    </kyThi>
</kyThiList>
```

---

### **🎓 thisinh.xml - Thí sinh với kỳ thi đã đăng ký**
```xml
<?xml version="1.0" encoding="UTF-8"?>
<thiSinhList>
    <thiSinh>
        <maThiSinh>TS001</maThiSinh>
        <hoTen>Nguyễn Văn An</hoTen>
        <ngaySinh>2000-05-15</ngaySinh>
        <gioiTinh>Nam</gioiTinh>
        <diaChi>Hà Nội</diaChi>
        <sdt>0123456789</sdt>
        
        <!-- Danh sách kỳ thi mà thí sinh này đã đăng ký -->
        <kyThiDangKy>
            <kyThi>
                <maKyThi>KT001</maKyThi>
                <tenKyThi>Kỳ thi Toán học</tenKyThi>
                <ngayToChuc>2024-12-15</ngayToChuc>
            </kyThi>
        </kyThiDangKy>
    </thiSinh>
</thiSinhList>
```

---

### **👨‍🏫 giamthi.xml - Giám thị với kỳ thi được phân công**
```xml
<?xml version="1.0" encoding="UTF-8"?>
<giamThiList>
    <giamThi>
        <maGiamThi>GT001</maGiamThi>
        <hoTen>Lê Văn Cường</hoTen>
        <donVi>Phòng Giáo dục</donVi>
        <sdt>0987654321</sdt>
        
        <!-- Danh sách kỳ thi mà giám thị này được phân công -->
        <kyThiPhanCong>
            <kyThi>
                <maKyThi>KT001</maKyThi>
                <tenKyThi>Kỳ thi Toán học</tenKyThi>
                <ngayToChuc>2024-12-15</ngayToChuc>
            </kyThi>
        </kyThiPhanCong>
    </giamThi>
</giamThiList>
```

---

### **📊 ketqua.xml - Kết quả thi với trạng thái chi tiết**
```xml
<?xml version="1.0" encoding="UTF-8"?>
<ketQuaList>
    <ketQua>
        <maThiSinh>TS001</maThiSinh>
        <tenThiSinh>Nguyễn Văn An</tenThiSinh>
        <maKyThi>KT001</maKyThi>
        <tenKyThi>Kỳ thi Toán học</tenKyThi>
        <diem>8.5</diem>
        <xepLoai>Giỏi</xepLoai>
        <trangThai>DA_CHAM</trangThai>
        <nguoiCham>giaovu</nguoiCham>
        <thoiGianBatDauThi>2024-12-15T08:00:00</thoiGianBatDauThi>
        <thoiGianNopBai>2024-12-15T10:00:00</thoiGianNopBai>
        <thoiGianCham>2024-12-15T14:30:00</thoiGianCham>
        <ghiChu>Bài làm tốt, có sáng tạo</ghiChu>
    </ketQua>
    <ketQua>
        <maThiSinh>TS002</maThiSinh>
        <tenThiSinh>Trần Thị Bình</tenThiSinh>
        <maKyThi>KT001</maKyThi>
        <tenKyThi>Kỳ thi Toán học</tenKyThi>
        <diem>7.0</diem>
        <xepLoai>Khá</xepLoai>
        <trangThai>DA_CHAM</trangThai>
        <nguoiCham>admin</nguoiCham>
        <thoiGianBatDauThi>2024-12-15T08:00:00</thoiGianBatDauThi>
        <thoiGianNopBai>2024-12-15T09:45:00</thoiGianNopBai>
        <thoiGianCham>2024-12-15T15:00:00</thoiGianCham>
        <ghiChu>Cần cải thiện phần tính toán</ghiChu>
    </ketQua>
    <ketQua>
        <maThiSinh>TS003</maThiSinh>
        <tenThiSinh>Lê Văn Cường</tenThiSinh>
        <maKyThi>KT001</maKyThi>
        <tenKyThi>Kỳ thi Toán học</tenKyThi>
        <diem>0.0</diem>
        <xepLoai>Chưa có kết quả</xepLoai>
        <trangThai>CHUA_CHAM</trangThai>
        <nguoiCham></nguoiCham>
        <thoiGianBatDauThi>2024-12-15T08:00:00</thoiGianBatDauThi>
        <thoiGianNopBai>2024-12-15T09:30:00</thoiGianNopBai>
        <thoiGianCham></thoiGianCham>
        <ghiChu>Đã nộp bài, chờ chấm</ghiChu>
    </ketQua>
</ketQuaList>
```

**📋 Các trạng thái bài thi:**
- `CHUA_THI`: Thí sinh chưa bắt đầu làm bài
- `DANG_THI`: Thí sinh đang trong thời gian làm bài  
- `DA_NOP_BAI`: Thí sinh đã nộp bài xong
- `CHUA_CHAM`: Bài đã nộp nhưng chưa được chấm
- `DANG_CHAM`: Đang trong quá trình chấm bài
- `DA_CHAM`: Đã chấm xong và có điểm

---

## **🔗 Mối quan hệ dữ liệu:**

1. **KyThi ↔ ThiSinh**: Mỗi kỳ thi có nhiều thí sinh, mỗi thí sinh có thể thi nhiều kỳ
2. **KyThi ↔ GiamThi**: Mỗi kỳ thi có nhiều giám thị, mỗi giám thị có thể giám sát nhiều kỳ
3. **ThiSinh ↔ KetQua**: Mỗi thí sinh có nhiều kết quả (từng kỳ thi)
4. **KyThi ↔ KetQua**: Mỗi kỳ thi có nhiều kết quả (từng thí sinh)
5. **User ↔ KetQua**: Người dùng (giám thị/giáo vụ/admin) có thể chấm bài và lưu thông tin

## **🔐 Phân quyền nhập điểm:**

### **👨‍💼 Admin (Quyền cao nhất)**
- ✅ Xem tất cả kết quả
- ✅ Nhập/sửa điểm cho tất cả bài thi
- ✅ Quản lý trạng thái bài thi
- ✅ Cập nhật điểm đã chấm bởi người khác

### **👩‍🏫 Giáo vụ (Quyền quản lý học tập)**  
- ✅ Xem tất cả kết quả
- ✅ Nhập/sửa điểm cho tất cả bài thi
- ✅ Bắt đầu/kết thúc quá trình chấm bài
- ❌ Không được bắt đầu thi/nộp bài (chỉ giám thị mới làm được)

### **👨‍🏫 Giám thị (Quyền giám sát thi)**
- ✅ Bắt đầu thi cho thí sinh
- ✅ Nộp bài cho thí sinh  
- ✅ Chấm bài kỳ thi được phân công
- ❌ Chỉ xem/chấm kỳ thi mình được phân công
- ❌ Không được cập nhật điểm bài đã chấm bởi người khác

## **💡 Ưu điểm thiết kế:**

- **Tính nhất quán**: Mỗi entity có file riêng + mối quan hệ được lưu trữ
- **Truy vấn linh hoạt**: Có thể tìm thí sinh theo kỳ thi hoặc ngược lại
- **Dễ backup**: 5 file XML dễ sao lưu và khôi phục
- **Readable**: XML có cấu trúc rõ ràng, dễ đọc và debug
