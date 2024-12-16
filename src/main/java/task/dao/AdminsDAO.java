package task.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import task.dto.AdminsDTO;

public class AdminsDAO {

    // 管理者をメールとパスワードで検索（ログイン用）
    public AdminsDTO findAdminByEmailAndPassword(String email, String password, Connection connection) throws SQLException {
        AdminsDTO admin = null;
        String query = "SELECT * FROM admins WHERE admin_email = ? AND admin_password = ? AND admin_deleted_at IS NULL";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    admin = new AdminsDTO(
                        rs.getInt("admin_id"),
                        rs.getString("admin_name"),
                        rs.getString("admin_email"),
                        rs.getString("admin_password"),
                        rs.getTimestamp("admin_deleted_at") != null 
                            ? rs.getTimestamp("admin_deleted_at").toLocalDateTime()
                            : null
                    );
                }
            }
        }
        return admin;
    }

    // 新規管理者を登録
    public boolean insertAdmin(AdminsDTO admin, Connection connection) throws SQLException {
        String query = "INSERT INTO admins (admin_name, admin_email, admin_password) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, admin.getAdminName());
            stmt.setString(2, admin.getAdminEmail());
            stmt.setString(3, admin.getAdminPassword());
            return stmt.executeUpdate() > 0;
        }
    }

    // 管理者を名前とメールアドレスで検索（認証用）
    public boolean authenticateAdmin(String name, String email, Connection connection) throws SQLException {
        String query = "SELECT COUNT(*) FROM admins WHERE admin_name = ? AND admin_email = ? AND admin_deleted_at IS NULL";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // 管理者が存在する場合trueを返す
                }
            }
        }
        return false;
    }

    // 管理者名とメールアドレスを基に管理者情報を取得
    public AdminsDTO findAdminByEmailAndName(String name, String email, Connection connection) throws SQLException {
        AdminsDTO admin = null;
        String query = "SELECT * FROM admins WHERE admin_name = ? AND admin_email = ? AND admin_deleted_at IS NULL";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    admin = new AdminsDTO(
                        rs.getInt("admin_id"),
                        rs.getString("admin_name"),
                        rs.getString("admin_email"),
                        rs.getString("admin_password"),
                        rs.getTimestamp("admin_deleted_at") != null 
                            ? rs.getTimestamp("admin_deleted_at").toLocalDateTime()
                            : null
                    );
                }
            }
        }
        return admin;
    }

    // 管理者IDを使ってパスワードを更新する
    public boolean updatePasswordById(int adminId, String newPassword, Connection connection) throws SQLException {
        String query = "UPDATE admins SET admin_password = ? WHERE admin_id = ? AND admin_deleted_at IS NULL";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, newPassword);
            stmt.setInt(2, adminId);
            return stmt.executeUpdate() > 0;
        }
    }
}
