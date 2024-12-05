document.addEventListener('DOMContentLoaded', function () {
    // ハンバーガーメニューとメニューの要素を取得
    const hamburger = document.getElementById('hamburger');
    const menu = document.querySelector('.menu');
    const header = document.querySelector('.main-header'); // ヘッダー全体を取得
    // console.log(menu);
    // ハンバーガーメニュークリックイベント
    hamburger.addEventListener('click', function () {
      // ハンバーガーアイコンの状態を切り替え
      hamburger.classList.toggle('active');
      // メニューの表示/非表示を切り替え
      menu.classList.toggle('active');
    });

    // ドキュメント内のクリックイベント
    document.addEventListener('click', function (e) {
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
  