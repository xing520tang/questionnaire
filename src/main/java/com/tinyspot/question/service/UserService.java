package com.tinyspot.question.service;

import com.tinyspot.question.entity.User;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author tinyspot
 * @Time 2019/11/25-11:34
 */
public interface UserService {

    User loginVerify(String userName, String password);

    User getUser(Integer userId);

    boolean updateNickName(String nickName, Integer userId);

    int updatePassword(String prePass, String newPass, Integer userId);

    int updateHeadImage(StringBuilder picPath, MultipartFile pic);

    boolean updateImgName(String s, Integer userId);
}
