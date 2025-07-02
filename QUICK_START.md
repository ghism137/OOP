## 🔗 HƯỚNG DẪN CHẠY CHƯƠNG TRÌNH VỚI DATABASE XML

### ✅ **Database đã sẵn sàng!**
Các file XML đã được tạo tự động tại thư mục `data/`:
- ✅ users.xml (7 users)
- ✅ kythi.xml (2 kỳ thi)  
- ✅ thisinh.xml (2 thí sinh)
- ✅ giamthi.xml (2 giám thị)
- ✅ ketqua.xml (2 kết quả)

### 🎮 **Cách chạy chương trình:**

#### **Option 1: Entry Point Chính (Có đăng nhập bắt buộc)**
```powershell
java -cp "src" QuanLyKyThi.MainGUIWithAuth
```

#### **Option 2: LoginForm (Entry point với authentication)**  
```powershell
java -cp "src" QuanLyKyThi.LoginForm
```

#### **Option 3: Test form riêng lẻ**
```powershell
# Test form quản lý trạng thái bài thi
java -cp "src" QuanLyKyThi.QuanLyTrangThaiBaiThiForm
```

### 🔐 **Tài khoản đăng nhập có sẵn:**

| Username | Password | Role | Mô tả |
|----------|----------|------|-------|
| admin | admin123 | Admin | Toàn quyền hệ thống |
| giaovu | gv123 | Giáo vụ | Quản lý kỳ thi, nhập điểm |
| giamthi | giamthi123 | Giám thị | Giám sát thi, chấm bài |

### 📊 **Dữ liệu mẫu có sẵn:**
- **2 Kỳ thi**: Java OOP, Web Development
- **2 Thí sinh**: Nguyễn Văn A, Trần Thị B  
- **2 Giám thị**: Phạm Văn C, Lê Thị D
- **2 Kết quả**: Đã có điểm mẫu

### 💡 **Khuyến nghị:**
Chạy **`MainGUIWithAuth`** để có trải nghiệm đầy đủ với:
- Màn hình chào mừng
- Đăng nhập bắt buộc  
- Phân quyền theo role
- Giao diện quản lý hoàn chỉnh

```powershell
# Lệnh chạy khuyến nghị:
java -cp "src" QuanLyKyThi.MainGUIWithAuth
```
