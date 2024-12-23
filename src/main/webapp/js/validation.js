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


function resetpasswordValidation() {
	console.log("バリデーション開始");
    // エラーメッセージをリセット
    clearErrors();

    // 各項目のバリデーション
    let isError = false;
    if (!validatePassword()) isError = true;

    // エラーがない場合、送信
    if (!isError) {
		console.log("フォームを送信します");
        alert("パスワードの変更が完了しました。ログイン画面に戻ります。");
        document.getElementById("resetForm").submit();
    }		else {
		        console.log("バリデーションに失敗しました");
		    }
}


function clearErrors() {
    document.querySelectorAll(".error-message").forEach((el) => (el.textContent = ""));
}

function validateName() {
    const name = document.getElementById("name").value.trim();
    const nameError = document.getElementById("nameError");

    if (name === "") {
        nameError.textContent = "※必須項目です。";
        return false;
    } else if (name.length <= 1) {
        nameError.textContent = "※2文字以上入力してください。";
        return false;
    }
    return true;
}

function validateEmail() {
    const email = document.getElementById("email").value.trim();
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
    const password = document.getElementById("password").value.trim();
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



function editValidation() {
    // エラーメッセージをリセット
    clearErrors();

    // 各項目のバリデーション
    let isError = false;
    if (!validateEditName()) isError = true;
    if (!validateEditEmail()) isError = true;
    if (!validateEditPassword()) isError = true;

    // エラーがない場合、送信
    if (!isError) {
        alert("管理者情報が更新されました。");
        document.getElementById("editForm").submit();
    }
}

function validateEditName() {
    const name = document.getElementById("name").value.trim();
    const nameError = document.getElementById("nameError");

    if (name.length > 0 && name.length <= 1) {
        nameError.textContent = "※2文字以上入力してください。";
        return false;
    }
    return true;
}

function validateEditEmail() {
    const email = document.getElementById("email").value.trim();
    const emailError = document.getElementById("emailError");
    const emailPattern = /^[a-z\d][\w.-]*@[\w.-]+\.[a-z\d]+$/i;

    if (email.length > 0 && !emailPattern.test(email)) {
        emailError.textContent = "※ xxx@xxx.xxxの形式で入力してください。";
        return false;
    }
    return true;
}

function validateEditPassword() {
    const password = document.getElementById("password").value.trim();
    const passwordError = document.getElementById("passwordError");
    const passwordPattern = /^(?=.*[a-zA-Z])(?=.*\d).{8,}$/;

    if (password.length > 0 && !passwordPattern.test(password)) {
        passwordError.textContent = "※下記の条件で入力してください";
        return false;
    }
    return true;
}
