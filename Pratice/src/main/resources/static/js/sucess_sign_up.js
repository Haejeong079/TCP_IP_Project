// function successful(){
//
//
//
// }


window.addEventListener('DOMContentLoaded', function() {
    var welcomeMessageElement = document.getElementById("welcomeMessage");
    var welcomeMessageText = welcomeMessageElement.innerText;

    if (welcomeMessageText) {
        // 클릭 이벤트를 추가하여 팝업을 표시
        welcomeMessageElement.onclick = function () {
            alert("환영합니다! 로그인 후 게시판을 이용해주세요!");
        }
    }
});
