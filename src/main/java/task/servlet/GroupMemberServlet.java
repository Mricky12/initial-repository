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
import task.dto.UsersDTO;

@WebServlet(name = "GroupMemberServlet", urlPatterns = "/groupmember")
public class GroupMemberServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private GroupsDAO groupsDAO;

    @Override
    public void init() throws ServletException {
        Connection conn = DBCon.getConnection();
        groupsDAO = new GroupsDAO(conn);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<GroupsDTO> groups = groupsDAO.getAll();
            request.setAttribute("groups", groups);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "グループの取得中にエラーが発生しました。");
        }
        request.getRequestDispatcher("groupmember.jsp").forward(request, response);
    }

    @Override
	/*ユーザーをグループに追加するリクエストを処理*/
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String groupId = request.getParameter("groupId");
        String[] Id = request.getParameterValues("Id");
        String member = request.getParameter("member");

        if (groupId == null || groupId.isEmpty() || member == null || member.isEmpty()) {
            request.setAttribute("error", "グループまたはメンバー情報が不足しています。");
            doGet(request, response);
            return;
        }

        try {
            UsersDTO user = groupsDAO.findUserByNameOrEmail(member);
            if (user == null) {
                request.setAttribute("error", "指定されたユーザーが見つかりません。");
            } else {
                boolean isAdded = groupsDAO.addUserToGroup(Integer.parseInt(groupId), user.getId());
                if (isAdded) {
                    request.setAttribute("success", "メンバーを追加しました。");
                } else {
                    request.setAttribute("error", "メンバー追加に失敗しました。");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "エラーが発生しました。");
        }
        doGet(request, response);
    }
}

