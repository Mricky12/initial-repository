<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ja">

<head>
<meta charset="UTF-8">
<title>タスク管理システム</title>
<link href="./css/reset.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="css/task.css">
<link href="./css/style.css" rel="stylesheet" type="text/css" />
</head>

<body>
	<div class="main-wrapper">
	
		<!-- ヘッダー -->
		<header class="main-header">
			<div class="logo">
				<!-- ハンバーガーメニューアイコン -->
				<div class="hamburger" id="hamburger">
					<span></span> <span></span> <span></span>
				</div>
			</div>

			<div class="header-title">
				<p>
					タスク管理システム <br>マイタスク
				</p>
			</div>

			<!-- 検索 -->
			<div class="search">
				<form action="/search" method="get" class="search-form-4">
					<button type="submit" aria-label="検索"></button>
					<label> <input type="text" placeholder="検索">
					</label>
				</form>
			</div>

			<!-- ヘッダーアイコン -->
			<div class="icon-container">
				<a href="" class="icon-link"> <img src="images\リロードのフリーアイコン.png"
					alt="リロード">
				</a> <a href="" class="icon-link"> <img src="images\narabikae.png"
					alt="リスト表示">
				</a> <a href="" class="icon-link"> <img src="images\無料の設定歯車アイコン.png"
					alt="設定">
				</a>
			</div>

			<div class="icon-container">
				<a href="" class="icon-link"> <img src="images\メニューの無料アイコン4.png"
					alt="ホーム">
				</a> <a href="" class="icon-link"> <img
					src="images\人物のアイコン素材 その3.png" alt="アカウント">
				</a>
			</div>
		</header>

		<div class="container">
			<!-- サイドバー -->
			<div class="sidebar">
				<ul class="menu">
					<li><a href="#"><span class="bullet">・</span>マイタスク</a></li>
					<li><a href="groupcreate.html"><span class="bullet">・</span>グループ作成/編集</a></li>
					<li><a href="groupmemberedit.html"><span class="bullet">・</span>グループメンバー編集</a></li>
					<li><a href="grouptask.html"><span class="bullet">・</span>グループタスク一覧</a></li>
					<li><a href="edituser.html"><span class="bullet">・</span>ユーザー編集</a></li>
					<li id="logout-link"><a href="#logout"><span
							class="bullet">・</span>ログアウト</a></li>
				</ul>
			</div>



			<!-- タスク追加 -->
			<div class="task-container">
				<div class="task-form collapsed" id="taskForm">
					<!-- 折り畳み時に見せる部分 -->
					<div class="task-form-header" id="taskFormHeader">
						<p>タスクを追加...</p>
					</div>

					<!-- タスク記入欄 -->
					<div class="task-input">
						<input type="text" id="task-title" placeholder="タイトル">
						<textarea id="task-content" placeholder="タスクを追加…"></textarea>
					</div>

					<!-- タスク記入欄内アイコン -->
					<div class="task-icons">
						<button class="color" onclick="toggleColorPalette()">
							<img src="images/パレットのアイコン5.png">
						</button>
						<input type="file" id="file-input" accept="image/"
							style="display: none;">
						<button class="document"
							onclick="document.getElementById('file-input').click();">
							<img src="images/写真のフリーアイコン5.png">
						</button>
						<button>
							<img src="images/メニューの無料アイコン9.png">
						</button>
						<button class="close">閉じる</button>
						<button class="task-submit">登録</button>
					</div>


					<!-- カラーパレットの表示エリア -->
					<div id="color-palette" class="color-palette hidden">
						<div class="color-option" data-color="#FFFFFF"
							style="background-color: #FFFFFF;" title="白"></div>
						<div class="color-option" data-color="#ADD8E6"
							style="background-color: #ADD8E6;" title="青"></div>
						<div class="color-option" data-color="#FFB6C1"
							style="background-color: #FFB6C1;" title="赤"></div>
						<div class="color-option" data-color="#FFFBB9"
							style="background-color: #FFFBB9;" title="黄色"></div>
						<div class="color-option" data-color="#90EE90"
							style="background-color: #90EE90;" title="緑"></div>
						<div class="color-option" data-color="#D8BFD8"
							style="background-color: #D8BFD8;" title="紫"></div>
						<div class="color-option" data-color="#FFDAB9"
							style="background-color: #FFDAB9;" title="オレンジ"></div>
					</div>

				</div>



				<!-- 登録されたタスク -->
				<div class="task">
					<!-- 例1 -->
					<div class="task-list">
						<p class="task-item-title">タイトルテスト</p>
						<p class="task-item-detail">タスク内容テスト</p>

						<!-- 編集ボタン -->
						<div class="task-list-btn">
							<p>
								<button class="edit-btn">
									<img src="images/鉛筆アイコン　6.png" alt="編集">
								</button>
							</p>

							<!-- 削除ボタン -->
							<form name="deleteForm" method="post" action="">
								<p>
									<button class="delete-btn">
										<img src="images/スタンダードなゴミ箱アイコン.png"
											alt="削除">
									</button>
								</p>
							</form>
						</div>
					</div>

					<!-- 例2 -->
					<div class="task-list">
						<p class="task-item-title">タイトルテスト</p>
						<p class="task-item-detail">タスク内容テスト</p>

						<!-- 編集ボタン -->
						<div class="task-list-btn">
							<p>
								<button class="edit-btn">
									<img src="images/鉛筆アイコン　6.png" alt="編集">
								</button>
							</p>

							<!-- 削除ボタン -->
							<form name="deleteForm" method="post" action="">
								<p>
									<button class="delete-btn">
										<img src="images/スタンダードなゴミ箱アイコン.png"
											alt="削除">
									</button>
								</p>
							</form>
						</div>
					</div>

					<!-- 例3 -->
					<div class="task-list">
						<p class="task-item-title">タイトルテスト</p>
						<p class="task-item-detail">タスク内容テスト</p>

						<!-- 編集ボタン -->
						<div class="task-list-btn">
							<p>
								<button class="edit-btn">
									<img src="images/鉛筆アイコン　6.png" alt="編集">
								</button>
							</p>

							<!-- 削除ボタン -->
							<form name="deleteForm" method="post" action="">
								<p>
									<button class="delete-btn">
										<img src="images/スタンダードなゴミ箱アイコン.png"
											alt="削除">
									</button>
								</p>
							</form>
						</div>
					</div>

					<!-- 例4 -->
					<div class="task-list">
						<p class="task-item-title">タイトルテスト</p>
						<p class="task-item-detail">タスク内容テスト</p>

						<!-- 編集ボタン -->
						<div class="task-list-btn">
							<p>
								<button class="edit-btn">
									<img src="images/鉛筆アイコン　6.png" alt="編集">
								</button>
							</p>

							<!-- 削除ボタン -->
							<form name="deleteForm" method="post" action="">
								<p>
									<button class="delete-btn">
										<img src="images/スタンダードなゴミ箱アイコン.png"
											alt="削除">
									</button>
								</p>
							</form>
						</div>
					</div>

				</div>

			</div>
		</div>
	</div>

	<!-- javascript -->
	<script src="js/script.js"></script>
	<script src="js/task.js"></script>
</body>

</html>