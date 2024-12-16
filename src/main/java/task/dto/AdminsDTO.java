package task.dto;

import java.time.LocalDateTime;

public class AdminsDTO {
    private int adminId; // 管理者ID
    private String adminName; // 管理者名
    private String adminEmail; // メールアドレス
    private String adminPassword; // パスワード
    private LocalDateTime adminDeletedAt; // 削除日時

    public AdminsDTO() {}

    public AdminsDTO(int adminId, String adminName, String adminEmail, String adminPassword, LocalDateTime adminDeletedAt) {
        this.adminId = adminId;
        this.adminName = adminName;
        this.adminEmail = adminEmail;
        this.adminPassword = adminPassword;
        this.adminDeletedAt = adminDeletedAt;
    }

    // Getter and Setter
    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public LocalDateTime getAdminDeletedAt() {
        return adminDeletedAt;
    }

    public void setAdminDeletedAt(LocalDateTime adminDeletedAt) {
        this.adminDeletedAt = adminDeletedAt;
    }
}
