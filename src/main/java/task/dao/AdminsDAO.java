package task.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import task.DBCon;
import task.dto.AdminsDTO;


public class AdminsDAO {
    public AdminsDTO findAdminByEmailAndPassword(String email, String password) {
        AdminsDTO admin = null;
        String query = "SELECT * FROM admins WHERE email = ? AND password = ?";

        try (Connection conn = DBCon.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                admin = new AdminsDTO(rs.getInt("id"), rs.getString("email"), rs.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return admin;
    }
}
