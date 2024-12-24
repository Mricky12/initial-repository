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
import jakarta.servlet.http.HttpSession;

import task.dao.TaskDAO;
import task.dto.TaskDTO;

@WebServlet(name = "myselftask", urlPatterns = "/myselftask")
//アップロードファイルの最大サイズを5MBに設定
@MultipartConfig(maxFileSize = 1024 * 1024 * 5)
public class TaskServlet extends HttpServlet {

	@Override
	public void init() throws ServletException {
		super.init();
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
		request.setAttribute("taskList", taskList);

		// タスク一覧を取得し、JSPへ転送
		RequestDispatcher dispatcher = request.getRequestDispatcher("/myselftask.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 文字エンコーディングを設定
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		String action = request.getParameter("action");
		System.out.println("TaskServlet: doPost() - POSTリクエスト受信");
		System.out.println("TaskServlet: action = " + action);

		if ("create".equals(action)) {
			HttpSession session = request.getSession();
			Integer userId = (Integer) session.getAttribute("userId");

			if (userId == null) {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "ユーザーがログインしていません。");
				return;
			}

			// タスクの情報を取得
			String taskTitle = request.getParameter("taskTitle");
			String taskContent = request.getParameter("taskContent");
			int colorId = Integer.parseInt(request.getParameter("colorId"));

			// タスクをデータベースに登録する処理を呼び出す
			boolean isTaskCreated = insertTask(userId, taskTitle, taskContent, colorId);
			if (isTaskCreated) {
				// タスクが正常に登録された場合の処理
				response.sendRedirect(request.getContextPath() + "/myselftask");
			} else {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "タスクの登録に失敗しました。");
			}
		}

	}

	private boolean insertTask(Integer userId, String taskTitle, String taskContent, int colorId) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

}
