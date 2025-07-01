# Hệ Thống Quản Lý Kỳ Thi

## Mô tả
Hệ thống quản lý kỳ thi được phát triển bằng Java, hỗ trợ quản lý thông tin thí sinh, giám thị, và kết quả thi.

## Tính năng chính
- **Quản lý Thí sinh**: Đăng ký, xem thông tin thí sinh
- **Quản lý Kỳ thi**: Tạo kỳ thi, thêm thí sinh và giám thị
- **Quản lý Giám thị**: Phân công giám thị cho các kỳ thi
- **Quản lý Kết quả**: Nhập điểm và xem kết quả thi
- **Phiếu đăng ký**: Tự động tạo phiếu đăng ký và tính phí

## Cấu trúc dự án
```
src/
├── QuanLyKyThi/
│   ├── KyThi.java          # Class quản lý kỳ thi
│   ├── ThiSinh.java        # Class quản lý thí sinh
│   ├── GiamThi.java        # Class quản lý giám thị
│   ├── KetQua.java         # Class quản lý kết quả thi
│   ├── PhieuDangKy.java    # Class quản lý phiếu đăng ký
│   ├── MainGUI.java        # Giao diện chính
│   ├── AddKyThiForm.java   # Form thêm kỳ thi
│   ├── KyThiListForm.java  # Form danh sách kỳ thi
│   └── StubForms.java      # Các form phụ
└── demo/
    └── Demo.java           # File demo
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

## Cách sử dụng

```java
// Tạo kỳ thi
KyThi kyThi = new KyThi("KT001", "Kỳ thi Java", LocalDate.now(), "Đang diễn ra", 
                        new ArrayList<>(), new ArrayList<>());

// Tạo thí sinh
ThiSinh thiSinh = new ThiSinh("TS001", "Nguyễn Văn A", LocalDate.of(2000, 1, 1), 
                              "Nam", "Hà Nội", "0123456789");

// Đăng ký thi
thiSinh.dangKythi(kyThi);

// Nhập điểm
kyThi.nhapDiem(thiSinh, 8.5);

// Xem kết quả
thiSinh.xemketqua(kyThi);
```

## Yêu cầu hệ thống
- Java 8 trở lên
- NetBeans IDE (khuyến nghị)

## Tác giả
- **ad** - Phát triển ban đầu

## Lịch sử phiên bản
- **v1.0** - Phiên bản đầu tiên với các tính năng cơ bản
