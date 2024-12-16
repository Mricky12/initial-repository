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

@WebServlet(name = "adminresetpassword", urlPatterns = "/adminresetpassword")
public class AdminResetPassword extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AdminResetPassword() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        request.getRequestDispatcher("adminresetpassword.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String newPassword = request.getParameter("admin_password");

        // セッションから管理者IDを取得
        Integer adminId = (Integer) request.getSession().getAttribute("admin_id");

        if (adminId == null) {
            request.setAttribute("error", "セッションが切れています。もう一度ログインしてください。");
            request.getRequestDispatcher("top.jsp").forward(request, response);
            return;
        }

        try (Connection connection = DBCon.getConnection()) {
            AdminsDAO adminDAO = new AdminsDAO();

            // 管理者IDを使用してパスワードを更新
            if (adminDAO.updatePasswordById(adminId, newPassword, connection)) {
                // パスワード更新成功
                response.sendRedirect("top");
            } else {
                // パスワード更新失敗
                request.setAttribute("error", "パスワードの更新に失敗しました。");
                request.getRequestDispatcher("adminresetpassword.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "システムエラーが発生しました。");
            request.getRequestDispatcher("adminresetpassword.jsp").forward(request, response);
        }
    }
}
