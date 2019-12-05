package com.tinyspot.question.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author tinyspot
 * @Time 2019/11/22-14:48
 * 对于根目录的一个控制器
 */
@Controller
public class RootController {

    @GetMapping("/")
    public String toIndex(){
        return "redirect:/views/index.html";
    }
}
