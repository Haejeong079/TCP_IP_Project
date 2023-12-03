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
        Member user = (Member) session.getAttribute("user");

        if (user == null) {
            return "redirect:/login";
        }

        commentService.addComment(comment, id, user);

        return "redirect:/dashboard/" + nickname + "/" + id; // 닉네임 사용의 정확성 확인
    }




}