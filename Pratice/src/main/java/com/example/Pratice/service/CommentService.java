package com.example.Pratice.service;
import com.example.Pratice.config.SessionUser;
import com.example.Pratice.dto.CommentDto;
import com.example.Pratice.entity.Comment;
import com.example.Pratice.entity.DashBoard;
import com.example.Pratice.entity.Member;
import com.example.Pratice.repository.BoardRepository;
import com.example.Pratice.repository.CommentRepository;
import com.example.Pratice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository memberRepository;
    private final BoardRepository dashboardRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, UserRepository memberRepository, BoardRepository dashboardRepository) {
        this.commentRepository = commentRepository;
        this.memberRepository = memberRepository;
        this.dashboardRepository = dashboardRepository;
    }

    public CommentDto findById(Long id) {
        CommentDto commentDto = new CommentDto();

        // CommentRepository를 사용하여 댓글을 조회하고 CommentDto에 설정
        List<Comment> comments = commentRepository.findByDashBoard_Id(id);

        // 나머지 코드는 그대로 유지
        commentDto.setComments(comments);

        return commentDto;
    }


    public void addComment(String commentText, String nickname, Long id, SessionUser sessionUser) {
        // 시간 설정 코드
        String currentDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        Member member = memberRepository.findByNickname(nickname)
                .orElseThrow(() -> new RuntimeException("해당 닉네임의 사용자를 찾을 수 없습니다."));

        DashBoard dashboard = dashboardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 대시보드를 찾을 수 없습니다."));

        Comment comment = Comment.builder()
                .comment(commentText)
                .member(member)
                .dashBoard(dashboard)
                .createDate(currentDateTime)
                .modifiedDate(currentDateTime)
                .build();

        commentRepository.save(comment);
    }

}
