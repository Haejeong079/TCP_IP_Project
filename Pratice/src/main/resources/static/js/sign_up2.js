// sign_up2.js
function checkEmail() {
    var emailInput = document.getElementById("email");
    var checkResultMessage = document.getElementById("check-result-message");
    var signupButton = document.getElementById("signupButton");

    var email = emailInput.value;

    // 이메일 입력이 없는 경우 처리
    if (email === "") {
        checkResultMessage.style.display = "block";
        checkResultMessage.style.color = "red";
        checkResultMessage.innerText = "이메일을 입력하세요.";
        signupButton.setAttribute("disabled", true);
        return;
    }

    // 이메일 주소에 '@' 문자가 포함되어 있는지 확인
    if (!email.includes('@')) {
        checkResultMessage.style.display = "block";
        checkResultMessage.style.color = "red";
        checkResultMessage.innerText = "이메일 주소에 '@'를 포함시켜주세요.";
        signupButton.setAttribute("disabled", true);
        return;
    }

    // 서버로 이메일 중복 체크 요청을 보냅니다.
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/signup/checkEmail?email=" + email, true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            checkResultMessage.style.display = "block";
            if (xhr.status === 200) {
                var response = JSON.parse(xhr.responseText);
                if (response.status === "ok") {
                    checkResultMessage.style.color = "green";
                    checkResultMessage.innerText = "사용 가능한 이메일";
                    signupButton.removeAttribute("disabled");
                } else {
                    checkResultMessage.style.color = "red";
                    checkResultMessage.innerText = "이미 사용중인 이메일";
                    signupButton.setAttribute("disabled", true);
                }
            }
        }
    };
    xhr.send();
}




