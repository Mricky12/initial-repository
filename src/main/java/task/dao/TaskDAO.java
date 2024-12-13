package task.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import task.DBCon;
import task.dto.TaskDTO;

public class TaskDAO {

	//タスク登録(INSERT)
	public boolean insertTask(TaskDAO task) {
		String sql = "INSERT INTO tasks (task_title,task,task_image,user_id,color_id,trash VALUE (?, ?, ?, ?, ?, ?)";
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

			int rows = pstmt.executeUpdate();
			//			登録成功した場合trueを返す
			return rows > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	//	タスク取得(SELECT BY ID)
	public TaskDTO getTaskById(int taskId) {
		String sql = "SELECT * FROM tasks WHERE task_id= ?";
		try(Connection conn = DBCon.getConnection());
				PreparedStatement pstmt = conn.prepareStatement(sql){
					
					pstmt.setInt(1,taskId);
					ResultSet rs = pstmt.executeQuery();
					
					if(rs.next()) {
						TaskDTO task = new TaskDTO();
						task.setTaskId(rs.getInt("task_id"));
						task.setTaskTitle(rs.getString("task_title"));
						task.setTask(rs.getString("task"));
						task.setTaskImage(rs.getBytes("task_image"));
						task.setUserId(rs.getInt("user_id"));
						task.setColorId(rs.getObject("color_id") !=null ? rs.getInt("color_id"):null);
						task.setTrash(rs.getBoolean("trash"));
						return task;
					}
									
				}catch (SQLException e) {
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
				task.ColorId(rs.getObject("color_id") != null ? rs.getInt("color_id") : null);
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
		String sql = "UPDATE tasks SET task_title = ?, task = ?,task_image = ?,user_id = ?,color_id = ?, trash = ? WHERE task_id =?";
		try (Connection conn = DBCon.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, task.getTaskTitle());
			pstmt.setString(2, task.getTask());
			pstmt.setBytes(3, getTaskImage());
			pstmt.setInt(4, task.getUserId());
			if (task.getColorId() != null) {
				pstmt.setInt(5, task.getColorId());
			} else {
				pstmt.setNull(5, Types.INTEGER);
			}
			pstmt.setBoolean(6, task.isTrash());
			pstmt.setInt(7, task.getTaskId());

			int rows = pstmt.executeUpdate();
		}
	}

}
