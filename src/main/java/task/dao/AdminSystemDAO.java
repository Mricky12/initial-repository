package task.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import task.dto.AdminSystemDTO;
import task.dto.AdminsDTO;

public class AdminSystemDAO {
    public List<AdminSystemDTO> searchUsers(String userId, String name, String email, Connection connection) throws Exception {
        List<AdminSystemDTO> users = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE user_deleted_at IS NULL"; // 削除されていないユーザーのみ取得

        if (userId != null && !userId.isEmpty()) {
            sql += " AND user_id LIKE ?";
        }
        if (name != null && !name.isEmpty()) {
            sql += " AND user_name LIKE ?";
        }
        if (email != null && !email.isEmpty()) {
            sql += " AND user_email LIKE ?";
        }

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            int index = 1;

            if (userId != null && !userId.isEmpty()) {
                ps.setString(index++, "%" + userId + "%");
            }
            if (name != null && !name.isEmpty()) {
                ps.setString(index++, "%" + name + "%");
            }
            if (email != null && !email.isEmpty()) {
                ps.setString(index++, "%" + email + "%");
            }

            ResultSet rs = ps.executeQuery();
            
           
            
            while (rs.next()) {
                AdminSystemDTO user = new AdminSystemDTO();
                user.setUserId(rs.getString("user_id"));
                user.setName(rs.getString("user_name"));
                user.setEmail(rs.getString("user_email"));
                users.add(user);
                
                
            }
        }
        

        return users;
    }
    
    public void deleteUser(String userId, Connection connection) throws Exception {
        String sql = "UPDATE users SET user_deleted_at = NOW() WHERE user_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, userId);
            ps.executeUpdate();
        }
    }

    public void updateAdmin(AdminsDTO admin, Connection connection) throws Exception {
        StringBuilder updateSql = new StringBuilder("UPDATE admins SET ");
        boolean hasUpdate = false;

        if (admin.getAdminName() != null && !admin.getAdminName().trim().isEmpty()) {
            updateSql.append("admin_name = ?, ");
            hasUpdate = true;
        }
        if (admin.getAdminEmail() != null && !admin.getAdminEmail().trim().isEmpty()) {
            updateSql.append("admin_email = ?, ");
            hasUpdate = true;
        }
        if (admin.getAdminPassword() != null && !admin.getAdminPassword().trim().isEmpty()) {
        	updateSql.append("admin_password = ?, ");
            hasUpdate = true;
        }

        if (!hasUpdate) {
            throw new IllegalArgumentException("更新するデータがありません。");
        }

        updateSql.setLength(updateSql.length() - 2); // 最後のカンマを削除
        updateSql.append(" WHERE admin_id = ?");

        try (PreparedStatement ps = connection.prepareStatement(updateSql.toString())) {
            int index = 1;
            if (admin.getAdminName() != null && !admin.getAdminName().trim().isEmpty()) {
                ps.setString(index++, admin.getAdminName());
            }
            if (admin.getAdminEmail() != null && !admin.getAdminEmail().trim().isEmpty()) {
                ps.setString(index++, admin.getAdminEmail());
            }
            if (admin.getAdminPassword() != null && !admin.getAdminPassword().trim().isEmpty()) {
                ps.setString(index++, admin.getAdminPassword());
            }
            ps.setInt(index, admin.getAdminId());
            ps.executeUpdate();
            
            }
}



    public void deleteAdmin(int adminId, Connection connection) throws Exception {
        String sql = "UPDATE admins SET admin_deleted_at = NOW() WHERE admin_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, adminId);
            ps.executeUpdate();
        }
    }
}









