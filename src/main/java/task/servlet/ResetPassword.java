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

@WebServlet(name = "resetpassword", urlPatterns = "/resetpassword")
public class ResetPassword extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ResetPassword() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        request.getRequestDispatcher("resetpassword.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	 String newPassword = request.getParameter("user_password");

    	    // セッションからユーザーIDを取得
    	    Integer userId = (Integer) request.getSession().getAttribute("user_id");

    	    if (userId == null) {
    	        request.setAttribute("error", "セッションが切れています。もう一度ログインしてください。");
    	        request.getRequestDispatcher("top.jsp").forward(request, response);
    	        return;
    	    }

    	    try (Connection connection = DBCon.getConnection()) {
    	        UsersDAO userDAO = new UsersDAO();

    	        // ユーザーIDを使用してパスワードを更新
    	        if (userDAO.updatePasswordById(userId, newPassword, connection)) {
    	            // パスワード更新成功
    	            response.sendRedirect("top");
    	        } else {
    	            // パスワード更新失敗
    	            request.setAttribute("error", "パスワードの更新に失敗しました。");
    	            request.getRequestDispatcher("resetpassword.jsp").forward(request, response);
    	        }
    	    } catch (Exception e) {
    	        e.printStackTrace();
    	        request.setAttribute("error", "システムエラーが発生しました。");
    	        request.getRequestDispatcher("resetpassword.jsp").forward(request, response);
    	    }
    }
  }
