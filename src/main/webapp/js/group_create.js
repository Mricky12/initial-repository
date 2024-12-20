document.addEventListener("DOMContentLoaded", function () {
    
    
    	const urlParams = new URLSearchParams(window.location.search);
        const success = urlParams.get("success");
        const groupName = urlParams.get("groupName");

        if (success === "true" && groupName) {
            // JavaScript内でdecodeURIComponentを使用
            const decodedGroupName = decodeURIComponent(groupName);
            alert(`グループ「${decodedGroupName}」が正常に作成されました。`);
            window.location.href = "groupmemberedit.jsp"; // 作成完了後に遷移
        }
   
    // フォーム送信時のバリデーション
    const form = document.querySelector("form");
    if (form) {
        form.addEventListener("submit", function (e) {
            const groupName = document.getElementById("groupname").value.trim();
            if (!groupName) {
                e.preventDefault(); // フォーム送信を中断
                alert("グループ名を入力してください。");
            }
        });
    }
    
    // サーバーサイドからのエラーメッセージを表示
            const errorMessage = document.getElementById("error-message").dataset.error;
            if (errorMessage) {
                alert(errorMessage);
            }
});
