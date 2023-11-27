package com.example.Pratice.controller;


import com.example.Pratice.dto.LoginDto;
import com.example.Pratice.entity.Member;
import com.example.Pratice.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
public class PrController {

    @Autowired
    private UserService userService;

    // 회원 가입 페이지
    @GetMapping("/signup")
    public String signup() {


        return "user/signup";
    }

    // 회원 가입 처리
    @PostMapping("/signup")
    public String processSignup(LoginDto loginDto, HttpSession session, Model model) {
        String email = loginDto.getEmail();
        String password = loginDto.getPassword();
        String nickname = loginDto.getNickname();

        if (email.isEmpty() || password.isEmpty()) {
            model.addAttribute("error", true);
            model.addAttribute("errormessage", "이메일과 비밀번호를 모두 입력하세요.");
            return "user/signup";
        }

        Member newMember = userService.registerUser(loginDto);
        if (newMember != null) {
//            session.setAttribute("successMessage", "회원가입 성공!");
//            model.addAttribute("success", true);
//            model.addAttribute("welcomeMessage", "회원가입 성공! 환영합니다! 로그인 후 게시판을 이용해주세요!");
            log.info("회원가입 완료");
            log.info("닉네임 : " + nickname);
            log.info("이메일 : " + email);
            log.info("비밀번호 : " + password);

            return "redirect:/login";
        } else {
//            session.setAttribute("errorMessage", "회원가입 실패!");
//            model.addAttribute("error", true);
//            model.addAttribute("errormessage", "Email already exists");

            log.warn("회원가입 실패 !");
            log.warn("닉네임 : " + nickname);
            log.warn("이메일 : " + email);
            log.warn("비밀번호 : " + password);

            return "user/signup";
        }
    }

    // 중복 체크를 위한 컨트롤러 엔드포인트
    @PostMapping("/signup/checkEmail")
    @ResponseBody
    public Map<String, String> emailCheck(@RequestParam("email") String email) {
        log.info("email = " + email);
        boolean isEmailExists = userService.isEmailExists(email);
        Map<String, String> response = new HashMap<>();

        if (isEmailExists) {
            response.put("status", "no"); // 이미 사용 중인 이메일
        } else {
            response.put("status", "ok"); // 사용 가능한 이메일
        }

        return response;
    }




    // 로그인 페이지
    @GetMapping("/login")
    public String login() {
        return "user/login";
    }

    // 로그인 처리
    @PostMapping("/login")
    public String processLogin(LoginDto loginDto, HttpSession session, Model model) {
        Member member = userService.login(loginDto);
        if (member != null) {

            // 사용자 정보를 세션에 저장
            session.setAttribute("user", member);

            return "redirect:/dashboard"; // 로그인 성공 시 대시보드 페이지로 리다이렉트
        } else {
            model.addAttribute("error",true);
            model.addAttribute("errorEorP", "이메일 또는 패스워드를 다시 확인하시오."); // 이메일 또는 패스워드가 틀렸을 경우
            session.setAttribute("errorMessage","로그인 실패!");
            return "user/login"; // 로그인 실패 시 다시 로그인 페이지로 이동
        }
    }

    //로그아웃 처리
    @GetMapping("/logout")
    public String logout(HttpSession session){

        //세션에서 사용자 정보 삭제
        session.removeAttribute("user");


        // 로그아웃 후 리다이렉트할 페이지 설정
        return "redirect:/dashboard";
    }





}

