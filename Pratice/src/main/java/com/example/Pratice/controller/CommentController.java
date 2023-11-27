package com.example.Pratice.controller;

import com.example.Pratice.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/dashboard")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{nickname}/{dashboardId}/comment")
    public String addComment(@RequestParam("comment") String comment,
                             @PathVariable("nickname") String nickname,
                             @PathVariable("dashboardId") Long dashboardId) {
        commentService.addComment(comment, nickname, dashboardId);
        return "redirect:/dashboard/{nickname}/{dashboardId}";
    }
}
