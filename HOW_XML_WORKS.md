                                                                                                ## 📝 Demo: Cách XMLDatabase tạo file XML tự động

### **Khi chạy ứng dụng lần đầu:**

```java
// 1. XMLDatabase được khởi tạo
XMLDatabase database = new XMLDatabase();

// 2. Kiểm tra thư mục data/ có tồn tại không
File dataDir = new File("data/");
if (!dataDir.exists()) {
    dataDir.mkdirs(); // Tạo thư mục data/
}

// 3. Kiểm tra file users.xml có tồn tại không
if (!new File("data/users.xml").exists()) {
    // Tạo dữ liệu mẫu
    List<User> sampleUsers = new ArrayList<>();
    sampleUsers.add(new User("admin", "admin123", "Quản trị viên", "admin@email.com", "admin"));
    
    // Lưu vào XML
    saveUsers(sampleUsers);
}
```

### **Quá trình tạo file XML:**

```java
public void saveUsers(List<User> users) {
    try {
        // 1. Tạo DocumentBuilder
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();
        
        // 2. Tạo root element
        Element root = doc.createElement("users");
        doc.appendChild(root);
        
        // 3. Duyệt qua từng user và tạo XML element
        for (User user : users) {
            Element userElement = doc.createElement("user");
            
            // Tạo các child element
            userElement.appendChild(createElement(doc, "username", user.getUsername()));
            userElement.appendChild(createElement(doc, "password", user.getPassword()));
            userElement.appendChild(createElement(doc, "hoTen", user.getHoTen()));
            userElement.appendChild(createElement(doc, "email", user.getEmail()));
            userElement.appendChild(createElement(doc, "role", user.getRole()));
            userElement.appendChild(createElement(doc, "isActive", String.valueOf(user.isActive())));
            
            root.appendChild(userElement);
        }
        
        // 4. Ghi file XML ra đĩa
        saveXMLDocument(doc, "data/users.xml");
        
    } catch (Exception e) {
        System.err.println("Lỗi khi lưu users: " + e.getMessage());
    }
}
```

### **Kết quả file `data/users.xml` được tạo:**

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

### **Cách thêm dữ liệu mới:**

```java
// Khi bạn thêm kỳ thi mới trong AddKyThiForm:
private void saveKyThi() {
    // 1. Tạo đối tượng KyThi
    KyThi kyThi = new KyThi(maKyThi, tenKyThi, ngayToChuc, tinhTrang, 
                           new ArrayList<>(), new ArrayList<>(), phiDangKy);
    
    // 2. Thêm vào danh sách trong memory
    danhSachKyThi.add(kyThi);
    
    // 3. Lưu toàn bộ danh sách vào XML
    XMLDatabase database = new XMLDatabase();
    database.saveKyThi(danhSachKyThi);
}
```

### **File `data/kythi.xml` sẽ được cập nhật:**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<kyThiList>
  <kyThi>
    <maKyThi>KT001</maKyThi>
    <tenKyThi>Kỳ thi Toán học</tenKyThi>
    <ngayToChuc>2024-12-15</ngayToChuc>
    <tinhTrang>Sắp diễn ra</tinhTrang>
    <phiDangKy>50000.0</phiDangKy>
    
    <!-- Danh sách thí sinh (rỗng ban đầu) -->
    <danhSachThiSinh>
    </danhSachThiSinh>
    
    <!-- Danh sách giám thị (rỗng ban đầu) -->
    <danhSachGiamThi>
    </danhSachGiamThi>
  </kyThi>
  
  <!-- Kỳ thi mới sẽ được thêm vào đây -->
</kyThiList>
```

## **🔍 Điểm khác biệt chính:**

| **Aspect** | **CSV/Excel** | **XML** |
|------------|---------------|---------|
| **Cấu trúc** | Bảng 2D (hàng/cột) | Cây phân cấp |
| **Mối quan hệ** | Khó biểu diễn | Dễ dàng lồng nhau |
| **Metadata** | Không có | Rich metadata |
| **Validation** | Hạn chế | Schema validation |
| **Kích thước** | Nhỏ gọn | Lớn hơn do tags |
| **Đọc/ghi** | Đơn giản | Phức tạp hơn |

## **💡 Tại sao chọn XML cho project này:**

1. **Mối quan hệ phức tạp**: KyThi chứa nhiều ThiSinh và GiamThi
2. **Cấu trúc lồng nhau**: XML xử lý tốt hơn CSV
3. **Tự mô tả**: XML tags giải thích ý nghĩa dữ liệu
4. **Validation**: Có thể validate cấu trúc dữ liệu
5. **Standard**: XML là chuẩn công nghiệp

**🎯 Tóm lại: XML không phải bảng như Excel, mà là cấu trúc cây cho phép lưu trữ dữ liệu phức tạp với mối quan hệ!**
