package com.tinyspot.question.controller;

import cn.hutool.core.util.ObjectUtil;
import com.tinyspot.question.entity.Result;
import com.tinyspot.question.util.ResultGeneratorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Author tinyspot
 * @Time 2019/11/21-16:01
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private static Logger LOG = LoggerFactory.getLogger(UserController.class);

    @GetMapping("")
    public String getUserDetail(){

        return "hello world!";
    }

    @PostMapping("")
    public String updateUser(@RequestParam("userName") String userName){
        System.out.println(userName);
        return "xiugaichenggong!";
    }

    @GetMapping("/login")
    public String login(){
        System.out.println("denglu");
        return "";
    }

    @GetMapping("/auth")
    public Result auth(HttpServletRequest request){
        LOG.debug("/auth...");
        HttpSession session = request.getSession();
        Result result = ResultGeneratorUtil.genSuccessResult();
        //未登录
        if (ObjectUtil.isEmpty(session.getAttribute("userId"))) {
            result.setCode(ResultGeneratorUtil.NEED_LOGIN);
        } else { //已经登录
            result.setCode(ResultGeneratorUtil.ALREADY_LOGGED);
        }
        return result;
    }
}
