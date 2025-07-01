# Hệ Thống Quản Lý Kỳ Thi

## Mô tả
Hệ thống quản lý kỳ thi được phát triển bằng Java, hỗ trợ quản lý thông tin thí sinh, giám thị, và kết quả thi.

## Tính năng chính
- **🔐 Hệ thống đăng nhập**: Xác thực người dùng với phân quyền theo role
- **👥 Quản lý Thí sinh**: Đăng ký, xem thông tin thí sinh
- **📝 Quản lý Kỳ thi**: Tạo kỳ thi, thêm thí sinh và giám thị với phí riêng biệt
- **👨‍🏫 Quản lý Giám thị**: Phân công giám thị cho các kỳ thi
- **📊 Quản lý Kết quả**: Nhập điểm và xem kết quả thi
- **⚡ Quản lý Trạng thái Bài thi**: Theo dõi chi tiết trạng thái từng bài thi (chưa thi → đang thi → đã nộp → chưa chấm → đang chấm → đã chấm)
- **🔒 Phân quyền nhập điểm**: Admin, Giáo vụ, Giám thị có quyền khác nhau trong việc chấm bài và nhập điểm
- **📋 Phiếu đăng ký**: Tự động tạo phiếu đăng ký và xác nhận đóng phí
- **💾 Cơ sở dữ liệu XML**: Lưu trữ dữ liệu persistent trong các file XML

## Cấu trúc dự án
```
src/
├── QuanLyKyThi/
│   ├── Core Classes/
│   │   ├── KyThi.java              # Class quản lý kỳ thi
│   │   ├── ThiSinh.java            # Class quản lý thí sinh
│   │   ├── GiamThi.java            # Class quản lý giám thị
│   │   ├── KetQua.java             # Class quản lý kết quả thi
│   │   ├── PhieuDangKy.java        # Class quản lý phiếu đăng ký
│   │   └── User.java               # Class quản lý người dùng
│   ├── Authentication/
│   │   ├── AuthenticationService.java  # Service xử lý đăng nhập
│   │   └── LoginForm.java          # Form đăng nhập
│   ├── Database/
│   │   └── XMLDatabase.java        # Quản lý cơ sở dữ liệu XML
│   ├── GUI/
│   │   ├── MainGUI.java            # Giao diện chính (legacy)
│   │   ├── MainGUIWithAuth.java    # Giao diện chính với authentication
│   │   ├── AddKyThiForm.java       # Form thêm kỳ thi
│   │   ├── KyThiListForm.java      # Form danh sách kỳ thi
│   │   ├── QuanLyTrangThaiBaiThiForm.java  # Form quản lý trạng thái bài thi
│   │   └── StubForms.java          # Các form phụ (stub)
│   └── demo/
│       └── Demo.java               # File demo
└── data/                           # Thư mục chứa file XML (tự tạo)
    ├── users.xml                   # Dữ liệu tài khoản
    ├── kythi.xml                   # Dữ liệu kỳ thi
    ├── thisinh.xml                 # Dữ liệu thí sinh
    ├── giamthi.xml                 # Dữ liệu giám thị
    └── ketqua.xml                  # Dữ liệu kết quả thi
```

## Các class chính

### KyThi
- Quản lý thông tin kỳ thi
- Danh sách thí sinh và giám thị
- Quản lý kết quả thi
- Tìm kiếm và thống kê

### ThiSinh
- Thông tin cá nhân thí sinh
- Đăng ký tham gia kỳ thi
- Xem kết quả thi

### GiamThi
- Thông tin giám thị
- Phân công giám thị vào kỳ thi

### KetQua
- Lưu trữ điểm số
- Liên kết thí sinh với kỳ thi

### PhieuDangKy
- Tự động tạo mã phiếu
- Tính toán phí đăng ký

## 🚀 Cách sử dụng

### Entry Points (Điểm khởi chạy):
Dự án có **3 điểm khởi chạy** khác nhau:

1. **`Demo.main()`** - Entry point chính không có authentication
   ```bash
   java -cp src demo.Demo
   ```

2. **`LoginForm.main()`** - **⭐ KHUYẾN NGHỊ** - Entry point có authentication và phân quyền
   ```bash
   java -cp src QuanLyKyThi.LoginForm
   ```

3. **`MainGUI.main()`** - Entry point trực tiếp vào giao diện chính (bypass login)
   ```bash
   java -cp src QuanLyKyThi.MainGUI
   ```

### 🔐 Authentication Flow (Luồng đăng nhập):
Khi sử dụng `LoginForm.main()`, hệ thống sẽ tự động chuyển hướng theo quyền:

| Role | Username/Password | Chuyển hướng đến | Quyền hạn |
|------|-------------------|------------------|-----------|
| **Admin** | `admin/admin123` | `MainGUIWithAuth` | Toàn quyền: CRUD tất cả |
| **Giáo vụ** | `giaovu/gv123` | `MainGUIWithAuth` | Quản lý kỳ thi, nhập điểm, thống kê |
| **User** | `user1/user123` | `MainGUI` | Xem thông tin, đăng ký thi |
| **Thí sinh** | `thisinh1/ts123` | `MainGUI` | Chỉ xem kết quả của mình |
| **Khác** | - | `Demo` | Fallback mode |

**💡 Tài khoản mẫu khác:**
- `giaovu2/gv456`, `user2/user456`, `thisinh2/ts456`

### Compile và chạy ứng dụng:
```bash
# Compile tất cả file Java
javac -cp . src/QuanLyKyThi/*.java

# Chạy ứng dụng từ LoginForm
java -cp src QuanLyKyThi.LoginForm
```

### Quy trình sử dụng:
1. **Đăng nhập**: Sử dụng tài khoản mặc định hoặc tạo tài khoản mới
2. **Quản lý dữ liệu**: Thêm kỳ thi, thí sinh, giám thị
3. **Đăng ký thi**: Thí sinh đăng ký và đóng phí
4. **Phân công**: Phân công giám thị cho kỳ thi
5. **Nhập điểm**: Giáo vụ nhập điểm sau khi thi
6. **Xem kết quả**: Thí sinh xem kết quả thi

### Code example - Logic nghiệp vụ:
```java
// Khởi tạo services
XMLDatabase database = new XMLDatabase();
AuthenticationService auth = new AuthenticationService();

// Đăng nhập
auth.login("giaovu", "gv123");

// Tạo kỳ thi với phí riêng
KyThi kyThi = new KyThi("KT001", "Kỳ thi Java OOP", 
                        LocalDate.of(2025, 8, 15), "Sắp diễn ra", 
                        new ArrayList<>(), new ArrayList<>(), 150.0);

// Thí sinh đăng ký (có kiểm tra trạng thái và đóng phí)
ThiSinh thiSinh = new ThiSinh("TS001", "Nguyễn Văn A", 
                              LocalDate.of(2003, 5, 10), "Nam", "Hà Nội", "0123456789");
boolean success = thiSinh.dangKythi(kyThi); // Chỉ thành công nếu đã đóng phí

// Nhập điểm (chỉ với quyền giáo vụ)
if (auth.hasPermission("NHAP_DIEM")) {
    kyThi.nhapDiem(thiSinh, 8.5);
}

// Lưu vào XML
database.saveKyThi(Arrays.asList(kyThi));
database.saveThiSinh(Arrays.asList(thiSinh));
```

## 💻 Yêu cầu hệ thống
- **Java**: JDK 8 trở lên
- **IDE**: NetBeans IDE (khuyến nghị) hoặc Eclipse/IntelliJ
- **Libraries**: 
  - Built-in Java XML APIs (DOM, SAX)
  - Swing GUI framework
- **OS**: Windows, macOS, Linux (Java cross-platform)
- **RAM**: Tối thiểu 512MB
- **Disk**: 50MB cho ứng dụng + dữ liệu XML

## 📁 Cấu trúc thư mục sau khi chạy
```
QuanLyKyThi_1/
├── src/QuanLyKyThi/           # Source code
├── data/                      # Tự tạo khi chạy lần đầu
│   ├── users.xml
│   ├── kythi.xml
│   ├── thisinh.xml
│   ├── giamthi.xml
│   └── ketqua.xml
├── build/                     # Compiled classes (nếu dùng NetBeans)
└── README.md
```

## Thành viên
- Trần Thái Hưng (MSV: 23010693)
- Hoàng Tiến Đạt (MSV: 23010864)

## Lịch sử phiên bản
- **v1.0** - Phiên bản đầu tiên với các tính năng cơ bản

## 🔐 Hệ thống đăng nhập

### Tài khoản mặc định:
- **Admin**: `admin/admin123` - Toàn quyền hệ thống
- **Giáo vụ**: `giaovu/gv123` - Quản lý kỳ thi, giám thị, nhập điểm
- **User**: `user1/user123` - Xem thông tin, đăng ký thi

### Phân quyền:
| Chức năng | Admin | Giáo vụ | Giám thị | User |
|-----------|-------|---------|----------|------|
| Quản lý tài khoản | ✅ | ❌ | ❌ | ❌ |
| Thêm/sửa kỳ thi | ✅ | ✅ | ❌ | ❌ |
| Quản lý giám thị | ✅ | ✅ | ❌ | ❌ |
| Bắt đầu thi | ✅ | ❌ | ✅ | ❌ |
| Nộp bài thi | ✅ | ❌ | ✅ | ❌ |
| Bắt đầu chấm bài | ✅ | ✅ | ✅* | ❌ |
| Nhập điểm | ✅ | ✅ | ✅* | ❌ |
| Cập nhật điểm | ✅ | ✅ | ❌** | ❌ |
| Xem kỳ thi | ✅ | ✅ | ✅ | ✅ |

**Ghi chú:**
- ✅* : Giám thị chỉ được chấm/nhập điểm cho kỳ thi được phân công
- ❌** : Giám thị chỉ được cập nhật điểm bài mình đã chấm

## 📊 Hệ thống Trạng thái Bài thi

### Quy trình trạng thái bài thi:
```
CHƯA THI → ĐANG THI → ĐÃ NỘP BÀI → CHƯA CHẤM → ĐANG CHẤM → ĐÃ CHẤM
```

### Chi tiết các trạng thái:

| Trạng thái | Mô tả | Thao tác được phép |
|------------|-------|-------------------|
| **CHƯA THI** | Thí sinh chưa bắt đầu làm bài | Bắt đầu thi |
| **ĐANG THI** | Thí sinh đang làm bài thi | Nộp bài |
| **ĐÃ NỘP BÀI** | Thí sinh đã nộp bài | Tự động chuyển sang "Chưa chấm" |
| **CHƯA CHẤM** | Bài thi chờ được chấm | Bắt đầu chấm |
| **ĐANG CHẤM** | Bài thi đang được chấm | Nhập điểm |
| **ĐÃ CHẤM** | Bài thi đã hoàn thành chấm | Xem kết quả, Cập nhật điểm |

### Quyền thao tác theo role:

**🔑 Admin:**
- Toàn quyền tất cả trạng thái
- Có thể cập nhật điểm bất kỳ bài thi nào

**📋 Giáo vụ:**
- Bắt đầu chấm, nhập điểm, cập nhật điểm
- Không thể bắt đầu thi hoặc nộp bài (thuộc về thí sinh/giám thị)

**👨‍🏫 Giám thị:**
- Bắt đầu thi, nộp bài (giám sát thí sinh)
- Chấm bài và nhập điểm cho kỳ thi được phân công
- Chỉ cập nhật được điểm bài mình đã chấm

### Form Quản lý Trạng thái:
```java
// Mở form quản lý trạng thái
java -cp src QuanLyKyThi.DemoTrangThaiBaiThi
```

## 💾 Cơ sở dữ liệu XML

### 🗂️ Cấu trúc file XML với mối quan hệ:

**5 file XML chính:**
- `users.xml` - Tài khoản người dùng
- `kythi.xml` - Kỳ thi (có danh sách thí sinh & giám thị)
- `thisinh.xml` - Thí sinh (có kỳ thi đã đăng ký) 
- `giamthi.xml` - Giám thị (có kỳ thi được phân công)
- `ketqua.xml` - Kết quả thi (liên kết thí sinh ↔ kỳ thi)

### 🔗 Mối quan hệ dữ liệu:
```
KyThi (1) ←→ (N) ThiSinh    // Một kỳ thi có nhiều thí sinh
KyThi (1) ←→ (N) GiamThi    // Một kỳ thi có nhiều giám thị  
ThiSinh (1) ←→ (N) KetQua   // Một thí sinh có nhiều kết quả
KyThi (1) ←→ (N) KetQua     // Một kỳ thi có nhiều kết quả
```

📄 **Chi tiết cấu trúc:** Xem file [`XML_DATABASE_STRUCTURE.md`](XML_DATABASE_STRUCTURE.md)

### Ví dụ XML cơ bản:

#### 1. `kythi.xml` - Kỳ thi với thí sinh & giám thị:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<kyThiList>
  <kyThi>
    <maKyThi>KT001</maKyThi>
    <tenKyThi>Kỳ thi Java OOP</tenKyThi>
    <ngayToChuc>2025-08-15</ngayToChuc>
    <tinhTrang>Sắp diễn ra</tinhTrang>
    <phiDangKy>150.0</phiDangKy>
    
    <!-- Danh sách thí sinh tham gia -->
    <danhSachThiSinh>
      <thiSinh>
        <maThiSinh>TS001</maThiSinh>
        <hoTen>Nguyễn Văn A</hoTen>
      </thiSinh>
    </danhSachThiSinh>
    
    <!-- Danh sách giám thị -->
    <danhSachGiamThi>
      <giamThi>
        <maGiamThi>GT001</maGiamThi>
        <hoTen>Lê Thị B</hoTen>
      </giamThi>
    </danhSachGiamThi>
  </kyThi>
</kyThiList>
```

#### 2. `ketqua.xml` - Kết quả thi:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<ketQuaList>
  <ketQua>
    <maThiSinh>TS001</maThiSinh>
    <tenThiSinh>Nguyễn Văn A</tenThiSinh>
    <maKyThi>KT001</maKyThi>
    <tenKyThi>Kỳ thi Java OOP</tenKyThi>
    <diem>8.5</diem>
    <xepLoai>Giỏi</xepLoai>
  </ketQua>
</ketQuaList>
```
```xml
<?xml version="1.0" encoding="UTF-8"?>
<giamThiList>
  <giamThi>
    <maGiamThi>GT001</maGiamThi>
    <hoTen>Phạm Văn C</hoTen>
    <donVi>Trường ĐHCN</donVi>
    <sdt>0111222333</sdt>
  </giamThi>
</giamThiList>
```

#### 5. `ketqua.xml` - Quản lý kết quả thi:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<ketQuaList>
  <ketQua>
    <maThiSinh>TS001</maThiSinh>
    <maKyThi>KT001</maKyThi>
    <diem>8.5</diem>
    <ngayThi>2025-08-15</ngayThi>
  </ketQua>
</ketQuaList>
```

### Tính năng XML Database:
- **Auto-create**: Tự tạo thư mục `data/` và file XML khi chạy lần đầu
- **Auto-save**: Tự động lưu khi có thay đổi dữ liệu
- **Data persistence**: Dữ liệu được lưu vĩnh viễn
- **Backup friendly**: File XML dễ sao lưu và phục hồi
- **Human readable**: Có thể đọc và chỉnh sửa trực tiếp

## 🎯 Các tính năng nâng cao

### Logic nghiệp vụ:
- **Kiểm tra trạng thái kỳ thi**: Chỉ cho phép đăng ký khi "Sắp diễn ra"
- **Xác nhận đóng phí**: Đăng ký chỉ thành công khi đã thanh toán
- **Phân quyền chặt chẽ**: Kiểm tra quyền truy cập cho từng chức năng
- **Phòng chống trùng lặp**: Không cho phép đăng ký/phân công trùng
- **Session management**: Theo dõi thời gian đăng nhập và hoạt động

### Database features:
- **Auto-backup**: Tự động tạo file backup trước khi ghi
- **Validation**: Kiểm tra tính hợp lệ của dữ liệu XML
- **Migration**: Hỗ trợ cập nhật cấu trúc database khi có thay đổi
- **Indexing**: Tối ưu tìm kiếm dữ liệu
- **Concurrent access**: An toàn khi nhiều process truy cập

## 🔧 Troubleshooting

### Lỗi thường gặp:
1. **"XML file not found"**: Đảm bảo thư mục `data/` có quyền ghi
2. **"Login failed"**: Kiểm tra username/password, tài khoản có bị khóa không
3. **"Permission denied"**: User không có quyền thực hiện chức năng này
4. **"Data not saved"**: Kiểm tra quyền ghi file và dung lượng đĩa

### Debug mode:
```java
// Bật log để debug
System.setProperty("quanly.debug", "true");
```

## 📈 Roadmap

### Version 2.0 (Coming soon):
- [ ] **REST API**: Web service cho mobile app
- [ ] **MySQL integration**: Hỗ trợ database quan hệ
- [ ] **Reporting**: Xuất báo cáo PDF/Excel
- [ ] **Email notification**: Gửi email thông báo kết quả
- [ ] **QR Code**: Tạo QR code cho phiếu dự thi
- [ ] **Multi-language**: Hỗ trợ đa ngôn ngữ

### Version 1.5 (Next release):
- [ ] **Form improvements**: Hoàn thiện các form stub
- [ ] **Data validation**: Validation mạnh hơn cho input
- [ ] **UI/UX**: Cải thiện giao diện người dùng
- [ ] **Search & Filter**: Tìm kiếm và lọc dữ liệu
- [ ] **Import/Export**: Import từ Excel, export dữ liệu

## 📞 Liên hệ & Hỗ trợ

- **Team**: Trần Thái Hưng (MSV: 23010693), Hoàng Tiến Đạt (MSV: 23010864)
- **GitHub**: [OOP Repository](https://github.com/ghism137/OOP)
- **Email**: support@quanlykythi.com
- **Documentation**: Xem thêm trong folder `docs/`

---

## 📄 Giấy phép
Dự án được phát triển cho mục đích học tập tại trường Đại học. 

**© 2025 QuanLyKyThi Team. All rights reserved.**
