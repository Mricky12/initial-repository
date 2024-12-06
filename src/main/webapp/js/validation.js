const ADMIN_SHARED_PASSWORD = "admin12345";

function validateAdmin() {
    const inputPassword = prompt("管理者の共通パスワードを入力してください:");
    if (inputPassword !== ADMIN_SHARED_PASSWORD) {
        alert("パスワードが正しくありません。");
        return false;
    }
    return true;
}

function validateAdminLogin() {
    const form = document.getElementById("adminLoginForm");
    const inputPassword = prompt("管理者の共通パスワードを入力してください:");
    if (inputPassword !== ADMIN_SHARED_PASSWORD) {
        alert("パスワードが正しくありません。");
        return;
    }
    form.submit();
}
