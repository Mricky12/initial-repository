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
import task.dao.GroupsDAO;
import task.dto.GroupsDTO;

@WebServlet(name = "GroupMemberServlet", urlPatterns = "/groupmember")
public class GroupMemberServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Connection connection;

    @Override
	/*Connectionをinit()で取得して保持*/
    public void init() throws ServletException {
    	connection = DBCon.getConnection();
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String groupId = request.getParameter("groupId");
        String userIds = request.getParameter("userIds");

        GroupsDAO groupsDAO = new GroupsDAO(connection);

        try {
            if ("add".equals(action)) {
                if (groupId != null && !groupId.isEmpty() && userIds != null && !userIds.isEmpty()) {
                    String[] userIdArray = userIds.split(",");
                    boolean success = true;

                    for (String userId : userIdArray) {
                        if (!groupsDAO.addUserToGroup(Integer.parseInt(groupId), Integer.parseInt(userId.trim()))) {
                            success = false;
                            break;
                        }
                    }

                    if (success) {
                        request.setAttribute("message", "メンバーを追加しました。");
                    } else {
                        request.setAttribute("message", "一部のメンバー追加に失敗しました。");
                    }
                } else {
                    request.setAttribute("message", "必要な情報が不足しています。");
                }
            } else if ("delete".equals(action)) {
                if (groupId != null && !groupId.isEmpty()) {
                    if (groupsDAO.removeAllMembersFromGroup(Integer.parseInt(groupId))) {
                        request.setAttribute("message", "メンバーを削除しました。");
                    } else {
                        request.setAttribute("message", "メンバー削除に失敗しました。");
                    }
                } else {
                    request.setAttribute("message", "グループが選択されていません。");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "エラーが発生しました。");
        }

        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            GroupsDAO groupsDAO = new GroupsDAO(connection);
            List<GroupsDTO> groups = groupsDAO.getAll();
            request.setAttribute("groups", groups);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "グループ情報の取得中にエラーが発生しました。");
        }

        request.getRequestDispatcher("groupmember.jsp").forward(request, response);
    }

    @Override
    public void destroy() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        
}

