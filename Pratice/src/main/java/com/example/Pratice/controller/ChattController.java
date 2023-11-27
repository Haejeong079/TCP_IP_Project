package com.example.Pratice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class ChattController {

    @RequestMapping("/chatt")
    public ModelAndView chatt(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("chat");

        return mv;
    }
}
