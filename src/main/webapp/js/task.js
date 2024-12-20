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