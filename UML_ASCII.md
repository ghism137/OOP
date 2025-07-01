# 📐 UML - ASCII Art Diagrams

## Class Diagram - Core Structure

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                          HỆTHỐNG QUẢN LÝ KỲ THI                             │
└─────────────────────────────────────────────────────────────────────────────┘

     ┌───────────────┐           ┌─────────────┐           ┌─────────────┐
     │    KyThi      │           │  ThiSinh    │           │   GiamThi   │
     ├───────────────┤    1:N    ├─────────────┤    1:N    ├─────────────┤
     │-maKyThi       │◆────────◇│-maThisinh   │◆────────◇│-maGiamThi   │
     │-tenKyThi      │           │-hoTen       │           │-hoTen       │
     │-ngayToChuc    │           │-ngaySinh    │           │-donVi       │
     │-tinhTrang     │           │-gioiTinh    │           │-sdt         │
     │-phiDangKy     │           │-diaChi      │           │-email       │
     │-danhSachTS    │           │-sdt         │           │-username    │
     │-danhSachGT    │           ├─────────────┤           ├─────────────┤
     ├───────────────┤           │+getAge()    │           │+phanCong()  │
     │+themThiSinh() │           │+validate()  │           │+toString()  │
     │+themGiamThi() │           └─────────────┘           └─────────────┘
     │+kiemTraTrung()│                │                         │
     └───────────────┘                │                         │
            │                         |                         │
            │                         | 1:N                  1:1│
            │                         |                         │
            │1:N                      ▼                         ▼
            │                ┌──────────────┐          ┌─────────────┐
            │                │   KetQua     │          │    User     │
            │                ├──────────────┤           ├─────────────┤
            └─────────────1:N│-thiSinh      │           │-username    │
                             │-kyThi        │           │-password    │
                             │-diem         │           │-hoTen       │
                             │-trangThai    │◆────────▶│-email       │
                             │-nguoiCham    │          │-role        │ 
                             │-thoiGianBD   │          │-lastLogin   │
                             │-thoiGianNop  │          │-isActive    │
                             │-thoiGianCham │          ├─────────────┤
                             │-ghiChu       │          │+getFullName()│
                             ├──────────────|          │+toString()  │
                             │+batDauThi()  │          └─────────────┘
                             │+nopBai()     │
                             │+batDauCham() │                 │
                             │+nhapDiem()   │                 │1:1
                             │+capNhatDiem()│                 ▼
                             │+getXepLoai() │          ┌─────────────┐
                             └──────────────┘          │PhieuDangKy  │
                                   │                 ├─────────────┤
                                   │1:1              │-thiSinh     │
                                   ▼                 │-kyThi       │
                        ┌─────────────────┐          │-ngayDangKy  │
                        │ TrangThaiBaiThi │          │-phiDangKy   │
                        │   <<enum>>      │          │-daThanhToan │
                        ├─────────────────┤          ├─────────────┤
                        │ CHUA_THI        │          │+xacNhanTT() │
                        │ DANG_THI        │          │+taoPhieu()  │
                        │ DA_NOP_BAI      │          └─────────────┘
                        │ CHUA_CHAM       │
                        │ DANG_CHAM       │
                        │ DA_CHAM         │
                        ├─────────────────┤
                        │ +getMoTa()      │
                        └─────────────────┘
```

## State Diagram - Trạng thái Bài thi

```
                   VÒNG ĐỜI BÀI THI

    [START] ────► CHƯA THI ────batDauThi()────► ĐANG THI
                     │                            │
                     │                            │
                     ▼                            ▼
               (Trạng thái ban đầu)         nopBai()
                                                  │
                                                  ▼
                                            ĐÃ NỘP BÀI
                                                  │
                                          (tự động chuyển)
                                                  │
                                                  ▼
    DA_CHAM ◄────nhapDiem()──── ĐANG CHẤM ◄──batDauCham()──── CHƯA CHẤM
       │                            ▲
       │                            │
       └────capNhatDiem()───────────┘

    🔵 CHƯA THI    : Thí sinh chưa bắt đầu (Giám thị bắt đầu)
    🟡 ĐANG THI    : Thí sinh đang làm bài (Tracking thời gian)  
    🟤 ĐÃ NỘP BÀI  : Thí sinh đã nộp (Tự động → CHƯA CHẤM)
    🟠 CHƯA CHẤM   : Bài chờ chấm (Admin/Giáo vụ/Giám thị)
    🔵 ĐANG CHẤM   : Đang chấm bài (Lưu người chấm)
    🟢 ĐÃ CHẤM     : Hoàn thành (Có thể cập nhật điểm)
```

## Use Case Diagram - Phân quyền

```
                        HỆ THỐNG PHÂN QUYỀN

    ┌─────────────┐                    ┌─────────────────────────────────┐
    │👨‍💼 ADMIN    │                    │        CÁC CHỨC NĂNG            │
    │             │──────────────────▶ │                                 │
    │Toàn quyền   │ ✅ CÓ TẤT CẢ      │ 🔐 Quản lý tài khoản            │
    └─────────────┘                    │ 📝 Tạo/sửa kỳ thi               │
                                       │ 👨‍🏫 Quản lý giám thị             │
    ┌─────────────┐                    │ ⚡ Bắt đầu thi                   │
    │👩‍🏫 GIÁO VỤ  │                    │ 📄 Nộp bài                      │
    │             │──────────────────▶ │ 🔍 Bắt đầu chấm bài             │
    │Quản lý học  │ ❌ KHÔNG: Bắt đầu  │ ✏️  Nhập điểm                    │
    │             │    thi, Nộp bài    │ 🔄 Cập nhật điểm                │
    └─────────────┘                    │ 👁️  Xem kết quả                 │
                                       │ 📊 Phân công giám thị           │
    ┌─────────────┐                    │ 🎯 Quản lý trạng thái bài thi   │
    │👨‍🏫 GIÁM THỊ │                    │                                 │
    │             │──────────────────▶ └─────────────────────────────────┘
    │Giám sát thi │ ✅ CÓ: Bắt đầu thi,     ▲
    │             │    Nộp bài, Chấm bài    │
    └─────────────┘ ❌ KHÔNG: Cập nhật điểm │
                    (chỉ bài mình chấm)     │
                                            │
                            PHÂN QUYỀN THEO ROLE
```

## Component Architecture

```
┌─────────────────────────────────────────────────────────────────────┐
│                     KIẾN TRÚC 3-LAYER                               │
├─────────────────────────────────────────────────────────────────────┤
│  PRESENTATION LAYER (GUI)                                           │
│  ┌─────────────┐ ┌─────────────┐ ┌─────────────┐ ┌─────────────┐    │
│  │ LoginForm   │ │ MainGUI     │ │ QuanLyForm  │ │ Add*Forms   │    │
│  │ 🔐         │  │🏠          │ │📊           │ │➕          │    │ 
│  └─────────────┘ └─────────────┘ └─────────────┘ └─────────────┘    │
├─────────────────────────────────────────────────────────────────────┤
│  BUSINESS LOGIC LAYER (Services + Models)                           │
│  ┌─────────────┐ ┌─────────────┐ ┌─────────────┐ ┌─────────────┐    │
│  │AuthService  │ │ KyThi       │ │ ThiSinh     │ │ GiamThi     │    │
│  │🔑           │ │📝          │ │👥           │ │👨‍🏫          │    │  
│  └─────────────┘ └─────────────┘ └─────────────┘ └─────────────┘    │
│  ┌─────────────┐ ┌─────────────┐ ┌─────────────┐ ┌─────────────┐    │
│  │ KetQua      │ │ PhieuDangKy │ │ User        │ │TrangThaiBT  │    │
│  │📊           │ │📋          │ │👤           │ │🔄          │    │
│  └─────────────┘ └─────────────┘ └─────────────┘ └─────────────┘    │
├─────────────────────────────────────────────────────────────────────┤
│  DATA ACCESS LAYER (Persistence)                                    │
│  ┌─────────────────────────────────────────────────────────────┐    │
│  │                XMLDatabase                                  │    │
│  │💾 Load/Save XML • CRUD Operations • Data Validation        │     │ 
│  └─────────────────────────────────────────────────────────────┘    │
├─────────────────────────────────────────────────────────────────────┤
│  DATA STORAGE (XML Files)                                           │
│  ┌───────────┐┌───────────┐┌───────────┐┌───────────┐┌───────────┐  │
│  │users.xml  ││kythi.xml  ││thisinh.xml││giamthi.xml││ketqua.xml │  │
│  │👥         ││📝        ││🎓         ││👨‍🏫         ││📊       │   │
│  └───────────┘└───────────┘└───────────┘└───────────┘└───────────┘  │
└─────────────────────────────────────────────────────────────────────┘

                          DATA FLOW
                    ┌─────────┐
                    │  USER   │
                    └────┬────┘
                         │ Thao tác
                         ▼
                    ┌─────────┐
                    │   GUI   │ ← Hiển thị kết quả
                    └────┬────┘
                         │ Gọi method
                         ▼
                  ┌───────────────┐
                  │BUSINESS LOGIC │ ← Xử lý logic
                  └─────┬─────────┘
                        │ CRUD operations  
                        ▼
                  ┌─────────┐
                  │XML DATA │ ← Lưu trữ persistent
                  └─────────┘
```

## 🔍 Giải thích chi tiết các Method trong UML

### Class KyThi - Quản lý kỳ thi
- **`+themThiSinh()`**: Thêm thí sinh vào kỳ thi với kiểm tra trạng thái "Sắp diễn ra"
- **`+themGiamThi()`**: Phân công giám thị cho kỳ thi
- **`+kiemTraTrung(ma: String, loai: String)`**: 
  - **Mục đích**: Kiểm tra trùng lặp mã thí sinh hoặc mã giám thị trong kỳ thi
  - **Tham số**: 
    - `ma`: Mã cần kiểm tra (mã thí sinh hoặc mã giám thị)
    - `loai`: "THISINH" hoặc "GIAMTHI"
  - **Trả về**: `true` nếu trùng lặp, `false` nếu không trùng
  - **Ý nghĩa**: Đảm bảo tính duy nhất của thí sinh/giám thị trong mỗi kỳ thi
- **`+themKetQua(KetQua)`**: Thêm kết quả thi vào danh sách
- **`+nhapDiem(ThiSinh, double)`**: Nhập điểm cho thí sinh (tạo KetQua mới)
- **`+tinhKetQua()`**: Tổng hợp và tính toán kết quả kỳ thi
- **`+thongke()`**: Thống kê số lượng thí sinh, giám thị
- **`+timKiem(String)`**: Tìm kiếm thí sinh theo tên

### Class ThiSinh - Quản lý thông tin thí sinh
- **`+getAge()`**: Tính tuổi thí sinh dựa trên ngày sinh (hiện tại - năm sinh)
- **`+validate()`**: Kiểm tra tính hợp lệ của thông tin thí sinh
  - Tuổi: 18-35 tuổi
  - SĐT: đúng 10 số
  - Họ tên và địa chỉ không được trống
- **`+dangKythi(KyThi)`**: Đăng ký tham gia kỳ thi (tạo phiếu đăng ký)
- **`+xemketqua(KyThi)`**: Xem kết quả thi của thí sinh

### Class GiamThi - Quản lý giám thị
- **`+phanCong()`**: Phân công giám thị cho kỳ thi cụ thể
- **`+toString()`**: Hiển thị thông tin giám thị dạng chuỗi

### Class KetQua - Quản lý kết quả thi
- **`+batDauThi()`**: Chuyển trạng thái từ CHƯA_THI → ĐANG_THI (ghi thời gian bắt đầu)
- **`+nopBai()`**: Chuyển trạng thái từ ĐANG_THI → ĐÃ_NỘP_BÀI → CHƯA_CHẤM (ghi thời gian nộp)
- **`+batDauCham(nguoiCham, role)`**: Chuyển trạng thái từ CHƯA_CHẤM → ĐANG_CHẤM (ghi nhận người chấm)
- **`+nhapDiem(diem, nguoiCham, role, ghiChu)`**: Nhập điểm và chuyển → ĐÃ_CHẤM
- **`+capNhatDiem(diem, nguoiCapNhat, role, ghiChu)`**: Cập nhật điểm khi đã chấm (chỉ Admin/Giáo vụ)
- **`+getXepLoai()`**: Trả về xếp loại dựa trên điểm (Xuất sắc, Giỏi, Khá, TB, Yếu)
- **`+coTheXemKetQua()`**: Kiểm tra có thể xem kết quả không
- **`+kiemTraQuyenCham(username, role)`**: Kiểm tra quyền chấm bài của user
- **`+kiemTraGiamThiDuocPhanCong(username)`**: Kiểm tra giám thị có được phân công không

### Class PhieuDangKy - Quản lý đăng ký thi
- **`+xacNhanTT()`**: Xác nhận thanh toán phí đăng ký (alias cho `dongPhi()`)
- **`+taoPhieu(thiSinh, kyThi)`**: Static method tạo phiếu đăng ký mới cho thí sinh
- **`+dongPhi()`**: Xác nhận đóng phí và cập nhật trạng thái
- **`+tinhPhi()`**: Tính phí đăng ký dựa trên kỳ thi

### Class User - Quản lý tài khoản
- **`+getFullName()`**: Trả về họ tên đầy đủ của người dùng
- **`+toString()`**: Hiển thị thông tin user (ẩn password)

### Enum TrangThaiBaiThi - Quản lý trạng thái
- **`+getMoTa()`**: Trả về mô tả chi tiết của từng trạng thái bài thi

## 🔄 Flow xử lý chính

### 1. Quy trình đăng ký thi:
```
Thí sinh đăng ký → kiemTraTrung() → themThiSinh() → taoPhieu() → xacNhanTT()
```

### 2. Quy trình thi và chấm:
```
batDauThi() → ĐANG_THI → nopBai() → CHƯA_CHẤM → batDauCham() → nhapDiem() → ĐÃ_CHẤM
```

### 3. Quy trình phân quyền:
```
User.role → AuthenticationService → Kiểm tra quyền → Cho phép/Từ chối thao tác
```

## 🚨 **Exception Handling System**

Hệ thống được trang bị exception handling toàn diện để xử lý các lỗi nghiệp vụ:

### **Exception Hierarchy:**
```
QuanLyKyThiException (base)
├── ThiSinhValidationException      # Lỗi validation thí sinh
├── GiamThiValidationException      # Lỗi validation giám thị  
├── KyThiValidationException        # Lỗi validation kỳ thi
├── DuplicateException              # Lỗi trùng lặp dữ liệu
├── NotFoundException               # Không tìm thấy
├── PermissionException             # Lỗi phân quyền
├── StateTransitionException        # Lỗi chuyển trạng thái
├── PaymentException                # Lỗi thanh toán
├── XMLDatabaseException            # Lỗi cơ sở dữ liệu
└── AuthenticationException         # Lỗi xác thực
```

### **Exception Handling trong Methods:**

#### ThiSinh.validate() throws ThiSinhValidationException:
- ❌ Họ tên rỗng hoặc < 2 ký tự
- ❌ Họ tên chứa ký tự đặc biệt
- ❌ Tuổi < 18 hoặc > 35
- ❌ SĐT không đúng format (10 số, bắt đầu 0)
- ❌ Địa chỉ rỗng hoặc < 5 ký tự
- ❌ Giới tính không hợp lệ
- ❌ Mã thí sinh sai format TSxxx

#### KyThi.themThiSinh() throws:
- **KyThiValidationException**: Input null, trạng thái kỳ thi không phù hợp
- **DuplicateException**: Thí sinh đã đăng ký
- **ThiSinhValidationException**: Thông tin thí sinh không hợp lệ

#### KetQua state methods throws:
- **StateTransitionException**: Chuyển trạng thái không hợp lệ
- **PermissionException**: Không có quyền thực hiện
- **QuanLyKyThiException**: Điểm không hợp lệ (0-10)

#### PhieuDangKy.dongPhi() throws PaymentException:
- ❌ Đã đóng phí trước đó
- ❌ Kỳ thi không hợp lệ
- ❌ Phí đăng ký <= 0

### **Best Practices:**
✅ **Graceful Error Handling**: Tất cả lỗi được catch và xử lý  
✅ **Meaningful Messages**: Thông báo lỗi chi tiết, dễ hiểu  
✅ **Input Validation**: Kiểm tra đầu vào trước khi xử lý  
✅ **State Validation**: Đảm bảo business logic đúng  
✅ **Security**: Kiểm tra phân quyền trước mỗi thao tác

---
*📝 Lưu ý: Các method được thiết kế theo nguyên tắc Single Responsibility và đảm bảo tính nhất quán dữ liệu*
