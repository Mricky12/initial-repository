// タスクに画像添付
document.getElementById('file-input').addEventListener('change', function(event) {
	const file = event.target.files[0];
	if (file) {
		const reader = new FileReader();
		reader.onload = function(e) {
			const imagePreview = document.getElementById('image-preview');
			imagePreview.innerHTML = `<img src="${e.target.result}" alt="添付画像">`;
		};
		reader.readAsDataURL(file);
	}
});

// タスクを閉じる
document.addEventListener("DOMContentLoaded", function() {
	const taskForm = document.querySelector(".task-form");
	const taskFormHeader = document.querySelector(".task-form-header");
	const closeButton = document.querySelector("button.close");
	const taskList = document.getElementById("task-list");

	if (taskList) {
		taskList.style.display = "block";
	}

	taskFormHeader.addEventListener("click", function() {
		taskForm.classList.remove("collapsed");
		taskForm.classList.add("expanded");
	});

	closeButton.addEventListener("click", function() {
		taskForm.classList.remove("expanded");
		taskForm.classList.add("collapsed");
	});

	document.addEventListener("click", function(event) {
		const isInsideForm = taskForm.contains(event.target);
		const isHeaderClick = taskFormHeader.contains(event.target);

		if (!isInsideForm && !isHeaderClick && taskForm.classList.contains("expanded")) {
			taskForm.classList.remove("expanded");
			taskForm.classList.add("collapsed");
		}
	});

	document.querySelectorAll(".save-button").forEach((button) => {
		button.addEventListener("click", () => {
			const taskId = button.dataset.taskId;
			const taskTitle = document.querySelector(`#taskTitle-${taskId}`).value;
			const taskContent = document.querySelector(`#taskContent-${taskId}`).value;

			if (!taskTitle.trim() && !taskContent.trim()) {
				alert("タイトルまたは内容を入力してください。");
				return;
			}

			button.disabled = true;

			const params = new URLSearchParams();
			params.append("action", "update");
			params.append("taskId", taskId);
			params.append("taskTitle", taskTitle);
			params.append("taskContent", taskContent);

			fetch("myselftask", {
				method: "POST",
				headers: {
					"Content-Type": "application/x-www-form-urlencoded",
				},
				body: params.toString(),
			})
				.then(response => {
					if (!response.ok) {
						throw new Error("サーバーエラー");
					}
					return response.text();
				})
				.then(() => {
					alert("タスクが更新されました。");
					window.location.reload();
				})
				.catch(error => {
					console.error("Error:", error);
					alert("タスクの更新に失敗しました。");
				})
				.finally(() => {
					button.disabled = false;
				});
		});
	});
});

// カラーパレット関係
document.addEventListener("DOMContentLoaded", function() {
	const colorButton = document.querySelector(".color");
	const colorPalette = document.getElementById("color-palette");
	const colorOptions = document.querySelectorAll(".color-option");
	const taskForm = document.getElementById("taskForm");
	const taskTitle = document.getElementById("task-title");
	const taskContent = document.getElementById("task-content");
	const selectedColorInput = document.getElementById("selected-color");

	colorButton.addEventListener("click", function() {
		colorPalette.classList.toggle("hidden");
	});

	colorOptions.forEach((option) => {
		option.addEventListener("click", function() {
			const selectedColor = option.getAttribute("data-color");
			const selectedColorId = option.getAttribute("data-id");

			taskForm.style.backgroundColor = selectedColor;
			taskTitle.style.backgroundColor = selectedColor;
			taskContent.style.backgroundColor = selectedColor;

			selectedColorInput.value = selectedColorId;

			colorOptions.forEach((opt) => opt.classList.remove("selected"));
			option.classList.add("selected");
		});
	});
});

// 編集関数
function toggleEditMode(taskId) {
	const taskElement = document.getElementById(`task-${taskId}`);
	const editForm = document.getElementById(`edit-form-${taskId}`);
	const taskTitle = taskElement.querySelector(".task-item-title");
	const taskDetail = taskElement.querySelector(".task-item-detail");
	const editButton = taskElement.querySelector(".edit-btn");

	if (editForm.classList.contains("hidden")) {
		editForm.classList.remove("hidden");
		taskTitle.classList.add("hidden");
		taskDetail.classList.add("hidden");
		editButton.textContent = "キャンセル";
	} else {
		editForm.classList.add("hidden");
		taskTitle.classList.remove("hidden");
		taskDetail.classList.remove("hidden");
		editButton.textContent = "編集";
	}
}

// タスク更新関数
function saveTask(taskId) {
	const editForm = document.getElementById(`edit-form-${taskId}`);
	if (!editForm) {
		alert("編集フォームが見つかりません。");
		return;
	}

	const taskTitleInput = editForm.querySelector('input[name="taskTitle"]');
	const taskContentInput = editForm.querySelector('textarea[name="taskContent"]');
	if (!taskTitleInput || !taskContentInput) {
		alert("タイトルまたは内容が見つかりません。");
		return;
	}

	const newTitle = taskTitleInput.value.trim();
	const newContent = taskContentInput.value.trim();

	const params = new URLSearchParams();
	params.append("action", "update");
	params.append("taskId", taskId);
	params.append("taskTitle", newTitle);
	params.append("taskContent", newContent);

	fetch("myselftask", {
		method: "POST",
		headers: {
			"Authorization": `Bearer ${getAuthToken()}`,
			"Content-Type": "application/x-www-form-urlencoded",
		},
		body: params.toString(),
	})
		.then(response => {
			if (!response.ok) {
				throw new Error(`HTTPエラー: ${response.status}`);
			}
			return response.text();
		})
		.then(() => {
			alert("タスクが更新されました。");
			window.location.reload();
		})
		.catch(error => {
			console.error("エラー:", error);
			alert("タスクの更新中にエラーが発生しました。");
		});
}

// 認証トークン取得
function getAuthToken() {
	return localStorage.getItem("authToken");
}

// タスク作成関数
function createTask(taskTitle, taskContent) {
	const authToken = getAuthToken();

	fetch("myselftask", {
		method: "POST",
		headers: {
			"Content-Type": "application/x-www-form-urlencoded",
			"Authorization": `Bearer ${authToken}`,
		},
		body: `action=create&taskTitle=${encodeURIComponent(taskTitle)}&taskContent=${encodeURIComponent(taskContent)}`,
	})
		.then(response => {
			if (!response.ok) {
				throw new Error("サーバーエラー");
			}
			return response.json();
		})
		.then(data => {
			console.log("タスク作成成功:", data);
		})
		.catch(error => {
			console.error("タスク作成エラー:", error);
		});
}
