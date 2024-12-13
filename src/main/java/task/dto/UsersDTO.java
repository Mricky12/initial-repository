package task.dto;

import java.time.LocalDateTime;

public class UsersDTO {
    private int id; // ユーザーID
    private String name; // ユーザー名
    private String email; // メールアドレス
    private String password; // パスワード
    private LocalDateTime deletedAt; // ソフトデリート用

    public UsersDTO() {}

    public UsersDTO(int id, String name, String email, String password, LocalDateTime deletedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.deletedAt = deletedAt;
    }

    // Getter and Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }
}