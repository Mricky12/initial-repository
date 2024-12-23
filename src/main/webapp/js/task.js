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

	// 「タスクを追加…」クリックでタスク追加欄を開く
	taskFormHeader.addEventListener("click", function() {
		taskForm.classList.remove("collapsed");
		taskForm.classList.add("expanded");
	});

	// 閉じるボタンでタスク追加欄を閉じる
	closeButton.addEventListener("click", function() {
		// 閉じるボタンが正しく取得されているか確認
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



function toggleEditMode(taskId) {
	// タスク表示エリアの取得
	const taskElement = document.getElementById(`task-${taskId}`);
	const taskTitleElement = taskElement.querySelector(".task-title");
	const taskDetailElement = taskElement.querySelector(".task-content");
	const editButton = taskElement.querySelector(".edit-btn");
	const saveButton = taskElement.querySelector(".save-btn");

	// すでに編集モードの場合は処理しない
	if (taskElement.dataset.editing === "true") return;

	// 編集モードに切り替え
	taskElement.dataset.editing = "true";

	// 現在のタイトルと詳細を取得
	const currentTitle = taskTitleElement.textContent.trim();
	const currentDetail = taskDetailElement.textContent.trim();

	// タイトルと内容を編集可能なフォームに切り替え
	taskTitleElement.innerHTML = `<input type="text" id="edit-title-${taskId}" value="${currentTitle}" class="edit-input">`;
	taskDetailElement.innerHTML = `<textarea id="edit-content-${taskId}" class="edit-textarea">${currentDetail}</textarea>`;

	// ボタンの表示/非表示を切り替え
	editButton.classList.add("hidden");
	saveButton.classList.remove("hidden");
}

function saveTask(taskId) {
	const taskTitleInput = document.getElementById(`edit-title-${taskId}`);
	const taskContentInput = document.getElementById(`edit-content-${taskId}`);

	const newTitle = taskTitleInput.value.trim();
	const newContent = taskContentInput.value.trim();

	// バリデーション：タイトルと内容が空の場合は警告
	if (!newTitle && !newContent) {
		alert("タスクタイトルまたは内容を入力してください。");
		return;
	}

	// データ送信用のフォームデータ作成
	const formData = new FormData();
	formData.append("action", "update");
	formData.append("taskId", taskId);
	formData.append("taskTitle", newTitle);
	formData.append("taskContent", newContent);

	// AJAXリクエスト
	fetch("myselftask", {
		method: "POST",
		body: formData,
	})
		.then((response) => {
			if (response.ok) {
				alert("タスクが更新されました。");
				location.reload(); // 更新後にページをリロード
			} else {
				throw new Error("サーバー側でエラーが発生しました。");
			}
		})
		.catch((error) => {
			console.error("Error:", error);
			alert("タスクの更新に失敗しました。");
		});
}


