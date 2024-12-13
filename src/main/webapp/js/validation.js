const ADMIN_SHARED_PASSWORD = "admin12345";

function validateAdmin() {
    const inputPassword = prompt("管理者の共通パスワードを入力してください:");
    if (inputPassword !== ADMIN_SHARED_PASSWORD) {
        alert("パスワードが正しくありません。");
        return false;
    }
    return true;
}

function handleValidation() {
    // エラーメッセージをリセット
    clearErrors();

    // 各項目のバリデーション
    let isError = false;
    if (!validateName()) isError = true;
    if (!validateEmail()) isError = true;
    if (!validatePassword()) isError = true;

    // エラーがない場合、送信
    if (!isError) {
        alert("登録完了しました。ログイン画面に戻ります。");
        document.getElementById("registerForm").submit();
    }
}

function identitycheckValidation() {
    // エラーメッセージをリセット
    clearErrors();

    // 各項目のバリデーション
    let isError = false;
    if (!validateName()) isError = true;
    if (!validateEmail()) isError = true;

    // エラーがない場合、送信
    if (!isError) {
        document.getElementById("resetForm").submit();
    }
}


function clearErrors() {
    document.querySelectorAll(".error-message").forEach((el) => (el.textContent = ""));
}

function validateName() {
    const name = document.getElementById("user_name").value.trim();
    const nameError = document.getElementById("nameError");

    if (name === "") {
        nameError.textContent = "※必須項目です。";
        return false;
    } else if (name.length <= 2) {
        nameError.textContent = "※3文字以上入力してください。";
        return false;
    }
    return true;
}

function validateEmail() {
    const email = document.getElementById("user_email").value.trim();
    const emailError = document.getElementById("emailError");
    const emailPattern = /^[a-z\d][\w.-]*@[\w.-]+\.[a-z\d]+$/i;

    if (email === "") {
        emailError.textContent = "※必須項目です。";
        return false;
    } else if (!emailPattern.test(email)) {
        emailError.textContent = "※ xxx@xxx.xxxの形式で入力してください。";
        return false;
    }
    return true;
}

function validatePassword() {
    const password = document.getElementById("user_password").value.trim();
    const confirmPassword = document.getElementById("confirm_password").value.trim();
    const passwordError = document.getElementById("passwordError");
    const passwordPattern = /^(?=.*[a-zA-Z])(?=.*\d).{8,}$/;

    if (password === "") {
        passwordError.textContent = "※必須項目です。";
        return false;
    } else if (!passwordPattern.test(password)) {
        passwordError.textContent = "※下記の条件で入力してください";
        return false;
    } else if (password !== confirmPassword) {
        passwordError.textContent = "※パスワードが一致しません。";
        return false;
    }
    return true;
}