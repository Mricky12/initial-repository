package task.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import task.DBCon;
import task.dto.AdminsDTO;

@WebServlet(name = "adminedit", urlPatterns = "/admin_edit")
public class AdminEditServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AdminEditServlet() {
        super();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        request.getRequestDispatcher("admin_edit.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        AdminsDTO loggedInAdmin = (AdminsDTO) session.getAttribute("loggedInAdmin");

        if (loggedInAdmin == null) {
            session.setAttribute("error", "セッションが切れました。再度ログインしてください。");
            response.sendRedirect("top");
            return;
        }

        String action = request.getParameter("action");
        if ("edit".equals(action)) {
            try (Connection connection = DBCon.getConnection()) {
                String newName = request.getParameter("name");
                String newEmail = request.getParameter("email");
                String newPassword = request.getParameter("password");

                StringBuilder updateSql = new StringBuilder("UPDATE admins SET ");
                boolean hasUpdate = false;

                if (newName != null && !newName.trim().isEmpty()) {
                    updateSql.append("admin_name = ?, ");
                    loggedInAdmin.setAdminName(newName); // セッション用
                    hasUpdate = true;
                }
                if (newEmail != null && !newEmail.trim().isEmpty()) {
                    updateSql.append("admin_email = ?, ");
                    loggedInAdmin.setAdminEmail(newEmail); // セッション用
                    hasUpdate = true;
                }
                if (newPassword != null && !newPassword.trim().isEmpty()) {
                    updateSql.append("admin_password = ?, ");
                    hasUpdate = true;
                }

                if (!hasUpdate) {
                    response.sendRedirect("admin_edit");
                    return;
                }

                updateSql.setLength(updateSql.length() - 2);
                updateSql.append(" WHERE admin_id = ?");

                try (PreparedStatement ps = connection.prepareStatement(updateSql.toString())) {
                    int index = 1;
                    if (newName != null && !newName.trim().isEmpty()) ps.setString(index++, newName);
                    if (newEmail != null && !newEmail.trim().isEmpty()) ps.setString(index++, newEmail);
                    if (newPassword != null && !newPassword.trim().isEmpty()) ps.setString(index++, newPassword);
                    ps.setInt(index, loggedInAdmin.getAdminId());
                    ps.executeUpdate();
                }

                session.setAttribute("loggedInAdmin", loggedInAdmin);
                response.sendRedirect("admin_edit");
            } catch (SQLException e) {
                throw new ServletException("データベースエラーが発生しました。", e);
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "無効なアクションです。");
        }
    }
}