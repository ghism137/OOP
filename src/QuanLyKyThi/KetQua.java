package QuanLyKyThi;

public class KetQua {
    private ThiSinh thiSinh;
    private KyThi kyThi;
    private double diem;

    public KetQua(ThiSinh thiSinh, KyThi kyThi, double diem) {
        this.thiSinh = thiSinh;
        this.kyThi = kyThi;
        this.diem = diem;
    }

    public ThiSinh getThiSinh() {
        return thiSinh;
    }

    public KyThi getKyThi() {
        return kyThi;
    }

    public double getDiem() {
        return diem;
    }

    public void capNhatKetQua(double diem) {
        this.diem = diem;
    }
}
