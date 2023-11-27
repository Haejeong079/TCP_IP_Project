package com.example.Pratice.session;

import com.example.Pratice.entity.Member;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

//@Slf4j
public class SessionInterceptor implements HandlerInterceptor {

//    @Autowired
//    private SessionManager sessionManager;
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        // 세션 이메일을 검사하여 유효한지 확인
//        HttpSession session = request.getSession();
//        Member loginMember = (Member) session.getAttribute("loginMember");
//
//        if (loginMember == null) {
//            // 로그인 안했을 경우
//            // email 파라미터가 없는 경우
//            String email = request.getParameter("email");
//            if (email == null) {
//                // 로그인 페이지로 이동
//                response.sendRedirect("/login");
//                return false;
//            }
//
//            // email 파라미터가 있는 경우
//            // 세션 id가 유효한지 확인!
//            String sessionId = request.getRequestedSessionId();
//            if (!sessionManager.isSessionExists(email, sessionId)) {
//                // 로그인 페이지로 이동
//                response.sendRedirect("/login");  // 수정: 로그인 페이지로 리다이렉트
//                return false;
//            }
//        }
//        return true;
//    }
}
