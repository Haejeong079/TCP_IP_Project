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
        // LocalDateTime.now()를 직접 사용
        LocalDateTime currentDateTime = LocalDateTime.now();

        Member member = memberRepository.findByNickname(sessionUser.getNickname())
                .orElseThrow(() -> new RuntimeException("해당 닉네임의 사용자를 찾을 수 없습니다."));
        DashBoard dashboard = dashboardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 대시보드를 찾을 수 없습니다."));

        Comment comment = Comment.builder()
                .comment(commentText)
                .member(member)
                .dashBoard(dashboard)
                .createDate(currentDateTime)  // 직접 LocalDateTime.now() 사용
                .modifiedDate(currentDateTime)  // 직접 LocalDateTime.now() 사용
                .build();
        commentRepository.save(comment);

        // 댓글 수 업데이트
        DashBoard dashBoardEntity = dashboardRepository.findById(id).orElse(null);
        if (dashBoardEntity != null) {
            dashBoardEntity.setCommentCount(dashBoardEntity.getCommentCount() + 1);
            dashboardRepository.save(dashBoardEntity); // 댓글 수 변경을 데이터베이스에 저장
        }
    }






    public List<CommentDto> comments(Long dashboardId) {
        return commentRepository.findByDashBoard_Id(dashboardId).stream()
                .map(comment -> {
                    String formattedCreateDate = comment.getCreateDate() != null
                            ? comment.getCreateDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"))
                            : "Not Available";
                    String formattedModifiedDate = comment.getModifiedDate() != null
                            ? comment.getModifiedDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"))
                            : "Not Available";

                    return new CommentDto(
                            comment.getComment(),
                            comment.getMember().getNickname(),
                            formattedCreateDate,
                            formattedModifiedDate
                    );
                })
                .collect(Collectors.toList());
    }



}