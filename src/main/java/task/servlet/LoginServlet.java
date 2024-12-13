package task.servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//サーブレットが「どのURLで動作するか」を指定する
//task のリクエストが来たときに、このサーブレットが呼び出される
@WebServlet(name = "top",urlPatterns = "/top")

//サーブレットを作成するためのクラスを定義
//HttpServlet を継承して、HTTPリクエストを処理する能力を持たせる
public class LoginServlet extends HttpServlet {
	//シリアライズ（オブジェクトを保存・転送）する際のバージョン管理用ID。
	//サーブレットで必須ではないが、クラスがシリアライズ可能であることを明示。
	private static final long serialVersionUID = 1L;
	
	//サーブレットの初期化用コンストラクタ。
	//super() で親クラス（HttpServlet）のコンストラクタを呼び出しています。
	public LoginServlet() {
        super();
	}
	
	//HTTPのGETリクエストを処理するためのメソッド
	//ブラウザでURLを直接開いたり、リンクをクリックしたときに呼び出される
	//HttpServletRequest request: リクエスト情報（送信されたデータやヘッダーなど）を取得する
	//HttpServletResponse response: レスポンス情報（画面に返す内容）を作成する
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//レスポンス（画面に表示する内容）のデータ形式を設定。
		//text/html: HTML形式で返すことを示す。
		response.setContentType("text/html; charset=UTF-8");
//		String action = request.getParameter("action");
//
//        if (action == null || action.isEmpty()) {
//            // デフォルトの処理 (URL直接アクセス時)
//            response.getWriter().println("ログインページへようこそ！");
            request.getRequestDispatcher("top.jsp").forward(request, response);
           
		
		
	}

    // POSTリクエスト処理
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        request.setCharacterEncoding("UTF-8");
//        //top.jspに記載のあるneme属性の中からactionに入った値を取得
//      	//変数名actionに格納
//        String action = request.getParameter("action");
//     	//もしactionが指定されていないor空の場合
//        if (action == null || action.isEmpty()) {
//        	//下記の適当なエラーコードを表示
//            response.getWriter().println("操作が確認できませんでした");
//            return;
//        }
//        //actionに格納されたコードからvalue属性を読込み
//        //値に応じて処理を分岐する
//        switch (action) {
//        	//ユーザー関連の処理を実行するためのメソッドを呼び出す。
//            case "user":
//                handleUserLogin(request, response);
//                break;
//            //管理者関連の処理を実行するためのメソッドを呼び出す。
//            case "admin":
//                handleAdminLogin(request, response);
//                break;
//            //エラーコード
//            default:
//                response.getWriter().println("無効なアクションパラメータです。");
//        }
    }

 
    // ユーザーログイン処理 (POST用)
//    private void handleUserLogin(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//    	//フォームから送信された メールアドレス と パスワード をリクエストから取得。
//        String email = request.getParameter("email");
//        String password = request.getParameter("password");
//
//        //UserDAO（データアクセスオブジェクト） のインスタンスを作成。
//        //ユーザーの認証処理でデータベースにアクセスするために使用。
//        UsersDAO userDAO = new UsersDAO();
//        //DAOを使ってデータベースに問い合わせ、メールアドレスとパスワードが一致するユーザーを取得。
//        //一致するユーザーが存在しない場合、user は null。
//        UsersDTO user = userDAO.findUserByEmailAndPassword(email, password);
//
//        //userの値がnullじゃなないとき
//        if (user != null) {
//        	//ユーザー情報をセッションに保存。
//        	//次回以降、このセッションを使ってログイン状態を管理可能。
//            request.getSession().setAttribute("user", user);
//            //ユーザーダッシュボード（userDashboard.jsp）にリダイレクト。
//            //ログイン成功後にユーザー専用のページを表示。
//            response.sendRedirect("userDashboard.jsp");
//        } else {
//        	//エラーメッセージをリクエストスコープにセット。
//        	//ログイン画面でエラーメッセージを表示するために使用。
//            request.setAttribute("errorMessage", "ユーザーログイン失敗: メールアドレスまたはパスワードが間違っています。");
//            //ログイン画面（top.jsp）に転送
//            request.getRequestDispatcher("/top.jsp").forward(request, response);
//        }
//    }
//
//    // 管理者ログイン処理 (POST用)
//    private void handleAdminLogin(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//    	//フォームから送信された メールアドレス と パスワード をリクエストから取得。
//    	String email = request.getParameter("email");
//        String password = request.getParameter("password");
//
//        //AdminDAO（データアクセスオブジェクト） のインスタンスを作成。
//        //管理者の認証処理でデータベースにアクセスするために使用。
//        AdminsDAO adminDAO = new AdminsDAO();
//        AdminsDTO admin = adminDAO.findAdminByEmailAndPassword(email, password);
//
//        if (admin != null) {
//        	//管理者情報をセッションに保存。
//        	//次回以降、このセッションを使ってログイン状態を管理可能。
//            request.getSession().setAttribute("admin", admin);
//            //管理者ダッシュボード（adminDashboard.jsp）にリダイレクト。
//            //ログイン成功後に管理者専用のページを表示。
//            response.sendRedirect("adminDashboard.jsp");
//        } else {
//        	//エラーメッセージをリクエストスコープにセット。
//        	//ログイン画面でエラーメッセージを表示するために使用。
//            request.setAttribute("errorMessage", "管理者ログイン失敗: メールアドレスまたはパスワードが間違っています。");
//            //ログイン画面（login.jsp）に転送してログインをやり直させる。
//            request.getRequestDispatcher("/top.jsp").forward(request, response);
//        }
//    }
}