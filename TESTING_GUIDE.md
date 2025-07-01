# 🧪 TESTING GUIDE - Hệ thống Quản lý Trạng thái Bài thi

## 📋 Mục tiêu Testing
Kiểm tra logic phân quyền và quy trình trạng thái bài thi hoạt động chính xác theo yêu cầu.

## 🚀 Các cách chạy test

### 1. Test Form Quản lý Trạng thái Bài thi
```bash
# Chạy demo với dialog chọn role
cd "c:\Users\ad\Documents\NetBeansProjects\QuanLyKyThi_1"
java -cp src QuanLyKyThi.DemoTrangThaiBaiThi
```

### 2. Test Main Application với Authentication
```bash
# Chạy ứng dụng chính
java -cp src QuanLyKyThi.LoginForm
```

### 3. Test XML Database
```bash
# Demo tạo file XML và dữ liệu mẫu
java -cp src QuanLyKyThi.XMLDemo
```

## 🔍 Test Cases cần kiểm tra

### A. Phân quyền theo Role

#### Test Admin (admin/admin123):
- ✅ Bắt đầu thi
- ✅ Nộp bài
- ✅ Bắt đầu chấm
- ✅ Nhập điểm
- ✅ Cập nhật điểm (tất cả bài thi)

#### Test Giáo vụ (giaovu/gv123):
- ❌ Bắt đầu thi (không được phép)
- ❌ Nộp bài (không được phép)
- ✅ Bắt đầu chấm
- ✅ Nhập điểm
- ✅ Cập nhật điểm

#### Test Giám thị (giamthi/gt123):
- ✅ Bắt đầu thi
- ✅ Nộp bài  
- ✅ Bắt đầu chấm (chỉ kỳ thi được phân công)
- ✅ Nhập điểm (chỉ kỳ thi được phân công)
- ❌ Cập nhật điểm (chỉ bài mình chấm)

### B. Quy trình Trạng thái Bài thi

#### Test Flow chính:
1. **CHƯA THI** → Click "Bắt đầu thi" → **ĐANG THI**
2. **ĐANG THI** → Click "Nộp bài" → **ĐÃ NỘP BÀI** (tự động → **CHƯA CHẤM**)
3. **CHƯA CHẤM** → Click "Bắt đầu chấm" → **ĐANG CHẤM**
4. **ĐANG CHẤM** → Nhập điểm + Click "Nhập điểm" → **ĐÃ CHẤM**
5. **ĐÃ CHẤM** → Click "Cập nhật điểm" → Vẫn **ĐÃ CHẤM**

#### Test Invalid Transitions:
- Không thể bắt đầu chấm khi chưa nộp bài
- Không thể nhập điểm khi chưa bắt đầu chấm
- Giám thị không thể chấm kỳ thi không được phân công

### C. Validation Logic

#### Test Input Validation:
- Điểm phải từ 0-10
- Ghi chú có thể để trống
- Thông báo lỗi hiển thị đúng

#### Test Database Integration:
- Trạng thái được lưu vào XML
- Reload form vẫn giữ đúng trạng thái
- Thông tin người chấm được lưu chính xác

## 📊 Expected Results

### Giao diện theo Role:
- **Admin**: Tất cả button được enable
- **Giáo vụ**: Không có button "Bắt đầu thi", "Nộp bài"
- **Giám thị**: Không có button "Cập nhật điểm"

### Dữ liệu XML:
- File `ketqua.xml` chứa đầy đủ trạng thái
- Thông tin người chấm chính xác
- Timestamp được ghi nhận

### Thông báo hệ thống:
- Success: "Bắt đầu thi thành công!"
- Error: "Không thể nhập điểm. Kiểm tra quyền và trạng thái!"
- Validation: "Điểm phải là số từ 0 đến 10!"

## 🐛 Known Issues & Workarounds

### Issue 1: Giám thị không được phân công
**Mô tả**: Logic kiểm tra giám thị chưa hoàn hảo
**Workaround**: Tạm thời return true trong `kiemTraGiamThiDuocPhanCong()`

### Issue 2: Unicode trong console
**Mô tả**: Ký tự tiếng Việt hiển thị không đúng trong terminal
**Workaround**: Chỉ ảnh hưởng display, logic hoạt động bình thường

### Issue 3: Look and Feel
**Mô tả**: Một số lỗi nhỏ về giao diện
**Workaround**: Sử dụng SystemLookAndFeel default

## 📈 Performance Expectations

- **Startup time**: < 3 giây
- **XML load/save**: < 1 giây với 1000 records
- **UI response**: Instant cho tất cả thao tác
- **Memory usage**: < 100MB RAM

## 🎯 Testing Checklist

- [ ] Compile thành công không có lỗi
- [ ] Login form hoạt động với tất cả role
- [ ] Phân quyền button chính xác theo role
- [ ] Transition trạng thái theo đúng flow
- [ ] Validation input điểm (0-10)
- [ ] Lưu/tải dữ liệu XML thành công
- [ ] Thông báo lỗi/thành công hiển thị đúng
- [ ] Giao diện responsive và user-friendly

## 🏁 Test Completion Criteria

**PASS nếu:**
✅ Tất cả test cases hoạt động theo expected results
✅ Không có exception/crash trong quá trình sử dụng
✅ Dữ liệu được lưu trữ persistent trong XML
✅ Phân quyền logic chính xác 100%

**FAIL nếu:**
❌ Có thể thực hiện thao tác không được phép theo role
❌ Transition trạng thái sai logic
❌ Dữ liệu bị mất sau khi restart
❌ Exception không được handle properly
