package com.example.Pratice.controller;

import com.example.Pratice.config.LoginUser;
import com.example.Pratice.config.SessionUser;
import com.example.Pratice.dto.CommentDto;
import com.example.Pratice.entity.Comment;
import com.example.Pratice.entity.Member;
import com.example.Pratice.service.CommentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/dashboard")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {

        this.commentService = commentService;
    }


    @PostMapping("/{nickname}/{id}/comment")
    public String addComment(@RequestParam("comment") String comment,
                             @PathVariable("nickname") String nickname,
                             @PathVariable("id") Long id,
                             Model model,
                             HttpSession session) {
        // 현재 로그인한 사용자를 세션에서 가져오기
        Member user = (Member) session.getAttribute("user");

        String comments = commentService.findById(id).getComment();

        if (user == null) {
            // 사용자가 로그인하지 않은 경우 에러 처리
            // 예: 로그인 페이지로 리다이렉트
            return "redirect:/login";
        }

        // 댓글 추가
        commentService.addComment(comment, id, user);

        model.addAttribute("comments", comments);

        // 리다이렉트 URL은 현재 보고 있는 게시글로
        return "redirect:/dashboard/" + nickname + "/" + id;
    }



}