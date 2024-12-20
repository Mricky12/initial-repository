package task.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import task.DBCon;
import task.dao.AdminSystemDAO;
import task.dto.AdminSystemDTO;

/**
 * Servlet implementation class AdminUserSearchServlet
 */
@WebServlet(name = "admin_usersearch", urlPatterns = "/admin_usersearch")
public class AdminUserSearchServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AdminUserSearchServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  
    
 // リクエストパラメータを取得
    String userId = request.getParameter("userid");
    String name = request.getParameter("name");
    String email = request.getParameter("email");

    try (Connection connection = DBCon.getConnection()) {
        AdminSystemDAO dao = new AdminSystemDAO();

        // キーワードであいまい検索を実行
        List<AdminSystemDTO> users = dao.searchUsers(userId, name, email, connection);
        
        

        // 結果をリクエスト属性に設定
        request.setAttribute("users", users);

        // 検索画面へフォワード
        request.getRequestDispatcher("admin_usersearch.jsp").forward(request, response);

    } catch (Exception e) {
        e.printStackTrace();
        request.setAttribute("error", "システムエラーが発生しました。");
        request.getRequestDispatcher("admin_usersearch.jsp").forward(request, response);
    }
}


        
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("userid");

        if (userId == null || userId.isEmpty()) {
            request.setAttribute("error", "ユーザーIDが指定されていません。");
            doGet(request, response);
            return;
        }

        try (Connection connection = DBCon.getConnection()) {
            AdminSystemDAO dao = new AdminSystemDAO();

            // ユーザーを論理削除
            dao.deleteUser(userId, connection);

            // 再検索して結果をリクエストに設定
            List<AdminSystemDTO> users = dao.searchUsers(null, null, null, connection);
            request.setAttribute("users", users);

            // 検索画面へフォワード
            request.getRequestDispatcher("admin_usersearch.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "システムエラーが発生しました。");
            request.getRequestDispatcher("admin_usersearch.jsp").forward(request, response);
        }
    }
}