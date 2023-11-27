package com.example.Pratice.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data   //롬복의 기능!(getter, setter등) 여러 기능을 제공함
@Table(name="member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //DB가 id를 자동생성
    private Long id;

    @Column(name = "email")
    private String email;

    @Column
    private String password;

    @Column
    private String nickname;



}
