package com.tinyspot.question.controller;

import com.tinyspot.question.entity.Test;
import com.tinyspot.question.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * @Author tinyspot
 * @Time 2019/12/1-22:40
 */
@RestController
public class TestController {

//    @PostMapping("/test")
//    public String test(@RequestBody String jsonString){
//        System.out.println("post: " + jsonString);
//        return "ok";
//    }

    @PostMapping("/test")
    public String test(@RequestBody @RequestParam("test") ArrayList<Test> list,  ArrayList<User> users){
        System.out.println("post: " + list + users);
        return "ok";
    }
    @GetMapping("/test")
    public String test1(@RequestBody ArrayList<Test> list, @RequestBody ArrayList<User> users){
        System.out.println("get: " + list + users);
        return "ok";
    }
}
