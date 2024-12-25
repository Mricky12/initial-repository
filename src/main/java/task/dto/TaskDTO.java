package task.dto;

import java.util.Arrays;
import java.util.Objects;

public class TaskDTO {

	// フィールド定義
	private Integer taskId; // タスクID
	private String taskTitle; // タスクタイトル
	private String task; // タスク内容
	private byte[] taskImage; // タスク画像
	private Integer userId; // ユーザーID
	private Integer colorId; // カラーID（NULL許容なのでInteger）
	private boolean trash; // ゴミ箱フラグ
	private String colorCode; // カラーコードを格納するフィールド
	private UsersDTO loggedInUser; // ログインユーザー情報を格納するフィールド

	// コンストラクタ
	public TaskDTO() {
	}

	// 全フィールドを因数に持つコンストラクタ
	public TaskDTO(int taskId, String taskTitle, String task, byte[] taskImage, int userId, Integer colorId,
			boolean trash, UsersDTO loggedInUser) {
		this.taskId = taskId;
		this.taskTitle = taskTitle;
		this.task = task;
		this.taskImage = taskImage;
		this.userId = userId;
		this.colorId = colorId;
		this.trash = trash;
		this.loggedInUser = loggedInUser;
	}

	// Getterメソッド
	public Integer getTaskId() {
		return taskId;
	}

	public String getTaskTitle() {
		return taskTitle;
	}

	public String getTask() {
		return task;
	}

	public byte[] getTaskImage() {
		return taskImage;
	}

	public Integer getUserId() {
		return userId;
	}

	public Integer getColorId() {
		return colorId;
	}

	public boolean isTrash() {
		return trash;
	}

	public String getColorCode() {
		return colorCode;
	}

	public UsersDTO getLoggedInUser() {
		return loggedInUser;
	}

	// Setterメソッド
	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public void setTaskTitle(String taskTitle) {
		this.taskTitle = taskTitle;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public void setTaskImage(byte[] taskImage) {
		this.taskImage = taskImage;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public void setColorId(Integer colorId) {
		this.colorId = colorId;
	}

	public void setTrash(boolean trash) {
		this.trash = trash;
	}

	public void setColorCode(String colorCode) {
		this.colorCode = colorCode;
	}

	public void setLoggedInUser(UsersDTO loggedInUser) {
		this.loggedInUser = loggedInUser;
	}

	// Override: toStringメソッド
	@Override
	public String toString() {
		return "TaskDTO{" +
				"taskId=" + taskId +
				", taskTitle='" + taskTitle + '\'' +
				", task='" + task + '\'' +
				", taskImage=" + Arrays.toString(taskImage) +
				", userId=" + userId +
				", colorId=" + colorId +
				", trash=" + trash +
				", loggedInUser=" + loggedInUser +
				'}';
	}

	// Override: hashCodeメソッド
	@Override
	public int hashCode() {
		return Objects.hash(taskId, taskTitle, task, Arrays.hashCode(taskImage), userId, colorId, trash, loggedInUser);
	}

	// Override: equalsメソッド
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		TaskDTO taskDTO = (TaskDTO) obj;
		return taskId == taskDTO.taskId &&
				userId == taskDTO.userId &&
				trash == taskDTO.trash &&
				Objects.equals(taskTitle, taskDTO.taskTitle) &&
				Objects.equals(task, taskDTO.task) &&
				Arrays.equals(taskImage, taskDTO.taskImage) &&
				Objects.equals(colorId, taskDTO.colorId) &&
				Objects.equals(loggedInUser, taskDTO.loggedInUser);
	}
}
