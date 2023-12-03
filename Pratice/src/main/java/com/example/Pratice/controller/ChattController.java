package com.example.Pratice.controller;

import com.example.Pratice.entity.Member;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class ChattController {

    @RequestMapping("/chatt")
    public ModelAndView chatt(Model model, HttpSession session){
        Member user = (Member) session.getAttribute("user");
        model.addAttribute("user", user.getNickname());

        ModelAndView mv = new ModelAndView();
        mv.setViewName("chat");

        model.addAttribute("nickname",user.getNickname());

        return mv;
    }
}
