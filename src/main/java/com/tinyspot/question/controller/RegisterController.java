package com.tinyspot.question.controller;

import com.tinyspot.question.entity.Result;
import com.tinyspot.question.entity.User;
import com.tinyspot.question.service.RegisterService;
import com.tinyspot.question.util.ResultGeneratorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author tinyspot
 * @Time 2019/11/23-16:24
 * 和注册页面相关的请求
 */
@RestController
@RequestMapping("/register")
public class RegisterController {

    private static Logger LOG = LoggerFactory.getLogger(RegisterController.class);

    @Autowired
    RegisterService registerService;
    /**
     * 验证用户名是否重复
     * @param userName 传入前端输入的用户名
     * @return 返回json字符串
     */
    @GetMapping("/verify")
    public Map<String, Boolean> verifyUserName(@RequestParam("userName") String userName){
        LOG.debug("验证用户名是否重复：" + userName);
        Map<String, Boolean> res = new HashMap<>();

        boolean unique = registerService.verifyUserNameUnique(userName);
        if(unique)
            res.put("valid", true);
        else res.put("valid", false);
        return res;
    }


    /**
     * 注册用户，添加一个用户到数据库
     * @param user
     * @return 返回添加结果
     */
    @PostMapping("")
    public Result register(User user){
        LOG.debug("用户注册.../register-POST");
        System.out.println(user);

        Result res = null;

        boolean ok = registerService.register(user);
        if (ok) {
            res = ResultGeneratorUtil.genSuccessResult();
            LOG.info("用户" + user.getUserName() + "注册成功...");
        } else {
            res = ResultGeneratorUtil.genFailResult();
            LOG.info("用户 注册失败...");
        }
        return res;
    }
}
