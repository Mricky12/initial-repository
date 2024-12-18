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

	private Connection conn;

	// データベース接続情報
	private static final String JDBC_URL = "jdbc:mysql://localhost:3306/taskstmdb";
	private static final String USER = "root";
	private static final String PASSWORD = "";
	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

	//タスク登録(INSERT)
	public boolean insertTask(TaskDTO task) {
		String sql = "INSERT INTO tasks (task_title, task, task_image, user_id, color_id, trash) VALUES (?, ?, ?, ?, ?, ?)";

		try (Connection conn = DBCon.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			// 必要な場合のみ設定
			conn.setAutoCommit(false);
			pstmt.setString(1, task.getTaskTitle());
			pstmt.setString(2, task.getTask());
			pstmt.setBytes(3, task.getTaskImage());
			pstmt.setInt(4, task.getUserId());
			if (task.getColorId() != null) {
				pstmt.setInt(5, task.getColorId());
			} else {
				pstmt.setNull(5, Types.INTEGER);
			}
			pstmt.setBoolean(6, task.isTrash());

			System.out.println("Task Title: " + task.getTaskTitle());
			System.out.println("Task Content: " + task.getTask());
			System.out.println("Task Image: " + (task.getTaskImage() != null ? task.getTaskImage().length : "null"));
			System.out.println("User ID: " + task.getUserId());
			System.out.println("Color ID: " + task.getColorId());
			System.out.println("Trash: " + task.isTrash());

			int rows = pstmt.executeUpdate();
			//登録成功した場合trueを返す
			return rows > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			//エラー時
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
		//		データが見つからない場合
		return null;

	}

	//	タスク一覧取得 (SELECT ALL)
	public List<TaskDTO> getAllTasks() {
		List<TaskDTO> taskList = new ArrayList<>();
		String sql = "SELECT * FROM tasks";
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
				task.setTrash(rs.getBoolean("trash"));
				taskList.add(task);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return taskList;
	}

	//	タスク更新 (UPDATE)
	public boolean updateTask(TaskDTO task) {
		String sql = "UPDATE tasks SET task_title = ?, task = ?, task_image = ?, user_id = ?, color_id = ?, trash = ? WHERE task_id = ?";
		try (Connection conn = DBCon.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, task.getTaskTitle());
			pstmt.setString(2, task.getTask());
			pstmt.setBytes(3, task.getTaskImage());
			pstmt.setInt(4, task.getUserId());

			if (task.getColorId() != null) {
				pstmt.setInt(5, task.getColorId());
			} else {
				pstmt.setNull(5, Types.INTEGER);
			}
			pstmt.setBoolean(6, task.isTrash());
			pstmt.setInt(7, task.getTaskId());

			int rows = pstmt.executeUpdate();
			// 更新成功の場合 true を返す
			return rows > 0;

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
