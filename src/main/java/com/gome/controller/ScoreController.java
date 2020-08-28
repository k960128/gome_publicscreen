package com.gome.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ScoreController {

    /**
     * 跳转至得分界面
     * @return
     */
    @GetMapping("/score")
    public String to_Wait(){
        return "score";
    }

}
