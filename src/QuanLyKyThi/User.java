package QuanLyKyThi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDateTime;

/**
 * Class đại diện cho người dùng trong hệ thống
 */
@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
public class User {
    private String username;
    private String password;
    private String hoTen;
    private String email;
    private String role; // "admin", "giaovu", "user"
    @XmlJavaTypeAdapter(DateAdapters.LocalDateTimeAdapter.class)
    private LocalDateTime lastLogin;
    private boolean isActive;
    private boolean isPending; // Tài khoản chờ duyệt (chỉ dành cho admin role)
    @XmlJavaTypeAdapter(DateAdapters.LocalDateTimeAdapter.class)
    private LocalDateTime createdDate;

    public User() {}

    public User(String username, String password, String hoTen, String email, String role) {
        this.username = username;
        this.password = password;
        this.hoTen = hoTen;
        this.email = email;
        this.role = role;
        this.isActive = true;
        this.isPending = "admin".equals(role); // Admin role cần duyệt
        this.lastLogin = null;
        this.createdDate = LocalDateTime.now();
    }

    // Getter và Setter methods
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public LocalDateTime getLastLogin() { return lastLogin; }
    public void setLastLogin(LocalDateTime lastLogin) { this.lastLogin = lastLogin; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    public boolean isPending() { return isPending; }
    public void setPending(boolean pending) { isPending = pending; }

    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }

    // Convenience method
    public String getFullName() {
        return hoTen;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", hoTen='" + hoTen + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
