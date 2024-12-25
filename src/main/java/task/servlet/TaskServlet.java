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
import task.dto.UsersDTO;

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

		HttpSession session = request.getSession();
		UsersDTO loggedInUser = (UsersDTO) session.getAttribute("loggedInUser");

		if (loggedInUser == null) {
			session.setAttribute("error", "セッションが切れました。再度ログインしてください。");
			response.sendRedirect("top");
			return;
		}

		TaskDAO taskDAO = new TaskDAO();
		// 全タスク取得
		List<TaskDTO> taskList = taskDAO.getAllTasks();
		request.setAttribute("taskList", taskList);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/myselftask.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 文字エンコーディングを設定
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession();
		UsersDTO loggedInUser = (UsersDTO) session.getAttribute("loggedInUser");

		if (loggedInUser == null) {
			response.sendRedirect("top");
			return;
		}

		String action = request.getParameter("action");
		System.out.println("TaskServlet: doPost() - POSTリクエスト受信");
		System.out.println("TaskServlet: action = " + action);
		TaskDAO taskDAO = new TaskDAO();

		//タスク投稿
		if ("create".equals(action)) {
			String taskTitle = request.getParameter("taskTitle");
			String taskContent = request.getParameter("taskContent");
			int colorId = Integer.parseInt(request.getParameter("colorId"));

			TaskDTO task = new TaskDTO();
			task.setTaskTitle(taskTitle);
			task.setTask(taskContent);
			task.setColorId(colorId);

			boolean isTaskCreated = taskDAO.insertTask(task, loggedInUser.getId());
			if (isTaskCreated) {
				response.sendRedirect(request.getContextPath() + "/myselftask");
			} else {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "タスクの登録に失敗しました。");
			}
			//タスク削除
		} else if ("delete".equals(action)) {
			int taskId = Integer.parseInt(request.getParameter("taskId"));
			boolean isTaskDeleted = taskDAO.deleteTask(taskId);
			if (isTaskDeleted) {
				response.sendRedirect(request.getContextPath() + "/myselftask");
			} else {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "タスクの削除に失敗しました。");
			}
			//タスク編集
		} else if ("update".equals(action)) {
			int taskId = Integer.parseInt(request.getParameter("taskId"));
			String taskTitle = request.getParameter("taskTitle");
			String taskContent = request.getParameter("taskContent");
			String colorIdParam = request.getParameter("colorId");
			Integer colorId = (colorIdParam != null && !colorIdParam.isEmpty()) ? Integer.parseInt(colorIdParam) : null;

			TaskDTO task = new TaskDTO();
			task.setTaskId(taskId);
			task.setTaskTitle(taskTitle);
			task.setTask(taskContent);
			task.setColorId(colorId);

			boolean isTaskUpdated = taskDAO.updateTask(task);
			if (isTaskUpdated) {
				response.sendRedirect(request.getContextPath() + "/myselftask");
			} else {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "タスクの更新に失敗しました。");
			}
		}
	}
}