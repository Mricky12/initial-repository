package task.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import task.dao.TaskDAO;
import task.dto.TaskDTO;

@WebServlet(name = "myselftask", urlPatterns = "/myselftask")
//アップロードファイルの最大サイズを5MBに設定
@MultipartConfig(maxFileSize = 1024 * 1024 * 5)
public class TaskServlet extends HttpServlet {
	private TaskDAO taskDAO;

	@Override
	public void init() throws ServletException {
		// DAOインスタンスを初期化
		taskDAO = new TaskDAO();
		System.out.println("TaskServlet: init() - DAO初期化完了");
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("TaskServlet: doGet() - GETリクエスト受信");

		TaskDAO taskDAO = new TaskDAO();
		// 全タスク取得
		List<TaskDTO> taskList = taskDAO.getAllTasks();

		// タスク一覧を取得し、JSPへ転送
		try {
			System.out.println("TaskServlet: タスクリストのサイズ: " + taskList.size());

			request.setAttribute("taskList", taskList);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/myselftask.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			System.out.println("TaskServlet: doGet() - エラー発生: " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// リクエストの文字エンコーディング設定
		request.setCharacterEncoding("UTF-8");

		System.out.println("TaskServlet: doPost() - POSTリクエスト受信");

		// すべてのパラメータを確認
		request.getParameterMap().forEach((key, values) -> {
			System.out.println("Param: " + key + " = " + String.join(", ", values));
		});

		// アクション判定
		String action = request.getParameter("action");
		System.out.println("TaskServlet: doPost() - Action: " + action);

		try {
			// タスク登録処理
			if ("create".equals(action)) {
				handleTaskCreation(request, response);
				// タスク更新処理
			} else if ("update".equals(action)) {
				handleTaskUpdate(request, response);
				// タスク削除処理
			} else if ("delete".equals(action)) {
				handleTaskDeletion(request, response);
			} else {
				System.out.println("TaskServlet: 不正なアクション - " + action);
				// 不正なリクエストの場合、タスク一覧にリダイレクト
				response.sendRedirect("myselftask");
			}
		} catch (Exception e) {
			System.out.println("TaskServlet: doPost() - エラー発生: " + e.getMessage());
			e.printStackTrace();
		}
	}

	// タスク登録処理
	private void handleTaskCreation(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("TaskServlet: タスク登録処理開始");

		String taskTitle = request.getParameter("taskTitle");
		String taskContent = request.getParameter("taskContent");
		String colorIdParam = request.getParameter("colorId");
		// 画像ファイルアップロード用
		Part filePart = request.getPart("taskImage");

		System.out.println(
				"TaskServlet: 入力データ - タイトル: " + taskTitle + ", 内容: " + taskContent + ", カラーID: " + colorIdParam);

		Integer colorId = null;
		byte[] imageBytes = null;

		// カラーパレットIDの処理
		if (colorIdParam != null && !colorIdParam.isEmpty()) {
			try {
				colorId = Integer.parseInt(colorIdParam);
			} catch (NumberFormatException e) {
				System.out.println("TaskServlet: カラーIDの変換エラー: " + colorIdParam);
			}
		}

		// ファイルがアップロードされている場合
		if (filePart != null && filePart.getSize() > 0) {
			System.out.println("TaskServlet: アップロードされたファイルサイズ: " + filePart.getSize() + " bytes");
			try (InputStream fileContent = filePart.getInputStream()) {
				imageBytes = fileContent.readAllBytes();
			} catch (Exception e) {
				System.out.println("TaskServlet: ファイル読み取りエラー: " + e.getMessage());
				e.printStackTrace();
			}
		} else {
			System.out.println("TaskServlet: ファイル未アップロードまたはサイズ0");
		}

		// 入力データのバリデーション（タイトルまたは内容が必須）
		if ((taskTitle == null || taskTitle.trim().isEmpty())
				&& (taskContent == null || taskContent.trim().isEmpty())) {
			System.out.println("TaskServlet: バリデーションエラー - タイトルまたは内容が未入力");
			request.setAttribute("error", "タスクタイトルまたはタスク内容を入力してください。");
			request.getRequestDispatcher("myselftask.jsp").forward(request, response);
			return;
		}

		// TaskDTOインスタンスの作成
		TaskDTO task = new TaskDTO();
		task.setTaskTitle(taskTitle != null ? taskTitle.trim() : "");
		task.setTask(taskContent != null ? taskContent.trim() : "");
		// 仮のユーザーID（実際はセッションなどから取得）
		task.setUserId(1);
		task.setColorId(colorId);
		task.setTaskImage(imageBytes);
		task.setTrash(false);

		// タスク登録処理
		boolean isInserted = taskDAO.insertTask(task);

		// 登録成功時はタスク一覧を取得し、myselftask.jspへ転送
		if (isInserted) {
			System.out.println("TaskServlet: タスク登録成功");
			// doGetを呼び出してJSPに一覧表示
			doGet(request, response);
		} else {
			System.out.println("TaskServlet: タスク登録失敗");
			request.setAttribute("error", "タスクの登録に失敗しました。");
			request.getRequestDispatcher("myselftask.jsp").forward(request, response);
		}

	}

	// タスク更新関係
	private void handleTaskUpdate(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("TaskServlet: handleTaskUpdate() - タスク更新処理開始");

		// taskId取得のバリデーション
		int taskId = 0;
		try {
			taskId = Integer.parseInt(request.getParameter("taskId"));
		} catch (NumberFormatException e) {
			System.out.println("TaskServlet: タスクIDが不正です");
			request.setAttribute("error", "不正なタスクIDです。");
			request.getRequestDispatcher("myselftask.jsp").forward(request, response);
			return;
		}

		String taskTitle = request.getParameter("taskTitle");
		String taskContent = request.getParameter("taskContent");

		System.out.println("TaskServlet: 更新データ - タスクID: " + taskId + ", タイトル: " + taskTitle + ", 内容: " + taskContent);

		// TaskDTOインスタンスの作成
		TaskDTO task = new TaskDTO();
		task.setTaskId(taskId);
		task.setTaskTitle(taskTitle != null ? taskTitle.trim() : "");
		task.setTask(taskContent != null ? taskContent.trim() : "");

		// タスク更新処理
		boolean isUpdated = taskDAO.updateTask(task);

		if (isUpdated) {
			System.out.println("TaskServlet: タスク更新成功");
			response.sendRedirect("myselftask"); // 更新後は一覧表示にリダイレクト
		} else {
			System.out.println("TaskServlet: タスク更新失敗");
			request.setAttribute("error", "タスクの更新に失敗しました。");
			request.getRequestDispatcher("myselftask.jsp").forward(request, response);
		}
	}

	// タスク削除処理
	private void handleTaskDeletion(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("TaskServlet: handleTaskDeletion() - タスク削除処理開始");

		int taskId = Integer.parseInt(request.getParameter("taskId"));
		System.out.println("TaskServlet: 削除対象タスクID: " + taskId);

		boolean isDeleted = taskDAO.deleteTask(taskId);

		if (isDeleted) {
			System.out.println("TaskServlet: タスク削除成功");
			response.sendRedirect("myselftask");
		} else {
			System.out.println("TaskServlet: タスク削除失敗");
			request.setAttribute("error", "タスクの削除に失敗しました。");
			request.getRequestDispatcher("myselftask.jsp").forward(request, response);
		}
	}
}
