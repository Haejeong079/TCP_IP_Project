package com.example.Pratice.repository;

import com.example.Pratice.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    List<Comment> findByDashBoard_Id(Long id);

    void deleteByDashBoard_Id(Long dashboardId);
}
