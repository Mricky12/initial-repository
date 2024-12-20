 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ page import="java.util.List" %>   
 <%@ page import="task.dto.GroupsDTO" %>
    

   
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
                    <br>グループページ
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
                <a href="./edituser.jsp" class="icon-link">
                    <img src="images\人物のアイコン素材 その3.png" alt="アカウント">
                </a>
            </div>
        </header> 
        <div class="container">
            <div class="sidebar">
                <ul class="menu">
                    <li><a href="myselftask.jsp"><span class="bullet">・</span>マイタスク</a></li>
                    <li><a href="#"><span class="bullet">・</span>グループ作成/編集</a></li>
                    <li><a href="groupmember.jsp"><span class="bullet">・</span>グループメンバー編集</a></li>
                    <li><a href="grouptask.jsp"><span class="bullet">・</span>グループタスク一覧</a></li>
                    <li><a href="edituser.jsp"><span class="bullet">・</span>ユーザー編集</a></li>
                    <li><a href="#logout" id="logout-link"><span class="bullet">・</span>ログアウト</a></li>
                </ul>
            </div>
            <script>
            	/* 成功時にはアラートが表示 */
    			document.addEventListener("DOMContentLoaded", function () {
        			const urlParams = new URLSearchParams(window.location.search);
        			const success = urlParams.get("success");
        			const groupName = urlParams.get("groupName");

        			if (success === "true" && groupName) {
            			const decodedGroupName = decodeURIComponent(groupName);
            			alert(`グループ「${decodedGroupName}」が正常に作成されました。`);
            			window.location.href = "groupmemberedit.jsp"; // 作成完了後に遷移
        			}

        			const errorMessage = document.getElementById("error-message").dataset.error;
        			if (errorMessage) {
            			alert(errorMessage);
        			}
    			});
			</script>
            
            <div class="main-content">
                <p>グループ作成</p>
                <%-- エラーメッセージをJavaScriptで処理するため、データ属性として設定 --%>
    			<div id="error-message" data-error="<%= request.getAttribute("error") != null ? request.getAttribute("error") : "" %>"></div>
    			
    			
                <form action="group" method="post" class="main-content-child">
                	
                    	<div class="form-group">
                        	<label for="groupname">グループ名</label>
                        	<input type="text" id="groupname" name="group_name" class="form-control" placeholder="">
                    	</div>
                	
                	<div class="button-container">
                    	<button class="cancel-button" type="button" onclick="window.location.href=''">キャンセル</button>
                    	<button class="bule-button search-button" type="submit">作成</button>
                	</div>
             	</form>
          
            </div>
            <div class="main-content">
                <p>グループ名変更</p>
                <div class="main-content-child">
                    <div class="form-group">
                        <select name="groupId" >
                            <option value="" selected>▼グループ選択</option>
                    		<%
                    		// グループリストをリクエストから取得
                   			List<GroupsDTO> groups = (List<GroupsDTO>) request.getAttribute("groups");
                    		if (groups != null && !groups.isEmpty()) {
                        		for (GroupsDTO group : groups) {
                        			// デバッグ出力
                                    System.out.println("JSPで取得したグループID: " + group.getGroupId() + ", グループ名: " + group.getGroupName());

                    		%>
                        		<option value="<%= group.getGroupId() %>"><%= group.getGroupName() %></option>
                    		<%
                        		}
                    		} else {
                   	 		%>
                        		<option value="">グループがありません</option>
                    		<%
                    		}
                    		%>
                      
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="name">新しい名前</label>
                        <input type="name" id="name" class="form-control" placeholder="">
                    </div>
                </div>
                <div class="button-container">
                    <button class="cancel-button" type="button">キャンセル</button>
                    <button class="bule-button search-button" type="button">変更</button>
                </div>
            </div>
            <div class="main-content">
                <p>グループ削除</p>
                <div class="main-content-child">
                    <div class="form-group">
                        <select name="groupId" >
                            <option value="" selected>▼グループ選択</option>
                    		<%
                    		// グループリストをリクエストから取得
                   			List<GroupsDTO> groups = (List<GroupsDTO>) request.getAttribute("groups");
                    		if (groups != null && !groups.isEmpty()) {
                        		for (GroupsDTO group : groups) {
                        			// デバッグ出力
                                    System.out.println("JSPで取得したグループID: " + group.getGroupId() + ", グループ名: " + group.getGroupName());

                    		%>
                        		<option value="<%= group.getGroupId() %>"><%= group.getGroupName() %></option>
                    		<%
                        		}
                    		} else {
                   	 		%>
                        		<option value="">グループがありません</option>
                    		<%
                    		}
                    		%>
                      
                        </select>
                    </div>
                </div>
                <div class="button-container">
                    <button class="cancel-button" type="button">キャンセル</button>
                    <button class="bule-button search-button" type="button">削除</button>
                </div>
            </div>
        </div>    
        
    </div>
    <script src="./js/script.js"></script>
    <script src="./js/group_create.js" defer></script>
</body>
</html>