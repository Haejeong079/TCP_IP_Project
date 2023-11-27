package com.example.Pratice.repository;

import com.example.Pratice.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Member,Long> {

    //로그인시 이메일, 패스워드 찾아오는거
    Member findByEmailAndPassword(String email, String password);



    Optional<Member> findByEmail(String email);

    Optional<Member> findByNickname(String nickname);

    //Member findByNickname(String nickname);


}
