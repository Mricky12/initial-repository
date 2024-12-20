package task.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import task.dto.GroupsDTO;

public class GroupsDAO {
	private Connection conn;

    // コンストラクタで DB 接続を受け取る形式
    public GroupsDAO(Connection conn) {
        this.conn = conn;
    }

    // グループの新規登録
    public boolean insertGroup(GroupsDTO group) {
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
    public List<GroupsDTO> getAll() {
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
    
    public Connection getConnection() {
        return this.conn;
    }
    
    


    }