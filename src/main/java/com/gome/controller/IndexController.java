package com.gome.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Description: 首页
 * @Author: lixinkang
 * @Date: 2020/8/4 19:31
 * @Modified By:
 */
@Controller
public class IndexController {
    @GetMapping("index")
    public ModelAndView page(){
        return new ModelAndView("index");
    }

    @GetMapping("toindex")
    public String toIndex(){
        return "to_index";
    }

}
