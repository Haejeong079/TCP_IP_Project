package com.example.Pratice.config;

import com.example.Pratice.entity.Member;
import lombok.Getter;
import org.apache.catalina.User;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private Long id;      // 사용자의 고유 ID
    private String nickname;
    private String email;

    public SessionUser(Member user) {

        this.id = user.getId();           // Member의 ID를 저장
        this.nickname = user.getNickname();
        this.email = user.getEmail();
    }
}
