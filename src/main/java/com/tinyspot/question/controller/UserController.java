package com.tinyspot.question.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author tinyspot
 * @Time 2019/11/21-16:01
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("")
    public String getUserDetail(){

        return "hello world!";
    }
}
