package com.tinyspot.question.service;

import com.tinyspot.question.entity.User;

/**
 * @Author tinyspot
 * @Time 2019/11/23-16:58
 */
public interface RegisterService {

    boolean verifyUserNameUnique(String userName);

    boolean register(User user);
}
