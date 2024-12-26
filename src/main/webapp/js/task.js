// タスクに画像添付
document.getElementById('file-input').addEventListener('change', function(event) {
	const file = event.target.files[0];
	if (file) {
		const reader = new FileReader();
		reader.onload = function(e) {
			const imagePreview = document.getElementById('image-preview');
			imagePreview.innerHTML = `<img src="${e.target.result}" alt="添付画像">`;
		}
		reader.readAsDataURL(file);
	}
});



// タスクを閉じる
document.addEventListener("DOMContentLoaded", function() {
	// タスクフォームの取得
	const taskForm = document.querySelector(".task-form");
	// 「タスクを追加…」部分
	const taskFormHeader = document.querySelector(".task-form-header");
	// 閉じるボタンの取得
	const closeButton = document.querySelector("button.close");

	const taskList = document.getElementById("task-list");
	if (taskList) {
		// 初期表示を確保
		taskList.style.display = "block";
	}

	// 「タスクを追加…」クリックでタスク追加欄を開く
	taskFormHeader.addEventListener("click", function() {
		taskForm.classList.remove("collapsed");
		taskForm.classList.add("expanded");
	});

	// 閉じるボタンでタスク追加欄を閉じる
	closeButton.addEventListener("click", function() {
		taskForm.classList.remove("expanded");
		taskForm.classList.add("collapsed");
	});

	// 外部クリックで折り畳み
	document.addEventListener("click", function(event) {
		const isInsideForm = taskForm.contains(event.target);
		const isHeaderClick = taskFormHeader.contains(event.target);

		if (!isInsideForm && !isHeaderClick && taskForm.classList.contains("expanded")) {
			taskForm.classList.remove("expanded");
			taskForm.classList.add("collapsed");
		}
	});

	// 保存ボタンをクリックした際のイベント
	document.querySelectorAll(".save-button").forEach((button) => {
		button.addEventListener("click", () => {
			const taskId = button.dataset.taskId; // 保存ボタンに付与されたタスクID
			const taskTitle = document.querySelector(`#taskTitle-${taskId}`).value; // テキストボックスの値
			const taskContent = document.querySelector(`#taskContent-${taskId}`).value;

			// バリデーション：データが空かどうか確認
			if (!taskTitle.trim() && !taskContent.trim()) {
				alert("タイトルまたは内容を入力してください。");
				return;
			}

			// ボタンを無効化して二重送信を防ぐ
			button.disabled = true;

			// AJAXリクエストを送信
			fetch("myselftask", {
				method: "POST",
				headers: {
					"Content-Type": "application/x-www-form-urlencoded",
				},
				body: `action=update&taskId=${encodeURIComponent(taskId)}&taskTitle=${encodeURIComponent(taskTitle)}&taskContent=${encodeURIComponent(taskContent)}`
			})
				.then(response => {
					if (response.ok) {
						return response.text();
					}
					throw new Error("サーバーエラー");
				})
				.then(() => {
					alert("タスクが更新されました。");
					window.location.reload(); // 更新後にページをリロード
				})
				.catch(error => {
					console.error("Error:", error);
					alert("タスクの更新に失敗しました。");
				})
				.finally(() => {
					// 成功・失敗に関わらずボタンを再度有効化
					button.disabled = false;
				});
		});
	});
});

// カラーパレット関係
document.addEventListener("DOMContentLoaded", function() {
	// カラーボタン
	const colorButton = document.querySelector(".color");
	// カラーパレット
	const colorPalette = document.getElementById("color-palette");
	// 各色オプション
	const colorOptions = document.querySelectorAll(".color-option");
	// タスクフォーム全体
	const taskForm = document.getElementById("taskForm");
	// タスク記入欄の要素
	const taskTitle = document.getElementById("task-title");
	const taskContent = document.getElementById("task-content");
	// 選択した色IDを格納するhidden input
	const selectedColorInput = document.getElementById("selected-color");

	// カラーパレットの表示/非表示を切り替え
	colorButton.addEventListener("click", function() {
		colorPalette.classList.toggle("hidden");
	});

	// 選んだ色をタスク記入欄とhidden inputに適用
	colorOptions.forEach((option) => {
		option.addEventListener("click", function() {
			// 選んだ色を取得
			const selectedColor = option.getAttribute("data-color");
			const selectedColorId = option.getAttribute("data-id"); // 色ID

			// タスクコンテナ全体、タスク記入欄に背景色を適用
			taskForm.style.backgroundColor = selectedColor;
			taskTitle.style.backgroundColor = selectedColor;
			taskContent.style.backgroundColor = selectedColor;

			// hidden input に選んだ色IDを設定 (サーバー送信用)
			selectedColorInput.value = selectedColorId;

			// 選択中の色オプションを強調表示
			colorOptions.forEach((opt) => opt.classList.remove("selected"));
			option.classList.add("selected");
		});
	});
});

//編集関数
function toggleEditMode(taskId) {
	// タスク要素を取得
	const taskElement = document.getElementById(`task-${taskId}`);
	const editForm = document.getElementById(`edit-form-${taskId}`);
	const taskTitle = taskElement.querySelector(".task-item-title");
	const taskDetail = taskElement.querySelector(".task-item-detail");
	const editButton = taskElement.querySelector(".edit-btn");

	if (editForm.classList.contains("hidden")) {
		// 編集モードを表示
		editForm.classList.remove("hidden");
		taskTitle.classList.add("hidden");
		taskDetail.classList.add("hidden");
		editButton.textContent = "キャンセル";
	} else {
		// 編集モードを非表示
		editForm.classList.add("hidden");
		taskTitle.classList.remove("hidden");
		taskDetail.classList.remove("hidden");
		editButton.textContent = "編集";
	}
}

//タスク更新関数
function saveTask(taskId) {
	// フォーム要素を取得
	const editForm = document.getElementById(`edit-form-${taskId}`);
	if (!editForm) {
		console.error(`Edit form not found for task ID: ${taskId}`);
		alert("編集フォームが見つかりません。");
		return;
	}

	// フォーム内のタイトルと内容の入力要素を取得
	const taskElement = document.getElementById(`task-${taskId}`);
	const taskTitleInput = editForm.querySelector('input[name="taskTitle"]');
	const taskContentInput = editForm.querySelector('textarea[name="taskContent"]');

	// 要素が見つからない場合のエラー確認
	if (!taskTitleInput) {
		console.error(`Task title input not found for task ID: ${taskId}`);
		alert("タスクタイトルの入力フィールドが見つかりません。");
		return;
	}
	if (!taskContentInput) {
		console.error(`Task content input not found for task ID: ${taskId}`);
		alert("タスク内容の入力フィールドが見つかりません。");
		return;
	}


	// 各フィールドの値を取得
	const newTitle = taskTitleInput.value.trim();
	const newContent = taskContentInput.value.trim();


	// データ送信用のフォームデータ作成
	const formData = new FormData();
	formData.append("action", "update");
	formData.append("taskId", taskId);
	formData.append("taskTitle", newTitle);
	formData.append("taskContent", newContent);

	// 認証トークンなどが必要なら設定
	const token = 'your_token_here'; // 必要に応じて動的に取得する方法を実装
	const headers = token ? { 'Authorization': `Bearer ${token}` } : {};

	// サーバーに送信
	fetch("myselftask", {
		method: "POST",
		body: formData,
	})
		.then(response => {
			if (response.ok) {
				return response.text(); // 必要に応じてJSONを使う
			} else {
				throw new Error(`HTTPエラー: ${response.status}`);
			}
		})
		.then(data => {
			// タスク表示を更新
			const taskTitle = taskElement.querySelector(".task-item-title");
			const taskDetail = taskElement.querySelector(".task-item-detail");
			taskTitle.textContent = newTitle;
			taskDetail.textContent = newContent;

			// 編集モードを終了
			toggleEditMode(taskId);
			alert("タスクが更新されました。");
		})
		.catch(error => {
			console.error("エラー:", error);
			alert("タスクの更新中にエラーが発生しました。");
		});
}

// 認証トークンを取得する関数（例）
function getAuthToken() {
	// ここでトークンを取得するロジックを実装します
	// 例えば、ローカルストレージやクッキーから取得することができます
	return localStorage.getItem('authToken'); // 例: localStorageから取得
}

// タスクを作成する関数
function createTask(taskTitle, taskContent) {
	const authToken = getAuthToken(); // 認証トークンを取得

	fetch("myselftask", {
		method: "POST",
		headers: {
			"Content-Type": "application/x-www-form-urlencoded",
			"Authorization": `Bearer ${authToken}` // 認証トークンを追加
		},
		body: `action=create&taskTitle=${encodeURIComponent(taskTitle)}&taskContent=${encodeURIComponent(taskContent)}`
	})
		.then(response => {
			if (!response.ok) {
				throw new Error('Network response was not ok');
			}
			return response.json();
		})
		.then(data => {
			console.log('Success:', data);
		})
		.catch((error) => {
			console.error('Error:', error);
		});
}
