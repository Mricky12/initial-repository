<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>タスク管理システム</title>
    <link rel="stylesheet" href="./css/register.css">
    <script src="./js/validation.js" defer></script>
</head>

<body>
    <div class="top-wrapper">
        <header class="header-wrapper">
            <div class="top-header">
                <p>タスク管理システム</p>
                <p>管理者登録</p>
            </div>
        </header>
        <div class="body-wrapper">
            <div class="user-box">
                <form class="register-form" id="registerForm" method="post" action="adminregister">
                    <div class="input-box">
                        <input type="text" class="text" name="user_name" id="name" placeholder="ユーザー名">
                        <p class="error-message" id="nameError"></p>
                    </div>
                    <div class="input-box">
                        <input type="email" class="text" name="user_email" id="email" placeholder="メールアドレス">
                        <p class="error-message" id="emailError"></p>
                    </div>
                    <div class="input-box2">
                        <input type="text" class="text2" name="user_password" id="password" placeholder="パスワード">
                        
                        <input type="text" class="text3" name="confirm_password" id="confirm_password" placeholder="パスワード確認">
                    	
                    </div>
                    <p class="error-message" id="passwordError"></p>
                    <p class="pw-comment">半角英字、数字を組み合わせて8文字以上で<br>
                    入力してください。</p>
                    <button type="button" class="button" value="" onclick="handleValidation()">次へ</button>
                </form>
            </div>
        </div>
    </div>
</body>
</html>
