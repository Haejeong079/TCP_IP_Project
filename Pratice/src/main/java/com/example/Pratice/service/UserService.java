package com.example.Pratice.service;


import com.example.Pratice.dto.LoginDto;
import com.example.Pratice.entity.Member;
import com.example.Pratice.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public boolean isEmailExists(String email) {
        Optional<Member> existingMember = userRepository.findByEmail(email);
        if (existingMember.isPresent()) {
            // 조회결과가 있다 -> 사용할 수 없음
            log.info("중복된 이메일: " + email);
            return true;
        } else {
            // 조회결과가 없다 -> 사용할 수 있음
            log.info("사용가능 이메일: " + email);
            return false;
        }
    }


    public Member registerUser(LoginDto loginDto) {
        String email = loginDto.getEmail();
        String password = loginDto.getPassword();
        String nickname = loginDto.getNickname();


        if (isEmailExists(email)) {
            return null; // 이미 등록된 사용자
        }

        Member newMember = new Member();
        newMember.setEmail(loginDto.getEmail());
        newMember.setPassword(loginDto.getPassword());
        newMember.setNickname(loginDto.getNickname());
        userRepository.save(newMember);

        return newMember;
    }

    public Member login(LoginDto loginDto) {
        Member member = userRepository.findByEmailAndPassword(loginDto.getEmail(), loginDto.getPassword());

        return member;
    }

/*    public String getemail(HttpSession session) {

        return (String) session.getAttribute("email");
    }*/

}


