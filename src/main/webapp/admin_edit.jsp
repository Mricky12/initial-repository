<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>タスク管理システム</title>
    <link href="./css/admin.css" rel="stylesheet" type="text/css"/>
</head>
<body>
    <div class="main-wrapper">
        <header class="main-header">
            <div class="logo">
                <!-- ハンバーガーメニューアイコン -->
                <div class="hamburger" id="hamburger">
                    <span></span>
                    <span></span>
                    <span></span>
                </div>
            </div>
            <div class="header-title">
                <p>
                    タスク管理システム
                    <br>管理者ページ
                </p>
            </div>
            
            <div class="search">
               
            </div>
            <div class="icon-container">
                <a href="" class="icon-link">
                    <img src="images\リロードのフリーアイコン.png" alt="リロード">
                </a>
                <a href="" class="icon-link">
                    <img src="images\narabikae.png" alt="リスト表示">
                </a>
                <a href="" class="icon-link">
                    <img src="images\無料の設定歯車アイコン.png" alt="設定">
                </a>
            </div>
            <div class="icon-container">
                <a href="" class="icon-link">
                    <img src="images\メニューの無料アイコン4.png" alt="ホーム">
                </a>
                <a href="./admin_edit.jsp" class="icon-link">
                    <img src="images\人物のアイコン素材 その3.png" alt="アカウント">
                </a>
            </div>
        </header> 
        <div class="container">
            <div class="sidebar">
                <ul class="menu">
                    <li><a href="admin_usersearch.jsp"><span class="bullet">・</span>ユーザー検索</a></li>
                    <li><a href="admin_edit.jsp"><span class="bullet">・</span>管理者編集</a></li>
                    <li><a href="#logout" id="logout-link"><span class="bullet">・</span>ログアウト</a></li>
                </ul>
            </div>
            <div class="main-content">
                <p class="main-p">管理者編集</p>
                
                	<form action="admin_edit" method="post" id="editForm">
                        <input type="hidden" name="action" value="edit">
                        <div class="main-content-child">
		                    <div class="form-group">
		                        <label for="name">名前</label>
		                        <input type="text" id="name" class="form-control" placeholder="">
		                        
		                    </div>
		                    <p class="error-message" id="nameError"></p>
		                    <div class="form-group">
		                        <label for="email">メールアドレス</label>
		                        <input type="email" id="email" class="form-control" placeholder="">
		                        
		                    </div>
		                    <p class="error-message" id="emailError"></p>
		                    <div class="form-group">
		                        <label for="password">パスワード</label>
		                        <input type="password" id="password" class="form-control" placeholder="" autocomplete="current-password">
		                        		                        
		                    </div>
		                    <p class="error-message" id="passwordError"></p>
		                    <small class="form-text">半角英字、数字を組み合わせて8文字以上で入力してください。</small>
	                		</div>
			                <div class="button-container">
			                    <button class="bule-button" type="button" onclick="editValidation()">変更</button>
			                </div>
						</form>
		                <hr>
		                <p class="main-p">管理者退会</p>
		                <div class="main-content-child">
		                	 <!-- 退会フォーム -->
                    		<form action="admin_edit" method="post" onsubmit="return confirm('本当に退会しますか？')">
                        		<input type="hidden" name="action" value="delete">
		                    	<button type="submit" class="unsubscribe">退会する</button>
		                    </form>
		                </div>
            </div>
           
        </div>    
        
    </div>
    <script src="./js/script.js "></script>
    <script src="./js/validation.js "></script>
</body>
</html>