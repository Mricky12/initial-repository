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
import task.dao.AdminSystemDAO;
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
        
        
        HttpSession session = request.getSession();
        AdminsDTO loggedInAdmin = (AdminsDTO) session.getAttribute("loggedInAdmin");
        
       

        if (loggedInAdmin == null) {
            session.setAttribute("error", "セッションが切れました。再度ログインしてください。");
            response.sendRedirect("top");
            return;
        }
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

        try (Connection connection = DBCon.getConnection()) {
            AdminSystemDAO adminDao = new AdminSystemDAO();

            if ("edit".equals(action)) {
                // 管理者情報の編集
                String newName = request.getParameter("name");
                String newEmail = request.getParameter("email");
                String newPassword = request.getParameter("password");

                if (newName != null && !newName.trim().isEmpty()) {
                    loggedInAdmin.setAdminName(newName);
                }
                if (newEmail != null && !newEmail.trim().isEmpty()) {
                    loggedInAdmin.setAdminEmail(newEmail);
                }
                if (newPassword != null && !newPassword.trim().isEmpty()) {
                    loggedInAdmin.setAdminPassword(newPassword);
                }

                adminDao.updateAdmin(loggedInAdmin, connection);
                session.setAttribute("loggedInAdmin", loggedInAdmin);
                response.sendRedirect("admin_edit");

            } else if ("delete".equals(action)) {
                // 管理者退会処理
                adminDao.deleteAdmin(loggedInAdmin.getAdminId(), connection);
                session.invalidate(); // セッションを終了
                response.sendRedirect("top");
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "無効なアクションです。");
            }
        } catch (Exception e) {
            throw new ServletException("処理中にエラーが発生しました。", e);
        }
    }
    }