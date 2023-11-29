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
    private DashBoard dashBoard;
    private Member member;
    private String comment;
    private String createDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
    private String modifiedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));;
    private List<Comment> comments;

    public CommentDto(Long id, Long dashboardId, String nickname, String comment) {
    }



    public static CommentDto createCommentDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getDashBoard().getId(),
                comment.getMember().getNickname(), // Member의 닉네임 가져오기
                comment.getComment()
        );
    }



    /* DTO -> Entity */
    public Comment toEntity(){
        Comment comments = Comment.builder()
                .id(id)
                .comment(comment)
                .createDate(createDate)
                .modifiedDate(modifiedDate)
                .member(member)
                .dashBoard(dashBoard)
                .build();

        return comments;
    }


}
