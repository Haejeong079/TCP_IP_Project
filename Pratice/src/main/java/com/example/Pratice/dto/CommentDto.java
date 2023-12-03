package com.example.Pratice.dto;

import com.example.Pratice.entity.Comment;
import com.example.Pratice.entity.DashBoard;
import com.example.Pratice.entity.Member;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDto {
    private Long id;
    private String memberNickname;
    private String comment;
    private String createDate; // 초기화 제거
    private String modifiedDate; // 초기화 제거

    public CommentDto(String comment, String nickname, String createDate, String modifiedDate) {
        this.comment = comment;
        this.memberNickname = nickname;
        this.createDate = createDate; // 생성자를 통해 설정
        this.modifiedDate = modifiedDate; // 생성자를 통해 설정
    }
}

