package QuanLyKyThi;

import javax.swing.*;
import java.util.List;

// Các form stub để tránh lỗi biên dịch
class ThiSinhListForm extends JInternalFrame {
    public ThiSinhListForm(List<ThiSinh> list) {
        setSize(600, 400);
        add(new JLabel("Form Danh Sách Thí Sinh - Đang phát triển..."));
    }
}

class AddThiSinhForm extends JInternalFrame {
    public AddThiSinhForm(List<ThiSinh> list) {
        setSize(500, 400);
        add(new JLabel("Form Thêm Thí Sinh - Đang phát triển..."));
    }
}

class DangKyThiForm extends JInternalFrame {
    public DangKyThiForm(List<ThiSinh> thiSinhs, List<KyThi> kyThis) {
        setSize(500, 400);
        add(new JLabel("Form Đăng Ký Thi - Đang phát triển..."));
    }
}

class GiamThiListForm extends JInternalFrame {
    public GiamThiListForm(List<GiamThi> list) {
        setSize(600, 400);
        add(new JLabel("Form Danh Sách Giám Thị - Đang phát triển..."));
    }
}

class AddGiamThiForm extends JInternalFrame {
    public AddGiamThiForm(List<GiamThi> list) {
        setSize(500, 400);
        add(new JLabel("Form Thêm Giám Thị - Đang phát triển..."));
    }
}

class PhanCongGiamThiForm extends JInternalFrame {
    public PhanCongGiamThiForm(List<GiamThi> giamThis, List<KyThi> kyThis) {
        setSize(500, 400);
        add(new JLabel("Form Phân Công Giám Thị - Đang phát triển..."));
    }
}

class NhapDiemForm extends JInternalFrame {
    public NhapDiemForm(List<KyThi> list) {
        setSize(500, 400);
        add(new JLabel("Form Nhập Điểm - Đang phát triển..."));
    }
}

class XemKetQuaForm extends JInternalFrame {
    public XemKetQuaForm(List<KyThi> kyThis, List<ThiSinh> thiSinhs) {
        setSize(600, 400);
        add(new JLabel("Form Xem Kết Quả - Đang phát triển..."));
    }
}

class ThongKeForm extends JInternalFrame {
    public ThongKeForm(List<KyThi> list) {
        setSize(600, 400);
        add(new JLabel("Form Thống Kê - Đang phát triển..."));
    }
}
