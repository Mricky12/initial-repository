package task.servlet;

import java.io.IOException;
import java.sql.Connection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import task.DBCon;
import task.dao.UsersDAO;
import task.dto.UsersDTO;
/**
 * Servlet implementation class identitycheck
 */
@WebServlet(name ="identitycheck", urlPatterns="/identitycheck")
public class IdentityCheck extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IdentityCheck() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        request.getRequestDispatcher("identitycheck.jsp").forward(request, response);
    }

	// IdentityCheck.java (例)

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String userName = request.getParameter("user_name");
	    String userEmail = request.getParameter("user_email");

	    try (Connection connection = DBCon.getConnection()) {
	        UsersDAO userDAO = new UsersDAO();

	        // ユーザー名とメールアドレスを基にユーザー情報を取得
	        UsersDTO user = userDAO.findUserByEmailAndName(userName, userEmail, connection);
	        if (user != null) {
	            // ユーザーIDをセッションに保存
	            request.getSession().setAttribute("user_id", user.getId());
	            response.sendRedirect("resetpassword.jsp");
	        } else {
	            request.setAttribute("error", "ユーザーが見つかりません。");
	            request.getRequestDispatcher("identitycheck.jsp").forward(request, response);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        request.setAttribute("error", "システムエラーが発生しました。");
	        request.getRequestDispatcher("identitycheck.jsp").forward(request, response);
	    }
	}
}