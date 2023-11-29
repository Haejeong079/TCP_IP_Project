package com.example.Pratice.service;
import com.example.Pratice.config.SessionUser;
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



    public void addComment(String commentText, String nickname, Long id, SessionUser sessionUser) {
        // 시간 설정 코드
        String currentDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        Member member = UserRepository.findByNickname(nickname)
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

    public List<CommentDto> comments(Long Dashboardid) {

        // 반환
        return commentRepository.findByDashBoard_Id(Dashboardid) // commentRepository에 목록조회
                .stream() // stream으로 변경
                .map(CommentDto::createCommentDto) //createCommentDto를 통해 comment를 하나하나전달하여 DTO로 변환
                .collect(Collectors.toList()); // map이 반환하는 값이 stream<Object>이기 때문에
    }



}
