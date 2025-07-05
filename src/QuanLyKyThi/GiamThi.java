/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package QuanLyKyThi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ad
 */
@XmlRootElement(name = "giamthi")
@XmlAccessorType(XmlAccessType.FIELD)
public class GiamThi {
    private String maGiamThi;
    private String HoTen;
    private String DonVi;
    private String SDT;
    private String email; // Thêm email để liên kết với User
    private String username; // Thêm username để liên kết với User account

    public GiamThi(String maGiamThi, String HoTen, String DonVi, String SDT) {
        this.maGiamThi = maGiamThi;
        this.HoTen = HoTen;
        this.DonVi = DonVi;
        this.SDT = SDT;
        this.email = "";
        this.username = "";
    }

    // Constructor với email và username
    public GiamThi(String maGiamThi, String HoTen, String DonVi, String SDT, String email, String username) {
        this.maGiamThi = maGiamThi;
        this.HoTen = HoTen;
        this.DonVi = DonVi;
        this.SDT = SDT;
        this.email = email;
        this.username = username;
    }

    // Getters
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

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    // Setters
    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setMaGiamThi(String maGiamThi) {
        this.maGiamThi = maGiamThi;
    }

    public void setHoTen(String HoTen) {
        this.HoTen = HoTen;
    }

    public void setDonVi(String DonVi) {
        this.DonVi = DonVi;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }
    
    public boolean phanCong(KyThi kythi) throws Exceptions.KyThiValidationException, Exceptions.DuplicateException {
        return kythi.themGiamThi(this);
    }
    
    @Override
    public String toString() {
        return String.format("GiamThi{maGiamThi='%s', HoTen='%s', DonVi='%s', email='%s'}", 
                           maGiamThi, HoTen, DonVi, email);
    }
}
