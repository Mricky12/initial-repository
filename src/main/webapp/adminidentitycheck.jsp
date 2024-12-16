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
            <div class="admin-box">
                <form class="reset-form" id="resetForm" method="post" action="adminidentitycheck">
                    <div class="input-box">
                        <p class="check-comment">続行するには、<br>本人確認を行ってください。</p>  
                        
                        <!-- エラーメッセージを表示 -->
    					<c:if test="${not empty error}">
        					<p class="identity-error">${error}</p>
    					</c:if>
                        
                        
                                         
                        <input type="text" class="text" name="admin_name" id="name" placeholder="ユーザー名">
                        <p class="error-message" id="nameError"></p>
                        <input type="email" class="text" name="admin_email" id="email" placeholder="メールアドレス">
                    	<p class="error-message" id="emailError"></p>
                    </div>
                    <button type="button" class="button" value="" onclick="identitycheckValidation()">次へ</button>
                </form>
            </div>


        </div>


    </div>

</body>

</html>