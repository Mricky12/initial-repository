package task.dto;

import java.time.LocalDateTime;

public class UsersDTO {
	private int id; // ユーザーID
	private String name; // ユーザー名
	private String email; // メールアドレス
	private String password; // パスワード
	private LocalDateTime deletedAt; // ソフトデリート用


    
    
    //this: クラス自身を指します。フィールド名と引数名が同じ場合に区別するために使われる
    //例: this.id = id; は「クラスのフィールドidに、引数idの値を代入する」という意味

	public UsersDTO() {
	}


	public UsersDTO(int id, String name, String email, String password, LocalDateTime deletedAt) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.deletedAt = deletedAt;
	}


    // Getter and Setter
    //Getterとはフィールドの値を取得するためのメソッド
    //Setterとはフィールドの値を変更するためのメソッド
    
    
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
