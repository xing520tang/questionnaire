package com.tinyspot.question.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

/**
 * @Author tinyspot
 * @Time 2019/11/25-15:24
 * 需要用到重定向的所有请求
 */
@Controller
public class RedirectController {

    private static Logger LOG = LoggerFactory.getLogger(RedirectController.class);

    /**
     * 用户退出接口
     * @param session
     * @return
     */
    @GetMapping("/logout")
    public String logout(HttpSession session){
        LOG.info(session.getAttribute("nickName") + " 退出系统...GET-/logout");
        session.removeAttribute("userId"); //移除session中保存的数据
        session.removeAttribute("userType");
        session.removeAttribute("imgName");
        session.removeAttribute("nickName");
        return "redirect:/views/index.html"; //重定向至主页
    }


    /**
     * 获取发布问卷页面
     * @return
     */
    @GetMapping("/paper")
    public String getPaperNewPage(){
        LOG.debug("/paper...GET");
        return "redirect:/views/paper_new.html";
    }
}
