<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>タスク管理システム</title>

    <!-- <link rel="stylesheet" href="./reset.css"> -->
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
                <form class="reset-form" id="resetForm" method="post" action="identitycheck">
                    <div class="input-box">
                        <p class="check-comment">続行するには、<br>本人確認を行ってください。</p>                   
                        <input type="text" class="text" name="user_name" id="user_name" placeholder="ユーザー名">
                        <p class="error-message" id="nameError"></p>
                        <input type="email" class="text" name="user_email" id="user_email" placeholder="メールアドレス">
                    	<p class="error-message" id="emailError"></p>
                    </div>
                    <button type="button" class="button" value="" onclick="identitycheckValidation()">次へ</button>
                </form>
            </div>


        </div>


    </div>

</body>

</html>