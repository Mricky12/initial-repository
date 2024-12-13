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
/**
 * Servlet implementation class identitycheck
 */
@WebServlet(name ="identitycheck", urlPatterns="/identitycheck")
public class Identitycheck extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Identitycheck() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        request.getRequestDispatcher("identitycheck.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = request.getParameter("user_name");
        String userEmail = request.getParameter("user_email");

        try (Connection connection = DBCon.getConnection()) {
            UsersDAO userDAO = new UsersDAO();

            if (userDAO.authenticateUser(userName, userEmail, connection)) {
                // 認証成功後、パスワード再設定画面へリダイレクト
                request.setAttribute("userEmail", userEmail); // 次画面で使用するため設定
                request.getRequestDispatcher("resetpassword.jsp").forward(request, response);
            } else {
                // 認証失敗時
                request.setAttribute("error", "ユーザー名またはメールアドレスが正しくありません。");
                request.getRequestDispatcher("identitycheck.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "システムエラーが発生しました。");
            request.getRequestDispatcher("identitycheck.jsp").forward(request, response);
        }
    }
}