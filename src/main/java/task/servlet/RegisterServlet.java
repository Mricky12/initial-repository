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
 * Servlet implementation class RegisterServlet
 */
@WebServlet(name ="register",urlPatterns = "/register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public RegisterServlet() {
        super();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//レスポンス（画面に表示する内容）のデータ形式を設定。
		//text/html: HTML形式で返すことを示す。
		response.setContentType("text/html; charset=UTF-8");

            request.getRequestDispatcher("register.jsp").forward(request, response);
           
		
		
	}
    
    
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = request.getParameter("user_name");
        String userEmail = request.getParameter("user_email");
        String userPassword = request.getParameter("user_password");

        try (Connection connection = DBCon.getConnection()) {
            UsersDAO userDAO = new UsersDAO();


            // 新規ユーザー登録
            UsersDTO user = new UsersDTO();
            user.setName(userName);
            user.setEmail(userEmail);
            user.setPassword(userPassword);

            if (userDAO.insertUser(user, connection)) {
                response.sendRedirect("top");
            } else {
                request.setAttribute("error", "登録に失敗しました。");
                request.getRequestDispatcher("register.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "システムエラーが発生しました。");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }
}
