package com.example.Pratice.config;

import com.example.Pratice.entity.Member;
import lombok.Getter;
import org.apache.catalina.User;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private String nickname;
    private String email;

    public SessionUser(Member user) {
        this.nickname = user.getNickname(); // 또는 getName()에 해당하는 메서드로 수정
        this.email = user.getEmail();
    }

}