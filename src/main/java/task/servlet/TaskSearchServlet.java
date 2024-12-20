package task.servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import task.dao.TaskDAO;
import task.dto.TaskDTO;

/**
 * Servlet implementation class TaskSearchServlet
 */
@WebServlet(name = "task", urlPatterns = "/searchTasks")
public class TaskSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	* @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	*/
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// パラメータから検索キーワードを取得
		String keyword = request.getParameter("keyword");
		TaskDAO taskDAO = new TaskDAO();

		// 検索実行
		List<TaskDTO> searchResults = taskDAO.searchTasks(keyword);

		// 検索結果をリクエスト属性にセット
		request.setAttribute("searchResults", searchResults);
		request.setAttribute("keyword", keyword);

		// JSPページにフォワード
		request.getRequestDispatcher("searchResults.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TaskSearchServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
