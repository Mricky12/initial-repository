package task.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import task.DBCon;
import task.dao.GroupsDAO;
import task.dto.UsersDTO;

@WebServlet(name = "GroupMemberServlet", urlPatterns = "/group/member")
public class GroupMemberServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private GroupsDAO groupsDAO;

    @Override
    public void init() throws ServletException {
        Connection conn = DBCon.getConnection();
        groupsDAO = new GroupsDAO(conn);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String action = request.getParameter("action");
            if ("search".equals(action)) {
                String keyword = request.getParameter("keyword");

                // 名前またはメールアドレスでユーザーを検索
                List<UsersDTO> searchResults = groupsDAO.searchUsers(keyword);
                request.setAttribute("searchResults", searchResults);
            }

            request.getRequestDispatcher("/groupmember.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "ユーザー検索中にエラーが発生しました。");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("addUser".equals(action)) {
                int groupId = Integer.parseInt(request.getParameter("groupId"));
                int userId = Integer.parseInt(request.getParameter("userId"));

                // ユーザーをグループに追加
                boolean isAdded = groupsDAO.addUserToGroup(groupId, userId);
                request.setAttribute(isAdded ? "message" : "error",
                        isAdded ? "ユーザーをグループに追加しました。" : "ユーザー追加に失敗しました。");
            } else if ("removeUser".equals(action)) {
                int groupId = Integer.parseInt(request.getParameter("groupId"));
                int userId = Integer.parseInt(request.getParameter("userId"));

                // ユーザーをグループから削除
                boolean isRemoved = groupsDAO.removeUserFromGroup(groupId, userId);
                request.setAttribute(isRemoved ? "message" : "error",
                        isRemoved ? "ユーザーをグループから削除しました。" : "ユーザー削除に失敗しました。");
            }

            doGet(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "処理中にエラーが発生しました。");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    @Override
    public void destroy() {
        try {
            if (groupsDAO != null && groupsDAO.getConnection() != null) {
                groupsDAO.getConnection().close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
