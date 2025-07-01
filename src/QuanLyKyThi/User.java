package QuanLyKyThi;

import java.time.LocalDateTime;

/**
 * Class đại diện cho người dùng trong hệ thống
 */
public class User {
    private String username;
    private String password;
    private String hoTen;
    private String email;
    private String role; // "admin", "giaovu", "user"
    private LocalDateTime lastLogin;
    private boolean isActive;

    public User() {}

    public User(String username, String password, String hoTen, String email, String role) {
        this.username = username;
        this.password = password;
        this.hoTen = hoTen;
        this.email = email;
        this.role = role;
        this.isActive = true;
        this.lastLogin = null;
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
