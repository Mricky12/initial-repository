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

@WebServlet(name ="adminidentitycheck", urlPatterns="/adminidentitycheck")
public class AdminIdentityCheck extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AdminIdentityCheck() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        request.getRequestDispatcher("adminidentitycheck.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String adminName = request.getParameter("admin_name");
        String adminEmail = request.getParameter("admin_email");

        try (Connection connection = DBCon.getConnection()) {
            AdminsDAO adminDAO = new AdminsDAO();

            // 管理者名とメールアドレスを基に管理者情報を取得
            AdminsDTO admin = adminDAO.findAdminByEmailAndName(adminName, adminEmail, connection);
            if (admin != null) {
                // 管理者IDをセッションに保存
                request.getSession().setAttribute("admin_id", admin.getAdminId());
                response.sendRedirect("adminresetpassword");
            } else {
                request.setAttribute("error", "※ユーザー名、メールアドレスに間違いがあるようです。※");
                request.getRequestDispatcher("adminidentitycheck.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "システムエラーが発生しました。");
            request.getRequestDispatcher("adminidentitycheck.jsp").forward(request, response);
        }
    }
}
