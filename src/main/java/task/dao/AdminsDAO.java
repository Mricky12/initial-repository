package task.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import task.DBCon;
import task.dto.AdminsDTO;

public class AdminsDAO {

    // 管理者をメールアドレスとパスワードで検索
    public AdminsDTO findAdminByEmailAndPassword(String email, String password) {
        AdminsDTO admin = null;
        String query = "SELECT * FROM admins WHERE admin_email = ? AND admin_password = ?";

        try (Connection conn = DBCon.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return admin;
    }

    // 管理者を新規登録
    public boolean insertAdmin(AdminsDTO admin) {
        String query = "INSERT INTO admins (admin_name, admin_email, admin_password) VALUES (?, ?, ?)";

        try (Connection conn = DBCon.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, admin.getAdminName());
            stmt.setString(2, admin.getAdminEmail());
            stmt.setString(3, admin.getAdminPassword());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 管理者を削除 (論理削除)
    public boolean deleteAdmin(int adminId) {
        String query = "UPDATE admins SET admin_deleted_at = ? WHERE admin_id = ?";

        try (Connection conn = DBCon.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setInt(2, adminId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
