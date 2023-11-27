function redirectToSignUp() {
    // 버튼 클릭 이벤트를 감지하여 회원 가입 페이지로 이동
    document.getElementById("signupButton").addEventListener("click", function() {
        // 이동할 페이지의 경로를 설정
        window.location.href = "/signup";
    });

    // 페이지 로드 시 redirectToSignUp 함수 호출
    window.onload = function() {
        redirectToSignUp();
    }


}
