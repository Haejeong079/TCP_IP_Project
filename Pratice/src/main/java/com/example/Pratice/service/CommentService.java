package com.example.Pratice.service;
import com.example.Pratice.dto.CommentDto;
import com.example.Pratice.entity.Comment;
import com.example.Pratice.entity.DashBoard;
import com.example.Pratice.entity.Member;
import com.example.Pratice.repository.BoardRepository;
import com.example.Pratice.repository.CommentRepository;
import com.example.Pratice.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
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
        return comments(id).stream()
                .findFirst()
                .orElse(null); // 첫 번째 CommentDto를 반환하거나, 없으면 null 반환
    }




    @Transactional
    public void addComment(String commentText, Long id, Member sessionUser) {
        // 시간 설정 코드
        String currentDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        // sessionUser의 정보를 사용하여 Member 조회
        Optional<Member> memberOptional = memberRepository.findByNickname(sessionUser.getNickname());
        if (!memberOptional.isPresent()) {
            throw new RuntimeException("해당 닉네임의 사용자를 찾을 수 없습니다.");
        }
        Member member = memberOptional.get();
        DashBoard dashboard = dashboardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 대시보드를 찾을 수 없습니다."));

        Comment comment = Comment.builder()
                .comment(commentText)
                .member(member) // 댓글 작성자로 sessionUser의 Member 객체 사용
                .dashBoard(dashboard)
                .createDate(currentDateTime)
                .modifiedDate(currentDateTime)
                .build();
        commentRepository.save(comment);
    }





    public List<CommentDto> comments(Long dashboardId) {
        return commentRepository.findByDashBoard_Id(dashboardId).stream()
                .map(comment -> new CommentDto(comment.getComment()))
                .collect(Collectors.toList());
    }

}
