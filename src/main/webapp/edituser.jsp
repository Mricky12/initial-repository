<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>タスク管理システム</title>
    <link href="./css/reset.css" rel="stylesheet" type="text/css"/>
    <link href="./css/style.css" rel="stylesheet" type="text/css"/>
    <link href="./css/main.css" rel="stylesheet" type="text/css"/>
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
                    <br>ユーザー編集
                </p>
            </div>
            
            <div class="search">
                <form action="/search" method="get" class="search-form-4">
                    <button type="submit" aria-label="検索"></button>
                    <label>
                        <input type="text"  placeholder="検索" >
                    </label>
                </form>
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
                <a href="" class="icon-link">
                    <img src="images\人物のアイコン素材 その3.png" alt="アカウント">
                </a>
            </div>
        </header> 
        <div class="container">
            <div class="sidebar">
                <ul class="menu">
                    <li><a href="myselftask"><span class="bullet">・</span>マイタスク</a></li>
                    <li><a href="group"><span class="bullet">・</span>グループ作成/編集</a></li>
                    <li><a href="groupmember"><span class="bullet">・</span>グループメンバー編集</a></li>
                    <li><a href="grouptask.jsp"><span class="bullet">・</span>グループタスク一覧</a></li>
                    <li><a href=""><span class="bullet">・</span>ユーザー編集</a></li>
                    <li><a href="logout" id="logout-link"><span class="bullet">・</span>ログアウト</a></li>
                </ul>
            </div>
            <div class="main-content">
                <p>ユーザー編集</p>
                <div class="main-content-child">
                    <div class="form-group">
                        <label for="name">名前</label>
                        <input type="text" id="name" class="form-control" placeholder="">
                    </div>
                    <div class="form-group">
                        <label for="email">メールアドレス</label>
                        <input type="email" id="email" class="form-control" placeholder="">
                    </div>
                    <div class="form-group">
                        <label for="password">パスワード</label>
                        <input type="password" id="password" class="form-control" placeholder="">
                        
                    </div>
                    <small class="form-text">半角英字、数字を組み合わせて8文字以上で入力してください。</small>
                </div>
                <div class="button-container">
                    <button class="bule-button" type="button">変更</button>
                </div>
                <hr><p>ユーザー退会</p>
                <div class="main-content-child">
                    <a class="unsubscribe" href="">退会する</a>
                </div>
            </div>
        </div>    
        
    </div>
    <script src="./js/script.js"></script>
</body>
</html>