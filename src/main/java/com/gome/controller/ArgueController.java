package com.gome.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ArgueController {



    @GetMapping("/argue")
    public String toArgue(){

        return "argue";
    }
}
