package com.example.Pratice.entity;

import jakarta.persistence.*;
import jdk.jshell.Snippet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    //아이디값 즉 순번을 의미

    @ManyToOne
    @JoinColumn(name = "dashboard_id")
    private DashBoard dashBoard;

    @ManyToOne
    @JoinColumn(name="nickname_id")
    private Member member;



    @Column(columnDefinition = "TEXT", nullable = false)
    private String comment;

    @Column(name = "create_date", updatable = false)
    @CreatedDate
    private String createDate;

    @Column(name = "modified_date")
    @LastModifiedDate
    private String modifiedDate;


}
