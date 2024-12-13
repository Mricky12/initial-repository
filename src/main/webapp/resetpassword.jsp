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
                <p>
                    タスク管理システム
                </p>
                <p>
                    パスワードを変更
                </p>
            </div>
        </header>
        <div class="body-wrapper">
            <div class="user-box">
                <form class="reset-form" id="resetForm" method="post" action="resetpassword">
                    
                    <div class="input-box">
                        <input type="text" class="text" name="user_password" id="user_password" placeholder="パスワード">
                        <input type="text" class="text" name="confirm_password" id="confirm_password" placeholder="パスワード確認">
                    </div>
                    <p class="error-message" id="passwordError"></p>
            
                    <p class="pw-comment">半角英字、数字を組み合わせて8文字以上で<br>
                        入力してください。</p>

                    <button type="button" class="button" value="" onclick="resetpasswordValidation()">次へ</button>
                </form>
            </div>


        </div>


    </div>

</body>

</html>