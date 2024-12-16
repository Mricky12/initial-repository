package task.servlet;

import java.io.IOException;
import java.sql.Connection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import task.DBCon;
import task.dao.AdminsDAO;
import task.dto.AdminsDTO;

/**
 * Servlet implementation class AdminRegisterServlet
 */
@WebServlet(name = "adminregister", urlPatterns = "/adminregister")
public class AdminRegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AdminRegisterServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // レスポンス（画面に表示する内容）のデータ形式を設定
        response.setContentType("text/html; charset=UTF-8");
        // 管理者登録画面へフォワード
        request.getRequestDispatcher("adminregister.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // リクエストパラメータを取得
        String adminName = request.getParameter("user_name");
        String adminEmail = request.getParameter("user_email");
        String adminPassword = request.getParameter("user_password");

        try (Connection connection = DBCon.getConnection()) {
            AdminsDAO adminsDAO = new AdminsDAO();

            // 新規管理者登録
            AdminsDTO admin = new AdminsDTO();
            admin.setAdminName(adminName);
            admin.setAdminEmail(adminEmail);
            admin.setAdminPassword(adminPassword);

            if (adminsDAO.insertAdmin(admin, connection)) {
                // 登録成功時、トップページへリダイレクト
                response.sendRedirect("top");
            } else {
                // 登録失敗時、エラーメッセージを設定し登録画面へフォワード
                request.setAttribute("error", "管理者登録に失敗しました。");
                request.getRequestDispatcher("adminregister.jsp").forward(request, response);
            }
        } catch (Exception e) {
            // システムエラー時、エラーメッセージを設定し登録画面へフォワード
            e.printStackTrace();
            request.setAttribute("error", "システムエラーが発生しました。");
            request.getRequestDispatcher("adminregister.jsp").forward(request, response);
        }
    }
}
