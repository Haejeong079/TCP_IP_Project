// validation.js

function validateForm() {
    var email = document.getElementById("email").value;
    var password = document.getElementById("password").value;

    if (email === "" || password === "") {
        showErrorPopup("Please fill in both email and password fields.");
        return false;
    }

    //폼이 제출될 수 있도록 true로 반환
    return true;
}

function showErrorPopup(message) {
    alert(message);
}