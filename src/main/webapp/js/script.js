
document.addEventListener('DOMContentLoaded', function() {
	// ハンバーガーメニューとメニューの要素を取得
	const hamburger = document.getElementById('hamburger');
	const menu = document.querySelector('.menu');
	// ヘッダー全体を取得
	const header = document.querySelector('.main-header');
	// ハンバーガーメニュークリックイベント
	hamburger.addEventListener('click', function() {
		// ハンバーガーアイコンの状態を切り替え
		hamburger.classList.toggle('active');
		// メニューの表示/非表示を切り替え
		menu.classList.toggle('active');
	});

	// ドキュメント内のクリックイベント
	document.addEventListener('click', function(e) {
		// クリック対象がハンバーガー、メニュー、またはヘッダー内の要素でない場合
		if (
			!hamburger.contains(e.target) &&
			!menu.contains(e.target) &&
			!header.contains(e.target)
		) {
			// メニューを閉じる
			menu.classList.remove('active');
			hamburger.classList.remove('active');
		}
	});
});



// ログアウト時の動作
document.getElementById("logout-link").addEventListener("click", function(event) {

	// デフォルトのリンク動作をキャンセル
	event.preventDefault();

	// 確認ダイアログを表示
	const isConfirmed = confirm("本当にログアウトしますか？");

	// 「はい」が押された場合はトップページ
	if (isConfirmed) {
		window.location.href = "../top.html";
	}
	// 「いいえ」の場合は何もしない（ダイアログが閉じるだけ）
});
