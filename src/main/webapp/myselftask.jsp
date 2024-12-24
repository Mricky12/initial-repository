<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="task.dto.TaskDTO"%>
<%@ page import="java.util.List"%>

<%
List<TaskDTO> taskList = (List<TaskDTO>) request.getAttribute("taskList");
%>

<!DOCTYPE html>
<html lang="ja">

<head>
<meta charset="UTF-8">
<title>タスク管理システム</title>
<link rel="stylesheet" href="css/task.css">
<link rel="stylesheet" href="css/style.css">
Authorization: Bearer
<your_token>
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
				<a href="" class="icon-link"> <img src="images/リロードのフリーアイコン.png"
					alt="リロード">
				</a> <a href="" class="icon-link"> <img src="images/narabikae.png"
					alt="リスト表示">
				</a> <a href="" class="icon-link"> <img src="images/無料の設定歯車アイコン.png"
					alt="設定">
				</a>
			</div>

			<div class="icon-container">
				<a href="" class="icon-link"> <img src="images/メニューの無料アイコン4.png"
					alt="ホーム">
				</a> <a href="" class="icon-link"> <img
					src="images/人物のアイコン素材 その3.png" alt="アカウント">
				</a>
			</div>
		</header>



		<div class="container">
			<!-- サイドバー -->
			<div class="sidebar">
				<ul class="menu">
					<li><a href="#"><span class="bullet">・</span>マイタスク</a></li>
					<li><a href="group"><span class="bullet">・</span>グループ作成/編集</a></li>
					<li><a href="groupmember"><span class="bullet">・</span>グループメンバー編集</a></li>
					<li><a href="grouptask"><span class="bullet">・</span>グループタスク一覧</a></li>
					<li><a href="edituser"><span class="bullet">・</span>ユーザー編集</a></li>
					<li id="logout-link"><a href="java_task/top.jsp"><span
							class="bullet">・</span>ログアウト</a></li>
				</ul>
			</div>



			<!-- タスク追加 -->
			<div class="main-container">
				<form action="myselftask" method="post"
					enctype="multipart/form-data">
					<input type="hidden" name="action" value="create">

					<div class="task-form collapsed" id="taskForm">
						<!-- 折り畳み時に見せる部分 -->
						<div class="task-form-header" id="taskFormHeader">
							<p>タスクを追加...</p>
						</div>

						<!-- タスク記入欄 -->
						<div class="task-input">
							<input type="text" name="taskTitle" id="task-title"
								placeholder="タイトル">
							<textarea name="taskContent" id="task-content"
								placeholder="タスクを追加…"></textarea>
						</div>

						<!-- タスク記入欄内アイコン -->
						<div class="task-icons">
							<button type="button" class="color"
								onclick="toggleColorPalette()">
								<img src="images/パレットのアイコン5.png">
							</button>
							<input type="file" id="file-input" name="taskImage"
								accept="image/*" style="display: none;">
							<button type="button" class="document" name="taskImage"
								onclick="document.getElementById('file-input').click();">
								<img src="images/写真のフリーアイコン5.png">
							</button>
							<button type="button">
								<img src="images/メニューの無料アイコン9.png">
							</button>
							<button type="button" class="close">閉じる</button>
							<button type="submit" class="task-submit">登録</button>
						</div>

						<!-- カラーパレットの表示エリア -->
						<input type="hidden" id="selected-color" name="colorId" value="">

						<div id="color-palette" class="color-palette hidden">
							<div class="color-option" data-color="#FFFFFF" data-id="1"
								style="background-color: #FFFFFF;" title="白"></div>
							<div class="color-option" data-color="#ADD8E6" data-id="2"
								style="background-color: #ADD8E6;" title="青"></div>
							<div class="color-option" data-color="#FFB6C1" data-id="3"
								style="background-color: #FFB6C1;" title="赤"></div>
							<div class="color-option" data-color="#FFFBB9" data-id="4"
								style="background-color: #FFFBB9;" title="黄色"></div>
							<div class="color-option" data-color="#90EE90" data-id="5"
								style="background-color: #90EE90;" title="緑"></div>
							<div class="color-option" data-color="#D8BFD8" data-id="6"
								style="background-color: #D8BFD8;" title="紫"></div>
							<div class="color-option" data-color="#FFDAB9" data-id="7"
								style="background-color: #FFDAB9;" title="オレンジ"></div>
						</div>

					</div>
				</form>
			</div>
		</div>



		<!-- 登録されたタスク一覧 -->
		<div class="task">
			<%
			if (taskList != null && !taskList.isEmpty()) {
				for (TaskDTO task : taskList) {
			%>

			<!-- タスク表示 -->
			<div class="task-list" id="task-<%=task.getTaskId()%>"
				style="background-color:<%=task.getColorCode() != null ? task.getColorCode() : "#FFFFFF"%>;">
				<!-- タイトル -->
				<p class="task-item-title"><%=task.getTaskTitle() != null ? task.getTaskTitle() : "タイトルなし"%></p>
				<!-- 詳細 -->
				<p class="task-item-detail"><%=task.getTask() != null ? task.getTask() : "詳細なし"%></p>

				<!-- 編集フォーム部分（非表示） -->
				<form action="myselftask" method="post" class="hidden"
					id="edit-form-<%=task.getTaskId()%>">
					<input type="hidden" name="action" value="update"> <input
						type="hidden" name="taskId" value="<%=task.getTaskId()%>">
					<input type="text" name="taskTitle"
						value="<%=task.getTaskTitle()%>">
					<textarea name="taskContent" name="task"><%=task.getTask()%></textarea>
				</form>


				<!-- ボタン部分 -->
				<div class="task-list-btn">
					<!-- 編集ボタン -->
					<button class="edit-btn"
						onclick="toggleEditMode(<%=task.getTaskId()%>)">
						<img src="images/鉛筆アイコン　6.png" alt="編集">
					</button>

					<!-- 保存ボタン -->
					<button class="save-btn hidden"
						onclick="saveTask(<%=task.getTaskId()%>)">
						<img src="images/鉛筆アイコン　6.png" alt="保存">
					</button>

					<!-- 削除ボタン -->
					<form name="deleteForm" method="post" action="myselftask">
						<input type="hidden" name="action" value="delete"> <input
							type="hidden" name="taskId" value="<%=task.getTaskId()%>">
						<button class="delete-btn">
							<img src="images/スタンダードなゴミ箱アイコン.png" alt="削除">
						</button>
					</form>
				</div>
			</div>

			<%
			}
			%>

			<%
			} else {
			%>

			<!-- タスクがない場合 -->
			<div class="no-tasks-message">
				<p>タスクはありません。</p>
			</div>

			<%
			}
			%>
		</div>

	</div>


	<!-- javascript -->
	<script src="js/script.js"></script>
	<script src="js/task.js"></script>
</body>

</html>