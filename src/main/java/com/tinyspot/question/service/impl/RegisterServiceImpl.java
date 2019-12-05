package com.tinyspot.question.service.impl;

import cn.hutool.core.date.DateUtil;
import com.tinyspot.question.entity.User;
import com.tinyspot.question.mapper.RegisterMapper;
import com.tinyspot.question.service.RegisterService;
import com.tinyspot.question.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Author tinyspot
 * @Time 2019/11/23-17:01
 */
@Service
public class RegisterServiceImpl implements RegisterService {

    private static Logger LOG = LoggerFactory.getLogger(RegisterServiceImpl.class);

    @Autowired
    RegisterMapper registerMapper;

    /*
    用户名唯一返回true，否则返回false
     */
    @Override
    public boolean verifyUserNameUnique(String userName) {
        int cnt = 1;
        try {
            cnt = registerMapper.getCountByUserName(userName);
        } catch (Exception e){
            LOG.error("RegisterService.verifyUserNameUnique根据用户名查询用户数量出错！", e);
        }
        return cnt==0 ? true : false;
    }

    @Override
    public boolean register(User user) {
        //设置用户相关的属性
        //user.setUserType(1);用户类型数据库默认是普通用户，所以无需设置
        user.setPassword(CommonUtils.genSHA_256(user.getPassword()));
        Date date = DateUtil.date(System.currentTimeMillis());
        user.setRegisterTime(date);

        try {
            //插入
            registerMapper.addUser(user);
        } catch (Exception e) {
            LOG.error("RegisterService.register插入用户时数据库出错！", e);
            return false;
        }
        return true;
    }
}
