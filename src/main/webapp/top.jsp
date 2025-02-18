<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ja">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>タスク管理システム</title>
    <link rel="stylesheet" href="./css/top.css">
    <script src="./js/validation.js" defer></script>
</head>

<body>
    <div class="top-wrapper">
        <header class="header-wrapper">
            <div class="top-header">
                <p>
                    タスク管理システム
                </p>
            </div>
        </header>
        <div class="body-wrapper">
            <!-- ユーザーログイン -->
            <div class="user-box">
                <form method="post" action="top">
                    <p>ユ ー ザ ー<br>ロ グ イ ン</p>
                    <div class="input-box">
                    
                    <!-- エラーメッセージを表示 -->
    					<c:if test="${not empty userError}">
    						<p class="identity-error">${userError}</p>
						</c:if>
                    
                    
                        <input type="email" class="text" name="email" placeholder="メールアドレス" required><br>
                        <input type="password" class="text" name="password" placeholder="パスワード" required><br>
                    </div>
                    <a href="register">ユーザー登録はこちら</a><br>
                    <a href="identitycheck">パスワードをお忘れの方</a><br>
                    <button type="submit" class="button" value="">次へ</button>
                    <input type="hidden" name="action" value="user" >
                </form>
            </div>

            <!-- 管理者ログイン -->
            <div class="admin-box">
                <form method="post" action="top" id="adminLoginForm">
                    <p>管 理 者<br>ロ グ イ ン</p>
                    <div class="input-box">
                    
                    <!-- エラーメッセージを表示 -->
    					<c:if test="${not empty adminError}">
    						<p class="identity-error">${adminError}</p>
						</c:if>
                    
                    
                        <input type="email" class="text" name="email" placeholder="メールアドレス" required><br>
                        <input type="password" class="text" name="password" placeholder="パスワード" required><br>
                    </div>
                    <a href="adminregister" onclick="return validateAdmin()">管理者登録はこちら</a><br>
                    <a href="adminidentitycheck" onclick="return validateAdmin()">パスワードをお忘れの方</a><br>
                    <button type="submit" class="button" value="" onclick="return validateAdmin()">次へ</button>
                    <input type="hidden" name="action" value="admin" >
                </form>
            </div>
        </div>
    </div>
</body>

</html>
