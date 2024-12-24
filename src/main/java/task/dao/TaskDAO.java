package task.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import task.DBCon;
import task.dto.TaskDTO;

public class TaskDAO {

	//色一覧取得
	public List<TaskDTO> getAllColors() {
		String sql = "SELECT color_id, color_name, color_code FROM colors";
		List<TaskDTO> colorList = new ArrayList<>();

		try (Connection conn = DBCon.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				TaskDTO color = new TaskDTO();
				color.setColorId(rs.getInt("color_id"));
				color.setColorCode(rs.getString("color_code"));
				color.setTaskTitle(rs.getString("color_name")); // 仮にcolor_nameをtaskTitleに流用
				colorList.add(color);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return colorList;
	}

	//タスク登録(INSERT)
	public boolean insertTask(TaskDTO task) {
		String sql = "INSERT INTO tasks (task_title, task, task_image, user_id, color_id, trash) VALUES (?, ?, ?, ?, ?, ?)";

		try (Connection conn = DBCon.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			// 必須フィールドの設定
			pstmt.setString(1, task.getTaskTitle());
			pstmt.setString(2, task.getTask());

			// 画像データの設定
			if (task.getTaskImage() != null) {
				pstmt.setBytes(3, task.getTaskImage());
			} else {
				pstmt.setNull(3, Types.BLOB);
			}

			// ユーザーIDの設定 (仮に固定値または取得ロジックが必要)
			if (task.getUserId() != null) {
				pstmt.setInt(4, task.getUserId());
			} else {
				throw new IllegalArgumentException("ユーザーIDが設定されていません。");
			}

			// カラーIDの設定
			if (task.getColorId() != null) {
				pstmt.setInt(5, task.getColorId());
			} else {
				pstmt.setNull(5, Types.INTEGER);
			}

			// ゴミ箱フラグの設定（デフォルト false）
			pstmt.setBoolean(6, false);

			// SQL 実行
			int rows = pstmt.executeUpdate();
			System.out.println("TaskDAO: insertTask - Rows Affected: " + rows);
			return rows > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	//タスク取得(SELECT BY ID)
	public TaskDTO getTaskById(int taskId) {
		String sql = "SELECT * FROM tasks WHERE task_id= ?";
		try (Connection conn = DBCon.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, taskId);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				TaskDTO task = new TaskDTO();
				task.setTaskId(rs.getInt("task_id"));
				task.setTaskTitle(rs.getString("task_title"));
				task.setTask(rs.getString("task"));
				task.setTaskImage(rs.getBytes("task_image"));
				task.setUserId(rs.getInt("user_id"));
				task.setColorId(rs.getObject("color_id") != null ? rs.getInt("color_id") : null);
				task.setTrash(rs.getBoolean("trash"));
				return task;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//データが見つからない場合
		return null;
	}

	//	タスク一覧取得 (SELECT ALL)
	public List<TaskDTO> getAllTasks() {
		String sql = "SELECT t.task_id, t.task_title, t.task, t.task_image, t.user_id, t.color_id, " +
				"c.color_code " +
				"FROM tasks t " +
				"LEFT JOIN colors c ON t.color_id = c.color_id";

		List<TaskDTO> taskList = new ArrayList<>();

		try (Connection conn = DBCon.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

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
	public boolean updateTask(TaskDTO task) {
		String sql = "UPDATE tasks SET task_title = ?, task = ?, color_id = ? WHERE task_id = ?";
		try (Connection conn = DBCon.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, task.getTaskTitle());
			pstmt.setString(2, task.getTask());

			if (task.getColorId() != null) {
				pstmt.setInt(3, task.getColorId());
			} else {
				pstmt.setNull(3, Types.INTEGER);
			}

			pstmt.setInt(4, task.getTaskId());

			int rowsAffected = pstmt.executeUpdate();
			return rowsAffected > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	//	タスク削除 (DELETE)
	public boolean deleteTask(int taskId) {
		String sql = "DELETE FROM tasks WHERE task_id = ?";
		try (Connection conn = DBCon.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, taskId);
			int rows = pstmt.executeUpdate();
			//	削除成功の場合trueを返す
			return rows > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	// タスク検索 (SEARCH BY KEYWORD)
	public List<TaskDTO> searchTasks(String keyword) {
		List<TaskDTO> taskList = new ArrayList<>();
		String sql = "SELECT * FROM tasks WHERE task_title LIKE ? OR task LIKE ?";

		try (Connection conn = DBCon.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			// 部分一致検索のためLIKEを使用
			String searchKeyword = "%" + keyword + "%";
			pstmt.setString(1, searchKeyword);
			pstmt.setString(2, searchKeyword);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				TaskDTO task = new TaskDTO();
				task.setTaskId(rs.getInt("task_id"));
				task.setTaskTitle(rs.getString("task_title"));
				task.setTask(rs.getString("task"));
				task.setTaskImage(rs.getBytes("task_image"));
				task.setUserId(rs.getInt("user_id"));
				task.setColorId(rs.getObject("color_id") != null ? rs.getInt("color_id") : null);
				task.setTrash(rs.getBoolean("trash"));
				taskList.add(task);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return taskList;
	}

}
