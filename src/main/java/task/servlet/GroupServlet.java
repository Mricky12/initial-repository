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
import task.dto.GroupsDTO;

@WebServlet(name = "GroupServlet", urlPatterns = "/group")
public class GroupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// GroupsDAOのインスタンスを作成
	private GroupsDAO groupsDAO;

	// 初期化処理（DB接続の準備）
	@Override
	public void init() throws ServletException {
		Connection conn = DBCon.getConnection();
		/*groupsDAOを作成*/
		groupsDAO = new GroupsDAO(conn);
	}

	// doGetメソッド：グループリストをDAOから取得してJSPに渡す
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			request.setCharacterEncoding("UTF-8");

			// グループリストを格納
			List<GroupsDTO> listGroupsDTO = groupsDAO.getAll();
			System.out.println("サーブレットで取得したグループ: " + listGroupsDTO);
			// デバッグ出力: リストサイズを確認
			System.out.println("グループリストのサイズ: " + (listGroupsDTO != null ? listGroupsDTO.size() : "null"));
			// 取得したグループリストをリクエストにセットしJSPに渡す
			request.setAttribute("groups", listGroupsDTO);

			// JSPにフォワード
			request.getRequestDispatcher("/group.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "データの取得中にエラーが発生しました。");
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}

	// doPostメソッド：JSPからグループ名を受け取って新規登録
	@Override
	/*	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		        throws ServletException, IOException {
		    try {
		        request.setCharacterEncoding("UTF-8");
		        String groupName = request.getParameter("group_name");
		
		        // バリデーション：グループ名が空の場合
		        if (groupName == null || groupName.trim().isEmpty()) {
		            request.setAttribute("error", "グループ名を入力してください。");
		            request.getRequestDispatcher("/groupcreate.jsp").forward(request, response);
		            return;
		        }
		
		        // DTOにデータをセット
		        GroupsDTO group = new GroupsDTO();
		        group.setGroupName(groupName);
		
		        // DAO経由でデータベースに登録
		        boolean isCreated = groupsDAO.insertGroup(group);
		
		        // 作成結果に応じた処理
		        if (isCreated) {
		            // 作成成功時はリダイレクト
					response.sendRedirect("groupmemberedit?success=true&groupName="
					        + URLEncoder.encode(groupName, "UTF-8"));
		        	doGet(request, response);
		        } else {
		            // 作成失敗時にエラーメッセージを表示
		            request.setAttribute("error", "グループ登録に失敗しました。");
		            request.getRequestDispatcher("/groupcreate.jsp").forward(request, response);
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		        request.setAttribute("error", "処理中にエラーが発生しました。");
		        request.getRequestDispatcher("/error.jsp").forward(request, response);
		    }
		}*/

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		// 処理種別を取得
		String action = request.getParameter("action");

		// アクションが不正な場合はエラー処理
		if (action == null || action.isEmpty()) {
		    request.setAttribute("error", "不正なリクエストです。");
		    request.getRequestDispatcher("/error.jsp").forward(request, response);
		    return;
		}

		
		try {
			switch (action) {
			case "create": {
				// グループの新規作成
				String groupName = request.getParameter("group_name");
				System.out.println("受け取ったグループ名: " + groupName);
				// バリデーション: グループ名が空かどうか確認
				if (groupName == null || groupName.trim().isEmpty()) {
					request.setAttribute("error", "グループ名を入力してください。");
					request.getRequestDispatcher("/group.jsp").forward(request, response);
					return;
				}

				GroupsDTO groupsDTO = new GroupsDTO();
				groupsDTO.setGroupName(groupName);

				boolean isCreated = groupsDAO.insertGroup(groupsDTO);
				if (isCreated) {
					request.setAttribute("message", "グループが正常に作成されました。");
				} else {
					request.setAttribute("error", "グループ作成に失敗しました。");
				}
				break;
			}
			
			case "update": {
				// グループ名変更
				int groupId = Integer.parseInt(request.getParameter("groupId"));
				String newName = request.getParameter("newName");

				if (newName == null || newName.trim().isEmpty()) {
                    request.setAttribute("error", "新しいグループ名を入力してください。");
                    doGet(request, response);
                    return;
                }
				
				boolean isUpdated = groupsDAO.updateGroupName(groupId, newName);
				if (isUpdated) {
					request.setAttribute("message", "グループ名が正常に変更されました。");
				} else {
					request.setAttribute("error", "グループ名の変更に失敗しました。");
					doGet(request, response);
				}
				break;
			}

			case "delete": {
				// グループ削除
				int groupId = Integer.parseInt(request.getParameter("groupId"));

				boolean isDeleted = groupsDAO.deleteGroup(groupId);
				if (isDeleted) {
					request.setAttribute("message", "グループが正常に削除されました。");
				} else {
					request.setAttribute("error", "グループの削除に失敗しました。");
					doGet(request, response);
				}
				break;
			}

			

			default: {
				// 無効なアクション
				request.setAttribute("error", "無効なアクションです。");
				request.getRequestDispatcher("/error.jsp").forward(request, response);
				break;
			}
			}

			// 処理終了後に一覧表示にリダイレクトまたはフォワード
			doGet(request, response);

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "処理中にエラーが発生しました。");
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}

	// サーブレット破棄時のリソースクリーンアップ
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





/*@WebServlet(name = "GroupServlet", urlPatterns = "/group")
public class GroupServlet extends HttpServlet {
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
            HttpSession session = request.getSession();
            UsersDTO loggedInUser =(UsersDTO) session.getAttribute("loggedInUser"); // セッションからユーザーIDを取得
			System.out.println("おはようJava!!");
			System.out.println(loggedInUser.getId());
			System.out.println(loggedInUser.getName());
            if (loggedInUser == null) {
                response.sendRedirect("login.jsp");
                return;
            }
            
            // ユーザーに紐付くグループリストを取得
			List<GroupsDTO> userGroups = groupsDAO.getGroupsByUserId(loggedInUser.getId());
			request.setAttribute("userGroups", userGroups);

            // JSPへフォワード
            request.getRequestDispatcher("/group.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "グループ情報の取得中にエラーが発生しました。");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
  
        String action = request.getParameter("action");
        try {
            HttpSession session = request.getSession();
            UsersDTO loggedInUser = (UsersDTO) session.getAttribute("loggedInUser");

            if (loggedInUser == null) {
                response.sendRedirect("login.jsp");
                return;
            }

            String groupName = request.getParameter("group_name");
            if (groupName == null || groupName.trim().isEmpty()) {
                request.setAttribute("error", "グループ名を入力してください。");
                request.getRequestDispatcher("/groupcreate.jsp").forward(request, response);
                return;
            }

            // グループを作成
            GroupsDTO group = new GroupsDTO();
            group.setGroupName(groupName);
            boolean isCreated = groupsDAO.insertGroup(group);

            // グループ作成成功後、ユーザーとグループを紐付け
            if (isCreated) {
                groupsDAO.addUserToGroup(loggedInUser.getId(), group.getGroupId());
                request.setAttribute("message", "グループを作成しました。");
            } else {
                request.setAttribute("error", "グループ作成に失敗しました。");
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
*/