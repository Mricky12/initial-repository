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
			/*System.out.println("グループリストのサイズ: " + (listGroupsDTO != null ? listGroupsDTO.size() : "null"));*/
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
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
				/*response.sendRedirect("groupmemberedit?success=true&groupName="
				        + URLEncoder.encode(groupName, "UTF-8"));*/
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
	
    

    
	/*@Override
	グループリストを取得してJSPに渡す
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		// グループリストを取得
		List<GroupsDTO> listGroupsDTO = new ArrayList<GroupsDTO>(); // グループのリストを取得
		listGroupsDTO = GroupsDAO.getAll();
		// グループリストをリクエストにセット
		request.setAttribute("groups", listGroupsDTO); // リクエスト属性にセット
	
		// groupmemberedit.jsp にフォワード
		request.getRequestDispatcher("/groupmemberedit.jsp").forward(request, response);
	}
	
	@Override
	group_nameというパラメータを受け取る
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String groupName = request.getParameter("group_name");
	
		// バリデーション：グループ名が空の場合
		if (groupName == null || groupName.trim().isEmpty()) {
		    request.setAttribute("error", "グループ名を入力してください。");
		    request.getRequestDispatcher("/groupcreate.jsp").forward(request, response);
		    return;
		}
		
	
		   
		
		    // DTO にデータをセット
		    GroupsDTO group = new GroupsDTO();
		    group.setGroupName(groupName);
		
		    // DAO 経由でデータベースに登録
		    boolean isCreated = groupsDAO.insertGroup(group);
	
	        // 作成結果に応じてリダイレクトまたはエラーメッセージ
	        if (isCreated) {
	            
	        	// 作成成功時にリダイレクト
	            response.sendRedirect("groupmemberedit.jsp?success=true&groupName=\" + URLEncoder.encode(groupName, \"UTF-8\")"); // グループ登録後、グループ編集ページにリダイレクト
	        } else {
	            // 作成失敗時にエラーメッセージを表示
	            request.setAttribute("error", "グループ登録に失敗しました。");
	            request.getRequestDispatcher("/groupcreate.jsp").forward(request, response);
	        }
	}*/
}
