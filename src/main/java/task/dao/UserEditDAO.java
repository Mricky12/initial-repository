package task.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import task.dto.UserEditDTO;

public class UserEditDAO {
	public List<UserEditDTO> searchUsers(
			String userId, String name, String email, Connection connection)
			throws Exception {
		List<UserEditDTO> users = new ArrayList<>();
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
				UserEditDTO user = new UserEditDTO();
				user.setId(rs.getInt("user_id"));
				user.setName(rs.getString("user_name"));
				user.setEmail(rs.getString("user_email"));
				users.add(user);
			}
		}
		return users;
	}

	public boolean updateUser(UserEditDTO loggedInUser, Connection connection) throws SQLException {
		String query = "UPDATE users SET user_name = ?, user_email = ?, user_password = ? WHERE user_id = ? AND user_deleted_at IS NULL";

		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setString(1, loggedInUser.getName());
			stmt.setString(2, loggedInUser.getEmail());
			stmt.setString(3, loggedInUser.getPassword());
			stmt.setInt(4, loggedInUser.getId());
			return stmt.executeUpdate() > 0;
		}
	}

	public void deleteUser(int userId, Connection connection) throws Exception {
		String sql = "UPDATE users SET user_deleted_at = NOW() WHERE user_id = ?";

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, userId);
			ps.executeUpdate();
		}
	}
}
