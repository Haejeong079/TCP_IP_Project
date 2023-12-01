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
    private DashBoard dashBoard;
    private Member member;
    private String comment;
    private String createDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
    private String modifiedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));;
    //private List<Comment> comments;


    public CommentDto(String comment) {
        this.comment = comment;
    }

}
