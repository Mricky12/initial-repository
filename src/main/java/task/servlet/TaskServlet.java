package task.servlet;

import java.io.IOException;
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
	@Override
	public void init() throws ServletException {
		new TaskDAO();
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
		request.setCharacterEncoding("UTF-8"); // 文字エンコーディングを設定
		response.setCharacterEncoding("UTF-8");

		System.out.println("TaskServlet: doPost() - POSTリクエスト受信");
		String action = request.getParameter("action");
		System.out.println("TaskServlet: action = " + action);

		if ("create".equals(action)) {
			System.out.println("TaskServlet: タスク登録処理開始");

			String taskTitle = request.getParameter("taskTitle");
			String taskContent = request.getParameter("task");
			String colorId = request.getParameter("colorId");
			Part filePart = request.getPart("taskImage");

			System.out.println("TaskServlet: taskTitle = " + taskTitle);
			System.out.println("TaskServlet: taskContent = " + taskContent);
			System.out.println("TaskServlet: colorId = " + colorId);
			System.out.println("TaskServlet: filePartサイズ = " + (filePart != null ? filePart.getSize() : "なし"));

			if (taskTitle == null || taskTitle.isEmpty() || taskContent == null || taskContent.isEmpty()) {
				System.out.println("TaskServlet: バリデーション失敗 - タイトルまたは内容が空");
				request.setAttribute("error", "タイトルと内容は必須です。");
				request.getRequestDispatcher("/myselftask.jsp").forward(request, response);
				return;
			}

			TaskDTO task = new TaskDTO();
			task.setTaskTitle(taskTitle);
			task.setTask(taskContent);

			if (colorId != null && !colorId.isEmpty()) {
				task.setColorId(Integer.valueOf(colorId));
			}

			if (filePart != null && filePart.getSize() > 0) {
				byte[] imageData = filePart.getInputStream().readAllBytes();
				task.setTaskImage(imageData);
			}

			TaskDAO taskDAO = new TaskDAO();
			boolean success = taskDAO.insertTask(task);

			if (success) {
				System.out.println("TaskServlet: タスク登録成功");
				response.sendRedirect("myselftask");
			} else {
				System.out.println("TaskServlet: タスク登録失敗");
				request.setAttribute("error", "タスクの登録に失敗しました。");
				request.getRequestDispatcher("/myselftask.jsp").forward(request, response);
			}
		} else {
			System.out.println("TaskServlet: 未対応のアクション = " + action);
		}
	}

}
