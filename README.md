# Hệ Thống Quản Lý Kỳ Thi

## ✨ **Cập nhật mới nhất**
**🎉 TẤT CẢ FORMS GUI ĐÃ ĐƯỢC HOÀN THIỆN!**

- ✅ **9 Forms chức năng đầy đủ**: Thay thế hoàn toàn các stub forms cũ
- 🎨 **Giao diện chuyên nghiệp**: Sử dụng Swing với layout đẹp mắt
- 🔗 **Tích hợp database**: Tất cả forms kết nối XMLDatabase
- ⚡ **Exception handling**: Xử lý lỗi và validation đầy đủ
- 📊 **Thống kê trực quan**: Báo cáo chi tiết với charts và tables

## Mô tả
Hệ thống quản lý kỳ thi được phát triển bằng Java Swing, hỗ trợ đầy đủ quản lý thông tin thí sinh, giám thị, và kết quả thi với giao diện đồ họa hoàn chỉnh.

## Tính năng chính

- **🔐 Hệ thống đăng nhập**: Xác thực người dùng với phân quyền theo role
- **� Quản lý Tài khoản**: Xem thông tin, đổi mật khẩu, đăng ký tài khoản mới
- **�👥 Quản lý Thí sinh**: Đăng ký, xem thông tin thí sinh với form chuyên nghiệp
- **📝 Quản lý Kỳ thi**: Tạo kỳ thi, thêm thí sinh và giám thị với phí riêng biệt
- **👨‍🏫 Quản lý Giám thị**: Phân công giám thị cho các kỳ thi
- **📊 Quản lý Kết quả**: Nhập điểm và xem kết quả thi với giao diện trực quan
- **⚡ Quản lý Trạng thái Bài thi**: Theo dõi chi tiết trạng thái từng bài thi (chưa thi → đang thi → đã nộp → chưa chấm → đang chấm → đã chấm)
- **🔒 Phân quyền nhập điểm**: Admin, Giáo vụ, Giám thị có quyền khác nhau trong việc chấm bài và nhập điểm
- **📋 Phiếu đăng ký**: Tự động tạo phiếu đăng ký và xác nhận đóng phí
- **💾 Cơ sở dữ liệu XML**: Lưu trữ dữ liệu persistent trong các file XML
- **🎨 Giao diện đồ họa**: Các form GUI đầy đủ chức năng với Swing
- **📈 Thống kê báo cáo**: Thống kê tổng quan và chi tiết theo kỳ thi
- **🔍 Tìm kiếm và lọc**: Tìm kiếm thông tin nhanh chóng trong các form

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
│   ├── Exception Handling/
│   │   └── Exceptions.java         # Tất cả custom exceptions
│   ├── GUI - Main/
│   │   ├── MainGUI.java            # Giao diện chính (legacy)
│   │   └── MainGUIWithAuth.java    # Giao diện chính với authentication
│   ├── GUI - Forms (Hoàn thiện)/
│   │   ├── AddKyThiForm.java       # Form thêm kỳ thi
│   │   ├── KyThiListForm.java      # Form danh sách kỳ thi
│   │   ├── ThiSinhListForm.java    # Form danh sách thí sinh (✅ Hoàn thiện)
│   │   ├── AddThiSinhForm.java     # Form thêm thí sinh (✅ Hoàn thiện)
│   │   ├── DangKyThiForm.java      # Form đăng ký thi (✅ Hoàn thiện)
│   │   ├── GiamThiListForm.java    # Form danh sách giám thị (✅ Hoàn thiện)
│   │   ├── AddGiamThiForm.java     # Form thêm giám thị (✅ Hoàn thiện)
│   │   ├── PhanCongGiamThiForm.java # Form phân công giám thị (✅ Hoàn thiện)
│   │   ├── NhapDiemForm.java       # Form nhập điểm (✅ Hoàn thiện)
│   │   ├── XemKetQuaForm.java      # Form xem kết quả (✅ Hoàn thiện)
│   │   ├── ThongKeForm.java        # Form thống kê (✅ Hoàn thiện)
│   │   ├── QuanLyTrangThaiBaiThiForm.java  # Form quản lý trạng thái bài thi
│   │   └── StubForms.java          # Documentation các form đã tách (legacy)
│   └── Testing/
│       └── TestForms.java          # Test runner cho các forms
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

## 📐 UML Diagrams

Hệ thống được thiết kế theo mô hình **MVC (Model-View-Controller)** với **kiến trúc 3-layer** rõ ràng.

### 🔗 **Xem UML chi tiết**: [`UML_DIAGRAMS.md`](UML_DIAGRAMS.md) | [`UML_ASCII.md`](UML_ASCII.md)

### 📊 **Class Diagram** - Core Structure

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                          HỆTHỐNG QUẢN LÝ KỲ THI                             │
└─────────────────────────────────────────────────────────────────────────────┘

     ┌──────────────┐          ┌─────────────┐          ┌─────────────┐
     │    KyThi     │          │  ThiSinh    │          │   GiamThi   │
     ├──────────────┤    1:N   ├─────────────┤    1:N   ├─────────────┤
     │-maKyThi      │◆────────◇│-maThisinh   │          │-maGiamThi   │
     │-tenKyThi     │          │-hoTen       │          │-hoTen       │
     │-ngayToChuc   │          │-ngaySinh    │          │-donVi       │
     │-tinhTrang    │          │-gioiTinh    │          │-sdt         │
     │-phiDangKy    │          │-diaChi      │          │-email       │
     │-danhSachTS   │          │-sdt         │          │-username    │
     │-danhSachGT   │          ├─────────────┤          ├─────────────┤
     ├──────────────┤          │+getAge()    │          │+toString()  │
     │+themThiSinh()│          │+validate()  │          └─────────────┘
     │+themGiamThi()│          └─────────────┘                 │
     │+kiemTraTrung()│                │                        │1:1
     └──────────────┘                │1:N                     ▼
            │                        ▼               ┌─────────────┐
            │                ┌─────────────┐         │    User     │
            │                │   KetQua    │         ├─────────────┤
            │                ├─────────────┤         │-username    │
            └─────────────1:N│-thiSinh     │         │-password    │
                             │-kyThi       │         │-role        │
                             │-diem        │         │-email       │
                             │-trangThai   │◆──────▶ │+getFullName()│
                             │-nguoiCham   │         └─────────────┘
                             │-thoiGianCham│
                             ├─────────────┤               │1:1
                             │+batDauThi() │               ▼
                             │+nopBai()    │        ┌─────────────┐
                             │+nhapDiem()  │        │PhieuDangKy  │
                             └─────────────┘        ├─────────────┤
                                   │1:1             │-thiSinh     │
                                   ▼                │-kyThi       │
                        ┌─────────────────┐         │-phiDangKy   │
                        │ TrangThaiBaiThi │         │-daThanhToan │
                        │   <<enum>>      │         │+taoPhieu()  │
                        ├─────────────────┤         └─────────────┘
                        │ CHUA_THI        │
                        │ DANG_THI        │
                        │ DA_NOP_BAI      │
                        │ CHUA_CHAM       │
                        │ DANG_CHAM       │
                        │ DA_CHAM         │
                        └─────────────────┘
```

### 🔄 **State Diagram** - Vòng đời Bài thi

```
                   VÒNG ĐỜI BÀI THI

    [START] ────► CHƯA THI ────batDauThi()────► ĐANG THI
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

### 🎭 **Use Case Diagram** - Phân quyền hệ thống

```
                        HỆ THỐNG PHÂN QUYỀN

    ┌─────────────┐                     ┌─────────────────────────────────┐
    │👨‍💼 ADMIN    │                     │        CÁC CHỨC NĂNG            │
    │             │──────────────────▶ │                                 │
    │Toàn quyền   │ ✅ CÓ TẤT CẢ       │ 🔐 Quản lý tài khoản            │
    └─────────────┘                    │ 📝 Tạo/sửa kỳ thi               │
                                       │ 👨‍🏫 Quản lý giám thị             │
    ┌─────────────┐                    │ ⚡ Bắt đầu thi                   │
    │👩‍🏫 GIÁO VỤ  │                     │ 📄 Nộp bài                      │
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

### 🏗️ **Component Architecture** - Kiến trúc 3-layer

```
┌─────────────────────────────────────────────────────────────────────┐
│  PRESENTATION LAYER (GUI)                                          │
│  ┌─────────────┐ ┌─────────────┐ ┌─────────────┐ ┌─────────────┐  │
│  │ LoginForm   │ │ MainGUI     │ │ QuanLyForm  │ │ Add*Forms   │  │
│  │🔐           │ │🏠           │ │📊           │ │➕           │  │
│  └─────────────┘ └─────────────┘ └─────────────┘ └─────────────┘  │
├─────────────────────────────────────────────────────────────────────┤
│  BUSINESS LOGIC LAYER (Services + Models)                          │
│  ┌─────────────┐ ┌─────────────┐ ┌─────────────┐ ┌─────────────┐  │
│  │AuthService  │ │ KyThi       │ │ ThiSinh     │ │ GiamThi     │  │
│  │🔑           │ │📝           │ │👥           │ │👨‍🏫           │  │
│  └─────────────┘ └─────────────┘ └─────────────┘ └─────────────┘  │
│  ┌─────────────┐ ┌─────────────┐ ┌─────────────┐ ┌─────────────┐  │
│  │ KetQua      │ │ PhieuDangKy │ │ User        │ │TrangThaiBT  │  │
│  │📊           │ │📋           │ │👤           │ │🔄           │  │
│  └─────────────┘ └─────────────┘ └─────────────┘ └─────────────┘  │
├─────────────────────────────────────────────────────────────────────┤
│  DATA ACCESS LAYER (Persistence)                                   │
│  ┌─────────────────────────────────────────────────────────────┐   │
│  │                XMLDatabase                                  │   │
│  │💾 Load/Save XML • CRUD Operations • Data Validation        │   │
│  └─────────────────────────────────────────────────────────────┘   │
├─────────────────────────────────────────────────────────────────────┤
│  DATA STORAGE (XML Files)                                          │
│  ┌───────────┐┌───────────┐┌───────────┐┌───────────┐┌───────────┐ │
│  │users.xml  ││kythi.xml  ││thisinh.xml││giamthi.xml││ketqua.xml │ │
│  │👥         ││📝         ││🎓         ││👨‍🏫         ││📊         │ │
│  └───────────┘└───────────┘└───────────┘└───────────┘└───────────┘ │
└─────────────────────────────────────────────────────────────────────┘
```

### 🔍 **Method quan trọng: `kiemTraTrung()`**

Method `kiemTraTrung()` trong class `KyThi` là một method quan trọng đảm bảo tính toàn vẹn dữ liệu:

```java
public boolean kiemTraTrung(String ma, String loai)
```

**🎯 Mục đích**: 
- Kiểm tra trùng lặp mã thí sinh hoặc mã giám thị trong kỳ thi
- Đảm bảo tính duy nhất và tránh duplicate data

**📝 Tham số**:
- `ma`: Mã cần kiểm tra (mã thí sinh hoặc mã giám thị)  
- `loai`: "THISINH" hoặc "GIAMTHI"

**📤 Trả về**: 
- `true` nếu mã đã tồn tại (trùng lặp)
- `false` nếu mã chưa tồn tại (an toàn để thêm)

**🔧 Cách sử dụng**:
```java
// Kiểm tra thí sinh
boolean daTonTai = kyThi.kiemTraTrung("TS001", "THISINH");

// Kiểm tra giám thị  
boolean daTonTai = kyThi.kiemTraTrung("GT001", "GIAMTHI");
```

**💡 Ý nghĩa trong UML**:
- Method này được gọi bên trong `themThiSinh()` và `themGiamThi()`
- Đảm bảo business rules: "Mỗi thí sinh/giám thị chỉ được đăng ký 1 lần cho 1 kỳ thi"
- Hỗ trợ data validation và user experience tốt hơn

---

### 🎨 **Design Patterns được sử dụng:**
- **MVC Pattern**: Tách biệt GUI, Logic, Data
- **State Pattern**: Quản lý trạng thái bài thi với 6 states
- **Factory Pattern**: Tạo objects XML và Forms
- **Observer Pattern**: Notification giữa components
- **Strategy Pattern**: Phân quyền theo role
- **DAO Pattern**: XMLDatabase access layer

## 🚀 Cách sử dụng

### Entry Points (Điểm khởi chạy):
Dự án có **3 điểm khởi chạy** chính:

1. **`LoginForm.main()`** - **⭐ KHUYẾN NGHỊ** - Entry point có authentication và phân quyền
   ```bash
   java -cp src QuanLyKyThi.LoginForm
   ```

2. **`MainGUIWithAuth.main()`** - Entry point với authentication (bypass login form)
   ```bash
   java -cp src QuanLyKyThi.MainGUIWithAuth
   ```

3. **`MainGUI.main()`** - Entry point trực tiếp vào giao diện chính (bypass login)
   ```bash
   java -cp src QuanLyKyThi.MainGUI
   ```

4. **`TestForms.main()`** - **🧪 TESTING** - Test tất cả forms GUI
   ```bash
   java -cp src QuanLyKyThi.TestForms
   ```

### 🎯 **Hướng dẫn sử dụng các Form chính:**

#### 1. **Quản lý Tài khoản**
- **Xem thông tin**: Menu → Hệ thống → Thông tin Tài khoản
- **Đổi mật khẩu**: Menu → Hệ thống → Đổi Mật khẩu
- **Quản lý người dùng** (Admin): Menu → Hệ thống → Quản lý Tài khoản
- **Đăng ký tài khoản mới**: Menu → Hệ thống → Đăng ký Tài khoản Mới

#### 2. **Quản lý Thí sinh**
- **Xem danh sách**: Menu → Quản lý Thí sinh → Danh sách Thí sinh
- **Thêm mới**: Click "Thêm mới" trong ThiSinhListForm
- **Sửa/Xóa**: Chọn hàng trong bảng → Click "Sửa" hoặc "Xóa"

#### 3. **Đăng ký Thi**
- **Đăng ký**: Menu → Quản lý Thi → Đăng ký Thi
- **Chọn thí sinh** từ ComboBox
- **Chọn kỳ thi** từ bảng → Click "Đăng ký"

#### 4. **Quản lý Giám thị**
- **Xem danh sách**: Menu → Quản lý Giám thị → Danh sách Giám thị
- **Phân công**: Menu → Quản lý Giám thị → Phân công Giám thị

#### 5. **Nhập điểm và Xem kết quả**
- **Nhập điểm**: Menu → Quản lý Kết quả → Nhập điểm
- **Xem kết quả**: Menu → Quản lý Kết quả → Xem kết quả
- **Thống kê**: Menu → Báo cáo → Thống kê

### 🔐 Authentication Flow (Luồng đăng nhập):
Khi sử dụng `LoginForm.main()`, hệ thống sẽ tự động chuyển hướng theo quyền:

| Role         | Username/Password | Chuyển hướng đến  |             Quyền hạn               |
|--------------|-------------------|-------------------|-------------------------------------|
| **Admin**    | `admin/admin123`  | `MainGUIWithAuth` | Toàn quyền: CRUD tất cả             |
| **Giáo vụ**  | `giaovu/gv123`    | `MainGUIWithAuth` | Quản lý kỳ thi, nhập điểm, thống kê |
| **User**     | `user1/user123`   |     `MainGUI`     | Xem thông tin, đăng ký thi          |
| **Thí sinh** | `thisinh1/ts123`  |     `MainGUI`     | Chỉ xem kết quả của mình            |

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
| Chức năng         | Admin | Giáo vụ | Giám thị | User |
|-------------------|------ |---------|----------|------|
| Quản lý tài khoản |  ✅  |    ❌   |    ❌   |  ❌  |
| Thêm/sửa kỳ thi   |  ✅  |    ✅   |    ❌   |  ❌  |
| Quản lý giám thị  |  ✅  |    ✅   |    ❌   |  ❌  | 
| Bắt đầu thi       |  ✅  |    ❌   |    ✅   |  ❌  |
| Nộp bài thi       |  ✅  |    ❌   |    ✅   |  ❌  |
| Bắt đầu chấm bài  |  ✅  |    ✅   |    ✅*  |  ❌  |
| Nhập điểm         |  ✅  |    ✅   |    ✅*  |  ❌  |
| Cập nhật điểm     |  ✅  |    ✅   |    ❌** |  ❌  |
| Xem kỳ thi        |  ✅  |    ✅   |    ✅   |  ✅  |

**Ghi chú:**
- ✅* : Giám thị chỉ được chấm/nhập điểm cho kỳ thi được phân công
- ❌** : Giám thị chỉ được cập nhật điểm bài mình đã chấm

## 📊 Hệ thống Trạng thái Bài thi

### Quy trình trạng thái bài thi:
```
CHƯA THI → ĐANG THI → ĐÃ NỘP BÀI → CHƯA CHẤM → ĐANG CHẤM → ĐÃ CHẤM
```

### Chi tiết các trạng thái:

| Trạng thái     |              Mô tả            |       Thao tác được phép        |
|----------------|-------------------------------|---------------------------------|
| **CHƯA THI**   | Thí sinh chưa bắt đầu làm bài | Bắt đầu thi                     |
| **ĐANG THI**   | Thí sinh đang làm bài thi     | Nộp bài                         |
| **ĐÃ NỘP BÀI** | Thí sinh đã nộp bài           | Tự động chuyển sang "Chưa chấm" |
| **CHƯA CHẤM**  | Bài thi chờ được chấm         | Bắt đầu chấm                    |
| **ĐANG CHẤM**  | Bài thi đang được chấm        | Nhập điểm                       |
| **ĐÃ CHẤM**    | Bài thi đã hoàn thành chấm    | Xem kết quả, Cập nhật điểm      |

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

#### 3. `users.xml` - Tài khoản người dùng:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<userList>
  <user>
    <username>admin</username>
    <password>admin123</password>
    <role>ADMIN</role>
    <email>admin@quanlykythi.com</email>
  </user>
</userList>
```

#### 4. `giamthi.xml` - Giám thị:
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

### ✅ **Completed in Current Version:**
- [x] **Hoàn thiện tất cả GUI Forms**: Đã thay thế tất cả stub forms bằng forms chức năng đầy đủ
- [x] **Quản lý Tài khoản**: Xem thông tin, đổi mật khẩu, đăng ký tài khoản mới với phân quyền
- [x] **ThiSinhListForm & AddThiSinhForm**: Quản lý thí sinh hoàn chỉnh
- [x] **DangKyThiForm**: Đăng ký thi với giao diện trực quan
- [x] **GiamThiListForm & AddGiamThiForm**: Quản lý giám thị
- [x] **PhanCongGiamThiForm**: Phân công giám thị cho kỳ thi
- [x] **NhapDiemForm**: Nhập điểm với validation đầy đủ
- [x] **XemKetQuaForm**: Xem kết quả với sắp xếp và xếp loại
- [x] **ThongKeForm**: Thống kê tổng quan và chi tiết
- [x] **UserManagementForm**: Quản lý tài khoản người dùng (Admin only)
- [x] **AccountInfoForm**: Xem và chỉnh sửa thông tin tài khoản
- [x] **ChangePasswordForm**: Đổi mật khẩu với bảo mật cao
- [x] **RegisterForm**: Đăng ký tài khoản mới với validation
- [x] **Exception handling**: Xử lý lỗi chuyên nghiệp trong tất cả forms
- [x] **Database integration**: Tất cả forms kết nối XMLDatabase

### Version 2.0 (Coming soon):
- [ ] **REST API**: Web service cho mobile app
- [ ] **MySQL integration**: Hỗ trợ database quan hệ
- [ ] **Reporting**: Xuất báo cáo PDF/Excel
- [ ] **Email notification**: Gửi email thông báo kết quả
- [ ] **QR Code**: Tạo QR code cho phiếu dự thi
- [ ] **Multi-language**: Hỗ trợ đa ngôn ngữ

### Version 1.5 (Next release):
- [ ] **Advanced Search**: Tìm kiếm nâng cao với filter
- [ ] **Data validation**: Validation mạnh hơn cho input
- [ ] **UI/UX improvements**: Cải thiện giao diện với modern look
- [ ] **Import/Export**: Import từ Excel, export dữ liệu
- [ ] **Backup & Restore**: Backup tự động và khôi phục
- [ ] **Audit Log**: Ghi lại lịch sử thao tác của user

## 📞 Liên hệ & Hỗ trợ

- **Team**: Trần Thái Hưng (MSV: 23010693), Hoàng Tiến Đạt (MSV: 23010864)
- **GitHub**: [OOP Repository](https://github.com/ghism137/OOP)
- **Email**: support@quanlykythi.com
- **Documentation**: Xem thêm trong folder `docs/`

---

## 📄 Giấy phép
Dự án được phát triển cho mục đích học tập tại trường Đại học. 

**© 2025 QuanLyKyThi Team. All rights reserved.**

## 🎨 Các Form GUI đã hoàn thiện

### 👥 **Quản lý Thí sinh**
- **ThiSinhListForm.java**: Danh sách thí sinh với bảng hiển thị
  - Hiển thị đầy đủ thông tin: Mã TS, Họ tên, Ngày sinh, Địa chỉ, Giới tính, SĐT
  - Chức năng: Thêm mới, Sửa, Xóa, Làm mới
  - Kết nối XMLDatabase để load/save data

- **AddThiSinhForm.java**: Form thêm thí sinh mới
  - Các trường: Mã thí sinh, Họ tên, Ngày sinh (dd/MM/yyyy), Giới tính, Địa chỉ, SĐT
  - Validation đầy đủ: Kiểm tra rỗng, format ngày, format SĐT (10-11 số)
  - Kiểm tra trùng lặp mã thí sinh
  - Giao diện: GridBagLayout với 500x400px

### 📝 **Quản lý Đăng ký Thi**
- **DangKyThiForm.java**: Form đăng ký thi cho thí sinh
  - ComboBox chọn thí sinh với renderer đẹp (Mã - Họ tên)
  - Bảng danh sách kỳ thi với cột: Mã, Tên, Ngày, Môn, Phí, Trạng thái
  - Chức năng: Đăng ký, Hủy đăng ký, Làm mới
  - Tự động cập nhật trạng thái "Đã đăng ký" / "Chưa đăng ký"

### 👨‍🏫 **Quản lý Giám thị**
- **GiamThiListForm.java**: Danh sách giám thị
  - Bảng hiển thị: Mã GT, Họ tên, Đơn vị, SĐT, Email, Username
  - Chức năng CRUD đầy đủ: Thêm, Sửa, Xóa, Làm mới
  - Kết nối database với exception handling

- **AddGiamThiForm.java**: Form thêm giám thị mới
  - Các trường: Mã giám thị, Họ tên, Đơn vị, SĐT, Email, Username
  - Validation cơ bản và kiểm tra trùng lặp
  - Layout gọn gàng 500x350px

- **PhanCongGiamThiForm.java**: Form phân công giám thị
  - ComboBox chọn kỳ thi với renderer
  - Bảng giám thị với cột trạng thái phân công
  - Chức năng: Phân công, Hủy phân công, Làm mới

### 📊 **Quản lý Điểm và Kết quả**
- **NhapDiemForm.java**: Form nhập điểm thi
  - ComboBox chọn kỳ thi
  - Bảng thí sinh với cột nhập điểm
  - Validation điểm số (0-10), chỉ nhận số thực
  - Lưu kết quả vào XMLDatabase với exception handling

- **XemKetQuaForm.java**: Form xem kết quả thi
  - ComboBox chọn kỳ thi với auto-refresh
  - Bảng kết quả: Mã TS, Họ tên, Điểm, Xếp loại, Trạng thái, Thời gian thi
  - Tự động sắp xếp theo điểm giảm dần
  - Chức năng: Làm mới, Xuất Excel (stub), In báo cáo (stub)

### 📈 **Thống kê và Báo cáo**
- **ThongKeForm.java**: Form thống kê tổng hợp
  - **Phần thống kê tổng quan** (JTextArea):
    - Tổng số kỳ thi, thí sinh, lượt đăng ký
    - Điểm trung bình hệ thống
    - Phân loại kết quả: Xuất sắc, Giỏi, Khá, Trung bình, Yếu/Kém
  - **Phần thống kê chi tiết** (JTable):
    - Bảng theo kỳ thi: Tên, Số TS, Điểm TB, Cao nhất, Thấp nhất, Đạt, Không đạt, Tỷ lệ đạt
  - JSplitPane chia 2 phần với tỷ lệ hợp lý

### 🧪 **Testing và Utilities**
- **TestForms.java**: Test runner cho tất cả forms
  - Mở tất cả forms cùng lúc để kiểm tra
  - Position offset để không chồng lấp
  - Console log tiến trình test

### 👤 **Quản lý Tài khoản**
- **AccountInfoForm.java**: Form xem và chỉnh sửa thông tin tài khoản
  - Hiển thị đầy đủ thông tin: Username, Họ tên, Email, Vai trò, Trạng thái, Lần đăng nhập cuối
  - Chức năng: Cập nhật thông tin, Đổi mật khẩu
  - Validation đầy đủ và kết nối XMLDatabase

- **ChangePasswordForm.java**: Form đổi mật khẩu bảo mật
  - Các trường: Mật khẩu hiện tại, Mật khẩu mới, Xác nhận mật khẩu
  - Validation mạnh: Kiểm tra mật khẩu cũ, độ mạnh mật khẩu mới (tối thiểu 6 ký tự)
  - Mã hóa SHA-256 cho bảo mật
  - Giao diện: GridBagLayout với 400x300px

- **RegisterForm.java**: Form đăng ký tài khoản mới
  - Các trường: Username, Họ tên, Email, Vai trò, Mật khẩu, Xác nhận mật khẩu
  - Logic phân quyền: Admin cần duyệt, các role khác kích hoạt ngay
  - Validation đầy đủ: Kiểm tra trùng lặp username/email, format email
  - Checkbox đồng ý điều khoản sử dụng

- **UserManagementForm.java**: Form quản lý tài khoản (Admin only)
  - Bảng hiển thị: Username, Họ tên, Email, Vai trò, Trạng thái, Lần đăng nhập cuối
  - Chức năng: Thêm, Sửa, Xóa, Kích hoạt/Vô hiệu hóa tài khoản
  - Tìm kiếm và lọc theo vai trò
  - Bảo vệ tài khoản admin chính khỏi bị xóa
