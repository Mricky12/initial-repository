package task.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import task.dto.GroupsDTO;
import task.dto.UsersDTO;

public class GroupsDAO {
	private Connection conn;

    // コンストラクタで DB 接続を受け取る形式
    public GroupsDAO(Connection conn) {
        this.conn = conn;
    }

    // グループの新規登録
    public boolean insertGroup(GroupsDTO group) throws SQLException{
        String query = "INSERT INTO groups (group_name) VALUES (?)";
        boolean isInserted = false;

        PreparedStatement stmt = null;
        ResultSet generatedKeys = null;

        try {
            stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, group.getGroupName());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    group.setGroupId(generatedKeys.getInt(1));
                    isInserted = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // リソースの解放
            try {
                if (generatedKeys != null) generatedKeys.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return isInserted;
    }

    // グループデータをすべて取得
    public List<GroupsDTO> getAll() throws SQLException{
        List<GroupsDTO> groups = new ArrayList<>();
        String sql = "SELECT group_id, group_name FROM groups";

        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                GroupsDTO group = new GroupsDTO();
                group.setGroupId(rs.getInt("group_id"));
                
             // デバッグ出力
				/*System.out.println("取得したグループ: " + group.getGroupId() + ", " + group.getGroupName());*/
                group.setGroupName(rs.getString("group_name"));
                groups.add(group);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // リソースの解放
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return groups;
    }
    
    // グループ名の更新
    public boolean updateGroupName(int groupId, String newName) {
        String sql = "UPDATE groups SET group_name = ? WHERE group_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newName);
            stmt.setInt(2, groupId);

            int updatedRows = stmt.executeUpdate();
            return updatedRows > 0; // 更新が成功した場合に true を返す
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // 更新に失敗した場合は false を返す
        }
    }
    
 // グループの削除
    public boolean deleteGroup(int groupId) {
        String sql = "DELETE FROM groups WHERE group_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, groupId);

            int deletedRows = stmt.executeUpdate();
            return deletedRows > 0; // 削除が成功した場合に true を返す
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // 削除に失敗した場合は false を返す
        }
    }
    
    
    public UsersDTO findUserByNameOrEmail(String identifier) throws SQLException {
        String query = "SELECT * FROM users WHERE username = ? OR email = ?";
        try (Connection conn = getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, identifier);
            stmt.setString(2, identifier);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    UsersDTO user = new UsersDTO();
                    user.setId(rs.getInt("user_id"));
                    user.setName(rs.getString("username"));
                    user.setEmail(rs.getString("email"));
                    return user;
                }
            }
        }
        return null;
    }

    public boolean addUserToGroup(int groupId, int userId) throws SQLException {
        String query = "INSERT INTO users_groups (group_id, user_id) VALUES (?, ?)";
        try (Connection conn = getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, groupId);
            stmt.setInt(2, userId);
            return stmt.executeUpdate() > 0;
        }

    }
    
    public Connection getConnection() {
        return this.conn;
    }

	public boolean removeAllMembersFromGroup(int int1) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}
    
}

/*public class GroupsDAO {
    private Connection conn;

    // コンストラクタで DB 接続を受け取る形式
    public GroupsDAO(Connection conn) {
        this.conn = conn;
    }

    // グループの新規登録
    public boolean insertGroup(GroupsDTO group) throws SQLException {
        String query = "INSERT INTO groups (group_name) VALUES (?)";
        boolean isInserted = false;

        try (PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, group.getGroupName());
            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        group.setGroupId(generatedKeys.getInt(1));
                        isInserted = true;
                    }
                }
            }
        }
        return isInserted;
    }

    // グループデータをすべて取得
    public List<GroupsDTO> getAll() throws SQLException {
        List<GroupsDTO> groups = new ArrayList<>();
        String sql = "SELECT group_id, group_name FROM groups";

        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                GroupsDTO group = new GroupsDTO();
                group.setGroupId(rs.getInt("group_id"));
                group.setGroupName(rs.getString("group_name"));
                groups.add(group);
            }
        }
        return groups;
    }

    // グループ名の更新
    public boolean updateGroupName(int groupId, String newName) throws SQLException {
        String sql = "UPDATE groups SET group_name = ? WHERE group_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newName);
            stmt.setInt(2, groupId);
            return stmt.executeUpdate() > 0;
        }
    }

    // グループの削除
    public boolean deleteGroup(int groupId) throws SQLException {
        String sql = "DELETE FROM groups WHERE group_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, groupId);
            return stmt.executeUpdate() > 0;
        }
    }
    
	ここからユーザーの操作
    // ユーザーに紐付くグループ情報を取得
    public List<GroupsDTO> getGroupsByUserId(int userId) throws SQLException {
        String sql = "SELECT g.group_id, g.group_name " +
                     "FROM groups g " +
                     "JOIN users_groups ug ON g.group_id = ug.group_id " +
                     "WHERE ug.user_id = ?";
        List<GroupsDTO> groups = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    GroupsDTO group = new GroupsDTO();
                    group.setGroupId(rs.getInt("group_id"));
                    group.setGroupName(rs.getString("group_name"));
                    groups.add(group);
                }
            }
        }
        return groups;
    }

    // ユーザーをグループに追加
    public boolean addUserToGroup(int userId, int groupId) throws SQLException {
        String sql = "INSERT INTO users_groups (user_id, group_id) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, groupId);
            return stmt.executeUpdate() > 0;
        }
    }

    // ユーザーをグループから削除
    public boolean removeUserFromGroup(int userId, int groupId) throws SQLException {
        String sql = "DELETE FROM users_groups WHERE user_id = ? AND group_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, groupId);
            return stmt.executeUpdate() > 0;
        }
    }

    // グループ名で検索
    public List<GroupsDTO> searchGroupsByName(String keyword) throws SQLException {
        String sql = "SELECT group_id, group_name FROM groups WHERE group_name LIKE ?";
        List<GroupsDTO> groups = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + keyword + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    GroupsDTO group = new GroupsDTO();
                    group.setGroupId(rs.getInt("group_id"));
                    group.setGroupName(rs.getString("group_name"));
                    groups.add(group);
                }
            }
        }
        return groups;
    }

 // 特定のグループに所属するユーザー情報を取得
    public List<UsersDTO> getUsersByGroupId(int groupId, Connection connection) throws SQLException {
        List<UsersDTO> users = new ArrayList<>();
        String query = "SELECT u.user_id, u.user_name, u.user_email, u.user_password, u.user_deleted_at " +
                       "FROM users u " +
                       "JOIN user_groups ug ON u.user_id = ug.user_id " +
                       "WHERE ug.group_id = ? AND u.user_deleted_at IS NULL";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, groupId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    UsersDTO user = new UsersDTO(
                        rs.getInt("user_id"),
                        rs.getString("user_name"),
                        rs.getString("user_email"),
                        rs.getString("user_password"),
                        rs.getTimestamp("user_deleted_at") != null 
                            ? rs.getTimestamp("user_deleted_at").toLocalDateTime()
                            : null
                    );
                    users.add(user);
                }
            }
        }
        return users;
    }
    

    // 名前またはメールアドレスでユーザーを検索
    public List<UsersDTO> searchUsers(String keyword) throws SQLException {
        List<UsersDTO> usersList = new ArrayList<>();
        String query = "SELECT * FROM users WHERE (user_name LIKE ? OR user_email LIKE ?) AND user_deleted_at IS NULL";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            String searchPattern = "%" + keyword + "%"; // 部分一致検索
            stmt.setString(1, searchPattern);
            stmt.setString(2, searchPattern);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    UsersDTO user = new UsersDTO(
                        rs.getInt("user_id"),
                        rs.getString("user_name"),
                        rs.getString("user_email"),
                        rs.getString("user_password"),
                        rs.getTimestamp("user_deleted_at") != null 
                            ? rs.getTimestamp("user_deleted_at").toLocalDateTime()
                            : null
                    );
                    usersList.add(user);
                }
            }
        }

        return usersList;
    }


    // DB コネクションを取得
    public Connection getConnection() {
        return this.conn;
    }
}*/