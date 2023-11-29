package com.example.Pratice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AutoCloseable.class)
@Table(name = "dashboard")
public class DashBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    //아이디값 즉 순번을 의미

    @Column
    private String title;   // 제목

    @Column
    private String content; //주제

    @Column
    private String nickname;    // 닉네임

    @Column(name="uploadDate")
    @CreatedDate
    private LocalDate uploadDate; //업로드 시간

    @Column(name="viewCount")
    private int viewCount=0;  // 게시글 조회수 기본값 0으로 설정

    @Column
    private int commentCount=0;   //게시글 댓글수 기본값 0으로 설정







}
