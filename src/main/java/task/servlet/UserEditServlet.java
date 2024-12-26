package task.servlet;

import java.io.IOException;
import java.sql.Connection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import task.DBCon;
import task.dao.UserEditDAO;
import task.dto.UserEditDTO;
import task.dto.UsersDTO;

@WebServlet(name = "UserEditServlet", urlPatterns = "/edituser")
public class UserEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UserEditServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		request.getRequestDispatcher("edituser.jsp").forward(request, response);

		HttpSession session = request.getSession();
		UserEditDTO loggedInUser = (UserEditDTO) session.getAttribute("loggedInUser");

		if (loggedInUser == null) {
			session.setAttribute("error", "セッションが切れました。再度ログインしてください。");
			response.sendRedirect("top");
			return;
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		UsersDTO usersDTO = (UsersDTO) session.getAttribute("loggedInUser");

		if (usersDTO == null) {
			session.setAttribute("error", "セッションが切れました。再度ログインしてください。");
			response.sendRedirect("top");
			return;
		}

		// UsersDTO を UserEditDTO に変換
		UserEditDTO loggedInUser = new UserEditDTO();
		loggedInUser.setId(usersDTO.getId());
		loggedInUser.setName(usersDTO.getName());
		loggedInUser.setEmail(usersDTO.getEmail());
		loggedInUser.setPassword(usersDTO.getPassword());

		String action = request.getParameter("action");

		try (Connection connection = DBCon.getConnection()) {
			UserEditDAO userEditDAO = new UserEditDAO();

			if ("edit".equals(action)) {
				handleEditAction(request, response, session, loggedInUser, userEditDAO, connection);
			} else if ("withdraw".equals(action)) {
				handleWithdrawAction(response, session, loggedInUser, userEditDAO, connection);
			} else {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "無効なアクションです。");
			}
		} catch (Exception e) {
			throw new ServletException("処理中にエラーが発生しました。", e);
		}
	}

	private void handleEditAction(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			UserEditDTO loggedInUser, UserEditDAO userEditDAO, Connection connection)
			throws IOException, ServletException {
		String newName = request.getParameter("name");
		String newEmail = request.getParameter("email");
		String newPassword = request.getParameter("password");

		if (newName != null && !newName.trim().isEmpty()) {
			loggedInUser.setName(newName);
		}
		if (newEmail != null && !newEmail.trim().isEmpty()) {
			loggedInUser.setEmail(newEmail);
		}
		if (newPassword != null && !newPassword.trim().isEmpty()) {
		}

		try {
			if (userEditDAO.updateUser(loggedInUser, connection)) {
				session.setAttribute("loggedInUser", loggedInUser);
				session.setAttribute("success", "ユーザー情報が更新されました。");
				response.sendRedirect("edituser");
			} else {
				session.setAttribute("error", "ユーザー情報の更新に失敗しました。");
				response.sendRedirect("edituser");
			}
		} catch (Exception e) {
			session.setAttribute("error", "処理中にエラーが発生しました。");
			response.sendRedirect("edituser");
		}
	}

	private void handleWithdrawAction(HttpServletResponse response, HttpSession session, UserEditDTO loggedInUser,
			UserEditDAO userEditDAO, Connection connection) throws IOException {
		try {
			userEditDAO.deleteUser(loggedInUser.getId(), connection);
			session.invalidate(); // セッションを終了
			response.sendRedirect("top");
		} catch (Exception e) {
			session.setAttribute("error", "退会処理中にエラーが発生しました。");
			response.sendRedirect("edituser");
		}
	}

}
