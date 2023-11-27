package com.example.Pratice.service;
import com.example.Pratice.entity.Comment;
import com.example.Pratice.entity.DashBoard;
import com.example.Pratice.entity.Member;
import com.example.Pratice.repository.BoardRepository;
import com.example.Pratice.repository.CommentRepository;
import com.example.Pratice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void addComment(String commentText, String nickname, Long dashboardId) {
        // 댓글 작성 시간은 자동으로 설정하거나, 필요에 따라 설정

        Member member = memberRepository.findByNickname(nickname)
                .orElseThrow(() -> new RuntimeException("해당 닉네임의 사용자를 찾을 수 없습니다."));

        DashBoard dashboard = dashboardRepository.findById(dashboardId)
                .orElseThrow(() -> new RuntimeException("해당 대시보드를 찾을 수 없습니다."));

        Comment comment = Comment.builder()
                .comment(commentText)
                .member(member)
                .dashBoard(dashboard)
                .build();

        commentRepository.save(comment);
    }
}
