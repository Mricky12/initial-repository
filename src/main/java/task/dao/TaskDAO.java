package task.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import task.DBCon;
import task.dto.TaskDTO;

public class TaskDAO {

	private Object userId;

	// 色一覧取得
	public List<TaskDTO> getAllColors() {
		String sql = "SELECT color_id, color_name, color_code FROM colors";
		List<TaskDTO> colorList = new ArrayList<>();

		try (Connection conn = DBCon.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {

			while (rs.next()) {
				TaskDTO color = new TaskDTO();
				color.setColorId(rs.getInt("color_id"));
				color.setColorCode(rs.getString("color_code"));
				color.setTaskTitle(rs.getString("color_name"));
				colorList.add(color);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return colorList;
	}

	// タスク登録(INSERT)
	public boolean insertTask(TaskDTO task, int user_Id) {
		String sql = "INSERT INTO tasks (task_title, task, user_id, color_id) VALUES (?, ?, ?, ?)";
		try (Connection conn = DBCon.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, task.getTaskTitle());
			pstmt.setString(2, task.getTask());
			pstmt.setInt(3, user_Id);
			pstmt.setObject(4, task.getColorId(), java.sql.Types.INTEGER);

			return pstmt.executeUpdate() > 0; // 1行以上影響を受けた場合は成功
		} catch (SQLException e) {
			System.out.println("Inserting task for userId: " + userId);
			e.printStackTrace(); // エラーログを出力
			return false; // 失敗した場合はfalseを返す
		}
	}

	// タスク一覧取得 (SELECT ALL)
	public List<TaskDTO> getAllTasks() {
		String sql = "SELECT t.task_id, t.task_title, t.task, t.task_image, t.user_id, t.color_id, " +
				"c.color_code " +
				"FROM tasks t " +
				"LEFT JOIN colors c ON t.color_id = c.color_id";
		List<TaskDTO> taskList = new ArrayList<>();
		try (Connection conn = DBCon.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {

			while (rs.next()) {
				TaskDTO task = new TaskDTO();
				task.setTaskId(rs.getInt("task_id"));
				task.setTaskTitle(rs.getString("task_title"));
				task.setTask(rs.getString("task"));
				task.setTaskImage(rs.getBytes("task_image"));
				task.setUserId(rs.getInt("user_id"));
				task.setColorId(rs.getObject("color_id") != null ? rs.getInt("color_id") : null);
				task.setColorCode(rs.getString("color_code"));
				taskList.add(task);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Task List Size: " + taskList.size());
		return taskList;
	}

	// タスク更新 (UPDATE)
	public void updateTask(TaskDTO task) throws SQLException {
		if (task.getUserId() == null) {
			throw new IllegalArgumentException("ユーザーIDがnullです。");
		}

		String sql = "UPDATE tasks SET title = ?, content = ? WHERE id = ? AND user_id = ?";
		try (Connection conn = DBCon.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, task.getTaskTitle());
			stmt.setString(2, task.getTask());
			stmt.setObject(3, task.getColorId(), java.sql.Types.INTEGER);
			stmt.setInt(4, task.getTaskId());
			stmt.setInt(5, task.getUserId());

			int rowsAffected = stmt.executeUpdate();
			if (rowsAffected == 0) {
				throw new SQLException("タスクの更新に失敗しました。影響を受けた行がありません。");
			}
		}
	}

	// タスク削除 (DELETE)
	public boolean deleteTask(int taskId) {
		String sql = "DELETE FROM tasks WHERE task_id = ?";
		try (Connection conn = DBCon.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, taskId);

			int rows = pstmt.executeUpdate();
			System.out.println("Rows deleted: " + rows);
			// 削除成功の場合trueを返す
			return rows > 0;
		} catch (SQLException e) {
			System.err.println("Delete task failed: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
}
