package com.example.Pratice.service;

import com.example.Pratice.entity.DashBoard;
import com.example.Pratice.repository.BoardRepository;
import com.example.Pratice.repository.CommentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class BoardService {


    private final BoardRepository boardRepository;


    private final CommentRepository commentRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository, CommentRepository commentRepository) {
        this.boardRepository = boardRepository;
        this.commentRepository = commentRepository;
    }

    @Transactional
    public void deleteDashBoard(Long id, String nickname) {
        DashBoard target = boardRepository.findByIdAndNickname(id, nickname);
        if(target != null){
            // 먼저 target DashBoard와 연관된 comment 삭제
            commentRepository.deleteByDashBoard_Id(target.getId());

            // 그 다음 DashBoard 삭제
            boardRepository.delete(target);
        }
    }
}
