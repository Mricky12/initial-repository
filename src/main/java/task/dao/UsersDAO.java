package task.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import task.dto.UsersDTO;

public class UsersDAO {

    // ユーザーをメールとパスワードで検索（ログイン用）
    public UsersDTO findUserByEmailAndPassword(String email, String password, Connection connection) throws SQLException {
        UsersDTO user = null;
        String query = "SELECT * FROM users WHERE user_email = ? AND user_password = ? AND user_deleted_at IS NULL";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    user = new UsersDTO(
                        rs.getInt("user_id"),
                        rs.getString("user_name"),
                        rs.getString("user_email"),
                        rs.getString("user_password"),
                        rs.getString("user_deleted_at")
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

    // メールアドレスが登録済みかチェック
    public boolean isEmailRegistered(String email, Connection connection) throws SQLException {
        String query = "SELECT COUNT(*) FROM users WHERE user_email = ? AND user_deleted_at IS NULL";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }
}
