package task.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import task.DBCon;
import task.dto.TaskDTO;

public class TaskSearchDAO {

	// タスクタイトルまたはタスク内容で検索するメソッド
	public List<TaskDTO> searchTasks(String title, String content) throws SQLException {
		List<TaskDTO> taskList = new ArrayList<>();
		String sql = "SELECT * FROM tasks WHERE (taskTitle LIKE ? OR task LIKE ?) AND trash = false";

		try (Connection conn = DBCon.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, "%" + title + "%");
			pstmt.setString(2, "%" + content + "%");

			try (ResultSet resultSet = pstmt.executeQuery()) {
				while (resultSet.next()) {
					TaskDTO task = new TaskDTO();
					task.setTaskId(resultSet.getInt("taskId"));
					task.setTaskTitle(resultSet.getString("taskTitle"));
					task.setTask(resultSet.getString("task"));
					task.setTaskImage(resultSet.getBytes("taskImage"));
					task.setUserId(resultSet.getInt("userId"));
					task.setColorId(resultSet.getInt("colorId"));
					task.setTrash(resultSet.getBoolean("trash"));
					task.setColorCode(resultSet.getString("colorCode")); // カラーコードも取得

					taskList.add(task);
				}
			}
		}

		return taskList;
	}

	public List<TaskDTO> searchTasksByKeyword(String searchKeyword) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public List<TaskDTO> getAllTasks() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}
}
