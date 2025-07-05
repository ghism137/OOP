# Nhật ký thay đổi dự án FeersuProject

## Ngày: 05/07/2025

### 1. Nâng cấp và hiện đại hóa môi trường phát triển
- **Nâng cấp phiên bản Java:** Cập nhật `nbproject/project.properties` để sử dụng Java 11 (`javac.source=11`, `javac.target=11`).

### 2. Tái cấu trúc xử lý dữ liệu XML (JAXB Integration)
- **Giới thiệu JAXB:** Thay thế hoàn toàn logic đọc/ghi XML thủ công bằng JAXB để đơn giản hóa và tăng tính an toàn cho việc lưu trữ dữ liệu.
- **Tạo `DateAdapters.java`:** Thêm các `XmlAdapter` tùy chỉnh (`LocalDateAdapter`, `LocalDateTimeAdapter`) để JAXB có thể xử lý các kiểu dữ liệu ngày tháng hiện đại của Java (`java.time.LocalDate`, `java.time.LocalDateTime`).
- **Cập nhật các lớp Model:**
    - `User.java`: Thêm chú thích JAXB (`@XmlRootElement`, `@XmlAccessorType`) và sử dụng `DateAdapters.LocalDateTimeAdapter` cho các trường `lastLogin` và `createdDate`.
    - `ThiSinh.java`: Thêm chú thích JAXB (`@XmlRootElement`, `@XmlAccessorType`).
    - `GiamThi.java`: Thêm chú thích JAXB (`@XmlRootElement`, `@XmlAccessorType`).
    - `KyThi.java`: Thêm chú thích JAXB (`@XmlRootElement`, `@XmlAccessorType`, `@XmlElementWrapper`, `@XmlElement`), sử dụng `DateAdapters.LocalDateAdapter` cho trường `ngayToChuc`, và thêm các phương thức `setter` cho danh sách (`danhSachThiSinh`, `danhSachGiamThi`, `danhSachKetQua`).
    - `KetQua.java`: Thêm chú thích JAXB (`@XmlRootElement`, `@XmlAccessorType`, `@XmlElement`), sử dụng `DateAdapters.LocalDateTimeAdapter` cho các trường thời gian, và thêm các phương thức `setter` cho `thiSinh` và `kyThi`.
- **Tái cấu trúc `XMLDatabase.java`:**
    - Triển khai các phương thức `writeData` và `readData` chung (generic) để xử lý việc đọc/ghi danh sách đối tượng bằng JAXB.
    - Bổ sung các phương thức cụ thể cho từng loại dữ liệu (`saveUsers`, `loadUsers`, `saveThiSinh`, `loadThiSinh`, v.v.).
    - Hoàn thiện phương thức `linkData` để tái tạo các mối quan hệ đối tượng sau khi tải từ XML.
    - Thêm các phương thức `findUserByUsername`, `deleteUser`, `updateUser` để quản lý người dùng hiệu quả hơn.

### 3. Cải thiện bảo mật và tiện ích
- **Tạo `PasswordUtil.java`:** Giới thiệu một lớp tiện ích mới để xử lý việc băm và xác minh mật khẩu một cách an toàn hơn bằng cách sử dụng salt và SHA-256.
- **Tái cấu trúc `Exceptions.java`:** Chuyển tất cả các lớp ngoại lệ tùy chỉnh thành các lớp lồng nhau `public static` bên trong một lớp `public final class Exceptions` để cải thiện cấu trúc và khả năng truy cập.

### 4. Tái cấu trúc và sửa lỗi giao diện người dùng (GUI Forms)
- **`LoginForm.java`:**
    - Thay thế các `KeyListener` và `MouseAdapter` bằng biểu thức lambda để mã nguồn ngắn gọn hơn.
- **`MainGUI.java`:**
    - Thay thế `ComponentAdapter` bằng biểu thức lambda.
- **`MainGUIWithAuth.java`:**
    - Thay thế `ComponentAdapter` và `WindowAdapter` bằng biểu thức lambda.
    - Sửa đổi `openQuanLyTrangThaiBaiThiForm()` để tạo `JInternalFrame` thay vì `JFrame`.
- **`AddKyThiForm.java`:**
    - Cải thiện `generateKyThiId()` để tạo ID duy nhất hơn bằng cách tìm ID lớn nhất hiện có.
    - Sửa đổi `saveKyThi()` để lưu dữ liệu vào XML sau khi thêm kỳ thi mới.
- **`KyThiListForm.java`:**
    - Sửa đổi `deleteKyThi()` để lưu dữ liệu vào XML sau khi xóa kỳ thi.
- **`AddThiSinhForm.java`:**
    - Cập nhật các lời gọi `database.getAllThiSinh()` thành `database.loadThiSinh()`.
    - Sửa lỗi tham chiếu `ts.getMaThisinh()` thành `ts.getSoBaoDanh()`.
- **`ThiSinhListForm.java`:**
    - Cập nhật các lời gọi `database.getAllThiSinh()` thành `database.loadThiSinh()`.
    - Sửa lỗi tham chiếu `ts.getMaThisinh()` thành `ts.getSoBaoDanh()`.
    - Loại bỏ `Thread.sleep()` và đảm bảo dữ liệu được tải lại sau khi `AddThiSinhForm` đóng.
    - Sửa đổi logic xóa thí sinh để lưu dữ liệu vào XML.
- **`AddGiamThiForm.java`:**
    - Cập nhật các lời gọi `database.getAllGiamThi()` thành `database.loadGiamThi()`.
- **`GiamThiListForm.java`:**
    - Cập nhật các lời gọi `database.getAllGiamThi()` thành `database.loadGiamThi()`.
    - Loại bỏ `Thread.sleep()` và đảm bảo dữ liệu được tải lại sau khi `AddGiamThiForm` đóng.
    - Sửa đổi logic xóa giám thị để lưu dữ liệu vào XML.
- **`DangKyThiForm.java`:**
    - Cập nhật các lời gọi `database.getAll...()` thành `database.load...()`.
    - Sửa lỗi tham chiếu `ts.getMaThisinh()` thành `ts.getSoBaoDanh()`.
    - Điều chỉnh logic đăng ký và hủy đăng ký để sử dụng các phương thức thích hợp trong lớp `KyThi` và lưu dữ liệu vào XML.
- **`PhanCongGiamThiForm.java`:**
    - Cập nhật các lời gọi `database.getAll...()` thành `database.load...()`.
    - Điều chỉnh logic phân công để sử dụng các phương thức thích hợp trong lớp `KyThi` và lưu dữ liệu vào XML.
    - Triển khai chức năng hủy phân công.
- **`NhapDiemForm.java`:**
    - Cập nhật các lời gọi `database.getAll...()` thành `database.load...()`.
    - Sửa lỗi tham chiếu `ts.getMaThisinh()` thành `ts.getSoBaoDanh()`.
    - Cải thiện logic `saveDiem()` để cập nhật đúng `KetQua` và lưu `KyThi` vào XML.
- **`XemKetQuaForm.java`:**
    - Cập nhật các lời gọi `database.getAll...()` thành `database.load...()`.
    - Sửa lỗi tham chiếu `ts.getMaThisinh()` thành `ts.getSoBaoDanh()`.
    - Xóa phương thức `getXepLoai()` trùng lặp và sử dụng phương thức của lớp `KetQua`.
- **`ThongKeForm.java`:**
    - Cập nhật các lời gọi `database.getAll...()` thành `database.load...()`.
    - Xóa phương thức `getXepLoai()` trùng lặp và sử dụng phương thức của lớp `KetQua`.
- **`UserManagementForm.java`:**
    - Cập nhật các lời gọi `database.getAllUsers()`, `findUserByUsername()`, `deleteUser()`, `updateUser()` để sử dụng các phương thức mới trong `XMLDatabase`.
- **`AccountInfoForm.java`:**
    - Thay thế `ActionListener` bằng lambda.
- **`RegisterForm.java`:**
    - Thay thế `ActionListener` bằng lambda.
    - Cập nhật logic đăng ký để sử dụng `database.loadUsers()` và `database.saveUsers()` và `database.findUserByUsername()`.

### 5. Cập nhật các lớp Service và Test
- **`AuthenticationService.java`:**
    - Sử dụng `PasswordUtil` để băm và xác minh mật khẩu trong các phương thức `login()`, `changePassword()`, `register()`.
    - Xóa phương thức `getAllUsers()` không cần thiết.
- **`QuickTest.java`:**
    - Cập nhật logic đăng nhập để sử dụng mật khẩu đã băm cho người dùng `admin` khi tạo dữ liệu mẫu.
- **`TestLoginFlow.java`:**
    - Đảm bảo người dùng 'admin' tồn tại với mật khẩu đã băm trước khi cố gắng đăng nhập.
