package task.dto;

import java.time.LocalDateTime;

public class UsersDTO {
<<<<<<< HEAD
	private int id; // ユーザーID
	private String name; // ユーザー名
	private String email; // メールアドレス
	private String password; // パスワード
	private String deletedAt; // ソフトデリート用
=======
    private int id; // ユーザーID
    private String name; // ユーザー名
    private String email; // メールアドレス
    private String password; // パスワード
    private LocalDateTime deletedAt; // ソフトデリート用
>>>>>>> 25ba755156d2aedab40ebf97aa6edd8ae3856fe9

	public UsersDTO() {
	}

<<<<<<< HEAD
	public UsersDTO(int id, String name, String email, String password, String deletedAt) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.deletedAt = deletedAt;
	}
=======
    public UsersDTO(int id, String name, String email, String password, LocalDateTime deletedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.deletedAt = deletedAt;
    }
>>>>>>> 25ba755156d2aedab40ebf97aa6edd8ae3856fe9

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

<<<<<<< HEAD
	public String getDeletedAt() {
		return deletedAt;
	}

	public void setDeletedAt(String deletedAt) {
		this.deletedAt = deletedAt;
	}
}
=======
    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }
}
>>>>>>> 25ba755156d2aedab40ebf97aa6edd8ae3856fe9
