/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package QuanLyKyThi;

/**
 *
 * @author ad
 */
public class GiamThi {
    private String maGiamThi;
    private String HoTen;
    private String DonVi;
    private String SDT;

    public GiamThi(String maGiamThi, String HoTen, String DonVi, String SDT) {
        this.maGiamThi = maGiamThi;
        this.HoTen = HoTen;
        this.DonVi = DonVi;
        this.SDT = SDT;
    }

    public String getMaGiamThi() {
        return maGiamThi;
    }

    public String getHoTen() {
        return HoTen;
    }

    public String getDonVi() {
        return DonVi;
    }

    public String getSDT() {
        return SDT;
    }
    
    public boolean phanCong(KyThi kythi){
        return kythi.themGiamThi(this);
    }
    
    
}
