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

### Class ThiSinh - Quản lý thông tin thí sinh
- **`+getAge()`**: Tính tuổi thí sinh dựa trên ngày sinh
- **`+validate()`**: Kiểm tra tính hợp lệ của thông tin thí sinh (tuổi, SĐT, email)

### Class GiamThi - Quản lý giám thị
- **`+phanCong()`**: Phân công giám thị cho kỳ thi cụ thể
- **`+toString()`**: Hiển thị thông tin giám thị dạng chuỗi

### Class KetQua - Quản lý kết quả thi
- **`+batDauThi()`**: Chuyển trạng thái từ CHƯA_THI → ĐANG_THI
- **`+nopBai()`**: Chuyển trạng thái từ ĐANG_THI → ĐÃ_NỘP_BÀI → CHƯA_CHẤM
- **`+batDauCham()`**: Chuyển trạng thái từ CHƯA_CHẤM → ĐANG_CHẤM (ghi nhận người chấm)
- **`+nhapDiem(diem: double)`**: Nhập điểm và chuyển → ĐÃ_CHẤM
- **`+capNhatDiem(diem: double)`**: Cập nhật điểm khi đã chấm (chỉ Admin/Giáo vụ)
- **`+getXepLoai()`**: Trả về xếp loại dựa trên điểm (Xuất sắc, Giỏi, Khá, TB, Yếu)

### Class PhieuDangKy - Quản lý đăng ký thi
- **`+xacNhanTT()`**: Xác nhận thanh toán phí đăng ký
- **`+taoPhieu()`**: Tạo phiếu đăng ký mới cho thí sinh

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

---
*📝 Lưu ý: Các method được thiết kế theo nguyên tắc Single Responsibility và đảm bảo tính nhất quán dữ liệu*
