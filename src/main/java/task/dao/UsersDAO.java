package task.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import task.dto.UsersDTO;

public class UsersDAO {

    // ユーザーをメールとパスワードで検索（ログイン用）
	
	//UsersDTO型のfindUserByEmailAndPasswordという名前のメソッド
	//検索条件としてメールアドレス、パスワードを設定
	//connection: データベース接続オブジェクト。
	//throws SQLException は、「このメソッドが SQLException（データベース操作中に発生する問題）をスローする可能性がある」ことを示す
    public UsersDTO findUserByEmailAndPassword(String email, String password, Connection connection) throws SQLException {
        UsersDTO user = null;
        
        //「?」はプレースホルダという。値を後でPreparedStatementで設定出来る
        //AND user_deleted_at IS NULLで削除フラグのNULLのユーザーを指定
        String query = "SELECT * FROM users WHERE user_email = ? AND user_password = ? AND user_deleted_at IS NULL";

        //try：Javaで例外処理を行うための構文の一部。エラーが発生する可能性のあるコードを監視するためのブロック       
        //今回はtry-with-resources構文を用いてリソース（connection, statement, resultSetなど）が自動的に開閉される仕様
        // PreparedStatement型のstmtを生成し、SQLクエリを設定       
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
        	//1つ目の?にemail、2つ目の?にpasswordを設定
            stmt.setString(1, email);
            stmt.setString(2, password);
            
            //クエリを実行し、結果をResultSet型のrsに取得
            try (ResultSet rs = stmt.executeQuery()) {
            	//rs.next(): 結果セットの次の行が存在するかを確認
                if (rs.next()) {
                    user = new UsersDTO(
                        rs.getInt("user_id"),
                        rs.getString("user_name"),
                        rs.getString("user_email"),
                        rs.getString("user_password"),
                        rs.getTimestamp("user_deleted_at") != null 
                            ? rs.getTimestamp("user_deleted_at").toLocalDateTime()
                            : null
                    );
                }
            }
        }
        return user;
    }

    // 新規ユーザーを登録
    public boolean insertUser(UsersDTO user, Connection connection) throws SQLException {
        String query = "INSERT INTO users (user_name, user_email, user_password) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            return stmt.executeUpdate() > 0;
        }
    }

    // ユーザーを名前とメールアドレスで検索（認証用）
    public boolean authenticateUser(String name, String email, Connection connection) throws SQLException {
        String query = "SELECT COUNT(*) FROM users WHERE user_name = ? AND user_email = ? AND user_deleted_at IS NULL";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // ユーザーが存在する場合trueを返す
                }
            }
        }
        return false;
    }

 // ユーザー名とメールアドレスを基にユーザー情報を取得
    public UsersDTO findUserByEmailAndName(String name, String email, Connection connection) throws SQLException {
        UsersDTO user = null;
        String query = "SELECT * FROM users WHERE user_name = ? AND user_email = ? AND user_deleted_at IS NULL";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    user = new UsersDTO(
                        rs.getInt("user_id"),
                        rs.getString("user_name"),
                        rs.getString("user_email"),
                        rs.getString("user_password"),
                        rs.getTimestamp("user_deleted_at") != null 
                            ? rs.getTimestamp("user_deleted_at").toLocalDateTime()
                            : null
                    );
                }
            }
        }
        return user;
    }
    
    
    
 // ユーザーIDを使ってパスワードを更新する
    public boolean updatePasswordById(int userId, String newPassword, Connection connection) throws SQLException {
        String query = "UPDATE users SET user_password = ? WHERE user_id = ? AND user_deleted_at IS NULL";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, newPassword);
            stmt.setInt(2, userId);
            return stmt.executeUpdate() > 0;
        }
        
    }
}
